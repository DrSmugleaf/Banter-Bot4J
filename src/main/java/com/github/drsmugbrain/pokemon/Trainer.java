package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.*;
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
    private Battle battle = null;
    private boolean ready = false;
    private TrainerStatus status = TrainerStatus.NONE;
    private String NAME;

    public Trainer(String name, @Nonnull Long id, @Nonnull Pokemon... pokemons) {
        this.NAME = name;
        this.ID = id;
        for (Pokemon pokemon : pokemons) {
            pokemon.setTrainer(this);
        }
        this.POKEMONS.addAll(Arrays.asList(pokemons));
    }

    @Nonnull
    public Long getID() {
        return this.ID;
    }

    protected void addPokemon(Pokemon pokemon) {
        this.POKEMONS.add(pokemon);
    }

    protected void removePokemon(Pokemon pokemon) {
        this.POKEMONS.remove(pokemon);
        this.ACTIVE_POKEMONS.remove(pokemon);
    }

    protected void removeActivePokemon(Pokemon pokemon) {
        this.ACTIVE_POKEMONS.remove(pokemon);
    }

    protected void replacePokemon(Pokemon oldPokemon, Pokemon newPokemon) {
        if (this.ACTIVE_POKEMONS.contains(oldPokemon)) {
            this.ACTIVE_POKEMONS.add(newPokemon);
        }
        this.removePokemon(oldPokemon);
        this.addPokemon(newPokemon);
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
        this.setStatus(TrainerStatus.WAITING);
        TrainerChooseTargetEvent event = new TrainerChooseTargetEvent(pokemon, move, target);
        EventDispatcher.dispatch(event);
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
        this.sendBack(oldPokemon);
        this.sendOut(newPokemon);
    }

    private void sendBack(Pokemon pokemon) {
        this.ACTIVE_POKEMONS.remove(pokemon);
        TrainerSendBackPokemonEvent event = new TrainerSendBackPokemonEvent(pokemon);
        EventDispatcher.dispatch(event);
    }

    protected void sendOut(Pokemon pokemon) {
        this.ACTIVE_POKEMONS.add(pokemon);
        this.setStatus(TrainerStatus.WAITING);
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

    public boolean hasPokemon(Pokemon pokemon) {
        return this.POKEMONS.contains(pokemon) || this.ACTIVE_POKEMONS.contains(pokemon);
    }

    public boolean hasActivePokemon(Pokemon pokemon) {
        return this.ACTIVE_POKEMONS.contains(pokemon);
    }

    public boolean hasInactivePokemon(Pokemon pokemon) {
        return this.POKEMONS.contains(pokemon);
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
        this.setStatus(TrainerStatus.CHOOSING_TARGET);
        TrainerChooseMoveEvent event = new TrainerChooseMoveEvent(this.pokemonInFocus, move);
        EventDispatcher.dispatch(event);
    }

    public void resetChosenMove() {
        this.chosenMove = null;
    }

    public List<Pokemon> getAlivePokemons() {
        List<Pokemon> alivePokemons = new ArrayList<>();

        for (Pokemon pokemon : this.POKEMONS) {
            if (pokemon.getStat(Stat.HP) > 0) {
                alivePokemons.add(pokemon);
            }
        }

        return alivePokemons;
    }

    public List<Pokemon> getAliveInactivePokemons() {
        List<Pokemon> aliveInactivePokemons = this.getAlivePokemons();

        aliveInactivePokemons.removeIf(this.ACTIVE_POKEMONS::contains);
        aliveInactivePokemons.removeIf(pokemon -> pokemon.getCurrentStat(Stat.HP) <= 0);

        return aliveInactivePokemons;
    }

    public Battle getBattle() {
        return this.battle;
    }

    protected void setBattle(Battle battle) {
        this.battle = battle;
    }

    public boolean isReady() {
        return this.ready;
    }

    protected void setReady(boolean bool) {
        this.ready = bool;
    }

    public TrainerStatus getStatus() {
        return this.status;
    }

    protected void setStatus(TrainerStatus status) {
        this.status = status;
    }

    protected void finishTurn() {
        if (this.ACTIVE_POKEMONS.size() < 1) {
            this.setStatus(TrainerStatus.CHOOSING_POKEMON);
            TrainerChoosingPokemonEvent event = new TrainerChoosingPokemonEvent(this);
            EventDispatcher.dispatch(event);
        } else {
            this.setStatus(TrainerStatus.CHOOSING_MOVE);
        }
    }

    public String getName() {
        return this.NAME;
    }

    public void setName(String name) {
        this.NAME = name;
    }

}
