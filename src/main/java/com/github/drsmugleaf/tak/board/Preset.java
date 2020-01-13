package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.pieces.Type;
import com.google.common.collect.ImmutableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Preset implements IPreset {

    FOUR(4, 0, 15, "4x4.jpg"),
    FIVE(5, 1, 21, "5x5.jpg"),
    SIX(6, 1, 30, "6x6.jpg"),
    EIGHT(8, 2, 50, "6x6 hybrid.jpg");

    private static final String IMAGES_PATH = Objects.requireNonNull(Preset.class.getClassLoader().getResource("tak/boards")).getFile();
    private final int SIZE;
    private final int CAPSTONES;
    private final int STONES;
    private final String IMAGE_NAME;
    private final ImmutableList<ICoordinates> ALL_ACTIONS;

    Preset(int size, int capstones, int stones, String imageName) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size less than or equal to 0. Size: " + size);
        }

        SIZE = size;
        STONES = stones;
        CAPSTONES = capstones;
        IMAGE_NAME = imageName;

        List<ICoordinates> allActions = new ArrayList<>();
        IBoard board = new Board(this);
        for (Row row : board.getRows()) {
            for (ISquare origin : row.getSquares()) {
                for (IType type : Type.getTypes()) {
                    Coordinates place = new Coordinates(origin, type);
                    allActions.add(place);
                }

                IAdjacentSquares adjacent = board.getAdjacent(origin);
                for (ISquare destination : adjacent.getAll()) {
                    if (destination == null) {
                        continue;
                    }

                    for (int pieces = 1; pieces <= getCarryLimit(); pieces++) {
                        MovingCoordinates move = new MovingCoordinates(origin, destination, pieces);
                        allActions.add(move);
                    }
                }
            }
        }

        ALL_ACTIONS = ImmutableList.copyOf(allActions);
    }

    public static IPreset getDefault() {
        return FIVE;
    }

    @Nullable
    public static IPreset getPreset(int size) {
        for (IPreset preset : values()) {
            if (preset.getSize() == size) {
                return preset;
            }
        }

        return null;
    }

    @Override
    public InputStream getImage() {
        String fileName = "/" + IMAGE_NAME;
        try {
            return new FileInputStream(IMAGES_PATH + fileName);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("Image for tak board " + this + " with name " + IMAGE_NAME + " not found", e);
            throw new IllegalStateException("Image for tak board " + this + " with name " + IMAGE_NAME + " not found", e);
        }
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public int getCapstones() {
        return CAPSTONES;
    }

    @Override
    public int getStones() {
        return STONES;
    }

    @Override
    public int getCarryLimit() {
        return getSize();
    }

    @Override
    public int getMaximumStackSize() {
        return (getCapstones() > 0 ? 1 : 0) + getStones() * 2;
    }

    @Override
    public ImmutableList<ICoordinates> getAllActions() {
        return ALL_ACTIONS;
    }

}
