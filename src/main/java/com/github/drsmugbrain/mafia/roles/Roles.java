package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Roles {

    BODYGUARD("Bodyguard", Teams.TOWN, Categories.TOWN_PROTECTIVE, Categories.TOWN_KILLING),
    BUS_DRIVER("Bus Driver", Teams.TOWN, Categories.TOWN_PROTECTIVE, Categories.TOWN_POWER),
    CITIZEN("Citizen", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    CORONER("Coroner", Teams.TOWN, Categories.TOWN_INVESTIGATIVE),
    CRIER("Crier", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    DETECTIVE("Detective", Teams.TOWN, Categories.TOWN_INVESTIGATIVE),
    DOCTOR("Doctor", Teams.TOWN, Categories.TOWN_PROTECTIVE),
    ESCORT("Escort", Teams.TOWN, Categories.TOWN_PROTECTIVE),
    INVESTIGATOR("Investigator", Teams.TOWN, Categories.TOWN_INVESTIGATIVE),
    JAILOR("Jailor", Teams.TOWN, Categories.TOWN_KILLING, Categories.TOWN_POWER),
    LOOKOUT("Lookout", Teams.TOWN, Categories.TOWN_INVESTIGATIVE),
    MARSHALL("Marshall", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    MASON("Mason", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    MASON_LEADER("Mason Leader", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    MAYOR("Mayor", Teams.TOWN, Categories.TOWN_GOVERNMENT),
    SHERIFF("Sheriff", Teams.TOWN, Categories.TOWN_INVESTIGATIVE),
    SPY("Spy", Teams.TOWN, Categories.TOWN_POWER),
    STUMP("Stump", Teams.TOWN),
    VETERAN("Veteran", Teams.TOWN, Categories.TOWN_KILLING, Categories.TOWN_POWER),
    VIGILANTE("Vigilante", Teams.TOWN, Categories.TOWN_KILLING),

    AGENT("Agent", Teams.MAFIA, Categories.MAFIA_SUPPORT),
    BEGUILER("Beguiler", Teams.MAFIA, Categories.MAFIA_DECEPTION),
    BLACKMAILER("Blackmailer", Teams.MAFIA, Categories.MAFIA_SUPPORT),
    CONSIGLIERE("Consigliere", Teams.MAFIA, Categories.MAFIA_SUPPORT),
    CONSORT("Consort", Teams.MAFIA, Categories.MAFIA_SUPPORT),
    DISGUISER("Disguiser", Teams.MAFIA, Categories.MAFIA_DECEPTION, Categories.MAFIA_KILLING),
    FRAMER("Framer", Teams.MAFIA, Categories.MAFIA_DECEPTION),
    GODFATHER("Godfather", Teams.MAFIA, Categories.MAFIA_KILLING),
    JANITOR("Janitor", Teams.MAFIA, Categories.MAFIA_DECEPTION),
    KIDNAPPER("Kidnapper", Teams.MAFIA, Categories.MAFIA_KILLING, Categories.MAFIA_SUPPORT),
    MAFIOSO("Mafioso", Teams.MAFIA, Categories.MAFIA_KILLING),

    ADMINISTRATOR("Administrator", Teams.TRIAD, Categories.TRIAD_SUPPORT),
    DECEIVER("Deceiver", Teams.TRIAD, Categories.TRIAD_DECEPTION),
    DRAGON_HEAD("Head", Teams.TRIAD, Categories.TRIAD_KILLING),
    ENFORCER("Enforcer", Teams.TRIAD, Categories.TRIAD_KILLING),
    FORGER("Forger", Teams.TRIAD, Categories.TRIAD_DECEPTION),
    INCENSE_MASTER("Master", Teams.TRIAD, Categories.TRIAD_DECEPTION),
    INFORMANT("Informant", Teams.TRIAD, Categories.TRIAD_DECEPTION, Categories.TRIAD_KILLING),
    INTERROGATOR("Interrogator", Teams.TRIAD, Categories.TRIAD_KILLING, Categories.TRIAD_SUPPORT),
    LIAISON("Liaison", Teams.TRIAD, Categories.TRIAD_SUPPORT),
    SILENCER("Silencer", Teams.TRIAD, Categories.TRIAD_SUPPORT),
    VANGUARD("Vanguard", Teams.TRIAD, Categories.TRIAD_SUPPORT),

    AMNESIAC("Amnesiac", Teams.NEUTRAL, Categories.NEUTRAL_BENIGN),
    ARSONIST("Arsonist", Teams.NEUTRAL, Categories.NEUTRAL_EVIL, Categories.NEUTRAL_KILLING),
    AUDITOR("Auditor", Teams.NEUTRAL, Categories.NEUTRAL_EVIL),
    CULTIST("Cultist", Teams.NEUTRAL, Categories.NEUTRAL_EVIL),
    EXECUTIONER("Executioner", Teams.NEUTRAL, Categories.NEUTRAL_BENIGN),
    JESTER("Jester", Teams.NEUTRAL, Categories.NEUTRAL_BENIGN),
    JUDGE("Judge", Teams.NEUTRAL),
    MASS_MURDERER("Murderer", Teams.NEUTRAL, Categories.NEUTRAL_EVIL, Categories.NEUTRAL_KILLING),
    SCUMBAG("Scumbag", Teams.NEUTRAL, Categories.NEUTRAL_EVIL),
    SERIAL_KILLER("Killer", Teams.NEUTRAL, Categories.NEUTRAL_EVIL, Categories.NEUTRAL_KILLING),
    SURVIVOR("Survivor", Teams.NEUTRAL, Categories.NEUTRAL_BENIGN),
    WITCH("Witch", Teams.NEUTRAL, Categories.NEUTRAL_EVIL),
    WITCH_DOCTOR("Witch Doctor", Teams.NEUTRAL, Categories.NEUTRAL_EVIL);

    static {
        for (Roles roles : Roles.values()) {
            roles.setAbility(Abilities.getAbility(roles.getName()));
        }
    }

    private final String NAME;
    private final Teams TEAM;
    private final List<Categories> CATEGORY = new ArrayList<>();
    private Abilities ABILITY;

    Roles(@Nonnull String name, @Nonnull Teams team, @Nonnull Categories... categories) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        this.TEAM = team;
        Collections.addAll(this.CATEGORY, categories);
    }

    public static Roles getRole(@Nonnull String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Teams getTeam() {
        return this.TEAM;
    }

    @Nonnull
    public List<Categories> getCategories() {
        return this.CATEGORY;
    }

    @Nonnull
    public Abilities getAbility() {
        return this.ABILITY;
    }

    private void setAbility(@Nonnull Abilities ability) {
        this.ABILITY = ability;
    }

    private static class Holder {
        static Map<String, Roles> MAP = new HashMap<>();
    }

}
