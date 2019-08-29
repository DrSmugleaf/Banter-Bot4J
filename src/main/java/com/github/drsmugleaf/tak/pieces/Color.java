package com.github.drsmugleaf.tak.pieces;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Color {

    BLACK("black/") {
        @Override
        public Color getOpposite() {
            return WHITE;
        }

        @Override
        public double toDouble() {
            return 1;
        }

        @Override
        public String toString() {
            return "B";
        }
    },
    WHITE("white/") {
        @Override
        public Color getOpposite() {
            return BLACK;
        }

        @Override
        public double toDouble() {
            return -1;
        }

        @Override
        public String toString() {
            return "W";
        }
    };

    private final String FOLDER_NAME;

    Color(String folderName) {
        FOLDER_NAME = folderName;
    }

    public String getFolderName() {
        return FOLDER_NAME;
    }

    public abstract Color getOpposite();

    public abstract double toDouble();

}
