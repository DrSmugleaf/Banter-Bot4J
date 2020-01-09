package com.github.drsmugleaf.tak.pieces;

import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Color implements IColor {

    BLACK("black/", 1, "B") {
        @Override
        public IColor getOpposite() {
            return WHITE;
        }
    },
    WHITE("white/", -1, "W") {
        @Override
        public IColor getOpposite() {
            return BLACK;
        }
    };

    private static final ImmutableSet<IColor> COLORS = ImmutableSet.copyOf(values());
    private final String FOLDER_NAME;
    private final double VALUE;
    private final String STRING;

    Color(String folderName, double value, String string) {
        FOLDER_NAME = folderName;
        VALUE = value;
        STRING = string;
    }

    public static ImmutableSet<IColor> getColors() {
        return COLORS;
    }

    @Override
    public String getFolderName() {
        return FOLDER_NAME;
    }

    @Override
    public double toDouble() {
        return VALUE;
    }

    @Override
    public String toString() {
        return STRING;
    }

}
