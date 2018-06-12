package com.github.drsmugleaf.commands.api;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 12/06/2018
 */
public interface ICommand {

    void run(@Nonnull CommandReceivedEvent event);

}
