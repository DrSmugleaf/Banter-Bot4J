package com.github.drsmugleaf.commands.api.tags;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public interface Tag {

    boolean isValid(@NotNull CommandReceivedEvent event);

    String message();

    void execute(@NotNull CommandReceivedEvent event);

}
