package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class Ability implements IAbility {

    private final String NAME;

    public Ability(String name) {
        NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
