package com.sdp.sudoku.config;

import java.util.*;

public class CFG {
    static char[] dom09 = {'0','1','2','3','4','5','6','7','8','9'};
    static char[] dom16 = {'0','1','2','3','4','5','6','7','8','9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static char[] DOMAIN = {'0','1','2','3','4','5','6','7','8','9'};
    public static int CARD = DOMAIN.length - 1;

    public static char getSymbol(int idx) {
        return DOMAIN[idx];
    }
    public void setDomain(int size) {
        if (size ==  9) DOMAIN = dom09;
        if (size == 16) DOMAIN = dom16;
        CARD = DOMAIN.length - 1;
    }
}
