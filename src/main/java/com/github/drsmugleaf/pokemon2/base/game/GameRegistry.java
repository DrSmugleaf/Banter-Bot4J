package com.github.drsmugleaf.pokemon2.base.game;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public class GameRegistry extends Registry<IGame> {

    private final ImmutableMap<String, IGame> CORE_GAMES;
    private final ImmutableMap<String, IGame> SIDE_GAMES;

    public GameRegistry(Map<String, IGame> games) {
        super(games);
        CORE_GAMES = games
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isCore())
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                                ImmutableMap::copyOf
                        )
                );

        SIDE_GAMES = games
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isCore())
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                                ImmutableMap::copyOf
                        )
                );
    }

    public GameRegistry(Set<IGame> core, Set<IGame> side) {
        this(Stream.of(core, side).flatMap(Collection::stream).collect(Collectors.toMap(IGame::getName, Function.identity())));
    }

    public GameRegistry(IGame... games) {
        this(Arrays.stream(games).collect(Collectors.toMap(IGame::getName, Function.identity())));
    }

    public ImmutableMap<String, IGame> getCoreGames() {
        return CORE_GAMES;
    }

    public ImmutableMap<String, IGame> getSideGames() {
        return SIDE_GAMES;
    }

}
