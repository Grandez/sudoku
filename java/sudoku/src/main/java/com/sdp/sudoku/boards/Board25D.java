package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.core.BoardBase;
import com.sdp.sudoku.core.Square;

import java.util.ArrayList;
import java.util.List;

public class Board25D extends Board20D implements Board {
    public Board25D(Integer[] data) {
        super(data);
//        squares = new Square[CFG.CARD * CFG.CARD];
//        initBoard(data);
    }
    // Contructor de copia
    public Board25D(Board25D board, boolean nextTree) {
        super(board);
//        squares = new Square[CFG.CARD * CFG.CARD];
//
//        this.tree = board.getTree();
//        if (nextTree)  {
//            this.tree++;
//            if (this.maxTree < this.tree) this.maxTree = this.tree;
//        }
//        Square[] old = board.getBoard();
//        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
//        initCurrent();
    }

    public Board copy(boolean nextTree) {
        return new Board25D(this, nextTree);
    }

    @Override
    protected void markAsUsed (int pos, int value) {
        super.markAsUsed(pos, value);
        int[] axis = getDimensions(pos); // fila, columna
//        int max;
//
//        // Eje x
//        int x = axis[0] * CFG.CARD;
//        max = x + CFG.CARD;
//        for (int i = x; i < max; i++) squares[i].setUsed(value);
//
//        // Eje y
//        max = (CFG.CARD * CFG.CARD) + axis[1];
//        for (int i = axis[1]; i < max; i += CFG.CARD) squares[i].setUsed(value);

        // Cuadrados internos
        for (Integer i : getSquaresOfSquare(axis)) squares[i].setUsed(value);
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

    private List<Integer> getSquaresOfSquare(int[] axis) {
        List<Integer> squares = new ArrayList<Integer>();
        // Los cuadrados son la raiz de la cardinalidad: 9 -> 3, 16 -> 4, 25 -> 5
        int block = (int) Math.sqrt(CFG.CARD);

        // Ponemos la fila inicio
        int offset = axis[0] % block;
        axis[0] -= offset;

        // Ponemos la columna inicio
        offset = axis[1] % block;
        axis[1] -= offset;
        int xMax = axis[0] + block;
        int yMax = axis[1] + block;
        for (int i = axis[0]; i < xMax; i++) {
            for (int j = axis[1]; j < yMax; j++) {
                int idx = (i * CFG.CARD) + j;
                squares.add(idx);
            }
        }
        return squares;
    }
}
