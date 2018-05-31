package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.youtube.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.jetbrains.annotations.Contract;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
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
public class Music {

    @Nonnull
    private static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();

    @Nonnull
    private static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();

    @Nonnull
    private static final Map<IGuild, List<IUser>> SKIP_VOTES = new HashMap<>();

    @Nonnull
    private static final Cache<SimpleEntry<IGuild, IUser>, List<AudioTrack>> UNDO_STOP_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Nonnull
    private static Map<IGuild, IMessage> lastMessage = new HashMap<>();

    static {
        AudioSourceManagers.registerRemoteSources(PLAYER_MANAGER);
        EventDispatcher.registerListener(new Music());
    }
    
    private static void sendMessage(@Nonnull IChannel channel, @Nonnull String message) {
        IMessage iMessage = CommandReceivedEvent.sendMessage(channel, message);
        IGuild guild = channel.getGuild();

        if (lastMessage.containsKey(guild)) {
            try {
                lastMessage.get(guild).delete();
            } catch (MissingPermissionsException ignored) {}
        }

        lastMessage.put(guild, iMessage);
    }

    private static void sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        IMessage iMessage = CommandReceivedEvent.sendMessage(channel, embed);
        IGuild guild = channel.getGuild();

        if (lastMessage.containsKey(guild)) {
            try {
                lastMessage.get(guild).delete();
            } catch (MissingPermissionsException ignored) {}
        }

        lastMessage.put(guild, iMessage);
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

