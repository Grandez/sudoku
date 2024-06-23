package com.sdp.sudoku.io;

import java.util.*;

import static java.lang.Math.min;

public class output {
    int COLUMN = 40
    int LENGTH = 133;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    Integer[] left;
    List<StringBuffer> right = new ArrayList<StringBuffer>();
    public void print() {
        String[] linesR = makeRightArray(right);
        String[] linesL = makeLeftArray(left);
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
        return (String[]) lines.toArray();
    }
    String[] makeLeftArray(Integer[] left) {
        List<String> lines = new ArrayList<String>();
        lines.add(makeSeparator());
        StringBuilder line = new StringBuilder('|');
        int i = 0;
        do {
            line.append(left[i] == 0 ? ' ' :  left[i]);
            line.append('|');
            if (i % 9 == 0) {
                lines.add(line.toString());
                lines.add(makeSeparator());
            }
            i++;
        } while (i < left.length);
        lines.add(makeSeparator());
        return (String[]) lines.toArray();
    }
    String makeSeparator() {
        StringBuffer buff = new StringBuffer('+');
        for (int i = 0; i < 9; i++) buff.append("-+");
        return buff.toString();
    }

}
