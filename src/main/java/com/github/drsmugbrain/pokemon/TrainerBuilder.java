package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.DiscordException;

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

    public TrainerBuilder() {}

    public Trainer build() throws DiscordException {
        PokemonBuilder[] pokemons = new PokemonBuilder[pokemonStrings.size()];

        for (int i = 0; i < pokemonStrings.size(); i++) {
            PokemonBuilder pokemon = SmogonImporter.parsePokemon(pokemonStrings.get(i));
            pokemons[i] = pokemon;
        }

        return new Trainer(id, name, pokemons);
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
        this.pokemonStrings.add(exportString);
        return this;
    }

    @Nonnull
    public TrainerBuilder addPokemons(@Nonnull String exportStrings) {
        this.pokemonStrings.addAll(Arrays.asList(exportStrings.split("\n\n")));
        return this;
    }

}
