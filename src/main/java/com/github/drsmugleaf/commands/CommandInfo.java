package com.github.drsmugleaf.commands;

import sx.blah.discord.handle.obj.Permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrSmugleaf on 20/05/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CommandInfo {

    String name() default "";

    Permissions[] permissions() default {};

    Tags[] tags() default {};

}
