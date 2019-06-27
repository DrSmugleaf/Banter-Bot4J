package com.github.drsmugleaf.commands.pokemon;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Player;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 03/09/2018
 */
public class PokemonEvents {

    static final Map<User, Battle> BATTLES = new HashMap<>();

    static {
        EventDispatcher.registerListener(new PokemonEvents());
    }

    private static Consumer<EmbedCreateSpec> sendOutPokemonEmbed(Trainer trainer) {
        return spec -> {
            spec.setTitle("Which Pokemon do you want to send out? (Reply with the number of the Pokemon)");

            List<Pokemon> aliveInactivePokemons = trainer.getAliveInactivePokemons();
            for (int i = 0; i < aliveInactivePokemons.size(); i++) {
                Pokemon pokemon = aliveInactivePokemons.get(i);
                int currentHP = pokemon.getHP();
                int maxHP = pokemon.getMaxHP();
                double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;
                spec.addField(
                        i+1 + ": " + pokemon.getName() + " (" + String.join(" ", pokemon.TYPES.getTypesString()) + ")",
                        "HP: " + percentageHP + "% (" + currentHP + "/" + maxHP + ")\n" +
                                "Ability: " + pokemon.ABILITY.get().getName() + " / Item: " + (pokemon.ITEM.get() != null ? pokemon.ITEM.get().NAME : "None") + "\n" +
                                "Stats: " + pokemon.getStatsStringWithoutHP(),
                        true
                );
            }
        };
    }

    private static Consumer<EmbedCreateSpec> chooseMoveEmbed(Trainer trainer) {
        return spec -> {
            spec.setTitle("Which move do you want to use?");

            for (BaseMove baseMove : trainer.getPokemonInFocus().MOVES.getValid()) {
                spec.addField(
                        baseMove.NAME,
                        baseMove.TYPE.getName() + " " + baseMove.PP + "/" + baseMove.PP,
                        true
                );
            }
        };
    }

    private static Consumer<EmbedCreateSpec> chooseTargetEmbed(Trainer trainer) {
        return spec -> {
            spec.setTitle("Who do you want to target?");

            int i = 1;
            for (Pokemon pokemon : trainer.BATTLE.getTargetList(trainer.getPokemonInFocus())) {
                int currentHP = pokemon.getHP();
                int maxHP = pokemon.getMaxHP();
                double percentageHP = Math.round((100.0 * currentHP / maxHP) * 10) / 10.0;

                spec.addField(
                        i + ": " + pokemon.getName(),
                        "HP: " + percentageHP + "%",
                        true
                );
                i++;
            }
        };
    }

    @EventListener(MessageCreateEvent.class)
    public static void handle(MessageCreateEvent event) {
        event.getMessage().getContent().ifPresent(message -> Mono
                .justOrEmpty(message)
                .filter(content -> !content.startsWith(BanterBot4J.BOT_PREFIX + "pokemon"))
                .zipWith(Mono.justOrEmpty(event.getMessage().getAuthor()))
                .filter(tuple -> BATTLES.containsKey(tuple.getT2()))
                .zipWith(event.getMessage().getChannel())
                .map(tuple -> Tuples.of(tuple.getT1().getT1(), tuple.getT1().getT2(), tuple.getT2()))
                .filter(tuple -> tuple.getT3().getType() == Channel.Type.DM)
                .zipWhen(tuple -> Mono.justOrEmpty(BATTLES.get(tuple.getT2())))
                .zipWhen(tuple -> Mono.justOrEmpty(tuple.getT2().getTrainer(tuple.getT1().getT2().getId().asLong())))
                .map(tuple -> Tuples.of(
                        tuple.getT1().getT1().getT1(),
                        tuple.getT1().getT1().getT2(),
                        tuple.getT1().getT1().getT3(),
                        tuple.getT1().getT2(),
                        tuple.getT2())
                )
                .flatMap(tuple -> {
                    MessageChannel channel = tuple.getT3();
                    Battle battle = tuple.getT4();
                    Trainer trainer = tuple.getT5();

                    switch (trainer.getStatus()) {
                        case NONE:
                            return Mono.empty();
                        case CHOOSING_POKEMON: {
                            Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                            if (!scanner.hasNextInt()) {
                                return channel.createMessage("Invalid Pokemon ID.");
                            }

                            int pokemonID = scanner.nextInt() - 1;
                            Pokemon chosenPokemon = trainer.getAliveInactivePokemons().get(pokemonID);
                            battle.sendOut(trainer, chosenPokemon);
                            trainer.setPokemonInFocus(chosenPokemon);

                            return Mono.empty();
                        }
                        case CHOOSING_MOVE:
                            if (!trainer.getPokemonInFocus().MOVES.hasOne(message)) {
                                return channel.createMessage("Invalid move name.");
                            }

                            trainer.setChosenMove(trainer.getPokemonInFocus().MOVES.get(message));

                            return Mono.empty();
                        case CHOOSING_TARGET: {
                            Scanner scanner = new Scanner(message).useDelimiter("[^0-9]+");
                            if (!scanner.hasNextInt()) {
                                return channel.createMessage("Invalid Pokemon ID.");
                            }

                            int pokemonID = scanner.nextInt() - 1;
                            battle.addAction(trainer, trainer.getPokemonInFocus(), trainer.getChosenMove(), battle.getTargetList(trainer.getPokemonInFocus()).get(pokemonID));

                            return Mono.empty();
                        }
                        case WAITING:
                            return Mono.empty();
                        default:
                            throw new IllegalStateException("Unrecognized trainer status: " + trainer.getStatus());
                    }
                })
                .subscribe());
    }

