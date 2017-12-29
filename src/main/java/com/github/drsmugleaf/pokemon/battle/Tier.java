package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.moves.BaseMove;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public enum Tier {

    UBER("Uber", "Uber"),
    OVERUSED("OverUsed", "OU"),
    BORDERLINE("Borderline", "BL"),
    UNDERUSED("UnderUsed", "UU"),
    BORDERLINE_2("Borderline 2", "BL2"),
    RARELYUSED("RarelyUsed", "RU"),
    BORDERLINE_3("Borderline 3", "BL3"),
    NEVERUSED("NeverUsed", "NU"),
    BORDERLINE_4("Borderline 4", "BL4"),
    PARTIALLYUSED("PartiallyUsed", "PU"),
    LITTLE_CUP("Little Cup", "LC"),
    DOUBLES("Doubles", "Doubles"),
    LIMBO("Limbo", "Limbo");

    static {
        UBER
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedPokemons("Mega Rayquaza")
                .setBannedMoves(BaseMove.SWAGGER);

        OVERUSED
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.BATON_PASS_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedTiers(Tier.UBER)
                .setBannedPokemons(
                        "Aegislash",
                        "Arceus",
                        "Blaziken",
                        "Darkrai",
                        "Deoxys",
                        "Deoxys-A",
                        "Deoxys-D",
                        "Deoxys-S",
                        "Dialga",
                        "Genesect",
                        "Giratina",
                        "Giratina-O",
                        "Greninja",
                        "Groudon",
                        "Ho-Oh",
                        "Kyogre",
                        "Kyurem-W",
                        "Lugia",
                        "Mewtwo",
                        "Palkia",
                        "Rayquaza",
                        "Reshiram",
                        "Shaymin-S",
                        "Xerneas",
                        "Yveltal",
                        "Zekrom"
                )
                .setBannedItems(
                        Items.GENGARITE,
                        Items.KANGASKHANITE,
                        Items.LUCARIONITE,
                        Items.MAWILITE,
                        Items.SALAMENCITE,
                        Items.SOUL_DEW
                )
                .setBannedMoves(BaseMove.SWAGGER);

        UNDERUSED
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedTiers(
                        Tier.UBER,
                        Tier.OVERUSED
                )
                .setBannedPokemons(
                        "Crawdaunt",
                        "Diggersby",
                        "Hawlucha",
                        "Klefki",
                        "Scolipede",
                        "Smeargle",
                        "Staraptor",
                        "Terrakion",
                        "Thundurus-T",
                        "Togekiss",
                        "Tornadus-T",
                        "Venomoth",
                        "Victini",
                        "Volcarona",
                        "Weavile",
                        "Zygarde"
                )
                .setBannedItems(
                        Items.ALAKAZITE,
                        Items.HERACRONITE,
                        Items.MEDICHAMITE,
                        Items.PINSIRITE
                )
                .setBannedAbilities(
                        Abilities.DRIZZLE,
                        Abilities.DROUGHT,
                        Abilities.SHADOW_TAG
                )
                .setBannedMoves(BaseMove.SWAGGER);

        RARELYUSED
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedTiers(
                        Tier.UBER,
                        Tier.OVERUSED,
                        Tier.UNDERUSED
                )
                .setBannedPokemons(
                        "Dragalge",
                        "Froslass",
                        "Kyurem",
                        "Moltres",
                        "Pangoro",
                        "Shuckle",
                        "Tornadus",
                        "Yanmega",
                        "Zoroark"
                )
                .setBannedItems(Items.HOUNDOOMINITE)
                .setBannedAbilities(
                        Abilities.DRIZZLE,
                        Abilities.DROUGHT,
                        Abilities.SHADOW_TAG
                )
                .setBannedMoves(BaseMove.SWAGGER);

        NEVERUSED
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedTiers(
                        Tier.UBER,
                        Tier.OVERUSED,
                        Tier.UNDERUSED,
                        Tier.RARELYUSED
                )
                .setBannedPokemons(
                        "Combusken",
                        "Sigilyph"
                )
                .setBannedAbilities(
                        Abilities.DRIZZLE,
                        Abilities.DROUGHT,
                        Abilities.SHADOW_TAG
                )
                .setBannedMoves(BaseMove.SWAGGER);

        PARTIALLYUSED
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedTiers(
                        Tier.UBER,
                        Tier.OVERUSED,
                        Tier.UNDERUSED,
                        Tier.RARELYUSED
                )
                .setBannedPokemons(
                        "Barbaracle",
                        "Carracosta",
                        "Exeggutor",
                        "Linoone",
                        "Throh",
                        "Victreebel",
                        "Vigoroth"
                )
                .setBannedAbilities(
                        Abilities.DRIZZLE,
                        Abilities.DROUGHT,
                        Abilities.SHADOW_TAG
                )
                .setBannedMoves(
                        BaseMove.SWAGGER,
                        BaseMove.CHATTER
                );

        LITTLE_CUP
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedPokemons(
                        "Gligar",
                        "Meditite",
                        "Misdreavus",
                        "Murkrow",
                        "Scyther",
                        "Sneasel",
                        "Swirlix",
                        "Tangela",
                        "Yanma"
                )
                .setBannedMoves(
                        BaseMove.DRAGON_RAGE,
                        BaseMove.SONIC_BOOM,
                        BaseMove.SWAGGER
                );

        DOUBLES
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedPokemons(
                        "Arceus",
                        "Dialga",
                        "Giratina",
                        "Giratina-O",
                        "Groudon",
                        "Ho-Oh",
                        "Kyogre",
                        "Kyurem-W",
                        "Lugia",
                        "Mewtwo",
                        "Palkia",
                        "Rayquaza",
                        "Reshiram",
                        "Xerneas",
                        "Yveltal",
                        "Zekrom"
                )
                .setBannedItems(
                        Items.SALAMENCITE,
                        Items.SOUL_DEW
                )
                .setBannedMoves(
                        BaseMove.DARK_VOID
                );
    }

    private final String NAME;
    private final String ABBREVIATION;
    private final List<Clause> CLAUSES = new ArrayList<>();
    private final List<String> BANNED_POKEMONS = new ArrayList<>();
    private final List<BaseMove> BANNED_MOVES = new ArrayList<>();
    private final List<Tier> BANNED_TIERS = new ArrayList<>();
    private final List<Items> BANNED_ITEMS = new ArrayList<>();
    private final List<Abilities> BANNED_ABILITIES = new ArrayList<>();

    Tier(@Nonnull String name, @Nonnull String abbreviation, @Nonnull Clause... clauses) {
        Holder.MAP.put(abbreviation.toLowerCase(), this);
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
        Collections.addAll(this.CLAUSES, clauses);
    }

    Tier(@Nonnull String name, @Nonnull String abbreviation) {
        this(name, abbreviation, new Clause[]{});
    }

    Tier(@Nonnull String name) {
        this(name, null, new Clause[]{});
    }

    public static Tier getTier(@Nonnull String abbreviation) {
        abbreviation = abbreviation.toLowerCase();
        if (!Holder.MAP.containsKey(abbreviation)) {
            throw new NullPointerException("Tier " + abbreviation + " doesn't exist");
        }

        return Holder.MAP.get(abbreviation);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public String getAbbreviation() {
        return this.ABBREVIATION;
    }

    public boolean isValid(Battle battle) {
        for (Clause clause : this.CLAUSES) {
            if (!clause.isValid(battle)) {
                return false;
            }
        }

        return true;
    }

    @Nonnull
    public List<Clause> getClauses() {
        return this.CLAUSES;
    }

    private Tier addClauses(Clause... clauses) {
        Collections.addAll(this.CLAUSES, clauses);
        return this;
    }

    private Tier setClauses(Clause... clauses) {
        this.CLAUSES.clear();
        return this.addClauses(clauses);
    }

    @Nonnull
    public List<String> getBannedPokemons() {
        return this.BANNED_POKEMONS;
    }

    private Tier addBannedPokemons(String... pokemons) {
        Collections.addAll(this.BANNED_POKEMONS, pokemons);
        return this;
    }

    private Tier setBannedPokemons(String... pokemons) {
        this.BANNED_POKEMONS.clear();
        return this.addBannedPokemons(pokemons);
    }

    @Nonnull
    public List<BaseMove> getBannedMoves() {
        return this.BANNED_MOVES;
    }

    private Tier addBannedMoves(BaseMove... moves) {
        Collections.addAll(this.BANNED_MOVES, moves);
        return this;
    }

    private Tier setBannedMoves(BaseMove... moves) {
        this.BANNED_MOVES.clear();
        return this.addBannedMoves(moves);
    }

    @Nonnull
    public List<Tier> getBannedTiers() {
        return this.BANNED_TIERS;
    }

    private Tier addBannedTiers(Tier... tiers) {
        Collections.addAll(this.BANNED_TIERS, tiers);
        return this;
    }

    private Tier setBannedTiers(Tier... tiers) {
        this.BANNED_TIERS.clear();
        return this.addBannedTiers(tiers);
    }

    @Nonnull
    public List<Items> getBannedItems() {
        return this.BANNED_ITEMS;
    }

    private Tier addBannedItems(Items... items) {
        Collections.addAll(this.BANNED_ITEMS, items);
        return this;
    }

    private Tier setBannedItems(Items... items) {
        this.BANNED_ITEMS.clear();
        return this.addBannedItems(items);
    }

    @Nonnull
    public List<Abilities> getBannedAbilities() {
        return this.BANNED_ABILITIES;
    }

    private Tier addBannedAbilities(Abilities... abilities) {
        Collections.addAll(this.BANNED_ABILITIES, abilities);
        return this;
    }

    private Tier setBannedAbilities(Abilities... abilities) {
        this.BANNED_ABILITIES.clear();
        return this.addBannedAbilities(abilities);
    }

    private static class Holder {
        static Map<String, Tier> MAP = new HashMap<>();
    }

}
