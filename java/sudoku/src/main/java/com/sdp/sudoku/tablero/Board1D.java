package com.sdp.sudoku.tablero;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board1D implements Board {
    Square[] squares = new Square[CFG.CARD];
    Square[] current = new Square[CFG.CARD];
    Square playing;
    int last;

    public Board1D(Integer[] data) {
        int i;
        for (i = 0; i < squares.length; i++) squares[i] = new Square(i);
        for (i = 0; i < data.length; i++) {
            if (data[i] != 0) setConstraint(i, data[i]);
        }
    }
    public void round (Square square) {
        if (playing != null) {
            playing.setPlayed();
        }
        this.playing = square;
         // La casilla ya tiene el valor
         // Quitarlo de donde proceda
        for (int i = 0; i < squares.length; i++) {
            squares[i].setUsed(square.getValue());
        }
        recalculateBoard();
    }
    public Square[] getCurrentBoard() {
        return current;
    }
    public int getCurrentSquare() {
        return playing.getPos();
    }
    public Square getCandidate() {
        return (last == -1) ? new Square() : current[last];
    }
    public Board1D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        calculateOptions();
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinality() == 0) last--;
        return this;
    }

    private void setConstraint(int pos, int value) {
        // Aqui la funcion es la identidad
        int idx = pos;
        for (int i = 0; i < squares.length; i++) {
            if (i == idx) squares[i].setConstraint(value);
            squares[i].setUsed(value);
        }
    }
    private void recalculateBoard () {
        Square tmp;
        for (int i = 0; i < last; i++) {
            if (current[i].cardinality() <= current[i+1].cardinality()) {
                tmp = current[i];
                current[i] = current[i+1];
                current[i+1] = tmp;
            }
        }
        while (last >= 0 && current[last].cardinality() == 0) last--;
    }
    private void calculateOptions() {
    }
}
