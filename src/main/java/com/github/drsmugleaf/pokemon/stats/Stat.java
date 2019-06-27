package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.pokemon.Species;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class Stat implements IStat {

    public final IStat STAT;
    public final int IV;
    public final int EV;
    private Stage STAGE = Stage.ZERO;

    protected Stat(IStat stat, int iv, int ev) {
        STAT = stat;
        IV = iv;
        EV = ev;
    }

    @Contract(pure = true)
    public IStat getStat() {
        return STAT;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return STAT.getName();
    }

    @Contract(pure = true)
    public String getAbbreviation() {
        return STAT.getAbbreviation();
    }

    @Contract(pure = true)
    public int getIV() {
        return IV;
    }

    @Contract(pure = true)
    public int getEV() {
        return EV;
    }

    @Contract(pure = true)
    public Stage getStage() {
        return STAGE;
    }

    public void setStage(Stage stage) {
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

    public int getBase(Species species) {
        if (STAT instanceof PermanentStat) {
            return species.getStats().get(STAT);
        }

        return 1;
    }

    public double calculate(Pokemon pokemon) {
        return STAT.calculate(pokemon);
    }

    public double calculateWithoutStages(Pokemon pokemon) {
        return STAT.calculateWithoutStages(pokemon);
    }

}
