package com.sdp.sudoku.io.console;

import com.sdp.sudoku.ctes.ANSI;
import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.ctes.BOX;
import com.sdp.sudoku.core.Square;

import java.util.*;

import static java.lang.Math.min;

public class ConsoleOutput {
    int COLUMN = 40;
    int LENGTH = 133;

    public static ConsoleOutput instance;
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

    List<StringBuffer> right = new ArrayList<>();
    Square[] board;
    PPrinter printer = new PPrinter();

    public static ConsoleOutput getInstance() {
       if (instance == null) instance = new ConsoleOutput();
       return instance;
    }
    public ConsoleOutput init() {
        right = new ArrayList<>();
        return this;
    }
    public ConsoleOutput setMessage(String txt) {
        System.out.println(txt);
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
            printer.pprint(linesL[i]).pprint(BOX.VERT).pprint(linesR[i]).println(" ");
        }
        for (int i = c; i < linesL.length; i++) {
            printer.pprint(linesL[i]).println(BOX.VERT);
        }
        for (int i = c; i < linesR.length; i++) {
            printer.pprint("                   ").pprint(BOX.VERT).println(linesR[i]);
        }
        init();
    }

    String[] makeRightArray(List<StringBuffer> right) {
        List<String> lines = new ArrayList<>();
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
        List<String> lines = new ArrayList<>();
        lines.add(lineSeparator(0));

        int i = 0;
        int j = CFG.CARD;
        int l = 0;
        do {
            //StringBuilder line = new StringBuilder(String.format("%02d %c", l, BOX.VERT));
            StringBuilder line = new StringBuilder("   ");
            l += 9;
            line.append(BOX.VERT);
            for (; i < j; i++) {
                line.append(board[i].getValue() == 0 ? ' ' :  getSquareAsAnsi(board[i]));
                line.append(BOX.VERT);
            }
            lines.add(line.toString());
            j += CFG.CARD;
            if (i + 1 < board.length) lines.add(lineSeparator(1));
        } while (i < board.length);
        lines.add(lineSeparator(2));
        lines.add("                   ");

        return lines.toArray(new String[lines.size()]);
    }
    String lineSeparator(int where) {
        StringBuffer buff = new StringBuffer("   ");
        Character[] car = BOX.box[where];
        buff.append(car[0]);
        for (int i = 0; i < 8; i++) buff.append(BOX.HORZ).append(car[1]);
        buff.append(BOX.HORZ).append(car[2]);
        return buff.toString();
    }

    StringBuilder getSquareAsAnsi (Square square) {
        StringBuilder str;
        switch (square.getType()) {
            case CONSTRAINT: str = new StringBuilder(ANSI.DEFINED); break;
            case PLAYED: str = new StringBuilder(ANSI.PLAYED); break;
            default: str = new StringBuilder(ANSI.PLAYING); break;
        }
        return str.append(CFG.getSymbol(square.getValue())).append(ANSI.RESET);
    }
}
