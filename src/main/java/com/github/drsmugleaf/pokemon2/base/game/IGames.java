package com.github.drsmugleaf.pokemon2.base.game;

import com.github.drsmugleaf.pokemon2.base.Nameable;

import java.util.Set;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public interface IGames extends Nameable {

    Set<IGame> getCoreGames();
    Set<IGame> getSideGames();

}