    @CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.DELETE_COMMAND_MESSAGE})
    public static void play(CommandReceivedEvent event) {
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();
        String searchString = String.join(" ", event.ARGS);

        PLAYER_MANAGER.loadItem(searchString, new AudioResultHandler(channel, author, searchString));
    }

    @CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
    public static void skip(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        AudioTrack currentTrack = musicManager.getScheduler().getCurrentTrack();
        if (currentTrack == null) {
            sendMessage(channel, "There isn't a track currently playing.");
            return;
        }

        SKIP_VOTES.computeIfAbsent(guild, k -> new ArrayList<>());

        IUser author = event.getAuthor();
        if (SKIP_VOTES.get(guild).contains(author)) {
            sendMessage(channel, "You have already voted to skip this track.");
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

        TrackUserData trackUserData = currentTrack.getUserData(TrackUserData.class);
        if (votes >= requiredVotes || author == trackUserData.SUBMITTER) {
            SKIP_VOTES.get(guild).clear();
            musicManager.getScheduler().skip();
            sendMessage(channel, "Skipped the current track.");
        } else {
            String response = String.format("Votes: %d/%.0f", votes, requiredVotes);
            sendMessage(channel, response);
        }
    }

    @CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
    public static void pause(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            sendMessage(channel, "There isn't a track currently playing.");
            return;
        }

        if (musicManager.getScheduler().isPaused()) {
            sendMessage(channel, "The current track is already paused. Use " + BanterBot4J.BOT_PREFIX + "resume to resume it.");
            return;
        }

        musicManager.getScheduler().pause();
        sendMessage(channel, "Paused the current track.");
    }

    @CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
    public static void resume(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            sendMessage(channel, "There isn't a track currently playing.");
            return;
        }

        if (!musicManager.getScheduler().isPaused()) {
            sendMessage(channel, "There isn't a track currently paused.");
            return;
        }

        musicManager.getScheduler().resume();
        sendMessage(channel, "Resumed the current track.");
    }

    @CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
    public static void stop(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();

        GuildMusicManager musicManager = getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentTrack() == null) {
            sendMessage(channel, "There aren't any tracks currently playing or in the queue.");
            return;
        }

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();

        SimpleEntry<IGuild, IUser> pair = new SimpleEntry<>(guild, author);
        UNDO_STOP_CACHE.put(pair, scheduler.cloneTracks());

        scheduler.stop();
        sendMessage(
                channel,
                "Stopped and removed all tracks from the queue.\n" +
                "You have one minute to restore them back to the queue using " + BanterBot4J.BOT_PREFIX + "undostop."
        );
    }

    @CommandInfo(tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
    public static void undostop(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();
        SimpleEntry<IGuild, IUser> pair = new SimpleEntry<>(guild, author);
        List<AudioTrack> tracks = UNDO_STOP_CACHE.getIfPresent(pair);
        if (tracks == null) {
            sendMessage(channel, "You haven't stopped any tracks in the last minute.");
            return;
        }

        UNDO_STOP_CACHE.invalidate(pair);

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();
        tracks.addAll(scheduler.cloneTracks());
        scheduler.stop();
        scheduler.queue(tracks);
        sendMessage(channel, "Restored all stopped tracks.");
    }

    @CommandInfo(tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
    public static void queue(CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();
        IUser author = event.getAuthor();

        TrackScheduler scheduler = getGuildMusicManager(guild).getScheduler();
        AudioTrack currentTrack = scheduler.getCurrentTrack();
        if (currentTrack == null) {
            sendMessage(channel, "There are no tracks currently playing or in the queue.");
            return;
        }

        TimeUnit msUnit = TimeUnit.MILLISECONDS;
        long trackDuration = currentTrack.getDuration();
        String trackDurationString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackDuration) % 24,
                msUnit.toMinutes(trackDuration) % 60,
                msUnit.toSeconds(trackDuration) % 60
        );

        long trackTimeLeft = trackDuration - currentTrack.getPosition();
        String trackTimeLeftString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackTimeLeft) % 24,
                msUnit.toMinutes(trackTimeLeft) % 60,
                msUnit.toSeconds(trackTimeLeft) % 60
        );

        String trackStatus = "Playing";
        if (scheduler.isPaused()) {
            trackStatus = "Paused";
        }

        List<AudioTrack> queue = scheduler.getQueue();
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName(author.getDisplayName(guild))
                .withAuthorIcon(author.getAvatarURL())
                .withTitle("Currently playing: " + currentTrack.getInfo().title)
                .appendDescription("\n")
                .appendDescription("Track status: " + trackStatus)
                .appendDescription("\n")
                .appendDescription("Track duration: " + trackDurationString)
                .appendDescription("\n")
                .appendDescription("Time remaining: " + trackTimeLeftString)
                .appendField("Tracks in queue", String.valueOf(queue.size()), false);

        if (scheduler.hasNextTrack()) {
            long queueDuration = 0;
            for (AudioTrack track : queue) {
                queueDuration += track.getDuration();
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

    @TrackEventHandler(event = TrackStartEvent.class)
    public static void handle(@Nonnull TrackStartEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        IGuild guild = trackUserData.CHANNEL.getGuild();
        IVoiceState voiceState = trackUserData.SUBMITTER.getVoiceStateForGuild(guild);
        IVoiceChannel authorVoiceChannel = voiceState.getChannel();
        IVoiceChannel botVoiceChannel = authorVoiceChannel.getClient().getOurUser().getVoiceStateForGuild(guild).getChannel();
        if (botVoiceChannel != authorVoiceChannel) {
            authorVoiceChannel.join();
        }

        String response = String.format("Now playing `%s`.", track.getInfo().title);
        sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = TrackQueueEvent.class)
    public static void handle(@Nonnull TrackQueueEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        String response = String.format("Added `%s` to the queue.", track.getInfo().title);
        sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = NoMatchesEvent.class)
    public static void handle(@Nonnull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        sendMessage(handler.getChannel(), response);
    }

    @TrackEventHandler(event = LoadFailedEvent.class)
    public static void handle(@Nonnull LoadFailedEvent event) {
        String response = String.format("Error playing track: %s", event.getException().getMessage());
        sendMessage(event.getHandler().getChannel(), response);
    }

    @TrackEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@Nonnull PlaylistQueueEvent event) {
        List<AudioTrack> tracks = event.TRACKS;
        AudioTrack firstTrack = tracks.get(0);
        IChannel channel = firstTrack.getUserData(TrackUserData.class).CHANNEL;
        String response = String.format("Added %d tracks to the queue.", tracks.size());
        sendMessage(channel, response);
    }

}
