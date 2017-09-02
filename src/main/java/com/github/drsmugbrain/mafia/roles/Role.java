package com.github.drsmugbrain.mafia.roles;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Role {

    private final Roles BASE_ROLE;
    private final Ability ABILITY;
    private final List<Crime> CRIMES = new ArrayList<>();

    public Role(@Nonnull Roles role) {
        this.BASE_ROLE = role;
        this.ABILITY = new Ability(role.getAbility());
    }

    @Nonnull
    public Roles getBaseRole() {
        return this.BASE_ROLE;
    }

    @Nonnull
    public Ability getAbility() {
        return this.ABILITY;
    }

    protected void useAbility(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
        this.getAbility().use(game, player, target1, target2);
        this.setCrimes();
    }

    public List<Crime> getCrimes() {
        return this.CRIMES;
    }

    private void setCrimes() {
        this.CRIMES.addAll(Crime.getCrimes(this.getBaseRole()));
    }

}
