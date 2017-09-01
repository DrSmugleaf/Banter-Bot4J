package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.chat.Chat;
import com.github.drsmugbrain.mafia.chat.Type;
import com.github.drsmugbrain.mafia.events.EventDispatcher;
import com.github.drsmugbrain.mafia.events.GameStartEvent;
import com.github.drsmugbrain.mafia.roles.Role;
import com.github.drsmugbrain.mafia.roles.Roles;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Game {

    private final Setup SETUP;
    private final Map<Long, Player> PLAYERS = new HashMap<>();
    private final List<Player> PLAYERS_DISPLAY = new ArrayList<>();
    private final Cycle CYCLE;
    private boolean started = false;
    private final Chat CHAT = new Chat();

    public Game(@Nonnull Setup setup, @Nonnull Map<Long, Player> players) {
        this.SETUP = setup;
        this.PLAYERS.putAll(players);
        this.CYCLE = new Cycle(this, Phase.DAY);
    }

    @Nonnull
    public Setup getSetup() {
        return this.SETUP;
    }

    @Nonnull
    public Map<Long, Player> getPlayers() {
        return new HashMap<>(this.PLAYERS);
    }

    @Nonnull
    public Map<Long, Player> getHumanPlayers() {
        Map<Long, Player> players = new HashMap<>(this.PLAYERS);
        players.entrySet().removeIf(entry -> entry.getValue().isBot());
        return players;
    }

    @Nonnull
    public List<Player> getPlayersDisplay() {
        return new ArrayList<>(this.PLAYERS_DISPLAY);
    }

    @Nonnull
    public Cycle getCycle() {
        return this.CYCLE;
    }

    public void start() {
        if (this.started) {
            return;
        }
        this.started = true;

        List<Player> playersWithoutRoles = new ArrayList<>(this.PLAYERS.values());

        for (Roles role : this.SETUP.getRoles()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(playersWithoutRoles.size());
            Player randomPlayer = playersWithoutRoles.remove(randomIndex);

            randomPlayer.setRole(new Role(role));
        }

        this.PLAYERS_DISPLAY.addAll(this.PLAYERS.values());
        Collections.shuffle(this.PLAYERS_DISPLAY);

        EventDispatcher.dispatch(new GameStartEvent(this));

        this.CYCLE.resume();
        this.CHAT.createChannel(Type.TOWN, this.PLAYERS.values());
    }

    protected long getNextBotID() {
        long lowestPlayerID = Collections.min(this.PLAYERS.keySet());
        if (lowestPlayerID < 0) {
            return lowestPlayerID - 1;
        }
        return -1;
    }

    public void fillWithBots() {
        for (int i = this.PLAYERS.size(); i < 15; i++) {
            long botID = this.getNextBotID();
            Player player = new Player(botID, "Bot" + botID);
            player.setBot(true);
            this.PLAYERS.put(botID, player);
        }
    }

    protected void switchPlayers(@Nonnull Player player1, @Nonnull Player player2) {}

    private void sendMessage(@Nonnull Player player, @Nonnull String message) {
        this.CHAT.sendMessage(this, player, message);
    }

    public void sendMessage(long id, @Nonnull String message) {
        this.sendMessage(this.PLAYERS.get(id), message);
    }

    protected Chat getChat() {
        return this.CHAT;
    }

    @Nonnull
    protected List<Player> getTargetedBy(Player player) {
        List<Player> targetedBy = new ArrayList<>();

        for (Player player1 : this.PLAYERS.values()) {
            if (player1.getTarget() == player) {
                targetedBy.add(player1);
            }
        }

        return targetedBy;
    }

}
