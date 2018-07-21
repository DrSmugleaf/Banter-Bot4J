package com.github.drsmugleaf.discord.oauth2;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 21/07/2018
 */
public enum Scope {

    CONNECTIONS("connections"),
    EMAIL("email"),
    IDENTIFY("identify"),
    GUILDS("guilds"),
    GUILDS_JOIN("guilds.join"),
    GDM_JOIN("gdm.join"),
    MESSAGES_READ("messages.read"),
    RPC("rpc"),
    RPC_API("rpc.api"),
    RPC_NOTIFICATIONS_READ("rpc.notifications.read"),
    WEBHOOK_INCOMING("webhook.incoming");

    @Nonnull
    public final String NAME;

    Scope(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
