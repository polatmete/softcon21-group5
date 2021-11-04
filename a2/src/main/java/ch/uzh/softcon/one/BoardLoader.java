package ch.uzh.softcon.one;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static ch.uzh.softcon.one.Board.*;

public class BoardLoader {

    public static void loadBoard() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Game...");
            fileChooser.setCurrentDirectory(new File("./src/"));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
                cleanBoard();
                Piece p;
                String row;
                int y = 0;
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
            } else System.out.println("Canceled or Error.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBoard() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game...");
            fileChooser.setCurrentDirectory(new File("./src/"));
            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getAbsolutePath()));

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
            } else System.out.println("Canceled or Error.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
