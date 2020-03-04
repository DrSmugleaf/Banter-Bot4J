package com.github.drsmugleaf.commands.api.converter;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class ConverterRegistry {

    private static final IValidator<Number> NUMBER_VALIDATOR = (context) -> {
        CommandField commandField = context.getField();
        Argument argument = commandField.getArgument();
        long value = context.getValue().longValue();
        long minimum = argument.minimum();
        long maximum = commandField.getMaximum();
        if (value < minimum) {
            return "Not enough " + commandField.getField().getName() + " requested. Minimum: " + minimum;
        } else if (value > maximum) {
            return "Too many " + commandField.getField().getName() + " requested. Maximum: " + maximum;
        }

        return "";
    };
    private final Map<Identifier<?, ?>, Converter<?, ?>> CONVERTERS = new HashMap<>();

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

    public ConverterRegistry register(Converter<?, ?> converter) {
        CONVERTERS.put(converter.getIdentifier(), converter);
        return this;
    }

    public <T, R> ConverterRegistry register(
            Class<T> from,
            Class<R> to,
            Transformer<R> transformer,
            @Nullable IValidator<? super R> validatorFunction
    ) {
        Identifier<T, R> identifier = new Identifier<>(from, to);
        Validator<R> validator = new Validator<>(to, validatorFunction);
        Converter<T, R> converter = new Converter<>(identifier, transformer, validator);
        register(converter);
        return this;
    }

    public <R> ConverterRegistry registerTransformer(
            Transformer<R> transformer
    ) {
        register(String.class, transformer.getType(), transformer, transformer.getValidator());
        return this;
    }

    public <R> ConverterRegistry registerCommandTo(
            Class<R> to,
            ITransformer<R> transformerFunction,
            @Nullable IValidator<? super R> validatorFunction
    ) {
        Transformer<R> transformer = new Transformer<>(to, transformerFunction, validatorFunction);
        registerTransformer(transformer);
        return this;
    }

    public <R> ConverterRegistry registerCommandTo(
            Class<R> to,
            ITransformer<R> transformerFunction
    ) {
        Transformer<R> transformer = new Transformer<>(to, transformerFunction);
        registerTransformer(transformer);
        return this;
    }

    public ConverterRegistry registerConverters(List<Entry<? extends Command>> commands) {
        for (Entry<? extends Command> entry : commands) {
            for (Transformer<?> transformer : entry.getTransformers().get()) {
                registerTransformer(transformer);
            }
        }

        return this;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T, U, R> Converter<T, R> find(Class<T> from1, Class<U> from2, Class<R> to) {
        return (Converter<T, R>) CONVERTERS
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(new Identifier<>(from1, to)))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
