package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.database.models.Channel;
import com.github.drsmugleaf.database.models.EveTimer;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    private static Long parseDatePortion(String input, Pattern regex) {
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        return null;
    }

    @Nonnull
    private static Long parseDate(String date) {
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

    @CommandInfo
    public static void eveTimer(MessageReceivedEvent event, List<String> args) {
        if (args.isEmpty() || args.size() != 3) {
            sendMessage(event.getChannel(), "Format: !evetimer \"structure\" \"system\", \"date\" (6d2h45m)");
            return;
        }

        String joinedArgs = String.join(" ", args);
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(joinedArgs);

        args.clear();
        while (matcher.find()) {
            args.add(matcher.group(1));
        }

        Channel channel = new Channel(event.getChannel().getLongID()).get().get(0);
        String structure = args.get(0);
        String system = args.get(1);
        Long date = parseDate(args.get(2));
        EveTimer eveTimerModel = new EveTimer(channel, structure, system, date);
        eveTimerModel.save();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendMessage(event.getChannel(), "Structure alert: " + structure + " in " + system);
            }
        };
        TIMER.schedule(task, date - System.currentTimeMillis());
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            List<EveTimer> timers = new EveTimer(null, null, null, null).get();

            for (EveTimer timer : timers) {
                ZonedDateTime timerDate = Instant.ofEpochMilli(timer.date).atZone(EVE_TIMEZONE);
                ZonedDateTime now = ZonedDateTime.now(EVE_TIMEZONE);

                IChannel channel = event.getClient().getChannelByID(timer.channel.id);
                String structure = timer.structure;
                String system = timer.system;
                if (timerDate.isBefore(now)) {
                    String response = "Skipped alert: %s in %s for date %s";
                    sendMessage(channel, String.format(response, timer.structure, timer.system, timer.date));
                } else {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            sendMessage(channel, "Structure alert: " + structure + " in " + system);
                        }
                    };
                    TIMER.schedule(task, timerDate.toInstant().toEpochMilli() - System.currentTimeMillis());
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
