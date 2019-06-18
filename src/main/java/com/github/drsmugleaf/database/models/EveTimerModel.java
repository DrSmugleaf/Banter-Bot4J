package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Snowflake;

import javax.annotation.Nonnull;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

/**
 * Created by DrSmugleaf on 02/05/2018.
 */
@Table(name = "eve_timers")
public class EveTimerModel extends Model<EveTimerModel> {

    @Nonnull
    public static final ZoneOffset EVE_TIMEZONE = ZoneOffset.UTC;

    @Nonnull
    private static final Timer TIMER = new Timer("Eve Structure Alert Timer", true);

    @Nonnull
    private static final Map<EveTimerModel, TimerTask> TASKS = new HashMap<>();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(EVE_TIMEZONE);

    @Column(name = "channel")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordChannel channel;

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
    public DiscordUser submitter;

    public EveTimerModel(Long channel, String structure, String system, Long date, Long submitter) {
        this.channel = new DiscordChannel(channel);
        this.structure = structure;
        this.system = system;
        this.date = date;
        this.submitter = new DiscordUser(submitter);
    }

    private EveTimerModel() {}

    private static void sendAlert(@Nonnull DiscordClient client, @Nonnull EveTimerModel timer, boolean skipped) {
        String structure = timer.structure;
        String system = timer.system;
        Instant date = Instant.ofEpochMilli(timer.date);
        String formattedDate = DATE_FORMAT.format(date);

        client
                .getChannelById(Snowflake.of(timer.channel.id))
                .cast(MessageChannel.class)
                .zipWith(
                        client
                                .getUserById(Snowflake.of(timer.submitter.id))
                                .map(user -> user.getUsername() + "#" + user.getDiscriminator())
                )
                .map(tuple -> {
                    MessageChannel channel = tuple.getT1();
                    String submitterName = tuple.getT2();

                    return channel.createMessage(message -> {
                        message.setContent("@everyone Structure timer");

                        message.setEmbed(embed -> {
                            embed
                                    .setTitle(skipped ? "SKIPPED STRUCTURE ALERT" : "Structure alert")
                                    .addField("Structure", structure, true)
                                    .addField("System", system, true)
                                    .addField("Submitted by", submitterName, true)
                                    .setFooter(formattedDate, null)
                                    .setColor(skipped ? Color.RED : Color.GREEN);
                        });
                    });
                })
                .subscribe(message -> deleteTimer(timer));
    }

    private static void sendAlert(@Nonnull DiscordClient client, @Nonnull EveTimerModel timer) {
        sendAlert(client, timer, false);
    }

    public static void createTimer(@Nonnull DiscordClient client, @Nonnull EveTimerModel timer) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendAlert(client, timer);
            }
        };

        TASKS.put(timer, task);
        TIMER.schedule(task, timer.date - System.currentTimeMillis());
    }

    public static boolean deleteTimer(@Nonnull EveTimerModel timer) {
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

    @EventListener(ReadyEvent.class)
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            List<EveTimerModel> timers = new EveTimerModel(null, null, null, null, null).get();

            for (EveTimerModel timer : timers) {
                ZonedDateTime timerDate = Instant.ofEpochMilli(timer.date).atZone(EVE_TIMEZONE);
                ZonedDateTime now = ZonedDateTime.now(EVE_TIMEZONE);
                DiscordClient client = event.getClient();

                if (timerDate.isBefore(now)) {
                    sendAlert(client, timer, true);
                } else {
                    createTimer(client, timer);
                }

                Database.LOGGER.info(
                        "Scheduled eve structure alert for structure %s in system %s in %s seconds",
                        timer.structure, timer.system, timer.date / 1000
                );
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
