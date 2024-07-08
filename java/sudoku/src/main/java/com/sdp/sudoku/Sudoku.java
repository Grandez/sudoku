/**
 * Sudoku
 * Encapsula la representacion del juego
 */
package com.sdp.sudoku;

import com.sdp.sudoku.boards.*;
import com.sdp.sudoku.ctes.CDG;
import com.sdp.sudoku.io.Output;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    Board solution;
    Output out = Output.getInstance();

    static int jugada = 0;

    /**
     * Crea un tablero con los datos aportados y lo juega
     * @param data Datos iniciales del tablero
     * @return 0 si ha encontrado la solucion
     */
    public int playGame(Integer[] data) {
        this.solution = prepareBoard(data);
        out.print(solution.getBoard());
        return play(solution);
    }
    /**
     * Devuelve el tablero resultante del juego
     * @return Board la representacion del tablero
     */
    public Board getBoard() {
        return solution;
    }

    /*
     * Ejecuta el juego mientras haya opciones
     * La lista de casillas (opciones candidatas) a jugar ser:
     * - Ninguna. Se ha finalizado el juego
     * - Varias: Cada una de ellas solo tiene una opcion posible. Con lo que es un tablero equivalente
     * - Una: Si solo tiene una opcion posible es el caso anterior
     *        Si tiene varias opciones, crea un escenario (arbol) para cada una de las opciones posibles
     * @param board
     * @return
     */
    private int play (Board board) {
        int rc = CDG.NEXT;

        while (rc == CDG.NEXT) {
            this.solution = board;

            List<Square> options = board.getCandidates();
            switch (options.size()) {
                case  0: return CDG.DONE;
                case  1: rc = playOption (options, board); break;
                default:
                    jugada++;
                    rc = board.round(options); break;
            }
        }
        return rc;
    }

    private int playOption(List<Square> options, Board board) {
        Board pBoard = null;
        int rc;
        switch (options.get(0).cardinal()) {
            case 0: rc = CDG.FAIL; break;
            case 1: rc = board.round(options); break;
            default:
//                out.setMessage("Creando arbol " + (board.getMaxTree() + 1) + " con " + options.get(0).cardinal() + "opciones");
                pBoard = board.copy(true);
                List<Square> copyList = new ArrayList<>(options);

                for (int i = 0; i < copyList.get(0).cardinal(); i++) {
//                    System.out.println("Jugando opcion " + copyList.get(0).getOptions()[i] + " en " + copyList.get(0).getPos());
                    Board iboard = pBoard.copy(false);
                    iboard.bet(i);  // Ponemos el valor como unica opcion
                    if (play(iboard) == CDG.DONE) return CDG.DONE;
                }
                rc = CDG.FAIL;
        }
        return rc;
    }
    private Board prepareBoard(Integer[] data) {
        BoardFactory factory = new BoardFactory();
        Board board = factory.getBoard(data);
        return board;
    }
}
