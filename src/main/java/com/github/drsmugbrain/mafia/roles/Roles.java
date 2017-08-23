package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Roles {

    BODYGUARD("Bodyguard", Teams.TOWN),
    BUS_DRIVER("Bus Driver", Teams.TOWN),
    CITIZEN("Citizen", Teams.TOWN),
    CORONER("Coroner", Teams.TOWN),
    CRIER("Crier", Teams.TOWN),
    DETECTIVE("Detective", Teams.TOWN),
    DOCTOR("Doctor", Teams.TOWN),
    ESCORT("Escort", Teams.TOWN),
    INVESTIGATOR("Investigator", Teams.TOWN),
    JAILOR("Jailor", Teams.TOWN),
    LOOKOUT("Lookout", Teams.TOWN),
    MARSHALL("Marshall", Teams.TOWN),
    MASON("Mason", Teams.TOWN),
    MASON_LEADER("Mason Leader", Teams.TOWN),
    MAYOR("Mayor", Teams.TOWN),
    SHERIFF("Sheriff", Teams.TOWN),
    SPY("Spy", Teams.TOWN),
    STUMP("Stump", Teams.TOWN),
    VETERAN("Veteran", Teams.TOWN),
    VIGILANTE("Vigilante", Teams.TOWN),

    AGENT("Agent", Teams.MAFIA),
    BEGUILER("Beguiler", Teams.MAFIA),
    BLACKMAILER("Blackmailer", Teams.MAFIA),
    CONSIGLIERE("Consigliere", Teams.MAFIA),
    CONSORT("Consort", Teams.MAFIA),
    DISGUISER("Disguiser", Teams.MAFIA),
    FRAMER("Framer", Teams.MAFIA),
    GODFATHER("Godfather", Teams.MAFIA),
    JANITOR("Janitor", Teams.MAFIA),
    KIDNAPPER("Kidnapper", Teams.MAFIA),
    MAFIOSO("Mafioso", Teams.MAFIA),

    ADMINISTRATOR("Administrator", Teams.TRIAD),
    DECEIVER("Deceiver", Teams.TRIAD),
    DRAGON_HEAD("Head", Teams.TRIAD),
    ENFORCER("Enforcer", Teams.TRIAD),
    FORGER("Forger", Teams.TRIAD),
    INCENSE_MASTER("Master", Teams.TRIAD),
    INFORMANT("Informant", Teams.TRIAD),
    INTERROGATOR("Interrogator", Teams.TRIAD),
    LIAISON("Liaison", Teams.TRIAD),
    SILENCER("Silencer", Teams.TRIAD),
    VANGUARD("Vanguard", Teams.TRIAD),

    AMNESIAC("Amnesiac", Teams.NEUTRAL),
    ARSONIST("Arsonist", Teams.NEUTRAL),
    AUDITOR("Auditor", Teams.NEUTRAL),
    CULTIST("Cultist", Teams.NEUTRAL),
    EXECUTIONER("Executioner", Teams.NEUTRAL),
    JESTER("Jester", Teams.NEUTRAL),
    JUDGE("Judge", Teams.NEUTRAL),
    MASS_MURDERER("Murderer", Teams.NEUTRAL),
    SCUMBAG("Scumbag", Teams.NEUTRAL),
    SERIAL_KILLER("Killer", Teams.NEUTRAL),
    SURVIVOR("Survivor", Teams.NEUTRAL),
    WITCH("Witch", Teams.NEUTRAL),
    WITCH_DOCTOR("Witch Doctor", Teams.NEUTRAL);

    private final String NAME;
    private final Teams TEAM;

    Roles(@Nonnull String name, @Nonnull Teams team) {
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
