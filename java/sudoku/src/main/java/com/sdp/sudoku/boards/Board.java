/**
 * Board
 * Cualquier tablero se gestiona con estos metodos
 *
 * - Un tablero esta compuesto de un conjunto de casillas (squares)
 * - Primero se crea la isntancia adecuada del tablero a traves de una factoria
 *
 * getBoard      - devuelve la situacion actual de las casillas
 * getCandidates - devuelve la(s) casilla(s) que se pueden jugar en una ronda
 * bet           - "Apuesta" por un valor en una casilla en una jugada dada
 * copy          - Constructor de copia del tablero para recursividad
 * round         - Realiza una jugada.
 *                 devolvera:
 *                  0 - NEXT - Mientras haya opciones de juego
 *                 -1 - FAIL - No ha encontrado una solucion
 *                  1 - DONE - Ha encontrado una solucion
 */
package com.sdp.sudoku.boards;

import com.sdp.sudoku.core.Square;

import java.util.List;

public interface Board {
    Square[]     getBoard();
    List<Square> getCandidates();
    Board        bet(int option);
    Board        copy(boolean nextTree);
    int          round(List<Square> options);
}
