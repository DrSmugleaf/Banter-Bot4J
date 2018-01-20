package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.youtube.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.jetbrains.annotations.Contract;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 04/09/2017.
 */
public class Music extends AbstractCommand {

    private static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();
    private static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();
    private static final Map<IGuild, List<IUser>> SKIP_VOTES = new HashMap<>();
    private static final Cache<SimpleEntry<IGuild, IUser>, List<Song>> UNDO_STOP_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    static {
        AudioSourceManagers.registerRemoteSources(PLAYER_MANAGER);
        EventDispatcher.registerListener(new Music());
    }

    public static synchronized GuildMusicManager getGuildMusicManager(IGuild guild) {
        GuildMusicManager musicManager = MUSIC_MANAGERS.computeIfAbsent(
                guild, k -> new GuildMusicManager(PLAYER_MANAGER)
        );

        guild.getAudioManager().setAudioProvider(musicManager.getProvider());

        return musicManager;
    }

    @Contract(pure = true)
    @Nonnull
    public static AudioPlayerManager getAudioPlayerManager() {
        return PLAYER_MANAGER;
    }

    @Command(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY})
    public static void play(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        try {
            event.getMessage().delete();
        } catch (MissingPermissionsException ignored) {}

        IUser author = event.getAuthor();
        IVoiceChannel authorVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();

        IUser bot = event.getClient().getOurUser();
        IVoiceChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (botVoiceChannel != authorVoiceChannel) {
            authorVoiceChannel.join();
        }

        String searchString = String.join(" ", args);

        PLAYER_MANAGER.loadItem(searchString, new AudioResultHandler(channel, author, searchString));
    }

    @Command(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL})
    public static void skip(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentSong() == null) {
            sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        SKIP_VOTES.computeIfAbsent(guild, k -> new ArrayList<>());

        IUser author = event.getAuthor();
        if (SKIP_VOTES.get(guild).contains(author)) {
            sendMessage(channel, "You have already voted to skip this song.");
            return;
        }

        SKIP_VOTES.get(guild).add(author);

        IUser bot = event.getClient().getOurUser();
        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        List<IUser> users = botVoiceChannel.getUsersHere();
        int humanUsers = 0;
        for (IUser user : users) {
            if (!user.isBot()) {
                humanUsers++;
            }
        }

        int votes = SKIP_VOTES.get(guild).size();
        double requiredVotes = humanUsers / 2;

        if (votes >= requiredVotes || author == musicManager.getScheduler().getCurrentSong().getSubmitter()) {
            SKIP_VOTES.get(guild).clear();
            musicManager.getScheduler().skip();
            sendMessage(channel, "Skipped the current song.");
        } else {
            String response = String.format("Votes: %d/%.0f", votes, requiredVotes);
            sendMessage(channel, response);
        }
    }

    @Command(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL})
    public static void pause(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        if (musicManager.getScheduler().isPaused()) {
            sendMessage(channel, "The current song is already paused. Use " + BanterBot4J.BOT_PREFIX + "resume to resume it.");
            return;
        }

        musicManager.getScheduler().pause();
        sendMessage(channel, "Paused the current song.");
    }

    @Command(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL})
    public static void resume(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        if (!musicManager.getScheduler().isPaused()) {
            sendMessage(channel, "There isn't a song currently paused.");
            return;
        }

        musicManager.getScheduler().resume();
        sendMessage(channel, "Resumed the current song.");
    }

    @Command(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY})
    public static void stop(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentSong() == null) {
            sendMessage(channel, "There aren't any songs currently playing or in the queue.");
            return;
        }

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();

        SimpleEntry<IGuild, IUser> pair = new SimpleEntry<>(guild, author);
        UNDO_STOP_CACHE.put(pair, scheduler.cloneSongs());

        scheduler.stop();
        sendMessage(
                channel,
                "Stopped and removed all songs from the queue.\n" +
                "You have one minute to restore them back to the queue using " + BanterBot4J.BOT_PREFIX + "undostop."
        );
    }

    @Command(tags = {Tags.GUILD_ONLY})
    public static void undostop(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();
        SimpleEntry<IGuild, IUser> pair = new SimpleEntry<>(guild, author);
        List<Song> songs = UNDO_STOP_CACHE.getIfPresent(pair);
        if (songs == null) {
            sendMessage(channel, "You haven't stopped any songs in the last minute.");
            return;
        }

        UNDO_STOP_CACHE.invalidate(pair);

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();
        songs.addAll(scheduler.cloneSongs());
        scheduler.stop();
        scheduler.queue(songs);
        sendMessage(channel, "Restored all stopped songs.");
    }

    @Command(tags = {Tags.GUILD_ONLY})
    public static void queue(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();
        IUser author = event.getAuthor();

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();
        Song currentSong = scheduler.getCurrentSong();
        if (currentSong == null) {
            sendMessage(channel, "There are no songs currently playing or in the queue.");
            return;
        }

        AudioTrack track = currentSong.getTrack();
        TimeUnit msUnit = TimeUnit.MILLISECONDS;
        long trackDuration = track.getDuration();
        String trackDurationString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackDuration) % 24,
                msUnit.toMinutes(trackDuration) % 60,
                msUnit.toSeconds(trackDuration) % 60
        );

        long trackTimeLeft = trackDuration - track.getPosition();
        String trackTimeLeftString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackTimeLeft) % 24,
                msUnit.toMinutes(trackTimeLeft) % 60,
                msUnit.toSeconds(trackTimeLeft) % 60
        );

        String songStatus = "Playing";
        if (scheduler.isPaused()) {
            songStatus = "Paused";
        }

        List<Song> queue = scheduler.getQueue();
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName(author.getDisplayName(guild))
                .withAuthorIcon(author.getAvatarURL())
                .withTitle("Currently playing: " + track.getInfo().title)
                .appendDescription("\n")
                .appendDescription("Song status: " + songStatus)
                .appendDescription("\n")
                .appendDescription("Song duration: " + trackDurationString)
                .appendDescription("\n")
                .appendDescription("Time remaining: " + trackTimeLeftString)
                .appendField("Songs in queue", String.valueOf(queue.size()), false);

        if (scheduler.hasNextSong()) {
            long queueDuration = 0;
            for (Song song : queue) {
                queueDuration += song.getTrack().getDuration();
            }
            String queueDurationString = String.format(
                    "%02d:%02d:%02d",
                    msUnit.toHours(queueDuration) % 24,
                    msUnit.toMinutes(queueDuration) % 60,
                    msUnit.toSeconds(queueDuration) % 60
            );

            builder.appendField("Queue duration", queueDurationString, false);
        }

        sendMessage(channel, builder.build());
    }

    @SongEventHandler(event = SongStartEvent.class)
    public static void handle(@Nonnull SongStartEvent event) {
        Song song = event.getSong();
        if (song == null) {
            return;
        }
        String response = String.format("Now playing `%s`.", song.getTrack().getInfo().title);
        sendMessage(song.getChannel(), response);
    }

    @SongEventHandler(event = SongQueueEvent.class)
    public static void handle(@Nonnull SongQueueEvent event) {
        Song song = event.getSong();
        if (song == null) {
            return;
        }
        String response = String.format("Added `%s` to the queue.", song.getTrack().getInfo().title);
        sendMessage(song.getChannel(), response);
    }

    @SongEventHandler(event = NoMatchesEvent.class)
    public static void handle(@Nonnull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        sendMessage(handler.getChannel(), response);
    }

    @SongEventHandler(event = LoadFailedEvent.class)
    public static void handle(@Nonnull LoadFailedEvent event) {
        String response = String.format("Error playing song: %s", event.getException().getMessage());
        sendMessage(event.getHandler().getChannel(), response);
    }

    @SongEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@Nonnull PlaylistQueueEvent event) {
        IChannel channel = event.getSongs().get(0).getChannel();
        String response = String.format("Added %d songs to the queue.", event.getSongs().size());
        sendMessage(channel, response);
    }

}
