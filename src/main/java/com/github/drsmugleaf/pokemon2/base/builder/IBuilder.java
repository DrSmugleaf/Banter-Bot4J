package com.github.drsmugleaf.pokemon2.base.builder;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IBuilder<T extends IBuilder<T>> {

    @Contract(value = " -> this", pure = true)
    @SuppressWarnings("unchecked")
    default T getThis() {
        return (T) this;
    }

}
