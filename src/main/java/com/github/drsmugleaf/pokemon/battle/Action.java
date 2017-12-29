package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.status.BaseVolatileStatus;

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

    protected Action(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target, int turn) {
        super(move.getBaseMove());

        MOVE = move;
        ATTACKER = attacker;
        TARGET = target;

        ATTACKER_VOLATILE_STATUSES.addAll(attacker.STATUSES.getVolatileStatuses().keySet());
        TARGET_VOLATILE_STATUSES.addAll(target.STATUSES.getVolatileStatuses().keySet());

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

    public void addDefender(@Nonnull Pokemon pokemon) {
        DEFENDERS.add(pokemon);
    }

    @Nonnull
    public Pokemon getTarget() {
        return TARGET;
    }

    public void setTarget(@Nonnull Pokemon target) {
        TARGET = target;
    }

    @Nullable
    public Integer getDamage(@Nonnull Pokemon pokemon) {
        return DAMAGE.get(pokemon);
    }

    public void setDamage(@Nonnull Pokemon pokemon, int damage) {
        DAMAGE.put(pokemon, damage);
    }

    @Nullable
    public Boolean isCritical(@Nonnull Pokemon pokemon) {
        return CRITICAL.get(pokemon);
    }

    public void setCritical(@Nonnull Pokemon pokemon, boolean bool) {
        CRITICAL.put(pokemon, bool);
    }

    @Nonnull
    public Map<Pokemon, Boolean> hit() {
        return new HashMap<>(HIT);
    }

    @Nullable
    public Boolean hit(@Nonnull Pokemon pokemon) {
        return HIT.get(pokemon);
    }

    public void setHit(@Nonnull Pokemon pokemon, boolean bool) {
        HIT.put(pokemon, bool);
    }

    @Nonnull
    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    public boolean attackerHasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return ATTACKER_VOLATILE_STATUSES.contains(status);
    }

    @Nonnull
    public List<BaseVolatileStatus> getTargetVolatileStatuses() {
        return TARGET_VOLATILE_STATUSES;
    }

    public boolean targetHasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return TARGET_VOLATILE_STATUSES.contains(status);
    }

    @Nonnull
    public Battle getBattle() {
        return ATTACKER.getBattle();
    }

    public int getTurn() {
        return TURN;
    }

    @Nonnull
    public Generation getGeneration() {
        return ATTACKER.getBattle().getGeneration();
    }

    @Override
    public int getPP() {
        return MOVE.getPP();
    }

    @Override
    public void setPP(int pp) {
        MOVE.setPP(pp);
    }

    @Override
    public void increasePP(int amount) {
        MOVE.increasePP(amount);
    }

    @Override
    public void decreasePP(int amount) {
        MOVE.decreasePP(amount);
    }

    @Override
    protected int use(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        return super.use(attacker, defender, action);
    }

    public void tryUse() {
        super.tryUse(ATTACKER, TARGET, this);
    }

    public void reflect() {
        Action action = getBattle().replaceAction(this, MOVE, TARGET, ATTACKER);
        action.MOVE.useAsReflect(action.ATTACKER, action.TARGET, action.ATTACKER.getBattle(), action);
    }

}
