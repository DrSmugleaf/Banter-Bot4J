package com.github.drsmugleaf.pokemon.base.format;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public class Format implements IFormat {

    private final String NAME;
    private final String ABBREVIATION;
    private final ImmutableSet<String> CLAUSES;
    private final ImmutableSet<String> BANNED_POKEMON;
    private final ImmutableSet<String> BANNED_MOVES;
    private final ImmutableSet<String> BANNED_TIERS;
    private final ImmutableSet<String> BANNED_ITEMS;
    private final ImmutableSet<String> BANNED_ABILITIES;
    private final ImmutableSet<String> BANNED_COMBINATIONS;

    public Format(
            String name,
            String abbreviation,
            Set<String> clauses,
            Set<String> bannedPokemon,
            Set<String> bannedMoves,
            Set<String> bannedTiers,
            Set<String> bannedItems,
            Set<String> bannedAbilities,
            Set<String> bannedCombinations
    ) {
        NAME = name;
        ABBREVIATION = abbreviation;
        CLAUSES = ImmutableSet.copyOf(clauses);
        BANNED_POKEMON = ImmutableSet.copyOf(bannedPokemon);
        BANNED_MOVES = ImmutableSet.copyOf(bannedMoves);
        BANNED_TIERS = ImmutableSet.copyOf(bannedTiers);
        BANNED_ITEMS = ImmutableSet.copyOf(bannedItems);
        BANNED_ABILITIES = ImmutableSet.copyOf(bannedAbilities);
        BANNED_COMBINATIONS = ImmutableSet.copyOf(bannedCombinations);
    }

    public Format(String name, String abbreviation) {
        NAME = name;
        ABBREVIATION = abbreviation;
        CLAUSES = ImmutableSet.of();
        BANNED_POKEMON = ImmutableSet.of();
        BANNED_MOVES = ImmutableSet.of();
        BANNED_TIERS = ImmutableSet.of();
        BANNED_ITEMS = ImmutableSet.of();
        BANNED_ABILITIES = ImmutableSet.of();
        BANNED_COMBINATIONS = ImmutableSet.of();
    }

    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Override
    public ImmutableSet<String> getClauses() {
        return CLAUSES;
    }

    @Override
    public ImmutableSet<String> getBannedPokemon() {
        return BANNED_POKEMON;
    }

    @Override
    public ImmutableSet<String> getBannedMoves() {
        return BANNED_MOVES;
    }

    @Override
    public ImmutableSet<String> getBannedTiers() {
        return BANNED_TIERS;
    }

    @Override
    public ImmutableSet<String> getBannedItems() {
        return BANNED_ITEMS;
    }

    @Override
    public ImmutableSet<String> getBannedAbilities() {
        return BANNED_ABILITIES;
    }

    @Override
    public ImmutableSet<String> getBannedCombinations() {
        return BANNED_COMBINATIONS;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
