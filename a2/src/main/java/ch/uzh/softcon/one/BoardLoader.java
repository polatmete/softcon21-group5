package ch.uzh.softcon.one;

import java.io.*;

import static ch.uzh.softcon.one.Board.*;

public class BoardLoader {

    public static void loadBoard() {
        cleanBoard();
        Piece p;
        String row;
        int y = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("a2/src/boardState.csv"));

            while ((row = reader.readLine()) != null) {
                String[] fields = row.split(",");

                if (y > size() - 1) {
                    String activePlayer = fields[0].split(":")[1];
                    Game.activePlayer = Player.valueOf(activePlayer);
                    break;
                }

                for (int x = 0; x < fields.length; x++) {
                    if (fields[x].charAt(1) == ' ') {
                        continue;
                    }
                    if (fields[x].charAt(1) == 'R') {
                        p = new Piece(Player.RED);
                    } else {
                        p = new Piece(Player.WHITE);
                    }
                    if (fields[x].charAt(2) == '+') {
                        p.startMultiJump();
                        //TODO STATUS MULTIJUMP
                    }
                    if (fields[x].charAt(3) == 'K') {
                        p.promote();
                    }

                    placePiece(x, y, p);
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBoard() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("a2/src/boardState.csv"));

            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    Piece piece = getPiece(j, i);
                    StringBuilder stringedPiece = new StringBuilder();
                    if (piece == null) {
                        stringedPiece.append("[   ],");
                    } else {
                        stringedPiece.append("[");
                        if (piece.getColor() == Player.RED) {
                            stringedPiece.append("R");
                        } else {
                            stringedPiece.append("W");
                        }
                        if (piece.inMultiJump()) {
                            stringedPiece.append("+");
                        } else {
                            stringedPiece.append("_");
                        }
                        if (piece.isKing()) {
                            stringedPiece.append("K");
                        } else {
                            stringedPiece.append("P");
                        }
                        stringedPiece.append("],");
                    }
                    writer.write(stringedPiece.toString());
                }
                writer.newLine();
            }
            StringBuilder aP = new StringBuilder().append("activePlayer:");
            if (Game.activePlayer == Player.RED) {
                aP.append(Player.RED);
            } else {
                aP.append(Player.WHITE);
            }
            writer.write(aP.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
