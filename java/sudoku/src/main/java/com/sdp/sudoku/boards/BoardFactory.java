package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

public class BoardFactory {
    public Board getBoard(Integer[] data) {
        if (data.length == CFG.CARD)            return new Board10D(data);
        if (data.length == CFG.CARD * CFG.CARD) return new Board25D(data);
        return new Board25D(data);
    }
}
