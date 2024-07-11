package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.core.BoardBase;
import com.sdp.sudoku.core.Square;

public class Board10D extends BoardBase implements Board {
    public Board10D(Integer[] data) {
        squares = new Square[CFG.CARD];
        initBoard(data);
    }
    // Contructor de copia
    public Board10D(Board10D board) {
        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        initCurrent();
    }
    public Board copy(boolean nextTree) {
        return new Board10D(this);
    }
    protected void markAsUsed (int pos, int value) {}
}
