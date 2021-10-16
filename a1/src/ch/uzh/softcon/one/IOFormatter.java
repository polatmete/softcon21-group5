package ch.uzh.softcon.one;

public class IOFormatter {
    public static Turn formatInput(String input, Player player) {
        Turn turn;
        String[] tmp1 = input.toLowerCase().split("", 5);
        char[] tmp = input.toLowerCase().toCharArray();

        if (tmp[2] == 'x' && // third char is x
                (int)tmp[0] >= 97 && (int)tmp[0] <= 104 && // first char between a and h
                (int)tmp[3] >= 97 && (int)tmp[3] <= 104 && // fourth char between a and h
                (int)tmp[1] >= 49 && (int)tmp[1] <= 56 && // second char between 1 and 8
                (int)tmp[4] >= 49 && (int)tmp[4] <= 56) { // fifth char between 1 and 8
            turn = new Turn((int)tmp[0] - 97, (int)tmp[1] - 49, (int)tmp[3] - 97, (int)tmp[5] - 49, player);
        }
        else {
            turn = new Turn(-1, -1, -1, -1, player, Turn.Status.ILLEGAL_TURN);
        }
        return turn;
    }

    public static String formatOutput(String textBeforeBoard, boolean printBoard, String textAfterBoard) {
        String out = "";
        if (!textBeforeBoard.equals("")) out += textBeforeBoard + "\n";

        if (printBoard) {
            out += "      a     b     c     d     e     f     g     h\n" +
                    "  +-------------------------------------------------+\n";
            for (int i = 0; i < 8; ++i) {
                String tmp = 8-i + " | ";
                for (int j = 0; j < 8; ++j) {
                    Piece piece = Board.getPiece(i, j);
                    if (piece == null) tmp += "[   ] ";
                    else {
                        String color = "";
                        String type = "";

                        if (piece.color == Player.RED) color = "R";
                        else if (piece.color == Player.WHITE) color = "W";
                        if (piece.isKing) type = "K";
                        else type = "P";

                        tmp += "[" + color + "_" + type + "] ";
                    }
                }
                tmp += "| " + (8-i) + "\n";
                out += tmp;
            }
            out +=  "  +-------------------------------------------------+\n" +
                    "      a     b     c     d     e     f     g     h\n";
        }
        out += textAfterBoard;
        return out;
    }
}