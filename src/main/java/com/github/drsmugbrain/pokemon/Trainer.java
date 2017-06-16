package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
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

}
