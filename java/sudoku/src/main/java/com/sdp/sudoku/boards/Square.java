package com.sdp.sudoku.boards;

import com.sdp.sudoku.config.CFG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Square implements Comparable<Square> {
//    static Set<Character> domain = new HashSet<Character>(Arrays.<Character>asList(Arrays.copyOfRange(CFG.DOMAIN, 1, CFG.CARD + 1)));

    public enum TYPE { FREE, CONSTRAINT, PLAYING, PLAYED}
    int pos  = 0;  // Posicion en el tablero
    TYPE type = TYPE.FREE; // Fijo: -1, Calculado: 1
    int value = 0; // Valor actual

    Set<Integer> options = new HashSet<>();
    public Square() {

    }
    public Square (int pos) {
        this.pos = pos;
        this.options = new HashSet<Integer>();
        for (int i = 1; i < CFG.DOMAIN.length; i++) this.options.add(i);
    }
    // Constructor de copia
    public Square(Square square) {
        this.pos = square.getPos();
        this.type = square.getType();
        this.value = square.getValue();
        Collections.addAll(this.options, square.getOptions());
    }
    public Square setConstraint (Integer value) {
        this.value = value;
        this.type = TYPE.CONSTRAINT; // Constraint
        this.options.clear();
        return this;
    }
    public Square setPlaying() {
        Integer[] wrk = new Integer[CFG.CARD];
        this.value = options.toArray(wrk)[0];
        this.type = TYPE.PLAYING; // Playing
        options.clear();
        return this;
    }
    public Square setPlayed () {
        this.type = TYPE.PLAYED; // Jugado
        return this;
    }
    public Square setUsed (Integer value) {
        options.remove(value);
        return this;
    }
    public Integer[] getOptions() {
        return options.toArray(new Integer[options.size()]);
    }
    public Square setBet(Integer value) {
        options.clear();
        options.add(value);
        return this;
    }
    public TYPE getType() {
        return type;
    }
    public void setType(TYPE type) {
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

    public int getNumberOfOptions () {
        int size = options.size();
        if (size == 0) {
            if (value == 0) {
                return -1;
            } else {
                return size;
            }
        }
        return size;
    }
    public int cardinality () {
        int size = options.size();
        if (size == 0) {
            if (value == 0) {
                return -1;
            } else {
                return size;
            }
        }
        return size;
    }
    public int cardinal2() {
        return options.size();
    }
    public int cardinal () {
        int size = options.size();
        return (size == 0 && value == 0) ? -1 : size;
    }

    @Override
    public int compareTo(Square o) {
        return o.cardinal() - this.cardinal(); // Ordena al reves
    }
    public boolean isPlaying() {
        return  type == TYPE.PLAYING;
    }
    public void markAsPlayed() {
        type = TYPE.PLAYED;
    }
}
