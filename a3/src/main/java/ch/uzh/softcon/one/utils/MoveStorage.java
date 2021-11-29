package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.turn.Turn;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MoveStorage {

    private static final int max = 64;
    private static int top = 0;
    //private static ArrayList<ArrayList<Object>> turnInfo = new ArrayList<>(max); // [0][0-2] turn, activeP, piece ???

    private static Map<Turn, Player>[] moves = new HashMap[max];
    private static Piece[] capturedPieces = new Piece[max];

    public static void push(Turn turn, Player activePlayer, Piece captured) {
        if (top == max) {
            int i = 1;
            while (i < max) {
                moves[i - 1] = moves[i];
                capturedPieces[i - 1] = capturedPieces[i];
                i++;
            }
            top--;
            System.out.println("Can only store " + max + " moves; first move got deleted.");
        }
        moves[top] = new HashMap<>();
        moves[top].put(turn, activePlayer);
        if (captured != null) {
            capturedPieces[top] = captured;
        }
        top++;
    }

    public static Map<Map<Turn, Player>, Piece> pop() {
        if (top - 1 < 0) {
            System.err.println("No more prior moves stored!");
            return null;
        } else {
            top--;
            Map<Map<Turn, Player>, Piece> move = new HashMap<>();
            move.put(moves[top], capturedPieces[top]);
            moves[top] = null;
            capturedPieces[top] = null;
            return move;
        }
    }

    public static Player lastPlayer() {
        Player lastP = null;
        if (top - 1 < 0) {
            return null;
        } else {
            Optional<Turn> turnMap = moves[top - 1].keySet().stream().findFirst();
            if (turnMap.isPresent()) {
                lastP = moves[top - 1].get(turnMap.get());
            }
        }
        return lastP;
    }

    public static void clear() {
        top = 0;
        moves = new HashMap[max];
        capturedPieces = new Piece[max];
    }
}