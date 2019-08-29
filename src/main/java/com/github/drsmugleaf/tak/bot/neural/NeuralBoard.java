package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import org.deeplearning4j.rl4j.space.Encodable;

import java.util.Arrays;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class NeuralBoard extends Board implements Encodable {

    public NeuralBoard(Preset preset) {
        super(preset);
    }

    public NeuralBoard(Square[][] squares) {
        super(squares);
    }

    @Override
    public double[] toArray() {
        return Arrays.stream(toDoubleArray()).flatMap(Arrays::stream).flatMapToDouble(Arrays::stream).toArray();
    }

}
