package com.github.drsmugleaf.commands.pokemon;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by DrSmugleaf on 03/09/2018
 */
public class PokemonEvents {

    static final Map<IUser, Battle> BATTLES = new HashMap<>();

    static {
        EventDispatcher.registerListener(new PokemonEvents());
    }

    private static EmbedObject sendOutPokemonEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Which Pokemon do you want to send out? (Reply with the number of the Pokemon)");

        List<com.github.drsmugleaf.pokemon.pokemon.Pokemon> aliveInactivePokemons = battle.getTrainer(user.getLongID()).getAliveInactivePokemons();
        for (int i = 0; i < aliveInactivePokemons.size(); i++) {
            com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon = aliveInactivePokemons.get(i);
            int currentHP = pokemon.getHP();
            int maxHP = pokemon.getMaxHP();
            double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
            builder.appendField(
                    i+1 + ": " + pokemon.getName() + " (" + String.join(" ", pokemon.TYPES.getTypesString()) + ")",
                    "HP: " + percentageHP + "% (" + currentHP + "/" + maxHP + ")\n" +
                    "Ability: " + pokemon.ABILITY.get().getName() + " / Item: " + (pokemon.ITEM.get() != null ? pokemon.ITEM.get().getName() : "None") + "\n" +
                    "Stats: " + pokemon.getStatsStringWithoutHP(),
                    true
            );
        }

