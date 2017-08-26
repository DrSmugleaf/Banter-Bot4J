package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class Cycle implements Runnable {

    private final ScheduledExecutorService TIMER = Executors.newScheduledThreadPool(1);
    private Future<?> CURRENT_TIMER = null;
    private Phase CYCLE;
    private int day;
    private int timeLeft;

    public Cycle(@Nonnull Phase phase) {
        this.CYCLE = phase;
    }

    public Phase getCycle() {
        return this.CYCLE;
    }

    protected void setCycle(Phase cycle) {
        this.CYCLE = cycle;
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
    }
}
