package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    private final Move MOVE;
    private final Pokemon ATTACKER;
    private Pokemon DEFENDER;
    private Integer DAMAGE;
    private Boolean CRITICAL;
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();
    private final List<BaseVolatileStatus> DEFENDER_VOLATILE_STATUSES = new ArrayList<>();

    Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        super(move.getBaseMove());

        MOVE = move;
        ATTACKER = attacker;
        DEFENDER = defender;

        ATTACKER_VOLATILE_STATUSES.addAll(
                attacker.getVolatileStatuses().stream().map(VolatileStatus::getBaseVolatileStatus).collect(Collectors.toList())
        );
        DEFENDER_VOLATILE_STATUSES.addAll(
                defender.getVolatileStatuses().stream().map(VolatileStatus::getBaseVolatileStatus).collect(Collectors.toList())
        );
    }

    Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Integer damage, @Nonnull Boolean critical) {
        super(move.getBaseMove());
        MOVE = move;
        ATTACKER = attacker;
        DEFENDER = defender;
        DAMAGE = damage;
        CRITICAL = critical;
        for (VolatileStatus volatileStatus : attacker.getVolatileStatuses()) {
            ATTACKER_VOLATILE_STATUSES.add(volatileStatus.getBaseVolatileStatus());
        }
        for (VolatileStatus volatileStatus : defender.getVolatileStatuses()) {
            DEFENDER_VOLATILE_STATUSES.add(volatileStatus.getBaseVolatileStatus());
        }
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

    @Nonnull
    public Boolean isCritical() {
        return CRITICAL;
    }

    @Nonnull
    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    @Nonnull
    public List<BaseVolatileStatus> getDefenderVolatileStatuses() {
        return DEFENDER_VOLATILE_STATUSES;
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
        super.tryUse(ATTACKER, DEFENDER);
    }
}
