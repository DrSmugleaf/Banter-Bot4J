package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.BotUtils;
import com.github.drsmugbrain.dungeon.DungeonMap;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonCommands {

    public static void start(MessageReceivedEvent event, List<String> args){
        DungeonMap dMap;
        try{
            dMap = new DungeonMap();
        } catch (IOException e){
            e.printStackTrace();
            BotUtils.sendMessage(event.getChannel(), "arregla la ruta al mapa porfa plis");
            return;
        }
        BotUtils.sendMessage(event.getChannel(), dMap.toString());
    }

    public static void up(MessageReceivedEvent event, List<String> args){
        BotUtils.sendMessage(event.getChannel(), "Tranquilo maquina, que esto aun no hace na");
    }

    public static void down(MessageReceivedEvent event, List<String> args){
        BotUtils.sendMessage(event.getChannel(), "Tranquilo maquina, que esto aun no hace na");
    }

    public static void left(MessageReceivedEvent event, List<String> args){
        BotUtils.sendMessage(event.getChannel(), "Tranquilo maquina, que esto aun no hace na");

    }

    public static void right(MessageReceivedEvent event, List<String> args){
        BotUtils.sendMessage(event.getChannel(), "Tranquilo maquina, que esto aun no hace na");

    }

}
