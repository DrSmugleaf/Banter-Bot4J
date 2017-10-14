package com.github.drsmugbrain.pokemon.trainer;

import com.github.drsmugbrain.pokemon.Battle;
import com.github.drsmugbrain.pokemon.PokemonBuilder;
import com.github.drsmugbrain.pokemon.SmogonImporter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class TrainerBuilder {

    private long id;
    private String name;
    private List<String> pokemonStrings = new ArrayList<>();
    private Battle battle;

    public TrainerBuilder() {}

    public Trainer build() throws UserException {
        PokemonBuilder[] pokemons = new PokemonBuilder[pokemonStrings.size()];

        for (int i = 0; i < pokemonStrings.size(); i++) {
            PokemonBuilder pokemon = SmogonImporter.importPokemon(pokemonStrings.get(i));
            pokemons[i] = pokemon;
        }

        return new Trainer(id, name, battle, pokemons);
    }

    public long getID() {
        return id;
    }

    @Nonnull
    public TrainerBuilder setID(long id) {
        this.id = id;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nonnull
    public TrainerBuilder setName(@Nonnull String name) {
        this.name = name;
        return this;
    }

    @Nonnull
    public TrainerBuilder addPokemon(@Nonnull String exportString) {
        pokemonStrings.add(exportString);
        return this;
    }

    @Nonnull
    public TrainerBuilder addPokemons(@Nonnull String exportStrings) {
        pokemonStrings.addAll(Arrays.asList(exportStrings.split("\n\n")));
        return this;
    }

    public TrainerBuilder setBattle(@Nonnull Battle battle) {
        this.battle = battle;
        return this;
    }

}