    @PokemonEventHandler(event = BattleStartedEvent.class)
    public static void handle(BattleStartedEvent event) {
        Battle battle = event.BATTLE;
        StringBuilder response = new StringBuilder();

        Flux
                .fromIterable(battle.getTrainers().values())
                .map(trainer -> Tuples.of(trainer, Arrays.stream(trainer.getPokemons()).map(Pokemon::getName)))
                .doOnNext(tuple -> response
                        .append(tuple.getT1().getName())
                        .append("'s team:\n")
                        .append(tuple.getT2().collect(Collectors.joining(" / ")))
                        .append("\n")
                )
                .map(Tuple2::getT1)
                .map(Player::getID)
                .map(Snowflake::of)
                .flatMap(BanterBot4J.CLIENT::getUserById)
                .flatMap(User::getPrivateChannel)
                .flatMap(channel -> channel.createMessage(response.toString()))
                .subscribe();
    }

    @PokemonEventHandler(event = PokemonDamagedEvent.class)
    public static void handle(PokemonDamagedEvent event) {
        Pokemon defender = event.POKEMON;
        StringBuilder response = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());
            String hpLoss = decimalFormat.format(100.0 * event.DAMAGE / defender.calculate(PermanentStat.HP));

            if (!defender.getTrainer().ID.equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(defender.getNickname())
                    .append(" lost ")
                    .append(hpLoss)
                    .append("% of its health!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = PokemonMoveEvent.class)
    public static void handle(PokemonMoveEvent event) {
        Pokemon attacker = event.POKEMON;
        StringBuilder response = new StringBuilder();

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!attacker.getTrainer().ID.equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(attacker.getNickname())
                    .append(" used **")
                    .append(event.MOVE.BASE_MOVE.NAME)
                    .append("**!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = TrainerSendOutPokemonEvent.class)
    public static void handle(TrainerSendOutPokemonEvent event) {
        Trainer trainer = event.TRAINER;
        Pokemon pokemon = event.POKEMON;
        StringBuilder response = new StringBuilder();

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!Objects.equals(trainer.getID(), id)) {
                response
                        .append(trainer.getName())
                        .append(" sent out ");
            } else {
                response.append("Go! ");
            }

            response.append(pokemon.getNickname());

            if (!Objects.equals(pokemon.getNickname(), pokemon.getName())) {
                response
                        .append(" (")
                        .append(pokemon.getName())
                        .append(")");
            }

            response.append("!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = TrainerSendBackPokemonEvent.class)
    public static void handle(TrainerSendBackPokemonEvent event) {
        Trainer trainer = event.TRAINER;
        Pokemon pokemon = event.POKEMON;
        StringBuilder response = new StringBuilder();

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!Objects.equals(trainer.ID, id)) {
                response
                        .append(trainer.getName())
                        .append(" withdrew ")
                        .append(pokemon.getNickname());

                if (!Objects.equals(pokemon.getNickname(), pokemon.getName())) {
                    response
                            .append(" (")
                            .append(pokemon.getName())
                            .append(")");
                }
            } else {
                response
                        .append(pokemon.getName())
                        .append(", come back");
            }

            response.append("!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = PokemonDeathEvent.class)
    public static void handle(PokemonDeathEvent event) {
        Pokemon pokemon = event.POKEMON;
        Trainer trainer = event.POKEMON.getTrainer();
        StringBuilder response = new StringBuilder();

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!trainer.ID.equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(pokemon.getNickname())
                    .append(" fainted!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = BattleTurnStartEvent.class)
    public static void handle(BattleTurnStartEvent event) {
        for (Trainer trainer : event.BATTLE.getTrainers().values()) {
            Mono
                    .just(trainer.getID())
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel
                            .createMessage(spec -> spec
                                    .setContent("**TURN " + event.BATTLE.getTurn() + "**")
                                    .setEmbed(chooseMoveEmbed(trainer))
                            )
                    )
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = TrainerChooseMoveEvent.class)
    public static void handle(TrainerChooseMoveEvent event) {
        Mono
                .just(event)
                .map(TrainerEvent::getTrainer)
                .map(Player::getID)
                .map(Snowflake::of)
                .flatMap(BanterBot4J.CLIENT::getUserById)
                .flatMap(User::getPrivateChannel)
                .flatMap(channel -> channel.createEmbed(chooseTargetEmbed(event.getTrainer())))
                .subscribe();
    }

    @PokemonEventHandler(event = PokemonDodgeEvent.class)
    public static void handle(PokemonDodgeEvent event) {
        Pokemon target = event.POKEMON;
        Trainer trainer = event.POKEMON.getTrainer();
        StringBuilder response = new StringBuilder();

        for (Long id : event.BATTLE.getTrainers().keySet()) {
            response.delete(0, response.length());

            if (!trainer.ID.equals(id)) {
                response.append("The opposing ");
            }

            response
                    .append(target.getNickname())
                    .append(" avoided the attack!");

            Mono
                    .just(id)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
        }
    }

    @PokemonEventHandler(event = TrainerChoosingPokemonEvent.class)
    public static void handle(TrainerChoosingPokemonEvent event) {
        for (Trainer trainer : event.getTrainers()) {
            Mono
                    .just(trainer)
                    .map(Player::getID)
                    .map(Snowflake::of)
                    .flatMap(BanterBot4J.CLIENT::getUserById)
                    .flatMap(User::getPrivateChannel)
                    .flatMap(channel -> channel.createEmbed(sendOutPokemonEmbed(trainer)))
                    .subscribe();
        }
    }

}
