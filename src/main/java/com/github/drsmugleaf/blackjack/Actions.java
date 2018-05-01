package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum Actions {

    HIT {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }
    },
    NONE {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() != Status.PLAYING;
        }
    },
    STAND {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }
    },
    DOUBLE_DOWN {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.getStatus() == Status.PLAYING;
        }
    },
    SPLIT {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            Hand hand = player.HAND;
            return hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue();
        }
    },
    SURRENDER {
        @Override
        public boolean isValidFor(@Nonnull Player player) {
            return player.HAND.size() == 2;
        }
    };

    public abstract boolean isValidFor(@Nonnull Player player);

}
