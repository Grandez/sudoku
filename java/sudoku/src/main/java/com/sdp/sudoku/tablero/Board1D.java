package com.sdp.sudoku.tablero;

import com.sdp.sudoku.config.CFG;

public class Board1D implements Board {
    // Obviamos el cero por simplicidad
    Square[] square = new Square[CFG.CARD + 1];

    public Board1D() {
        for (int i = 0; i < square.length; i++) square[i] = new Square(i);
    }
    public Board setConstraint(int pos, int value) {
        // Aqui la funcion es la identidad
        int idx = pos;
        for (int i = 0; i < square.length; i++) {
            if (i == idx) {
                square[i].setConstraint(value);
            } else {
                square[i].setUsed(value);
            }
        }
        return this;
    }
}
