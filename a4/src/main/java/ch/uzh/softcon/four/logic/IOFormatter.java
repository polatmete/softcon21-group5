package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.Hand;
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
            ┃ │ X │   [1] Hit           Dealer:                    │""").append(deckAmount()).append("│ ┃\n").append("""
            ┃ └───┘   [2] Split \040""").append(dealerCards()).append("              └───┘ ┃\n").append("""
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

    private static String deckAmount() {
        StringBuilder row = new StringBuilder();
        float sideLength = (3 - (float)String.valueOf(CardDeck.getInstance().size()).length()) / 2;

        row.append(" ".repeat((int)Math.ceil(sideLength)));
        row.append(CardDeck.getInstance().size());
        row.append(" ".repeat((int)Math.floor(sideLength)));

        return row.toString();
    }

    private static String dealerCards() {
        Hand hand = new Hand();
        try {
            hand = Game.getDealer().getHand(0);
        } catch (NullHandException e) {System.err.println(e.getMessage());}
        StringBuilder row = new StringBuilder();
        float sideLength = (float) (10.5 - 1.5 * hand.size());
        if (hand.size() == 0) {
            sideLength = 10;
        }
        for (int i = 0; i < hand.size(); i++) {
            try {
                if (hand.getCard(i).getRank().getValue() > 9) {
                    sideLength -= 0.5;
                }
            } catch (CardHiddenException | NullCardException e) {/**/}
        }

        row.append(" ".repeat((int)Math.ceil(sideLength)));
        for (int i = 0; i < hand.size(); i++) {
            try {
                Card card = hand.getCard(i);
                if (card.getRank().getValue() == 1) {
                    row.append("A");
                } else {
                    row.append(card.getRank().getValue());
                }
                row.append(card.getSuit().getLetter());
            } catch (CardHiddenException e) {
                row.append("XX");
            } catch (NullCardException e) {
                row.append(" ".repeat(2));
            }
            if (i < hand.size() - 1) {
                row.append(" ");
            }
        }
        row.append(" ".repeat((int)Math.floor(sideLength)));

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
            row.append("┃\n");

            i++;
        }
        return row.toString();
    }

    private static void appendName(StringBuilder row, String playerName) {
        float sideLength = (9 - (float)playerName.length()) / 2;
        row.append(" ".repeat((int)Math.ceil(sideLength)));
        row.append(playerName).append(":");
        row.append(" ".repeat((int)Math.floor(sideLength)));
    }

    private static void appendBalance(StringBuilder row, int balance) {
        float sideLength = (9 - (float)String.valueOf(balance).length()) / 2;
        row.append(" ".repeat((int)Math.ceil(sideLength)));
        row.append("$").append(balance);
        row.append(" ".repeat((int)Math.floor(sideLength)));
    }

    private static void appendCards(StringBuilder row, int cardIdx, Player p) {
        float sideLength = (float) (6.5 - 1.5 * p.amountHands());

        row.append(" ".repeat((int)Math.ceil(sideLength)));
        for (int i = 0; i < p.amountHands(); i++) {
            try {
                Card card = p.getHand(i).getCard(cardIdx);
                if (card.getRank().getValue() > 9) {
                    row.setLength(row.length() - 1);
                }
                if (card.getRank().getValue() == 1) {
                    row.append("A");
                } else {
                    row.append(card.getRank().getValue());
                }
                row.append(card.getSuit().getLetter());
            } catch (NullCardException | NullHandException | CardHiddenException e){
                row.append(" ".repeat(2));
            }
            if (i < p.amountHands() - 1) {
                row.append(" ");
            }
        }
        row.append(" ".repeat((int)Math.floor(sideLength)));
    }
}