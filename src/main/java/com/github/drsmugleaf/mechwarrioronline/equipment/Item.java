package com.github.drsmugleaf.mechwarrioronline.equipment;

import com.github.drsmugleaf.mechwarrioronline.Factions;
import com.github.drsmugleaf.mechwarrioronline.battlemechs.Hardpoints;
import com.opencsv.*;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by DrSmugleaf on 10/01/2019
 */
public class Item {

    @NotNull
    private static final String PATH = Objects.requireNonNull(
            Item.class.getClassLoader().getResource("mechwarrioronline/equipment")
    ).getFile();

    @NotNull
    private static final Map<Factions, List<Item>> ITEMS = registerItems();

    @NotNull
    private final String NAME;

    @Nullable
    private final Double DAMAGE;

    @Nullable
    private final Double HEAT;

    @Nullable
    private final String COOLDOWN;

    @NotNull
    private final String RANGE;

    private final int MAX_RANGE;
    private final int SLOTS;
    private final double TONS;

    @Nullable
    private final Integer SPEED;

    @Nullable
    private final String AMMO_PER_TON;

    @Nullable
    private final String DURATION;

    @Nullable
    private final Double DPS;

    @Nullable
    private final Double DPH;

    @Nullable
    private final Double DPS_PER_TON;

    @Nullable
    private final Double HPS;

    @Nullable
    private final Double IMPULSE;

    private final double HEALTH;

    @Nullable
    private final Integer COSTS;

    @NotNull
    private final Hardpoints HARDPOINT;

    private Item(
            @NotNull String name,
            @Nullable Double damage,
            @Nullable Double heat,
            @Nullable String cooldown,
            @NotNull String range,
            int maxRange,
            int slots,
            double tons,
            @Nullable Integer speed,
            @Nullable String ammoPerTon,
            @Nullable String duration,
            @Nullable Double dps,
            @Nullable Double dph,
            @Nullable Double dpsPerTon,
            @Nullable Double hps,
            @Nullable Double impulse,
            double health,
            @Nullable Integer costs,
            @NotNull Hardpoints hardpoint
    ) {
        NAME = name;
        DAMAGE = damage;
        HEAT = heat;
        COOLDOWN = cooldown;
        RANGE = range;
        MAX_RANGE = maxRange;
        SLOTS = slots;
        TONS = tons;
        SPEED = speed;
        AMMO_PER_TON = ammoPerTon;
        DURATION = duration;
        DPS = dps;
        DPH = dph;
        DPS_PER_TON = dpsPerTon;
        HPS = hps;
        IMPULSE = impulse;
        HEALTH = health;
        COSTS = costs;
        HARDPOINT = hardpoint;
    }

    private Item(
            @NotNull String name,
            @Nullable String damage,
            @Nullable String heat,
            @Nullable String cooldown,
            @NotNull String range,
            @NotNull String maxRange,
            @NotNull String slots,
            @NotNull String tons,
            @Nullable String speed,
            @Nullable String ammoPerTon,
            @Nullable String duration,
            @Nullable String dps,
            @Nullable String dph,
            @Nullable String dpsPerTon,
            @Nullable String hps,
            @Nullable String impulse,
            @NotNull String health,
            @Nullable String costs,
            @NotNull Hardpoints hardpoint
    ) {
        this(
                name,
                damage != null ? Double.valueOf(damage) : null,
                heat != null ? Double.valueOf(heat) : null,
                cooldown,
                range,
                maxRange != null ? Integer.valueOf(maxRange) : null,
                slots != null ? Integer.valueOf(slots) : null,
                tons != null ? Double.valueOf(tons) : null,
                speed != null ? Integer.valueOf(speed) : null,
                ammoPerTon,
                duration,
                dps != null ? Double.valueOf(dps) : null,
                dph != null ? Double.valueOf(dph) : null,
                dpsPerTon != null ? Double.valueOf(dpsPerTon) : null,
                hps != null ? Double.valueOf(hps) : null,
                impulse != null ? Double.valueOf(impulse) : null,
                health != null ? Double.valueOf(health) : null,
                costs != null ? Integer.valueOf(costs) : null,
                hardpoint
        );
    }

