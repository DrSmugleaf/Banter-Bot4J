package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public enum Priority {

    ACCELEROCK("Accelerock", 1),
    ALLY_SWITCH("Ally Switch", 2),
    AQUA_JET("Aqua Jet", 1),
    AVALANCHE("Avalanche", -4),
    BABY_DOLL_EYES("Baby-Doll Eyes", 1),
    BANEFUL_BUNKER("Baneful Bunker", 4),
    BEAK_BLAST("Beak Blast", -3),
    BIDE("Bide", 1),
    BULLET_PUNCH("Bullet Punch", 1),
    CIRCLE_THROW("Circle Throw", -6),
    COUNTER("Counter", -5),
    CRAFTY_SHIELD("Crafty Shield", 3),
    DETECT("Detect", 4),
    DRAGON_TAIL("Dragon Tail", -6),
    ENDURE("Endure", 4),
    EXTREMESPEED("Extremespeed", 2),
    FAKE_OUT("Fake Out", 3),
    FEINT("Feint", 2),
    FIRST_IMPRESSION("First Impression", 2),
    FOCUS_PUNCH("Focus Punch", -3),
    FOLLOW_ME("Follow Me", 2),
    HELPING_HAND("Helping Hnd", 5),
    ICE_SHARD("Ice Shard", 1),
    ION_DELUGE("Ion Deluge", 1),
    KINGS_SHIELD("King's Shield", 4),
    MACH_PUNCH("Mach Punch", 1),
    MAGIC_COAT("Magic Coat", 4),
    MIRROR_COAT("Mirror Coat", -5),
    POWDER("Powder", 1),
    PROTECT("Protect", 4),
    QUICK_ATTACK("Quick Attack", 1),
    QUICK_GUARD("Quick Guard", 3),
    RAGE_POWDER("Rage Powder", 2),
    REVENGE("Revenge", -4),
    ROAR("Roar", -6),
    SHADOW_SNEAK("Shadow Sneak", 1),
    SHELL_TRAP("Shell Trap", -3),
    SNATCH("Snatch", 4),
    SPIKY_SHIELD("Spiky Shield", 4),
    SPOTLIGHT("Spotlight", 3),
    SUCKER_PUNCH("Sucker Punch", 1),
    TRICK_ROOM("Trick Room", -7),
    VACUUM_WAVE("Vacuum Wave", 1),
    VITAL_THROW("Vital Throw", -1),
    WATER_SHURIKEN("Water Shuriken", 1),
    WHIRLWIND("Whirlwind", -6),
    WIDE_GUARD("Wide Guard", 3);

    private final String NAME;
    private final Integer PRIORITY;

    Priority(@Nonnull String name, Integer priority) {
        this.NAME = name;
        this.PRIORITY = priority;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Integer getPriority() {
        return this.PRIORITY;
    }

    public static int getPriority(@Nonnull String move) {
        if(!Holder.MAP.containsKey(move)) return 0;
        return Holder.MAP.get(move).PRIORITY;
    }

    public static int getPriority(@Nonnull BaseMove move) {
        return Priority.getPriority(move.getName());
    }

    @Nonnull
    public static Priority getPriorityEnum(@Nonnull String move) {
        if(!Holder.MAP.containsKey(move)) {
            throw new NullPointerException("Move " + move + " doesn't exist");
        }

        return Holder.MAP.get(move);
    }

    private static class Holder {
        static Map<String, Priority> MAP = new HashMap<>();
    }

}
