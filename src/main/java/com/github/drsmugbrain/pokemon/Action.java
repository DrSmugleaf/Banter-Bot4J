package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    @Nonnull
    private final Move MOVE;

    @Nonnull
    private final Pokemon ATTACKER;

    @Nonnull
    private Pokemon DEFENDER;

    @Nullable
    private Integer DAMAGE;

    @Nullable
    private Boolean CRITICAL;

    @Nonnull
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();

    @Nonnull
    private final List<BaseVolatileStatus> DEFENDER_VOLATILE_STATUSES = new ArrayList<>();

    private final int TURN;

    Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon defender, int turn) {
        super(move.getBaseMove());

        MOVE = move;
        ATTACKER = attacker;
        DEFENDER = defender;

        ATTACKER_VOLATILE_STATUSES.addAll(attacker.getVolatileStatuses().keySet());
        DEFENDER_VOLATILE_STATUSES.addAll(defender.getVolatileStatuses().keySet());

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
    public Pokemon getDefender() {
        return DEFENDER;
    }

    protected void setDefender(@Nonnull Pokemon pokemon) {
        DEFENDER = pokemon;
    }

    @Nullable
    public Integer getDamage() {
        return DAMAGE;
    }

    protected void setDamage(int damage) {
        DAMAGE = damage;
    }

    @Nullable
    public Boolean isCritical() {
        return CRITICAL;
    }

    protected void setCritical(boolean bool) {
        CRITICAL = bool;
    }

    @Nonnull
    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    @Nonnull
    public List<BaseVolatileStatus> getDefenderVolatileStatuses() {
        return DEFENDER_VOLATILE_STATUSES;
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

    protected void tryUse() {
        super.tryUse(ATTACKER, DEFENDER, this);
    }
}
