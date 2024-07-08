package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board10D extends BoardBase implements Board {
    public Board10D(Integer[] data) {
        squares = new Square[CFG.CARD];

        for (int i = 0; i < squares.length; i++) squares[i] = new Square(i);
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) setConstraint(i, data[i]);
        }
    }
    // Contructor de copia
    public Board10D(Board10D board, boolean nextTree) {
        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        initCurrent();
    }
    public Board copy(boolean nextTree) {
        return new Board10D(this, nextTree);
    }
    protected void markAsUsed (int pos, int value) {

    }
}
