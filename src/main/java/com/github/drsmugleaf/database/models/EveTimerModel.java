package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Created by DrSmugleaf on 02/05/2018.
 */
@Table(name = "eve_timers")
public class EveTimerModel extends Model<EveTimerModel> {

    @NotNull
    public static final ZoneOffset EVE_TIMEZONE = ZoneOffset.UTC;

    @NotNull
    private static final Timer TIMER = new Timer("Eve Structure Alert Timer", true);

    @NotNull
    private static final Map<EveTimerModel, TimerTask> TASKS = new HashMap<>();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(EVE_TIMEZONE);

    @Column(name = "channel")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Channel channel;

    @Column(name = "structure")
    @Column.Id
    public String structure;

    @Column(name = "system")
    @Column.Id
    public String system;

    @Column(name = "date")
    public Long date;

    @Column(name = "submitter")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public User submitter;

    public EveTimerModel(Long channel, String structure, String system, Long date, Long submitter) {
        this.channel = new Channel(channel);
        this.structure = structure;
        this.system = system;
        this.date = date;
        this.submitter = new User(submitter);
    }

    private EveTimerModel() {
    }

    private static void sendAlert(@NotNull IDiscordClient client, @NotNull EveTimerModel timer, boolean skipped) {
        String structure = timer.structure;
        String system = timer.system;
        Instant date = Instant.ofEpochMilli(timer.date);
        String formattedDate = DATE_FORMAT.format(date);
        IUser submitter = client.fetchUser(timer.submitter.id);
        IChannel channel = client.getChannelByID(timer.channel.id);
        String submitterName = submitter.getDisplayName(channel.getGuild());
        EmbedBuilder builder = new EmbedBuilder();

        builder
                .withTitle(skipped ? "SKIPPED STRUCTURE ALERT" : "Structure alert")
                .appendField("Structure", structure, true)
                .appendField("System", system, true)
                .appendField("Submitted by", submitterName, true)
                .withFooterText(formattedDate)
                .withColor(skipped ? Color.RED : Color.GREEN);

        Command.sendMessage(channel, "@everyone Structure timer", builder.build());

        deleteTimer(timer);
    }

    private static void sendAlert(@NotNull IDiscordClient client, @NotNull EveTimerModel timer) {
        sendAlert(client, timer, false);
    }

    public static void createTimer(@NotNull IDiscordClient client, @NotNull EveTimerModel timer) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendAlert(client, timer);
            }
        };

        TASKS.put(timer, task);
        TIMER.schedule(task, timer.date - System.currentTimeMillis());
    }

    public static boolean deleteTimer(@NotNull EveTimerModel timer) {
        for (Map.Entry<EveTimerModel, TimerTask> entry : TASKS.entrySet()) {
            EveTimerModel entryTimer = entry.getKey();
            TimerTask entryTask = entry.getValue();
            String entryTimerStructure = entryTimer.structure;
            String timerStructure = timer.structure;
            String entryTimerSystem = entryTimer.system;
            String timerSystem = timer.system;

            if (entryTimerStructure.equalsIgnoreCase(timerStructure) && entryTimerSystem.equalsIgnoreCase(timerSystem)) {
                entryTask.cancel();
                TASKS.remove(timer);
                timer.delete();
                return true;
            }
        }

        return false;
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            List<EveTimerModel> timers = new EveTimerModel(null, null, null, null, null).get();

            for (EveTimerModel timer : timers) {
                ZonedDateTime timerDate = Instant.ofEpochMilli(timer.date).atZone(EVE_TIMEZONE);
                ZonedDateTime now = ZonedDateTime.now(EVE_TIMEZONE);
                IDiscordClient client = event.getClient();

                if (timerDate.isBefore(now)) {
                    sendAlert(client, timer, true);
                } else {
                    createTimer(client, timer);
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
