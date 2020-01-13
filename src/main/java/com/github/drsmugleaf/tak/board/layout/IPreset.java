package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.board.action.IAction;
import com.google.common.collect.ImmutableList;

import java.io.InputStream;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPreset {

    InputStream getImage();
    int getSize();
    int getCapstones();
    int getStones();
    int getCarryLimit();
    int getMaximumStackSize();
    ImmutableList<IAction> getAllActions();

}