    @NotNull
    private static Item from(@NotNull Map<String, String> csv, @NotNull Hardpoints hardpoint) {
        return new Item(
                csv.get("Name"),
                csv.get("Damage"),
                csv.get("Heat"),
                csv.get("Cooldown"),
                csv.get("Range"),
                csv.get("Max Range"),
                csv.get("Slots"),
                csv.get("Tons"),
                csv.get("Speed"),
                csv.get("Ammo/t"),
                csv.get("Duration"),
                csv.get("DPS"),
                csv.get("DPH"),
                csv.get("DPS/T"),
                csv.get("HPS"),
                csv.get("Impulse"),
                csv.get("Health"),
                csv.get("Costs"),
                hardpoint
        );
    }

    @NotNull
    private static Map<Factions, List<Item>> registerItems() {
        Map<Factions, List<Item>> map = new EnumMap<>(Factions.class);

        for (Factions faction : Factions.values()) {
            map.put(faction, new ArrayList<>());

            for (Hardpoints hardpoint : Hardpoints.values()) {
                String csvPath = PATH + "/" + faction.getName().toLowerCase() + "/" + hardpoint.getName().toLowerCase() + ".csv";
                List<Map<String, String>> csv = read(csvPath);
                for (Map<String, String> line : csv) {
                    Item item = from(line, hardpoint);
                    map.get(faction).add(item);
                }
            }

            map.put(faction, Collections.unmodifiableList(map.get(faction)));
        }

        return Collections.unmodifiableMap(map);
    }

    @NotNull
    private static List<Map<String, String>> read(@NotNull String csvPath) {
        List<Map<String, String>> csv = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS).build();

        try (
                FileReader fileReader = new FileReader(csvPath);
                CSVReaderHeaderAware reader = (CSVReaderHeaderAware) new CSVReaderHeaderAwareBuilder(fileReader)
                        .withCSVParser(parser)
                        .build()
        ) {
            Map<String, String> line;
            while ((line = reader.readMap()) != null) {
                csv.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File " + PATH + " not found", e);
        } catch (IOException e) {
            throw new IllegalStateException("Error reading file " + PATH, e);
        }

        return csv;
    }

    @NotNull
    public static Map<Factions, List<Item>> getItems() {
        return ITEMS;
    }

    @NotNull
    public static Item getItem(@NotNull String name) {
        name = name.toLowerCase();

        for (List<Item> items : ITEMS.values()) {
            for (Item item : items) {
                if (item.NAME.toLowerCase().equals(name)) {
                    return item;
                }
            }
        }

        throw new IllegalArgumentException("No item found with name " + name);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @Nullable
    public Double getDamage() {
        return DAMAGE;
    }

    @Nullable
    public Double getHeat() {
        return HEAT;
    }

    @Nullable
    public String getCooldown() {
        return COOLDOWN;
    }

    @NotNull
    public String getRange() {
        return RANGE;
    }

    public int getMaxRange() {
        return MAX_RANGE;
    }

    public int getSlots() {
        return SLOTS;
    }

    public double getTons() {
        return TONS;
    }

    @Nullable
    public Integer getSpeed() {
        return SPEED;
    }

    @Nullable
    public String getAmmoPerTon() {
        return AMMO_PER_TON;
    }

    @Nullable
    public String getDuration() {
        return DURATION;
    }

    @Nullable
    public Double getDps() {
        return DPS;
    }

    @Nullable
    public Double getDph() {
        return DPH;
    }

    @Nullable
    public Double getDpsPerTon() {
        return DPS_PER_TON;
    }

    @Nullable
    public Double getHps() {
        return HPS;
    }

    @Nullable
    public Double getImpulse() {
        return IMPULSE;
    }

    public double getHealth() {
        return HEALTH;
    }

    @Nullable
    public Integer getCosts() {
        return COSTS;
    }

    @NotNull
    public Hardpoints getHardpoint() {
        return HARDPOINT;
    }

}
