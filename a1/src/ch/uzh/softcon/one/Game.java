package ch.uzh.softcon.one;

import java.util.Scanner;
import ch.uzh.softcon.one.Turn.Status;

public class Game {
    private static Player activePlayer;
    private static boolean winStatus;
    private static boolean rematch;

    public static void main(String[] args) {
        winStatus = false;
        rematch = false;
        setActivePlayer(Player.RED);
        Board.initialize();
        String out = IOFormatter.formatOutput("Welcome to the Checkers Game. Player red may begin.", true,"Please enter your move: ");
        System.out.print(out);
        gameLoop();
    }

    private static void gameLoop() {
        while (!winStatus) {
            String player = "";
            if (getActivePlayer() == Player.RED) player = "Player red";
            else if (getActivePlayer() == Player.WHITE) player = "Player white";

            Scanner scn = new Scanner(System.in);
            String input = scn.nextLine();
            Turn turn = IOFormatter.formatInput(input, getActivePlayer());

            if (turn.status == Status.ILLEGAL_TURN) {
                String tmp = IOFormatter.formatOutput("", false, player + ": Invalid input. Please enter your move according to the following pattern: a1Xb2 ");
                System.out.println(tmp);
                continue;
            }

            Status status = TurnHandler.runTurnSequence(turn);

            if (!winStatus) {
                String out = "";
                if (status == Status.ILLEGAL_TURN) out = IOFormatter.formatOutput(player + ": Invalid move - please try again.", true, "Please enter a valid move: ");
                else if (status == Status.JUMP_REQUIRED) out = IOFormatter.formatOutput(player + ": Invalid move - a jump is required.", true, "Please enter a valid move: ");
                else if (status == Status.JUMP_AGAIN) out = IOFormatter.formatOutput(player + ": Another jump is required.", true, "Please enter your next move: ");
                else if (status == Status.COMPLETED) {
                    if (getActivePlayer() == Player.RED) {
                        setActivePlayer(Player.WHITE);
                        player = "Player white";
                    } else if (getActivePlayer() == Player.WHITE) {
                        setActivePlayer(Player.RED);
                        player = "Player red";
                    }
                    out = IOFormatter.formatOutput(player + ": It's your turn", true, "Please enter your move: ");
                }
                System.out.print(out);
            }
        }

        if (rematch) main(null);
    }

    private static void setActivePlayer(Player playerStatus) {
        activePlayer = playerStatus;
    }

    private static Player getActivePlayer() {
        return activePlayer;
    }

    protected static void win(Player player) {
        winStatus = true;

        String playerString = "";
        if (player == Player.RED) playerString = "Player red";
        else if (player == Player.WHITE) playerString = "Player white";

        String out = IOFormatter.formatOutput("Congratulations " + playerString + ", you won!", true,"Do you want a revenge? (y/n): ");
        System.out.print(out);

        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();

        if (input.equals("y")) rematch = true;
        else System.out.println("Game over.");
    }
}