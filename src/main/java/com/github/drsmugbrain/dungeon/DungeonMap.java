package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.BotUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonMap {
    private Tile[][] tiles;
    private static final int LINES = 8;
    private static final int COLUMNS = 8;


    public DungeonMap(int width, int height){
        this.tiles = new Tile[width][height];
    }



    public DungeonMap() throws IOException{
        this.tiles = FileParser.fileToArray("src\\main\\java\\com\\github\\drsmugbrain\\dungeon\\example.map");



//        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\com\\github\\drsmugbrain\\dungeon\\example.map"));
//        List<Tile[]> lines = new ArrayList<>();
//        String sLine;
//
//        while ((sLine = br.readLine()) != null) {
//            // añadimos un array de objetos Tile a la lista
//            lines.add(Tile.stringToTileRow(sLine.replaceAll("\n", "")));
//        }
//
//        this.tiles = new Tile[lines.size()][lines.get(0).length];
//
//        int counter = 0;
//        for (Tile[] line : lines ){
//            this.tiles[counter] = line;
//        }
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
