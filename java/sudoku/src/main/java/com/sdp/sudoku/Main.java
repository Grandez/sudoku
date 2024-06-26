/*
 * This source file was generated by the Gradle 'init' task
 */
package com.sdp.sudoku;

import com.sdp.sudoku.config.CDG;
import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.io.Input;
import com.sdp.sudoku.io.Output;
import com.sdp.sudoku.tablero.Board;
import com.sdp.sudoku.tablero.BoardFactory;
import com.sdp.sudoku.tablero.Square;

import java.util.List;
import java.util.Set;

public class Main {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Main app = new Main();
        int rc = app.playGame(args);
        if (rc == CDG.DONE) System.out.println("Hecho!");
        System.exit(rc);
    }
    private int playGame(String[] args) {
        Input input = new Input();
        Integer[] data = input.load(args);
        Board board = prepareBoard(data);
        return play(board);
    }

    private int play (Board board) {
        Output output;
        Square[] current = board.getCurrentBoard();
        Square option = board.getCandidate();
        int card = option.cardinality();
        while (card != 0) {
           output = new Output();
           switch (card) {
               case 1:
                   option.setValue(0);
                   board.round(option);
                   output.setMessage("Cardinalidad 1");
                   output.setMessage("Jugando casilla " + option.getPos());
                   output.setMessage("Jugando valor " + option.getValue());
                   output.print(board.getCurrentBoard());
                   break;
               default: return CDG.FAIL;
           }
            option = board.getCandidate();
            card = option.cardinality();
        }

        return CDG.DONE;
    }
    private Board prepareBoard(Integer[] data) {
        BoardFactory factory = new BoardFactory();
        Output output = new Output();
        Board board = factory.getBoard(data);
        board.init();
        output.setBoard(board.getCurrentBoard());
        output.setMessage("Situacion inicial");
        output.print();
        return board;
    }
}
