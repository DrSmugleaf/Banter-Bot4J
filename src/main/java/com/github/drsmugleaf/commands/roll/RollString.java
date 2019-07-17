package com.github.drsmugleaf.commands.roll;

/**
 * Created by DrSmugleaf on 17/07/2019
 */
public class RollString {

    private final long amount;
    private final long sides;

    public RollString(String roll) {
        String[] dice = roll.split("d");
        amount = Long.parseLong(dice[0]);
        sides = Long.parseLong(dice[1]);
    }

    public long getAmount() {
        return amount;
    }

    public long getSides() {
        return sides;
    }

}
