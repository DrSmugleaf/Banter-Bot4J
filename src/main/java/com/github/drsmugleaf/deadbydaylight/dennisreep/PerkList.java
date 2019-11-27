package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    public PerkList<E> getBest(int amount) {
        if (amount > size()) {
            amount = size();
        }

        return sortByValues()
                .stream()
                .limit(amount)
                .collect(Collectors.toCollection(PerkList::new));
    }

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

    public PerkList<E> getRandom(int amount, Killer killer) {
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

    public List<String> getNames() {
        return stream()
                .map(Perk::getName)
                .collect(Collectors.toList());
    }

    public Tiers getTier() {
        double rating = getAverageRating();
        return Tiers.from(rating);
    }

    public PerkList<E> getWithinRating(@Nullable Double from, @Nullable Double to) {
        final double finalFrom = Objects.requireNonNullElse(from, Double.MIN_VALUE);
        final double finalTo = Objects.requireNonNullElse(to, Double.MAX_VALUE);
        PerkList<E> newPerks = new PerkList<>(this);
        newPerks.removeIf(entry -> {
                    double perkRating = entry.getRating();
                    return perkRating < finalFrom || perkRating > finalTo;
        });

        return newPerks;
    }

    public PerkList<E> getWithinRating(Tiers from, Tiers to) {
        return getWithinRating(from.THRESHOLD, to.THRESHOLD);
    }

    public PerkList<E> sortByValues() {
        return stream()
                .sorted()
                .collect(Collectors.toCollection(PerkList::new));
    }

}
