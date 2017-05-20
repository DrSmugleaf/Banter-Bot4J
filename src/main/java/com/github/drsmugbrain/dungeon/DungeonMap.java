package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.dungeon.entities.IEntity;
import com.github.drsmugbrain.dungeon.entities.SpawnPoint;
import com.github.drsmugbrain.dungeon.helpers.Location;

import java.io.IOException;
import java.util.*;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonMap {
    private Tile[][] tiles;
    private Map<Long, List<IEntity>> entities;
    private List<SpawnPoint> spawnPoints;


    public DungeonMap() throws IOException, InputMismatchException{
        this.tiles = FileParser.fileToArray("src\\main\\java\\com\\github\\drsmugbrain\\dungeon\\example.map");
        this.entities = new HashMap<>();
        this.spawnPoints = new ArrayList<>();

        int row = 0;
        boolean spawn_found = false;

        while (row < this.tiles.length) {
            int column = 0;
            while (column < this.tiles[row].length){
                if(this.tiles[row][column].is_spawn()){
                    spawn_found = true;
                    this.spawnPoints.add(new SpawnPoint(column, row));
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


    public void addEntity(IEntity entity){
        long location = entity.getLocation();
        if(this.entities.get(entity.getLocation()) != null){
            this.entities.get(location).add(entity);
        }else{
            List<IEntity> list = new ArrayList<>();
            list.add(entity);
            this.entities.put(location, list);
        }
    }

    public SpawnPoint getRandomSpawnPoint(){
        int rnd = new Random().nextInt(this.spawnPoints.size());
        System.out.println(rnd);
        return this.spawnPoints.get(rnd);
    }

    public Tile getTile(int x, int y){
        return this.tiles[y][x];
    }


    public String toString(){
        StringBuilder outputBuilder = new StringBuilder();
        // Start code block
        outputBuilder.append("```\n");

        for(int row = 0; row < this.tiles.length; row++){
            for(int column = 0; column < this.tiles[row].length; column++){

                List<IEntity> entities = this.entities.get(Location.buildKey(column, row));

                String output = entities == null ? this.tiles[row][column].toString() : entities.get(0).getCharacter();
                outputBuilder.append(output);
            }
            outputBuilder.append("\n");
        }
        // End code block
        outputBuilder.append("```");

        return outputBuilder.toString();
    }

}
