package com.github.drsmugbrain.pokemon;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public class SmogonImporter {

    public static Pokemon parsePokemon(String export) {
        export = export.trim();
        String[] exportArray = export.split("\n");

        String[] nameItemArray = exportArray[0].split("@");
        String nameString = nameItemArray[0].trim();
        String itemString = nameItemArray[1].trim();

        String abilityString = exportArray[1].replace("Ability: ", "");

        String[] effortValuesStringArray = exportArray[2].replace("EVs: ", "").split(" / ");

        String natureString = exportArray[3].replace(" Nature", "");

        String[] movesString = new String[]{
                exportArray[4].replace("- ", ""),
                exportArray[5].replace("- ", ""),
                exportArray[6].replace("- ", ""),
                exportArray[7].replace("- ", "")
        };

        BasePokemon basePokemon = BasePokemon.getBasePokemon(nameString);
        Nature nature = Nature.getNature(natureString);
        Ability ability = Ability.getAbility(abilityString);
        Set<Move> moves = new LinkedHashSet<>();
        for (String move : movesString) {
            moves.add(new Move(Move.getBaseMove(move)));
        }

        return new Pokemon(basePokemon, ability, moves, 100);
    }

}
