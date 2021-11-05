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
            fileChooser.setCurrentDirectory(new File("a2/src/"));
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
                        if (fields[0].split(":")[1].equals("RED")) {
                            Game.activatePlayerRed();
                        } else {
                            Game.activatePlayerWhite();
                        }
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
                        }
                        if (fields[x].charAt(3) == 'K') {
                            p.promote();
                        }

                        placePiece(x, y, p);
                    }
                    y++;
                }
            } else JOptionPane.showMessageDialog(null, "You messed up.", "Load Failed", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBoard() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game...");
            fileChooser.setCurrentDirectory(new File("a2/src/"));
            fileChooser.setSelectedFile(new File("boardState.csv"));
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
                            if (piece.isInMultiJump()) {
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
                writer.write("activePlayer:" + Game.getActivePlayer());
                writer.flush();
                writer.close();
            } else JOptionPane.showMessageDialog(null,"You messed up.", "Save Failed", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
