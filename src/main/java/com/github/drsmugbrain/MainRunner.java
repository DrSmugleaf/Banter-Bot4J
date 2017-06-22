package com.github.drsmugbrain;

import com.github.drsmugbrain.commands.PokemonCommands;
import com.github.drsmugbrain.models.Guild;
import com.github.drsmugbrain.models.Member;
import com.github.drsmugbrain.models.User;
import com.github.drsmugbrain.pokemon.SerebiiParser;
import com.github.drsmugbrain.pokemon.SmogonParser;
import com.github.drsmugbrain.util.Bot;
import com.github.drsmugbrain.util.Env;
import sx.blah.discord.api.IDiscordClient;

/**
 * Created by declan on 03/04/2017.
 */
public class MainRunner {

    public static void main(String[] args) {
        try {
            SmogonParser.parse();
            SerebiiParser.parse();
        } catch (Exception e) {
            Bot.LOGGER.error("Error parsing Serebii HTML", e);
        }

        IDiscordClient cli = Bot.buildClient(Env.readFile().get("DISCORD_TOKEN"));

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new CommandHandler());
        cli.getDispatcher().registerListeners(Guild.class, User.class, PokemonCommands.class);
        Env.readFile();
        new Database();

        User.createTable(Database.conn);
        Guild.createTable(Database.conn);
        Member.createTable(Database.conn);
        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }

}