        return builder.build();
    }

    private static EmbedObject chooseMoveEmbed(Trainer trainer) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Which move do you want to use?");

        for (BaseMove baseMove : trainer.getPokemonInFocus().MOVES.getValid()) {
            builder.appendField(
                    baseMove.NAME,
                    baseMove.TYPE.getName() + " " + baseMove.PP + "/" + baseMove.PP,
                    true
            );
        }

        return builder.build();
    }

    private static EmbedObject chooseTargetEmbed(Trainer trainer, Move move) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Who do you want to target?");

        int i = 1;
        for (com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon : trainer.getBattle().getTargetList(trainer.getPokemonInFocus())) {
            int currentHP = pokemon.getHP();
            int maxHP = pokemon.getMaxHP();
            double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;

            builder.appendField(
                    i + ": " + pokemon.getName(),
                    "HP: " + percentageHP + "%",
                    true
            );
            i++;
        }

        return builder.build();
    }

    @EventSubscriber
    public static void handle(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("-pokemon")) {
            return;
        }

        long authorID = event.getAuthor().getLongID();
        if (!BATTLES.containsKey(event.getAuthor())) {
            return;
        }
        if (event.getChannel() != event.getAuthor().getOrCreatePMChannel()) {
            return;
        }

        Battle battle = BATTLES.get(event.getAuthor());
        Trainer trainer = battle.getTrainer(authorID);
        String message = event.getMessage().getContent();

        switch (trainer.getStatus()) {
            case NONE:
                break;
            case CHOOSING_POKEMON: {
                Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                if (!scanner.hasNextInt()) {
                    Command.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
                    return;
                }

                int pokemonID = scanner.nextInt() - 1;
                com.github.drsmugleaf.pokemon.pokemon.Pokemon chosenPokemon = trainer.getAliveInactivePokemons().get(pokemonID);
                battle.sendOut(trainer, chosenPokemon);
                trainer.setPokemonInFocus(chosenPokemon);
                break;
            }
            case CHOOSING_MOVE:
                if (!trainer.getPokemonInFocus().MOVES.hasOne(message)) {
                    Command.sendMessage(event.getChannel(), "Invalid move name.");
                    return;
                }

                trainer.setChosenMove(trainer.getPokemonInFocus().MOVES.get(message));
                break;
            case CHOOSING_TARGET: {
                Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                if (!scanner.hasNextInt()) {
                    Command.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
                    return;
                }

                int pokemonID = scanner.nextInt() - 1;

                battle.addAction(trainer, trainer.getPokemonInFocus(), trainer.getChosenMove(), battle.getTargetList(trainer.getPokemonInFocus()).get(pokemonID));
                break;
            }
            case WAITING:
                break;
        }
    }

    @PokemonEventHandler(event = BattleStartedEvent.class)
    public static void handle(BattleStartedEvent event) {
        Battle battle = event.getBattle();
        StringBuilder response = new StringBuilder();

        for (Trainer trainer : battle.getTrainers().values()) {
            List<String> pokemons = new ArrayList<>();

            for (com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon : trainer.getPokemons()) {
                pokemons.add(pokemon.getName());
            }

            response
                    .append(BanterBot4J.CLIENT.fetchUser(trainer.getID()).getName())
                    .append("'s team:\n")
                    .append(String.join(" / ", pokemons))
                    .append("\n");
        }

        for (Long id : battle.getTrainers().keySet()) {
            IUser user = BanterBot4J.CLIENT.fetchUser(id);
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage(response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonDamagedEvent.class)
    public static void handle(PokemonDamagedEvent event) {
        com.github.drsmugleaf.pokemon.pokemon.Pokemon defender = event.getPokemon();
        StringBuilder response = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());
            String hpLoss = decimalFormat.format(100.0 * event.getDamage() / defender.calculate(PermanentStat.HP));

            if (!defender.getTrainer().getID().equals(id)) {
                response.append("The opposing ");
            }
            response
                    .append(defender.getNickname())
                    .append(" lost ")
                    .append(hpLoss)
                    .append("% of its health!");

            IPrivateChannel channel = BanterBot4J.CLIENT.fetchUser(id).getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonMoveEvent.class)
    public static void handle(PokemonMoveEvent event) {
        com.github.drsmugleaf.pokemon.pokemon.Pokemon attacker = event.getPokemon();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!attacker.getTrainer().getID().equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(attacker.getNickname())
                    .append(" used **")
                    .append(event.getMove().getBaseMove().NAME)
                    .append("**!");

            IPrivateChannel channel = BanterBot4J.CLIENT.fetchUser(id).getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerSendOutPokemonEvent.class)
    public static void handle(TrainerSendOutPokemonEvent event) {
        Trainer trainer = event.getTrainer();
        IUser user = BanterBot4J.CLIENT.fetchUser(trainer.getID());
        com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon = event.getPokemon();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!Objects.equals(trainer.getID(), id)) {
                response
                        .append(user.getName())
                        .append(" sent out ");
            } else {
                response.append("Go! ");
            }

            response.append(pokemon.getNickname());

            if (!Objects.equals(pokemon.getNickname(), pokemon.getName())) {
                response
                        .append(" (")
                        .append(pokemon.getName())
                        .append(")");
            }

            response.append("!");

            IPrivateChannel channel = BanterBot4J.CLIENT.fetchUser(id).getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerSendBackPokemonEvent.class)
    public static void handle(TrainerSendBackPokemonEvent event) {
        Trainer trainer = event.getTrainer();
        IUser user = BanterBot4J.CLIENT.fetchUser(trainer.getID());
        com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon = event.getPokemon();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!Objects.equals(trainer.getID(), id)) {
                response
                        .append(user.getName())
                        .append(" withdrew ")
                        .append(pokemon.getNickname());

                if (!Objects.equals(pokemon.getNickname(), pokemon.getName())) {
                    response
                            .append(" (")
                            .append(pokemon.getName())
                            .append(")");
                }
            } else {
                response
                        .append(pokemon.getName())
                        .append(", come back");
            }

            response.append("!");

            IPrivateChannel channel = BanterBot4J.CLIENT.fetchUser(id).getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonDeathEvent.class)
    public static void handle(PokemonDeathEvent event) {
        com.github.drsmugleaf.pokemon.pokemon.Pokemon pokemon = event.getPokemon();
        Trainer trainer = event.getPokemon().getTrainer();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!trainer.getID().equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(pokemon.getNickname())
                    .append(" fainted!");

            IUser user = BanterBot4J.CLIENT.fetchUser(id);
            IPrivateChannel channel = user.getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = BattleTurnStartEvent.class)
    public static void handle(BattleTurnStartEvent event) {
        for (Trainer trainer : event.getBattle().getTrainers().values()) {
            IUser user = BanterBot4J.CLIENT.fetchUser(trainer.getID());

            IPrivateChannel channel = user.getOrCreatePMChannel();
            Command.sendMessage(channel, "**TURN " + event.getBattle().getTurn() + "**");
            Command.sendMessage(channel, chooseMoveEmbed(trainer));
        }
    }

    @PokemonEventHandler(event = TrainerChooseMoveEvent.class)
    public static void handle(TrainerChooseMoveEvent event) {
        Trainer trainer = event.getTrainer();
        Move move = event.getMove();
        IUser user = BanterBot4J.CLIENT.fetchUser(trainer.getID());

        IPrivateChannel channel = user.getOrCreatePMChannel();
        Command.sendMessage(channel, chooseTargetEmbed(trainer, move));
    }

    @PokemonEventHandler(event = PokemonDodgeEvent.class)
    public static void handle(PokemonDodgeEvent event) {
        com.github.drsmugleaf.pokemon.pokemon.Pokemon target = event.getPokemon();
        Trainer trainer = event.getPokemon().getTrainer();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!trainer.getID().equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(target.getNickname())
                    .append(" avoided the attack!");

            IPrivateChannel channel = BanterBot4J.CLIENT.fetchUser(id).getOrCreatePMChannel();
            Command.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerChoosingPokemonEvent.class)
    public static void handle(TrainerChoosingPokemonEvent event) {
        for (Trainer trainer : event.getTrainers()) {
            IUser user = BanterBot4J.CLIENT.fetchUser(trainer.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();

            Command.sendMessage(channel, sendOutPokemonEmbed(event.getBattle(), user));
        }
    }

}
