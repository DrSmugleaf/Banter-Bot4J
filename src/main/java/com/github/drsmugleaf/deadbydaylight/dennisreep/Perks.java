package com.github.drsmugleaf.deadbydaylight.dennisreep;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class Perks<K extends Enum<K>, V extends Perk> extends LinkedHashMap<K, V> {

    public Perks(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Perks() {
        super();
    }

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

    public double getAverageRating() {
        double ratings = 0.0;
        for (V perk : values()) {
            ratings += perk.RATING;
        }
        ratings /= values().size();

        return ratings;
    }

    public List<String> getNames() {
        return values()
                .stream()
                .map(Perk::getName)
                .collect(Collectors.toList());
    }

    public Perks<K, V> sortByValues() {
        LinkedHashMap<K, V> map = entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
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
                        LinkedHashMap::new
                ));

        return new Perks<>(map);
    }

}
