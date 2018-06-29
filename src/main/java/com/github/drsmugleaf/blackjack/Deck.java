package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Cards;
import com.github.drsmugleaf.blackjack.decks.Suits;
import com.github.drsmugleaf.blackjack.decks.french.FrenchCards;
import com.github.drsmugleaf.blackjack.decks.french.FrenchSuits;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Deck<T extends Enum & Cards, E extends Enum & Suits> {

    @Nonnull
    private final Class<T> CARD_TYPE;

    @Nonnull
    private final Class<E> SUIT_TYPE;

    @Nonnull
    private final List<Card> CARDS = new ArrayList<>();

    @Nonnull
    private final List<Card> DISCARDED = new ArrayList<>();

    Deck(@Nonnull Class<T> cards, @Nonnull Class<E> suits, int decks) {
        CARD_TYPE = cards;
        SUIT_TYPE = suits;

        for (int i = 0; i < decks; i++) {
            for (T cardType : CARD_TYPE.getEnumConstants()) {
                for (E suitType : SUIT_TYPE.getEnumConstants()) {
                    Card card = new Card(cardType, suitType);
                    CARDS.add(card);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    Deck() {
        this((Class<T>) FrenchCards.class, (Class<E>) FrenchSuits.class, 6);
    }

    @Nonnull
    private Card removeRandomCard() {
        int random = ThreadLocalRandom.current().nextInt(CARDS.size());
        return CARDS.remove(random);
    }

    private void deal(@Nonnull Hand hand) {
        if (CARDS.isEmpty()) {
            CARDS.addAll(DISCARDED);
            DISCARDED.clear();
        }

        hand.add(removeRandomCard());
    }

    void deal(@Nonnull Player player, int amount) {
        for (int i = 0; i < 2; i++) {
            deal(player.HAND);
        }
    }

    void deal(@Nonnull Dealer dealer, int amount) {
        for (int i = 0; i < amount; i++) {
            deal(dealer.HAND);
        }
    }

}
