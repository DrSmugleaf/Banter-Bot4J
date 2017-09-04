package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public abstract class Cycle implements Runnable {

    private final ScheduledExecutorService TIMER = Executors.newScheduledThreadPool(1);
    private Future<?> CURRENT_TIMER = null;
    private final Setup SETUP;
    private Phase PHASE;
    private int day = 1;
    private int timeLeft;

    Cycle(@Nonnull Setup setup, @Nonnull Phase phase) {
        this.SETUP = setup;
        this.PHASE = phase;
        this.timeLeft = setup.getSettings().getDayLength();
    }

    @Nonnull
    public Setup getSetup() {
        return this.SETUP;
    }

    @Nonnull
    public Phase getPhase() {
        return this.PHASE;
    }

    protected void setPhase(@Nonnull Phase phase) {
        this.PHASE = phase;
    }

    public int getDay() {
        return this.day;
    }

    protected void setDay(int day) {
        this.day = day;
    }

    public int getTimeLeft() {
        return this.timeLeft;
    }

    protected void resume() {
        this.CURRENT_TIMER = this.TIMER.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    protected void pause() {
        if (this.CURRENT_TIMER != null) {
            this.CURRENT_TIMER.cancel(true);
        }
    }

    @Override
    public void run() {
        this.timeLeft--;

        if (this.timeLeft <= 0) {
            this.CURRENT_TIMER.cancel(true);

            switch (this.PHASE) {
                case DAY:
                    this.PHASE = Phase.NIGHT;
                    this.timeLeft = this.getSetup().getSettings().getNightLength();
                    break;
                case NIGHT:
                    this.PHASE = Phase.DAY;
                    this.day++;
                    this.timeLeft = this.getSetup().getSettings().getDayLength();
                    break;
            }

            this.CURRENT_TIMER = this.TIMER.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
        }
    }

    protected abstract void changePhase(Phase phase);

}
