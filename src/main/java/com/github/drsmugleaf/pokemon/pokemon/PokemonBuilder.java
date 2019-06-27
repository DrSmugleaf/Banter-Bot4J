package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.UserException;

import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class PokemonBuilder {

    private Trainer trainer;
    private Species species;
    private String nickname;
    private Items item;
    private Nature nature;
    private Abilities ability;
    private Gender gender;
    private int level;
    private Map<PermanentStat, Integer> individualValues;
    private Map<PermanentStat, Integer> effortValues;
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

    @Nullable
    public Trainer getTrainer() {
        return trainer;
    }

    public PokemonBuilder setTrainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    @Nullable
    public Species getSpecies() {
        return species;
    }

    public PokemonBuilder setSpecies(Species species) {
        this.species = species;
        return this;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    public PokemonBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Nullable
    public Items getItem() {
        return item;
    }

    public PokemonBuilder setItem(Items item) {
        this.item = item;
        return this;
    }

    @Nullable
    public Nature getNature() {
        return nature;
    }

    public PokemonBuilder setNature(Nature nature) {
        this.nature = nature;
        return this;
    }

    @Nullable
    public Abilities getAbility() {
        return ability;
    }

    public PokemonBuilder setAbility(Abilities ability) {
        this.ability = ability;
        return this;
    }

    @Nullable
    public Gender getGender() {
        return gender;
    }

    public PokemonBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public PokemonBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getIndividualValues() {
        return individualValues;
    }

    public PokemonBuilder setIndividualValues(Map<PermanentStat, Integer> individualValues) {
        this.individualValues = individualValues;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getEffortValues() {
        return effortValues;
    }

    public PokemonBuilder setEffortValues(Map<PermanentStat, Integer> effortValues) {
        this.effortValues = effortValues;
        return this;
    }

    @Nullable
    public List<Move> getMoves() {
        return moves;
    }

    public PokemonBuilder setMoves(List<Move> moves) {
        this.moves = moves;
        return this;
    }
}
