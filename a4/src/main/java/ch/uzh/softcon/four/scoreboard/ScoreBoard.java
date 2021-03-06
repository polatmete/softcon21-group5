package ch.uzh.softcon.four.scoreboard;

import ch.uzh.softcon.four.player.Player;

import java.io.*;

/**
 * BlackJack scoreboard
 */
public class ScoreBoard {

    /**
     * The size of the scoreboard
     */
    private static final int scoreBoardSize = 10;

    private static ScoreBoardEntry[] scoreBoard = new ScoreBoardEntry[scoreBoardSize];

    /**
     * Check if a player is eligible to be on the scoreboard and updates the scoreboard accordingly
     * @param p the player whose score is to be saved
     */
    public static void saveScore(Player p) {
        int score = p.getBalance();

        int i = 0;
        for (ScoreBoardEntry scoreBoardEntry : scoreBoard) {
            if (scoreBoardEntry == null || scoreBoardEntry.balance() < score) {
                ScoreBoardEntry s = new ScoreBoardEntry(p.getName(), p.getBalance());
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

    /**
     * Write the contents of the scoreboard onto the scoreBoard.csv file
     * @param fileName the name of the file in which the contents of the scoreboard are saved
     */
    private static void updateCSV(String fileName) {

        try {
            File file = new File(path() + fileName); //if file doesn't exist it'll create it
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
            System.err.println("ScoreBoard ERROR.");
        }
    }

    /**
     * Load the content of the scoreBoard.csv file and save it in the scoreBoard variable
     * @return the status message
     */
    private static String loadScore() {

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path() + "scoreBoard.csv"));
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
            return "Success";
        } catch (FileNotFoundException e) {
            return "ScoreBoard is empty.";
        } catch (IOException e) {
            System.err.println("ScoreBoard ERROR.");
            return "Error";
        }
    }

    /**
     * Print the up-to-date contents of the scoreboard on the terminal
     */
    public static void printScore() {
        loadScore();

        System.out.println("===== Hall of fame =====");
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

    /**
     * Calculate the size of the longest name in the scoreboard
     * @return the size of the longest name in the scoreboard
     */
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

    /**
     * Clear the contents of the scoreboard variable, and depending on the parameter, clear the contents of the file
     * @param updateCSV boolean parameter, determines if the csv file should be cleared alongside the scoreboard
     */
    public static void resetScoreBoard(boolean updateCSV) {
        scoreBoard = new ScoreBoardEntry[scoreBoardSize];
        if (updateCSV) {
            updateCSV("scoreBoard.csv");
        }
    }

    /**
     * Get the path of the scoreboard csv file
     * @return the path of the scoreboard csv file
     */
    public static String path() {
        if (new File("a4/").exists()) {
            return "a4/resources/";
        } else {
            return "resources/";
        }
    }

    /**
     * Get an entry of the scoreboard
     * @param i the index of the scoreboard entry in the scoreboard
     * @return the scoreboard entry at index i
     */
    public static ScoreBoardEntry getScoreBoardEntry(int i) {
        if (i > scoreBoardSize - 1 || i < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        } else {
            return scoreBoard[i];
        }
    }

    /**
     * Get the size of the scoreboard
     * @return the size of the scoreboard
     */
    public static int size() {
        return scoreBoardSize;
    }
}
