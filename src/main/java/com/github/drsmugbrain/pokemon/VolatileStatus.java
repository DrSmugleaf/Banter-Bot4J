package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum VolatileStatus {

    BOUND("Bound"),
    CANT_ESCAPE("Can't escape"),
    CONFUSION("Confusion"),
    CURSE("Curse"),
    EMBARGO("Embargo"),
    ENCORE("Encore"),
    FLINCH("Flinch"),
    HEAL_BLOCK("Heal Block"),
    IDENTIFIED("Identified"),
    INFATUATION("Infatuation"),
    LEECH_SEED("Leech Seed"),
    NIGHTMARE("Nightmare"),
    PERISH_SONG("Perish Song"),
    SPOOKED("Spooked"),
    TAUNT("Taunt"),
    TELEKINESIS("Telekinesis"),
    TORMENT("Torment"),

    AQUA_RING("Aqua Ring"),
    BRACING("Bracing"),
    CENTER_OF_ATTENTION("Center of attention"),
    DEFENSE_CURL("Defense Curl"),
    GLOWING("Glowing"),
    ROOTING("Rooting"),
    MAGIC_COAT("Magic Coat"),
    MAGNETIC_LEVITATION("Magnetic Levitation"),
    MINIMIZE("Minimize"),
    PROTECTION("Protection"),
    RECHARGING("Recharging"),
    SEMI_INVULNERABLE("Semi-invulnerable"),
    SUBSTITUTE("Substitute"),
    TAKING_AIM("Taking aim"),
    TAKING_IN_SUNLIGHT("Taking in sunlight"),
    WITHDRAWING("Withdrawing"),
    WHIPPING_UP_A_WHIRLWIND("Whipping up a whirlwind");

    private String NAME;

    VolatileStatus(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
