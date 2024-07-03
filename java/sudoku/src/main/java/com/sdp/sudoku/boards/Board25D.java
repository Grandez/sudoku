package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board25D implements Board, Cloneable {
    Square[] squares = new Square[CFG.CARD * CFG.CARD];
    Square[] current = new Square[CFG.CARD * CFG.CARD];
    Square playing;
    int last;

    public Board25D(Integer[] data) {
        for (int i = 0; i < CFG.CARD * CFG.CARD; i++) squares[i] = new Square(i);
        for (int i = 0; i < data.length; i++)         if (data[i] != 0) setConstraint(i, data[i]);

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
    public Board25D init() {
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
        Board25D board = (Board25D) super.clone();
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
        for (int i = axis[1]; i < maxY; i += CFG.CARD) squares[i].setUsed(value);
        // Cuadrados internos
        for (Integer i : getSquaresOfSquare(axis, pos)) squares[i].setUsed(value);
    }
    private List<Integer> getSquaresOfSquare(int[] axis, int pos) {
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
                squares.add((i * block) + (j * block));
            }
        }
        return squares;
    }
}
