package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    @Nonnull
    private final Move MOVE;

    @Nonnull
    private final Pokemon ATTACKER;

    @Nonnull
    private final List<Pokemon> DEFENDERS = new ArrayList<>();

    @Nonnull
    private Pokemon TARGET;

    @Nonnull
    private Map<Pokemon, Integer> DAMAGE = new LinkedHashMap<>();

    @Nonnull
    private Map<Pokemon, Boolean> CRITICAL = new LinkedHashMap<>();

    @Nonnull
    private Map<Pokemon, Boolean> HIT = new LinkedHashMap<>();

    @Nonnull
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();

    @Nonnull
    private final List<BaseVolatileStatus> TARGET_VOLATILE_STATUSES = new ArrayList<>();

    private final int TURN;

    Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target, int turn) {
        super(move.getBaseMove());

        MOVE = move;
        ATTACKER = attacker;
        TARGET = target;

        ATTACKER_VOLATILE_STATUSES.addAll(attacker.getVolatileStatuses().keySet());
        TARGET_VOLATILE_STATUSES.addAll(target.getVolatileStatuses().keySet());

        TURN = turn;
    }

    @Nonnull
    public Move getMove() {
        return MOVE;
    }

    @Nonnull
    public Pokemon getAttacker() {
        return ATTACKER;
    }

    @Nonnull
    public List<Pokemon> getDefenders() {
        return DEFENDERS;
    }

    protected void addDefender(@Nonnull Pokemon pokemon) {
        DEFENDERS.add(pokemon);
    }

    @Nonnull
    public Pokemon getTarget() {
        return TARGET;
    }

    protected void setTarget(@Nonnull Pokemon target) {
        TARGET = target;
    }

    @Nullable
    public Integer getDamage(@Nonnull Pokemon pokemon) {
        return DAMAGE.get(pokemon);
    }

    protected void setDamage(@Nonnull Pokemon pokemon, int damage) {
        DAMAGE.put(pokemon, damage);
    }

    @Nullable
    public Boolean isCritical(@Nonnull Pokemon pokemon) {
        return CRITICAL.get(pokemon);
    }

    protected void setCritical(@Nonnull Pokemon pokemon, boolean bool) {
        CRITICAL.put(pokemon, bool);
    }

    @Nullable
    public Boolean hit(@Nonnull Pokemon pokemon) {
        return HIT.get(pokemon);
    }

    protected void setHit(@Nonnull Pokemon pokemon, boolean bool) {
        HIT.put(pokemon, bool);
    }

    @Nonnull
    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    protected boolean attackerHasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return ATTACKER_VOLATILE_STATUSES.contains(status);
    }

    @Nonnull
    public List<BaseVolatileStatus> getTargetVolatileStatuses() {
        return TARGET_VOLATILE_STATUSES;
    }

    protected boolean targetHasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return TARGET_VOLATILE_STATUSES.contains(status);
    }

    public int getTurn() {
        return TURN;
    }

    @Override
    public int getPP() {
        return MOVE.getPP();
    }

    @Override
    protected void setPP(int pp) {
        MOVE.setPP(pp);
    }

    @Override
    protected void increasePP(int amount) {
        MOVE.increasePP(amount);
    }

    @Override
    protected void decreasePP(int amount) {
        MOVE.decreasePP(amount);
    }

    @Override
    protected int use(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        return super.use(attacker, defender, action);
    }

    protected void tryUse() {
        super.tryUse(ATTACKER, TARGET, this);
    }

    protected void reflect() {
        Action action = new Action(MOVE, TARGET, ATTACKER, TARGET.getBattle().getTurn());
        action.MOVE.getBaseMove().use(action.ATTACKER, action.TARGET, action.ATTACKER.getBattle(), action);
    }

}
