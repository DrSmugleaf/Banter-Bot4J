package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.pokemon.Species;
import org.jetbrains.annotations.Contract;

import org.jetbrains.annotations.NotNull;
import java.util.*;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public enum Tier {

    ANYTHING_GOES("Anything Goes", "AG"),
    UBER("Uber", "Uber"),
    OVERUSED("OverUsed", "OU"),
    UNDERUSED_BANLIST("UnderUsed Banlist", "UUBL"),
    UNDERUSED("UnderUsed", "UU"),
    RARELYUSED_BANLIST("RarelyUsed Banlist", "RUBL"),
    RARELYUSED("RarelyUsed", "RU"),
    NEVERUSED_BANLIST("NeverUsed Banlist", "NUBL"),
    NEVERUSED("NeverUsed", "NU"),
    PARTIALLYUSED_BANLIST("PartiallyUsed Banlist", "PUBL"),
    PARTIALLYUSED("PartiallyUsed", "PU"),
    UNTIERED("Untiered", "Untiered"),
    LITTLE_CUP("Little Cup", "LC"),
    DOUBLES("Doubles", "Doubles"),
    LIMBO("Limbo", "Limbo");

    static {
        ANYTHING_GOES
                .setClauses(Clause.ENDLESS_BATTLE_CLAUSE);

        UBER
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.SPECIES_CLAUSE
                )
                .setBannedPokemons(Species.RAYQUAZA_MEGA)
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
                        Species.AEGISLASH,
                        Species.ARCEUS,
                        Species.BLAZIKEN,
                        Species.DARKRAI,
                        Species.DEOXYS,
                        Species.DEOXYS_ATTACK,
                        Species.DEOXYS_DEFENSE,
                        Species.DEOXYS_SPEED,
                        Species.DIALGA,
                        Species.GENESECT,
                        Species.GIRATINA,
                        Species.GIRATINA_ORIGIN,
                        Species.GRENINJA,
                        Species.GROUDON,
                        Species.HO_OH,
                        Species.KYOGRE,
                        Species.KYUREM_WHITE,
                        Species.LUGIA,
                        Species.MEWTWO,
                        Species.PALKIA,
                        Species.RAYQUAZA,
                        Species.RESHIRAM,
                        Species.SHAYMIN_SKY,
                        Species.XERNEAS,
                        Species.YVELTAL,
                        Species.ZEKROM
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
                        Species.CRAWDAUNT,
                        Species.DIGGERSBY,
                        Species.HAWLUCHA,
                        Species.KLEFKI,
                        Species.SCOLIPEDE,
                        Species.SMEARGLE,
                        Species.STARAPTOR,
                        Species.TERRAKION,
                        Species.THUNDURUS_THERIAN,
                        Species.TOGEKISS,
                        Species.TORNADUS_THERIAN,
                        Species.VENOMOTH,
                        Species.VICTINI,
                        Species.VOLCARONA,
                        Species.WEAVILE,
                        Species.ZYGARDE
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
                        Species.DRAGALGE,
                        Species.FROSLASS,
                        Species.KYUREM,
                        Species.MOLTRES,
                        Species.PANGORO,
                        Species.SHUCKLE,
                        Species.TORNADUS,
                        Species.YANMEGA,
                        Species.ZOROARK
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
                        Species.COMBUSKEN,
                        Species.SIGILYPH
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
                        Species.BARBARACLE,
                        Species.CARRACOSTA,
                        Species.EXEGGUTOR,
                        Species.LINOONE,
                        Species.THROH,
                        Species.VICTREEBEL,
                        Species.VIGOROTH
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
                        Species.GLIGAR,
                        Species.MEDITITE,
                        Species.MISDREAVUS,
                        Species.MURKROW,
                        Species.SCYTHER,
                        Species.SNEASEL,
                        Species.SWIRLIX,
                        Species.TANGELA,
                        Species.YANMA
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
                        Species.ARCEUS,
                        Species.DIALGA,
                        Species.GIRATINA,
                        Species.GIRATINA_ORIGIN,
                        Species.GROUDON,
                        Species.HO_OH,
                        Species.KYOGRE,
                        Species.KYUREM_WHITE,
                        Species.LUGIA,
                        Species.MEWTWO,
                        Species.PALKIA,
                        Species.RAYQUAZA,
                        Species.RESHIRAM,
                        Species.XERNEAS,
                        Species.YVELTAL,
                        Species.ZEKROM
                )
                .setBannedItems(
                        Items.SALAMENCITE,
                        Items.SOUL_DEW
                )
                .setBannedMoves(
                        BaseMove.DARK_VOID
                );
    }

    @NotNull
    public final String NAME;

    @NotNull
    public final String ABBREVIATION;

    @NotNull
    private final List<Clause> CLAUSES = new ArrayList<>();

    @NotNull
    private final List<Species> BANNED_POKEMONS = new ArrayList<>();

    @NotNull
    private final List<BaseMove> BANNED_MOVES = new ArrayList<>();

    @NotNull
    private final List<Tier> BANNED_TIERS = new ArrayList<>();

    @NotNull
    private final List<Items> BANNED_ITEMS = new ArrayList<>();

    @NotNull
    private final List<Abilities> BANNED_ABILITIES = new ArrayList<>();

    Tier(@NotNull String name, @NotNull String abbreviation, @NotNull Clause... clauses) {
        Holder.MAP.put(abbreviation.toLowerCase(), this);
        NAME = name;
        ABBREVIATION = abbreviation;
        Collections.addAll(CLAUSES, clauses);
    }

    Tier(@NotNull String name, @NotNull String abbreviation) {
        this(name, abbreviation, new Clause[]{});
    }

    @NotNull
    public static Tier getTier(@NotNull String abbreviation) {
        abbreviation = abbreviation.toLowerCase();

        if (!Holder.MAP.containsKey(abbreviation)) {
            throw new NullPointerException("Tier " + abbreviation + " doesn't exist");
        }

        return Holder.MAP.get(abbreviation);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    public boolean isValid(@NotNull Battle battle) {
        for (Clause clause : CLAUSES) {
            if (!clause.isValid(battle)) {
                return false;
            }
        }

        return true;
    }

    @Contract(pure = true)
    @NotNull
    public List<Clause> getClauses() {
        return CLAUSES;
    }

    @NotNull
    private Tier addClauses(@NotNull Clause... clauses) {
        Collections.addAll(CLAUSES, clauses);
        return this;
    }

    @NotNull
    private Tier setClauses(@NotNull Clause... clauses) {
        CLAUSES.clear();
        return addClauses(clauses);
    }

    @Contract(pure = true)
    @NotNull
    public List<Species> getBannedPokemons() {
        return BANNED_POKEMONS;
    }

    @NotNull
    private Tier addBannedPokemons(@NotNull Species... pokemons) {
        Collections.addAll(BANNED_POKEMONS, pokemons);
        return this;
    }

    @NotNull
    private Tier setBannedPokemons(@NotNull Species... pokemons) {
        BANNED_POKEMONS.clear();
        return addBannedPokemons(pokemons);
    }

    @NotNull
    public List<BaseMove> getBannedMoves() {
        return BANNED_MOVES;
    }

    @NotNull
    private Tier addBannedMoves(@NotNull BaseMove... moves) {
        Collections.addAll(BANNED_MOVES, moves);
        return this;
    }

    @NotNull
    private Tier setBannedMoves(@NotNull BaseMove... moves) {
        BANNED_MOVES.clear();
        return addBannedMoves(moves);
    }

    @Contract(pure = true)
    @NotNull
    public List<Tier> getBannedTiers() {
        return BANNED_TIERS;
    }

    @NotNull
    private Tier addBannedTiers(@NotNull Tier... tiers) {
        Collections.addAll(BANNED_TIERS, tiers);
        return this;
    }

    @NotNull
    private Tier setBannedTiers(@NotNull Tier... tiers) {
        BANNED_TIERS.clear();
        return addBannedTiers(tiers);
    }

    @Contract(pure = true)
    @NotNull
    public List<Items> getBannedItems() {
        return BANNED_ITEMS;
    }

    @NotNull
    private Tier addBannedItems(@NotNull Items... items) {
        Collections.addAll(BANNED_ITEMS, items);
        return this;
    }

    @NotNull
    private Tier setBannedItems(@NotNull Items... items) {
        BANNED_ITEMS.clear();
        return addBannedItems(items);
    }

    @Contract(pure = true)
    @NotNull
    public List<Abilities> getBannedAbilities() {
        return BANNED_ABILITIES;
    }

    @NotNull
    private Tier addBannedAbilities(@NotNull Abilities... abilities) {
        Collections.addAll(BANNED_ABILITIES, abilities);
        return this;
    }

    @NotNull
    private Tier setBannedAbilities(@NotNull Abilities... abilities) {
        BANNED_ABILITIES.clear();
        return addBannedAbilities(abilities);
    }

    private static class Holder {
        @NotNull
        static Map<String, Tier> MAP = new HashMap<>();
    }

}
