package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;
import ch.uzh.softcon.four.player.Player;

public class IOFormatter {

    public static String formatOutput(String textBeforeTable, boolean printTable, String textAfterTable) { // Concatenate text with Table layout
        StringBuilder out = new StringBuilder();
        if (!textBeforeTable.equals("")) out.append(textBeforeTable).append("\n");

        // If table has to be printed generate table
        if (printTable) {
            out.append("""
                    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
                    ┃ ┌───┐   [0] Stay                                     ┌───┐ ┃
                    ┃ │ X │   [1] Hit           Dealer                     │133│ ┃
                    ┃ └───┘   [2] Split         XX  XX                     └───┘ ┃
                    ┃                                                            ┃
                    """)
                    .append(moneyRow())
                    .append(nameRow())
                    .append(cardRows())
                    .append("""
                    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
                    """);
        }

        out.append(textAfterTable);
        return out.toString();
    }

    public static String formatErrorMessage(String errorMessage) { // Color the error messages in red
        final String BRIGHT_RED = "\u001B[91m";
        final String RESET = "\u001B[0m";
        return BRIGHT_RED + errorMessage + RESET;
    }

    //TODO: dealer
    private static String dealerCards() {
        StringBuilder row = new StringBuilder();
        row.append(" ");
        return row.toString();
    }

    private static String moneyRow() {
        StringBuilder row = new StringBuilder();

        row.append("┃ ");
        for (int i = 0; i < Game.getPlayers().length; i++) {
            if (Game.getPlayers()[i] != null) {
                appendBalance(row, Game.getPlayers()[i].getBalance());
            } else {
                row.append(" ".repeat(10));
            }
            if (i < Game.getPlayers().length - 1) {
                row.append(" ".repeat(2));
            }
        }
        row.append(" ┃\n");

        return row.toString();
    }

    private static String nameRow() {
        StringBuilder row = new StringBuilder();

        row.append("┃ ");
        for (int i = 0; i < Game.getPlayers().length; i++) {
            if (Game.getPlayers()[i] != null) {
                appendName(row, Game.getPlayers()[i].getName());
            } else {
                row.append("  No P").append(i + 1).append("!  ");
            }
            if (i < Game.getPlayers().length - 1) {
                row.append(" ".repeat(2));
            }
        }
        row.append(" ┃\n");

        return row.toString();
    }

    private static String cardRows() {
        int mostCardsOnHand = 0;
        for (Player p : Game.getPlayers()) {
            if (p == null) {
                continue;
            }
            for (int i = 0; i < p.amountHands(); i++) {
                try {
                    if (p.getHand(i).size() > mostCardsOnHand) {
                        mostCardsOnHand = p.getHand(i).size();
                    }
                } catch (NullHandException e) {System.err.println(e.getMessage());}
            }
        }

        int i = 0;
        StringBuilder row = new StringBuilder();
        while (i < Math.max(3, mostCardsOnHand)) {

            row.append("┃");
            for (int j = 0; j < Game.getPlayers().length; j++) {
                if (Game.getPlayers()[j] != null) {
                    appendCards(row, i, Game.getPlayers()[j]);
                } else {
                    if (i == 0) {
                        row.append("    ╭─╮     ");
                    } else if (i == 1) {
                        row.append("    ╰─╯     ");
                    } else {
                        row.append("            ");
                    }
                }
            }
            row.append("┃");
            //if (i < Math.max(3, mostCardsOnHand) - 1)
            row.append("\n");

            i++;
        }
        return row.toString();
    }

    private static void appendName(StringBuilder row, String playerName) {
        row.append(" ".repeat((int)Math.ceil((float)(9 - playerName.length()) / 2)));
        row.append(playerName).append(":");
        row.append(" ".repeat((int)Math.floor((float)(9 - playerName.length()) / 2)));
    }

    private static void appendBalance(StringBuilder row, int balance) {
        row.append(" ".repeat((int)Math.ceil((float)(9 - String.valueOf(balance).length()) / 2)));
        row.append("$").append(balance);
        row.append(" ".repeat((int)Math.floor((float)(9 - String.valueOf(balance).length()) / 2)));
    }
    //  1         2         3         4
    //5-2-5 , 3.5-5-3.5 , 2-8-2 , 0.5-11-0.5
    private static void appendCards(StringBuilder row, int cardIdx, Player p) {
        row.append(" ".repeat((int)Math.ceil(6.5 - 1.5 * p.amountHands())));
        for (int i = 0; i < p.amountHands(); i++) {
            try {
                row.append(p.getHand(i).getCard(cardIdx).getRank().getValue());
                row.append(p.getHand(i).getCard(cardIdx).getSuit().getLetter());
            } catch (NullCardException | NullHandException | CardHiddenException e){
                row.append(" ".repeat(2));
            }
            if (i < p.amountHands() - 1) {
                row.append(" ");
            }
        }
        row.append(" ".repeat((int)Math.floor(6.5 - 1.5 * p.amountHands())));
    }
}
