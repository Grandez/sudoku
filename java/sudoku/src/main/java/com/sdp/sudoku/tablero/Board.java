package com.sdp.sudoku.tablero;

import java.util.List;
import java.util.Set;

public interface Board {
    public Board init();
    public Square[] getCurrentBoard();
    public Square getCandidate();
    public void round(Square square);
}
