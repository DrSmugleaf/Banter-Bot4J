package com.github.drsmugbrain.pokemon.status;

import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.stats.IStat;
import com.github.drsmugbrain.pokemon.stats.StatBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 12/10/2017.
 */
public class Statuses {

    @Nullable
    private Status status = null;

    @Nonnull
    private final Map<BaseVolatileStatus, VolatileStatus> VOLATILE_STATUSES = new LinkedHashMap<>();

    public Statuses() {}

    private static final Map<IStat, Map<IModifier, Double>> defaultModifiers() {
        Map<IStat, Map<IModifier, Double>> statModifiers = new HashMap<>();

        for (IStat istat : StatBuilder.ISTATS) {
            statModifiers.put(istat, new HashMap<>());
        }

        return statModifiers;
    }

    @Nullable
    public Status getStatus() {
        return status;
    }

    public void resetStatus() {
        status = null;
    }

    public void setStatus(@Nonnull Status status) {
        this.status = status;
    }

    public void addVolatileStatus(@Nonnull Collection<VolatileStatus> statuses) {
        for (VolatileStatus status : statuses) {
            BaseVolatileStatus baseStatus = status.getBaseVolatileStatus();
            VOLATILE_STATUSES.put(baseStatus, status);
        }
    }

    public void addVolatileStatus(@Nonnull VolatileStatus... statuses) {
        addVolatileStatus(Arrays.asList(statuses));
    }

    @Nullable
    public VolatileStatus getVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.get(status);
    }

    @Nonnull
    public Map<BaseVolatileStatus, VolatileStatus> getVolatileStatuses() {
        return new LinkedHashMap<>(VOLATILE_STATUSES);
    }

    public boolean hasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.containsKey(status);
    }

    public void removeVolatileStatus(@Nonnull BaseVolatileStatus... statuses) {
        for (BaseVolatileStatus status : statuses) {
            VOLATILE_STATUSES.remove(status);
        }
    }

}
