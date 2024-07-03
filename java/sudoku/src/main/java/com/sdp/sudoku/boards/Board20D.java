package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board20D implements Board, Cloneable {
    Square[] squares = new Square[CFG.CARD * CFG.CARD];
    Square[] current = new Square[CFG.CARD * CFG.CARD];
    Square playing;
    int last;

    public Board20D(Integer[] data) {
        int size = CFG.CARD * CFG.CARD;
        for (int i = 0; i < size; i++) squares[i] = new Square(i);
        // Caso especial, para distinguir version 2 de 2.5 la 2 tiene mas elementos que se ignoran
        for (int i = 0; i < size; i++)         if (data[i] != 0) setConstraint(i, data[i]);

    }
    public void round (Square square) {
        if (playing != null) playing.setPlayed();
        this.playing = square;
        markAsUsed(square.getPos(), square.getValue());
        recalculateBoard();
    }
    public Square[] getCurrentBoard() {
        return squares;
    }
    public Square getCandidate() {
        return (last == -1) ? new Square() : current[last];
    }
    public Board bet (int idx) {
        Square curr = current[last];
        curr.setBet(curr.getOptions()[idx]);
        return this;
    }
    public Board20D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinality() == 0) last--;
        return this;
    }

    // ////////////////////////////////////////////////////////
    // Deep clone
    // ////////////////////////////////////////////////////////

    public Board copy() {
        Board board = null;
        try {
            board = (Board) this.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("error en el clone");
        }
        return board;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board20D board = (Board20D) super.clone();
        // Clonamos los escaques
        Square[] cloning = new Square[CFG.CARD];
        for (int i = 0; i < squares.length; i++) cloning[i] = (Square) squares[i].clone();
        board.setSquares(cloning);

        // Ahora la posicion actual del tablero
        Square[] curr = new Square[CFG.CARD];
        for (int i = 0; i < current.length; i++) {
            curr[i] = cloning[current[i].getPos()];
        }
        board.setCurrent(curr);
        return board;
    }

    // ////////////////////////////////////////////////////////
    // Private code
    // ////////////////////////////////////////////////////////

    private void setConstraint(int pos, int value) {
        markAsUsed(pos, value);
        squares[pos].setConstraint(value);
    }
    private void recalculateBoard () {
        Square tmp;
        for (int i = 0; i < last; i++) {
            if (current[i].cardinality() <= current[i+1].cardinality()) {
                tmp = current[i];
                current[i] = current[i+1];
                current[i+1] = tmp;
            }
        }
        while (last >= 0 && current[last].cardinality() == 0) last--;
    }

    private void setSquares (Square[] squares) {
        this.squares = squares;
    }
    private void setCurrent (Square[] squares) {
        this.current = squares;
    }
    private int[] getDimensions(int pos) {
        int[] dims = {0,0};
        dims[0] = pos / CFG.CARD;
        dims[1] = pos % CFG.CARD;
        return dims;
     }
     private void markAsUsed (int pos, int value) {
         int[] axis = getDimensions(pos); // fila, columna
         // Eje x
         int x = axis[0] * CFG.CARD;
         for (int i = x; i < CFG.CARD; i++) squares[i].setUsed(value);
         // Eje y
         int maxY = (CFG.CARD * (CFG.CARD - 1)) + axis[1];
         for (int i = axis[1]; i < maxY; i += CFG.CARD)
             squares[i].setUsed(value);
     }
}
