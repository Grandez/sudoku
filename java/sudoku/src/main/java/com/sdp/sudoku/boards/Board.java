package com.sdp.sudoku.boards;

import java.util.List;

public interface Board {
    Square[]     getBoard();
    List<Square> getCandidates();
    Board        bet(int option);
    Board        copy(boolean nextTree);
    int          round(List<Square> options);
    int          getTree();
    int          getMaxTree();

}
