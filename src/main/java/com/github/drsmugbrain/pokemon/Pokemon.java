package com.github.drsmugbrain.pokemon;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon implements Comparable<Pokemon> {

    private static final Set<Pokemon> BASE_POKEMON = new TreeSet<>();

    private final String NAME;
    private final String NICKNAME;

    private final Type[] TYPES;

    private final int BASE_HP;
    private final int BASE_ATTACK;
    private final int BASE_DEFENSE;
    private final int BASE_SPEED;
    private final int BASE_SPECIAL_ATTACK;
    private final int BASE_SPECIAL_DEFENSE;
    private final int BASE_ACCURACY;
    private final int BASE_EVASION;

    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private int specialAttack;
    private int specialDefense;
    private int accuracy;
    private int evasion;

    public Pokemon(@Nonnull String name, Type[] types, int hp, int attack, int defense, int speed, int specialAttack, int specialDefense, int accuracy, int evasion) {
        this.NAME = name;
        this.NICKNAME = name;

        this.TYPES = types;

        this.BASE_HP = hp;
        this.BASE_ATTACK = attack;
        this.BASE_DEFENSE = defense;
        this.BASE_SPEED = speed;
        this.BASE_SPECIAL_ATTACK = specialAttack;
        this.BASE_SPECIAL_DEFENSE = specialDefense;
        this.BASE_ACCURACY = accuracy;
        this.BASE_EVASION = evasion;

        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.accuracy = accuracy;
        this.evasion = evasion;
    }

    @Override
    public int compareTo(@NotNull Pokemon pokemon) {
        return this.NAME.compareTo(pokemon.NAME);
    }

    static void createBasePokemon(@Nonnull String name, @Nonnull Type[] types, int hp, int attack, int defense, int speed, int specialAttack, int specialDefense, int accuracy, int evasion) {
        Pokemon.BASE_POKEMON.add(new Pokemon(name, types, hp, attack, defense, speed, specialAttack, specialDefense, accuracy, evasion));
    }

    @Nonnull
    public static Set<Pokemon> getBasePokemon() {
        return Pokemon.BASE_POKEMON;
    }

    public Type[] getTypes() {
        return this.TYPES;
    }

}
