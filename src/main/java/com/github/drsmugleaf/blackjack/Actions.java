package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum Actions {

    DOUBLE_DOWN {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
            // TODO: Double bet
            game.DECK.deal(hand, 1);
        }
    },
    HIT {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
            game.DECK.deal(hand, 1);
        }
    },
    NONE {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.getStatus() == Status.PLAYING && hand.getAction() != STAND && hand.getAction() != SURRENDER;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
            throw new IllegalStateException("Action none executed");
        }
    },
    SPLIT {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.getStatus() == Status.PLAYING && hand.size() == 2 && hand.get(0).getValue(hand) == hand.get(1).getValue(hand);
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
            // TODO: Copy bet
            Hand splitHand = new Hand();
            Card splitCard = hand.remove(1);
            splitHand.add(splitCard);
            splitHand.setStatus(Status.PLAYING);
            player.addHand(splitHand);
        }
    },
    STAND {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.getStatus() == Status.PLAYING;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {}
    },
    SURRENDER {
        @Override
        public boolean isValidFor(@Nonnull Hand hand) {
            return hand.size() == 2;
        }

        @Override
        void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
            // TODO: Halve bet
            hand.setStatus(Status.SURRENDER);
            Event event = new SurrenderEvent(game, player, hand);
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

    public abstract boolean isValidFor(@Nonnull Hand hand);

    abstract void execute(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand);

}
