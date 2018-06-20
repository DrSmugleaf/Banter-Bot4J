package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.AbstractCommand;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import com.github.drsmugleaf.eve.esi.ApiException;
import com.github.drsmugleaf.eve.esi.api.StatusApi;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;

import javax.annotation.Nonnull;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DrSmugleaf on 25/05/2018.
 */
@Table(name = "eve_downtime_users")
public class EveDowntimeUser extends Model<EveDowntimeUser> {

    @Nonnull
    private static final Timer DOWNTIME_TIMER = new Timer("Eve Online Downtime Timer", true);

    @Nonnull
    private static final StatusApi STATUS_API = new StatusApi();

    private static boolean wasEveOffline = false;

    @Column(name = "user_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public User user;

    public EveDowntimeUser(Long userID) {
        user = new User(userID);
    }

    private EveDowntimeUser() {}

    private static boolean isOffline() {
        try {
            STATUS_API.getStatus("tranquility", null, null, null);
        } catch (ApiException e) {
            if (SocketTimeoutException.class.isInstance(e.getCause())) {
                return true;
            }
        }

        return false;
    }

    private static void alertAll() {
        List<EveDowntimeUser> users = new EveDowntimeUser().get();
        for (EveDowntimeUser user : users) {
            IPrivateChannel channel = BanterBot4J.CLIENT.getUserByID(user.user.id).getOrCreatePMChannel();
            AbstractCommand.sendMessage(channel, "Eve Online server is online again.");
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        DOWNTIME_TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isOffline()) {
                    wasEveOffline = true;
                } else if (wasEveOffline) {
                    wasEveOffline = false;
                    alertAll();
                }
            }
        }, 0, 30000);
    }

}
