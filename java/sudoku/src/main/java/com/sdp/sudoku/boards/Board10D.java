package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board10D extends BoardBase implements Board {
    public Board10D(Integer[] data) {
        squares = new Square[CFG.CARD];
        current = new Square[CFG.CARD];

        for (int i = 0; i < squares.length; i++) squares[i] = new Square(i);
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) setConstraint(i, data[i]);
        }
    }
    // Contructor de copia
    public Board10D(Board10D board) {
        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        init();
    }
    public Board copy() {
        return new Board10D(this);
    }


    /*
    public void round (Square square) {
        if (playing != null) playing.setPlayed();
        this.playing = square;
        for (Square sq : squares) sq.setUsed(square.getValue());
        recalculate();
    }

     */
    public Board recalculate () {
        Square tmp;
        for (int i = 0; i < last; i++) {
            if (current[i].cardinality() <= current[i+1].cardinality()) {
                tmp = current[i];
                current[i] = current[i+1];
                current[i+1] = tmp;
            }
        }
        while (last >= 0 && current[last].cardinality() == 0) last--;
        return this;
    }

    public Board bet (int idx) {
        Square curr = current[last];
        curr.setBet(curr.getOptions()[idx]);
        return this;
    }
    public Board10D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinality() == 0) last--;
        return this;
    }
    protected void markAsUsed (int pos, int value) {

    }
    private void setSquares (Square[] squares) {
        this.squares = squares;
    }
    private void setCurrent (Square[] squares) {
        this.current = squares;
    }
}
