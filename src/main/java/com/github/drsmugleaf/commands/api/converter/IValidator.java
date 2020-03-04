package com.github.drsmugleaf.commands.api.converter;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 04/03/2020
 */
public interface IValidator<T> extends Function<ValidatorContext<? extends T>, String> {}
