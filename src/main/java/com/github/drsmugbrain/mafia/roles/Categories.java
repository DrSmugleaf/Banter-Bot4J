package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Categories {

    TOWN_INVESTIGATIVE("Town Investigative", Teams.TOWN),
    TOWN_PROTECTIVE("Town Protective", Teams.TOWN),
    TOWN_GOVERNMENT("Town Government", Teams.TOWN),
    TOWN_KILLING("Town Killing", Teams.TOWN),
    TOWN_POWER("Town Power", Teams.TOWN),

    MAFIA_DECEPTION("Mafia Deception", Teams.MAFIA),
    MAFIA_KILLING("Mafia Killing", Teams.MAFIA),
    MAFIA_SUPPORT("Mafia Support", Teams.MAFIA),

    TRIAD_DECEPTION("Triad Deception", Teams.TRIAD),
    TRIAD_KILLING("Triad Killing", Teams.TRIAD),
    TRIAD_SUPPORT("Triad Support", Teams.TRIAD),

    NEUTRAL_BENIGN("Neutral Benign", Teams.NEUTRAL),
    NEUTRAL_EVIL("Neutral Evil", Teams.NEUTRAL),
    NEUTRAL_KILLING("Neutral Killing", Teams.NEUTRAL);

    private final String NAME;
    private final Teams TEAM;

    Categories(@Nonnull String name, @Nonnull Teams team) {
        this.NAME = name;
        this.TEAM = team;
    }

    public String getName() {
        return this.NAME;
    }

    public Teams getTeam() {
        return this.TEAM;
    }

}
