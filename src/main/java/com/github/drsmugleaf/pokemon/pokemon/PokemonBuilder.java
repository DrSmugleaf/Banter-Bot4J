package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Map;

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
