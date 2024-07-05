package com.sdp.sudoku.boards;

import com.sdp.sudoku.ctes.CDG;
import com.sdp.sudoku.io.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BoardBase {
    protected Square[] squares;
    protected Square[] current;
    protected Square playing;
    protected int last;

    public    abstract Board copy();
    protected abstract void markAsUsed (int pos, int value);

    public Square[] getBoard() {
        return squares;
    }
    public List<Square> getCandidates() {
        List<Square> candidates = new ArrayList<>();
        if (last == -1) return candidates;
        while (last >= 0 && current[last].cardinal2() == 1) candidates.add(current[last--]);
        if (candidates.size() > 0) return candidates;
        candidates.add(current[last]);
        return candidates;
    }
    public int round (Square sq) {
        Square square = null;
        for (int i = 0; i < squares.length; i++) {
            square = squares[i];
            if (!square.isPlaying()) continue;
            markAsUsed(square.getPos(), square.getValue());
        }

        for (int i = 0; i < squares.length; i++) {
            square = squares[i];
            if (square.isPlaying()) square.markAsPlayed();;
        }

        return recalculate();
    }
    public Board bet (int idx) {
        Square curr = current[last];
        curr.setBet(curr.getOptions()[idx]);
        return (Board) this;
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
        System.arraycopy(current, 0, current, 0, last);

        Arrays.sort(current);
        while (last >= 0 && current[last].cardinal() == 0) {
            if (current[last].getValue() != 0) return CDG.FAIL;
            last--;
        }
        return CDG.NEXT;
    }
}
