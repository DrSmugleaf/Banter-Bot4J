package com.github.drsmugleaf.deadbydaylight.dennisreep;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class Perks<K extends Enum<K>, V extends Perk> extends LinkedHashMap<K, V> {

    public Perks(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Perks<K, V> sortByValues() {
        LinkedHashMap<K, V> map = entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (u, v) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));

        return new Perks<>(map);
    }

}
