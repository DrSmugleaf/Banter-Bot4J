package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.character.CharacterBuilder;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import discord4j.core.object.entity.User;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
@CommandInfo(
        aliases = {"cc"},
        description = "Create a character"
)
public class CharacterCreate extends Command {

    private static final Map<User, CharacterBuilder> CHARACTERS = new HashMap<>();

    @Argument(position = 1, maxWords = Integer.MAX_VALUE, examples = "Ash")
    private String name;

    @Contract(pure = true)
    public static Map<User, CharacterBuilder> getCharacters() {
        return CHARACTERS;
    }

    @Override
    public void run() {
        CharacterBuilder character = new CharacterBuilder();
        character.setName(this.name);
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> CHARACTERS.put(author, character));
    }

}
