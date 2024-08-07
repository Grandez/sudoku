/*
 * This source file was generated by the Gradle 'init' task
 */
package com.sdp.sudoku;

import com.sdp.sudoku.config.GameDefinition;
import com.sdp.sudoku.core.Sudoku;
import com.sdp.sudoku.ctes.ANSI;
import com.sdp.sudoku.ctes.CDG;
import com.sdp.sudoku.boards.*;
import com.sdp.sudoku.io.console.ConsoleInput;
import com.sdp.sudoku.io.console.ConsoleOutput;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();

        GameDefinition game = processCommandLine(args);

        long start = System.currentTimeMillis();
        int rc = sudoku.playGame(game);

        Board board =  sudoku.getBoard();

        printSummary(board, rc, start);
        printResult(board);

        System.exit(rc);
    }
    private static GameDefinition  processCommandLine(String[] args) {
        if (args.length > 0) {
            String cmd = args[0].toUpperCase();
            if (cmd.compareTo("-H")     == 0) showHelp(false);
            if (cmd.compareTo("--HELP") == 0) showHelp(true);
        }
        ConsoleInput input = new ConsoleInput();
        return input.load(args);
    }
    private static void printResult(Board board) {
        ConsoleOutput out = ConsoleOutput.getInstance();
        out.print(board.getBoard());
    }
    private static void printSummary(Board board, int rc, long start) {
        PrintWriter printer = new PrintWriter(System.out, true);
        printer.print("Resultado: ");
        printer.println((rc == CDG.DONE) ? ANSI.use(ANSI.BOLD,"Resuelto")
                                         : ANSI.use(ANSI.RED, "Erroneo"));
//        printer.print("Jugadas:   ");
//        printer.println(ANSI.use(ANSI.BOLD, Long.toString(board.getMaxTree())));
        printer.print("Tiempo:    ");
        printer.println(ANSI.use(ANSI.BOLD, Long.toString(System.currentTimeMillis() - start) + " ms"));
    }
    private static void showHelp(boolean longHelp) {
        System.out.println("Solucionador de Sudokus");
        System.out.println("Uso: java -jar sudoku.jar [fichero_de_definicion_del_sudoku]");
        System.out.println("Si se omite el fichero se lee de la consola");

        if (!longHelp) System.exit(1);

        System.out.println("El fichero es de la forma:");
        System.out.println("0 X X X ...");
        System.out.println("0 0 X X ...");
        System.out.println("...");
        System.out.println("[0 ]");
        System.out.println("Donde:");
        System.out.println("\t0 - Representa una casilla vacia");
        System.out.println("\tX - Uno de los simbolos posibles");
        System.out.println("El tipo de Sudoku se identifica por el numero de filas y columnas");
        System.out.println("Si la ultima fila contiene un unico cero se suprime la restriccion de los bloques internos");
        System.out.println("Ejemplo:");
        System.out.println("\t5 0 0 7 0 1 0 3 0");
        System.out.println("\t0 4 0 0 0 0 2 0 0");
        System.out.println("\t3 8 0 9 4 0 0 0 0");
        System.out.println("\t0 3 5 6 8 7 0 9 4");
        System.out.println("\t0 6 1 4 5 0 7 2 3");
        System.out.println("\t0 7 9 0 0 0 8 0 0");
        System.out.println("\t7 0 0 0 1 0 0 0 0");
        System.out.println("\t0 0 0 8 0 4 5 0 2");
        System.out.println("\t0 5 0 0 0 0 0 4 1");
        System.out.println("Seria un Sudoku clasico");
        System.out.println("Añadiendo la linea:");
        System.out.println("\t0");
        System.out.println("Seria un Sudoku que solo valida filas y columnas");

        System.exit(1);
    }

}
