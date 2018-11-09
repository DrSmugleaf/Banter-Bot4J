package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.IPerk;
import com.github.drsmugleaf.deadbydaylight.Killers;

import javax.annotation.Nonnull;
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

    public Perks<K, V> getRandom(int amount) {
        if (amount > size()) {
            amount = size();
        }

        Set<Map.Entry<K, V>> entrySet = entrySet();
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

    public Perks<K, V> getRandom(int amount, @Nonnull Killers killer) {
        if (amount > size()) {
            amount = size();
        }

        Set<Map.Entry<K, V>> entrySet = entrySet();
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
