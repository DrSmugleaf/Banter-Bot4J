package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon.stats.StatBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 12/10/2017.
 */
public class Statuses {

    @Nullable
    private Status status = null;

    @NotNull
    private final Map<BaseVolatileStatus, VolatileStatus> VOLATILE_STATUSES = new LinkedHashMap<>();

    public Statuses() {}

    @NotNull
    private static Map<IStat, Map<IModifier, Double>> defaultModifiers() {
        Map<IStat, Map<IModifier, Double>> statModifiers = new HashMap<>();

        for (IStat istat : StatBuilder.STAT_LIST) {
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

    public void setStatus(@NotNull Status status) {
        this.status = status;
    }

    public void addVolatileStatus(@NotNull Collection<VolatileStatus> statuses) {
        for (VolatileStatus status : statuses) {
            BaseVolatileStatus baseStatus = status.BASE_VOLATILE_STATUS;
            VOLATILE_STATUSES.put(baseStatus, status);
        }
    }

    public void addVolatileStatus(@NotNull VolatileStatus... statuses) {
        addVolatileStatus(Arrays.asList(statuses));
    }

    @Nullable
    public VolatileStatus getVolatileStatus(@NotNull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.get(status);
    }

    @NotNull
    public Map<BaseVolatileStatus, VolatileStatus> getVolatileStatuses() {
        return new LinkedHashMap<>(VOLATILE_STATUSES);
    }

    public boolean hasVolatileStatus(@NotNull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.containsKey(status);
    }

    public void removeVolatileStatus(@NotNull BaseVolatileStatus... statuses) {
        for (BaseVolatileStatus status : statuses) {
            VOLATILE_STATUSES.remove(status);
        }
    }

}
