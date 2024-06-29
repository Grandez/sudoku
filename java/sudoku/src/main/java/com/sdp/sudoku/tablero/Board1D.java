package com.sdp.sudoku.tablero;

import com.sdp.sudoku.config.CFG;

import java.util.Arrays;

public class Board1D implements Board, Cloneable {
    Square[] squares = new Square[CFG.CARD];
    Square[] current = new Square[CFG.CARD];
    Square playing;
    int last;

    private Board1D() {
    }

    public Board1D(Integer[] data) {
        int i;
        for (i = 0; i < squares.length; i++) squares[i] = new Square(i);
        for (i = 0; i < data.length; i++) {
            if (data[i] != 0) setConstraint(i, data[i]);
        }
    }
    public void round (Square square) {
        if (playing != null) playing.setPlayed();
        this.playing = square;
        for (int i = 0; i < squares.length; i++) {
            squares[i].setUsed(square.getValue());
        }
        recalculateBoard();
    }
    public Square[] getCurrentBoard() {
        return squares;
    }
    public int getCurrentSquare() {
        return playing.getPos();
    }
    public Square getCandidate() {
        return (last == -1) ? new Square() : current[last];
    }
    public Board bet (int idx) {
        Square curr = current[last];
        curr.setBet(curr.getOptions()[idx]);
        return this;
    }
    public Board1D init() {
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinality() == 0) last--;
        return this;
    }

    private void setConstraint(int pos, int value) {
        // Aqui la funcion es la identidad
        int idx = pos;
        for (int i = 0; i < squares.length; i++) {
            if (i == idx) squares[i].setConstraint(value);
            squares[i].setUsed(value);
        }
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
        Board1D board = (Board1D) super.clone();
        // Clonamos los escaques
        Square[] cloning = new Square[CFG.CARD];
        for (int i = 0; i < squares.length; i++) cloning[i] = (Square) squares[i].clone();
        board.setSquares(cloning);

        // Ahora la posicion actual del tablero
        Square[] curr = new Square[CFG.CARD];
        for (int i = 0; i < current.length; i++) {
            curr[i] = cloning[current[i].getPos()];
        }
        board.setCurrent(cloning);
        return board;
    }

    private void setSquares (Square[] squares) {
        this.squares = squares;
    }
    private void setCurrent (Square[] squares) {
        this.current = squares;
    }

}
