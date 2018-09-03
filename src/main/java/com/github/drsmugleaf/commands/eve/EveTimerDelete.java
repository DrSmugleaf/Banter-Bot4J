package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.EveTimerModel;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 03/09/2018
 */
@CommandInfo(
        name = "evetimerdelete",
        aliases = {
                "evetimer delete",
                "evetimerdel", "evetimer del",
                "evetimerremove", "evetimer remove",
                "evetimerrem", "evetimer rem"
        }
)
public class EveTimerDelete extends Command {

    protected EveTimerDelete(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String invalidFormatResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"structure\" \"system\"\n\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "evetimer delete \"Fortizar\" \"Jita\"";
    }

    @Override
    public void run() {
        if (ARGS.size() != 2) {
            EVENT.reply(invalidFormatResponse());
            return;
        }

        IChannel channel = EVENT.getChannel();
        long channelID = channel.getLongID();
        String structure = ARGS.get(0);
        String system = ARGS.get(1);

        EveTimerModel timer = new EveTimerModel(channelID, structure, system, null, null);

        List<EveTimerModel> timers = timer.get();
        if (timers.isEmpty()) {
            String response = String.format(
                    "No timer exists assigned to channel %s with structure %s and system %s",
                    channel, structure, system
            );

            EVENT.reply(response);
            return;
        }

        timer = timers.get(0);
        if (!EveTimerModel.deleteTimer(timer)) {
            String response = String.format(
                    "Couldn't delete timer assigned to channel %s with structure %s and system %s",
                    channel, structure, system
            );

            EVENT.reply(response);
            return;
        }

        String response = String.format(
                "Deleted timer assigned to channel %s with structure %s and system %s",
                channel, structure, system
        );

        EVENT.reply(response);
    }

}
