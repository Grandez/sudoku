package com.sdp.sudoku.tablero;

public interface Board {
    Board init();
    Board bet(int option);
    Square[] getCurrentBoard();
    Square getCandidate();
    void round(Square square);
     Board copy();
}
