package com.github.drsmugbrain;

import com.github.drsmugbrain.models.Member;
import com.github.drsmugbrain.util.Annotations;
import com.github.drsmugbrain.util.Bot;
import com.google.api.services.youtube.YouTube;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by declan on 04/04/2017.
 */
public class CommandHandler {

    // A static map of commands mapping from command string to the functional impl
    private static Map<String, Command> commandMap = new HashMap<>();

    public static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    private static YouTube youtube;

    // Statically populate the commandMap with the intended functionality
    // Might be better practise to do this from an instantiated objects constructor
    static {
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);

        List<Method> commands = Annotations.findMethodsWithAnnotations(com.github.drsmugbrain.commands.Command.class);
        if(commands != null) {
            commands.forEach((method -> {
                Command command = (event, args) -> {
                    try {
                        method.invoke(method.getClass(), event, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        Bot.LOGGER.error("Error running command", e);
                    }
                };
                commandMap.put(method.getName(), command);
            }));
        }

        commandMap.put("exampleembed", (event, args) -> {

            EmbedBuilder builder = new EmbedBuilder();

            builder.appendField("fieldTitleInline", "fieldContentInline", true);
            builder.appendField("fieldTitleInline2", "fieldContentInline2", true);
            builder.appendField("fieldTitleNotInline", "fieldContentNotInline", false);
            builder.appendField(":tada: fieldWithCoolThings :tada:", "[hiddenLink](http://i.imgur.com/Y9utuDe.png)", false);

            builder.withAuthorName("authorName");
            builder.withAuthorIcon("http://i.imgur.com/PB0Soqj.png");
            builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

            builder.withColor(255, 0, 0);
            builder.withDesc("withDesc");
            builder.withDescription("withDescription");
            builder.withTitle("withTitle");
            builder.withTimestamp(100);
            builder.withUrl("http://i.imgur.com/IrEVKQq.png");
            builder.withImage("http://i.imgur.com/agsp5Re.png");

            builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
            builder.withFooterText("footerText");
            builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
            builder.withThumbnail("http://www.gstatic.com/webp/gallery/1.webp");

            builder.appendDesc(" + appendDesc");
            builder.appendDescription(" + appendDescription");

            RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));

        });
    }

    @EventSubscriber
    public void handle(MessageReceivedEvent event) {
        // Note for error handling, you'll probably want to log failed commands with a logger or sout
        // In most cases it's not advised to annoy the user with a reply incase they didn't intend to trigger a
        // command anyway, such as a user typing ?notacommand, the bot should not say "notacommand" doesn't exist in
        // most situations. It's partially good practise and partially developer preference

        // Given a message "/test arg1 arg2", argArray will contain ["/test", "arg1", "arg"]
        String[] argArray = event.getMessage().getContent().split(" ");

        // First ensure at least the command and prefix is present, the arg length can be handled by your command func
        if(argArray.length == 0)
            return;

        // Check if the first arg (the command) starts with the prefix defined in the utils class
        if(!argArray[0].startsWith(Bot.BOT_PREFIX))
            return;

        // Filter out blacklisted users
        Long userID = event.getAuthor().getLongID();
        Long guildID = event.getGuild().getLongID();
        Member member = Member.get(userID, guildID);
        if(member != null && member.isBlacklisted) return;

        // Extract the "command" part of the first arg out by just ditching the first character
        String commandStr = argArray[0].substring(Bot.BOT_PREFIX.length());

        // Load the rest of the args in the array into a List for safer access
        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command

        // Instead of delegating the work to a switch, automatically do it via calling the mapping if it exists
        if(commandMap.containsKey(commandStr))
            commandMap.get(commandStr).runCommand(event, argsList);
    }

}
