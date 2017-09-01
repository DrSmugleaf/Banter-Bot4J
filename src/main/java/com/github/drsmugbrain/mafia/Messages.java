package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public enum Messages {

    CORONER("Coroner") {
        @Nonnull
        @Override
        public String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2) {
            return String.format("%s's role was %s", target1.getName(), target1.getRole());
        }
    },
    DETECTIVE("Detective") {
        @Nonnull
        @Override
        public String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2) {
            if (target1.getTarget() == null) {
                return String.format("%s didn't visit anyone today", target1.getName());
            }

            return String.format("%s visited %s today", target1.getName(), target1.getTarget().getName());
        }
    };

    private final String NAME;

    Messages(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public abstract String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2);

}
