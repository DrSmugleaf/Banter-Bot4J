package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.character.Character;
import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.charactersheets.stat.Stat;
import com.github.drsmugleaf.charactersheets.stat.Stats;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;

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
                                    for (Stats stats : character.getSheet().getStats().values()) {
                                        embed.addField(stats.getName(), "\u200b", false);

                                        for (Stat stat : stats.get().values()) {
                                            embed.addField(stat.getName(), String.valueOf(stat.getValue()), true);
                                        }
                                    }
                            }))).subscribe();
                });
    }

}
