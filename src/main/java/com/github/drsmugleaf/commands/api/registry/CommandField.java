package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/04/2019
 */
public class CommandField {

    private final Field FIELD;

    private final Argument ARGUMENT;

    private final long MAXIMUM;

    private CommandField(Field field) {
        FIELD = field;
        ARGUMENT = field.getDeclaredAnnotation(Argument.class);
        MAXIMUM = getMaximum(FIELD, ARGUMENT);
    }

    private static long getMaximum(Field field, Argument argument) {
        for (Field declaredField : field.getDeclaringClass().getDeclaredFields()) {
            Argument.Maximum maximumAnnotation = declaredField.getDeclaredAnnotation(Argument.Maximum.class);

            if (maximumAnnotation != null && maximumAnnotation.value().equals(field.getName())) {
                declaredField.setAccessible(true);

                try {
                    return declaredField.getInt(null);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Error accessing field " + declaredField, e);
                }
            }
        }

        return argument.maximum();
    }

    public static List<CommandField> from(Class<? extends Command> command) {
        List<CommandField> fields = new ArrayList<>();

        for (Field field : command.getDeclaredFields()) {
            if (field.isAnnotationPresent(Argument.class)) {
                CommandField commandField = new CommandField(field);
                fields.add(commandField);
            }
        }

        return fields;
    }

    public Field getField() {
        return FIELD;
    }

    public Argument getArgument() {
        return ARGUMENT;
    }

    public long getMaximum() {
        return MAXIMUM;
    }

}
