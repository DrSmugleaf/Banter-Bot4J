package com.github.drsmugleaf.commands.api.converter.transformer;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.converter.validator.IValidator;

/**
 * Created by DrSmugleaf on 28/02/2020
 */
public class Transformer<T> {

    private final Class<T> TYPE;
    private final ITransformer<T> TRANSFORMER;
    @Nullable
    private final IValidator<? super T> VALIDATOR;

    public Transformer(Class<T> type, ITransformer<T> transformer, @Nullable IValidator<? super T> validator) {
        TYPE = type;
        TRANSFORMER = transformer;
        VALIDATOR = validator;
    }

    public Transformer(Class<T> type, ITransformer<T> transformer) {
        this(type, transformer, null);
    }

    public Class<T> getType() {
        return TYPE;
    }

    public ITransformer<T> getTransformer() {
        return TRANSFORMER;
    }

    @Nullable
    public IValidator<? super T> getValidator() {
        return VALIDATOR;
    }

}
