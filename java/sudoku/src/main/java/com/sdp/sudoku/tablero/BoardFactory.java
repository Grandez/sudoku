package com.sdp.sudoku.tablero;

public class BoardFactory {
    public Board getBoard(Integer[] data) {
        return new Board1D(data);
    }
}
