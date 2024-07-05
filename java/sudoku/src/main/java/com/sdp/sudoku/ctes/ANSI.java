package com.sdp.sudoku.ctes;

public class ANSI {
    public static final String BOLD    = "\u001b[1m";
    public static final String BLACK   = "\u001B[30m";
    public static final String BLUE    = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String RESET   = "\u001b[0m";
    public static final String DEFINED = BOLD + BLACK;
    public static final String PLAYED  = BOLD + BLUE;
    public static final String PLAYING = BOLD + RED;

    public static String use(String ansi, String txt) {
        StringBuilder str = new StringBuilder(ansi);
        str.append(txt);
        str.append(RESET);
        return str.toString();
    }
}
