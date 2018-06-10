package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.EveTimerModel;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public class EveTimer extends Command {

    @Nonnull
    private static Long parseDate(@Nonnull String date) {
        Long days = parseDatePortion(date, Pattern.compile("(\\d+)d"));
        Long hours = parseDatePortion(date, Pattern.compile("(\\d+)h"));
        Long minutes = parseDatePortion(date, Pattern.compile("(\\d+)m"));
        Long seconds = parseDatePortion(date, Pattern.compile("(\\d+)s"));

        ZonedDateTime time = ZonedDateTime.now(EveTimerModel.EVE_TIMEZONE);
        if (days != null) time = time.plusDays(days);
        if (hours != null) time = time.plusHours(hours);
        if (minutes != null) time = time.plusMinutes(minutes);
        if (seconds != null) time = time.plusSeconds(seconds);

        return time.toInstant().toEpochMilli();
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
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"structure\" \"system\" \"date\" (6d2h45m)\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"structure\" \"system\"\n\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"Fortizar\" \"7RM\" \"4d15h30m\"\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"Fortizar\" \"7RM\"";
    }

    private static boolean exists(@Nonnull String structure, @Nonnull String system) {
        EveTimerModel timer = new EveTimerModel(null, structure, system, null, null);
        return !timer.get().isEmpty();
    }

    @Override
    protected void run(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply(wrongFormatResponse());
            return;
        }

        String firstArg = event.ARGS.first();
        if (firstArg.equalsIgnoreCase("delete")) {
            EveTimerModel.deleteTimer(event);
            return;
        }

        IChannel channel = event.getChannel();
        String structure = event.ARGS.get(0);
        String system = event.ARGS.get(1);
        if (exists(structure, system)) {
            String response = "Timer for structure %s in system %s already exists";
            response = String.format(response, structure, system);
            event.reply(response);
            return;
        }

        Long date = parseDate(event.ARGS.get(2));
        Long submitter = event.getAuthor().getLongID();
        EveTimerModel eveTimerModel = new EveTimerModel(channel.getLongID(), structure, system, date, submitter);
        eveTimerModel.save();

        EveTimerModel.createTimer(event.getClient(), eveTimerModel);
    }

}
