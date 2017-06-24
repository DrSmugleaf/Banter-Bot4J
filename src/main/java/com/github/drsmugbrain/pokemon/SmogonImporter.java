package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.DiscordException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public class SmogonImporter {

    public static Pokemon parsePokemon(String export) throws DiscordException {
        export = export.trim();
        String[] exportArray = export.split("\n");

        if (export.length() == 0) {
            throw new DiscordException("Add a Pokemon in Smogon format to the command to start. Example:\n" +
                    "-pokemon Serperior @ Leftovers\n" +
                    "Ability: Contrary\n" +
                    "EVs: 252 SpA / 4 SpD / 252 Spe\n" +
                    "Timid Nature\n" +
                    "- Leaf Storm\n" +
                    "- Substitute\n" +
                    "- Hidden Power [Fire]\n" +
                    "- Glare\n");
        }

        String nameString;
        Gender gender = null;
        String itemString = null;
        if (exportArray[0].contains("@")) {
            nameString = exportArray[0].split("@")[0].trim();
            itemString = exportArray[0].split("@")[1].trim();
        } else {
            nameString = exportArray[0].trim();
        }
        if (nameString.contains("(M)") || nameString.contains("(F)")) {
            nameString = nameString.replaceAll("\\(M\\)|\\(F\\)", "");
            Matcher matcher = Pattern.compile("(\\(\\w\\))").matcher(nameString);
            matcher.find();
            gender = Gender.getGender(matcher.group());
        }

        String abilityString = null;
        if (export.contains("Ability:")) {
            Matcher matcher = Pattern.compile("Ability:(.*)").matcher(export);
            if (matcher.find()) {
                abilityString = matcher.group(1).trim();
            }
        }

        String[] effortValuesStringArray = null;
        if (export.contains("EVs:")) {
            Matcher matcher = Pattern.compile("EVs:(.*)").matcher(export);
            if (matcher.find()) {
                effortValuesStringArray = matcher.group(1).split("/");
            }
        }

        String natureString = null;
        if (export.contains("Nature")) {
            Matcher matcher = Pattern.compile("(.*)Nature").matcher(export);
            if (matcher.find()) {
                natureString = matcher.group(1).trim();
            }
        }

        List<String> movesStringArray = new ArrayList<>();
        if (export.contains("- ")) {
            Matcher matcher = Pattern.compile("- (.*)").matcher(export);
            while (matcher.find()) {
                movesStringArray.add(matcher.group().replace("- ", ""));
            }
        }

        if (movesStringArray.size() == 0) {
            throw new DiscordException(nameString + " has no moves.");
        }

        BasePokemon basePokemon = BasePokemon.getBasePokemon(nameString);
        Item item = Item.getItem(itemString);
        Ability ability = Ability.getAbility(abilityString);
        Nature nature = Nature.getNature(natureString);
        List<Move> moves = new ArrayList<>();
        for (String move : movesStringArray) {
            moves.add(new Move(BaseMove.getMove(move)));
        }
        Map<Stat, Integer> individualValues = new HashMap<>();
        Map<Stat, Integer> effortValues = new HashMap<>();
        if (effortValuesStringArray != null) {
            for (String s : effortValuesStringArray) {
                if (s.contains("HP")) {
                    effortValues.put(Stat.HP, Integer.valueOf(s.replace("HP", "").trim()));
                } else if (s.contains("Atk")) {
                    effortValues.put(Stat.ATTACK, Integer.valueOf(s.replace("Atk", "").trim()));
                } else if (s.contains("Def")) {
                    effortValues.put(Stat.DEFENSE, Integer.valueOf(s.replace("Def", "").trim()));
                } else if (s.contains("SpA")) {
                    effortValues.put(Stat.SPECIAL_ATTACK, Integer.valueOf(s.replace("SpA", "").trim()));
                } else if (s.contains("SpD")) {
                    effortValues.put(Stat.SPECIAL_DEFENSE, Integer.valueOf(s.replace("SpD", "").trim()));
                } else if (s.contains("Spe")) {
                    effortValues.put(Stat.SPEED, Integer.valueOf(s.replace("Spe", "").trim()));
                }
            }
        }

        return new Pokemon(basePokemon, item, nature, ability, gender, 100, individualValues, effortValues, moves);
    }

    public static List<Pokemon> parsePokemons(String export) throws DiscordException {
        String[] exportArray = export.split("\n\n");
        List<Pokemon> pokemons = new ArrayList<>();

        for (String s : exportArray) {
            pokemons.add(SmogonImporter.parsePokemon(s));
        }

        return pokemons;
    }

}
