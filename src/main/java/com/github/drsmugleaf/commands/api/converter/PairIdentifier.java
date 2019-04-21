package com.github.drsmugleaf.commands.api.converter;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class PairIdentifier<T, R> {

    @Nonnull
    private final Class<T> FROM;

    @Nonnull
    private final Class<R> TO;

    public PairIdentifier(@Nonnull Class<T> from, @Nonnull Class<R> to) {
        FROM = from;
        TO = to;
    }

    @Nonnull
    public Class<T> getFrom() {
        return FROM;
    }

    @Nonnull
    public Class<R> getTo() {
        return TO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairIdentifier<?, ?> that = (PairIdentifier<?, ?>) o;
        return FROM.equals(that.FROM) &&
                TO.equals(that.TO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FROM, TO);
    }

}
