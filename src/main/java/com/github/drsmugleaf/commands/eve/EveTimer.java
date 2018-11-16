package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.EveTimerModel;
import sx.blah.discord.handle.obj.IChannel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        aliases = {
                "evetimercreate", "evetimer create",
                "evetimernew", "evetimer new"
        }
)
public class EveTimer extends Command {

    protected EveTimer(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @NotNull
    private static Long parseDate(@NotNull String date) {
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
    private static Long parseDatePortion(@NotNull String input, @NotNull Pattern regex) {
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        return null;
    }

    @NotNull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"structure\" \"system\" \"date\"\n\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer \"Fortizar\" \"Jita\" \"4d15h30m\"";
    }

    private static boolean exists(@NotNull String structure, @NotNull String system) {
        EveTimerModel timer = new EveTimerModel(null, structure, system, null, null);
        return !timer.get().isEmpty();
    }

    @Override
    public void run() {
        if (ARGS.size() != 3) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        IChannel channel = EVENT.getChannel();
        String structure = ARGS.get(0);
        String system = ARGS.get(1);
        if (exists(structure, system)) {
            String response = "Timer for structure %s in system %s already exists";
            response = String.format(response, structure, system);
            EVENT.reply(response);
            return;
        }

        Long date = parseDate(ARGS.get(2));
        Long submitter = EVENT.getAuthor().getLongID();
        EveTimerModel eveTimerModel = new EveTimerModel(channel.getLongID(), structure, system, date, submitter);
        eveTimerModel.save();

        EveTimerModel.createTimer(EVENT.getClient(), eveTimerModel);
    }

}
