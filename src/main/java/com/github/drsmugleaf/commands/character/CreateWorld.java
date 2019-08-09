package com.github.drsmugleaf.commands.character;

import com.github.drsmugleaf.charactersheets.location.Location;
import com.github.drsmugleaf.charactersheets.location.World;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import discord4j.core.object.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
@CommandInfo(
        aliases = {"cw"},
        description = "Create a world"
)
public class CreateWorld extends Command {

    private static final Map<User, World> WORLDS = new HashMap<>();

    @Argument(position = 1, examples = "Hoenn", maxWords = Integer.MAX_VALUE)
    private String name;

    @Override
    public void run() {
        ARGUMENTS.remove(0);
        Map<String, Location> locations = new HashMap<>();
        for (String locationName : ARGUMENTS) {
            Location location = new Location(locationName);
            locations.put(locationName, location);
        }

        World world = new World(name, locations);
        EVENT
                .getMessage()
                .getAuthor()
                .ifPresent(author -> WORLDS.put(author, world));
    }

}
