package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.equipment.Item;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class Loadout {

    @NotNull
    private final Map<Sections, List<Hardpoint>> SECTIONS;

    @NotNull
    private final Map<Sections, List<Item>> ITEMS;

    protected Loadout(@NotNull Map<Sections, List<Hardpoint>> sections) {
        SECTIONS = sections;
        EnumMap<Sections, List<Item>> items = new EnumMap<>(Sections.class);

        for (Sections section : Sections.values()) {
            items.put(section, new ArrayList<>());
        }

        ITEMS = items;
    }

    private boolean fit(@NotNull Item item) {
        for (Sections section : ITEMS.keySet()) {
            List<Hardpoint> hardpoints = SECTIONS.get(section);
            Optional<Hardpoint> hardpoint = hardpoints.stream().filter(h -> h.getType() == item.getHardpoint()).findFirst();
            if (!hardpoint.isPresent()) {
                continue;
            }

            int usedSlots = ITEMS.get(section).stream().mapToInt(Item::getSlots).sum();
            if (section.getSlots() < usedSlots + item.getSlots()) {
                continue;
            }

            ITEMS.get(section).add(item);
            SECTIONS.get(section).remove(hardpoint.get());

            return true;
        }

        return false;
    }

    protected boolean fit(@NotNull List<Item> items) {
        items = new ArrayList<>(items);
        List<Item> itemsLeft = new ArrayList<>(items);

        for (Sections section : ITEMS.keySet()) {
            if (!section.isFittable()) {
                continue;
            }

            for (int i = 0; i < itemsLeft.size(); i++) {
                Item item = itemsLeft.get(i);
                if (fit(item)) {
                    items.remove(i);
                    return fit(items);
                }
            }
        }

        return items.isEmpty();
    }

}
