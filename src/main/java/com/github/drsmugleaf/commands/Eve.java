package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.database.models.EveDowntimeUser;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Eve extends AbstractCommand {

    @Nonnull
    private static final ZoneOffset EVE_TIMEZONE = ZoneOffset.UTC;

    @Nonnull
    private static final Timer STRUCTURE_TIMER = new Timer("Eve Online Structure Alert Timer", true);

    @Nonnull
    private static final Map<EveTimer, TimerTask> TASKS = new HashMap<>();

    @Nonnull
    private static List<String> getQuotedArguments(List<String> args) {
        List<String> groupedArguments = new ArrayList<>();
        String joinedArgs = String.join(" ", args);
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(joinedArgs);

        while (matcher.find()) {
            groupedArguments.add(matcher.group(1));
        }

        return groupedArguments;
    }

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

        deleteTask(timer);
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

        TASKS.put(timer, task);
        STRUCTURE_TIMER.schedule(task, timer.date - System.currentTimeMillis());
    }

    private static boolean deleteTask(@Nonnull EveTimer timer) {
        for (Map.Entry<EveTimer, TimerTask> entry : TASKS.entrySet()) {
            EveTimer entryTimer = entry.getKey();
            TimerTask entryTask = entry.getValue();
            String entryTimerStructure = entryTimer.structure;
            String timerStructure = timer.structure;
            String entryTimerSystem = entryTimer.system;
            String timerSystem = timer.system;

            if (entryTimerStructure.equalsIgnoreCase(timerStructure) && entryTimerSystem.equalsIgnoreCase(timerSystem)) {
                entryTask.cancel();
                TASKS.remove(timer);
                timer.delete();
                return true;
            }
        }

        return false;
    }

    private static void deleteTimer(MessageReceivedEvent event, List<String> args) {
        IChannel channel = event.getChannel();
        String structure = args.get(0);
        String system = args.get(1);
        EveTimer timer = new EveTimer(channel.getLongID(), structure, system, null, null);

        List<EveTimer> timers = timer.get();
        if (timers.isEmpty()) {
            String response = "No timer exists assigned to channel %s with structure %s and system %s";
            response = String.format(response, channel, structure, system);
            sendMessage(channel, response);
        } else {
            timer = timers.get(0);
            if (!deleteTask(timer)) {
                String response = "Couldn't delete timer assigned to channel %s with structure %s and system %s";
                response = String.format(response, channel, structure, system);
                sendMessage(channel, response);
                return;
            }

            String response = "Deleted timer assigned to channel %s with structure %s and system %s";
            response = String.format(response, channel, structure, system);
            sendMessage(channel, response);
        }
    }

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"structure\" \"system\" \"date\" (6d2h45m)\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"structure\" \"system\"\n\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"Fortizar\" \"7RM\" \"4d15h30m\"\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"Fortizar\" \"7RM\"";
    }

    private static boolean exists(@Nonnull String structure, @Nonnull String system) {
        EveTimer timer = new EveTimer(null, structure, system, null, null);
        return !timer.get().isEmpty();
    }

    @CommandInfo
    public static void eveTimer(MessageReceivedEvent event, List<String> args) {
        List<String> quotedArguments = getQuotedArguments(args);
        if (quotedArguments.isEmpty() || quotedArguments.size() != 3) {
            if (args.size() == 3) {
                String firstArg = args.get(0);
                if (firstArg.equalsIgnoreCase("delete") || firstArg.equalsIgnoreCase("\"delete\"")) {
                    deleteTimer(event, quotedArguments);
                    return;
                }
            }

            sendMessage(event.getChannel(), wrongFormatResponse());
            return;
        }

        IChannel channel = event.getChannel();
        String structure = quotedArguments.get(0);
        String system = quotedArguments.get(1);
        if (exists(structure, system)) {
            String response = "Timer for structure %s in system %s already exists";
            response = String.format(response, structure, system);
            sendMessage(event.getChannel(), response);
            return;
        }

        Long date = parseDate(quotedArguments.get(2));
        Long submitter = event.getAuthor().getLongID();
        EveTimer eveTimerModel = new EveTimer(channel.getLongID(), structure, system, date, submitter);
        eveTimerModel.save();

        createTimer(event.getClient(), eveTimerModel);
    }

    @CommandInfo
    public static void eveDowntime(MessageReceivedEvent event, List<String> args) {
        long authorID = event.getAuthor().getLongID();
        EveDowntimeUser user = new EveDowntimeUser(event.getAuthor().getLongID());
        IChannel channel = event.getChannel();
        if (user.get().isEmpty()) {
            user.createIfNotExists();
            sendMessage(channel, "I will notify you when the eve online tranquility server comes back up from maintenance each day.");
        } else {
            user.delete();
            sendMessage(channel, "I will no longer notify you when the server comes back online.");
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            List<EveTimer> timers = new EveTimer(null, null, null, null, null).get();

            for (EveTimer timer : timers) {
                ZonedDateTime timerDate = Instant.ofEpochMilli(timer.date).atZone(EVE_TIMEZONE);
                ZonedDateTime now = ZonedDateTime.now(EVE_TIMEZONE);
                IDiscordClient client = event.getClient();

                if (timerDate.isBefore(now)) {
                    sendAlert(client, timer, true);
                } else {
                    createTimer(client, timer);
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
