package com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.google.common.collect.ImmutableSortedMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public class StageRegistry extends Registry<IStage> {

    private final ImmutableSortedMap<Integer, IStage> STAGES_BY_ID;

    public StageRegistry(IStage... stages) {
        super(stages);
        Map<Integer, IStage> stagesByID = new HashMap<>();
        for (IStage stage : stages) {
            stagesByID.put(stage.getStep(), stage);
        }

        STAGES_BY_ID = ImmutableSortedMap.copyOf(stagesByID);
    }

    public ImmutableSortedMap<Integer, IStage> getByID() {
        return STAGES_BY_ID;
    }

    public IStage get(Integer id) {
        return STAGES_BY_ID.get(id);
    }

}
