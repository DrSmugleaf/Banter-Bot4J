package com.github.drsmugbrain;

import com.github.drsmugbrain.models.User;
import sx.blah.discord.api.IDiscordClient;

/**
 * Created by declan on 03/04/2017.
 */
public class MainRunner {

    public static void main(String[] args){
        IDiscordClient cli = BotUtils.getBuiltDiscordClient(EnvVariables.getEnvVariables().get("DISCORD_TOKEN"));

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new CommandHandler());
        EnvVariables.getEnvVariables();
        new Database();
        User.createTable(Database.conn);
        // Only login after all events are registered otherwise some may be missed.
        cli.login();

    }

}
