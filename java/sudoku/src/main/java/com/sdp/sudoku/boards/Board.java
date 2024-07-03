package com.sdp.sudoku.boards;

public interface Board {
    Board    init();
    Board    bet(int option);
    Square[] getCurrentBoard();
    Square   getCandidate();
    void     round(Square square);
    Board    copy();
}
