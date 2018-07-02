package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Game {

    @Nonnull
    private final Dealer DEALER = new Dealer();

    @Nonnull
    final Deck DECK;

    @Nonnull
    private final Map<Long, Player> PLAYERS = new LinkedHashMap<>();

    Game(@Nonnull Deck deck, @Nonnull Set<Long> players) {
        DECK = deck;
        for (Long id : players) {
            PLAYERS.put(id, new Player(id));
        }

        if (!PLAYERS.isEmpty()) {
            start();
        }
    }

    public void addPlayer(@Nonnull Long id) {
        PLAYERS.put(id, new Player(id));
        if (PLAYERS.size() == 1) {
            start();
        }
    }

    @Nonnull
    public Dealer getDealer() {
        return DEALER;
    }

    @Nonnull
    public Player getPlayer(@Nonnull Long id) {
        return PLAYERS.get(id);
    }

    @Nonnull
    public Map<Long, Player> getPlayers() {
        return new LinkedHashMap<>(PLAYERS);
    }

    public boolean hasPlayer(@Nonnull Long id) {
        return PLAYERS.containsKey(id);
    }

    public void removePlayer(@Nonnull Long id) {
        PLAYERS.remove(id);

        if (PLAYERS.size() == 0) {
            Event event = new EndEvent(this);
            EventDispatcher.dispatch(event);
        }
    }

    void start() {
        reset();
        DECK.deal(DEALER, 1);

        for (Player player : PLAYERS.values()) {
            DECK.deal(player, 2);
            if (player.HAND.getScore() == 21) {
                player.setStatus(Status.BLACKJACK);
            } else {
                player.setStatus(Status.PLAYING);
            }
        }

        Event event = new StartEvent(this);
        EventDispatcher.dispatch(event);

        boolean everyoneBlackjacks = PLAYERS.values().stream().allMatch(player -> player.getStatus() == Status.BLACKJACK);
        if (everyoneBlackjacks) {
            start();
            return;
        }
    }

    void reset() {
        DEALER.reset();
        for (Player player : PLAYERS.values()) {
            player.reset();
        }
    }

    void nextTurn() {
        Collection<Player> players = PLAYERS.values();
        for (Player player : players) {
            player.setAction(Actions.NONE);
        }

        Event event = new TurnStartEvent(this);
        EventDispatcher.dispatch(event);
    }

    private void processTurn() {
        Event event;
        Collection<Player> players = PLAYERS.values();
        for (Player player : players) {
            if (player.getStatus() != Status.PLAYING) {
                continue;
            }

            player.getAction().execute(this, player);

            Hand hand = player.HAND;
            if (hand.getScore() > 21) {
                player.setStatus(Status.LOST);
                event = new LoseEvent(this, player);
                EventDispatcher.dispatch(event);
            }
        }

        if (players.stream().allMatch(player -> player.getStatus() != Status.PLAYING)) {
            start();
            return;
        }

        if (players.stream().allMatch(player ->
                player.getStatus() != Status.PLAYING ||
                player.getAction() == Actions.STAND ||
                player.HAND.getScore() == 21
        )) {
            while (DEALER.HAND.getScore() < 17) {
                DECK.deal(DEALER, 1);
            }

            event = new EndRoundEvent(this);
            EventDispatcher.dispatch(event);

            for (Player player : players) {
                if (player.getStatus() != Status.PLAYING) {
                    continue;
                }

                Integer dealerScore = DEALER.HAND.getScore();
                if (dealerScore > 21) {
                    event = new WinEvent(this, player);
                    EventDispatcher.dispatch(event);
                    continue;
                }

                Integer playerScore = player.HAND.getScore();
                if (dealerScore > playerScore) {
                    event = new LoseEvent(this, player);
                    EventDispatcher.dispatch(event);
                    continue;
                }

                if (dealerScore < playerScore) {
                    event = new WinEvent(this, player);
                    EventDispatcher.dispatch(event);
                    continue;
                }

                event = new TieEvent(this, player);
                EventDispatcher.dispatch(event);
            }
        } else {
            nextTurn();
        }
    }

    public void setAction(@Nonnull Player of, @Nonnull String to) {
        of.setAction(to);

        boolean everyoneHasAction = PLAYERS.values().stream().allMatch(Player::isReady);
        if (everyoneHasAction) {
            processTurn();
        }
    }

}
