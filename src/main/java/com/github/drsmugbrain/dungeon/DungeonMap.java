package com.github.drsmugbrain.dungeon;

import com.github.drsmugbrain.dungeon.entities.IEntity;
import com.github.drsmugbrain.dungeon.entities.SpawnPoint;
import com.github.drsmugbrain.dungeon.entities.Treasure;
import org.apache.commons.lang3.math.IEEE754rUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Brian on 16/05/2017.
 */
public class DungeonMap {
    private Tile[][] tiles;
    private List<IEntity> entities;
    private List<SpawnPoint> spawnPoints;


    public DungeonMap() throws IOException, InputMismatchException{
        this.tiles = FileParser.fileToArray("src\\main\\java\\com\\github\\drsmugbrain\\dungeon\\example2.map");
        this.entities = new ArrayList<>();
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


    public boolean canMoveTo(int x, int y){
        boolean canMoveToTile = this.tiles[y][x].canMoveTo();
        boolean canMoveToEntity = true;
        List<IEntity> entities = this.getEntitiesFromCoords(x, y);
        for(IEntity entity : entities){
            if(entity.isSolid()){
                canMoveToEntity = false;
                break;
            }
        }
        return canMoveToEntity && canMoveToTile;
    }


    public void addEntity(IEntity entity){
        if(!this.entities.contains(entity)){
            this.entities.add(entity);
        }
    }

    public void removeEntity(IEntity entity){
        if(this.entities.contains(entity)){
            this.entities.remove(entity);
        }
    }

    public SpawnPoint getRandomSpawnPoint(){
        int rnd = new Random().nextInt(this.spawnPoints.size());
        return this.spawnPoints.get(rnd);
    }

    public void createRandomTreasure(){
        boolean treasureCreated = false;
        Random rnd = new Random();
        while(!treasureCreated){
            int y = rnd.nextInt(this.tiles.length);
            int x = rnd.nextInt(this.tiles[0].length);
            if(this.tiles[y][x].is_empty()){
                this.addEntity(new Treasure(x,y, this));
                treasureCreated = true;
            }
        }
    }

    public Tile getTile(int x, int y){
        return this.tiles[y][x];
    }

    public List<IEntity> getEntitiesFromCoords(int x, int y){
        return this.entities.stream().filter(e -> e.getX() == x && e.getY() == y).collect(Collectors.toList());
    }

    public String toString(){
        StringBuilder outputBuilder = new StringBuilder();
        // Start code block
        outputBuilder.append("```\n");

        for(int row = 0; row < this.tiles.length; row++){
            for(int column = 0; column < this.tiles[row].length; column++){

                List<IEntity> entities = this.getEntitiesFromCoords(column, row);

                String output = entities.size() > 0 ? entities.get(0).getCharacter() : this.tiles[row][column].toString();
                outputBuilder.append(output);
            }
            outputBuilder.append("\n");
        }
        // End code block
        outputBuilder.append("```");

        return outputBuilder.toString();
    }

}
