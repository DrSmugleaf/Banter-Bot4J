package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
                    if (pokemon.hasItem(Item.LEPPA_BERRY) && pokemon.hasAllMoves(BaseMove.RECYCLE, BaseMove.HEAL_PULSE) && pokemon.hasOneMove(recycleLeppaBannedMoves)) {
                        return false;
                    }

                    if (pokemon.hasItem(Item.LEPPA_BERRY) && pokemon.hasAllMoves(BaseMove.RECYCLE, BaseMove.PAIN_SPLIT)) {
                        return false;
                    }

                    if (pokemon.hasItem(Item.LEPPA_BERRY) && pokemon.hasAllMoves(BaseMove.RECYCLE, BaseMove.FLING)) {
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
                    if (pokemon.hasOneMove(BaseMove.DOUBLE_TEAM, BaseMove.MINIMIZE)) {
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
                    if (pokemon.getAbility() == Ability.MOODY) {
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
                    if (pokemon.hasOneMove(BaseMove.FISSURE, BaseMove.GUILLOTINE, BaseMove.HORN_DRILL, BaseMove.SHEER_COLD)) {
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
                Map<Ability, Integer> abilityCount = new HashMap<>();

                for (Pokemon pokemon : trainer.getPokemons()) {
                    Ability ability = pokemon.getAbility();

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
                    if (pokemons[i].getItem() == Item.SOUL_DEW) {
                        return false;
                    }

                    for (int j = i + 1; j < pokemons.length; j++) {
                        if (i != j && pokemons[i].getItem() == pokemons[j].getItem()) {
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
                    if (pokemon.hasOneMove(BaseMove.SWAGGER)) {
                        return false;
                    }
                }
            }

            return true;
        }
    },
    SLEEP_CLAUSE("Sleep Clause", "If a player has already put a Pokemon on his/her opponent's side to sleep and it is still sleeping, another one can't be put to sleep.");

    private final String NAME;
    private final String DESCRIPTION;

    Clause(@Nonnull String name, @Nonnull String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }

    public String getName() {
        return this.NAME;
    }

    public String getDescription() {
        return this.DESCRIPTION;
    }

    public boolean isValid(Battle battle) {
        return true;
    }

}
