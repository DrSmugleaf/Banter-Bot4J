package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon.status.Status;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
public interface IModifier {

    default void onTrainerTurnStart(@Nonnull Trainer trainer, @Nonnull Pokemon pokemon) {}

    default void onTurnStart(@Nonnull Action action) {}

    default void onOwnSendBack(@Nonnull Pokemon pokemon) {}

    default void onEnemySendBack(@Nonnull Pokemon user, @Nonnull Pokemon enemy) {}

    default void onOwnSendOut(@Nonnull Pokemon pokemon) {}

    default void onEnemySendOut(@Nonnull Pokemon pokemon) {}

    default void onOwnTurnStart(@Nonnull Pokemon pokemon) {}

    default void onEnemyTurnStart(@Nonnull Pokemon pokemon) {}

    default boolean onOwnAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        return true;
    }

    default boolean onEnemyAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        return true;
    }

    default void onOwnDealAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onEnemyDealAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default boolean onOwnReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        return true;
    }

    default void onEnemyReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onPokemonFailAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onEnemyFailAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onOwnFaint(@Nullable Pokemon attacker, @Nonnull Pokemon defender) {}

    default void onOwnTurnEnd(@Nonnull Pokemon pokemon) {}

    default void onEnemyTurnEnd(@Nonnull Pokemon pokemon) {}

    default void onTurnEnd(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {}

    default void onOwnItemUsed(@Nonnull Pokemon user, @Nonnull Items item) {}

    default void onEnemyItemUsed(@Nonnull Pokemon user, @Nonnull Items item) {}

    default boolean hits(@Nonnull Pokemon target, @Nonnull Action action, DamageTags... tags) {
        return true;
    }

    default double ownDamageMultiplier(@Nonnull Pokemon pokemon, @Nonnull Action action) {
        return 1.0;
    }

    default double enemyDamageMultiplier(@Nonnull Pokemon pokemon, @Nonnull Action action) {
        return 1.0;
    }

    default double damageMultiplier(@Nonnull Action action) {
        return 1.0;
    }

    default double damageMultiplier(@Nonnull Weather weather, @Nonnull Pokemon pokemon) {
        return 1.0;
    }

    default double powerMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
        return 1.0;
    }

    default double statMultiplier(@Nonnull Pokemon pokemon, @Nonnull IStat stat) {
        return 1.0;
    }

    default boolean canApplyStatus(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Status status) {
        return true;
    }

    default boolean canSwitch(@Nonnull Pokemon pokemon) {
        return true;
    }

}
