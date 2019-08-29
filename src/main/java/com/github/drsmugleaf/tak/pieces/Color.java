package com.github.drsmugleaf.tak.pieces;

import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Color {

    BLACK("black/", 1, "B") {
        @Override
        public Color getOpposite() {
            return WHITE;
        }
    },
    WHITE("white/", -1, "W") {
        @Override
        public Color getOpposite() {
            return BLACK;
        }
    };

    private static final ImmutableSet<Color> COLORS = ImmutableSet.copyOf(values());
    private final String FOLDER_NAME;
    private final double VALUE;
    private final String STRING;

    Color(String folderName, double value, String string) {
        FOLDER_NAME = folderName;
        VALUE = value;
        STRING = string;
    }

    public static ImmutableSet<Color> getColors() {
        return COLORS;
    }

    public String getFolderName() {
        return FOLDER_NAME;
    }

    public double toDouble() {
        return VALUE;
    }

    @Override
    public String toString() {
        return STRING;
    }

    public abstract Color getOpposite();

}
