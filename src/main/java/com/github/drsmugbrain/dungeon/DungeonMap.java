package com.github.drsmugbrain.dungeon;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonMap {
    private Tile[][] tiles;
    public int spawn_x;
    public int spawn_y;

    public DungeonMap() throws IOException, InputMismatchException{
        this.tiles = FileParser.fileToArray("src\\main\\java\\com\\github\\drsmugbrain\\dungeon\\example.map");
        int row = 0;
        boolean spawn_found = false;
        while (row < this.tiles.length) {
            int column = 0;
            while (column < this.tiles[row].length){
                if(this.tiles[row][column].is_spawn()){
                    if(spawn_found){
                        // mal rollo
                        this.tiles[row][column].setEmpty();
                        continue;
                    }
                    spawn_found = true;
                    this.spawn_x = column;
                    this.spawn_y = row;
                    this.tiles[row][column].setEmpty();
                }
                column++;
            }
            row ++;
        }
        if(!spawn_found){
            throw new InputMismatchException("Map has no spawn");
        }
    }

    public Tile getTile(int x, int y){
        return this.tiles[y][x];
    }

    @Override
    public String toString(){
        StringBuilder outputBuilder = new StringBuilder();
//        return "Probando cosas";
        outputBuilder.append("```\n");
        for(Tile[] row : this.tiles){
            for(Tile tile : row){
                outputBuilder.append(tile.toString());
            }
            outputBuilder.append("\n");
        }
        outputBuilder.append("```");

        return outputBuilder.toString();
    }
}
