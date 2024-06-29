package com.sdp.sudoku.tablero;

public interface Board {
    public Board init();
    public Board bet(int option);
    public Square[] getCurrentBoard();
    public Square getCandidate();
    public void round(Square square);
    public Board copy();
}
