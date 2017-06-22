package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.DiscordException;
import com.github.drsmugbrain.pokemon.Battle;
import com.github.drsmugbrain.pokemon.SmogonImporter;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.*;

/**
 * Created by DrSmugleaf on 11/06/2017.
 */
public class PokemonCommands {

    private static final Map<Long, Battle> BATTLES = new HashMap<>();

    @Command
    public static void pokemon(MessageReceivedEvent event, List<String> args) {
        try {
            System.out.println(Arrays.toString(SmogonImporter.parsePokemons(String.join(" ", args))));
        } catch (DiscordException e) {
            Bot.sendMessage(event.getChannel(), e.getMessage());
        }
    }

    @EventSubscriber
    public static void handle(MessageReceivedEvent event) {
        if (!PokemonCommands.BATTLES.containsKey(event.getAuthor().getLongID())) {
            return;
        }

        Battle battle = PokemonCommands.BATTLES.get(event.getAuthor().getLongID());
    }

}
