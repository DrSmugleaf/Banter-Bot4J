package com.github.drsmugbrain.pokemon.moves;

import com.github.drsmugbrain.pokemon.pokemon.Pokemon;
import com.github.drsmugbrain.pokemon.events.EventDispatcher;
import com.github.drsmugbrain.pokemon.events.PokemonMoveEvent;
import com.github.drsmugbrain.pokemon.types.Type;

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

    public Move(BaseMove baseMove) {
        this.BASE_MOVE = baseMove;
        this.type = baseMove.getType();
        this.category = baseMove.getCategory();
        this.pp = baseMove.getPP();
        this.power = baseMove.getBasePower();
        this.priority = baseMove.getPriority();
    }

    private Move(Move move) {
        BASE_MOVE = move.getBaseMove();
        type = move.getType();
        category = move.getCategory();
        pp = move.getPP();
        power = move.getPower();
        priority = move.getPriority();
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

    protected int use(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        PokemonMoveEvent event = new PokemonMoveEvent(attacker, this);
        EventDispatcher.dispatch(event);
        action.setHit(defender, true);
        return getBaseMove().use(attacker, defender, attacker.getBattle(), action);
    }

    protected int useAsZMove(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        attacker.removeItem();
        return getBaseMove().useAsZMove(attacker, defender, attacker.getBattle(), action);
    }

    protected int useAsZMove(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        Action action = new Action(this, attacker, defender, attacker.getBattle().getTurn());
        return useAsZMove(attacker, defender, action);
    }

    protected int miss(@Nonnull Pokemon defender, @Nonnull Action action) {
        action.setHit(defender, false);
        return getBaseMove().miss(defender);
    }

    protected int tryUse(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
        if (getBaseMove().hits(action)) {
            decreasePP(1);
            return use(attacker, defender, action);
        } else {
            return miss(defender, action);
        }
    }

    protected int tryUse(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        Action action = new Action(this, attacker, defender, attacker.getBattle().getTurn());

        if (getBaseMove().hits(action)) {
            return use(attacker, defender, action);
        } else {
            return miss(defender, action);
        }
    }

}
