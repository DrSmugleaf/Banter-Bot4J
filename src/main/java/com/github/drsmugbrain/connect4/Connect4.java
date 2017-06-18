package com.github.drsmugbrain.connect4;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

import java.util.Arrays;

/**
 * Created by Brian on 18/06/2017.
 */
public class Connect4 {
    Connect4Tile[][] tiles;
    IMessage message;
    IUser player1;
    IUser player2;
    int turn;

    public Connect4(IUser user1, IUser user2){
        this.tiles = new Connect4Tile[7][6];
        for (Connect4Tile[] tile : this.tiles) {
            Arrays.fill(tile, Connect4Tile.player0);
        }
        this.player1 = user1;
        this.player2 = user2;

        turn = Math.random() > 0.5 ? 1 : 2;

    }

    public boolean move(IUser player, int column){
        // columna no existe
        if(column >= tiles.length){
            throw new IllegalArgumentException("column "+column+"does not exist");
        }
        // columna llena
        if(this.tiles[column][this.tiles[column].length-1] != Connect4Tile.player0){
            throw new IllegalArgumentException("column "+column+"is full");
        }
        // user is not a player
        if(!this.player1.equals(player) && !this.player2.equals(player)){
            throw new IllegalArgumentException("User is not a player");
        }

        int playerNum = this.player1.equals(player) ? 1 : 2;
        for(int row = 0; row < this.tiles[column].length; row++){
            if(this.tiles[column][row] == Connect4Tile.player0){
                this.tiles[column][row] = playerNum == 1 ? Connect4Tile.player1 : Connect4Tile.player2;
                return this.checkVictory(column, row);
            }
        }
        // execution should never reach this:
        System.err.println("Reached unreachable code, something's fishy here...");
        return false;
    }

    private boolean checkVictory(int column, int row){
        if(this.checkHorizontalVictory(column, row)){
            return true;
        }
        if(this.checkVerticalVictory(column, row)){
            return true;
        }
        if(this.checkDiagonalAVictory(column, row)){
            return true;
        }
        if(this.checkDiagonalBVictory(column, row)){
            return true;
        }
        return false;
    }

    private boolean checkHorizontalVictory(int column, int row){
        Connect4Tile victoryTile = this.tiles[column][row];
        // we have 1 point for the placed tile
        int consecutiveTiles = 1;

        //check the left
        int currentColumn = column - 1;
        while(currentColumn >= 0 && this.tiles[currentColumn][row] == victoryTile){
            consecutiveTiles++;
            currentColumn--;
        }

        //check the right
        currentColumn = column + 1;
        while(currentColumn < this.tiles.length && this.tiles[currentColumn][row] == victoryTile){
            consecutiveTiles++;
            currentColumn++;
        }

        return consecutiveTiles >= 4;
    }

    private boolean checkVerticalVictory(int column, int row){
        Connect4Tile victoryTile = this.tiles[column][row];
        // we have 1 point for the placed tile
        int consecutiveTiles = 1;

        //check downwards
        int currentRow = row - 1;
        while(currentRow >= 0 && this.tiles[column][currentRow] == victoryTile){
            consecutiveTiles++;
            currentRow--;
        }

        //check upwards
        currentRow = column + 1;
        while(currentRow < this.tiles[column].length && this.tiles[column][currentRow] == victoryTile){
            consecutiveTiles++;
            currentRow++;
        }

        return consecutiveTiles >= 4;
    }

    private boolean checkDiagonalAVictory(int column, int row){
        //from bottom left to top right

        Connect4Tile victoryTile = this.tiles[column][row];
        // we have 1 point for the placed tile
        int consecutiveTiles = 1;

        int currentColumn = column + 1;
        int currentRow = row + 1;
        // check upwards to the right
        while(currentColumn < this.tiles.length && currentRow < this.tiles[currentColumn].length && this.tiles[currentColumn][currentRow] == victoryTile){
            consecutiveTiles++;
            currentColumn++;
            currentRow++;
        }

        // check downwards to the left
        currentColumn = column - 1;
        currentRow = row - 1;
        // check upwards to the right
        while(currentColumn >= 0 && currentRow >= 0 && this.tiles[currentColumn][currentRow] == victoryTile){
            consecutiveTiles++;
            currentColumn--;
            currentRow--;
        }
        return consecutiveTiles >= 4;
    }

    private boolean checkDiagonalBVictory(int column, int row){
        //from top left to bottom right

        Connect4Tile victoryTile = this.tiles[column][row];
        // we have 1 point for the placed tile
        int consecutiveTiles = 1;

        int currentColumn = column + 1;
        int currentRow = row - 1;
        // check downwards to the right
        while(currentColumn < this.tiles.length && currentRow >= 0 && this.tiles[currentColumn][currentRow] == victoryTile){
            consecutiveTiles++;
            currentColumn++;
            currentRow--;
        }

        // check upwards to the left
        currentColumn = column - 1;
        currentRow = row + 1;
        // check upwards to the right
        while(currentColumn >= 0 && currentRow < this.tiles[currentColumn].length && this.tiles[currentColumn][currentRow] == victoryTile){
            consecutiveTiles++;
            currentColumn--;
            currentRow--;
        }
        return consecutiveTiles >= 4;
    }

    public String toEmotes(){
        StringBuilder outputBuilder = new StringBuilder();


        int row = this.tiles[0].length-1;
        while(row >= 0){
            int column = 0;
            while(column < this.tiles.length){
                switch(this.tiles[column][row]){
                    case player1:
                        outputBuilder.append(":white_circle:");
                        break;
                    case player2:
                        outputBuilder.append(":large_blue_circle:");
                        break;
                    default:
                        outputBuilder.append(":o:");
                        break;
                }
                column++;
            }
            outputBuilder.append("\n");
            row--;
        }

        outputBuilder.append(":one::two::three::four::five::six::seven:");

        return outputBuilder.toString();
    }

    public void setMessage(IMessage message){
        this.message = message;
        RequestBuffer.request(() -> message.addReaction(":one:")).get();
        RequestBuffer.request(() -> message.addReaction(":two:")).get();
        RequestBuffer.request(() -> message.addReaction(":three:")).get();
        RequestBuffer.request(() -> message.addReaction(":four:")).get();
        RequestBuffer.request(() -> message.addReaction(":five:")).get();
        RequestBuffer.request(() -> message.addReaction(":six:")).get();
        RequestBuffer.request(() -> message.addReaction(":seven:")).get();
    }

}
