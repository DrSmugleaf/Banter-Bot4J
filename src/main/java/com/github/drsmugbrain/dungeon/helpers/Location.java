package com.github.drsmugbrain.dungeon.helpers;

/**
 * Created by Brian on 20/05/2017.
 */
public class Location {
    public static long buildKey(int x, int y){
        return ((long)x << 32 | (long)y);
    }
}
