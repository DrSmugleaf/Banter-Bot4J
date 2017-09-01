package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.events.DayStartEvent;
import com.github.drsmugbrain.mafia.events.EventDispatcher;
import com.github.drsmugbrain.mafia.events.NightStartEvent;

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
    private final Game GAME;
    private Phase PHASE;
    private int day = 1;
    private int timeLeft;

    public Cycle(@Nonnull Game game, @Nonnull Phase phase) {
        this.GAME = game;
        this.PHASE = phase;
        this.timeLeft = game.getSetup().getSettings().getDayLength();
    }

    @Nonnull
    public Phase getPhase() {
        return this.PHASE;
    }

    protected void setPhase(@Nonnull Phase phase) {
        this.PHASE = phase;
    }

    @Nonnull
    public Game getGame() {
        return this.GAME;
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
                    this.timeLeft = this.GAME.getSetup().getSettings().getNightLength();
                    EventDispatcher.dispatch(new NightStartEvent(this.GAME));
                    break;
                case NIGHT:
                    this.PHASE = Phase.DAY;
                    this.day++;
                    this.timeLeft = this.GAME.getSetup().getSettings().getDayLength();
                    EventDispatcher.dispatch(new DayStartEvent(this.GAME));
                    break;
            }

            this.CURRENT_TIMER = this.TIMER.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
        }
    }

}
