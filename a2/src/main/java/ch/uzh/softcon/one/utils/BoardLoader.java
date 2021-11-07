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

    public static void loadBoard() {
        loadBoard(null);
    }

    public static boolean loadBoard(String fileName) {
        try {
            BufferedReader reader;
            if (fileName == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load Game");
                fileChooser.setCurrentDirectory(new File("a2/resources/"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
                int response = fileChooser.showOpenDialog(fileChooser.getParent());

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
                    reader = new BufferedReader(new FileReader("a2/resources/" + fileName));
                } catch (FileNotFoundException ex) {
                    return false;
                }
            }

            cleanBoard();
            String row;
            int y = 0;
            while ((row = reader.readLine()) != null) {
                if (row.startsWith("//")) {
                    continue;
                }
                String[] fields = row.split(",");

                    if (row.startsWith("activePlayer")) {
                        if (fields[0].split(":")[1].equals("RED")) {
                            GameHandling.playerSubject().changePlayer(Player.RED);
                        } else {
                            GameHandling.playerSubject().changePlayer(Player.WHITE);
                        }
                        break;
                    }

                Piece p;
                for (int x = 0; x < fields.length; x++) {
                    if (!Pattern.matches("\\[[RW][_+][PK]]", fields[x])) {
                        throw new Exception();
                    }
                    if (fields[x].charAt(1) == ' ') {
                        continue;
                    } else if (fields[x].charAt(1) == 'R') {
                        p = new Piece(Player.RED);
                    } else{
                        p = new Piece(Player.WHITE);
                    }
                    if (fields[x].charAt(2) == '+') {
                        if (activeMultiJump()) {
                            System.err.println("Another piece is already in a multiJump!" +
                                    "Cannot have multiple multiJumps at the same time!");
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
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The given file was corrupted.",
                    "File error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void saveBoard() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");
            fileChooser.setCurrentDirectory(new File("a2/resources/"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));

            File file = new File("a2/resources/boardState.csv");
            int files = 1;
            while (file.exists()) {
                file = new File("a2/resources/boardState" + files + ".csv");
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
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The file could not be saved.", "General file error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
