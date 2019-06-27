package com.github.drsmugleaf;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrSmugleaf on 22/06/2019
 */
@Nonnull
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
@TypeQualifierDefault({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface NonnullApi {}
