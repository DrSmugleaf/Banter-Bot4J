package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

    static {
        TOWN_INVESTIGATIVE.setRoles(
                Roles.INVESTIGATOR,
                Roles.LOOKOUT,
                Roles.CORONER,
                Roles.DETECTIVE,
                Roles.SHERIFF
        );

        TOWN_PROTECTIVE.setRoles(
                Roles.BUS_DRIVER,
                Roles.BODYGUARD,
                Roles.DOCTOR,
                Roles.ESCORT
        );

        TOWN_GOVERNMENT.setRoles(
                Roles.MARSHALL,
                Roles.CRIER,
                Roles.MASON,
                Roles.MASON_LEADER,
                Roles.CITIZEN,
                Roles.MAYOR
        );

        TOWN_KILLING.setRoles(
                Roles.VIGILANTE,
                Roles.BODYGUARD,
                Roles.JAILOR,
                Roles.VETERAN
        );

        TOWN_POWER.setRoles(
                Roles.BUS_DRIVER,
                Roles.SPY,
                Roles.JAILOR,
                Roles.VETERAN
        );


        MAFIA_DECEPTION.setRoles(
                Roles.BEGUILER,
                Roles.DISGUISER,
                Roles.FRAMER,
                Roles.JANITOR
        );

        MAFIA_KILLING.setRoles(
                Roles.KIDNAPPER,
                Roles.MAFIOSO,
                Roles.DISGUISER,
                Roles.GODFATHER
        );

        MAFIA_SUPPORT.setRoles(
                Roles.KIDNAPPER,
                Roles.AGENT,
                Roles.CONSIGLIERE,
                Roles.CONSORT,
                Roles.BLACKMAILER
        );


        TRIAD_DECEPTION.setRoles(
                Roles.DECEIVER,
                Roles.FORGER,
                Roles.INCENSE_MASTER,
                Roles.INFORMANT
        );

        TRIAD_KILLING.setRoles(
                Roles.ENFORCER,
                Roles.DRAGON_HEAD,
                Roles.INFORMANT,
                Roles.INTERROGATOR
        );

        TRIAD_SUPPORT.setRoles(
                Roles.SILENCER,
                Roles.VANGUARD,
                Roles.ADMINISTRATOR,
                Roles.INTERROGATOR,
                Roles.LIAISON
        );


        NEUTRAL_BENIGN.setRoles(
                Roles.AMNESIAC,
                Roles.EXECUTIONER,
                Roles.JESTER,
                Roles.SURVIVOR
        );

        NEUTRAL_EVIL.setRoles(
                Roles.SCUMBAG,
                Roles.AUDITOR,
                Roles.ARSONIST,
                Roles.MASS_MURDERER,
                Roles.CULTIST,
                Roles.SERIAL_KILLER,
                Roles.WITCH,
                Roles.WITCH_DOCTOR
        );

        NEUTRAL_KILLING.setRoles(
                Roles.ARSONIST,
                Roles.MASS_MURDERER,
                Roles.SERIAL_KILLER
        );
    }

    private final String NAME;
    private final Teams TEAM;
    private final List<Roles> ROLES = new ArrayList<>();

    Categories(@Nonnull String name, @Nonnull Teams team) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        this.TEAM = team;
    }

    public static Categories getCategory(@Nonnull String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    public String getName() {
        return this.NAME;
    }

    public Teams getTeam() {
        return this.TEAM;
    }

    public List<Roles> getRoles() {
        return this.ROLES;
    }

    private Categories setRoles(Roles... roles) {
        Collections.addAll(this.ROLES, roles);
        return this;
    }

    public Roles random() {
        int randomIndex = ThreadLocalRandom.current().nextInt(this.ROLES.size());
        return this.ROLES.get(randomIndex);
    }

    private static class Holder {
        static Map<String, Categories> MAP = new HashMap<>();
    }

}
