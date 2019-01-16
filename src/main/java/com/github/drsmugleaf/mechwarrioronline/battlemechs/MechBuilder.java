package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.Factions;
import com.github.drsmugleaf.mechwarrioronline.equipment.Item;
import com.github.drsmugleaf.mechwarrioronline.equipment.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class MechBuilder {

    @NotNull
    private final List<Item> ITEMS = new ArrayList<>();

    @NotNull
    private Factions faction = Factions.NONE;

    @Nullable
    private Integer engineRating = null;

    private int minimumWeight = Tonnage.getSmallest();
    private int maximumWeight = Tonnage.getBiggest();

    public MechBuilder() {}

    @NotNull
    public List<Mech> findValid() {
        List<Mech> validMechs = new ArrayList<>();
        Collection<List<BattleMech>> allMechs = new ArrayList<>();

        if (faction == Factions.NONE) {
            allMechs = BattleMech.getMechs().values();
        } else {
            allMechs.add(BattleMech.getMechs().get(faction));
        }

        for (List<BattleMech> mechs : allMechs) {
            for (BattleMech battlemech : mechs) {
                int minimumEngineRating = battlemech.getMinimumEngine();
                int maximumEngineRating = battlemech.getMaximumEngine();
                if (engineRating != null && (engineRating < minimumEngineRating || engineRating > maximumEngineRating)) {
                    continue;
                }

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

    @NotNull
    public MechBuilder addItem(@NotNull Item item, int amount) {
        for (int i = 0; i < amount; i++) {
            ITEMS.add(item);
        }

        return this;
    }

    @NotNull
    public MechBuilder addItem(@NotNull Item item) {
        return addItem(item, 1);
    }

    @NotNull
    public MechBuilder addItem(@NotNull Items item, int amount) {
        for (int i = 0; i < amount; i++) {
            ITEMS.add(Item.getItem(item));
        }

        return this;
    }

    @NotNull
    public MechBuilder addItem(@NotNull Items item) {
        return addItem(item, 1);
    }

    @NotNull
    public List<Item> getItems() {
        return new ArrayList<>(ITEMS);
    }

    @NotNull
    public Factions getFaction() {
        return faction;
    }

    @NotNull
    public MechBuilder setFaction(@NotNull Factions faction) {
        this.faction = faction;
        return this;
    }

    @Nullable
    public Integer getEngineRating() {
        return engineRating;
    }

    @NotNull
    public MechBuilder setEngineRating(int rating) {
        engineRating = rating;
        return this;
    }

    public int getMinimumWeight() {
        return minimumWeight;
    }

    @NotNull
    public MechBuilder setMinimumWeight(int weight) {
        minimumWeight = weight;
        return this;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    @NotNull
    public MechBuilder setMaximumWeight(int weight) {
        maximumWeight = weight;
        return this;
    }

    @NotNull
    public String validToString() {
        StringBuilder builder = new StringBuilder("**");

        if (!ITEMS.isEmpty()) {
            builder.append("Items: ");
        }

        Map<Item, Integer> items = new HashMap<>();
        for (Item item : ITEMS) {
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

        if (faction == Factions.NONE) {
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
