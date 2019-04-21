package com.github.drsmugleaf.commands.api;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrSmugleaf on 11/04/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Argument {

    int position();

    long minimum() default 1;

    long maximum() default Long.MAX_VALUE;

    @Nonnull String example();

    boolean optional() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Maximum {

        @Nonnull String value();

    }

}
