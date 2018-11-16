package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon.status.Status;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
public interface IModifier {

    default void onTrainerTurnStart(@NotNull Trainer trainer, @NotNull Pokemon pokemon) {}

    default void onTurnStart(@NotNull Action action) {}

    default void onOwnSendBack(@NotNull Pokemon pokemon) {}

    default void onEnemySendBack(@NotNull Pokemon user, @NotNull Pokemon enemy) {}

    default void onOwnSendOut(@NotNull Pokemon pokemon) {}

    default void onEnemySendOut(@NotNull Pokemon pokemon) {}

    default void onOwnTurnStart(@NotNull Pokemon pokemon) {}

    default void onEnemyTurnStart(@NotNull Pokemon pokemon) {}

    default boolean onOwnAttemptAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        return true;
    }

    default boolean onEnemyAttemptAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        return true;
    }

    default void onOwnDealAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {}

    default void onEnemyDealAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {}

    default boolean onOwnReceiveAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        return true;
    }

    default void onEnemyReceiveAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {}

    default void onPokemonFailAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {}

    default void onEnemyFailAttack(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {}

    default void onOwnFaint(@Nullable Pokemon attacker, @NotNull Pokemon defender) {}

    default void onOwnTurnEnd(@NotNull Pokemon pokemon) {}

    default void onEnemyTurnEnd(@NotNull Pokemon pokemon) {}

    default void onTurnEnd(@NotNull Battle battle, @NotNull Pokemon pokemon) {}

    default void onOwnItemUsed(@NotNull Pokemon user, @NotNull Items item) {}

    default void onEnemyItemUsed(@NotNull Pokemon user, @NotNull Items item) {}

    default boolean hits(@NotNull Pokemon target, @NotNull Action action, DamageTags... tags) {
        return true;
    }

    default double ownDamageMultiplier(@NotNull Pokemon pokemon, @NotNull Action action) {
        return 1.0;
    }

    default double enemyDamageMultiplier(@NotNull Pokemon pokemon, @NotNull Action action) {
        return 1.0;
    }

    default double damageMultiplier(@NotNull Action action) {
        return 1.0;
    }

    default double damageMultiplier(@NotNull Weather weather, @NotNull Pokemon pokemon) {
        return 1.0;
    }

    default double powerMultiplier(@NotNull Pokemon attacker, @NotNull Action action) {
        return 1.0;
    }

    default double statMultiplier(@NotNull Pokemon pokemon, @NotNull IStat stat) {
        return 1.0;
    }

    default boolean canApplyStatus(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Status status) {
        return true;
    }

    default boolean canSwitch(@NotNull Pokemon pokemon) {
        return true;
    }

}
