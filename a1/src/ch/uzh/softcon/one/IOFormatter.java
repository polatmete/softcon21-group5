package ch.uzh.softcon.one;

public class IOFormatter {
    public static Turn formatInput(String input, Player player) {
        char[] tmp = input.toLowerCase().toCharArray();
        Turn turn;

        if (tmp.length == 5 && tmp[2] == 'x' && // input is exactly 5 chars long and third char is x
                (int)tmp[0] >= 97 && (int)tmp[0] <= 104 && // first char between a and h
                (int)tmp[3] >= 97 && (int)tmp[3] <= 104 && // fourth char between a and h
                (int)tmp[1] >= 49 && (int)tmp[1] <= 56 && // second char between 1 and 8
                (int)tmp[4] >= 49 && (int)tmp[4] <= 56) { // fifth char between 1 and 8
            turn = new Turn((int)tmp[0] - 97, (int)tmp[1] - 49, (int)tmp[3] - 97, (int)tmp[4] - 49, player); // subtract ascii code starting point (97 for a and 49 for 1)
        }
        else {
            turn = new Turn(-1, -1, -1, -1, player, Turn.Status.ILLEGAL_TURN);
        }
        return turn;
    }

    public static String formatOutput(String textBeforeBoard, boolean printBoard, String textAfterBoard) {
        StringBuilder out = new StringBuilder();
        if (!textBeforeBoard.equals("")) out.append(textBeforeBoard).append("\n");

        if (printBoard) {
            out.append("""
                          a     b     c     d     e     f     g     h
                      +-------------------------------------------------+
                    """);
            for (int i = 0; i < 8; ++i) {
                StringBuilder tmp = new StringBuilder(8 - i + " | ");
                for (int j = 0; j < 8; ++j) {
                    Piece piece = Board.getPiece(i, j);
                    if (piece == null) tmp.append("[   ] ");
                    else {
                        String color = "";
                        String type;

                        if (piece.color == Player.RED) color = "R";
                        else if (piece.color == Player.WHITE) color = "W";
                        if (piece.isKing) type = "K";
                        else type = "P";

                        tmp.append("[").append(color).append("_").append(type).append("] ");
                    }
                }
                tmp.append("| ").append(8 - i).append("\n");
                out.append(tmp);
            }
            out.append("""
                      +-------------------------------------------------+
                          a     b     c     d     e     f     g     h
                    """);
        }
        out.append(textAfterBoard);
        return out.toString();
    }
}