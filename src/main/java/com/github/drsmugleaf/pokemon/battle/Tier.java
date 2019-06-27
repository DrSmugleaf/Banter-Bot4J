package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.pokemon.Species;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public enum Tier {

    ANYTHING_GOES("Anything Goes", "AG"),
    UBER("Uber", "Uber"),
    LGPE_OU("Let's Go! Pikachu and Eevee", "LGPE OU"),
    CREATE_A_POKEMON("Create-a-Pokemon", "CAP"),
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

    public final String NAME;
    public final String ABBREVIATION;
    private final List<Clause> CLAUSES = new ArrayList<>();
    private final List<Species> BANNED_POKEMONS = new ArrayList<>();
    private final List<BaseMove> BANNED_MOVES = new ArrayList<>();
    private final List<Tier> BANNED_TIERS = new ArrayList<>();
    private final List<Items> BANNED_ITEMS = new ArrayList<>();
    private final List<Abilities> BANNED_ABILITIES = new ArrayList<>();

    Tier(String name, String abbreviation, Clause... clauses) {
        Holder.MAP.put(abbreviation.toLowerCase(), this);
        NAME = name;
        ABBREVIATION = abbreviation;
        Collections.addAll(CLAUSES, clauses);
    }

    Tier(String name, String abbreviation) {
        this(name, abbreviation, new Clause[]{});
    }

    public static Tier getTier(String abbreviation) {
        abbreviation = abbreviation.toLowerCase();

        if (!Holder.MAP.containsKey(abbreviation)) {
            throw new NullPointerException("Tier " + abbreviation + " doesn't exist");
        }

        return Holder.MAP.get(abbreviation);
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    public boolean isValid(Battle battle) {
        for (Clause clause : CLAUSES) {
            if (!clause.isValid(battle)) {
                return false;
            }
        }

        return true;
    }

    @Contract(" -> new")
    public List<Clause> getClauses() {
        return new ArrayList<>(CLAUSES);
    }

    @Contract("_ -> this")
    private Tier addClauses(Clause... clauses) {
        Collections.addAll(CLAUSES, clauses);
        return this;
    }

    @Contract("_ -> this")
    private Tier setClauses(Clause... clauses) {
        CLAUSES.clear();
        return addClauses(clauses);
    }

    @Contract(pure = true)
    public List<Species> getBannedPokemons() {
        return new ArrayList<>(BANNED_POKEMONS);
    }

    @Contract("_ -> this")
    private Tier addBannedPokemons(Species... pokemons) {
        Collections.addAll(BANNED_POKEMONS, pokemons);
        return this;
    }

    @Contract("_ -> this")
    private Tier setBannedPokemons(Species... pokemons) {
        BANNED_POKEMONS.clear();
        return addBannedPokemons(pokemons);
    }

    @Contract(" -> new")
    public List<BaseMove> getBannedMoves() {
        return new ArrayList<>(BANNED_MOVES);
    }

    @Contract("_ -> this")
    private Tier addBannedMoves(BaseMove... moves) {
        Collections.addAll(BANNED_MOVES, moves);
        return this;
    }

    @Contract("_ -> this")
    private Tier setBannedMoves(BaseMove... moves) {
        BANNED_MOVES.clear();
        return addBannedMoves(moves);
    }

    @Contract(" -> new")
    public List<Tier> getBannedTiers() {
        return new ArrayList<>(BANNED_TIERS);
    }

    @Contract("_ -> this")
    private Tier addBannedTiers(Tier... tiers) {
        Collections.addAll(BANNED_TIERS, tiers);
        return this;
    }

    @Contract("_ -> this")
    private Tier setBannedTiers(Tier... tiers) {
        BANNED_TIERS.clear();
        return addBannedTiers(tiers);
    }

    @Contract(" -> new")
    public List<Items> getBannedItems() {
        return new ArrayList<>(BANNED_ITEMS);
    }

    @Contract("_ -> this")
    private Tier addBannedItems(Items... items) {
        Collections.addAll(BANNED_ITEMS, items);
        return this;
    }

    @Contract("_ -> this")
    private Tier setBannedItems(Items... items) {
        BANNED_ITEMS.clear();
        return addBannedItems(items);
    }

    @Contract(" -> new")
    public List<Abilities> getBannedAbilities() {
        return new ArrayList<>(BANNED_ABILITIES);
    }

    @Contract("_ -> this")
    private Tier addBannedAbilities(Abilities... abilities) {
        Collections.addAll(BANNED_ABILITIES, abilities);
        return this;
    }

    @Contract("_ -> this")
    private Tier setBannedAbilities(Abilities... abilities) {
        BANNED_ABILITIES.clear();
        return addBannedAbilities(abilities);
    }

    private static class Holder {
        static Map<String, Tier> MAP = new HashMap<>();
    }

}
