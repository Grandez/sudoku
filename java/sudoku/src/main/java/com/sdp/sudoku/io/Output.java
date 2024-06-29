package com.sdp.sudoku.io;

import com.sdp.sudoku.tablero.Square;

import java.util.*;

import static java.lang.Math.min;

public class Output {
    int COLUMN = 40;
    int LENGTH = 133;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static final String BOLD    = "\u001b[1m";
    static final String BLACK   = "\u001B[30m";
    static final String BLUE    = "\u001B[34m";
    static final String RED = "\u001B[31m";
    static final String RESET   = "\u001b[0m";
    static final String DEFINED = BOLD + BLACK;
    static final String PLAYED  = BOLD + BLUE;
    static final String PLAYING = BOLD + RED;
    Integer[] left;
    List<StringBuffer> right = new ArrayList<StringBuffer>();
    Square[] board;
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
        String[] linesR = makeRightArray(right);
        String[] linesL = makeLeftArray(board);
        int c = min(linesR.length,linesL.length);
        for (int i = 0; i < c; i++) {
            System.out.print(linesL[i]);
            System.out.print(" | ");
            System.out.print(linesR[i]);
            System.out.println(" ");
        }
        for (int i = c; i < linesL.length; i++) {
            System.out.print(linesL[i]);
            System.out.println(" | ");
        }
        for (int i = c; i < linesR.length; i++) {
            System.out.print(" | ");
            System.out.println(linesR[i]);
        }
        String space133 = new String(new char[133]).replace('\0', '-');
        System.out.println(space133);
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
        lines.add(makeSeparator());
        StringBuilder line = new StringBuilder("|");
        for (int i = 0; i < board.length; i++) {
            line.append(board[i].getValue() == 0 ? ' ' :  getSquareAsAnsi(board[i]));
            line.append('|');
            if (i > 0 && i % 9 == 0) {
                lines.add(line.toString());
                lines.add(makeSeparator());
            }
        }
        lines.add(line.toString());
        lines.add(makeSeparator());

        lines.add("                   ");
        lines.add(makeSeparator());
        return lines.toArray(new String[lines.size()]);
    }
    String makeSeparator() {
        StringBuffer buff = new StringBuffer("+");
        for (int i = 0; i < 9; i++) buff.append("-+");
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
