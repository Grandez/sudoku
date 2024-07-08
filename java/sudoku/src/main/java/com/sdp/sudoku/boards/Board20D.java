package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board20D extends BoardBase implements Board {
    // Constructor con datos
    public Board20D(Integer[] data) {
        squares = new Square[CFG.CARD * CFG.CARD];
        initBoard(data);
    }
    // Contructor de copia
    public Board20D(Board20D board, boolean nextTree) {
        squares = new Square[CFG.CARD * CFG.CARD];

        this.tree = board.getTree();
        if (nextTree)  {
            this.tree++;
            if (this.maxTree < this.tree) this.maxTree = this.tree;
        }
        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        initCurrent();
    }
    public Board copy(boolean nextTree) {
        return new Board20D(this, nextTree);
    }
    /*
    private Board20D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinal() == 0) last--;
        return this;
    }
*/
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
