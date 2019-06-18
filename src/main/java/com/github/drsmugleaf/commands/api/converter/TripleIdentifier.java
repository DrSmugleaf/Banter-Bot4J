package com.github.drsmugleaf.commands.api.converter;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class TripleIdentifier<T, U, R> {

    @Nonnull
    private final Class<T> FROM1;

    @Nonnull
    private final Class<U> FROM2;

    @Nonnull
    private final Class<R> TO;

    public TripleIdentifier(@Nonnull Class<T> from1, @Nonnull Class<U> from2, @Nonnull Class<R> to) {
        FROM1 = from1;
        FROM2 = from2;
        TO = to;
    }

    @Nonnull
    public Class<T> getFrom1() {
        return FROM1;
    }

    @Nonnull
    public Class<U> getFrom2() {
        return FROM2;
    }

    @Nonnull
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
