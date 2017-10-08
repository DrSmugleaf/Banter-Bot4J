package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class PokemonBuilder {

    private Trainer trainer;
    private Species basePokemon;
    private String nickname;
    private Item item;
    private Nature nature;
    private Ability ability;
    private Gender gender;
    private int level;
    private Map<PermanentStat, Integer> individualValues;
    private Map<PermanentStat, Integer> effortValues;
    private List<Move> moves;

    protected PokemonBuilder() {}

    protected Pokemon build() {
        return new Pokemon(
                trainer,
                basePokemon,
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
    public Species getBasePokemon() {
        return basePokemon;
    }

    @Nonnull
    public PokemonBuilder setBasePokemon(@Nonnull Species basePokemon) {
        this.basePokemon = basePokemon;
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
    public Item getItem() {
        return item;
    }

    @Nonnull
    public PokemonBuilder setItem(@Nonnull Item item) {
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
    public Ability getAbility() {
        return ability;
    }

    @Nonnull
    public PokemonBuilder setAbility(@Nonnull Ability ability) {
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
