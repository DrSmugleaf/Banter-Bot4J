package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.types.Type;

import java.util.*;

/**
 * Created by DrSmugleaf on 16/07/2017.
 */
public enum Clause {

    ENDLESS_BATTLE_CLAUSE("Endless Battle Clause", "Players cannot intentionally prevent an opponent from being able to end the game without forfeiting.") {
        BaseMove[] recycleLeppaBannedMoves = new BaseMove[]{
                BaseMove.MILK_DRINK,
                BaseMove.MOONLIGHT,
                BaseMove.MORNING_SUN,
                BaseMove.RECOVER,
                BaseMove.ROOST,
                BaseMove.SLACK_OFF,
                BaseMove.SOFT_BOILED,
                BaseMove.WISH
        };

        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.ITEM.is(Items.LEPPA_BERRY) && pokemon.MOVES.hasAll(BaseMove.RECYCLE, BaseMove.HEAL_PULSE) && pokemon.MOVES.hasOne(recycleLeppaBannedMoves)) {
                        return false;
                    }

                    if (pokemon.ITEM.is(Items.LEPPA_BERRY) && pokemon.MOVES.hasAll(BaseMove.RECYCLE, BaseMove.PAIN_SPLIT)) {
                        return false;
                    }

                    if (pokemon.ITEM.is(Items.LEPPA_BERRY) && pokemon.MOVES.hasAll(BaseMove.RECYCLE, BaseMove.FLING)) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    EVASION_CLAUSE("Evasion Clause", "Players cannot use the moves Double Team or Minimize.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.MOVES.hasOne(BaseMove.DOUBLE_TEAM, BaseMove.MINIMIZE)) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    MOODY_CLAUSE("Moody Clause", "Players cannot use a Pokémon with the Moody ability.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.ABILITY.get() == Abilities.MOODY) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    OHKO_CLAUSE("OHKO Clause", "Players cannot use the moves Fissure, Guillotine, Horn Drill, or Sheer Cold.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.MOVES.hasOne(BaseMove.FISSURE, BaseMove.GUILLOTINE, BaseMove.HORN_DRILL, BaseMove.SHEER_COLD)) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    SPECIES_CLAUSE("Species Clause", "A player cannot have two Pokémon with the same National Pokédex number on a team.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                Pokemon[] pokemons = trainer.getPokemons();

                for (int i = 0; i < pokemons.length; i++) {
                    for (int j = i + 1; j < pokemons.length; j++) {
                        if (i != j && Objects.equals(pokemons[i].getName(), pokemons[j].getName())) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    },
    CFZ_CLAUSE("CFZ Clause", "Players cannot use any signature Z-Moves without their Z-Crystals on their native users."),
    ABILITY_CLAUSE("Ability Clause", "Each team may not have more than two of any ability.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                Map<Abilities, Integer> abilityCount = new HashMap<>();

                for (Pokemon pokemon : trainer.getPokemons()) {
                    Abilities ability = pokemon.ABILITY.get();

                    if (abilityCount.containsKey(ability)) {
                        if (abilityCount.get(ability) == 2) {
                            return false;
                        }

                        abilityCount.put(ability, abilityCount.get(ability) + 1);
                    } else {
                        abilityCount.put(ability, 1);
                    }
                }
            }

            return true;
        }
    },
    ITEM_CLAUSE("Item Clause", "No two Pokémon may hold the same item during battle. Soul Dew is not allowed.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                Pokemon[] pokemons = trainer.getPokemons();

                for (int i = 0; i < pokemons.length; i++) {
                    if (pokemons[i].ITEM.is(Items.SOUL_DEW)) {
                        return false;
                    }

                    for (int j = i + 1; j < pokemons.length; j++) {
                        if (i != j && pokemons[i].ITEM.is(pokemons[j].ITEM.get())) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    },
    BATTLE_TIMER("Battle Timer", "Players have a total of 10 minutes to make their moves for the entirety of the match with a 1 minute turn timer. If a player allows their 10 minutes to expire they will lose."),
    LEVEL_RESTRICTIONS("Level Restrictions", "All Pokémon above level 50 will be leveled down to level 50, any Pokemon below level 50 will retain their level.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.getLevel() > 50) {
                        trainer.removePokemon(pokemon);
                        pokemon.setLevel(50);
                        trainer.addPokemon(pokemon);
                    }
                }
            }

            return true;
        }
    },
    THE_3DS_BORN_RULE("The 3DS-Born Rule", "All Pokemon used in Sun and Moon Battle Spot must be either caught, hatched, or gifted in a generation VI or VII game."),
    SWAGGER_CLAUSE("Swagger Clause", "Players cannot use the move Swagger.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.MOVES.hasOne(BaseMove.SWAGGER)) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    SLEEP_CLAUSE("Sleep Clause", "If a player has already put a Pokemon on his/her opponent's side to sleep and it is still sleeping, another one can't be put to sleep."),
    LEVEL_5_CLAUSE("Level 5 Clause", "All Pokémon must be level 5.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.getLevel() != 5) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    MEGA_STONE_AND_ORB_CLAUSE("Mega Stone / Orb Clause", "A limit of two of each Mega Stone and Orb may be used.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                Map<Items, Integer> itemCount = new HashMap<>();

                for (Pokemon pokemon : trainer.getPokemons()) {
                    Items item = pokemon.ITEM.get();

                    if (itemCount.containsKey(item)) {
                        if (itemCount.get(item) == 2) {
                            return false;
                        }

                        itemCount.put(item, itemCount.get(item) + 1);
                    } else {
                        itemCount.put(item, 1);
                    }
                }
            }

            return true;
        }
    },
    BATON_PASS_CLAUSE("Baton Pass Clause", "Only one Pokémon per team may know the move Baton Pass.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                boolean onePokemonHasBatonPass = false;

                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.MOVES.hasOne(BaseMove.BATON_PASS)) {
                        if (onePokemonHasBatonPass) {
                            return false;
                        }

                        onePokemonHasBatonPass = true;
                    }
                }
            }

            return true;
        }
    },
    SAME_TYPE_CLAUSE("Same Type Clause", "All Pokémon in a team must share a type.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                List<Type> types = trainer.getPokemon(0).TYPES.getTypes();

                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (Collections.disjoint(types, pokemon.TYPES.getTypes())) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    LEVEL_LIMIT("Level Limit", "Pokémon above and below Level 50 are permitted, but will be auto-leveled to Level 50 during battle.") {
        @Override
        public boolean isValid(Battle battle) {
            for (Trainer trainer : battle.getTrainers().values()) {
                for (Pokemon pokemon : trainer.getPokemons()) {
                    if (pokemon.getLevel() != 50) {
                        trainer.removePokemon(pokemon);
                        pokemon.setLevel(50);
                        trainer.addPokemon(pokemon);
                    }
                }
            }

            return true;
        }
    },
    // TODO
    TIME_LIMIT("Time Limit", "Battles will have a 10-minute player time limit, 45-second move time limit, and a 90-second team preview."),
    // TODO
    SKETCH_CLAUSE("Sketch Clause", "You can't sketch two of the same moves in a team with non-native users.");

    public final String NAME;

    public final String DESCRIPTION;

    Clause(String name, String description) {
        Holder.MAP.put(name, this);
        NAME = name;
        DESCRIPTION = description;
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public boolean isValid(Battle battle) {
        return true;
    }

    private static class Holder {
        static Map<String, Clause> MAP = new HashMap<>();
    }

}
