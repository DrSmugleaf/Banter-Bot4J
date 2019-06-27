package com.github.drsmugleaf.pokemon.trainer;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.external.SmogonImporter;
import com.github.drsmugleaf.pokemon.pokemon.PokemonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class TrainerBuilder {

    private long id;
    private String name;

    private List<String> pokemonExportStrings = new ArrayList<>();

    private Battle battle;

    public TrainerBuilder() {}

    public Trainer build() throws UserException {
        PokemonBuilder[] pokemons = new PokemonBuilder[pokemonExportStrings.size()];

        for (int i = 0; i < pokemonExportStrings.size(); i++) {
            PokemonBuilder pokemon = SmogonImporter.importPokemon(pokemonExportStrings.get(i));
            pokemons[i] = pokemon;
        }

        return new Trainer(id, name, battle, pokemons);
    }

    public long getID() {
        return id;
    }

    public TrainerBuilder setID(long id) {
        this.id = id;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public TrainerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TrainerBuilder addPokemon(String exportString) {
        pokemonExportStrings.add(exportString);
        return this;
    }

    public TrainerBuilder addPokemons(String exportStrings) {
        pokemonExportStrings.addAll(Arrays.asList(exportStrings.split("\n\n")));
        return this;
    }

    public TrainerBuilder setBattle(Battle battle) {
        this.battle = battle;
        return this;
    }

}
