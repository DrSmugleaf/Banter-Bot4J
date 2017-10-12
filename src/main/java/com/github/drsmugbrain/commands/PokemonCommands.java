package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.pokemon.*;
import com.github.drsmugbrain.pokemon.events.*;
import com.github.drsmugbrain.pokemon.stats.PermanentStat;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by DrSmugleaf on 11/06/2017.
 */
public class PokemonCommands {

    private static final Map<IUser, Battle> BATTLES = new HashMap<>();
    private static final Map<IUser, TrainerBuilder> awaitingTrainer = new LinkedHashMap<>();

    static {
        EventDispatcher.registerListener(new PokemonCommands());
    }

    private static EmbedObject sendOutPokemonEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Which Pokemon do you want to send out? (Reply with the number of the Pokemon)");

        List<Pokemon> aliveInactivePokemons = battle.getTrainer(user.getLongID()).getAliveInactivePokemons();
        for (int i = 0; i < aliveInactivePokemons.size(); i++) {
            Pokemon pokemon = aliveInactivePokemons.get(i);
            int currentHP = pokemon.getHP();
            int maxHP = pokemon.getMaxHP();
            double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
            builder.appendField(
                    i+1 + ": " + pokemon.getName() + " (" + String.join(" ", pokemon.TYPES.getTypesString()) + ")",
                    "HP: " + percentageHP + "% (" + currentHP + "/" + maxHP + ")\n" +
                            "Ability: " + pokemon.getAbility().getName() + " / Item: " + (pokemon.getItem() != null ? pokemon.getItem().getName() : "None") + "\n" +
                            "Stats: " + pokemon.getStatsStringWithoutHP(),
                    true
            );
        }

