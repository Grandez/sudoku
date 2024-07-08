/*
 * This source file was generated by the Gradle 'init' task
 */
package com.sdp.sudoku;

import com.sdp.sudoku.ctes.ANSI;
import com.sdp.sudoku.ctes.CDG;
import com.sdp.sudoku.io.*;
import com.sdp.sudoku.boards.*;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();

        Integer[] data = processCommandLine(args);

        long start = System.currentTimeMillis();
        int rc = sudoku.playGame(data);

        Board board =  sudoku.getBoard();
        printResult(board);
        printSummary(board, rc, start);

        System.exit(rc);
    }
    private static Integer[]  processCommandLine(String[] args) {
        Input input = new Input();
        return input.load(args);
    }
    private static void printResult(Board board) {
        Output out = Output.getInstance();
        out.print(board.getBoard());
    }

    private static void printSummary(Board board, int rc, long start) {
        PrintWriter printer = new PrintWriter(System.out, true);
        printer.print("Resultado: ");
        printer.println((rc == CDG.DONE) ? ANSI.use(ANSI.BOLD,"Resuelto")
                                         : ANSI.use(ANSI.RED, "Erroneo"));
        printer.print("Arboles:    ");
        printer.println(ANSI.use(ANSI.BOLD, Long.toString(board.getMaxTree())));
        printer.print("Tiempo:    ");
        printer.println(ANSI.use(ANSI.BOLD, Long.toString(System.currentTimeMillis() - start) + " ms"));
    }

}
