package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Move extends BaseMove {

    private Type type;
    private int pp;
    private double damageMultiplier = 1.0;

    protected Move(@Nonnull BaseMove baseMove) {
        super(baseMove);
        this.type = baseMove.getType();
        this.pp = baseMove.getPP();
    }

    @Nonnull
    public Type getType() {
        return this.type;
    }

    public void setType(@Nonnull Type type) {
        this.type = type;
    }

    public int getPP() {
        return this.pp;
    }

    protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        int attackStat;
        int defenseStat;
        if (this.getCategory() == Category.PHYSICAL) {
            attackStat = attacker.getStat(Stat.ATTACK);
            defenseStat = defender.getStat(Stat.DEFENSE);
        } else if (this.getCategory() == Category.SPECIAL) {
            attackStat = attacker.getStat(Stat.SPECIAL_ATTACK);
            defenseStat = defender.getStat(Stat.SPECIAL_DEFENSE);
        } else {
            return 0;
        }
        int level = attacker.getLevel();
        int attackPower = this.getPower();
        double stabMultiplier = attacker.getStabMultiplier();
        double effectiveness = Type.getDamageMultiplier(defender.getTypes(), this.getType());
        int randomNumber = (int) (Math.random() * 100 + 85);
        return (int) (((((2 * level / 5 + 2) * attackStat * attackPower / defenseStat) / 50) + 2) * stabMultiplier * effectiveness * randomNumber / 100);
    }

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    protected void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    protected void incrementDamageMultiplier(double amount) {
        this.damageMultiplier += amount;
    }

    protected void decreaseDamageMultiplier(double amount) {
        this.damageMultiplier -= amount;
    }

    protected void use(@Nonnull Pokemon attacker, @Nullable Pokemon defender) {
        if (defender != null && this.getPower() > 0) {
            defender.damage(this.getDamage(attacker, defender));
        }
    }

}
