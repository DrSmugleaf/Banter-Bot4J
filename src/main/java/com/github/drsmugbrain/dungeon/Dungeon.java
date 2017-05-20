package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.BotUtils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Brian on 16/05/2017.
 */
public class Dungeon {
    public static Map<Long, Dungeon> dungeonHash = new HashMap<>();  // <message LongID, Dungeon instance>

    private DungeonMap map;
    private Map<Long, Player> playerHash;  // <user LongID, Player instance>

    public String uuid;

    public Dungeon(IUser[] users) throws IOException, InputMismatchException{
        this.uuid = UUID.randomUUID().toString();
        this.map = new DungeonMap();
        this.playerHash = new HashMap<>();

        for(IUser user : users){
            this.playerHash.put(user.getLongID(), new Player(this.map.spawn_x, this.map.spawn_y));
        }
    }

    public String getFinishedMap(){
        // hacer cosas como pintar jugadores y NPCs
        return this.map.toString();
    }

    private void parseInput(IReaction reaction, IUser user){
        IMessage message = reaction.getMessage();
        IChannel channel = message.getChannel();
        String reactions = "⬅ ⬆ ⬇ ➡";
        String sReaction = reaction.toString();
        Player player = this.playerHash.get(user.getLongID());

        if(player == null){
//            channel.sendMessage("You're not a player, stop trying to play >:(");
            return;
        }
        if(!reactions.contains(sReaction)){
            channel.sendMessage("This is not a valid reaction");
            return;
        }


        RequestBuffer.request(() -> reaction.getMessage().removeReaction(user, reaction));
        String msg = null;
        switch(sReaction){
            case "⬅":
                msg = user.mention() + " moved to the left";
            case "➡":
                msg = user.mention() + " moved to the right";
                break;
            case "⬆":
                msg = user.mention() + " moved up";
                break;
            case "⬇":
                msg = user.mention() + " moved down";
                break;
        }
        if(msg != null){
            String final_msg = msg;
            RequestBuffer.request(() -> reaction.getMessage().getChannel().sendMessage(final_msg));
        }

    }

    public void up(IUser user){
        this.getPlayerFromUser(user).moveUp(this.map);
    }
    public void down(IUser user){
        this.getPlayerFromUser(user).moveDown(this.map);
    }
    public void left(IUser user){
        this.getPlayerFromUser(user).moveLeft(this.map);
    }
    public void right(IUser user){
        this.getPlayerFromUser(user).moveRight(this.map);
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

}
