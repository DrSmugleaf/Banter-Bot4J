package com.github.drsmugbrain.pokemon;

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

    public int getPower() {
        return this.power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    public int getPriority() {
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

    protected void use(@Nonnull Pokemon user, Pokemon target) {
        this.pp--;
        this.getBaseMove().use(user, target);
    }

}
