package com.github.drsmugleaf.pokemon2.base.battle;

/**
 * Created by DrSmugleaf on 18/11/2019
 */
public interface IBug extends Runnable {

    default void execute(IBattle<?> battle) {
        if (battle.bugsEnabled()) {
            run();
        }
    }

}
