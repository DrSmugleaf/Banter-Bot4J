package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;

import java.util.List;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public interface IBoardHistory {

    int getID();
    void addState(IBoard state);
    List<IBoardState> getStates();
    IBoardState getState();
    @Nullable
    IBoardState getPrevious(IBoardState state);
    @Nullable
    IBoardState getNext(IBoardState state);
    void reset(IBoard board);

}
