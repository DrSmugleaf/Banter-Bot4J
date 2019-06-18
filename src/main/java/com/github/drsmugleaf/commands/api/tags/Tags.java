package com.github.drsmugleaf.commands.api.tags;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Created by DrSmugleaf on 16/01/2018.
 */
public enum Tags implements Tag {

    DELETE_COMMAND_MESSAGE {

        @Override
        public void execute(@Nonnull CommandReceivedEvent event) {
            event.getMessage().delete().subscribe();
        }

    },
    GUILD_ONLY {

        @Override
        public boolean isValid(@Nonnull MessageCreateEvent event) {
            return event.getGuild().blockOptional().isPresent();
        }

        @Nonnull
        @Override
        public String message() {
            return "That command must be used in a server channel.";
        }

    },
    OWNER_ONLY {

        @Override
        public boolean isValid(@Nonnull MessageCreateEvent event) {
            return Command.isOwner(event.getMember().orElse(null));
        }

        @Nonnull
        @Override
        public String message() {
            return "You don't have permission to use that command.";
        }

    },
    SAME_VOICE_CHANNEL {

        @Override
        public boolean isValid(@Nonnull MessageCreateEvent event) {
            if (!GUILD_ONLY.isValid(event)) {
                return false;
            }

            Optional<Snowflake> authorVoice = event
                    .getMember()
                    .flatMap(author -> author.getVoiceState().blockOptional())
                    .flatMap(VoiceState::getChannelId);

            return event
                    .getGuild()
                    .zipWith(Mono.justOrEmpty(event.getClient().getSelfId()))
                    .flatMap(tuple -> tuple.getT1().getMemberById(tuple.getT2()))
                    .flatMap(Member::getVoiceState)
                    .map(voice -> voice.getChannelId().equals(authorVoice))
                    .blockOptional()
                    .orElse(false);
        }

        @Nonnull
        @Override
        public String message() {
            return "You aren't in the same voice channel as me.";
        }

    },
    VOICE_ONLY {

        @Override
        public boolean isValid(@Nonnull MessageCreateEvent event) {
            if (!GUILD_ONLY.isValid(event)) {
                return false;
            }

            return event
                    .getMember()
                    .flatMap(author -> author.getVoiceState().blockOptional())
                    .isPresent();
        }

        @Nonnull
        @Override
        public String message() {
            return "You must be in a voice channel to use that command.";
        }

    };

    public boolean isValid(@Nonnull MessageCreateEvent event) {
        return true;
    }

    @Nonnull
    public String message() {
        return "You can't use that command.";
    }

    public void execute(@Nonnull CommandReceivedEvent event) {}

}
