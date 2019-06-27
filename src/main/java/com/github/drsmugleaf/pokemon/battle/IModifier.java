package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon.status.Status;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
public interface IModifier {

    default void onTrainerTurnStart(Trainer trainer, Pokemon pokemon) {}

    default void onTurnStart(Action action) {}

    default void onOwnSendBack(Pokemon pokemon) {}

    default void onEnemySendBack(Pokemon user, Pokemon enemy) {}

    default void onOwnSendOut(Pokemon pokemon) {}

    default void onEnemySendOut(Pokemon pokemon) {}

    default void onOwnTurnStart(Pokemon pokemon) {}

    default void onEnemyTurnStart(Pokemon pokemon) {}

    default boolean onOwnAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
        return true;
    }

    default boolean onEnemyAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
        return true;
    }

    default void onOwnDealAttack(Pokemon attacker, Pokemon defender, Action action) {}

    default void onEnemyDealAttack(Pokemon attacker, Pokemon defender, Action action) {}

    default boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
        return true;
    }

    default void onEnemyReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {}

    default void onPokemonFailAttack(Pokemon attacker, Pokemon defender, Action action) {}

    default void onEnemyFailAttack(Pokemon attacker, Pokemon defender, Action action) {}

    default void onOwnFaint(@Nullable Pokemon attacker, Pokemon defender) {}

    default void onOwnTurnEnd(Pokemon pokemon) {}

    default void onEnemyTurnEnd(Pokemon pokemon) {}

    default void onTurnEnd(Battle battle, Pokemon pokemon) {}

    default void onOwnItemUsed(Pokemon user, Items item) {}

    default void onEnemyItemUsed(Pokemon user, Items item) {}

    default boolean hits(Pokemon target, Action action, DamageTags... tags) {
        return true;
    }

    default double ownDamageMultiplier(Pokemon pokemon, Action action) {
        return 1.0;
    }

    default double enemyDamageMultiplier(Pokemon pokemon, Action action) {
        return 1.0;
    }

    default double damageMultiplier(Action action) {
        return 1.0;
    }

    default double damageMultiplier(Weather weather, Pokemon pokemon) {
        return 1.0;
    }

    default double powerMultiplier(Pokemon attacker, Action action) {
        return 1.0;
    }

    default double statMultiplier(Pokemon pokemon, IStat stat) {
        return 1.0;
    }

    default boolean canApplyStatus(Pokemon attacker, Pokemon defender, Status status) {
        return true;
    }

    default boolean canSwitch(Pokemon pokemon) {
        return true;
    }

}
