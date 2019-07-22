package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 23/01/2018.
 */
@CommandInfo(
        tags = {Tags.GUILD_ONLY},
        description = "Change your name color"
)
public class Color extends Command {

    @Nullable
    private static java.awt.Color resolve(String string) {
        java.awt.Color color = null;
        try {
            color = java.awt.Color.decode(string);
        } catch (NumberFormatException nfe) {
            try {
                color = java.awt.Color.decode("#" + string);
            } catch (NumberFormatException nfe2) {
                try {
                    color = (java.awt.Color) java.awt.Color.class.getField(string.trim().toUpperCase().replace(" ", "_")).get(null);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    LOGGER.warn("Error resolving color " + string, e);
                }
            }
        }

        return color;
    }

    @Argument(position = 1, example = "#FF0000", maxWords = Integer.MAX_VALUE, optional = true)
    private String color;

    @Override
    public void run() {
        Mono<Guild> guildMono = EVENT.getGuild();

        guildMono
                .flatMapMany(Guild::getRoles)
                .zipWith(Mono.justOrEmpty(EVENT.getMessage().getAuthor().map(User::getId)).repeat())
                .filter(tuple -> tuple.getT1().getName().contains("color-" + tuple.getT2().asLong()))
                .map(Tuple2::getT1)
                .collect(Collectors.toList())
                .zipWith(guildMono)
                .subscribe(tuple -> {
                    List<Role> roles = tuple.getT1();
                    Guild guild = tuple.getT2();

                    if (color == null) {
                        if (roles.isEmpty()) {
                            reply("You don't have a name color. Use " + BanterBot4J.BOT_PREFIX + "color name OR hexadecimal code to assign one.").subscribe();
                            return;
                        }

                        Role colorRole = roles.get(0);
                        colorRole
                                .getPosition()
                                .filterWhen(colorPosition -> SELF_MEMBER
                                        .getRoles()
                                        .filter(botRole -> botRole.getPermissions().contains(Permission.MANAGE_ROLES))
                                        .single()
                                        .hasElement()
                                )
                                .doOnDiscard(Integer.class, (position) -> reply("I don't have permission to manage roles.").subscribe())
                                .filterWhen(colorPosition -> SELF_MEMBER
                                        .getRoles()
                                        .filter(botRole -> botRole.getPermissions().contains(Permission.MANAGE_ROLES))
                                        .flatMap(Role::getPosition)
                                        .filter(botRolePosition -> botRolePosition > colorPosition)
                                        .single()
                                        .hasElement()
                                )
                                .doOnDiscard(Integer.class, (position) -> reply(
                                        "I can't remove your name color.\n" +
                                                "My highest role with the permission to modify roles has a lower position in the role list than your colored role."
                                ).subscribe())
                                .flatMap(position -> EVENT.getMessage().getAuthorAsMember())
                                .zipWith(
                                        guild
                                                .getMembers()
                                                .groupBy(User::getId)
                                                .flatMap(member -> member.reduce((a, b) -> a.getId().compareTo(b.getId()) > 0 ? a : b)) // Remove once Discord4J#543 is fixed
                                                .filterWhen(member -> member.getRoles().hasElement(colorRole))
                                                .count()
                                )
                                .doOnNext(tuple2 -> tuple2.getT1().removeRole(colorRole.getId()).subscribe())
                                .doOnNext(tuple2 -> {
                                    if (tuple2.getT2() <= 1) {
                                        colorRole.delete().subscribe();
                                    }
                                })
                                .map(tuple2 -> String.format("#%06x", colorRole.getColor().getRGB() & 0x00FFFFFF).toUpperCase())
                                .flatMap(hex -> reply("Removed your name color. It was " + hex))
                                .subscribe();
                    } else {
                        java.awt.Color colorObject = resolve(color);
                        if (colorObject == null) {
                            reply("Invalid color. Make sure it is a hexadecimal string (0000FF) or a simple color like red.").subscribe();
                            return;
                        }

                        if (roles.isEmpty()) {
                            EVENT
                                    .getMessage()
                                    .getAuthorAsMember()
                                    .filterWhen(author -> SELF_MEMBER
                                            .getRoles()
                                            .filter(botRole -> botRole.getPermissions().contains(Permission.MANAGE_ROLES))
                                            .single()
                                            .hasElement()
                                    )
                                    .doOnDiscard(Member.class, (author) -> reply("I don't have permission to manage roles.").subscribe())
                                    .zipWhen(author -> guild.createRole(role -> role
                                            .setName("color-" + author.getId().asString())
                                            .setColor(colorObject)
                                            .setPermissions(PermissionSet.none())
                                    ).map(Role::getId))
                                    .flatMap(tuple2 -> tuple2.getT1().addRole(tuple2.getT2()))
                                    .then(reply("Changed your name color to " + color))
                                    .subscribe();
                        } else {
                            Role role = roles.get(0);
                            String oldHex = String.format("#%06x", role.getColor().getRGB() & 0x00FFFFFF).toUpperCase();
                            EVENT
                                    .getMessage()
                                    .getAuthorAsMember()
                                    .zipWith(role.edit(spec -> spec.setColor(colorObject)).map(Role::getId))
                                    .flatMap(tuple2 -> tuple2.getT1().addRole(tuple2.getT2()))
                                    .doOnError(e -> reply("I can't modify your name color. Check my highest role with permission to manage roles.").subscribe())
                                    .then(reply("Changed your name color to " + color + ". Your old name color's hex code was " + oldHex))
                                    .subscribe();
                        }
                    }
                });
    }

}
