package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/04/2019
 */
public class CommandField {

    @Nonnull
    private final Field FIELD;

    @Nonnull
    private final Argument ARGUMENT;

    private final long MAXIMUM;

    private CommandField(@Nonnull Field field) {
        FIELD = field;
        ARGUMENT = field.getDeclaredAnnotation(Argument.class);
        MAXIMUM = getMaximum(FIELD, ARGUMENT);
    }

    private static long getMaximum(@Nonnull Field field, @Nonnull Argument argument) {
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

    @Nonnull
    public static List<CommandField> from(@Nonnull Class<? extends Command> command) {
        List<CommandField> fields = new ArrayList<>();

        for (Field field : command.getDeclaredFields()) {
            if (field.isAnnotationPresent(Argument.class)) {
                CommandField commandField = new CommandField(field);
                fields.add(commandField);
            }
        }

        return fields;
    }

    @Nonnull
    public Field getField() {
        return FIELD;
    }

    @Nonnull
    public Argument getArgument() {
        return ARGUMENT;
    }

    public long getMaximum() {
        return MAXIMUM;
    }

}
