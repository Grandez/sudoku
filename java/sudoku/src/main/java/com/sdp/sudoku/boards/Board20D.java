package com.sdp.sudoku.boards;
/**
 * Board20D
 *
 * Clase que implementa un tablero en dos dimensiones
 */

import com.sdp.sudoku.config.CFG;

public class Board20D extends BoardBase implements Board {
    // Constructor con datos
    public Board20D(Integer[] data) {
        squares = new Square[CFG.CARD * CFG.CARD];
        initBoard(data);
    }
    // Contructor de copia
    public Board20D(Board20D board, boolean nextTree) {
        squares = new Square[CFG.CARD * CFG.CARD];
        copyObject(board, nextTree);
    }
    public Board copy(boolean nextTree) {
        return new Board20D(this, nextTree);
    }

    // ////////////////////////////////////////////////////////
    // Private code
    // ////////////////////////////////////////////////////////

     protected void markAsUsed (int pos, int value) {
         int[] axis = getDimensions(pos); // fila, columna
         int max;

         // Eje x
         int x = axis[0] * CFG.CARD;
         max = x + CFG.CARD;
         for (int i = x; i < max; i++) squares[i].setUsed(value);
         // Eje y
         max = (CFG.CARD * CFG.CARD) + axis[1];
         for (int i = axis[1]; i < max; i += CFG.CARD) squares[i].setUsed(value);
     }
    private int[] getDimensions(int pos) {
        int[] dims = {0,0};
        dims[0] = pos / CFG.CARD;
        dims[1] = pos % CFG.CARD;
        return dims;
    }

}
