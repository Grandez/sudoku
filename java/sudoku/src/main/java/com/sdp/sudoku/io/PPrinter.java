package com.sdp.sudoku.io;

import java.io.PrintWriter;

public class PPrinter extends PrintWriter {
    public PPrinter() { super(System.out,true); }
    public PPrinter pprint(String str) {
        super.print(str);
        return this;
    }
    public PPrinter pprint(char c) {
        super.print(c);
        return this;
    }
    public PPrinter pprintln() {
        super.println();
        return this;
    }

}
