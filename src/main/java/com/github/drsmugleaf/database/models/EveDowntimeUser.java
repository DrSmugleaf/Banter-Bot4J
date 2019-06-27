package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.StatusApi;

import javax.ws.rs.ProcessingException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DrSmugleaf on 25/05/2018.
 */
@Table(name = "eve_downtime_users")
public class EveDowntimeUser extends Model<EveDowntimeUser> {

    private static final Timer DOWNTIME_TIMER = new Timer("Eve Online Downtime Timer", true);

    private static final StatusApi STATUS_API = new StatusApi();

    private static boolean wasEveOffline = false;

    @Column(name = "user_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public DiscordUser user;

    public EveDowntimeUser(Long userID) {
        user = new DiscordUser(userID);
    }

    private EveDowntimeUser() {}

    private static boolean isOffline() {
        try {
            STATUS_API.getStatus("tranquility", null);
        } catch (ApiException e) {
            if (e.getCode() == 503) {
                return true;
            }
        } catch (ProcessingException e) {
            BanterBot4J.LOGGER.error("Error contacting Eve Online API", e);
        }

        return false;
    }

    private static void alertAll() {
        List<EveDowntimeUser> users = new EveDowntimeUser().get();
        for (EveDowntimeUser user : users) {
            BanterBot4J
                    .CLIENT
                    .getUserById(user.user.user().getId())
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage("Eve Online server is back online."))
                    .subscribe();
        }
    }

    @EventListener(ReadyEvent.class)
    public static void handle(ReadyEvent event) {
        Database.LOGGER.info("Scheduled EVE Online downtime timer");

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
