package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.regex.Pattern;

import ch.uzh.softcon.one.abstraction.Board;
import static ch.uzh.softcon.one.abstraction.Piece.activeMultiJump;

public class BoardLoader {

    private static Board boardInstance;

    public static boolean loadBoard(String fileName) throws Exception {
        boardInstance = Board.getInstance();
        try {
            BufferedReader reader;
            if (fileName == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load Game");
                fileChooser.setCurrentDirectory(new File(path()));
                fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
                fileChooser.setAcceptAllFileFilterUsed(false);

                fileName = new File(path() + "initialBoard.csv").getAbsolutePath();
                int response = JFileChooser.APPROVE_OPTION;
                if (GameHandling.playerSubject() != null) {
                    response = fileChooser.showOpenDialog(null);
                    fileName = fileChooser.getSelectedFile().getAbsolutePath();
                }

                if (response == JFileChooser.APPROVE_OPTION && new File(fileName).isFile()) {
                    reader = new BufferedReader(new FileReader(fileName));
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

            boardInstance.cleanBoard();
            String row;
            int y = 0;
            while ((row = reader.readLine()) != null) {
                if (row.startsWith("//") || row.isEmpty()) {
                    continue;
                }

                String[] fields = row.split(",");
                if (row.startsWith("activePlayer")) {
                    if (fields[0].split(":")[1].equals("RED")) {
                        if (GameHandling.playerSubject() != null)
                            GameHandling.playerSubject().changePlayer(Player.RED);
                    } else if (fields[0].split(":")[1].equals("WHITE")) {
                        if (GameHandling.playerSubject() != null)
                            GameHandling.playerSubject().changePlayer(Player.WHITE);
                    } else throw new Exception("Active player is corrupt!");
                    continue;
                }

                Piece p;
                Piece multiJumpPiece = null;
                boolean alreadyJumping = false;
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
                        if (alreadyJumping) {
                            throw new Exception("Cannot have multiple pieces in a multi jump!");
                        } else {
                            alreadyJumping = true;
                            multiJumpPiece = p;
                        }
                    }
                    if (fields[x].charAt(3) == 'K') {
                        p.promote();
                    }

                    boardInstance.placePiece(x, y, p);
                }
                if (multiJumpPiece != null)
                    multiJumpPiece.promote();
                y++;
            }
            return true;
        } catch (Exception e) {
            if (GameHandling.playerSubject() != null)
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(),
                        "File error", JOptionPane.ERROR_MESSAGE);
            else throw e;
            return false;
        }
    }

    public static void saveBoard() {
        saveBoard("boardState");
    }

    public static void saveBoard(String fileName) {
        boardInstance = Board.getInstance();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");
            fileChooser.setCurrentDirectory(new File(path()));
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
            fileChooser.setAcceptAllFileFilterUsed(false);

            File file = new File(path() + fileName + ".csv");
            int files = 1;
            while (file.exists()) {
                file = new File(path() + fileName + files++ + ".csv");
            }
            fileChooser.setSelectedFile(file);
            int response = JFileChooser.APPROVE_OPTION;
            String activePlayer = "";
            String path = new File(fileName + ".csv").getAbsolutePath();
            if (GameHandling.playerSubject() != null) {
                response = fileChooser.showSaveDialog(fileChooser.getParent());
                path = fileChooser.getSelectedFile().getAbsolutePath();
                activePlayer = GameHandling.playerSubject().activePlayer().toString();
            }

            if (response == JFileChooser.APPROVE_OPTION) {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(path));

                for (int i = 0; i < boardInstance.size(); i++) {
                    for (int j = 0; j < boardInstance.size(); j++) {
                        Piece piece = boardInstance.getPiece(j, i);
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
                writer.write("activePlayer:" + activePlayer);
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
