package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.regex.Pattern;

import static ch.uzh.softcon.one.abstraction.Board.*;
import static ch.uzh.softcon.one.abstraction.Piece.activeMultiJump;

public class BoardLoader {

    public static boolean loadBoard(String fileName) {
        try {
            BufferedReader reader;
            if (fileName == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load Game");
                fileChooser.setCurrentDirectory(new File(path()));
                fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().isFile()) {
                    reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
                } else {
                    if (response != JFileChooser.CANCEL_OPTION) {
                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                "Desired file does not exist.", "Load Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    return false;
                }
            } else {
                try {
                    reader = new BufferedReader(new FileReader(path() + fileName));
                } catch (FileNotFoundException ex) {
                    return false;
                }
            }

            cleanBoard();
            String row;
            int y = 0;
            while ((row = reader.readLine()) != null) {
                if (row.startsWith("//") || row.isEmpty()) {
                    continue;
                }

                String[] fields = row.split(",");
                    if (row.startsWith("activePlayer")) {
                        if (fields[0].split(":")[1].equals("RED")) {
                            GameHandling.playerSubject().changePlayer(Player.RED);
                        } else if (fields[0].split(":")[1].equals("WHITE")) {
                            GameHandling.playerSubject().changePlayer(Player.WHITE);
                        } else throw new Exception("Active player is corrupt!");
                        continue;
                    }

                Piece p;
                for (int x = 0; x < fields.length; x++) {
                    if (!Pattern.matches("\\[[RW ][_+ ][PK ]]", fields[x])) {
                        throw new Exception("Board is corrupt or non commented text is written!");
                    }
                    if (fields[x].charAt(1) == ' ') {
                        continue;
                    } else if (fields[x].charAt(1) == 'R') {
                        p = new Piece(Player.RED);
                    } else {
                        p = new Piece(Player.WHITE);
                    }
                    if (fields[x].charAt(2) == '+') {
                        if (activeMultiJump()) {
                            throw new Exception("Cannot have multiple pieces in a multi jump!");
                        } else {
                            p.startMultiJump();
                        }
                    }
                    if (fields[x].charAt(3) == 'K') {
                        p.promote();
                    }

                    placePiece(x, y, p);
                }
                y++;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(),
                    "File error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void saveBoard() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");
            fileChooser.setCurrentDirectory(new File(path()));
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
            fileChooser.setAcceptAllFileFilterUsed(false);

            File file = new File(path() + "boardState.csv");
            int files = 1;
            while (file.exists()) {
                file = new File(path() + "boardState" + files++ + ".csv");
            }
            fileChooser.setSelectedFile(file);
            int response = fileChooser.showSaveDialog(fileChooser.getParent());

            if (response == JFileChooser.APPROVE_OPTION) {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(fileChooser.getSelectedFile().getAbsolutePath()));

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
                writer.write("activePlayer:" + GameHandling.playerSubject().activePlayer());
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The file could not be saved.",
                    "General file error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String path() {
        if (new File("a3/").exists()) {
            return "a3/resources/";
        } else {
            return "resources/";
        }
    }
}
