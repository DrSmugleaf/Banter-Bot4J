package com.github.drsmugleaf.pokemon2.base.pokemon.state;

/**
 * Created by DrSmugleaf on 18/11/2019
 */
public enum PokemonStates implements IPokemonState {

    DEFAULT,
    ATTEMPTING_MOVE,
    CALCULATING_ATTACK,
    DEALING_DAMAGE,
    AFTER_MOVE // TODO: Or at the end of the turn if it did not attack

}
