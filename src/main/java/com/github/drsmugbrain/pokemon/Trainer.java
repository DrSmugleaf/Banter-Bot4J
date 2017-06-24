package com.github.drsmugbrain.pokemon;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer {

    private final Long ID;
    private final List<Pokemon> POKEMONS = new ArrayList<>();
    private final List<Pokemon> ACTIVE_POKEMONS = new ArrayList<>();
    private final Map<Pokemon, Move> ACTIONS = new LinkedHashMap<>();
    private Pokemon pokemonInFocus = null;
    private Move chosenMove = null;

    public Trainer(@Nonnull Long name, @Nonnull Pokemon... pokemons) {
        this.ID = name;
        this.POKEMONS.addAll(Arrays.asList(pokemons));
    }

    @Nonnull
    public Long getID() {
        return this.ID;
    }

    @Nonnull
    public Pokemon getPokemon(int id) {
        return this.POKEMONS.get(id);
    }

    @Nonnull
    public Pokemon[] getPokemons() {
        return this.POKEMONS.toArray(new Pokemon[0]);
    }

    @Nonnull
    public List<Pokemon> getActivePokemons() {
        return this.ACTIVE_POKEMONS;
    }

    public void addAction(Pokemon pokemon, Move move, Pokemon target) {
        pokemon.setAction(move, target);
        this.ACTIONS.put(pokemon, move);
    }

    public void addAction(Pokemon pokemon, String move, Pokemon target) {
        this.addAction(pokemon, pokemon.getMove(move), target);
    }

    @Nonnull
    public Move getAction(Pokemon pokemon) {
        return this.ACTIONS.get(pokemon);
    }

    public boolean hasAction(Pokemon pokemon) {
        return this.ACTIONS.containsKey(pokemon);
    }

    @Nonnull
    protected Map<Pokemon, Move> getActions() {
        return this.ACTIONS;
    }

    public void removeAction(Pokemon pokemon, BaseMove move) {
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

    public void swapActivePokemonPlaces(Pokemon pokemon1, Pokemon pokemon2) {
        Collections.swap(this.ACTIVE_POKEMONS, this.ACTIVE_POKEMONS.indexOf(pokemon1), this.ACTIVE_POKEMONS.indexOf(pokemon2));
    }

    public boolean hasActivePokemon(Pokemon pokemon) {
        return this.ACTIVE_POKEMONS.contains(pokemon);
    }

    @Nullable
    public Pokemon getPokemonInFocus() {
        return this.pokemonInFocus;
    }

    public void setPokemonInFocus(@Nonnull Pokemon pokemon) {
        this.pokemonInFocus = pokemon;
    }

    public void resetPokemonInFocus() {
        this.pokemonInFocus = null;
    }

    @Nullable
    public Move getChosenMove() {
        return this.chosenMove;
    }

    public void setChosenMove(Move move) {
        this.chosenMove = move;
    }

    public void resetChosenMove() {
        this.chosenMove = null;
    }

}
