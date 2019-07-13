package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
@CommandInfo(aliases = {"cf"})
public class CharacterFinish extends Command {

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> {
                    CharacterBuilder builder = CharacterCreate.getCharacters().get(author);
                    Character character = builder.build();

                    EVENT
                            .getMessage()
                            .getChannel()
                            .flatMap(channel -> channel.createMessage(message -> message.setEmbed(embed -> {
                                    embed.setTitle(character.getName());
                                    for (StatGroup statGroup : character.getSheet().getStats().values()) {
                                        List<String> statsDescription = statGroup.get().values().stream().map(stat -> "**" + stat.getName() + ":** " + stat.getValue()).collect(Collectors.toList());
                                        embed.addField("**" + statGroup.getName().toUpperCase() + "**", String.join("\n", statsDescription), false);
                                    }
                            }))).subscribe();
                });
    }

}
