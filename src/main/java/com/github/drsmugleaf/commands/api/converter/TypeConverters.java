package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class TypeConverters {

    @Nonnull
    private final Map<TripleIdentifier, Converter> CONVERTERS = new HashMap<>();

    @Nonnull
    private final BiFunction<CommandField, Number, String> NUMBER_VALIDATOR = (field, n) -> {
        Argument argument = field.getArgument();
        long value = n.longValue();
        long minimum = argument.minimum();
        long maximum = field.getMaximum();
        if (value < minimum) {
            return "Not enough " + field.getField().getName() + " requested. Minimum: " + minimum;
        } else if (value > maximum) {
            return "Too many " + field.getField().getName() + " requested. Maximum: " + maximum;
        }

        return "";
    };

    public TypeConverters() {
        registerStringTo(CommandReceivedEvent.class, String.class, (s, e) -> s);

        registerStringTo(CommandReceivedEvent.class, Integer.class, (s, e) -> Integer.parseInt(s), NUMBER_VALIDATOR);
        registerStringTo(CommandReceivedEvent.class, int.class, (s, e) -> Integer.parseInt(s), NUMBER_VALIDATOR);

        registerStringTo(CommandReceivedEvent.class, Long.class, (s, e) -> Long.valueOf(s), NUMBER_VALIDATOR);
        registerStringTo(CommandReceivedEvent.class, long.class, (s, e) -> Long.parseLong(s), NUMBER_VALIDATOR);

        registerStringTo(CommandReceivedEvent.class, Float.class, (s, e) -> Float.valueOf(s), NUMBER_VALIDATOR);
        registerStringTo(CommandReceivedEvent.class, float.class, (s, e) -> Float.parseFloat(s), NUMBER_VALIDATOR);

        registerStringTo(CommandReceivedEvent.class, Double.class, (s, e) -> Double.valueOf(s), NUMBER_VALIDATOR);
        registerStringTo(CommandReceivedEvent.class, double.class, (s, e) -> Double.parseDouble(s), NUMBER_VALIDATOR);

        registerStringTo(CommandReceivedEvent.class, Character.class, (s, e) -> s.charAt(0));
        registerStringTo(CommandReceivedEvent.class, char.class, (s, e) -> s.charAt(0));
        registerStringTo(CommandReceivedEvent.class, char[].class, (s, e) -> s.toCharArray());

        registerStringTo(CommandReceivedEvent.class, Boolean.class, (s, e) -> {
            switch (s.toLowerCase()) {
                case "true":
                case "yes":
                case "y":
                    return Boolean.TRUE;
                case "false":
                case "no":
                case "n":
                    return Boolean.FALSE;
                default:
                    return null;
            }
        });
        registerStringTo(CommandReceivedEvent.class, boolean.class, (s, e) -> {
            switch (s.toLowerCase()) {
                case "true":
                case "yes":
                case "y":
                    return true;
                case "false":
                case "no":
                case "n":
                    return false;
                default:
                    return null;
            }
        });
    }

    public void register(@Nonnull Converter converter) {
        CONVERTERS.put(converter.getIdentifier(), converter);
    }

    public <T, U, R> void register(
            @Nonnull Class<T> from1,
            @Nonnull Class<U> from2,
            @Nonnull Class<R> to,
            @Nonnull BiFunction<T, U, R> converterFunction,
            @Nullable BiFunction<CommandField, ? super R, String> validatorFunction
    ) {
        TripleIdentifier<T, U, R> identifier = new TripleIdentifier<>(from1, from2, to);
        Validator<R> validator = new Validator<>(to, validatorFunction);
        Converter<T, U, R> converter = new Converter<>(identifier, converterFunction, validator);
        register(converter);
    }

    public <T, U, R> void register(
            @Nonnull Class<T> from1,
            @Nonnull Class<U> from2,
            @Nonnull Class<R> to,
            @Nonnull BiFunction<T, U, R> converterFunction
    ) {
        register(from1, from2, to, converterFunction, null);
    }

    public <U, R> void registerStringTo(
            @Nonnull Class<U> from,
            @Nonnull Class<R> to,
            @Nonnull BiFunction<String, U, R> converterFunction,
            @Nullable BiFunction<CommandField, ? super R, String> validatorFunction
    ) {
        register(String.class, from, to, converterFunction, validatorFunction);
    }

    public <U, R> void registerStringTo(
            @Nonnull Class<U> from,
            @Nonnull Class<R> to,
            @Nonnull BiFunction<String, U, R> converterFunction
    ) {
        registerStringTo(from, to, converterFunction, null);
    }

    public void registerConverters(@Nonnull List<Entry> commands) {
        for (Entry entry : commands) {
            Command command = entry.newInstance();
            command.registerConverters(this);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T, U, R> Converter<T, U, R> find(Class<T> from1, Class<U> from2, Class<R> to) {
        return (Converter<T, U, R>) CONVERTERS
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(new TripleIdentifier(from1, from2, to)))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
