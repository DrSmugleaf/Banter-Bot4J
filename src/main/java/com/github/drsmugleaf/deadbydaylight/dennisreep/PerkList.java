package com.github.drsmugleaf.deadbydaylight.dennisreep;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class PerkList<E extends Perk> extends ArrayList<E> {

    public PerkList(List<E> m) {
        super(m);
    }

    public PerkList() {
        super();
    }

    @Nonnull
    public PerkList<E> getBest(int amount) {
        if (amount > size()) {
            amount = size();
        }

        return sortByValues()
                .stream()
                .limit(amount)
                .collect(Collectors.toCollection(PerkList::new));
    }

    @Nonnull
    public PerkList<E> getRandom(int amount) {
        if (amount > size()) {
            amount = size();
        }

        PerkList<E> perks = new PerkList<>(this);
        Collections.shuffle(perks);

        return perks
                .stream()
                .limit(amount)
                .collect(Collectors.toCollection(PerkList::new));
    }

    @Nonnull
    public PerkList<E> getRandom(int amount, @Nonnull Killer killer) {
        PerkList<E> perks = getRandom(amount)
                .stream()
                .filter(perk -> !(perk.getCharacter() != null && perk.getCharacter() != killer))
                .collect(Collectors.toCollection(PerkList::new));

        Collections.shuffle(perks);

        return perks;
    }

    public double getAverageRating() {
        double ratings = 0.0;
        for (E perk : this) {
            ratings += perk.RATING;
        }
        ratings /= size();

        return ratings;
    }

    @Nonnull
    public List<String> getNames() {
        return stream()
                .map(Perk::getName)
                .collect(Collectors.toList());
    }

    @Nonnull
    public Tiers getTier() {
        double rating = getAverageRating();
        return Tiers.from(rating);
    }

    @Nonnull
    public PerkList<E> getWithinRating(@Nullable Double from, @Nullable Double to) {
        final double finalFrom;
        if (from == null) {
            finalFrom = Double.MIN_VALUE;
        } else {
            finalFrom = from;
        }

        final double finalTo;
        if (to == null) {
            finalTo = Double.MAX_VALUE;
        } else {
            finalTo = to;
        }

        PerkList<E> newPerks = new PerkList<>(this);
        newPerks.removeIf(entry -> {
                    double perkRating = entry.getRating();
                    return perkRating < finalFrom || perkRating > finalTo;
        });

        return newPerks;
    }

    @Nonnull
    public PerkList<E> getWithinRating(@Nonnull Tiers from, @Nonnull Tiers to) {
        return getWithinRating(from.THRESHOLD, to.THRESHOLD);
    }

    public PerkList<E> sortByValues() {
        return stream()
                .sorted()
                .collect(Collectors.toCollection(PerkList::new));
    }

}
