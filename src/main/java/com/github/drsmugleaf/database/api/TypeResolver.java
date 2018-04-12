package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class TypeResolver {

    @Nonnull
    private final Field FIELD;

    @Nonnull
    List<Annotation> ANNOTATIONS = new ArrayList<>();

    TypeResolver(@Nonnull Field type) {
        FIELD = type;
        Collections.addAll(ANNOTATIONS, FIELD.getDeclaredAnnotations());
    }

}
