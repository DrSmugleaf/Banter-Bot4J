package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.pokemon.Species;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class Stat implements IStat {

    @NotNull
    public final IStat STAT;

    public final int IV;

    public final int EV;

    @NotNull
    private Stage STAGE = Stage.ZERO;

    protected Stat(@NotNull IStat stat, int iv, int ev) {
        STAT = stat;
        IV = iv;
        EV = ev;
    }

    @NotNull
    public IStat getStat() {
        return STAT;
    }

    @NotNull
    @Override
    public String getName() {
        return STAT.getName();
    }

    @NotNull
    public String getAbbreviation() {
        return STAT.getAbbreviation();
    }

    public int getIV() {
        return IV;
    }

    public int getEV() {
        return EV;
    }

    @NotNull
    public Stage getStage() {
        return STAGE;
    }

    public void setStage(@NotNull Stage stage) {
        STAGE = stage;
    }

    public void raiseStage(int amount) {
        int currentStage = getStage().STAGE;
        Stage newStage = Stage.getStage(currentStage + amount);
        setStage(newStage);
    }

    public void lowerStage(int amount) {
        raiseStage(-amount);
    }

    public void resetStage() {
        setStage(Stage.ZERO);
    }

    public void resetLoweredStage() {
        if (STAGE.STAGE < Stage.ZERO.STAGE) {
            setStage(Stage.ZERO);
        }
    }

    public int getBase(@NotNull Species species) {
        if (STAT instanceof PermanentStat) {
            return species.getStats().get(STAT);
        }

        return 1;
    }

    public double calculate(@NotNull Pokemon pokemon) {
        return STAT.calculate(pokemon);
    }

    public double calculateWithoutStages(@NotNull Pokemon pokemon) {
        return STAT.calculateWithoutStages(pokemon);
    }

}
