package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.IPerk;
import com.github.drsmugleaf.deadbydaylight.Killers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class Perks<K extends Enum<K> & IPerk, V extends Perk> extends LinkedHashMap<K, V> {

    public Perks(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Perks() {
        super();
    }

    @Nonnull
    public Perks<K, V> getBest(int amount) {
        if (amount > size()) {
            amount = size();
        }

        return sortByValues()
                .entrySet()
                .stream()
                .limit(amount)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> {
                            throw new IllegalStateException(
                                    "Duplicate key found.\n" +
                                    "Key: " + k.getName() + "\n" +
                                    "Value: " + v.getName()
                            );
                        },
                        Perks::new
                ));
    }

    @Nonnull
    public Perks<K, V> getRandom(int amount) {
        if (amount > size()) {
            amount = size();
        }

        Perks<K, V> perks = new Perks<>(this);
        Set<Map.Entry<K, V>> entrySet = perks.entrySet();

        List<Map.Entry<K, V>> keyList = new ArrayList<>(entrySet);
        Collections.shuffle(keyList);
        Set<Map.Entry<K, V>> randomSet = new HashSet<>(keyList.subList(0, amount));

        return randomSet.stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> {
                            throw new IllegalStateException(
                                    "Duplicate key found.\n" +
                                    "Key: " + k.getName() + "\n" +
                                    "Value: " + v.getName()
                            );
                        },
                        Perks::new
                ));
    }

    @Nonnull
    public Perks<K, V> getRandom(int amount, @Nonnull Killers killer) {
        if (amount > size()) {
            amount = size();
        }

        Perks<K, V> newPerks = new Perks<>(this);
        Set<Map.Entry<K, V>> entrySet = newPerks.entrySet();
        entrySet.removeIf(entry -> {
            ICharacter character = entry.getValue().getCharacter();
            return character != Killers.ALL && character != killer;
        });

        List<Map.Entry<K, V>> keyList = new ArrayList<>(entrySet);
        Collections.shuffle(keyList);
        Set<Map.Entry<K, V>> randomSet = new HashSet<>(keyList.subList(0, amount));

        return randomSet.stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> {
                            throw new IllegalStateException(
                                    "Duplicate key found.\n" +
                                    "Key: " + k.getName() + "\n" +
                                    "Value: " + v.getName()
                            );
                        },
                        Perks::new
                ));
    }

    public double getAverageRating() {
        double ratings = 0.0;
        for (V perk : values()) {
            ratings += perk.RATING;
        }
        ratings /= values().size();

        return ratings;
    }

    @Nonnull
    public List<String> getNames() {
        return values()
                .stream()
                .map(Perk::getName)
                .collect(Collectors.toList());
    }

    @Nonnull
    public Tiers getTier() {
        double rating = getAverageRating();
        return Tiers.from(rating);
    }

    @Nonnull
    public Perks<K, V> getWithinRating(@Nullable Double from, @Nullable Double to) {
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

        Perks<K, V> newPerks = new Perks<>(this);
        newPerks.entrySet()
                .removeIf(entry -> {
                    double perkRating = entry.getValue().getRating();
                    return perkRating < finalFrom || perkRating > finalTo;
                });

        return newPerks;
    }

    @Nonnull
    public Perks<K, V> getWithinRating(@Nonnull Tiers from, @Nonnull Tiers to) {
        return getWithinRating(from.THRESHOLD, to.THRESHOLD);
    }

    public Perks<K, V> sortByValues() {
        return entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> {
                            throw new IllegalStateException(
                                    "Duplicate key found.\n" +
                                    "Key: " + k.getName() + "\n" +
                                    "Value: " + v.getName()
                            );
                        },
                        Perks::new
                ));
    }

}
