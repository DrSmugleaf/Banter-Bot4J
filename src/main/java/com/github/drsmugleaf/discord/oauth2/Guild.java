package com.github.drsmugleaf.discord.oauth2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 22/07/2018
 */
public class Guild {

    public final boolean IS_OWNER;

    public final long PERMISSIONS;

    @Nullable
    public final String ICON;

    @Nonnull
    public final String NAME;

    public final long ID;

    Guild(boolean isOwner, long permissions, @Nullable String icon, @Nonnull String name, long id) {
        IS_OWNER = isOwner;
        PERMISSIONS = permissions;
        ICON = icon;
        NAME = name;
        ID = id;
    }

}
