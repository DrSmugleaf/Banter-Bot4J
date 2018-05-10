package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.database.models.Channel;
import com.github.drsmugleaf.database.models.EveTimer;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Eve extends AbstractCommand {

    @Nonnull
    private static final ZoneOffset EVE_TIMEZONE = ZoneOffset.UTC;

    private static final Timer TIMER = new Timer("Eve Structure Alert Timer", true);

    @Nullable
    private static Long parseDatePortion(@Nonnull String input, @Nonnull Pattern regex) {
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        return null;
    }

    @Nonnull
    private static Long parseDate(@Nonnull String date) {
        Long days = parseDatePortion(date, Pattern.compile("(\\d+)d"));
        Long hours = parseDatePortion(date, Pattern.compile("(\\d+)h"));
        Long minutes = parseDatePortion(date, Pattern.compile("(\\d+)m"));
        Long seconds = parseDatePortion(date, Pattern.compile("(\\d+)s"));

        ZonedDateTime time = ZonedDateTime.now(EVE_TIMEZONE);
        if (days != null) time = time.plusDays(days);
        if (hours != null) time = time.plusHours(hours);
        if (minutes != null) time = time.plusMinutes(minutes);
        if (seconds != null) time = time.plusSeconds(seconds);

        return time.toInstant().toEpochMilli();
    }

    private static void sendAlert(@Nonnull IDiscordClient client, @Nonnull EveTimer timer, boolean skipped) {
        String structure = timer.structure;
        String system = timer.system;
        Date date = new Date(timer.date);
        IUser submitter = client.fetchUser(timer.submitter.id);
        IChannel channel = client.getChannelByID(timer.channel.id);
        String submitterName = submitter.getDisplayName(channel.getGuild());
        EmbedBuilder builder = new EmbedBuilder();

        builder
                .withTitle(skipped ? "SKIPPED STRUCTURE ALERT" : "Structure alert")
                .appendField("Structure", structure, true)
                .appendField("System", system, true)
                .appendField("Submitted by", submitterName, true)
                .withFooterText(date.toString())
                .withColor(skipped ? Color.RED : Color.GREEN);

        sendMessage(channel, "@everyone Structure timer", builder.build());

        timer.delete();
    }

    private static void sendAlert(@Nonnull IDiscordClient client, @Nonnull EveTimer timer) {
        sendAlert(client, timer, false);
    }

    private static void createTimer(@Nonnull IDiscordClient client, @Nonnull EveTimer timer) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendAlert(client, timer);
            }
        };

        TIMER.schedule(task, timer.date - System.currentTimeMillis());
    }

    @CommandInfo
    public static void eveTimer(MessageReceivedEvent event, List<String> args) {
        String joinedArgs = String.join(" ", args);
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(joinedArgs);

        args.clear();
        while (matcher.find()) {
            args.add(matcher.group(1));
        }

        if (args.isEmpty() || args.size() != 3) {
            sendMessage(
                    event.getChannel(),
                    "Format: !evetimer \"structure\" \"system\" \"date\" (6d2h45m)\n" +
                    "Example: !evetimer \"Fortizar\" \"7RM\" \"4d15h30m\""
            );
            return;
        }

        Channel channel = new Channel(event.getChannel().getLongID()).get().get(0);
        String structure = args.get(0);
        String system = args.get(1);
        Long date = parseDate(args.get(2));
        Long submitter = event.getAuthor().getLongID();
        EveTimer eveTimerModel = new EveTimer(channel, structure, system, date, submitter);
        eveTimerModel.save();

        createTimer(event.getClient(), eveTimerModel);
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            List<EveTimer> timers = new EveTimer(null, null, null, null, null).get();

            for (EveTimer timer : timers) {
                ZonedDateTime timerDate = Instant.ofEpochMilli(timer.date).atZone(EVE_TIMEZONE);
                ZonedDateTime now = ZonedDateTime.now(EVE_TIMEZONE);

                if (timerDate.isBefore(now)) {
                    sendAlert(event.getClient(), timer, true);
                } else {
                    createTimer(event.getClient(), timer);
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
