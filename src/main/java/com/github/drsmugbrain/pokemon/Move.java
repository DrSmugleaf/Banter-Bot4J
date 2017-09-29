package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.EventDispatcher;
import com.github.drsmugbrain.pokemon.events.PokemonMoveEvent;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 22/06/2017.
 */
public class Move {

    private final BaseMove BASE_MOVE;
    private Type type;
    private Category category;
    private Integer pp;
    private Integer power;
    private Integer priority;
    private double damageMultiplier = 1.0;

    Move(BaseMove baseMove) {
        this.BASE_MOVE = baseMove;
        this.type = baseMove.getType();
        this.category = baseMove.getCategory();
        this.pp = baseMove.getPP();
        this.power = baseMove.getBasePower();
        this.priority = baseMove.getPriority();
    }

    public BaseMove getBaseMove() {
        return this.BASE_MOVE;
    }

    @Nonnull
    public Type getType() {
        return this.type;
    }

    protected void setType(Type type) {
        this.type = type;
    }

    @Nonnull
    public Category getCategory() {
        return this.category;
    }

    protected void setCategory(Category category) {
        this.category = category;
    }

    public int getPP() {
        return this.pp;
    }

    protected void setPP(int pp) {
        this.pp = pp;
    }

    protected void increasePP(int amount) {
        this.setPP(this.getPP() + amount);
    }

    protected void decreasePP(int amount) {
        this.increasePP(-amount);
    }

    public int getPower() {
        return this.power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    public Integer getPriority() {
        return this.priority;
    }

    protected void setPriority(int priority) {
        this.priority = priority;
    }

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    protected void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    protected void incrementDamageMultiplier(double multiplier) {
        this.setDamageMultiplier(this.getDamageMultiplier() + multiplier);
    }

    protected void decreaseDamageMultiplier(double multiplier) {
        this.incrementDamageMultiplier(-multiplier);
    }

    protected void resetDamageMultiplier() {
        this.damageMultiplier = 1.0;
    }

    protected int use(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        PokemonMoveEvent event = new PokemonMoveEvent(attacker, this);
        EventDispatcher.dispatch(event);
        decreasePP(1);
        return getBaseMove().use(attacker, defender, attacker.getBattle(), this);
    }

    protected int useAsZMove(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        decreasePP(1);
        attacker.removeItem();
        return getBaseMove().useAsZMove(attacker, defender, attacker.getBattle(), this);
    }

    protected int miss(@Nonnull Pokemon defender) {
        return getBaseMove().miss(defender);
    }


    protected int tryUse(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        if (getBaseMove().hits(attacker, defender, attacker.getBattle(), this)) {
            return use(attacker, defender);
        } else {
            return miss(defender);
        }
    }

}
