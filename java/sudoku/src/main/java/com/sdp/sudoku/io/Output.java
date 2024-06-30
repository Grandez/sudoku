package com.sdp.sudoku.io;

import com.sdp.sudoku.tablero.Square;

import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;

public class Output {
    int COLUMN = 40;
    int LENGTH = 133;

    public static Output instance;
    private static int calls = 0;
/*
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
*/
    static final String BOLD    = "\u001b[1m";
    static final String BLACK   = "\u001B[30m";
    static final String BLUE    = "\u001B[34m";
    static final String RED = "\u001B[31m";
    static final String RESET   = "\u001b[0m";
    static final String DEFINED = BOLD + BLACK;
    static final String PLAYED  = BOLD + BLUE;
    static final String PLAYING = BOLD + RED;

    static final char BARRA   = '\u2502';
    static final char LEFT_UP = '\u2514';
    static final char LEFT_DOWN = '\u250C';
    static final char RIGHT_UP = '\u2518';
    static final char RIGHT_DOWN = '\u2510';
    static final char HORZ = '\u2500';
    static final char VERT = '\u2502';
    static final char CROSS = '\u253C';
    static final char CROSS_DOWN = '\u252C';
    static final char CROSS_UP = '\u2534';

    static Character[][] box = {
          {LEFT_DOWN, CROSS_DOWN, RIGHT_DOWN}
         ,{LEFT_DOWN, CROSS, RIGHT_DOWN}
         ,{LEFT_UP,   CROSS_UP, RIGHT_UP}
    };
    Integer[] left;
    List<StringBuffer> right = new ArrayList<StringBuffer>();
    Square[] board;
    PrintWriter printer = new PrintWriter(System.out,true);

    public static Output getInstance() {
       if (instance == null) instance = new Output();
       return instance;
    }
    public Output init() {
        right = new ArrayList<StringBuffer>();
        return this;
    }
    public Output setBoard(Square[] board) {
        this.board = board;
        return this;
    }
    public Output setAction(String txt) {
        right.add(new StringBuffer(txt));
        return this;
    }
    public Output setAction(StringBuffer txt) {
        right.add(txt);
        return this;
    }
    public Output setMessage(String txt) {
        right.add(new StringBuffer(txt));
        return this;
    }
    public void print (Square[] board) {
        this.board = board;
        print();
    }
    public void print() {
        if (calls++ > 0) {
            String space133 = new String(new char[133]).replace('\0', '-');
            printer.println(space133);
        }
        String[] linesR = makeRightArray(right);
        String[] linesL = makeLeftArray(board);
        int c = min(linesR.length,linesL.length);
        for (int i = 0; i < c; i++) {
            printer.print(linesL[i]);
            printer.print(BARRA);
            printer.print(linesR[i]);
            printer.println(" ");
        }
        for (int i = c; i < linesL.length; i++) {
            printer.print(linesL[i]);
            printer.println(BARRA);
        }
        for (int i = c; i < linesR.length; i++) {
            printer.print("                   ");
            printer.print(BARRA);
            printer.println(linesR[i]);
        }
        init();
    }

    String[] makeRightArray(List<StringBuffer> right) {
        List<String> lines = new ArrayList<String>();
        for (StringBuffer line : right) {
            String[] toks = line.toString().split("\n");
            for (int i = 0; i < toks.length; i++) {
                if (toks[i].length() > 80) {
                    int sep = toks[i].indexOf(' ', 75);
                    lines.add(toks[i].substring(0,sep));
                    lines.add(toks[i].substring(sep + 1));
                } else {
                    lines.add(toks[i]);
                }
            }
        }
        return lines.toArray(new String[lines.size()]);
    }
    String[] makeLeftArray(Square[] board) {
        List<String> lines = new ArrayList<String>();
        lines.add(lineSeparator(0));
        StringBuilder line = new StringBuilder();
        line.append(BARRA);
        for (int i = 0; i < board.length; i++) {
            line.append(board[i].getValue() == 0 ? ' ' :  getSquareAsAnsi(board[i]));
            line.append(BARRA);
            if (i > 0 && i % 9 == 0) {
                lines.add(line.toString());
                lines.add(lineSeparator(1));
            }
        }
        lines.add(line.toString());
        lines.add(lineSeparator(2));

        lines.add("                   ");

        return lines.toArray(new String[lines.size()]);
    }
    String lineSeparator(int where) {
        StringBuffer buff = new StringBuffer();
        Character[] car = box[where];
        buff.append(car[0]);
        for (int i = 0; i < 8; i++) buff.append(HORZ).append(car[1]);
        buff.append(HORZ).append(car[2]);
        return buff.toString();
    }

    StringBuilder getSquareAsAnsi (Square square) {
        StringBuilder str = new StringBuilder(12);
        switch (square.getType()) {
            case -1: str = new StringBuilder(DEFINED); break;
            case  1: str = new StringBuilder(PLAYED); break;
            default: str = new StringBuilder(PLAYING); break;
        }
        return str.append(square.getValue()).append(RESET);
    }
}
