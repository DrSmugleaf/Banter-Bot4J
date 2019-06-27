package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.moves.CriticalHitStage;
import com.github.drsmugleaf.pokemon.moves.InvalidCriticalStageException;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by DrSmugleaf on 27/06/2017.
 */
public enum Generation {

    I("Generation I", "RB") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            throw new InvalidGenerationException(this, "Generation I doesn't have critical hit percentages");
        }
    },
    II("Generation II", "GS") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            switch (stage) {
                case ZERO:
                    return 6.25;
                case ONE:
                    return 12.5;
                case TWO:
                    return 25.0;
                case THREE:
                    return 33.3333333333;
                case FOUR:
                    return 50.0;
                default:
                    throw new InvalidCriticalStageException(stage);
            }
        }
    },
    III("Generation III", "RS") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            return II.getCriticalPercentage(stage);
        }
    },
    IV("Generation IV", "DP") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            return II.getCriticalPercentage(stage);
        }
    },
    V("Generation V", "BW") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            return II.getCriticalPercentage(stage);
        }
    },
    VI("Generation VI", "XY") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            switch (stage) {
                case ZERO:
                    return 6.25;
                case ONE:
                    return 12.5;
                case TWO:
                    return 50.0;
                case THREE:
                case FOUR:
                    return 100.0;
                default:
                    throw new InvalidCriticalStageException(stage);
            }
        }
    },
    VII("Generation VII", "SM") {
        @Override
        public double getCriticalPercentage(CriticalHitStage stage) {
            switch (stage) {
                case ZERO:
                    return 4.1666666667;
                case ONE:
                    return 12.5;
                case TWO:
                    return 50.0;
                case THREE:
                case FOUR:
                    return 100.0;
                default:
                    throw new InvalidCriticalStageException(stage);
            }
        }
    };

    static {
        I
                .setNewPokemons(151)
                .setTotalPokemons(151)
                .setCoreGames(
                        Game.RED_AND_GREEN,
                        Game.BLUE,
                        Game.RED_AND_BLUE,
                        Game.YELLOW
                );

        II
                .setNewPokemons(100)
                .setTotalPokemons(251)
                .setCoreGames(
                        Game.GOLD_AND_SILVER,
                        Game.CRYSTAL
                );

        III
                .setNewPokemons(135)
                .setTotalPokemons(386)
                .setCoreGames(
                        Game.RUBY_AND_SAPPHIRE,
                        Game.FIRERED_AND_LEAFGREEN,
                        Game.EMERALD
                );

        IV
                .setNewPokemons(107)
                .setTotalPokemons(493)
                .setCoreGames(
                        Game.DIAMOND_AND_PEARL,
                        Game.PLATINUM,
                        Game.HEARTGOLD_AND_SOULSILVER
                );

        V
                .setNewPokemons(156)
                .setTotalPokemons(649)
                .setCoreGames(
                        Game.BLACK_AND_WHITE,
                        Game.BLACK_2_AND_WHITE_2
                );

        VI
                .setNewPokemons(72)
                .setTotalPokemons(721)
                .setCoreGames(
                        Game.X_AND_Y,
                        Game.OMEGA_RUBY_AND_ALPHA_SAPPHIRE
                );

        VII
                .setNewPokemons(81)
                .setTotalPokemons(802)
                .setCoreGames(
                        Game.SUN_AND_MOON,
                        Game.ULTRA_SUN_AND_ULTRA_MOON
                );
    }

    public final String NAME;

    public final String SHORTHAND;

    private final List<Game> CORE_GAMES = new ArrayList<>();

    private int NEW_POKEMONS;

    private int TOTAL_POKEMONS;

    Generation(String name, String shorthand) {
        Holder.MAP.put(shorthand.toLowerCase(), this);
        NAME = name;
        SHORTHAND = shorthand;
    }

    public static Generation getGeneration(String shorthand) {
        shorthand = shorthand.toLowerCase();

        if (!Holder.MAP.containsKey(shorthand)) {
            throw new NullPointerException("Generation " + shorthand + " doesn't exist");
        }

        return Holder.MAP.get(shorthand);
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public String getShorthand() {
        return SHORTHAND;
    }

    @Contract(pure = true)
    public int getNewPokemons() {
        return NEW_POKEMONS;
    }

    private Generation setNewPokemons(int amount) {
        NEW_POKEMONS = amount;
        return this;
    }

    @Contract(pure = true)
    public int getTotalPokemons() {
        return TOTAL_POKEMONS;
    }

    private Generation setTotalPokemons(int amount) {
        TOTAL_POKEMONS = amount;
        return this;
    }

    public List<Game> getCoreGames() {
        return new ArrayList<>(CORE_GAMES);
    }

    private Generation setCoreGames(Game... games) {
        Collections.addAll(CORE_GAMES, games);
        return this;
    }

    public abstract double getCriticalPercentage(CriticalHitStage stage);

    public boolean isAbove(Generation generation) {
        return ordinal() > generation.ordinal();
    }

    private static class Holder {
        static Map<String, Generation> MAP = new HashMap<>();
    }

}
