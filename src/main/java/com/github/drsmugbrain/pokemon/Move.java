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

    protected int use(@Nonnull Pokemon user, @Nonnull Pokemon target, @Nonnull Battle battle, @Nonnull Trainer trainer) {
        PokemonMoveEvent event = new PokemonMoveEvent(user, this);
        EventDispatcher.dispatch(event);
        this.pp--;
        return this.getBaseMove().use(user, target, battle, trainer, this);
    }

    protected int useAsZMove(@Nonnull Pokemon user, @Nonnull Pokemon target, @Nonnull Battle battle, @Nonnull Trainer trainer) {
        PokemonMoveEvent event = new PokemonMoveEvent(user, this);
        EventDispatcher.dispatch(event);
        this.pp--;
        user.removeItem();
        return this.getBaseMove().useAsZMove(user, target, battle, trainer, this);
    }

    protected int fail(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
        return this.getBaseMove().fail(user, target, battle, trainer, this);
    }

    protected int tryUse(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
        if (this.getBaseMove().hits(user, target, this)) {
            return this.use(user, target, battle, trainer);
        } else {
            return this.fail(user, target, battle, trainer);
        }
    }

}
