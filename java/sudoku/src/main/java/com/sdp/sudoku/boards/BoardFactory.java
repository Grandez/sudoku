/**
 * Factoria para devolver instancias concretas del tablero apropiado
 * Cualquier tablero es una instancia de @see com.sdp.sudoku.boards.Board
 *
 */
package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.config.GameDefinition;

public class BoardFactory {
    public Board getBoard(GameDefinition game) {
        Integer[] data = game.getData();
        if (data.length == CFG.CARD)            return new Board10D(data);
        if (data.length == CFG.CARD * CFG.CARD) return new Board25D(data);
        return new Board25D(data);
    }
}
