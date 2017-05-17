package com.github.drsmugbrain;

import com.github.drsmugbrain.dungeon.DungeonEvents;
import com.github.drsmugbrain.models.Blacklist;
import com.github.drsmugbrain.models.Guild;
import com.github.drsmugbrain.models.User;
import sx.blah.discord.api.IDiscordClient;

/**
 * Created by declan on 03/04/2017.
 */
public class MainRunner {

    public static void main(String[] args){
        IDiscordClient cli = BotUtils.getBuiltDiscordClient(EnvVariables.readFile().get("DISCORD_TOKEN"));

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new CommandHandler());
        cli.getDispatcher().registerListener(new DungeonEvents());
        EnvVariables.readFile();
        new Database();
        User.createTable(Database.conn);
        Guild.createTable(Database.conn);
        Blacklist.createTable(Database.conn);
        // Only login after all events are registered otherwise some may be missed.
        cli.login();

    }

}
