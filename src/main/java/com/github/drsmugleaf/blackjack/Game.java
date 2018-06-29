package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
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
    private final Deck DECK;

    @Nonnull
    private final Map<Long, Player> PLAYERS = new LinkedHashMap<>();

    Game(@Nonnull Deck deck, @Nonnull Set<Long> players) {
        DECK = deck;
        for (Long id : players) {
            PLAYERS.put(id, new Player(id));
        }
    }

    public void addPlayer(@Nonnull Long id) {
        PLAYERS.put(id, new Player(id));
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
    }

    public void start() {
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

    public void reset() {
        DEALER.reset();
        for (Player player : PLAYERS.values()) {
            player.reset();
        }
    }

}
