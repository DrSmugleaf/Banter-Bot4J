package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.Move;
import com.github.drsmugleaf.tak.board.action.Place;
import com.github.drsmugleaf.tak.board.coordinates.IMovingCoordinates;
import com.github.drsmugleaf.tak.board.coordinates.MovingCoordinates;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.pieces.Type;
import com.google.common.collect.ImmutableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final ImmutableList<IAction> ALL_ACTIONS;

    Preset(int size, int capstones, int stones, String imageName) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size less than or equal to 0. Size: " + size);
        }

        SIZE = size;
        STONES = stones;
        CAPSTONES = capstones;
        IMAGE_NAME = imageName;

        List<IAction> allActions = new ArrayList<>();
        IBoard board = new Board(this);
        for (Row row : board.getRows()) {
            for (ISquare origin : row.getSquares()) {
                for (IType type : Type.getTypes()) {
                    Place place = new Place(origin, type);
                    allActions.add(place);
                }

                for (int pickedUp = 1; pickedUp <= getCarryLimit(); pickedUp++) {
                    for (IDirection direction : Directions.values()) {
                        ISquare thisSquare = origin;
                        List<ISquare> possibleSquares = new ArrayList<>();
                        possibleSquares.add(origin);
                        while ((thisSquare = direction.get(board, thisSquare)) != null) {
                            possibleSquares.add(thisSquare);
                        }

                        for (int n = 2; n <= possibleSquares.size(); n++) {
                            List<int[]> possible = generate(pickedUp, pickedUp, n);
                            for (int[] array : possible) {
                                List<IMovingCoordinates> coordinates = toMovingCoordinates(possibleSquares, array);
                                if (coordinates.isEmpty()) {
                                    continue;
                                }

                                if (coordinates.size() == 1) {
                                    System.out.println();
                                }
                                IMove move = new Move(coordinates, direction);
                                allActions.add(move);
                            }
                        }
                    }
                }
            }
        }

        ALL_ACTIONS = ImmutableList.copyOf(allActions);
    }

    private static List<IMovingCoordinates> toMovingCoordinates(List<ISquare> possibleSquares, int[] array) {
        List<IMovingCoordinates> coordinates = new ArrayList<>();
        for (int j = 0; j < array.length; j++) {
            int amount = array[j];
            if (amount == 0 && j != 0) {
                return new ArrayList<>();
            }

            ISquare square = possibleSquares.get(j);
            IMovingCoordinates coordinate = new MovingCoordinates(square.getRow(), square.getColumn(), amount);
            coordinates.add(coordinate);
        }

        return coordinates;
    }

    private static List<int[]> generate(int target, int max, int n) {
        return generate(new ArrayList<>(), new int[0], target, max, n);
    }

    private static List<int[]> generate(List<int[]> solutions, int[] current, int target, int max, int n) {
        int sum = Arrays.stream(current).sum();
        if (current.length == n) {
            if (sum == target) {
                solutions.add(current);
            }

            return solutions;
        }

        if (sum > target) {
            return solutions;
        }

        for(int i = 0; i <= max; i++) {
            int[] next = Arrays.copyOf(current, current.length + 1);
            next[current.length] = i;
            generate(solutions, next, target, max, n);
        }

        return solutions;
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
    public ImmutableList<IAction> getAllActions() {
        return ALL_ACTIONS;
    }

}
