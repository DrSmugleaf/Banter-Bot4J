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
public class MechBuilder extends MechInformation {

    @NotNull
    private final List<Item> ITEMS = new ArrayList<>();

    @Nullable
    private Integer engineRating = null;

    private int minimumWeight = Tonnage.getSmallest();
    private int maximumWeight = Tonnage.getBiggest();

    public MechBuilder() {}

    @Override
    public @NotNull Models getModel() {
        return super.getModel();
    }

    @Override
    public @NotNull MechBuilder setModel(@NotNull Models model) {
        super.setModel(model);
        return this;
    }

    @Override
    public @NotNull String getName() {
        return super.getName();
    }

    @Override
    public @NotNull MechBuilder setName(@NotNull String name) {
        super.setName(name);
        return this;
    }

    @Override
    public @NotNull Map<Sections, List<Hardpoint>> getSections() {
        return super.getSections();
    }

    @Override
    public @NotNull MechBuilder setSections(@NotNull Map<Sections, List<Hardpoint>> sections) {
        super.setSections(sections);
        return this;
    }

    @Override
    public int getJumpJets() {
        return super.getJumpJets();
    }

    @Override
    public @NotNull MechBuilder setJumpJets(int jumpJets) {
        super.setJumpJets(jumpJets);
        return this;
    }

    @Override
    public int getMinimumEngine() {
        return super.getMinimumEngine();
    }

    @Override
    public @NotNull MechBuilder setMinimumEngine(int minimumEngine) {
        super.setMinimumEngine(minimumEngine);
        return this;
    }

    @Override
    public int getMaximumEngine() {
        return super.getMaximumEngine();
    }

    @Override
    public @NotNull MechBuilder setMaximumEngine(int maximumEngine) {
        super.setMaximumEngine(maximumEngine);
        return this;
    }

    @Override
    public @NotNull String getDefaultEngine() {
        return super.getDefaultEngine();
    }

    @Override
    public @NotNull MechBuilder setDefaultEngine(@NotNull String defaultEngine) {
        super.setDefaultEngine(defaultEngine);
        return this;
    }

    @Override
    public @NotNull Map<Hardpoints, Integer> getHardpoints() {
        return super.getHardpoints();
    }

    @Override
    public @NotNull MechBuilder setHardpoints(@NotNull Map<Hardpoints, Integer> hardpoints) {
        super.setHardpoints(hardpoints);
        return this;
    }

    @Override
    public @NotNull String getRotation() {
        return super.getRotation();
    }

    @Override
    public @NotNull MechBuilder setRotation(@NotNull String rotation) {
        super.setRotation(rotation);
        return this;
    }

    @Override
    public @Nullable Integer getCostMC() {
        return super.getCostMC();
    }

    @Override
    public @NotNull MechBuilder setCostMC(@NotNull Integer costMC) {
        super.setCostMC(costMC);
        return this;
    }

    @Override
    public @Nullable Integer getCostCBills() {
        return super.getCostCBills();
    }

    @Override
    public @NotNull MechBuilder setCostCBills(@NotNull Integer costCBills) {
        super.setCostCBills(costCBills);
        return this;
    }

    @Override
    public int getWeight() {
        return super.getWeight();
    }

    @NotNull
    @Override
    public MechBuilder setWeight(int weight) {
        super.setWeight(weight);
        return this;
    }

    @Override
    public @NotNull Tonnage getTonnage() {
        return super.getTonnage();
    }

    @Override
    public @NotNull MechBuilder setTonnage(@NotNull Tonnage tonnage) {
        super.setTonnage(tonnage);
        return this;
    }

    @Override
    public @NotNull Factions getFaction() {
        return super.getFaction();
    }

    @Override
    public @NotNull MechBuilder setFaction(@NotNull Factions faction) {
        super.setFaction(faction);
        return this;
    }

    public @Nullable Integer getEngineRating() {
        return engineRating;
    }

    public @NotNull MechBuilder setEngineRating(@Nullable Integer rating) {
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
    public List<Mech> findValid() {
        List<Mech> validMechs = new ArrayList<>();
        Collection<List<BattleMech>> allMechs = new ArrayList<>();

        if (getFaction() == Factions.NONE) {
            allMechs = BattleMech.getMechs().values();
        } else {
            allMechs.add(BattleMech.getMechs().get(getFaction()));
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

        if (getFaction() == Factions.NONE) {
            builder.append("Any");
        } else {
            builder.append(getFaction().getName());
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
