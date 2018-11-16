package com.github.drsmugleaf.pokemon.trainer;

import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.pokemon.PokemonBuilder;
import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.moves.EntryHazard;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.status.BaseVolatileStatus;
import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer extends Player {

    @NotNull
    public final Battle BATTLE;

    @NotNull
    private final List<Pokemon> POKEMONS = new ArrayList<>();

    @NotNull
    private final List<Pokemon> ACTIVE_POKEMONS = new ArrayList<>();

    @NotNull
    private final List<Action> ACTIONS = new ArrayList<>();

    @Nullable
    private Pokemon pokemonInFocus = null;

    @Nullable
    private Move chosenMove = null;

    private boolean ready = false;

    @NotNull
    private TrainerStatus status = TrainerStatus.NONE;

    @NotNull
    private final List<EntryHazard> HAZARDS = new ArrayList<>();

    protected Trainer(@NotNull Long id, @NotNull String name, @NotNull Battle battle, @NotNull PokemonBuilder... pokemonBuilders) throws UserException {
        super(id, name);

        BATTLE = battle;

        List<Pokemon> pokemons = new ArrayList<>();
        for (PokemonBuilder pokemonBuilder : pokemonBuilders) {
            pokemonBuilder.setTrainer(this);
            pokemons.add(pokemonBuilder.build());
        }

        POKEMONS.addAll(pokemons);
    }

    public void addPokemon(Pokemon pokemon) {
        POKEMONS.add(pokemon);
    }

    public void removePokemon(Pokemon pokemon) {
        POKEMONS.remove(pokemon);
        ACTIVE_POKEMONS.remove(pokemon);
    }

    public void removeActivePokemon(Pokemon pokemon) {
        ACTIVE_POKEMONS.remove(pokemon);
    }

    protected void replacePokemon(Pokemon oldPokemon, Pokemon newPokemon) {
        if (ACTIVE_POKEMONS.contains(oldPokemon)) {
            ACTIVE_POKEMONS.add(newPokemon);
        }

        removePokemon(oldPokemon);
        addPokemon(newPokemon);
    }

    @NotNull
    public Pokemon getPokemon(int id) {
        return POKEMONS.get(id);
    }

    @NotNull
    public Pokemon[] getPokemons() {
        return POKEMONS.toArray(new Pokemon[0]);
    }

    @Nullable
    public Pokemon getNextAliveUnactivePokemon() {
        List<Pokemon> alivePokemons = getAliveInactivePokemons();
        return alivePokemons.get(0);
    }

    @NotNull
    public List<Pokemon> getActivePokemons() {
        return ACTIVE_POKEMONS;
    }

    @Nullable
    public Pokemon getActivePokemon(int index) {
        return ACTIVE_POKEMONS.get(index);
    }

    public int getActivePokemon(Pokemon pokemon) {
        return ACTIVE_POKEMONS.indexOf(pokemon);
    }

    @Nullable
    public Pokemon getRandomActivePokemon() {
        if (ACTIVE_POKEMONS.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(ACTIVE_POKEMONS.size());
        return ACTIVE_POKEMONS.get(randomIndex);
    }

    @NotNull
    public List<Pokemon> getAdjacentEnemyPokemons(Pokemon pokemon) {
        Trainer opposingTrainer = BATTLE.getOppositeTrainer(this);
        List<Pokemon> opposingPokemons = opposingTrainer.getActivePokemons();
        int index = ACTIVE_POKEMONS.indexOf(pokemon);
        List<Pokemon> adjacentEnemyPokemons = new ArrayList<>();

        if (ACTIVE_POKEMONS.isEmpty() || opposingPokemons.isEmpty()) {
            return adjacentEnemyPokemons;
        }

        if (!ACTIVE_POKEMONS.contains(pokemon)) {
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
    public Pokemon getRandomAdjacentEnemyPokemon(Pokemon pokemon) {
        List<Pokemon> adjacentEnemyPokemons = getAdjacentEnemyPokemons(pokemon);
        if (adjacentEnemyPokemons.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(adjacentEnemyPokemons.size());
        return adjacentEnemyPokemons.get(randomIndex);
    }

    public List<Pokemon> getAlivePokemons() {
        List<Pokemon> alivePokemons = new ArrayList<>();

        for (Pokemon pokemon : POKEMONS) {
            if (!pokemon.isFainted()) {
                alivePokemons.add(pokemon);
            }
        }

        return alivePokemons;
    }

    public List<Pokemon> getAliveInactivePokemons() {
        List<Pokemon> aliveInactivePokemons = getAlivePokemons();

        aliveInactivePokemons.removeIf(ACTIVE_POKEMONS::contains);
        aliveInactivePokemons.removeIf(Pokemon::isFainted);

        return aliveInactivePokemons;
    }

    public void addAction(Battle battle, Move move, Pokemon target, Pokemon pokemon) {
        Action action = battle.createAction(move, pokemon, target);
        pokemon.setAction(action);
        ACTIONS.add(action);

        if (ACTIONS.size() == ACTIVE_POKEMONS.size()) {
            setStatus(TrainerStatus.WAITING);
        }

        TrainerChooseTargetEvent event = new TrainerChooseTargetEvent(pokemon, move, target);
        EventDispatcher.dispatch(event);
    }

    public boolean hasAction(Pokemon pokemon) {
        for (Action action : ACTIONS) {
            if (action.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    public List<Action> getActions() {
        return new ArrayList<>(ACTIONS);
    }

    public void resetActions() {
        ACTIONS.clear();
    }

    public void switchPokemon(Pokemon newPokemon, Pokemon oldPokemon) {
        sendBack(oldPokemon);
        sendOut(newPokemon);
    }

    private void sendBack(Pokemon pokemon) {
        ACTIVE_POKEMONS.remove(pokemon);
        TrainerSendBackPokemonEvent event = new TrainerSendBackPokemonEvent(pokemon);
        EventDispatcher.dispatch(event);
    }

    public void sendOut(Pokemon pokemon) {
        ACTIVE_POKEMONS.add(pokemon);
        setStatus(TrainerStatus.WAITING);
    }

    @Nullable
    public Pokemon getOppositePokemon(Pokemon pokemon) {
        int index = ACTIVE_POKEMONS.indexOf(pokemon);
        if (ACTIVE_POKEMONS.size() >= 3) {
            return Lists.reverse(ACTIVE_POKEMONS).get(index);
        }
        return null;
    }

    public void swapActivePokemonPlaces(Pokemon pokemon1, Pokemon pokemon2) {
        Collections.swap(ACTIVE_POKEMONS, ACTIVE_POKEMONS.indexOf(pokemon1), ACTIVE_POKEMONS.indexOf(pokemon2));
    }

    public boolean hasPokemon(Pokemon pokemon) {
        return POKEMONS.contains(pokemon) || ACTIVE_POKEMONS.contains(pokemon);
    }

    public boolean hasActivePokemon(Pokemon pokemon) {
        return ACTIVE_POKEMONS.contains(pokemon);
    }

    public boolean hasInactivePokemon(Pokemon pokemon) {
        return POKEMONS.contains(pokemon);
    }

    @Nullable
    public Pokemon getPokemonInFocus() {
        return pokemonInFocus;
    }

    public void setPokemonInFocus(@NotNull Pokemon newPokemon) {
        pokemonInFocus = newPokemon;
    }

    public void resetPokemonInFocus() {
        pokemonInFocus = null;
    }

    @Nullable
    public Move getChosenMove() {
        return chosenMove;
    }

    public void setChosenMove(@NotNull Move move) {
        chosenMove = move;
        setStatus(TrainerStatus.CHOOSING_TARGET);
        TrainerChooseMoveEvent event = new TrainerChooseMoveEvent(pokemonInFocus, move);
        EventDispatcher.dispatch(event);
    }

    public void resetChosenMove() {
        chosenMove = null;
    }

    @NotNull
    public Battle getBattle() {
        return BATTLE;
    }

    public boolean isReady() {
        return ready;
    }

    protected void setReady(boolean bool) {
        ready = bool;
    }

    @NotNull
    public TrainerStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull TrainerStatus status) {
        this.status = status;
    }

    public void finishTurn() {
        if (ACTIVE_POKEMONS.size() < 1) {
            setStatus(TrainerStatus.CHOOSING_POKEMON);
            TrainerChoosingPokemonEvent event = new TrainerChoosingPokemonEvent(this);
            EventDispatcher.dispatch(event);
        } else {
            setStatus(TrainerStatus.CHOOSING_MOVE);
        }
    }

    public boolean hasOpponentOnField() {
        for (Trainer trainer : BATTLE.getTrainers().values()) {
            if (trainer == this) {
                continue;
            }

            if (!trainer.getActivePokemons().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public void removeVolatileStatus(@NotNull BaseVolatileStatus... volatileStatuses) {
        for (Pokemon pokemon : ACTIVE_POKEMONS) {
            pokemon.STATUSES.removeVolatileStatus(volatileStatuses);
        }
    }

    protected void addEntryHazard(@NotNull EntryHazard hazard) {
        HAZARDS.add(hazard);
    }

    @NotNull
    public List<EntryHazard> getHazards() {
        return new ArrayList<>(HAZARDS);
    }

    public void removeEntryHazard(@NotNull EntryHazard... hazards) {
        HAZARDS.removeAll(Arrays.asList(hazards));
    }

}
