package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.*;
import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer extends Player {

    private final List<Pokemon> POKEMONS = new ArrayList<>();
    private final List<Pokemon> ACTIVE_POKEMONS = new ArrayList<>();
    private final List<Action> ACTIONS = new ArrayList<>();
    private Pokemon pokemonInFocus = null;
    private Move chosenMove = null;
    private Battle battle = null;
    private boolean ready = false;
    private TrainerStatus status = TrainerStatus.NONE;

    public Trainer(@Nonnull Long id, @Nonnull String name, @Nonnull Pokemon... pokemons) {
        super(id, name);
        this.POKEMONS.addAll(Arrays.asList(pokemons));
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

    @Nullable
    public Pokemon getNextAliveUnactivePokemon() {
        List<Pokemon> alivePokemons = this.getAliveInactivePokemons();
        return alivePokemons.get(0);
    }

    @Nonnull
    public List<Pokemon> getActivePokemons() {
        return this.ACTIVE_POKEMONS;
    }

    @Nullable
    public Pokemon getActivePokemon(int index) {
        return this.ACTIVE_POKEMONS.get(index);
    }

    public int getActivePokemon(Pokemon pokemon) {
        return this.ACTIVE_POKEMONS.indexOf(pokemon);
    }

    @Nullable
    protected Pokemon getRandomActivePokemon() {
        if (this.ACTIVE_POKEMONS.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(this.ACTIVE_POKEMONS.size());
        return this.ACTIVE_POKEMONS.get(randomIndex);
    }

    @Nonnull
    protected List<Pokemon> getAdjacentEnemyPokemons(Pokemon pokemon) {
        Trainer opposingTrainer = this.battle.getOppositeTrainer(this);
        List<Pokemon> opposingPokemons = opposingTrainer.getActivePokemons();
        int index = this.ACTIVE_POKEMONS.indexOf(pokemon);
        List<Pokemon> adjacentEnemyPokemons = new ArrayList<>();

        if (this.ACTIVE_POKEMONS.isEmpty() || opposingPokemons.isEmpty()) {
            return adjacentEnemyPokemons;
        }

        if (!this.ACTIVE_POKEMONS.contains(pokemon)) {
            return adjacentEnemyPokemons;
        }

        for (int i = index - 1; i <= index + 1; i++) {
            if (index >= 0 && index < opposingPokemons.size()) {
                adjacentEnemyPokemons.add(opposingPokemons.get(i));
            }
        }

        return adjacentEnemyPokemons;
    }

    @Nullable
    protected Pokemon getRandomAdjacentEnemyPokemon(Pokemon pokemon) {
        List<Pokemon> adjacentEnemyPokemons = this.getAdjacentEnemyPokemons(pokemon);
        if (adjacentEnemyPokemons.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(adjacentEnemyPokemons.size());
        return adjacentEnemyPokemons.get(randomIndex);
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

    public void addAction(Pokemon pokemon, Move move, Pokemon target) {
        Action action = new Action(move, pokemon, target, battle.getTurn());
        pokemon.setAction(action);
        this.ACTIONS.add(action);

        if (this.ACTIONS.size() == this.ACTIVE_POKEMONS.size()) {
            this.setStatus(TrainerStatus.WAITING);
        }

        TrainerChooseTargetEvent event = new TrainerChooseTargetEvent(pokemon, move, target);
        EventDispatcher.dispatch(event);
    }

    public boolean hasAction(Pokemon pokemon) {
        for (Action action : this.ACTIONS) {
            if (action.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

    @Nonnull
    protected List<Action> getActions() {
        return this.ACTIONS;
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

    protected boolean hasOpponentOnField() {
        for (Trainer trainer : this.battle.getTrainers().values()) {
            if (trainer == this) {
                continue;
            }

            if (!trainer.getActivePokemons().isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
