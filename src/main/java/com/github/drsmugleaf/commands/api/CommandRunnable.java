package com.github.drsmugleaf.commands.api;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
interface CommandRunnable {

    void run(@Nonnull CommandReceivedEvent event);

}
