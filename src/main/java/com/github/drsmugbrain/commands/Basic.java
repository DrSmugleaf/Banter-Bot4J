package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.audio.AudioPlayer;

import java.util.List;
import java.util.Random;

/**
 * Created by Brian on 13/05/2017.
 */
public class Basic {

    private static final String MAGIC_8_BALL_RESPONSES[] = {
            "It is certain", "It is decidedly so", "Without a doubt", "Yes definitely", "You may rely on it",
            "As I see it, yes", "Most likely", "Outlook good", "Yes", "Signs point to yes",
            "Reply hazy try again", "Ask again later", "Better not tell you now", "Cannot predict now",
            "Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
            "Outlook not so good", "Very doubtful"
    };

    public static void echo(MessageReceivedEvent event, List<String> args){
        String echo = String.join(" ", args);
        try {
            event.getMessage().delete();
        } catch(MissingPermissionsException e) {/* Don't do anything */}
        Bot.sendMessage(event.getChannel(), echo);
    }

    public static void roll(MessageReceivedEvent event, List<String> args){
        int randomNumber = new Random().nextInt(100) + 1;
        Bot.sendMessage(event.getChannel(), String.valueOf(randomNumber));
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

    public static void magic8ball(MessageReceivedEvent event, List<String> args) {
        int randomID = new Random().nextInt(Basic.MAGIC_8_BALL_RESPONSES.length);
        Bot.sendMessage(event.getChannel(), Basic.MAGIC_8_BALL_RESPONSES[randomID]);
    }

}
