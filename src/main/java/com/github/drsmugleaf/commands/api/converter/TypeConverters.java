package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class TypeConverters {

    @Nonnull
    private final Map<PairIdentifier, Converter> CONVERTERS = new HashMap<>();

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
        registerStringTo(String.class, String::toString);

        registerStringTo(Integer.class, Integer::valueOf, NUMBER_VALIDATOR);
        registerStringTo(int.class, Integer::parseInt, NUMBER_VALIDATOR);

        registerStringTo(Long.class, Long::valueOf, NUMBER_VALIDATOR);
        registerStringTo(long.class, Long::parseLong, NUMBER_VALIDATOR);

        registerStringTo(Float.class, Float::valueOf, NUMBER_VALIDATOR);
        registerStringTo(float.class, Float::parseFloat, NUMBER_VALIDATOR);

        registerStringTo(Double.class, Double::valueOf, NUMBER_VALIDATOR);
        registerStringTo(double.class, Double::parseDouble, NUMBER_VALIDATOR);

        registerStringTo(Character.class, s -> s.charAt(0));
        registerStringTo(char.class, s -> s.charAt(0));
        registerStringTo(char[].class, String::toCharArray);

        registerStringTo(Boolean.class, Boolean::valueOf);
        registerStringTo(boolean.class, s -> {
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

    public <T, R> void register(@Nonnull Class<T> from, @Nonnull Class<R> to, @Nonnull Function<T, R> converterFunction, @Nullable BiFunction<CommandField, ? super R, String> validatorFunction) {
        PairIdentifier<T, R> identifier = new PairIdentifier<>(from, to);
        Validator<R> validator = new Validator<>(to, validatorFunction);
        Converter<T, R> converter = new Converter<>(identifier, converterFunction, validator);
        register(converter);
    }

    public <T, R> void register(@Nonnull Class<T> from, @Nonnull Class<R> to, @Nonnull Function<T, R> converterFunction) {
        register(from, to, converterFunction, null);
    }

    public <R> void registerStringTo(@Nonnull Class<R> to, @Nonnull Function<String, R> converterFunction, @Nullable BiFunction<CommandField, ? super R, String> validatorFunction) {
        register(String.class, to, converterFunction, validatorFunction);
    }

    public <R> void registerStringTo(@Nonnull Class<R> to, @Nonnull Function<String, R> converterFunction) {
        registerStringTo(to, converterFunction, null);
    }

    public void registerConverters(@Nonnull List<Entry> commands) {
        for (Entry entry : commands) {
            Command command = entry.newInstance();
            command.registerConverters(this);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T, R> Converter<T, R> find(Class<T> from, Class<R> to) {
        return (Converter<T, R>) CONVERTERS
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getFrom().equals(from) && entry.getKey().getTo().equals(to))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
