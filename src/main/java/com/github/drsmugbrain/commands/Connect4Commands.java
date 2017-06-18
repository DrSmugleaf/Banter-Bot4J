package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.connect4.Connect4;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

/**
 * Created by Brian on 18/06/2017.
 */
public class Connect4Commands {
    @Command
    public static void connect4(MessageReceivedEvent event, List<String> args){
        IUser player1 = event.getAuthor();
        IUser player2 = event.getMessage().getMentions().get(0);
        Connect4 game = new Connect4(player1, player2);

        game.setMessage(event.getChannel().sendMessage(game.toEmotes()));
    }
}
