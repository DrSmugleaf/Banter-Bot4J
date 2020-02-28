package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.TransformerSet;
import com.github.drsmugleaf.database.model.EveTimerModel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

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
        },
        description = "Create a structure timer for Eve Online"
)
public class EveTimer extends Command {

    @Argument(position = 1, examples = "Fortizar")
    private String structure;

    @Argument(position = 2, examples = "Jita")
    private String system;

    @Argument(position = 3, examples = "4d15h30m")
    private ZonedDateTime date;

    @Nullable
    private static Long parseDatePortion(String input, Pattern regex) {
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        return null;
    }

    private static boolean exists(String structure, String system) {
        EveTimerModel timer = new EveTimerModel(null, structure, system, null, null);
        return !timer.get().isEmpty();
    }

    @Override
    public void run() {
        EveTimerModel timer = new EveTimerModel(null, structure, system, null, null);
        if (!timer.get().isEmpty()) {
            String response = "Timer for structure %s in system %s already exists";
            response = String.format(response, structure, system);
            reply(response).subscribe();
            return;
        }

        EVENT
                .getMessage()
                .getChannel()
                .map(MessageChannel::getId)
                .map(Snowflake::asLong)
                .zipWith(
                        Mono.justOrEmpty(
                                EVENT
                                        .getMessage()
                                        .getAuthor()
                                        .map(User::getId)
                                        .map(Snowflake::asLong)
                        )
                )
                .flatMap(tuple -> {
                    Long channelId = tuple.getT1();
                    Long authorId = tuple.getT2();
                    long dateMillis = date.toInstant().toEpochMilli();

                    EveTimerModel eveTimerModel = new EveTimerModel(channelId, structure, system, dateMillis, authorId);
                    eveTimerModel.save();
                    EveTimerModel.createTimer(EVENT.getClient(), eveTimerModel);

                    return reply("Created timer for structure " + structure + " in system " + system + " in " + ARGUMENTS.get(2));
                })
                .subscribe();
    }

    @Override
    public TransformerSet getTransformers() {
        return TransformerSet.of(
                ZonedDateTime.class, (s, e) -> {
                    Long days = parseDatePortion(s, Pattern.compile("(\\d+)d"));
                    Long hours = parseDatePortion(s, Pattern.compile("(\\d+)h"));
                    Long minutes = parseDatePortion(s, Pattern.compile("(\\d+)m"));
                    Long seconds = parseDatePortion(s, Pattern.compile("(\\d+)s"));

                    ZonedDateTime time = ZonedDateTime.now(EveTimerModel.EVE_TIMEZONE);
                    if (days != null) time = time.plusDays(days);
                    if (hours != null) time = time.plusHours(hours);
                    if (minutes != null) time = time.plusMinutes(minutes);
                    if (seconds != null) time = time.plusSeconds(seconds);

                    return time;
                }
        );
    }

}
