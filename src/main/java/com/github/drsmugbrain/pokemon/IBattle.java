package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
interface IBattle {

    default void onTrainerTurnStart(@Nonnull Trainer trainer) {}

    default void onTurnStart(@Nonnull Battle battle) {}

    default void onOwnSwitch(@Nonnull Pokemon user, @Nonnull Pokemon target) {}

    default void afterOwnSwitch(@Nonnull Pokemon user, @Nonnull Pokemon target) {}

    default void onEnemySwitch(@Nonnull Pokemon user, @Nonnull Pokemon target) {}

    default void afterEnemySwitch(@Nonnull Pokemon user, @Nonnull Pokemon target) {}

    default void onPokemonTurnStart(@Nonnull Pokemon pokemon) {}

    default void onPokemonAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {}

    default void onPokemonDealAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {}

    default void onPokemonReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {}

    default void onPokemonFailAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {}

    default void onPokemonTurnEnd(@Nonnull Pokemon pokemon) {}

    default void onTurnEnd(@Nonnull Battle battle) {}

    default boolean hits(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Move move) {
        return true;
    }

    default double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Move move) {
        return 1.0;
    }

    default double powerMultiplier(@Nonnull Pokemon attacker, @Nonnull Move move) {
        return 1.0;
    }

    default void onItemUsed(@Nonnull Pokemon pokemon, @Nonnull Item item) {}

}
