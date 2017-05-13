package com.github.drsmugbrain.commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.audio.AudioPlayer;

import java.util.List;
import java.util.Random;

/**
 * Created by Brian on 13/05/2017.
 */
public class Basic {

    public static void echo(MessageReceivedEvent event, List<String> args){
        String echo = String.join(" ", args);
        try {
            event.getMessage().delete();
        }catch(MissingPermissionsException e){/* Don't do anything */}
        RequestBuffer.request(() -> event.getChannel().sendMessage(echo));
    }

    public static void roll(MessageReceivedEvent event, List<String> args){
        int randomNumber = new Random().nextInt(100) + 1;
        event.getChannel().sendMessage(String.valueOf(randomNumber));
    }

    public static void join(MessageReceivedEvent event, List<String> args){
        IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();
        if(userVoiceChannel == null)
            return;
        userVoiceChannel.join();
    }

    public static void leave(MessageReceivedEvent event, List<String> args){
        IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();
        if(botVoiceChannel == null)
            return;
        AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
        audioP.clear();
        botVoiceChannel.leave();
    }
}
