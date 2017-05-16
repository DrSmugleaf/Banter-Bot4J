package com.github.drsmugbrain.dungeon;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 16/05/2017.
 */
public class FileParser {

    protected static Tile[][] fileToArray(String filePath) throws IOException {
        int rows = 0;
        int columns = 0;
        Tile[][] result;
        List<Tile[]> rowList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String sLine;

        while ((sLine = br.readLine()) != null) {
            int counter = 0;
            Tile[] tiles = new Tile[sLine.length()];

            for(char character : sLine.toCharArray()){
                tiles[counter] = new Tile(character);
                counter++;
            }

            if(columns != 0 && columns != sLine.length()){
                throw new ValueException("Invalid map file, all rows must be the same length!");
            }
            columns = sLine.length();
            rowList.add(tiles);
            rows++;
        }
        result = new Tile[rows][columns];
        int counter = 0;
        for(Tile[] tileList : rowList){
            result[counter] = tileList;
            counter++;
        }
        return result;
    }

}
