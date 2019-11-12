package com.github.drsmugleaf.pokemon2.base.game;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public class GameRegistry extends Registry<IGame> {

    private final ImmutableMap<String, IGame> CORE_GAMES;
    private final ImmutableMap<String, IGame> SIDE_GAMES;

    public GameRegistry(IGame... games) {
        super(games);
        Map<String, IGame> core = new HashMap<>();
        Map<String, IGame> side = new HashMap<>();

        for (IGame game : games) {
            String name = game.getName();

            if (game.isCore()) {
                core.put(name, game);
            } else {
                side.put(name, game);
            }
        }

        CORE_GAMES = ImmutableMap.copyOf(core);
        SIDE_GAMES = ImmutableMap.copyOf(side);
    }

    public ImmutableMap<String, IGame> getCoreGames() {
        return CORE_GAMES;
    }

    public ImmutableMap<String, IGame> getSideGames() {
        return SIDE_GAMES;
    }

}
