package com.github.drsmugbrain.mafia.roles;

import com.github.drsmugbrain.mafia.Abilities;
import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public class Ability {

    private final Abilities ABILITY;
    private int usesLeft;

    public Ability(@Nonnull Abilities ability) {
        this.ABILITY = ability;
    }

    @Nonnull
    protected Abilities getBaseAbility() {
        return this.ABILITY;
    }

    public int getUsesLeft() {
        return this.usesLeft;
    }

    protected void setUsesLeft(int amount) {
        this.usesLeft = amount;
    }

    public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
        this.getBaseAbility().use(game, player, target1, target2);
    }

}
