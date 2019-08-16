package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.util.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrSmugleaf on 20/05/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {

    String name() default "";

    String[] aliases() default {};

    Permission[] permissions() default {};

    Tags[] tags() default {};

    String description();

}
