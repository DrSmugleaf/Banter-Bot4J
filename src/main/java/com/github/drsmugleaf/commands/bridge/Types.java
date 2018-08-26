package com.github.drsmugleaf.commands.bridge;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.relay.JabberListener;
import com.google.common.net.InternetDomainName;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 23/08/2018
 */
public enum Types {

    JABBER("Jabber") {
        @Nonnull
        private final Map<JabberListener, MultiUserChat> RELAYS = new HashMap<>();

        @Nonnull
        @Override
        public Validation setupRelay(@Nonnull Arguments args, @Nonnull CommandReceivedEvent event) {
            if (args.size() != 8) {
                return new Validation(false, invalidArgumentsResponse());
            }

            Validation allValidations = Validation.from(
                    isValidIP(args.get(1)),
                    isValidPort(args.get(2)),
                    isValidDomainName(args.get(3)),
                    isValidUsername(args.get(4)),
                    isValidPassword(args.get(5)),
                    isValidJabberChatName(args.get(6)),
                    isValidDiscordChannelName(args.get(7), event)
            );

            if (!allValidations.IS_VALID) {
                return allValidations;
            }

            try {
                addListener(args, event);
                return new Validation(true);
            } catch (IOException | InterruptedException | XMPPException | SmackException e) {
                Relay.LOGGER.error("Error setting up Jabber chat relay", e);
                return new Validation(false, "Error setting up Jabber chat relay");
            }
        }

        @Nonnull
        @Override
        public String invalidArgumentsResponse() {
            return "Invalid arguments.\n" +
                   "Usage: " + Command.botPrefix + "relay jabber \"ip\" \"port\" \"domain name\" \"username\" \"password\" \"chat room name to listen to (1@conference.216.58.211.36)\" \"discord channel name to post to\"\n" +
                   "Example: " + Command.botPrefix + "relay jabber 216.58.211.36 5222 www.google.com DrSmugleaf PaSsWoRd 1@conference.216.58.211.36 jabber-relay";
        }

        private void addListener(@Nonnull Arguments args, @Nonnull CommandReceivedEvent event) throws IOException, InterruptedException, XMPPException, SmackException {
            String ip = args.get(1);
            Integer port = Integer.parseInt(args.get(2));
            String domainName = args.get(3);
            String username = args.get(4);
            Resourcepart usernameResourcepart = Resourcepart.from(username);
            String password = args.get(5);
            String jabberChatName = args.get(6);
            EntityBareJid jabberChatJID = JidCreate.entityBareFrom(jabberChatName);
            String discordChannelName = args.get(7);
            IChannel discordChannel = event.getGuild().getChannelsByName(discordChannelName).get(0);
            JabberListener listener = new JabberListener(discordChannel);
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost(ip)
                    .setPort(port)
                    .setXmppDomain(domainName)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setUsernameAndPassword(username, password)
                    .build();

            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect().login();
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            MultiUserChat chat = manager.getMultiUserChat(jabberChatJID);
            MucEnterConfiguration mucConfig = chat.getEnterConfigurationBuilder(usernameResourcepart).build();
            chat.join(mucConfig);
            chat.addMessageListener(listener);

            RELAYS.put(listener, chat);
        }

        private void removeListener(@Nonnull JabberListener listener) {
            MultiUserChat chat = RELAYS.remove(listener);
            chat.removeMessageListener(listener);
        }

        @Nonnull
        private Validation isValidIP(@Nullable String ip) {
            if (ip == null) {
                return new Validation(false, "Error validating server IP: No IP was provided");
            }

            String[] octets = ip.split("\\.");
            if (octets.length != 4) {
                return new Validation(false, "Error validating server IP: IP address has " + octets.length + " octets instead of 4");
            }

            for (String octetString : octets) {
                int octetInteger;
                try {
                    octetInteger = Integer.parseInt(octetString);
                } catch (NumberFormatException e) {
                    return new Validation(false, "Error validating server IP: " + octetString + " isn't a valid number");
                }

                if (octetInteger < 0 || octetInteger > 255) {
                    return new Validation(false, "Error validating server IP: " + octetInteger + " isn't between 0 and 255");
                }
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidPort(@Nullable String portString) {
            if (portString == null) {
                return new Validation(false, "Error validating server port: No port was provided");
            }

            try {
                Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                return new Validation(false, "Error validating server port: Port isn't a valid number");
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidDomainName(@Nullable String domainName) {
            if (domainName == null) {
                return new Validation(false, "Error validating domain name: No domain name was provided");
            }

            try {
                InternetDomainName.isValid(domainName);
            } catch (IllegalArgumentException e) {
                return new Validation(false, "Error validating domain name: " + e.getMessage());
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidUsername(@Nullable String username) {
            if (username == null) {
                return new Validation(false, "Error validating username: No username was provided");
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidPassword(@Nullable String password) {
            if (password == null) {
                return new Validation(false, "Error validating password: No password was provided");
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidJabberChatName(@Nullable String chatName) {
            if (chatName == null) {
                return new Validation(false, "Error validating jabber chat name: No jabber chat name was provided");
            }

            try {
                JidCreate.entityBareFrom(chatName);
            } catch (XmppStringprepException e) {
                return new Validation(false, e.getMessage());
            }

            return new Validation(true);
        }

        @Nonnull
        private Validation isValidDiscordChannelName(@Nullable String chatName, @Nonnull CommandReceivedEvent event) {
            if (chatName == null) {
                return new Validation(false, "Error validating discord chat name: No discord chat name was provided");
            }

            List<IChannel> channels = event.getGuild().getChannelsByName(chatName);
            if (channels.isEmpty()) {
                return new Validation(false, "Error validating discord chat name: No discord channel with name " + chatName + " exists in this server");
            }

            if (channels.size() > 1) {
                return new Validation(false, "Error validating discord chat name: More than 1 channel with the name " + chatName + " exist in this server");
            }

            return new Validation(true);
        }
    };

    @Nonnull
    private final String NAME;

    @Nonnull
    private final List<String> ALIASES = new ArrayList<>();

    Types(@Nonnull String... aliases) {
        if (aliases.length == 0) {
            throw new IllegalArgumentException("No aliases provided for relay type" + this);
        }

        NAME = aliases[0];
        Holder.NAMES.add(NAME);

        for (String alias : aliases) {
            ALIASES.add(alias.toLowerCase());
        }
    }

    @Nonnull
    public static String getAllNames() {
        return String.join(", ", Holder.NAMES);
    }

    @Nullable
    public static Types from(@Nullable String name) {
        if (name == null) {
            return null;
        }

        name = name.toLowerCase();

        for (Types type : values()) {
            if (type.ALIASES.contains(name)) {
                return type;
            }
        }

        return null;
    }

    @Nonnull
    public abstract Validation setupRelay(@Nonnull Arguments args, @Nonnull CommandReceivedEvent event);

    @Nonnull
    public abstract String invalidArgumentsResponse();

    private static class Holder {
        @Nonnull
        static final List<String> NAMES = new ArrayList<>();
    }

}
