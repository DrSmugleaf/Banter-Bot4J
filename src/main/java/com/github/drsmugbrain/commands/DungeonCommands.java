package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.BotUtils;
import com.github.drsmugbrain.dungeon.Dungeon;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonCommands {

    public static void start(MessageReceivedEvent event, List<String> args){
        Dungeon game;
        try{
            List<IUser> users = event.getMessage().getMentions();
            if(!users.contains(event.getAuthor())){
                users.add(event.getAuthor());
            }
            game = new Dungeon(new IUser[]{event.getMessage().getAuthor()});
        } catch (IOException e){
            e.printStackTrace();
            BotUtils.sendMessage(event.getChannel(), "arregla la ruta al mapa porfa plis");
            return;
        } catch (InputMismatchException e){
            e.printStackTrace();
            BotUtils.sendMessage(event.getChannel(), "El mapa no tiene spawn");
            return;
        }
        IMessage message = event.getChannel().sendMessage(game.getFinishedMap());
        Dungeon.dungeonHash.put(message.getLongID(), game);

        RequestBuffer.request(() -> message.addReaction(":arrow_left:")).get();
        RequestBuffer.request(() -> message.addReaction(":arrow_up:")).get();
        RequestBuffer.request(() -> message.addReaction(":arrow_down:")).get();
        RequestBuffer.request(() -> message.addReaction(":arrow_right:")).get();

    }

}
