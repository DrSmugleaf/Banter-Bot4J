package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.BotUtils;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by Brian on 16/05/2017.
 */
public class Dungeon {
    public static Map<IMessage, Dungeon> dungeonHash = new HashMap<>();

    private DungeonMap map;
    private Map<IUser, Player> playerHash;

    public Dungeon(IUser[] users) throws IOException, InputMismatchException{
        this.map = new DungeonMap();
        this.playerHash = new HashMap<>();

        for(IUser user : users){
            this.playerHash.put(user, new Player(this.map.spawn_x, this.map.spawn_y));
        }

    }

    public String getFinishedMap(){
        // hacer cosas como pintar usuario y NPCs
        return this.map.toString();
    }

    public void parseInput(IReaction reaction, IUser user){
        String reactions = ":arrow_left: :arrow_right: :arrow_up: :arrow_down:";
        String sReaction = reaction.toString();
        Player player = this.playerHash.get(user);

        if(player == null || reactions.contains(sReaction)){
            return;
        }



    }

    public void up(IUser user){
        this.playerHash.get(user).moveUp(this.map);
    }
    public void down(IUser user){
        this.playerHash.get(user).moveDown(this.map);
    }
    public void left(IUser user){
        this.playerHash.get(user).moveLeft(this.map);
    }
    public void right(IUser user){
        this.playerHash.get(user).moveRight(this.map);
    }

    public static void input(IMessage message, IReaction reaction, IUser user){
        Dungeon dungeon = dungeonHash.get(message);
        if (dungeon == null){
            BotUtils.sendMessage(message.getChannel(), "Esto no es del juego");
            return;
        }
        dungeon.parseInput(reaction, user);
        BotUtils.sendMessage(message.getChannel(), "jugando?");

    }

}
