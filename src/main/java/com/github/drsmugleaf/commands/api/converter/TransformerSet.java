package com.github.drsmugleaf.commands.api.converter;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Created by DrSmugleaf on 28/02/2020
 */
public class TransformerSet {

    private static final TransformerSet EMPTY = new TransformerSet(ImmutableSet.of());
    private final ImmutableSet<Transformer<?>> TRANSFORMERS;

    private TransformerSet(Set<Transformer<?>> transformers) {
        TRANSFORMERS = ImmutableSet.copyOf(transformers);
    }

    public static TransformerSet of() {
        return EMPTY;
    }

    public static TransformerSet of(Set<Transformer<?>> transformers) {
        return new TransformerSet(transformers);
    }

    public static TransformerSet of(Transformer<?>... transformers) {
        return of(Set.of(transformers));
    }

    public static <E1> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1
    ) {
        return of(
                new Transformer<>(type1, transformer1)
        );
    }
    public static <E1, E2> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2)
        );
    }
    public static <E1, E2, E3> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3)
        );
    }
    public static <E1, E2, E3, E4> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4)
        );
    }
    public static <E1, E2, E3, E4, E5> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5)
        );
    }
    public static <E1, E2, E3, E4, E5, E6> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5,
            Class<E6> type6, ITransformer<E6> transformer6
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5),
                new Transformer<>(type6, transformer6)
        );
    }
    public static <E1, E2, E3, E4, E5, E6, E7> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5,
            Class<E6> type6, ITransformer<E6> transformer6,
            Class<E7> type7, ITransformer<E7> transformer7
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5),
                new Transformer<>(type6, transformer6),
                new Transformer<>(type7, transformer7)
        );
    }
    public static <E1, E2, E3, E4, E5, E6, E7, E8> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5,
            Class<E6> type6, ITransformer<E6> transformer6,
            Class<E7> type7, ITransformer<E7> transformer7,
            Class<E8> type8, ITransformer<E8> transformer8
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5),
                new Transformer<>(type6, transformer6),
                new Transformer<>(type7, transformer7),
                new Transformer<>(type8, transformer8)
        );
    }

    public static <E1, E2, E3, E4, E5, E6, E7, E8, E9> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5,
            Class<E6> type6, ITransformer<E6> transformer6,
            Class<E7> type7, ITransformer<E7> transformer7,
            Class<E8> type8, ITransformer<E8> transformer8,
            Class<E9> type9, ITransformer<E9> transformer9
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5),
                new Transformer<>(type6, transformer6),
                new Transformer<>(type7, transformer7),
                new Transformer<>(type8, transformer8),
                new Transformer<>(type9, transformer9)
        );
    }

    public static <E1, E2, E3, E4, E5, E6, E7, E8, E9, E10> TransformerSet of(
            Class<E1> type1, ITransformer<E1> transformer1,
            Class<E2> type2, ITransformer<E2> transformer2,
            Class<E3> type3, ITransformer<E3> transformer3,
            Class<E4> type4, ITransformer<E4> transformer4,
            Class<E5> type5, ITransformer<E5> transformer5,
            Class<E6> type6, ITransformer<E6> transformer6,
            Class<E7> type7, ITransformer<E7> transformer7,
            Class<E8> type8, ITransformer<E8> transformer8,
            Class<E9> type9, ITransformer<E9> transformer9,
            Class<E10> type10, ITransformer<E10> transformer10
    ) {
        return of(
                new Transformer<>(type1, transformer1),
                new Transformer<>(type2, transformer2),
                new Transformer<>(type3, transformer3),
                new Transformer<>(type4, transformer4),
                new Transformer<>(type5, transformer5),
                new Transformer<>(type6, transformer6),
                new Transformer<>(type7, transformer7),
                new Transformer<>(type8, transformer8),
                new Transformer<>(type9, transformer9),
                new Transformer<>(type10, transformer10)
        );
    }

    public ImmutableSet<Transformer<?>> get() {
        return TRANSFORMERS;
    }

}
