package ch.uzh.softcon.one;

import java.util.regex.Pattern;

public class IOFormatter {
    public static Turn formatInput(String input, Player player) {
        char[] tmp = input.toCharArray();
        Turn turn;

        if (tmp.length == 9 && tmp[4] == 'X' && // Check for length and syntax of input
                tmp[0] == '[' && tmp[5] == '[' && tmp[3] == ']' && tmp[8] == ']' && // Check for syntax of input
                (int)tmp[1] >= 97 && (int)tmp[1] <= 104 && // second char between a and h
                (int)tmp[6] >= 97 && (int)tmp[6] <= 104 && // seventh char between a and h
                (int)tmp[2] >= 49 && (int)tmp[2] <= 56 && // third char between 1 and 8
                (int)tmp[7] >= 49 && (int)tmp[7] <= 56) { // eight char between 1 and 8

            // subtract ascii code starting point (97 for a and 49 for 1)
            turn = new Turn((int)tmp[1] - 97, 8 - ((int)tmp[2] - 49 + 1),
                    (int)tmp[6] - 97, 8 - ((int)tmp[7] - 49 + 1), player);
        }
        else {
            turn = new Turn(-1, -1, -1, -1, player, Turn.Status.ILLEGAL_TURN);
        }
        return turn;
    }

    public static String formatOutput(String textBeforeBoard, boolean printBoard, String textAfterBoard) {
        StringBuilder out = new StringBuilder();
        out.append("\u001B[47m                                                         \u001B[0m\n");
        if (!textBeforeBoard.equals("")) out.append(textBeforeBoard).append("\n");

        if (printBoard) {
            out.append("""
                    \u001B[37m\u001B[40m       a     b     c     d     e     f     g     h       \u001B[0m
                    \u001B[37m\u001B[40m   +-------------------------------------------------+   \u001B[0m
                    """);
            for (int i = 0; i < 8; i++) {
                StringBuilder tmp = new StringBuilder("\u001B[37m\u001B[40m " + (8 - i) + " | \u001B[0m");
                for (int j = 0; j < 8; j++) {
                    Piece piece = Board.getPiece(j, i);
                    if (piece == null) tmp.append("\u001B[37m\u001B[40m[   ] ");
                    else {
                        String color = "";
                        String type;
                        String consoleFontColor = "";
                        String consoleBackgroundColor;

                        if (piece.color == Player.RED) {
                            color = "R";
                            consoleFontColor = "\u001B[91m"; //Font red
                        }
                        else if (piece.color == Player.WHITE) {
                            color = "W";
                            consoleFontColor = "\u001B[97m"; // Font white
                        }
                        if (piece.isKing()) {
                            type = "K";
                            consoleBackgroundColor = "\u001B[44m"; // Background green
                        }
                        else {
                            type = "P";
                            consoleBackgroundColor = "\u001B[40m"; // Background black
                        }

                        tmp.append(consoleFontColor).append(consoleBackgroundColor).append("[").append(color).append("_").append(type).append("]\u001B[37m\u001B[40m ");
                    }
                }
                tmp.append("| ").append(8 - i).append(" \u001B[0m\n");
                out.append(tmp);
            }
            out.append("""
                    \u001B[37m\u001B[40m   +-------------------------------------------------+   \u001B[0m
                    \u001B[37m\u001B[40m       a     b     c     d     e     f     g     h       \u001B[97m\u001B[0m
                    """);
        }
        out.append(textAfterBoard);
        return out.toString();
    }
}