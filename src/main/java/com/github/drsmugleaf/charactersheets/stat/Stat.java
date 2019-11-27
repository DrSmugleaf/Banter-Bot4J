package com.github.drsmugleaf.charactersheets.stat;

import com.github.drsmugleaf.charactersheets.Nameable;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Stat implements Nameable {

    private final String NAME;
    private long value;

    public Stat(String name, long value) {
        NAME = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getValue() {
        return value;
    }

    public Stat changeValue(long amount) {
        value += amount;
        return this;
    }

}
