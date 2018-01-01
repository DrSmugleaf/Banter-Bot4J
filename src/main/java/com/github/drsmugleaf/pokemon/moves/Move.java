package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.EventDispatcher;
import com.github.drsmugleaf.pokemon.events.PokemonMoveEvent;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.types.Type;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 22/06/2017.
 */
public class Move {

    @Nonnull
    private final BaseMove BASE_MOVE;

    @Nonnull
    private Type type;

    @Nonnull
    private Category category;

    @Nonnull
    private Integer pp;

    @Nonnull
    private Integer power;

    @Nonnull
    private Integer priority;

    private double damageMultiplier = 1.0;

    public Move(@Nonnull BaseMove baseMove) {
        BASE_MOVE = baseMove;
        type = baseMove.TYPE;
        category = baseMove.CATEGORY;
        pp = baseMove.PP;
        power = baseMove.POWER;
        priority = baseMove.PRIORITY;
    }

    private Move(@Nonnull Move move) {
        BASE_MOVE = move.getBaseMove();
        type = move.getType();
        category = move.getCategory();
        pp = move.getPP();
        power = move.getPower();
        priority = move.getPriority();
    }

    @Nonnull
    public BaseMove getBaseMove() {
        return BASE_MOVE;
    }

    @Nonnull
    public Type getType() {
        return type;
    }

    protected void setType(@Nonnull Type type) {
        this.type = type;
    }

    @Nonnull
    public Category getCategory() {
        return category;
    }

    protected void setCategory(@Nonnull Category category) {
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

    @Nonnull
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

    protected void incrementDamageMultiplier(double multiplier) {
        setDamageMultiplier(damageMultiplier + multiplier);
    }

    protected void decreaseDamageMultiplier(double multiplier) {
        incrementDamageMultiplier(-multiplier);
    }

    protected void resetDamageMultiplier() {
        damageMultiplier = 1.0;
    }

    protected int use(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        PokemonMoveEvent event = new PokemonMoveEvent(attacker, this);
        EventDispatcher.dispatch(event);
        action.setHit(defender, true);
        return getBaseMove().use(attacker, defender, attacker.getBattle(), action);
    }

    protected int useAsZMove(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        attacker.ITEM.remove();
        return getBaseMove().useAsZMove(attacker, defender, attacker.getBattle(), action);
    }

    protected int replaceAsZMove(@Nonnull Action oldAction, @Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        Action action = attacker.getBattle().replaceAction(oldAction, this, attacker, defender);
        return useAsZMove(attacker, defender, action);
    }

    protected int miss(@Nonnull Pokemon defender, @Nonnull Action action) {
        action.setHit(defender, false);
        return getBaseMove().miss(defender);
    }

    protected int tryUse(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        if (getBaseMove().hits(defender, action)) {
            decreasePP(1);
            return use(attacker, defender, action);
        } else {
            return miss(defender, action);
        }
    }

    protected int tryUse(@Nonnull Pokemon target, @Nonnull Action action) {
        if (getBaseMove().hits(target, action)) {
            return use(action.getAttacker(), target, action);
        } else {
            return miss(target, action);
        }
    }

    public void useAsReflect(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Battle battle, @Nonnull Action action) {
        BASE_MOVE.use(attacker, defender, battle, action);
    }

}
