package com.github.drsmugleaf.commands.api.converter.validator;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import discord4j.core.object.entity.User;

/**
 * Created by DrSmugleaf on 04/03/2020
 */
public class ValidatorContext<T> {

    private final CommandField FIELD;
    private final T VALUE;
    private final CommandReceivedEvent EVENT;
    private final User USER;

    public ValidatorContext(CommandField field, T value, CommandReceivedEvent event) {
        FIELD = field;
        VALUE = value;
        EVENT = event;
        USER = event.getMessage().getAuthor().orElseThrow(() -> new IllegalArgumentException("No author found for event " + event));
    }

    public CommandField getField() {
        return FIELD;
    }

    public T getValue() {
        return VALUE;
    }

    public CommandReceivedEvent getEvent() {
        return EVENT;
    }

    public User getUser() {
        return USER;
    }

}
