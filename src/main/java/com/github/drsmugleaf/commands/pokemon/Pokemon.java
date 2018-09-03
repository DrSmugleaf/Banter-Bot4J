package com.github.drsmugleaf.commands.pokemon;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.pokemon.battle.*;
import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by DrSmugleaf on 11/06/2017.
 */
public class Pokemon extends Command {

    private static final Map<IUser, TrainerBuilder> awaitingTrainer = new LinkedHashMap<>();

    protected Pokemon(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IUser user1 = EVENT.getAuthor();
        long id = user1.getLongID();
        String name = user1.getName();

        TrainerBuilder trainerBuilder1 = new TrainerBuilder();
        trainerBuilder1
                .setID(id)
                .setName(name)
                .addPokemons(ARGS.toString());

        if (awaitingTrainer.isEmpty()) {
            awaitingTrainer.put(EVENT.getAuthor(), trainerBuilder1);
            return;
        }

        Entry<IUser, TrainerBuilder> userTrainerEntry = awaitingTrainer.entrySet().iterator().next();
        IUser user2 = userTrainerEntry.getKey();
        TrainerBuilder trainerBuilder2 = userTrainerEntry.getValue();

        BattleBuilder battleBuilder = new BattleBuilder();
        Setup setup = new Setup(Generation.VII, Variation.SINGLE_BATTLE);

        battleBuilder
                .setSetup(setup)
                .addTrainer(trainerBuilder1, trainerBuilder2);

        Battle battle = null;
        try {
            battle = battleBuilder.build();
        } catch (UserException e) {
            sendMessage(EVENT.getChannel(), e.getMessage());
        }

        PokemonEvents.BATTLES.put(user1, battle);
        PokemonEvents.BATTLES.put(user2, battle);
    }

}
