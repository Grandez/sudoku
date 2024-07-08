package com.sdp.sudoku.boards;
/**
 * BoardBase
 * Representa el tablero y el estado de sus casillas en un momento determinado
 *
 * Cada posible tipo de tablero implementa:
 *  1. Su constructor en funcion de los datos recibidos
 *  2. Su constructor de copia
 *  3. El metodo para jugar un determinado valor en una determinada casilla (markAsUsed)
 */

import com.sdp.sudoku.ctes.CDG;
import com.sdp.sudoku.io.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BoardBase {
    protected Square[] squares;
    private   Square[] current;

    protected int status = CDG.NEXT;
    protected int last;
    protected int tree = 1;
    protected int maxTree = 1;
    protected abstract void markAsUsed (int pos, int value);

    public    abstract Board copy(boolean nextTree);

    /**
     * Devuelve el tablero actual
     * @return Array de las casillas del tablero
     */
    public Square[] getBoard() {
        return squares;
    }

    /**
     * Devuelve la lista de casillas a jugar en una determinada ronda
     * @return Lista de casillas a jugar
     */
    public List<Square> getCandidates() {
        List<Square> candidates = new ArrayList<>();
        if (last == -1) return candidates;

        while (last >= 0 && current[last].cardinal() == 1) {
            candidates.add(current[last--]);
        }

        if (candidates.size() > 0) return candidates;

        candidates.add(current[last]);
        return candidates;
    }
    /**
     * Realiza una jugada con las casillas indicadas
     * @return Lista de casillas a jugar
     */
    public int round (List<Square> options) {
        Output out = Output.getInstance();
        finishPreviousRound();
        for (Square square : options) {
            // Se da si al procesar varios se produce un value = 0 / opciones = ninguna
            if (square.cardinal() == 0) return CDG.FAIL;
            square.setPlaying();
            markAsUsed(square.getPos(), square.getValue());
        }
        // out.print(getBoard());
        int rc = recalculate();
        return rc;
    }

    /**
     * Cuando una casilla puede tener varias opciones
     * Apuesta por una de ellas
     * Ejemplo:
     * Si la casilla puede tener los valores: 3,5 o 7
     * Modifica la casilla para que su unica opcion (la apuesta) sea, por ejemplo, 5
     * @param idx Indica el indice de la opcion a la que se apuesta
     * @return
     */
    public Board bet (int idx) {
        Square curr = current[last];
        curr.setBet(curr.getOptions()[idx]);
        return (Board) this;
    }
    public int getTree () {
        return tree;
    }
    public int getMaxTree () {
        return maxTree;
    }
    protected void initBoard(Integer[] data) {
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(i);

        for (int i = 0; i < data.length; i++)  {
            if (data[i] != 0) setConstraint(i, data[i]);
        }
        initCurrent();
    }
    protected void initCurrent() {
        current = new Square[squares.length];
        System.arraycopy(squares, 0, current, 0, squares.length);
        Arrays.sort(current);
        last = current.length - 1;
        while (last >= 0 && current[last].cardinal() == 0) last--;
    }
    protected void copyObject(Board board, boolean nextTree) {
        this.tree = board.getTree();
        if (nextTree)  {
            this.tree++;
            if (this.maxTree < this.tree) this.maxTree = this.tree;
        }
        Square[] old = board.getBoard();
        for (int i = 0; i < squares.length; i++) squares[i] = new Square(old[i]);
        initCurrent();

    }
    protected void setConstraint(int pos, int value) {
        markAsUsed(pos, value);
        squares[pos].setConstraint(value);
    }
    protected void setSquaresAsPlayed() {
        for (Square square : squares) if (square.getType() == Square.TYPE.PLAYING) square.setType(Square.TYPE.PLAYED);
    }
    private int recalculate () {
        if (last == -1) return CDG.DONE;

        current = Arrays.copyOf(current, last + 1);
        Arrays.sort(current);

        while (last >= 0 && current[last].cardinal() == 0) {
            if (current[last].getValue() == 0)return CDG.FAIL;
            last--;
        }
        return CDG.NEXT;
    }
    private void finishPreviousRound() {
        for (Square square : squares) if (square.isPlaying()) square.markAsPlayed();
    }
}
