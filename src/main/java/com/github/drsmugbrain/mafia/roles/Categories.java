package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Categories {

    ANY_RANDOM("Any Random", Teams.NONE),

    TOWN_INVESTIGATIVE("Town Investigative", Teams.TOWN),
    TOWN_PROTECTIVE("Town Protective", Teams.TOWN),
    TOWN_GOVERNMENT("Town Government", Teams.TOWN),
    TOWN_KILLING("Town Killing", Teams.TOWN),
    TOWN_POWER("Town Power", Teams.TOWN),
    TOWN_RANDOM("Town Random", Teams.TOWN),

    MAFIA_DECEPTION("Mafia Deception", Teams.MAFIA),
    MAFIA_KILLING("Mafia Killing", Teams.MAFIA),
    MAFIA_SUPPORT("Mafia Support", Teams.MAFIA),
    MAFIA_RANDOM("Mafia Random", Teams.MAFIA),

    TRIAD_DECEPTION("Triad Deception", Teams.TRIAD),
    TRIAD_KILLING("Triad Killing", Teams.TRIAD),
    TRIAD_SUPPORT("Triad Support", Teams.TRIAD),
    TRIAD_RANDOM("Triad Random", Teams.TRIAD),

    NEUTRAL_BENIGN("Neutral Benign", Teams.NEUTRAL),
    NEUTRAL_EVIL("Neutral Evil", Teams.NEUTRAL),
    NEUTRAL_KILLING("Neutral Killing", Teams.NEUTRAL),
    NEUTRAL_RANDOM("Neutral Random", Teams.NEUTRAL);

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


        ANY_RANDOM.setRoles(Roles.values());
        TOWN_RANDOM.setRoles(TOWN_INVESTIGATIVE, TOWN_PROTECTIVE, TOWN_GOVERNMENT, TOWN_KILLING, TOWN_POWER);
        MAFIA_RANDOM.setRoles(MAFIA_DECEPTION, MAFIA_KILLING, MAFIA_SUPPORT);
        TRIAD_RANDOM.setRoles(TRIAD_DECEPTION, TRIAD_KILLING, TRIAD_SUPPORT);
        NEUTRAL_RANDOM.setRoles(NEUTRAL_BENIGN, NEUTRAL_EVIL, NEUTRAL_KILLING);
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

    private Categories addRoles(Roles... roles) {
        for (Roles role : roles) {
            if (this.ROLES.contains(role)) {
                continue;
            }
            this.ROLES.add(role);
        }
        return this;
    }

    private Categories setRoles(Roles... roles) {
        this.ROLES.clear();
        return this.addRoles(roles);
    }

    private Categories setRoles(List<Roles>... roles) {
        List<Roles> list = new ArrayList<>();
        for (List<Roles> roleList : roles) {
            list.addAll(roleList);
        }
        return this.setRoles(list.toArray(new Roles[list.size()]));
    }

    private Categories setRoles(Categories... categories) {
        List<Roles> list = new ArrayList<>();
        for (Categories category : categories) {
            list.addAll(category.getRoles());
        }
        return this.setRoles(list.toArray(new Roles[list.size()]));
    }

    public Roles random() {
        int randomIndex = ThreadLocalRandom.current().nextInt(this.ROLES.size());
        return this.ROLES.get(randomIndex);
    }

    private static class Holder {
        static Map<String, Categories> MAP = new HashMap<>();
    }

}
