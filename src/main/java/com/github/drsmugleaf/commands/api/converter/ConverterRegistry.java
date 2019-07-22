package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class ConverterRegistry {

    private static final BiFunction<CommandField, Number, String> NUMBER_VALIDATOR = (field, n) -> {
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
    private final Map<TripleIdentifier, Converter> CONVERTERS = new HashMap<>();

    public ConverterRegistry() {
        registerCommandTo(String.class, (s, e) -> s);

        registerCommandTo(Integer.class, (s, e) -> Integer.parseInt(s), NUMBER_VALIDATOR);
        registerCommandTo(int.class, (s, e) -> Integer.parseInt(s), NUMBER_VALIDATOR);

        registerCommandTo(Long.class, (s, e) -> Long.valueOf(s), NUMBER_VALIDATOR);
        registerCommandTo(long.class, (s, e) -> Long.parseLong(s), NUMBER_VALIDATOR);

        registerCommandTo(Float.class, (s, e) -> Float.valueOf(s), NUMBER_VALIDATOR);
        registerCommandTo(float.class, (s, e) -> Float.parseFloat(s), NUMBER_VALIDATOR);

        registerCommandTo(Double.class, (s, e) -> Double.valueOf(s), NUMBER_VALIDATOR);
        registerCommandTo(double.class, (s, e) -> Double.parseDouble(s), NUMBER_VALIDATOR);

        registerCommandTo(Character.class, (s, e) -> s.charAt(0));
        registerCommandTo(char.class, (s, e) -> s.charAt(0));
        registerCommandTo(char[].class, (s, e) -> s.toCharArray());

        registerCommandTo(Boolean.class, (s, e) -> {
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
        registerCommandTo(boolean.class, (s, e) -> {
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

    public ConverterRegistry register(Converter converter) {
        CONVERTERS.put(converter.getIdentifier(), converter);
        return this;
    }

    public <T, U, R> ConverterRegistry register(
            Class<T> from1,
            Class<U> from2,
            Class<R> to,
            BiFunction<T, U, R> converterFunction,
            @Nullable BiFunction<CommandField, ? super R, String> validatorFunction
    ) {
        TripleIdentifier<T, U, R> identifier = new TripleIdentifier<>(from1, from2, to);
        Validator<R> validator = new Validator<>(to, validatorFunction);
        Converter<T, U, R> converter = new Converter<>(identifier, converterFunction, validator);
        register(converter);
        return this;
    }

    public <T, U, R> ConverterRegistry register(
            Class<T> from1,
            Class<U> from2,
            Class<R> to,
            BiFunction<T, U, R> converterFunction
    ) {
        register(from1, from2, to, converterFunction, null);
        return this;
    }

    public <U, R> ConverterRegistry registerStringTo(
            Class<U> from,
            Class<R> to,
            BiFunction<String, U, R> converterFunction,
            @Nullable BiFunction<CommandField, ? super R, String> validatorFunction
    ) {
        register(String.class, from, to, converterFunction, validatorFunction);
        return this;
    }

    public <U, R> ConverterRegistry registerStringTo(
            Class<U> from,
            Class<R> to,
            BiFunction<String, U, R> converterFunction
    ) {
        registerStringTo(from, to, converterFunction, null);
        return this;
    }

    public <R> ConverterRegistry registerCommandTo(
            Class<R> to,
            BiFunction<String, CommandReceivedEvent, R> converterFunction,
            @Nullable BiFunction<CommandField, ? super R, String> validatorFunction
    ) {
        registerStringTo(CommandReceivedEvent.class, to, converterFunction, validatorFunction);
        return this;
    }

    public <R> ConverterRegistry registerCommandTo(
            Class<R> to,
            BiFunction<String, CommandReceivedEvent, R> converterFunction
    ) {
        registerCommandTo(to, converterFunction, null);
        return this;
    }

    public ConverterRegistry registerConverters(List<Entry> commands) {
        for (Entry entry : commands) {
            Command command = entry.newInstance();
            command.registerConverters(this);
        }

        return this;
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
