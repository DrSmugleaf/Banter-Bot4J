package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public enum Crime {

    TRESPASSING("Trespassing"),
    KIDNAPPING("Kidnapping"),
    NO_CRIME("No Crime"),
    CORRUPTION("Corruption"),
    IDENTITY_THEFT("Identity Theft"),
    SOLICITING("Soliciting"),
    MURDER("Murder"),
    DISTURBING_THE_PEACE("Disturbing the peace"),
    CONSPIRACY("Conspiracy"),
    DESTRUCTION_OF_PROPERTY("Destruction of property"),
    ARSON("Arson");

    private final String NAME;
    private static final Map<Roles, List<Crime>> ROLE_CRIMES = new HashMap<>();

    static {
        Crime.TRESPASSING.setRoles(
                Roles.INVESTIGATOR,
                Roles.SPY,
                Roles.VIGILANTE,
                Roles.MASON_LEADER,
                Roles.DETECTIVE,
                Roles.LOOKOUT,
                Roles.MAFIOSO,
                Roles.GODFATHER,
                Roles.CONSIGLIERE,
                Roles.FRAMER,
                Roles.JANITOR,
                Roles.AGENT,
                Roles.BEGUILER,
                Roles.SERIAL_KILLER,
                Roles.ARSONIST,
                Roles.MASS_MURDERER,
                Roles.ADMINISTRATOR,
                Roles.DECEIVER,
                Roles.DRAGON_HEAD,
                Roles.ENFORCER,
                Roles.FORGER,
                Roles.INCENSE_MASTER,
                Roles.VANGUARD
        );

        Crime.KIDNAPPING.setRoles(
                Roles.JAILOR,
                Roles.KIDNAPPER,
                Roles.INTERROGATOR
        );

        Crime.NO_CRIME.setRoles(
                Roles.CITIZEN,
                Roles.SHERIFF,
                Roles.DOCTOR,
                Roles.MASON,
                Roles.CORONER,
                Roles.STUMP,
                Roles.BLACKMAILER,
                Roles.SURVIVOR,
                Roles.JESTER,
                Roles.WITCH,
                Roles.AMNESIAC,
                Roles.EXECUTIONER,
                Roles.SILENCER
        );

        Crime.CORRUPTION.setRoles(
                Roles.MAYOR,
                Roles.MARSHALL,
                Roles.AUDITOR,
                Roles.JUDGE
        );

        Crime.IDENTITY_THEFT.setRoles(
                Roles.DISGUISER,
                Roles.INFORMANT
        );

        Crime.SOLICITING.setRoles(
                Roles.ESCORT,
                Roles.MASON_LEADER,
                Roles.CONSORT,
                Roles.CULTIST,
                Roles.LIAISON
        );

        Crime.MURDER.setRoles(
                Roles.BUS_DRIVER,
                Roles.BODYGUARD,
                Roles.VIGILANTE,
                Roles.JAILOR,
                Roles.MASON_LEADER,
                Roles.VETERAN,
                Roles.MAFIOSO,
                Roles.GODFATHER,
                Roles.DISGUISER,
                Roles.KIDNAPPER,
                Roles.BEGUILER,
                Roles.SERIAL_KILLER,
                Roles.ARSONIST,
                Roles.MASS_MURDERER,
                Roles.DECEIVER,
                Roles.DRAGON_HEAD,
                Roles.ENFORCER,
                Roles.INFORMANT,
                Roles.INTERROGATOR
        );

        Crime.DISTURBING_THE_PEACE.setRoles(
                Roles.CRIER,
                Roles.JUDGE,
                Roles.ESCORT,
                Roles.CONSORT,
                Roles.LIAISON
        );

        Crime.CONSPIRACY.setRoles(
                Roles.MASON_LEADER,
                Roles.CULTIST,
                Roles.WITCH_DOCTOR
        );

        Crime.DESTRUCTION_OF_PROPERTY.setRoles(
                Roles.VETERAN,
                Roles.JANITOR,
                Roles.ARSONIST,
                Roles.MASS_MURDERER,
                Roles.INCENSE_MASTER
        );

        Crime.ARSON.setRoles(
                Roles.ARSONIST
        );
    }

    Crime(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public static List<Crime> getCrimes(Roles role) {
        return Crime.ROLE_CRIMES.get(role);
    }

    private void setRoles(Roles... roles) {
        for (Roles role : roles) {
            if (!Crime.ROLE_CRIMES.containsKey(role)) {
                Crime.ROLE_CRIMES.put(role, new ArrayList<>());
            }

            Crime.ROLE_CRIMES.get(role).add(this);
        }
    }

}
