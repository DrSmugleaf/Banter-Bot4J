package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.util.Bot;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.RoleBuilder;
import sx.blah.discord.util.audio.AudioPlayer;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Brian on 13/05/2017.
 */
public class Basic {

    private static final String MAGIC_8_BALL_RESPONSES[] = {
            "It is certain", "It is decidedly so", "Without a doubt", "Yes definitely", "You may rely on it",
            "As I see it, yes", "Most likely", "Outlook good", "Yes", "Signs point to yes",
            "Reply hazy try again", "Ask again later", "Better not tell you now", "Cannot predict now",
            "Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
            "Outlook not so good", "Very doubtful"
    };

    private static final String INFO = "GitHub link: https://github.com/cajon-de-brian/BlueBot/\n" +
            "Made in Java using Discord4J";

    @Command
    public static void echo(MessageReceivedEvent event, List<String> args){
        String echo = String.join(" ", args);
        try {
            event.getMessage().delete();
        } catch(MissingPermissionsException e) {/* Don't do anything */}
        Bot.sendMessage(event.getChannel(), echo);
    }

    @Command
    public static void roll(MessageReceivedEvent event, List<String> args){
        int randomNumber = new Random().nextInt(100) + 1;
        Bot.sendMessage(event.getChannel(), String.valueOf(randomNumber));
    }

    @Command
    public static void join(MessageReceivedEvent event, List<String> args){
        IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();
        if(userVoiceChannel == null)
            return;
        userVoiceChannel.join();
    }

    @Command
    public static void leave(MessageReceivedEvent event, List<String> args){
        IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();
        if(botVoiceChannel == null)
            return;
        AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
        audioP.clear();
        botVoiceChannel.leave();
    }

    @Command
    public static void magic8ball(MessageReceivedEvent event, List<String> args) {
        int randomID = new Random().nextInt(Basic.MAGIC_8_BALL_RESPONSES.length);
        Bot.sendMessage(event.getChannel(), Basic.MAGIC_8_BALL_RESPONSES[randomID]);
    }

    @Command
    public static void whois(MessageReceivedEvent event, List<String> args) {
        IUser author = event.getAuthor();

        event.getMessage().getMentions().forEach((mention) -> {
            EmbedBuilder builder = new EmbedBuilder();
            String mentionNickname = mention.getNicknameForGuild(event.getGuild());
            List<IRole> mentionRoles = mention.getRolesForGuild(event.getGuild());

            builder.withAuthorName(String.format("%s#%s (ID: %d)", mention.getName(), mention.getDiscriminator(), mention.getLongID()));
            builder.withAuthorIcon(mention.getAvatarURL());

            builder.appendField(
                    "Member Details",
                    "" + (mentionNickname != null ? "Nickname: " + mentionNickname : "No nickname") + "\n" +
                    "Roles: " + StringUtils.join(mentionRoles, ", ") + "\n" +
                    "Joined at: " + mention.getCreationDate(),
                    false
            );

            builder.appendField(
                    "User Details",
                    "" + (mention.isBot() ? "Is a bot account\n" : "") +
                    "Status: " + mention.getPresence().getStatus().toString() + "\n" +
                    "Game: " + (mention.getPresence().getPlayingText().orElse("None")),
                    false
            );

            builder.withFooterIcon(author.getAvatarURL());
            builder.withFooterText("Requested by " + author.getDisplayName(event.getGuild()));

            RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
        });
    }

    @Command
    public static void info(MessageReceivedEvent event, List<String> args) {
        Bot.sendMessage(event.getChannel(), Basic.INFO);
    }

    @Command
    public static void color(MessageReceivedEvent event, List<String> args) {
        String requestedColor = 0 < args.size() ? args.get(0) : null;
        IUser author = event.getAuthor();
        IChannel channel = event.getChannel();
        IGuild guild = event.getGuild();
        List<IRole> authorColoredRoles = author.getRolesForGuild(guild);
        authorColoredRoles.removeIf(role -> role.getColor().equals(Color.black));
        IRole highestAuthorColoredRole = Bot.getHighestRole(authorColoredRoles);
        List<IUser> usersWithColoredRole = guild.getUsersByRole(highestAuthorColoredRole);
        IRole highestSelfRole = Bot.getHighestRole(event.getClient().getOurUser(), guild);

        if(requestedColor == null) {
            if(highestAuthorColoredRole == null) {
                Bot.sendMessage(channel, "I can't remove your name color because you don't have one.");
                return;
            }

            if(!highestAuthorColoredRole.getName().startsWith("color-")) {
                Bot.sendMessage(channel, "Your name color was assigned by someone else.");
                return;
            }

            author.removeRole(highestAuthorColoredRole);
            usersWithColoredRole.remove(author);
            if(usersWithColoredRole.isEmpty()) {
                highestAuthorColoredRole.delete();
            }
            Bot.sendMessage(channel, "Removed your name color.");
        } else {
            if(highestAuthorColoredRole != null && highestSelfRole != null && highestAuthorColoredRole.getPosition() > highestSelfRole.getPosition()) {
                Bot.sendMessage(channel, "I can't change your name color.\n" +
                        "My highest role has a lower position in the role manager than your highest role.");
                return;
            }

            Color color;
            try {
                color = Color.decode(requestedColor);
            } catch (NumberFormatException nfe) {
                Bot.LOGGER.error("Error decoding color " + requestedColor, nfe);
                try {
                    color = (Color) Color.class.getField(requestedColor.trim().toUpperCase().replace(" ", "_")).get(null);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    Bot.LOGGER.error("Error getting color field " + requestedColor, e);
                    Bot.sendMessage(channel, "Invalid color. Make sure it is a hexadecimal string (#0000FF) or a simple color like red.");
                    return;
                }
            }

            if(highestAuthorColoredRole != null && highestAuthorColoredRole.getColor().equals(color)) {
                Bot.sendMessage(channel, "You already have that name color.");
                return;
            }

            String hexCode = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
            List<IRole> guildRoles = guild.getRoles().stream()
                    .filter(role -> role.getName().startsWith("color-" + hexCode))
                    .collect(Collectors.toList());

            if(guildRoles.isEmpty()) {
                IRole newColorRole = new RoleBuilder(guild)
                        .withName("color-" + hexCode)
                        .withColor(color)
                        .build();

                author.addRole(newColorRole);
            } else {
                author.addRole(guildRoles.get(0));
            }

            if(highestAuthorColoredRole != null) {
                author.removeRole(highestAuthorColoredRole);
                usersWithColoredRole.remove(author);
                if(usersWithColoredRole.isEmpty()) {
                    highestAuthorColoredRole.delete();
                }
            }

            Bot.sendMessage(channel, "Added name color " + hexCode);
        }
    }

}
