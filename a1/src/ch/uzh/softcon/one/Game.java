package ch.uzh.softcon.one;

import java.util.Scanner;
import ch.uzh.softcon.one.Turn.Status;

public class Game {
    private static Player activePlayer;
    private static boolean winStatus;
    private static boolean rematch;
    private static boolean multiJump;

    public static void main(String[] args) { // Setup and initialize game
        winStatus = false;
        rematch = false;
        multiJump = false;
        setActivePlayer(Player.RED);
        Board.initialize();
        String out = IOFormatter.formatOutput("Welcome to the Checkers Game. Player red may begin.",
                true,"Please enter your move: ");
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
                String tmp = IOFormatter.formatOutput("", false,
                        player + ": Invalid input. Please enter your move according to the following pattern: [a1]X[b2]");
                System.out.println(tmp);
                continue;
            }

            if (multiJump) {
                turn.status = Status.JUMP_AGAIN;
            }
            Status status = TurnHandler.runTurnSequence(turn);

            if (!winStatus) {
                String out = "";
                if (status == Status.ILLEGAL_TURN)
                    out = IOFormatter.formatOutput(player + ": Invalid move - please try again.",
                            true, "Please enter a valid move: ");
                else if (status == Status.JUMP_REQUIRED)
                    out = IOFormatter.formatOutput(player + ": Invalid move - a jump is required.",
                            true, "Please enter a valid move: ");
                else if (status == Status.JUMP_AGAIN) {
                    multiJump = true;
                    out = IOFormatter.formatOutput(player + ": Another jump is required.",
                            true, "Please enter your next move: ");
                    }
                else if (status == Status.COMPLETED) {
                    multiJump = false;
                    if (getActivePlayer() == Player.RED) {
                        setActivePlayer(Player.WHITE);
                        player = "Player white";
                    } else if (getActivePlayer() == Player.WHITE) {
                        setActivePlayer(Player.RED);
                        player = "Player red";
                    }
                    out = IOFormatter.formatOutput(player + ": It's your turn",
                            true, "Please enter your move: ");
                }
                System.out.print(out);
            }
        }

        if (rematch) main(null);
    }

    private static void setActivePlayer(Player playerStatus) {
        activePlayer = playerStatus;
    }

    public static Player getActivePlayer() {
        return activePlayer;
    }

    public static void win(Player player) {
        winStatus = true;

        String playerString = "";
        if (player == Player.RED) playerString = "Player red";
        else if (player == Player.WHITE) playerString = "Player white";

        String out = IOFormatter.formatOutput("Congratulations " + playerString + ", you won!",
                true,"Do you want a revenge? (y|n): ");
        System.out.print(out);

        boolean acceptedAnswer = false;


        Scanner scn = new Scanner(System.in);
        String input;
        do {
            input = scn.nextLine().toLowerCase();
            if (input.equals("y")) {
                rematch = true;
                System.out.print("\u001B[101m                                                         \u001B[0m\n\n");
            } else if (input.equals("n")) System.out.println("Game over.");
            else {
                System.out.print("\nInput not recognized. Please type \"y\" or \"n\" and press enter.\nDo you want a revenge? (y|n): ");
            }
        } while (!input.equals("y") && !input.equals("n"));
    }
}