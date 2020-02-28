package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.converter.TransformerSet;
import com.github.drsmugleaf.commands.api.registry.Entry;

/**
 * Created by DrSmugleaf on 12/06/2018
 */
public interface ICommand extends Runnable {

    void run();
    TransformerSet getTransformers();
    Entry<? extends ICommand> toEntry();

}
