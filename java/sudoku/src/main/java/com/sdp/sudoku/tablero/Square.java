package com.sdp.sudoku.tablero;

import com.sdp.sudoku.config.CFG;

import java.util.*;

public class Square implements Comparable<Square> {
    static Set<Integer> domain = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    static Integer[] wrk = new Integer[CFG.CARD];

    int pos  = 0;  // Posicion en el tablero
    int type = 0; // Fijo: -1, Calculado: 1
    int value = 0; // Valor actual

    Set<Integer> options = new HashSet<>();
    public Square() {

    }
    public Square (int pos) {
        this.pos = pos;
        this.options = new HashSet<>(CFG.OPTIONS);
    }
    public Square setConstraint (Integer value) {
        this.value = value;
        this.type = -1; // Constraint
        this.options.clear();
        return this;
    }
    public Square setValue (int value) {
        this.value = options.toArray(wrk)[value];
        this.type = 0; // Playing
        return this;
    }
    public Square setPlayed () {
        this.type = 1; // Jugado
        return this;
    }
    public Square setUsed (Integer value) {
        options.remove(value);
        return this;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getValue() {
        return value;
    }
    public int getPos() {
        return pos;
    }
    /*
    public Square setValue(int value) {
        this.value = value;
        options.remove(value);
        return this;
    }
    */

    public int cardinality () {
        return options.size();
    }
    @Override
    public int compareTo(Square o) {
        return o.cardinality() - this.cardinality(); // Ordena al reves
    }
}
