package com.github.drsmugleaf.relay;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.delay.packet.DelayInformation;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/08/2018
 */
public class JabberListener implements MessageListener {

    @Nonnull
    private final IChannel RELAY_CHANNEL;

    public JabberListener(@Nonnull IChannel relayChannel) {
        RELAY_CHANNEL = relayChannel;
    }

    @Override
    public void processMessage(@Nonnull Message message) {
        DelayInformation information = DelayInformation.from(message);
        if (information != null) {
            return;
        }

        String authorName = message.getFrom().getResourceOrEmpty().toString();
        RELAY_CHANNEL.sendMessage("**" + authorName + "**: " + message.getBody());
    }

}
