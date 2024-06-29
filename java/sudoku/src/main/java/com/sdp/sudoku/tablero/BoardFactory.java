package com.sdp.sudoku.tablero;

public class BoardFactory {
    public Board getBoard(Integer[] data) {
        return (Board) new Board1D(data);
    }
}
