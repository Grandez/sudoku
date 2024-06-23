package com.sdp.sudoku.tablero;

import java.util.*;

public class Square implements Comparable<Square> {
    static Set<Integer> domain = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

    int pos  = 0;  // Posicion en el tablero
    int type = 0; // CaLculado, fijo, activo
    int value = 0; // Valor actual
    Set<Integer> options = new HashSet<Integer>();
    Set<Integer> used = new HashSet<Integer>();
    public Square (int pos) {
        this.pos = pos;
    }
    public Square setConstraint (Integer value) {
        this.value = value;
        this.type = -1; // Constraint
        return this;
    }
    public Square setUsed (Integer value) {
        this.used.add(value);
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

    public void setValue(int value) {
        this.value = value;
    }

    public void addOption (Integer option) {
        options.add(option);
    }
    public void addUsed (Set<Integer> used) {
        options.addAll(used);
    }

    public void nada() {
        try {
            options = (Set<Integer>) domain.clone();
            options.removeAll(used);
        } catch (CloneNotSupportedException ex) {

        }
    }
    public int cardinality () {
        return options.size();
    }
    @Override
    public int compareTo(Square o) {
        return o.cardinality() - this.cardinality(); // Ordena al reves
    }
}
