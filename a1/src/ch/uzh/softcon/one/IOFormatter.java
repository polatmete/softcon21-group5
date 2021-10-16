package ch.uzh.softcon.one;

public class IOFormatter {
    public static Turn formatInput(String input) {
        // TODO
        return null;
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