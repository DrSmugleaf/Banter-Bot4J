package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.dungeon.entities.Player;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by Brian on 16/05/2017.
 */
public class Dungeon {
    public static Map<Long, Dungeon> dungeonHash = new HashMap<>();  // <message LongID, Dungeon instance>

    private DungeonMap map;
    private Map<Long, Player> playerHash;  // <user LongID, Player instance>



    public Dungeon(IUser[] users) throws IOException, InputMismatchException{
        this.map = new DungeonMap();
        this.playerHash = new HashMap<>();

        for(IUser user : users){
            this.playerHash.put(user.getLongID(), new Player(this.map.spawn_x, this.map.spawn_y));
        }
    }


    public String getFinishedMap(){
        // hacer cosas como pintar jugadores y NPCs
        return this.map.toString(this.playerHash.values());
    }


    private void parseInput(IReaction reaction, IUser user){
        IMessage message = reaction.getMessage();
        String sReaction = reaction.toString();
        Player player = this.playerHash.get(user.getLongID());

        // We remove the reaction (only if the author is not the bot itself), so it can be used again.
        if(user.isBot()){
            return;
        }
        RequestBuffer.request(() -> reaction.getMessage().removeReaction(user, reaction));

        switch(sReaction){
            case "⬅":
                player.moveLeft(this.map);
                break;
            case "➡":
                player.moveRight(this.map);
                break;
            case "⬆":
                player.moveUp(this.map);
                break;
            case "⬇":
                player.moveDown(this.map);
                break;
        }
        message.edit(this.getFinishedMap());
    }


    private Player getPlayerFromUser(IUser user){
        return this.playerHash.get(user.getLongID());
    }


    static void input(IMessage message, IReaction reaction, IUser user){
        Dungeon dungeon = getDungeonFromMessage(message);
        if (dungeon != null){
            dungeon.parseInput(reaction, user);
        }
    }


    private static Dungeon getDungeonFromMessage(IMessage msg){
        return dungeonHash.get(msg.getLongID());
    }


    private static boolean inputIsValid(String sReaction){
        String reactions = "⬅ ⬆ ⬇ ➡";
        return reactions.contains(sReaction);
    }
}
