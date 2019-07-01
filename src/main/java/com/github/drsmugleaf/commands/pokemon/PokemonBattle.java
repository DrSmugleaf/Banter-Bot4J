package com.github.drsmugleaf.commands.pokemon;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.pokemon.battle.*;
import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by DrSmugleaf on 11/06/2017.
 */
public class PokemonBattle extends Command {

    private static final Map<User, TrainerBuilder> awaitingTrainer = new LinkedHashMap<>();

    @Override
    public void run() {
        Mono
                .just(EVENT)
                .map(MessageCreateEvent::getMessage)
                .flatMap(message -> message.getAuthor().map(Mono::just).orElseGet(Mono::empty))
                .doOnNext(user1 -> {
                    TrainerBuilder trainerBuilder1 = new TrainerBuilder();
                    trainerBuilder1
                            .setID(user1.getId().asLong())
                            .setName(user1.getUsername())
                            .addPokemons(ARGUMENTS.toString());

                    if (awaitingTrainer.isEmpty()) {
                        EVENT
                                .getMessage()
                                .getAuthor()
                                .ifPresent(author -> awaitingTrainer.put(author, trainerBuilder1));
                        return;
                    }

                    Entry<User, TrainerBuilder> userTrainerEntry = awaitingTrainer.entrySet().iterator().next();
                    User user2 = userTrainerEntry.getKey();
                    TrainerBuilder trainerBuilder2 = userTrainerEntry.getValue();

                    BattleBuilder battleBuilder = new BattleBuilder().addTrainer(trainerBuilder1, trainerBuilder2);

                    Battle battle;
                    try {
                        battle = battleBuilder.build();
                    } catch (UserException e) {
                        EVENT
                                .getMessage()
                                .getChannel()
                                .flatMap(channel -> channel.createMessage(e.getMessage()))
                                .subscribe();
                        return;
                    }

                    PokemonEvents.BATTLES.put(user1, battle);
                    PokemonEvents.BATTLES.put(user2, battle);
                })
                .subscribe();
    }

}
