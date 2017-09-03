package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.chat.Channel;
import com.github.drsmugbrain.mafia.chat.Chatter;
import com.github.drsmugbrain.mafia.chat.Type;
import com.github.drsmugbrain.mafia.events.EventDispatcher;
import com.github.drsmugbrain.mafia.events.StatusEvent;
import com.github.drsmugbrain.mafia.roles.RoleStatuses;
import com.github.drsmugbrain.mafia.roles.Roles;
import com.github.drsmugbrain.mafia.roles.Types;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public enum Abilities {

    BODYGUARD(Roles.BODYGUARD, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            player.setTarget(target1);
        }
    },
    BUS_DRIVER(Roles.BUS_DRIVER, Types.TARGET_SWITCH, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            game.switchPlayers(target1, target2);
        }
    },
    CITIZEN(Roles.CITIZEN, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            player.addStatuses(RoleStatuses.BULLETPROOF_VEST);
        }
    },
    CORONER(Roles.CORONER, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.CORONER.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    CRIER(Roles.CRIER, Types.CHAT, Phase.NIGHT) {
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
    DETECTIVE(Roles.DETECTIVE, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.DETECTIVE.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    DOCTOR(Roles.DOCTOR, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.HEALED);
        }
    },
    ESCORT(Roles.ESCORT, Types.ROLE_BLOCK, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            target1.addStatuses(RoleStatuses.ROLEBLOCKED);
        }
    },
    INVESTIGATOR(Roles.INVESTIGATOR, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {
            String message = Messages.INVESTIGATOR.getMessage(game, player, target1, target2);
            StatusEvent event = new StatusEvent(game, player, message);
            EventDispatcher.dispatch(event);
        }
    },
    JAILOR(Roles.JAILOR, Types.DETAIN, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LOOKOUT(Roles.LOOKOUT, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MARSHALL(Roles.MARSHALL, Types.MISCELLANEOUS, Phase.DAY) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON(Roles.MASON, Types.CHAT, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASON_LEADER(Roles.MASON_LEADER, Types.MASON, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAYOR(Roles.MAYOR, Types.MISCELLANEOUS, Phase.DAY) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SHERIFF(Roles.SHERIFF, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SPY(Roles.SPY, Types.CHAT, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    STUMP(Roles.STUMP, Types.NONE, Phase.NONE) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VETERAN(Roles.VETERAN, Types.BULLETPROOF_VEST, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VIGILANTE(Roles.VIGILANTE, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AGENT(Roles.AGENT, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BEGUILER(Roles.BEGUILER, Types.TARGET_SWITCH, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    BLACKMAILER(Roles.BLACKMAILER, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSIGLIERE(Roles.CONSIGLIERE, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CONSORT(Roles.CONSORT, Types.ROLE_BLOCK, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DISGUISER(Roles.DISGUISER, Types.DISGUISE, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FRAMER(Roles.FRAMER, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    GODFATHER(Roles.GODFATHER, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JANITOR(Roles.JANITOR, Types.JANITOR, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    KIDNAPPER(Roles.KIDNAPPER, Types.DETAIN, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MAFIOSO(Roles.MAFIOSO, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    ADMINISTRATOR(Roles.ADMINISTRATOR, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DECEIVER(Roles.DECEIVER, Types.TARGET_SWITCH, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    DRAGON_HEAD(Roles.DRAGON_HEAD, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ENFORCER(Roles.ENFORCER, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    FORGER(Roles.FORGER, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INCENSE_MASTER(Roles.INCENSE_MASTER, Types.JANITOR, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INFORMANT(Roles.INFORMANT, Types.DISGUISE, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    INTERROGATOR(Roles.INTERROGATOR, Types.DETAIN, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    LIAISON(Roles.LIAISON, Types.ROLE_BLOCK, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SILENCER(Roles.SILENCER, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    VANGUARD(Roles.VANGUARD, Types.INVESTIGATION, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },

    AMNESIAC(Roles.AMNESIAC, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    ARSONIST(Roles.ARSONIST, Types.ARSON, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    AUDITOR(Roles.AUDITOR, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    CULTIST(Roles.CULTIST, Types.CULT, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    EXECUTIONER(Roles.EXECUTIONER, Types.NONE, Phase.NONE) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JESTER(Roles.JESTER, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    JUDGE(Roles.JUDGE, Types.MISCELLANEOUS, Phase.DAY) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    MASS_MURDERER(Roles.MASS_MURDERER, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SCUMBAG(Roles.SCUMBAG, Types.NONE, Phase.NONE) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SERIAL_KILLER(Roles.SERIAL_KILLER, Types.KILL, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    SURVIVOR(Roles.SURVIVOR, Types.BULLETPROOF_VEST, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH(Roles.WITCH, Types.TARGET_SWITCH, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    },
    WITCH_DOCTOR(Roles.WITCH_DOCTOR, Types.MISCELLANEOUS, Phase.NIGHT) {
        @Override
        public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}
    };

    private final Roles ROLE;
    private final Types TYPE;
    private final Phase PHASE;

    Abilities(@Nonnull Roles role, @Nonnull Types type, @Nonnull Phase phase) {
        Holder.MAP.put(role, this);
        this.ROLE = role;
        this.TYPE = type;
        this.PHASE = phase;
    }

    @Nonnull
    public Roles getRole() {
        return this.ROLE;
    }

    @Nonnull
    public Types getType() {
        return this.TYPE;
    }

    @Nonnull
    public Phase getPhase() {
        return this.PHASE;
    }

    public void use(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, Player target2) {}

    protected void onPhaseChange(@Nonnull Game game, @Nonnull Phase phase, @Nonnull Player target1, Player target2, @Nonnull Player player) {}

    @Nonnull
    public static Abilities getAbility(@Nonnull Roles role) {
        return Holder.MAP.get(role);
    }

    private static class Holder {
        static Map<Roles, Abilities> MAP = new HashMap<>();
    }

}
