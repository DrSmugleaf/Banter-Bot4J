package com.github.drsmugleaf.commands.api.converter;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class TripleIdentifier<T, U, R> {

    private final Class<T> FROM1;
    private final Class<U> FROM2;
    private final Class<R> TO;

    public TripleIdentifier(Class<T> from1, Class<U> from2, Class<R> to) {
        FROM1 = from1;
        FROM2 = from2;
        TO = to;
    }

    public Class<T> getFrom1() {
        return FROM1;
    }

    public Class<U> getFrom2() {
        return FROM2;
    }

    public Class<R> getTo() {
        return TO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripleIdentifier<?, ?, ?> that = (TripleIdentifier<?, ?, ?>) o;
        return FROM1.equals(that.FROM1) &&
                FROM2.equals(that.FROM2) &&
                TO.equals(that.TO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FROM1, FROM2, TO);
    }

}
