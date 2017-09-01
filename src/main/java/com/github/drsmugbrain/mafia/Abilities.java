package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.events.EventDispatcher;
import com.github.drsmugbrain.mafia.events.StatusEvent;
import com.github.drsmugbrain.mafia.roles.RoleStatuses;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public enum Abilities {

    BODYGUARD("Bodyguard", Phase.NIGHT) {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.BODYGUARDED);
        }
    },
    BUS_DRIVER("Bus Driver", Phase.NIGHT) {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            game.switchPlayers(target1, target2);
        }
    },
    CITIZEN("Citizen", Phase.NIGHT) {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            player.addStatuses(RoleStatuses.BULLETPROOF_VEST);
        }
    },
    CORONER("Coroner") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.CORONER.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    CRIER("Crier") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DETECTIVE("Detective") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DOCTOR("Doctor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ESCORT("Escort") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INVESTIGATOR("Investigator") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JAILOR("Jailor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LOOKOUT("Lookout") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MARSHALL("Marshall") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON("Mason") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON_LEADER("Mason Leader") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAYOR("Mayor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SHERIFF("Sheriff") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SPY("Spy") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    STUMP("Stump") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VETERAN("Veteran") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VIGILANTE("Vigilante") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AGENT("Agent") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BEGUILER("Beguiler") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BLACKMAILER("Blackmailer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSIGLIERE("Consigliere") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSORT("Consort") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DISGUISER("Disguiser") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FRAMER("Framer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    GODFATHER("Godfather") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JANITOR("Janitor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    KIDNAPPER("Kidnapper") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAFIOSO("Mafioso") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    ADMINISTRATOR("Administrator") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DECEIVER("Deceiver") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DRAGON_HEAD("Dragon Head") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ENFORCER("Enforcer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FORGER("Forger") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INCENSE_MASTER("Incense Master") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INFORMANT("Informant") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INTERROGATOR("Interrogator") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LIAISON("Liaison") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SILENCER("Silencer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VANGUARD("Vanguard") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AMNESIAC("Amnesiac") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ARSONIST("Arsonist") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    AUDITOR("Auditor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CULTIST("Cultist") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    EXECUTIONER("Executioner") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JESTER("Jester") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JUDGE("Judge") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASS_MURDERER("Mass Murderer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SCUMBAG("Scumbag") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SERIAL_KILLER("Serial Killer") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SURVIVOR("Survivor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH("Witch") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH_DOCTOR("Witch Doctor") {
        @Override
        protected void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    };

    private final String NAME;
    private final List<Phase> PHASES = new ArrayList<>();

    Abilities(@Nonnull String name, @Nonnull Phase... phase) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        Collections.addAll(this.PHASES, phase);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public List<Phase> getPhases() {
        return new ArrayList<>(this.PHASES);
    }

    protected abstract void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2);

    @Nonnull
    public static Abilities getAbility(@Nonnull String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    private static class Holder {
        static Map<String, Abilities> MAP = new HashMap<>();
    }

}
