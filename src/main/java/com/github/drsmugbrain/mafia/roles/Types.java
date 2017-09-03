package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 02/09/2017.
 */
public enum Types {

    DETAIN("Detain", 1),
    CHAT("Chat", 2),
    BULLETPROOF_VEST("Bulletproof Vest", 3),
    TARGET_SWITCH("Target Switch", 4),
    ROLE_BLOCK("Role Block", 5),
    FRAME("Frame", 6),
    ARSON("Arson", 7),
    MISCELLANEOUS("Miscellaneous", 8),
    KILL("Kill", 9),
    SUICIDE("Suicide", 9),
    JANITOR("Janitor", 10),
    INVESTIGATION("Investigation", 11),
    DISGUISE("Disguise", 12),
    MASON("Mason", 13),
    CULT("Cult", 14),
    NONE("None", Integer.MAX_VALUE);

    private final String NAME;
    private final int PRIORITY;

    Types(@Nonnull String name, int priority) {
        this.NAME = name;
        this.PRIORITY = priority;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    public int getPriority() {
        return this.PRIORITY;
    }

}
