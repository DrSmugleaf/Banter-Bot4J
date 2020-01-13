package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import org.deeplearning4j.rl4j.space.Encodable;

import java.util.Arrays;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class NeuralBoard extends Board implements INeuralBoard, Encodable {

    public NeuralBoard(IPreset preset) {
        super(preset);
    }

    public NeuralBoard(ISquare[][] squares) {
        super(squares);
    }

    @Override
    public double[] toArray() {
        return Arrays.stream(toDoubleArray()).flatMap(Arrays::stream).flatMapToDouble(Arrays::stream).toArray();
    }

}