        return builder.build();
    }

    private static EmbedObject chooseMoveEmbed(Trainer trainer) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Which move do you want to use?");

        for (BaseMove baseMove : trainer.getPokemonInFocus().getValidMoves()) {
            builder.appendField(
                    baseMove.getName(),
                    baseMove.getType().getName() + " " + baseMove.getPP() + "/" + baseMove.getPP(),
                    true
            );
        }

        return builder.build();
    }

    private static EmbedObject chooseTargetEmbed(Trainer trainer, Move move) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.withTitle("Who do you want to target?");

        int i = 1;
        for (Pokemon pokemon : trainer.getBattle().getTargetList(trainer.getPokemonInFocus())) {
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

    @Command
    public static void pokemon(MessageReceivedEvent event, List<String> args) {
        IUser user1 = event.getAuthor();
        long id = user1.getLongID();
        String name = user1.getName();

        TrainerBuilder trainerBuilder1 = new TrainerBuilder();
        trainerBuilder1
                .setID(id)
                .setName(name)
                .addPokemons(String.join(" ", args));

        if (awaitingTrainer.isEmpty()) {
            awaitingTrainer.put(event.getAuthor(), trainerBuilder1);
            return;
        }

        Entry<IUser, TrainerBuilder> userTrainerEntry = awaitingTrainer.entrySet().iterator().next();
        IUser user2 = userTrainerEntry.getKey();
        TrainerBuilder trainerBuilder2 = userTrainerEntry.getValue();

        BattleBuilder battleBuilder = new BattleBuilder();
        Setup setup = new Setup(Generation.VII, Variation.SINGLE_BATTLE);

        battleBuilder
                .setSetup(setup)
                .addTrainer(trainerBuilder1, trainerBuilder2);

        Battle battle = null;
        try {
            battle = battleBuilder.build();
        } catch (UserException e) {
            Bot.sendMessage(event.getChannel(), e.getMessage());
        }

        PokemonCommands.BATTLES.put(user1, battle);
        PokemonCommands.BATTLES.put(user2, battle);
    }

    @EventSubscriber
    public static void handle(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("-pokemon")) {
            return;
        }

        long authorID = event.getAuthor().getLongID();
        if (!PokemonCommands.BATTLES.containsKey(event.getAuthor())) {
            return;
        }
        if (event.getChannel() != event.getAuthor().getOrCreatePMChannel()) {
            return;
        }

        Battle battle = PokemonCommands.BATTLES.get(event.getAuthor());
        Trainer trainer = battle.getTrainer(authorID);
        String message = event.getMessage().getContent();

        switch (trainer.getStatus()) {
            case NONE:
                break;
            case CHOOSING_POKEMON: {
                Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                if (!scanner.hasNextInt()) {
                    Bot.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
                    return;
                }

                int pokemonID = scanner.nextInt() - 1;
                Pokemon chosenPokemon = trainer.getAliveInactivePokemons().get(pokemonID);
                battle.sendOut(trainer, chosenPokemon);
                trainer.setPokemonInFocus(chosenPokemon);
                break;
            }
            case CHOOSING_MOVE:
                if (!trainer.getPokemonInFocus().hasOneMove(message)) {
                    Bot.sendMessage(event.getChannel(), "Invalid move name.");
                    return;
                }

                trainer.setChosenMove(trainer.getPokemonInFocus().getMove(message));
                break;
            case CHOOSING_TARGET: {
                Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                if (!scanner.hasNextInt()) {
                    Bot.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
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

            for (Pokemon pokemon : trainer.getPokemons()) {
                pokemons.add(pokemon.getName());
            }

            response
                    .append(Bot.client.fetchUser(trainer.getID()).getName())
                    .append("'s team:\n")
                    .append(String.join(" / ", pokemons))
                    .append("\n");
        }

        for (Long id : battle.getTrainers().keySet()) {
            IUser user = Bot.client.fetchUser(id);
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage(response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonDamagedEvent.class)
    public static void handle(PokemonDamagedEvent event) {
        Pokemon defender = event.getPokemon();
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

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonMoveEvent.class)
    public static void handle(PokemonMoveEvent event) {
        Pokemon attacker = event.getPokemon();
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!attacker.getTrainer().getID().equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(attacker.getNickname())
                    .append(" used **")
                    .append(event.getMove().getBaseMove().getName())
                    .append("**!");

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerSendOutPokemonEvent.class)
    public static void handle(TrainerSendOutPokemonEvent event) {
        Trainer trainer = event.getTrainer();
        IUser user = Bot.client.fetchUser(trainer.getID());
        Pokemon pokemon = event.getPokemon();
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

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerSendBackPokemonEvent.class)
    public static void handle(TrainerSendBackPokemonEvent event) {
        Trainer trainer = event.getTrainer();
        IUser user = Bot.client.fetchUser(trainer.getID());
        Pokemon pokemon = event.getPokemon();
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

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = PokemonDeathEvent.class)
    public static void handle(PokemonDeathEvent event) {
        Pokemon pokemon = event.getPokemon();
        Trainer trainer = event.getPokemon().getTrainer();
        IUser user = Bot.client.fetchUser(trainer.getID());
        StringBuilder response = new StringBuilder();

        for (Long id : event.getBattle().getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!trainer.getID().equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(pokemon.getNickname())
                    .append(" fainted!");

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = BattleTurnStartEvent.class)
    public static void handle(BattleTurnStartEvent event) {
        for (Trainer trainer : event.getBattle().getTrainers().values()) {
            IUser user = Bot.client.fetchUser(trainer.getID());

            IPrivateChannel channel = user.getOrCreatePMChannel();
            Bot.sendMessage(channel, "**TURN " + event.getBattle().getTurn() + "**");
            Bot.sendMessage(channel, PokemonCommands.chooseMoveEmbed(trainer));
        }
    }

    @PokemonEventHandler(event = TrainerChooseMoveEvent.class)
    public static void handle(TrainerChooseMoveEvent event) {
        Trainer trainer = event.getTrainer();
        Move move = event.getMove();
        IUser user = Bot.client.fetchUser(trainer.getID());

        IPrivateChannel channel = user.getOrCreatePMChannel();
        Bot.sendMessage(channel, PokemonCommands.chooseTargetEmbed(trainer, move));
    }

    @PokemonEventHandler(event = PokemonDodgeEvent.class)
    public static void handle(PokemonDodgeEvent event) {
        Pokemon target = event.getPokemon();
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

            IPrivateChannel channel = Bot.client.fetchUser(id).getOrCreatePMChannel();
            Bot.sendMessage(channel, response.toString());
        }
    }

    @PokemonEventHandler(event = TrainerChoosingPokemonEvent.class)
    public static void handle(TrainerChoosingPokemonEvent event) {
        for (Trainer trainer : event.getTrainers()) {
            IUser user = Bot.client.fetchUser(trainer.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();

            Bot.sendMessage(channel, PokemonCommands.sendOutPokemonEmbed(event.getBattle(), user));
        }
    }

}
