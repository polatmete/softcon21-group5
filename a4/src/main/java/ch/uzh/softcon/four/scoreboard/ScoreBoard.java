package ch.uzh.softcon.four.scoreboard;

import ch.uzh.softcon.four.logic.IOFormatter;
import ch.uzh.softcon.four.player.Player;

import java.io.*;

public class ScoreBoard {

    private static final int scoreBoardSize = 10;
    private static ScoreBoardEntry[] scoreBoard = new ScoreBoardEntry[scoreBoardSize];
    private static int score;

    public static void saveScore(Player p) {
        score = p.balance();

        int i = 0;
        for (ScoreBoardEntry scoreBoardEntry : scoreBoard) {
            if (scoreBoardEntry == null || scoreBoardEntry.balance() < score) {
                ScoreBoardEntry s = new ScoreBoardEntry(p.getName(), p.balance());
                ScoreBoardEntry previous = s;
                ScoreBoardEntry current;
                while (i < scoreBoardSize) {
                    current = scoreBoard[i];
                    scoreBoard[i] = previous;
                    previous = current;
                    i++;
                }
                updateCSV("scoreBoard.csv");
                break;
            }
            i++;
        }

    }

    private static void updateCSV(String fileName) {

        try {
            File file = new File("a4/resources/" + fileName); //if file doesn't exist it'll create it
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            for (ScoreBoardEntry scoreBoardEntry : scoreBoard) {
                if (scoreBoardEntry != null) {
                    pw.println(scoreBoardEntry.getName() + "," + scoreBoardEntry.balance());
                } else {
                    pw.println("null");
                }
            }

            pw.close();
        } catch (IOException e) {
            System.out.println(IOFormatter.formatErrorMessage("ScoreBoard ERROR."));
        }
    }

    public static void loadScore() {

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("a4/resources/scoreBoard.csv"));
            String line = csvReader.readLine();
            int i = 0;
            while (line != null) {
                if (line.equals("null")) {
                    scoreBoard[i] = null;
                } else {
                    String[] data = line.split(",");
                    scoreBoard[i] = new ScoreBoardEntry(data[0], Integer.parseInt(data[1]));
                }
                line = csvReader.readLine();
                i++;
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println(IOFormatter.formatErrorMessage("ScoreBoard ERROR."));
        }
    }

    public static void printScore() {

        System.out.println("**Scoreboard**");
        System.out.println("--------------");
        int rank = 1;
        int sizeOfLongestName = sizeOfLongestName();
        int lengthOfLargestRank = String.valueOf(scoreBoardSize).length();
        for (ScoreBoardEntry scoreBoardEntry : scoreBoard) {
            String spacingBeforeName = new String(new char[lengthOfLargestRank - String.valueOf(rank).length() + 1]).replace("\0", " ");
            if (scoreBoardEntry != null) {
                String name = scoreBoardEntry.getName();
                int score = scoreBoardEntry.balance();
                String spacingAfterName = new String(new char[sizeOfLongestName - name.length() + 3]).replace("\0", " ");
                System.out.println(rank + "." + spacingBeforeName + name + spacingAfterName + score);
            } else {
                System.out.println(rank + "." + spacingBeforeName + "-");
            }
            rank++;
        }
        System.out.println();
    }

    private static int sizeOfLongestName() {
        int longest = -1;
        for (ScoreBoardEntry scoreBoardEntry : scoreBoard) {
            if (scoreBoardEntry != null) {
                if (scoreBoardEntry.getName().length() > longest) {
                    longest = scoreBoardEntry.getName().length();
                }
            }
        }
        return longest;
    }

    public static void resetScoreBoard() {
        scoreBoard = new ScoreBoardEntry[scoreBoardSize];
        updateCSV("scoreBoard.csv");
    }
}
