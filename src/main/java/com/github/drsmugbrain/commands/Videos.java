package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.BotUtils;
import com.github.drsmugbrain.CommandHandler;
import com.github.drsmugbrain.VideoManager;
import com.github.drsmugbrain.lavaplayer.GuildMusicManager;
import com.github.drsmugbrain.lavaplayer.YoutubeSearch;
import com.google.api.services.youtube.model.Video;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.apache.commons.lang3.ObjectUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Brian on 13/05/2017.
 */
public class Videos {
    private static final Map<Long, GuildMusicManager> musicManagers  = new HashMap<>();

    // Commands:
    public static void play(MessageReceivedEvent event, List<String> args){
        IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();

        if(botVoiceChannel == null) {
            IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();
            if(userVoiceChannel == null)
                return;
            userVoiceChannel.join();
        }

        // Turn the args back into a string separated by space
        String searchStr = String.join(" ", args);

        if(searchStr.startsWith("http")) {
            loadAndPlay(event.getChannel(), searchStr);
        } else {
            String url = YoutubeSearch.search(searchStr);
            loadAndPlay(event.getChannel(), url);
        }
    }

    public static void skip(MessageReceivedEvent event, List<String> args){
        boolean exit = false;
        try{
            exit = musicManagers.get(event.getGuild().getLongID()).player.getPlayingTrack() == null;
        }catch(NullPointerException e){
            exit = true;
        }

        if(exit){
            BotUtils.sendMessage(event.getChannel(), "No hay canciones en cola");
            return;
        }

        if(!VideoManager.votes.contains(event.getAuthor())) {
            VideoManager.votes.add(event.getAuthor());
        }

        List<IUser> connectedUsers = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().getUsersHere();
        int humanConnectedUsers = 0;
        for(IUser user : connectedUsers){
            if(!user.isBot()){
                humanConnectedUsers++;
            }
        }
        int votes = VideoManager.votes.size();
        int requiredVotes = humanConnectedUsers / 2;

        if(votes >= requiredVotes){
            skipTrack(event.getChannel());
            VideoManager.votes.clear();
        }else{
            BotUtils.sendMessage(event.getChannel(), "Votos: "+votes+"/"+requiredVotes);
        }

    }

    // End commands

    // Util
    private static synchronized GuildMusicManager getGuildAudioPlayer(IGuild guild) {
        long guildId = Long.parseLong(guild.getID());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(CommandHandler.playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setAudioProvider(musicManager.getAudioProvider());

        return musicManager;
    }

    private static void loadAndPlay(final IChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        CommandHandler.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                BotUtils.sendMessage(channel, "Adding to queue " + track.getInfo().title);

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                BotUtils.sendMessage(channel, "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")");

                play(musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                BotUtils.sendMessage(channel, "Nothing found by " + trackUrl);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                BotUtils.sendMessage(channel, "Could not play: " + exception.getMessage());
            }
        });
    }

    private static void play(GuildMusicManager musicManager, AudioTrack track) {

        musicManager.scheduler.queue(track);
    }

    private static void skipTrack(IChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        BotUtils.sendMessage(channel, "Skipped to next track.");
    }

}
