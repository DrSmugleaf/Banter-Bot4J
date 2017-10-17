package com.github.drsmugbrain.pokemon.pokemon;

import com.github.drsmugbrain.pokemon.item.Items;
import com.github.drsmugbrain.pokemon.Nature;
import com.github.drsmugbrain.pokemon.ability.Abilities;
import com.github.drsmugbrain.pokemon.moves.Move;
import com.github.drsmugbrain.pokemon.stats.PermanentStat;
import com.github.drsmugbrain.pokemon.trainer.Trainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public Pokemon build() {
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

    @Nonnull
    public PokemonBuilder setTrainer(@Nonnull Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    @Nullable
    public Species getSpecies() {
        return species;
    }

    @Nonnull
    public PokemonBuilder setSpecies(@Nonnull Species species) {
        this.species = species;
        return this;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nonnull
    public PokemonBuilder setNickname(@Nonnull String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Nullable
    public Items getItem() {
        return item;
    }

    @Nonnull
    public PokemonBuilder setItem(@Nonnull Items item) {
        this.item = item;
        return this;
    }

    @Nullable
    public Nature getNature() {
        return nature;
    }

    @Nonnull
    public PokemonBuilder setNature(@Nonnull Nature nature) {
        this.nature = nature;
        return this;
    }

    @Nullable
    public Abilities getAbility() {
        return ability;
    }

    @Nonnull
    public PokemonBuilder setAbility(@Nonnull Abilities ability) {
        this.ability = ability;
        return this;
    }

    @Nullable
    public Gender getGender() {
        return gender;
    }

    @Nonnull
    public PokemonBuilder setGender(@Nonnull Gender gender) {
        this.gender = gender;
        return this;
    }

    public int getLevel() {
        return level;
    }

    @Nonnull
    public PokemonBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getIndividualValues() {
        return individualValues;
    }

    @Nonnull
    public PokemonBuilder setIndividualValues(@Nonnull Map<PermanentStat, Integer> individualValues) {
        this.individualValues = individualValues;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getEffortValues() {
        return effortValues;
    }

    @Nonnull
    public PokemonBuilder setEffortValues(@Nonnull Map<PermanentStat, Integer> effortValues) {
        this.effortValues = effortValues;
        return this;
    }

    @Nullable
    public List<Move> getMoves() {
        return moves;
    }

    @Nonnull
    public PokemonBuilder setMoves(@Nonnull List<Move> moves) {
        this.moves = moves;
        return this;
    }
}
