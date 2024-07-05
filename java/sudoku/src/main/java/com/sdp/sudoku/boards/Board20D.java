package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board20D extends BoardBase implements Board {
    public Board20D(Integer[] data) {
        squares = new Square[CFG.CARD * CFG.CARD];
        current = new Square[CFG.CARD * CFG.CARD];

        int size = CFG.CARD * CFG.CARD;
        for (int i = 0; i < size; i++) squares[i] = new Square(i);

        for (int i = 0; i < size; i++)  {
            if (data[i] != 0) setConstraint(i, data[i]);
        }

    }
    // Contructor de copia
    public Board20D(Board20D board) {
        squares = new Square[CFG.CARD * CFG.CARD];
        current = new Square[CFG.CARD * CFG.CARD];

        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        init();
    }
    public Board copy() {
        return new Board20D(this);
    }
    private Board20D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinal() == 0) last--;
        return this;
    }

    // ////////////////////////////////////////////////////////
    // Private code
    // ////////////////////////////////////////////////////////

    private int[] getDimensions(int pos) {
        int[] dims = {0,0};
        dims[0] = pos / CFG.CARD;
        dims[1] = pos % CFG.CARD;
        return dims;
     }
     protected void markAsUsed (int pos, int value) {
         int[] axis = getDimensions(pos); // fila, columna
         int max;

         // Eje x
         int x = axis[0] * CFG.CARD;
         max = x + CFG.CARD;
         for (int i = x; i < max; i++) squares[i].setUsed(value);
         // Eje y
         max = (CFG.CARD * (CFG.CARD - 1)) + axis[1];
         for (int i = axis[1]; i < max; i += CFG.CARD) squares[i].setUsed(value);
     }
}
