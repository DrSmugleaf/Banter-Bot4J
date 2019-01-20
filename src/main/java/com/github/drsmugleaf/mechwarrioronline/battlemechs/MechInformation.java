package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.Factions;
import com.github.drsmugleaf.mechwarrioronline.equipment.Hardpoints;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 16/01/2019
 */
public class MechInformation {

    @NotNull
    private Models model;

    @NotNull
    private String name;

    @NotNull
    private Map<Sections, List<Hardpoint>> sections;

    private int jumpJets;
    private int minimumEngine;
    private int maximumEngine;

    @NotNull
    private String defaultEngine;

    @NotNull
    private Map<Hardpoints, Integer> hardpoints;

    @NotNull
    private String rotation;

    private Integer costMC;

    private Integer costCBills;

    private int weight;

    @NotNull
    private Tonnage tonnage;

    @NotNull
    private Factions faction = Factions.NONE;

    public MechInformation(
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
        this.model = model;
        this.name = name;
        this.sections = sections;
        this.jumpJets = jumpJets;
        this.minimumEngine = minimumEngine;
        this.maximumEngine = maximumEngine;
        this.defaultEngine = defaultEngine;
        this.hardpoints = hardpoints;
        this.rotation = rotation;
        this.costMC = costMC;
        this.costCBills = costCBills;
        this.weight = weight;
        this.tonnage = tonnage;
        this.faction = faction;
    }
    
    protected MechInformation(@NotNull MechInformation information) {
        this(
                information.model,
                information.name,
                information.sections,
                information.jumpJets,
                information.minimumEngine,
                information.maximumEngine,
                information.defaultEngine,
                information.hardpoints,
                information.rotation,
                information.costMC,
                information.costCBills,
                information.weight,
                information.tonnage,
                information.faction
        );
    }

    protected MechInformation() {}

    @NotNull
    public Models getModel() {
        return model;
    }

    public @NotNull MechInformation setModel(@NotNull Models model) {
        this.model = model;
        return this;
    }


    @NotNull
    public String getName() {
        return name;
    }

    public @NotNull MechInformation setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public Map<Sections, List<Hardpoint>> getSections() {
        return sections;
    }

    public @NotNull MechInformation setSections(@NotNull Map<Sections, List<Hardpoint>> sections) {
        this.sections = sections;
        return this;
    }

    public int getJumpJets() {
        return jumpJets;
    }

    public @NotNull MechInformation setJumpJets(int jumpJets) {
        this.jumpJets = jumpJets;
        return this;
    }

    public int getMinimumEngine() {
        return minimumEngine;
    }

    public @NotNull MechInformation setMinimumEngine(int minimumEngine) {
        this.minimumEngine = minimumEngine;
        return this;
    }

    public int getMaximumEngine() {
        return maximumEngine;
    }

    public @NotNull MechInformation setMaximumEngine(int maximumEngine) {
        this.maximumEngine = maximumEngine;
        return this;
    }

    @NotNull
    public String getDefaultEngine() {
        return defaultEngine;
    }

    public @NotNull MechInformation setDefaultEngine(@NotNull String defaultEngine) {
        this.defaultEngine = defaultEngine;
        return this;
    }

    @NotNull
    public Map<Hardpoints, Integer> getHardpoints() {
        return hardpoints;
    }

    public @NotNull MechInformation setHardpoints(@NotNull Map<Hardpoints, Integer> hardpoints) {
        this.hardpoints = hardpoints;
        return this;
    }

    @NotNull
    public String getRotation() {
        return rotation;
    }

    public @NotNull MechInformation setRotation(@NotNull String rotation) {
        this.rotation = rotation;
        return this;
    }

    @Nullable
    public Integer getCostMC() {
        return costMC;
    }

    public @NotNull MechInformation setCostMC(@NotNull Integer costMC) {
        this.costMC = costMC;
        return this;
    }

    @Nullable
    public Integer getCostCBills() {
        return costCBills;
    }

    public @NotNull MechInformation setCostCBills(@NotNull Integer costCBills) {
        this.costCBills = costCBills;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public @NotNull MechInformation setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @NotNull
    public Tonnage getTonnage() {
        return tonnage;
    }
    
    public @NotNull MechInformation setTonnage(@NotNull Tonnage tonnage) {
        this.tonnage = tonnage;
        return this;
    }

    @NotNull
    public Factions getFaction() {
        return faction;
    }
    
    public @NotNull MechInformation setFaction(@NotNull Factions faction) {
        this.faction = faction;
        return this;
    }

}
