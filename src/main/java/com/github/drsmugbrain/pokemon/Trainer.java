package com.github.drsmugbrain.pokemon;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer {

    private final String NAME;
    private final List<Pokemon> POKEMONS = new ArrayList<>();
    private final List<Pokemon> ACTIVE_POKEMONS = new ArrayList<>();
    private final Map<Pokemon, Move> ACTIONS = new LinkedHashMap<>();

    public Trainer(@Nonnull String name, @Nonnull Pokemon... pokemons) {
        this.NAME = name;
        this.POKEMONS.addAll(Arrays.asList(pokemons));
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Pokemon[] getPokemons() {
        return this.POKEMONS.toArray(new Pokemon[0]);
    }

    @Nonnull
    public Pokemon[] getActivePokemons() {
        return this.ACTIVE_POKEMONS.toArray(new Pokemon[0]);
    }

    public void addAction(Pokemon pokemon, Move move, Pokemon target) {
        pokemon.setAction(move, target);
        this.ACTIONS.put(pokemon, move);
    }

    @Nonnull
    public Move getAction(Pokemon pokemon) {
        return this.ACTIONS.get(pokemon);
    }

    @Nonnull
    protected Map<Pokemon, Move> getActions() {
        return this.ACTIONS;
    }

    public void removeAction(Pokemon pokemon, Move move) {
        this.ACTIONS.remove(pokemon, move);
    }

    public void resetActions() {
        this.ACTIONS.clear();
    }

    public void switchPokemon(Pokemon newPokemon, Pokemon oldPokemon) {
        this.ACTIVE_POKEMONS.remove(oldPokemon);
        this.ACTIVE_POKEMONS.add(newPokemon);
    }

    public void sendOut(Pokemon pokemon) {
        this.ACTIVE_POKEMONS.add(pokemon);
    }

    @Nullable
    public Pokemon getOppositePokemon(Pokemon pokemon) {
        int index = this.ACTIVE_POKEMONS.indexOf(pokemon);
        if (this.ACTIVE_POKEMONS.size() >= 3) {
            return Lists.reverse(this.ACTIVE_POKEMONS).get(index);
        }
        return null;
    }

    public void swapActivePokemon(Pokemon pokemon1, Pokemon pokemon2) {
        Collections.swap(this.ACTIVE_POKEMONS, this.ACTIVE_POKEMONS.indexOf(pokemon1), this.ACTIVE_POKEMONS.indexOf(pokemon2));
    }

}
