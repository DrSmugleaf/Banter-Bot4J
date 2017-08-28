package com.github.drsmugbrain.mafia.settings;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class Settings {

    private int DAY_LENGTH;
    private int NIGHT_LENGTH;
    private int DISCUSSION_TIME;
    private int TRIAL_LENGTH;
    private DayType DAY_TYPE;
    private boolean LAST_WILL_ALLOWED;
    private GameStart GAME_START;
    private NightTimeActions NIGHT_TIME_ACTIONS;
    private boolean PM_ALLOWED;
    private boolean DISCUSSION_PHASE;
    private boolean TRIAL_PAUSES_DAY;
    private boolean TRIAL_DEFENSE;
    private boolean CHOOSE_NAMES;

    public Settings() {}

    public static Settings getDefault() {
        Settings settings = new Settings();

        settings
                .setDayLength(120)
                .setNightLength(60)
                .setDiscussionTime(60)
                .setTrialLength(60)
                .setDayType(DayType.TRIAL)
                .setLastWillAllowed(true)
                .setGameStart(GameStart.NIGHT)
                .setNightTimeActions(NightTimeActions.NIGHT_SEQUENCE)
                .setPmAllowed(true)
                .setDiscussionPhase(true)
                .setTrialPausesDay(true)
                .setTrialDefense(true)
                .setChooseNames(false);

        return settings;
    }

    public int getDayLength() {
        return DAY_LENGTH;
    }

    public Settings setDayLength(int length) {
        this.DAY_LENGTH = length;
        return this;
    }

    public int getNightLength() {
        return NIGHT_LENGTH;
    }

    public Settings setNightLength(int length) {
        this.NIGHT_LENGTH = length;
        return this;
    }

    public int getDiscussionTime() {
        return DISCUSSION_TIME;
    }

    public Settings setDiscussionTime(int time) {
        this.DISCUSSION_TIME = time;
        return this;
    }

    public int getTrialLength() {
        return TRIAL_LENGTH;
    }

    public Settings setTrialLength(int length) {
        this.TRIAL_LENGTH = length;
        return this;
    }

    public DayType getDayType() {
        return DAY_TYPE;
    }

    public Settings setDayType(DayType type) {
        this.DAY_TYPE = type;
        return this;
    }

    public boolean isLastWillAllowed() {
        return LAST_WILL_ALLOWED;
    }

    public Settings setLastWillAllowed(boolean bool) {
        this.LAST_WILL_ALLOWED = bool;
        return this;
    }

    public GameStart getGameStart() {
        return GAME_START;
    }

    public Settings setGameStart(GameStart startAt) {
        this.GAME_START = startAt;
        return this;
    }

    public NightTimeActions getNightTimeActions() {
        return NIGHT_TIME_ACTIONS;
    }

    public Settings setNightTimeActions(NightTimeActions actions) {
        this.NIGHT_TIME_ACTIONS = actions;
        return this;
    }

    public boolean isPmAllowed() {
        return PM_ALLOWED;
    }

    public Settings setPmAllowed(boolean bool) {
        this.PM_ALLOWED = bool;
        return this;
    }

    public boolean hasDiscussionPhase() {
        return DISCUSSION_PHASE;
    }

    public Settings setDiscussionPhase(boolean bool) {
        this.DISCUSSION_PHASE = bool;
        return this;
    }

    public boolean doesTrialPauseDay() {
        return TRIAL_PAUSES_DAY;
    }

    public Settings setTrialPausesDay(boolean bool) {
        this.TRIAL_PAUSES_DAY = bool;
        return this;
    }

    public boolean hasTrialDefense() {
        return TRIAL_DEFENSE;
    }

    public Settings setTrialDefense(boolean bool) {
        this.TRIAL_DEFENSE = bool;
        return this;
    }

    public boolean canChooseNames() {
        return CHOOSE_NAMES;
    }

    public Settings setChooseNames(boolean bool) {
        this.CHOOSE_NAMES = bool;
        return this;
    }

}
