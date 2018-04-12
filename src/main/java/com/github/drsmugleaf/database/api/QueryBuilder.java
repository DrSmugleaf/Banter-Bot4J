package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
public class QueryBuilder {

    @Nonnull
    private final Class<? extends Model> MODEL;

    @Nonnull
    private final List<Field> FIELDS = new ArrayList<>();

    QueryBuilder(@Nonnull Class<? extends Model> model) {
        MODEL = model;

        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                FIELDS.add(field);
            }
        }
    }

}
