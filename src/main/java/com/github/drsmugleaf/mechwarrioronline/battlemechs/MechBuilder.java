package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.Factions;
import com.github.drsmugleaf.mechwarrioronline.equipment.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class MechBuilder {

    @NotNull
    private final List<Item> ITEMS = new ArrayList<>();

    @Nullable
    private Factions faction = null;

    private int minimumWeight = Integer.MIN_VALUE;
    private int maximumWeight = Integer.MAX_VALUE;

    public MechBuilder() {}

    @NotNull
    public List<Mech> findValid() {
        List<Mech> validMechs = new ArrayList<>();
        Collection<List<BattleMech>> allMechs = new ArrayList<>();

        if (faction != null) {
            allMechs.add(BattleMech.getMechs().get(faction));
        } else {
            allMechs = BattleMech.getMechs().values();
        }

        for (List<BattleMech> mechs : allMechs) {
            for (BattleMech battlemech : mechs) {
                int weight = battlemech.getWeight();
                if (weight < minimumWeight || weight > maximumWeight) {
                    continue;
                }

                Mech mech = new Mech(battlemech);
                if (mech.getLoadout().fit(ITEMS)) {
                    validMechs.add(mech);
                }
            }
        }

        return validMechs;
    }

    public boolean addItem(@NotNull Item item) {
        return ITEMS.add(item);
    }

    public void setFaction(@Nullable Factions faction) {
        this.faction = faction;
    }

    public void setMinimumWeight(int weight) {
        minimumWeight = weight;
    }

    public void setMaximumWeight(int weight) {
        maximumWeight = weight;
    }

    @NotNull
    public String validToString() {
        StringBuilder builder = new StringBuilder("**");

        if (!ITEMS.isEmpty()) {
            builder.append("Items: ");
        }

        Map<Item, Integer> items = new HashMap<>();
        for (int i = 0; i < ITEMS.size(); i++) {
            Item item = ITEMS.get(i);
            if (!items.containsKey(item)) {
                items.put(item, 1);
                continue;
            }

            items.put(item, items.get(item) + 1);
        }

        for (Item item : items.keySet()) {
            builder
                    .append(items.get(item))
                    .append(" ")
                    .append(item.getName())
                    .append(", ");
        }

        builder
                .append("Weight: ")
                .append(minimumWeight)
                .append("-")
                .append(maximumWeight)
                .append(", ")
                .append("Faction: ");

        if (faction == null) {
            builder.append("Any");
        } else {
            builder.append(faction.getName());
        }

        List<Mech> mechs = findValid();
        builder
                .append(", ")
                .append(mechs.size())
                .append(" mechs")
                .append("**\n");

        for (int i = 0; i < mechs.size(); i++) {
            Mech mech = mechs.get(i);
            builder
                    .append("**")
                    .append(i + 1)
                    .append(":** ")
                    .append(mech)
                    .append("\n");
        }

        return builder.toString();
    }

    @NotNull
    public List<String> validToString(int size) {
        List<String> lines = new ArrayList<>(Arrays.asList(validToString().split("\n")));
        List<String> sizedLines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String line : lines) {
            if (currentLine.length() + line.length() > size) {
                sizedLines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }

            currentLine
                    .append("\n")
                    .append(line);
        }

        if (currentLine.length() > 0) {
            sizedLines.add(currentLine.toString());
        }

        return sizedLines;
    }

}
