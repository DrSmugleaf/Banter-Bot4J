package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.model.EveTimerModel;

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
        },
        description = "Delete a structure timer for Eve Online"
)
public class EveTimerDelete extends Command {

    @Argument(position = 1, examples = "Fortizar")
    private String structure;

    @Argument(position = 2, examples = "Jita")
    private String system;

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getChannel()
                .flatMap(channel -> {
                    long channelID = channel.getId().asLong();
                    EveTimerModel timer = new EveTimerModel(channelID, structure, system, null, null);
                    List<EveTimerModel> timers = timer.get();

                    if (timers.isEmpty()) {
                        String response = String.format(
                                "No timer exists assigned to this channel with structure %s and system %s",
                                structure, system
                        );

                        return reply(response);
                    }

                    timer = timers.get(0);
                    if (!EveTimerModel.deleteTimer(timer)) {
                        String response = String.format(
                                "Couldn't delete timer assigned to this channel with structure %s and system %s",
                                structure, system
                        );

                        return reply(response);
                    }

                    String response = String.format(
                            "Deleted timer assigned to this channel with structure %s and system %s",
                            structure, system
                    );

                    return reply(response);
                })
                .subscribe();
    }

}
