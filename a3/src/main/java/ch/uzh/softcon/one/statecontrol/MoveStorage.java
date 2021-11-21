package ch.uzh.softcon.one.statecontrol;

import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.turn.Turn;

import java.util.HashMap;
import java.util.Map;

public class MoveStorage {

    private static final int max = 255;
    private static int top = 0;
    private static Turn[] moves = new Turn[max];
    private static Piece[] captures = new Piece[max];

    public static void push(Turn turn, Piece captured) {
        if (top == max) {
            int i = 1;
            while (i < max) {
                moves[i - 1] = moves[i];
                captures[i - 1] = captures[i];
                i++;
            }
            top--;
            System.out.println("Can only store " + max + " moves; first move got deleted.");
        }
        moves[top] = turn;
        if (captured != null) {
            captures[top] = captured;
        }
        top++;
    }

    public static Map<Turn, Piece> pop() {
        if (top - 1 < 0) {
            System.err.println("No more prior moves stored!");
            return null;
        } else {
            top--;
            Map<Turn, Piece> move = new HashMap<>();
            move.put(moves[top], captures[top]);
            moves[top] = null;
            captures[top] = null;
            return move;
        }
    }
}
