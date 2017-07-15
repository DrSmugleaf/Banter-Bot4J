package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.DiscordException;
import com.github.drsmugbrain.pokemon.*;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 11/06/2017.
 */
public class PokemonCommands {

    private static final Map<IUser, Battle> BATTLES = new HashMap<>();
    private static final Map<IUser, Trainer> awaitingTrainer = new LinkedHashMap<>();

    @Command
    public static void pokemon(MessageReceivedEvent event, List<String> args) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            pokemons = SmogonImporter.parsePokemons(String.join(" ", args));
        } catch (DiscordException e) {
            Bot.sendMessage(event.getChannel(), e.getMessage());
        }

        IUser author1 = event.getAuthor();
        Trainer trainer1 = new Trainer(event.getAuthor().getLongID(), pokemons.toArray(new Pokemon[]{}));
        if (PokemonCommands.awaitingTrainer.isEmpty()) {
            PokemonCommands.awaitingTrainer.put(event.getAuthor(), trainer1);
            return;
        }

        IUser author2 = PokemonCommands.awaitingTrainer.entrySet().iterator().next().getKey();
        Trainer trainer2 = PokemonCommands.awaitingTrainer.entrySet().iterator().next().getValue();
        Battle battle = new Battle(Generation.VII, trainer1, author2.getLongID(), trainer2, author1.getLongID());

        PokemonCommands.BATTLES.put(author1, battle);
        PokemonCommands.BATTLES.put(author2, battle);

        author1.getOrCreatePMChannel().sendMessage(PokemonCommands.sendOutPokemonEmbed(battle, author1));
        author2.getOrCreatePMChannel().sendMessage(PokemonCommands.sendOutPokemonEmbed(battle, author1));
    }

    @EventSubscriber
    public static void handle(MessageReceivedEvent event) {
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

        if (trainer.getActivePokemons().isEmpty()) {
            Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
            if (!scanner.hasNextInt()) {
                Bot.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
                return;
            }

            int pokemonID = scanner.nextInt() - 1;
            Pokemon chosenPokemon = trainer.getPokemon(pokemonID);
            trainer.sendOut(chosenPokemon);
            trainer.setPokemonInFocus(chosenPokemon);

            if (battle.isReady()) {
                for (Long trainerID : battle.getTrainers().keySet()) {
                    IUser trainerUser = Bot.client.fetchUser(trainerID);
                    trainerUser.getOrCreatePMChannel().sendMessage(PokemonCommands.chooseMoveEmbed(battle, trainerUser));
                }
            }
        } else {
            if (trainer.getChosenMove() == null) {
                if (!trainer.getPokemonInFocus().hasMove(message)) {
                    Bot.sendMessage(event.getChannel(), "Invalid move name.");
                    return;
                }

                trainer.setChosenMove(trainer.getPokemonInFocus().getMove(message));
                event.getChannel().sendMessage(PokemonCommands.chooseTargetEmbed(battle, event.getAuthor()));
            } else {
                Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                if (!scanner.hasNextInt()) {
                    Bot.sendMessage(event.getChannel(), "Invalid Pokemon ID.");
                    return;
                }

                int pokemonID = scanner.nextInt() - 1;

                Map<IUser, Trainer> userTrainerMap = battle.getTrainers().entrySet().stream()
                        .collect(Collectors.toMap(key -> Bot.client.fetchUser(key.getKey()), Map.Entry::getValue));
                List<Trainer> trainerList = new ArrayList<>(userTrainerMap.values());
                List<Pokemon> activePokemons = new ArrayList<>();

                for (Trainer trainer1 : trainerList) {
                    activePokemons.addAll(trainer1.getActivePokemons());
                }

                trainer.addAction(trainer.getPokemonInFocus(), trainer.getChosenMove(), activePokemons.get(pokemonID));

                if (battle.executeTurnReady()) {
                    battle.executeTurn();
                    for (IUser user : userTrainerMap.keySet()) {
                        user.getOrCreatePMChannel().sendMessage(PokemonCommands.chooseMoveEmbed(battle, user));
                    }
                }
            }
        }
    }

    private static EmbedObject sendOutPokemonEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        Map<IUser, Trainer> trainers = battle.getTrainers().entrySet().stream()
                .collect(Collectors.toMap(key -> Bot.client.fetchUser(key.getKey()), Map.Entry::getValue));
        Trainer trainer = trainers.get(user);

        builder.withTitle("Which Pokemon do you want to send out? (Reply with the number of the Pokemon");

        for (int i = 0; i < trainer.getPokemons().length; i++) {
            Pokemon pokemon = trainer.getPokemons()[i];
            int currentHP = pokemon.getCurrentStat(Stat.HP);
            int maxHP = pokemon.getStat(Stat.HP);
            double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
            builder.appendField(
                    i+1 + ": " + pokemon.getName() + " (" + String.join(" ", pokemon.getTypesString()) + ")",
                    "HP: " + percentageHP + "% (" + currentHP + "/" + maxHP + ")\n" +
                            "Ability: " + pokemon.getAbility().getName() + " / Item: " + (pokemon.getItem() != null ? pokemon.getItem().getName() : "None") + "\n" +
                            "Stats: " + pokemon.getStatsStringWithoutHP(),
                    true
            );
        }

        return builder.build();
    }

    private static EmbedObject chooseMoveEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        Map<IUser, Trainer> trainers = battle.getTrainers().entrySet().stream()
                .collect(Collectors.toMap(key -> Bot.client.fetchUser(key.getKey()), Map.Entry::getValue));
        Trainer trainer = trainers.get(user);

        builder.withTitle("Which move do you want to use?");

        for (Move move : trainer.getPokemonInFocus().getMoves()) {
            builder.appendField(move.getBaseMove().getName(), move.getType().getName() + " " + move.getPP() + "/" + move.getBaseMove().getPP(), true);
        }

        return builder.build();
    }

    private static EmbedObject chooseTargetEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        Map<IUser, Trainer> userTrainerMap = battle.getTrainers().entrySet().stream()
                .collect(Collectors.toMap(key -> Bot.client.fetchUser(key.getKey()), Map.Entry::getValue));
        List<String> users = userTrainerMap.keySet().stream().map(IUser::getName)
                .collect(Collectors.toList());

        builder.withTitle("Who do you want to target?");

        List<Trainer> trainerList = new ArrayList<>(userTrainerMap.values());

        int i = 1;
        for (Trainer trainer : trainerList) {
            List<Pokemon> activePokemons = trainer.getActivePokemons();
            for (Pokemon pokemon : activePokemons) {
                int currentHP = pokemon.getCurrentStat(Stat.HP);
                int maxHP = pokemon.getStat(Stat.HP);
                double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
                builder.appendField(
                        i + ": " + pokemon.getName(),
                        "HP: " + percentageHP + "%",
                        true
                );
                i++;
            }
        }

        return builder.build();
    }

}
