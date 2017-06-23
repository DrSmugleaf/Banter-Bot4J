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
        Battle battle = new Battle(author1.getLongID(), trainer1, author2.getLongID(), trainer2);

        PokemonCommands.BATTLES.put(author1, battle);
        PokemonCommands.BATTLES.put(author2, battle);

        author1.getOrCreatePMChannel().sendMessage(PokemonCommands.assemblePrivateBattleEmbed(battle, author1));
        author2.getOrCreatePMChannel().sendMessage(PokemonCommands.assemblePrivateBattleEmbed(battle, author1));
    }

    @EventSubscriber
    public static void handle(MessageReceivedEvent event) {
        long authorID = event.getAuthor().getLongID();
        if (!PokemonCommands.BATTLES.containsKey(event.getAuthor())) {
            return;
        }

        Battle battle = PokemonCommands.BATTLES.get(event.getAuthor());
        Trainer trainer = battle.getTrainer(authorID);
    }

    private static EmbedObject assemblePrivateBattleEmbed(Battle battle, IUser user) {
        EmbedBuilder builder = new EmbedBuilder();

        Map<IUser, Trainer> trainers = battle.getTrainers().entrySet().stream()
                .collect(Collectors.toMap(key -> Bot.client.fetchUser(key.getKey()), Map.Entry::getValue));
        List<String> users = trainers.keySet().stream().map(IUser::getName)
                .collect(Collectors.toList());
        Trainer trainer = trainers.get(user);

        builder.withAuthorName(String.join(" vs ", users));

        Pokemon pokemonInFocus = trainer.getPokemonInFocus();
        if (pokemonInFocus != null) {
            builder.withTitle(pokemonInFocus.getName());

            for (Move move : pokemonInFocus.getMoves()) {
                builder.appendField(move.getBaseMove().getName(), move.getType() + " " + move.getPP() + "/" + move.getBaseMove().getPP(), true);
            }
        } else {
            for (Pokemon pokemon : trainer.getPokemons()) {
                int currentHP = pokemon.getCurrentStat(Stat.HP);
                int maxHP = pokemon.getStat(Stat.HP);
                double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
                builder.appendField(
                        pokemon.getName() + " (" + String.join(" ", pokemon.getTypesString()) + ")",
                        "HP: " + percentageHP + "% (" + currentHP + "/" + maxHP + ")\n" +
                        "Ability: " + pokemon.getAbility().getName() + " / Item: " + (pokemon.getItem() != null ? pokemon.getItem().getName() : "None") + "\n" +
                        "Stats: " + pokemon.getStatsStringWithoutHP(),
                        true
                );
            }
        }

        return builder.build();
    }

}
