package com.sdp.sudoku.config;

public class BOX {
    static final char LEFT_UP      = '\u2514';
    static final char LEFT_DOWN    = '\u250C';
    static final char LEFT_MIDDLE  = '\u251C';
    static final char RIGHT_UP     = '\u2518';
    static final char RIGHT_DOWN   = '\u2510';
    static final char RIGHT_MIDDLE = '\u2524';
    static final char CROSS        = '\u253C';
    static final char CROSS_DOWN   = '\u252C';
    static final char CROSS_UP     = '\u2534';
    public static final char HORZ         = '\u2500';
    public static final char VERT         = '\u2502';

    public static Character[][] box = {
            {LEFT_DOWN,   CROSS_DOWN, RIGHT_DOWN}
            ,{LEFT_MIDDLE, CROSS,      RIGHT_MIDDLE}
            ,{LEFT_UP,     CROSS_UP,   RIGHT_UP}
    };

}
