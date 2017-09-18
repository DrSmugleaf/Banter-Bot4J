package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
interface IBattle {

    default void onTrainerTurnStart(@Nonnull Trainer trainer, @Nonnull Pokemon pokemon) {}

    default void onTurnStart(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {}

    default void onOwnSendBack(@Nonnull Pokemon pokemon) {}

    default void onEnemySendBack(@Nonnull Pokemon pokemon) {}

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

    default void onOwnReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onEnemyReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onPokemonFailAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onEnemyFailAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {}

    default void onOwnTurnEnd(@Nonnull Pokemon pokemon) {}

    default void onEnemyTurnEnd(@Nonnull Pokemon pokemon) {}

    default void onTurnEnd(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {}

    default void onOwnItemUsed(@Nonnull Pokemon user, @Nonnull Item item) {}

    default void onEnemyItemUsed(@Nonnull Pokemon user, @Nonnull Item item) {}

    default boolean hits(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {
        return true;
    }

    default double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
        return 1.0;
    }

    default double powerMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
        return 1.0;
    }

}
