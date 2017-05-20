package com.github.drsmugbrain.dungeon;

import java.util.Map;

/**
 * Created by Brian on 16/05/2017.
 */
public class Tile {
    private static final String ALLOWED_CHARS = "-# X";
    private static final String ALLOWED_MOVES = " ";

    private char type;

    public Tile(char type){
        this.type = type;
    }

    private static boolean tileTypeIsValid(char type){
        return ALLOWED_CHARS.indexOf(type) != -1;
    }

    public static Tile[] stringToTileRow(String input){
        return Tile.charRowToTileRow(input.toCharArray());
    }

    public boolean is_spawn(){
        return this.type == 'X';
    }

    public boolean is_empty(){
        return this.type == ' ';
    }

    public boolean canMoveTo(){
        return ALLOWED_MOVES.indexOf(this.type) != -1;
    }

    public void setEmpty(){
        this.type = ' ';
    }

    public void setPlayer(){
        this.type = '@';
    }

    public static Tile[] charRowToTileRow(char[] charRow){
        Tile[] result = new Tile[charRow.length];
        for (int i = 0; i<charRow.length; i++){
            Tile tile = new Tile(charRow[i]);
            result[i] = tile;
        }

        return result;
    }

    @Override
    public String toString(){
        return String.valueOf(this.type);
    }
}
