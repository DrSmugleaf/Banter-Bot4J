package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class PokemonBuilder {

    @Nullable
    private Trainer trainer;
    @Nullable
    private Species species;
    @Nullable
    private String nickname;
    @Nullable
    private Items item;
    @Nullable
    private Nature nature;
    @Nullable
    private Abilities ability;
    @Nullable
    private Gender gender;
    @Nullable
    private Integer level;
    @Nullable
    private Map<PermanentStat, Integer> individualValues;
    @Nullable
    private Map<PermanentStat, Integer> effortValues;
    @Nullable
    private List<Move> moves;

    public PokemonBuilder() {}

    public static PokemonBuilder fromSmogon(String export) throws UserException {
        export = export.trim();
        String[] exportArray = export.split("\n");

        if (export.length() == 0) {
            throw new UserException("Add a Pokemon in Smogon format to the command to start. Example:\n" +
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
        Gender gender;
        String itemString = null;
        if (exportArray[0].contains("@")) {
            nameString = exportArray[0].split("@")[0].trim();
            itemString = exportArray[0].split("@")[1].trim();

            Matcher matcher = Pattern.compile(".*\\((.*)\\)").matcher(nameString);
            if (matcher.find()) {
                nameString = matcher.group(1);
            }
        } else {
            nameString = exportArray[0].trim();
        }

        Species pokemon = Species.getPokemon(nameString);
        if (nameString.contains("(M)") || nameString.contains("(F)")) {
            nameString = nameString.replaceAll("\\(M\\)|\\(F\\)", "");
            Matcher matcher = Pattern.compile("(\\(\\w\\))").matcher(nameString);
            matcher.find();
            gender = Gender.getGender(matcher.group());
        } else {
            // Also works for genderless Pokemon
            gender = Gender.getRandomGender(pokemon);
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
            throw new UserException(nameString + " has no moves.");
        }

        Items item = Items.getItem(itemString);
        Abilities ability = Abilities.getAbility(abilityString);
        Nature nature = Nature.getNature(natureString);

        List<Move> moves = new ArrayList<>();
        for (String move : movesStringArray) {
            moves.add(new Move(BaseMove.getMove(move)));
        }

        Map<PermanentStat, Integer> individualValues = new HashMap<>();
        Map<PermanentStat, Integer> effortValues = new HashMap<>();
        if (effortValuesStringArray != null) {
            for (String s : effortValuesStringArray) {
                if (s.contains("HP")) {
                    effortValues.put(PermanentStat.HP, Integer.valueOf(s.replace("HP", "").trim()));
                } else if (s.contains("Atk")) {
                    effortValues.put(PermanentStat.ATTACK, Integer.valueOf(s.replace("Atk", "").trim()));
                } else if (s.contains("Def")) {
                    effortValues.put(PermanentStat.DEFENSE, Integer.valueOf(s.replace("Def", "").trim()));
                } else if (s.contains("SpA")) {
                    effortValues.put(PermanentStat.SPECIAL_ATTACK, Integer.valueOf(s.replace("SpA", "").trim()));
                } else if (s.contains("SpD")) {
                    effortValues.put(PermanentStat.SPECIAL_DEFENSE, Integer.valueOf(s.replace("SpD", "").trim()));
                } else if (s.contains("Spe")) {
                    effortValues.put(PermanentStat.SPEED, Integer.valueOf(s.replace("Spe", "").trim()));
                }
            }
        }

        PokemonBuilder pokemonBuilder = new PokemonBuilder();
        pokemonBuilder
                .setSpecies(pokemon)
                .setNickname(nameString)
                .setItem(item)
                .setNature(nature)
                .setAbility(ability)
                .setGender(gender)
                .setLevel(100)
                .setIndividualValues(individualValues)
                .setEffortValues(effortValues)
                .setMoves(moves);

        return pokemonBuilder;
    }

    public Pokemon build() throws UserException {
        return new Pokemon(
                trainer,
                species,
                nickname,
                ability,
                item,
                nature,
                gender,
                level,
                individualValues,
                effortValues,
                moves
        );
    }

    @Contract(pure = true)
    @Nullable
    public Trainer getTrainer() {
        return trainer;
    }

    @Contract("_ -> this")
    public PokemonBuilder setTrainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Species getSpecies() {
        return species;
    }

    @Contract("_ -> this")
    public PokemonBuilder setSpecies(Species species) {
        this.species = species;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Contract("_ -> this")
    public PokemonBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Items getItem() {
        return item;
    }

    @Contract("_ -> this")
    public PokemonBuilder setItem(Items item) {
        this.item = item;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Nature getNature() {
        return nature;
    }

    @Contract("_ -> this")
    public PokemonBuilder setNature(Nature nature) {
        this.nature = nature;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Abilities getAbility() {
        return ability;
    }

    @Contract("_ -> this")
    public PokemonBuilder setAbility(Abilities ability) {
        this.ability = ability;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Gender getGender() {
        return gender;
    }

    @Contract("_ -> this")
    public PokemonBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Integer getLevel() {
        return level;
    }

    @Contract("_ -> this")
    public PokemonBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Map<PermanentStat, Integer> getIndividualValues() {
        return individualValues;
    }

    @Contract("_ -> this")
    public PokemonBuilder setIndividualValues(Map<PermanentStat, Integer> individualValues) {
        this.individualValues = individualValues;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public Map<PermanentStat, Integer> getEffortValues() {
        return effortValues;
    }

    @Contract("_ -> this")
    public PokemonBuilder setEffortValues(Map<PermanentStat, Integer> effortValues) {
        this.effortValues = effortValues;
        return this;
    }

    @Contract(pure = true)
    @Nullable
    public List<Move> getMoves() {
        return moves;
    }

    @Contract("_ -> this")
    public PokemonBuilder setMoves(List<Move> moves) {
        this.moves = moves;
        return this;
    }

}
