package com.sdp.sudoku.boards;

import java.util.List;

public interface Board {
    Square[]     getBoard();
    List<Square> getCandidates();
    int          round(Square square);
    Board        bet(int option);
    Board        copy();
}
