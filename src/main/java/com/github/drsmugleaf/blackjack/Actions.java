package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum Actions {

    DOUBLE_DOWN {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {
            // TODO: Double bet
            game.DECK.deal(player, 1);
        }
    },
    HIT {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {
            game.DECK.deal(player, 1);
        }
    },
    NONE {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() != Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {
            throw new IllegalStateException("Action none executed");
        }
    },
    SPLIT {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            Hand hand = player.HAND;
            return hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue();
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {
            // TODO: Split hand
        }
    },
    STAND {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {}
    },
    SURRENDER {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.HAND.size() == 2;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player) {
            // TODO: Halve bet
            player.setStatus(Status.SURRENDER);
            Event event = new SurrenderEvent(game, player);
            EventDispatcher.dispatch(event);
        }
    };

    @Nullable
    public static Actions getAction(@Nonnull String name) {
        for (Actions action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action;
            }
        }

        return null;
    }

    public abstract boolean isValidFor(@Nonnull Player player);

    abstract void execute(@Nonnull Game game, @Nonnull Player player);

}
