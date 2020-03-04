package com.github.drsmugleaf.commands.api.converter;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Identifier<T, R> {

    private final Class<T> FROM;
    private final Class<R> TO;

    public Identifier(Class<T> from1, Class<R> to) {
        FROM = from1;
        TO = to;
    }

    public Class<T> getFrom() {
        return FROM;
    }

    public Class<R> getTo() {
        return TO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier<?, ?> that = (Identifier<?, ?>) o;
        return FROM.equals(that.FROM) &&
                TO.equals(that.TO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FROM, TO);
    }

}
