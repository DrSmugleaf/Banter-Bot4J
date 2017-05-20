package com.github.drsmugbrain.dungeon;

import java.io.*;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Map;

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


    public DungeonMap(DungeonMap instance){
        this.tiles = instance.tiles;
        this.spawn_x = instance.spawn_x;
        this.spawn_y = instance.spawn_y;
    }


    public Tile getTile(int x, int y){
        return this.tiles[y][x];
    }


    public void positionPlayers(Map<Long, Player> playerHash){
        for(Player player : playerHash.values()){
            this.tiles[player.pos_y][player.pos_x].setPlayer();
        }
    }

    public String toString(Collection<Player> players){
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("```\n");

        for(int row = 0; row < this.tiles.length; row++){
            for(int column = 0; column < this.tiles[row].length; column++){
                Player playerToPrint = null;
                // We iterate over the players to check if any of them is standing on the current coords
                for(Player player : players){
                    if(player.pos_y == row && player.pos_x == column){
                        playerToPrint = player;
                        break;
                    }
                }
                // if any player is at current coord, we ignore the tile and print the player
                String output = playerToPrint == null ? this.tiles[row][column].toString() : playerToPrint.toString();
                outputBuilder.append(output);

            }
            outputBuilder.append("\n");
        }

        /*for(Tile[] row : this.tiles){
            for(Tile tile : row){
                boolean isPlayer = false;
                for(Player player : players){
                    if(player.pos_y == row && player.pos_x == tile){
                        isPlayer = true;
                    }
                }
                outputBuilder.append(tile.toString());
            }
            outputBuilder.append("\n");
        }*/
        outputBuilder.append("```");

        return outputBuilder.toString();
    }

}
