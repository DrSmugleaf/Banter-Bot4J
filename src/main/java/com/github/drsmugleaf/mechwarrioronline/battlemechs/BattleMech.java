package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.Factions;
import com.github.drsmugleaf.mechwarrioronline.equipment.Item;
import com.opencsv.*;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class BattleMech {

    @NotNull
    private static final String PATH = Objects.requireNonNull(
            Item.class.getClassLoader().getResource("mechwarrioronline/battlemechs")
    ).getFile();

    @NotNull
    private static final Pattern MODEL_PATTERN = Pattern.compile(
            "(.+) \\((\\d+)\\)"
    );

    @NotNull
    private static final Pattern SECTIONS_PATTERN = Pattern.compile(
            "(?<amount>\\d+) (?<type>\\w+)"
    );

    @NotNull
    private static final Pattern MISSILES_PATTERN = Pattern.compile(
            "(?:\\(|, )(\\d+x\\d+|\\d+)(?:\\)|)"
    );

    @NotNull
    private static final Pattern ENGINES_PATTERN = Pattern.compile(
            "((?<minimumEngine>\\d+)-(?<maximumEngine>\\d+))?\\s?((?<defaultEngineType>STD|XL|Light)(?<defaultEngineModel>\\d+))"
    );

    @NotNull
    private static final Pattern HARDPOINTS_PATTERN = Pattern.compile(
            "(?<energy>\\d+) (?<ballistic>\\d+) (?<missile>\\d+)"
    );

    @NotNull
    private static final Pattern COSTS_PATTERN = Pattern.compile(
            "(?<mc>\\d+)?(?: (?<cbills>\\d+))?"
    );

    @NotNull
    private static final Map<Factions, List<BattleMech>> MECHS = registerMechs();

    @NotNull
    private final Models MODEL;

    @NotNull
    private final String NAME;

    @NotNull
    private final Map<Sections, List<Hardpoint>> SECTIONS;

    private final int JUMP_JETS;
    private final int MINIMUM_ENGINE;
    private final int MAXIMUM_ENGINE;

    @NotNull
    private final String DEFAULT_ENGINE;

    @NotNull
    private final Map<Hardpoints, Integer> HARDPOINTS;

    @NotNull
    private final String ROTATION;

    @Nullable
    private final Integer COST_MC;

    @Nullable
    private final Integer COST_CBILLS;

    private final int WEIGHT;

    @NotNull
    private final Tonnage TONNAGE;

    @NotNull
    private final Factions FACTION;

    private BattleMech(
            @NotNull Models model,
            @NotNull String name,
            @NotNull Map<Sections, List<Hardpoint>> sections,
            int jumpJets,
            int minimumEngine,
            int maximumEngine,
            @NotNull String defaultEngine,
            @NotNull Map<Hardpoints, Integer> hardpoints,
            @NotNull String rotation,
            @Nullable Integer costMC,
            @Nullable Integer costCBills,
            int weight,
            @NotNull Tonnage tonnage,
            @NotNull Factions faction
    ) {
        MODEL = model;
        NAME = name;
        SECTIONS = Collections.unmodifiableMap(sections);
        JUMP_JETS = jumpJets;
        MINIMUM_ENGINE = minimumEngine;
        MAXIMUM_ENGINE = maximumEngine;
        DEFAULT_ENGINE = defaultEngine;
        HARDPOINTS = Collections.unmodifiableMap(hardpoints);
        ROTATION = rotation;
        COST_MC = costMC;
        COST_CBILLS = costCBills;
        WEIGHT = weight;
        TONNAGE = tonnage;
        FACTION = faction;
    }

    protected BattleMech(@NotNull BattleMech mech) {
        this(
                mech.MODEL,
                mech.NAME,
                mech.SECTIONS,
                mech.JUMP_JETS,
                mech.MINIMUM_ENGINE,
                mech.MAXIMUM_ENGINE,
                mech.DEFAULT_ENGINE,
                mech.HARDPOINTS,
                mech.ROTATION,
                mech.COST_MC,
                mech.COST_CBILLS,
                mech.WEIGHT,
                mech.TONNAGE,
                mech.FACTION
        );
    }

    @NotNull
    private static BattleMech from(@NotNull Models model, @NotNull Map<String, String> csv, int weight, @NotNull Tonnage tonnage, @NotNull Factions faction) {
        String name = csv.get("Name");
        Map<Sections, List<Hardpoint>> sections = new EnumMap<>(Sections.class);

        for (Sections section : Sections.values()) {
            List<Hardpoint> hardpoints = new ArrayList<>();
            if (!section.isFittable()) {
                sections.put(section, hardpoints);
                continue;
            }

            String cell = csv.get(section.getName());
            if (cell == null) {
                sections.put(section, hardpoints);
                continue;
            }

            Matcher sectionsMatcher = SECTIONS_PATTERN.matcher(cell);
            while (sectionsMatcher.find()) {
                int amount = Integer.parseInt(sectionsMatcher.group("amount"));
                Hardpoints type = Hardpoints.from(sectionsMatcher.group("type"));

                if (type == Hardpoints.MISSILE) {
                    Matcher missileMatcher = MISSILES_PATTERN.matcher(cell);
                    while (missileMatcher.find()) {
                        String group = missileMatcher.group(1);
                        int size;
                        int repeats;

                        if (group.contains("x")) {
                            String[] groupArray = group.split("x");
                            size = Integer.parseInt(groupArray[0]);
                            repeats = Integer.parseInt(groupArray[1]);
                        } else {
                            size = Integer.parseInt(group);
                            repeats = 1;
                        }

                        for (int i = 0; i < repeats; i++) {
                            hardpoints.add(new Hardpoint(type, size));
                        }
                    }
                } else {
                    for (int i = 0; i < amount; i++) {
                        hardpoints.add(new Hardpoint(type, 1));
                    }
                }
            }

            sections.put(section, hardpoints);
        }

        int jumpJets;
        String jumpJetsCSV = csv.get("JJ");
        if (jumpJetsCSV == null) {
            jumpJets = 0;
        } else {
            jumpJets = Integer.parseInt(jumpJetsCSV);
        }

        Matcher engineMatcher = ENGINES_PATTERN.matcher(csv.get("Engines"));
        engineMatcher.find();
        String engineType = engineMatcher.group("defaultEngineType");
        int engineModel = Integer.parseInt(engineMatcher.group("defaultEngineModel"));

        String minimumEngineCSV = engineMatcher.group("minimumEngine");
        String maximumEngineCSV = engineMatcher.group("maximumEngine");
        int minimumEngine;
        int maximumEngine;
        String defaultEngine = engineType + engineModel;
        if (minimumEngineCSV != null && maximumEngineCSV != null) {
            minimumEngine = Integer.parseInt(minimumEngineCSV);
            maximumEngine = Integer.parseInt(maximumEngineCSV);
        } else {
            minimumEngine = engineModel;
            maximumEngine = engineModel;
        }

        Matcher hardpointsMatcher = HARDPOINTS_PATTERN.matcher(csv.get("Hardpoints"));
        hardpointsMatcher.find();
        int energyHardpoints = Integer.parseInt(hardpointsMatcher.group("energy"));
        int ballisticHardpoints = Integer.parseInt(hardpointsMatcher.group("ballistic"));
        int missileHardpoints = Integer.parseInt(hardpointsMatcher.group("missile"));
        Map<Hardpoints, Integer> hardpoints = new EnumMap<>(Hardpoints.class);
        hardpoints.put(Hardpoints.ENERGY, energyHardpoints);
        hardpoints.put(Hardpoints.BALLISTIC, ballisticHardpoints);
        hardpoints.put(Hardpoints.MISSILE, missileHardpoints);

        String rotation = csv.get("Torso/Arm");

        String costsCSV = csv.get("Costs");
        Integer costMC = null;
        Integer costCBills = null;
        if (costsCSV != null) {
            Matcher costs = COSTS_PATTERN.matcher(costsCSV);
            costs.find();

            String mcCSV = costs.group("mc");
            if (mcCSV != null) {
                costMC = Integer.valueOf(mcCSV);
            }

            String cbillsCSV = costs.group("cbills");
            if (cbillsCSV != null) {
                costCBills = Integer.valueOf(cbillsCSV);
            }
        }

        return new BattleMech(
                model,
                name,
                sections,
                jumpJets,
                minimumEngine,
                maximumEngine,
                defaultEngine,
                hardpoints,
                rotation,
                costMC,
                costCBills,
                weight,
                tonnage,
                faction
        );
    }

    @NotNull
    private static Map<Factions, List<BattleMech>> registerMechs() {
        Map<Factions, List<BattleMech>> mechs = new EnumMap<>(Factions.class);

        for (Factions faction : Factions.values()) {
            mechs.put(faction, new ArrayList<>());
            String csvPath = PATH + "/" + faction.getName().toLowerCase() + ".csv";
            List<Map<String, String>> csv = read(csvPath);

            String modelName = null;
            int weight = 20;
            Tonnage tonnage = Tonnage.LIGHT;
            for (Map<String, String> line : csv) {
                Matcher modelMatcher = MODEL_PATTERN.matcher(line.get("Name"));
                if (modelMatcher.matches()) {
                    modelName = modelMatcher.group(1);
                    weight = Integer.parseInt(modelMatcher.group(2));
                    tonnage = Tonnage.getByWeight(weight);
                    continue;
                }

                Models model = Models.from(modelName);
                BattleMech mech = from(model, line, weight, tonnage, faction);
                mechs.get(faction).add(mech);
            }
        }

        return mechs;
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
    public static Map<Factions, List<BattleMech>> getMechs() {
        return MECHS;
    }

    @NotNull
    public Models getModel() {
        return MODEL;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public Map<Sections, List<Hardpoint>> getSections() {
        return SECTIONS;
    }

    public int getJumpJets() {
        return JUMP_JETS;
    }

    public int getMinimumEngine() {
        return MINIMUM_ENGINE;
    }

    public int getMaximumEngine() {
        return MAXIMUM_ENGINE;
    }

    @NotNull
    public String getDefaultEngine() {
        return DEFAULT_ENGINE;
    }

    @NotNull
    public Map<Hardpoints, Integer> getHardpoints() {
        return HARDPOINTS;
    }

    @NotNull
    public String getRotation() {
        return ROTATION;
    }

    @Nullable
    public Integer getCostMC() {
        return COST_MC;
    }

    @Nullable
    public Integer getCostCBills() {
        return COST_CBILLS;
    }

    public int getWeight() {
        return WEIGHT;
    }

    @NotNull
    public Tonnage getTonnage() {
        return TONNAGE;
    }

    @NotNull
    public Factions getFaction() {
        return FACTION;
    }

}
