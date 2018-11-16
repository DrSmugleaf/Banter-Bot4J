package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.EventDispatcher;
import com.github.drsmugleaf.pokemon.events.PokemonMoveEvent;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.types.Type;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 22/06/2017.
 */
public class Move {

    @NotNull
    public final BaseMove BASE_MOVE;

    @NotNull
    private Type type;

    @NotNull
    private MoveCategory category;

    private int pp;
    private int power;
    private int priority;
    private double damageMultiplier = 1.0;

    public Move(@NotNull BaseMove baseMove) {
        BASE_MOVE = baseMove;
        type = baseMove.TYPE;
        category = baseMove.CATEGORY;
        pp = baseMove.PP;
        power = baseMove.POWER;
        priority = baseMove.PRIORITY;
    }

    private Move(@NotNull Move move) {
        BASE_MOVE = move.BASE_MOVE;
        type = move.type;
        category = move.category;
        pp = move.pp;
        power = move.power;
        priority = move.priority;
        damageMultiplier = move.damageMultiplier;
    }

    @NotNull
    public BaseMove getBaseMove() {
        return BASE_MOVE;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    protected void setType(@NotNull Type type) {
        this.type = type;
    }

    @NotNull
    public MoveCategory getCategory() {
        return category;
    }

    protected void setCategory(@NotNull MoveCategory category) {
        this.category = category;
    }

    public int getPP() {
        return pp;
    }

    public void setPP(int pp) {
        this.pp = pp;
    }

    public void increasePP(int amount) {
        setPP(pp + amount);
    }

    public void decreasePP(int amount) {
        increasePP(-amount);
    }

    public int getPower() {
        return power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    @NotNull
    public Integer getPriority() {
        return priority;
    }

    protected void setPriority(int priority) {
        this.priority = priority;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    protected void setDamageMultiplier(double multiplier) {
        damageMultiplier = multiplier;
    }

    protected void increaseDamageMultiplier(double multiplier) {
        setDamageMultiplier(damageMultiplier + multiplier);
    }

    protected void decreaseDamageMultiplier(double multiplier) {
        increaseDamageMultiplier(-multiplier);
    }

    protected void resetDamageMultiplier() {
        damageMultiplier = 1.0;
    }

    protected int use(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        PokemonMoveEvent event = new PokemonMoveEvent(attacker, this);
        EventDispatcher.dispatch(event);
        action.setHit(defender, true);
        return BASE_MOVE.use(attacker, defender, attacker.getBattle(), action);
    }

    protected int useAsZMove(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        attacker.ITEM.remove();
        return BASE_MOVE.useAsZMove(attacker, defender, attacker.getBattle(), action);
    }

    protected int replaceAsZMove(@NotNull Action oldAction, @NotNull Pokemon attacker, @NotNull Pokemon defender) {
        Action action = attacker.getBattle().replaceAction(oldAction, this, attacker, defender);
        return useAsZMove(attacker, defender, action);
    }

    protected int miss(@NotNull Pokemon defender, @NotNull Action action) {
        action.setHit(defender, false);
        return BASE_MOVE.miss(defender);
    }

    protected int tryUse(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        if (BASE_MOVE.hits(defender, action)) {
            decreasePP(1);
            return use(attacker, defender, action);
        } else {
            return miss(defender, action);
        }
    }

    protected int tryUse(@NotNull Pokemon target, @NotNull Action action) {
        if (BASE_MOVE.hits(target, action)) {
            return use(action.getAttacker(), target, action);
        } else {
            return miss(target, action);
        }
    }

    public void useAsReflect(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Battle battle, @NotNull Action action) {
        BASE_MOVE.use(attacker, defender, battle, action);
    }

}
