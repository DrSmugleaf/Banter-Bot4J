package com.github.drsmugleaf.tak.pieces;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Color {

    BLACK("black/") {
        @NotNull
        @Override
        public Color getOpposite() {
            return WHITE;
        }
    },
    WHITE("white/") {
        @NotNull
        @Override
        public Color getOpposite() {
            return BLACK;
        }
    };

    @NotNull
    private final String FOLDER_NAME;

    Color(@NotNull String folderName) {
        FOLDER_NAME = folderName;
    }

    @NotNull
    protected String getFolderName() {
        return FOLDER_NAME;
    }

    @NotNull
    public abstract Color getOpposite();

}
