package com.github.drsmugbrain.mafia.roles;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Role {

    private final Roles BASE_ROLE;
    private final Map<Phase, Ability> ABILITIES = new HashMap<>();
    private final List<Crime> CRIMES = new ArrayList<>();

    public Role(@Nonnull Roles role) {
        this.BASE_ROLE = role;

        role.getAbilities().forEach((phase, ability) -> {
            this.ABILITIES.put(phase, new Ability(ability));
        });
    }

    @Nonnull
    public Roles getBaseRole() {
        return this.BASE_ROLE;
    }

    @Nonnull
    public Map<Phase, Ability> getAbilities() {
        return this.ABILITIES;
    }

    public void useAbility(@Nonnull Game game, @Nonnull Phase phase, @Nonnull Player target1, Player target2, @Nonnull Player player) {
        this.getAbilities().get(phase).use(game, player, target1, target2);
        this.setCrimes();
    }

    public List<Crime> getCrimes() {
        return this.CRIMES;
    }

    private void setCrimes() {
        this.CRIMES.addAll(Crime.getCrimes(this.getBaseRole()));
    }

}
