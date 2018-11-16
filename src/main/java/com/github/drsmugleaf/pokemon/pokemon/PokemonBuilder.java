package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.UserException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    @NotNull
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

    @NotNull
    public PokemonBuilder setTrainer(@NotNull Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    @Nullable
    public Species getSpecies() {
        return species;
    }

    @NotNull
    public PokemonBuilder setSpecies(@NotNull Species species) {
        this.species = species;
        return this;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @NotNull
    public PokemonBuilder setNickname(@NotNull String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Nullable
    public Items getItem() {
        return item;
    }

    @NotNull
    public PokemonBuilder setItem(@NotNull Items item) {
        this.item = item;
        return this;
    }

    @Nullable
    public Nature getNature() {
        return nature;
    }

    @NotNull
    public PokemonBuilder setNature(@NotNull Nature nature) {
        this.nature = nature;
        return this;
    }

    @Nullable
    public Abilities getAbility() {
        return ability;
    }

    @NotNull
    public PokemonBuilder setAbility(@NotNull Abilities ability) {
        this.ability = ability;
        return this;
    }

    @Nullable
    public Gender getGender() {
        return gender;
    }

    @NotNull
    public PokemonBuilder setGender(@NotNull Gender gender) {
        this.gender = gender;
        return this;
    }

    public int getLevel() {
        return level;
    }

    @NotNull
    public PokemonBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getIndividualValues() {
        return individualValues;
    }

    @NotNull
    public PokemonBuilder setIndividualValues(@NotNull Map<PermanentStat, Integer> individualValues) {
        this.individualValues = individualValues;
        return this;
    }

    @Nullable
    public Map<PermanentStat, Integer> getEffortValues() {
        return effortValues;
    }

    @NotNull
    public PokemonBuilder setEffortValues(@NotNull Map<PermanentStat, Integer> effortValues) {
        this.effortValues = effortValues;
        return this;
    }

    @Nullable
    public List<Move> getMoves() {
        return moves;
    }

    @NotNull
    public PokemonBuilder setMoves(@NotNull List<Move> moves) {
        this.moves = moves;
        return this;
    }
}
