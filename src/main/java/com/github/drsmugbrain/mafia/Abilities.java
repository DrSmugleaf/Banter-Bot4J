package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.chat.Channel;
import com.github.drsmugbrain.mafia.chat.Chatter;
import com.github.drsmugbrain.mafia.chat.Type;
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
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.BODYGUARDED);
        }
    },
    BUS_DRIVER("Bus Driver", Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            game.switchPlayers(target1, target2);
        }
    },
    CITIZEN("Citizen", Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            player.addStatuses(RoleStatuses.BULLETPROOF_VEST);
        }
    },
    CORONER("Coroner") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.CORONER.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    CRIER("Crier") {
        @Override
        protected void onPhaseChange(@Nonnull Game game, @Nonnull Phase phase, @Nonnull Player target1, Player target2, @Nonnull Player player) {
            Channel channel = game.getChat().getChannel(Type.TOWN);

            switch (phase) {
                case DAY:
                    game.getChat().removeChatter(channel, player.getID());
                    break;
                case NIGHT:
                    Chatter chatter = new Chatter(player);
                    chatter.setAnonymous(true);
                    game.getChat().addChatter(channel, chatter);
                    break;
            }
        }
    },
    DETECTIVE("Detective") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.DETECTIVE.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    DOCTOR("Doctor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.HEALED);
        }
    },
    ESCORT("Escort") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.ROLEBLOCKED);
        }
    },
    INVESTIGATOR("Investigator") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.INVESTIGATOR.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    JAILOR("Jailor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LOOKOUT("Lookout") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MARSHALL("Marshall") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON("Mason") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON_LEADER("Mason Leader") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAYOR("Mayor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SHERIFF("Sheriff") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SPY("Spy") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    STUMP("Stump") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VETERAN("Veteran") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VIGILANTE("Vigilante") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AGENT("Agent") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BEGUILER("Beguiler") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BLACKMAILER("Blackmailer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSIGLIERE("Consigliere") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSORT("Consort") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DISGUISER("Disguiser") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FRAMER("Framer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    GODFATHER("Godfather") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JANITOR("Janitor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    KIDNAPPER("Kidnapper") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAFIOSO("Mafioso") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    ADMINISTRATOR("Administrator") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DECEIVER("Deceiver") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DRAGON_HEAD("Dragon Head") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ENFORCER("Enforcer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FORGER("Forger") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INCENSE_MASTER("Incense Master") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INFORMANT("Informant") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INTERROGATOR("Interrogator") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LIAISON("Liaison") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SILENCER("Silencer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VANGUARD("Vanguard") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AMNESIAC("Amnesiac") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ARSONIST("Arsonist") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    AUDITOR("Auditor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CULTIST("Cultist") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    EXECUTIONER("Executioner") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JESTER("Jester") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JUDGE("Judge") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASS_MURDERER("Mass Murderer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SCUMBAG("Scumbag") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SERIAL_KILLER("Serial Killer") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SURVIVOR("Survivor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH("Witch") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH_DOCTOR("Witch Doctor") {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
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

    public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {};

    protected void onPhaseChange(@Nonnull Game game, @Nonnull Phase phase, @Nonnull Player target1, Player target2, @Nonnull Player player) {}

    @Nonnull
    public static Abilities getAbility(@Nonnull String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    private static class Holder {
        static Map<String, Abilities> MAP = new HashMap<>();
    }

}
