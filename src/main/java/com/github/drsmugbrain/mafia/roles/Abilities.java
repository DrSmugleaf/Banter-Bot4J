package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public enum Abilities {

    BODYGUARD("Bodyguard") {
        @Override
        protected void use() {}
    },
    BUS_DRIVER("Bus Driver") {
        @Override
        protected void use() {}
    },
    CITIZEN("Citizen") {
        @Override
        protected void use() {}
    },
    CORONER("Coroner") {
        @Override
        protected void use() {}
    },
    CRIER("Crier") {
        @Override
        protected void use() {}
    },
    DETECTIVE("Detective") {
        @Override
        protected void use() {}
    },
    DOCTOR("Doctor") {
        @Override
        protected void use() {}
    },
    ESCORT("Escort") {
        @Override
        protected void use() {}
    },
    INVESTIGATOR("Investigator") {
        @Override
        protected void use() {}
    },
    JAILOR("Jailor") {
        @Override
        protected void use() {}
    },
    LOOKOUT("Lookout") {
        @Override
        protected void use() {}
    },
    MARSHALL("Marshall") {
        @Override
        protected void use() {}
    },
    MASON("Mason") {
        @Override
        protected void use() {}
    },
    MASON_LEADER("Mason Leader") {
        @Override
        protected void use() {}
    },
    MAYOR("Mayor") {
        @Override
        protected void use() {}
    },
    SHERIFF("Sheriff") {
        @Override
        protected void use() {}
    },
    SPY("Spy") {
        @Override
        protected void use() {}
    },
    STUMP("Stump") {
        @Override
        protected void use() {}
    },
    VETERAN("Veteran") {
        @Override
        protected void use() {}
    },
    VIGILANTE("Vigilante") {
        @Override
        protected void use() {}
    },

    AGENT("Agent") {
        @Override
        protected void use() {}
    },
    BEGUILER("Beguiler") {
        @Override
        protected void use() {}
    },
    BLACKMAILER("Blackmailer") {
        @Override
        protected void use() {}
    },
    CONSIGLIERE("Consigliere") {
        @Override
        protected void use() {}
    },
    CONSORT("Consort") {
        @Override
        protected void use() {}
    },
    DISGUISER("Disguiser") {
        @Override
        protected void use() {}
    },
    FRAMER("Framer") {
        @Override
        protected void use() {}
    },
    GODFATHER("Godfather") {
        @Override
        protected void use() {}
    },
    JANITOR("Janitor") {
        @Override
        protected void use() {}
    },
    KIDNAPPER("Kidnapper") {
        @Override
        protected void use() {}
    },
    MAFIOSO("Mafioso") {
        @Override
        protected void use() {}
    },

    ADMINISTRATOR("Administrator") {
        @Override
        protected void use() {}
    },
    DECEIVER("Deceiver") {
        @Override
        protected void use() {}
    },
    DRAGON_HEAD("Dragon Head") {
        @Override
        protected void use() {}
    },
    ENFORCER("Enforcer") {
        @Override
        protected void use() {}
    },
    FORGER("Forger") {
        @Override
        protected void use() {}
    },
    INCENSE_MASTER("Incense Master") {
        @Override
        protected void use() {}
    },
    INFORMANT("Informant") {
        @Override
        protected void use() {}
    },
    INTERROGATOR("Interrogator") {
        @Override
        protected void use() {}
    },
    LIAISON("Liaison") {
        @Override
        protected void use() {}
    },
    SILENCER("Silencer") {
        @Override
        protected void use() {}
    },
    VANGUARD("Vanguard") {
        @Override
        protected void use() {}
    },

    AMNESIAC("Amnesiac") {
        @Override
        protected void use() {}
    },
    ARSONIST("Arsonist") {
        @Override
        protected void use() {}
    },
    AUDITOR("Auditor") {
        @Override
        protected void use() {}
    },
    CULTIST("Cultist") {
        @Override
        protected void use() {}
    },
    EXECUTIONER("Executioner") {
        @Override
        protected void use() {}
    },
    JESTER("Jester") {
        @Override
        protected void use() {}
    },
    JUDGE("Judge") {
        @Override
        protected void use() {}
    },
    MASS_MURDERER("Mass Murderer") {
        @Override
        protected void use() {}
    },
    SCUMBAG("Scumbag") {
        @Override
        protected void use() {}
    },
    SERIAL_KILLER("Serial Killer") {
        @Override
        protected void use() {}
    },
    SURVIVOR("Survivor") {
        @Override
        protected void use() {}
    },
    WITCH("Witch") {
        @Override
        protected void use() {}
    },
    WITCH_DOCTOR("Witch Doctor") {
        @Override
        protected void use() {}
    };

    Abilities(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
    }

    protected abstract void use();

    public static Abilities getAbility(@Nonnull String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    private static class Holder {
        static Map<String, Abilities> MAP = new HashMap<>();
    }

}
