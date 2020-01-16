package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public class BoardHistory implements IBoardHistory {

    private int id;
    private final List<IBoardState> STATES;

    public BoardHistory(IBoard board) {
        STATES = new ArrayList<>();
        IBoardState state = new BoardState(0, null, board);
        STATES.add(state);
        id = STATES.size() - 1;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void addState(IBoard board) {
        IBoardState previous = getState();
        id++;
        IBoardState state = new BoardState(id, previous, board);
        STATES.add(id, state);
    }

    @Override
    public List<IBoardState> getStates() {
        return Collections.unmodifiableList(STATES);
    }

    @Override
    public IBoardState getState() {
        return STATES.get(getID());
    }

    @Nullable
    @Override
    public IBoardState getPrevious(IBoardState state) {
        int id = state.getID();
        if (id < 0) {
            return null;
        }

        return STATES.get(id);
    }

    @Nullable
    @Override
    public IBoardState getNext(IBoardState state) {
        int id = state.getID();
        if (id >= STATES.size()) {
            return null;
        }

        return STATES.get(id);
    }

}
