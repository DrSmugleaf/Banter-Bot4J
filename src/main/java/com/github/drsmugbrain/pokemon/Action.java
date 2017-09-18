package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    private final Move MOVE;
    private final Pokemon ATTACKER;
    private final Pokemon DEFENDER;
    private int DAMAGE;
    private boolean CRITICAL;
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();
    private final List<BaseVolatileStatus> DEFENDER_VOLATILE_STATUSES = new ArrayList<>();

    Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Integer damage, @Nonnull Boolean critical) {
        super(move.getBaseMove());
        this.MOVE = move;
        this.ATTACKER = attacker;
        this.DEFENDER = defender;
        this.DAMAGE = damage;
        this.CRITICAL = critical;
        for (VolatileStatus volatileStatus : attacker.getVolatileStatuses()) {
            this.ATTACKER_VOLATILE_STATUSES.add(volatileStatus.getBaseVolatileStatus());
        }
        for (VolatileStatus volatileStatus : defender.getVolatileStatuses()) {
            this.DEFENDER_VOLATILE_STATUSES.add(volatileStatus.getBaseVolatileStatus());
        }
    }

    public Move getMove() {
        return this.MOVE;
    }

    public Pokemon getAttacker() {
        return this.ATTACKER;
    }

    public Pokemon getDefender() {
        return this.DEFENDER;
    }

    public Integer getDamage() {
        return this.DAMAGE;
    }

    public boolean isCritical() {
        return this.CRITICAL;
    }

    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return this.ATTACKER_VOLATILE_STATUSES;
    }

    public List<BaseVolatileStatus> getDefenderVolatileStatuses() {
        return this.DEFENDER_VOLATILE_STATUSES;
    }

}
