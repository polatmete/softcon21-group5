package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.exceptions.player.BrokeException;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.player.Player;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void initialize() {
        String input = "x\n1\n0\n2\n0\n3\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertDoesNotThrow(Game::initialize, "Game could not be initialized!");
    }

    @Test
    void takeNewPlayers() {
        String input = "x\n5\nmaettuu\npolatmete\nUnitTest3\nUnitTest4\nUnitTest5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{null, null, null, null, null};
            playersField.set(null, p);

            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));

            Method takeNewPlayersField = Game.class.getDeclaredMethod("takeNewPlayers", int.class);
            takeNewPlayersField.setAccessible(true);
            takeNewPlayersField.invoke(null, 5);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void startNewRound() {
        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, p);

            Field deckField = Game.class.getDeclaredField("deck");
            deckField.setAccessible(true);
            deckField.set(null, CardDeck.getInstance());
            CardDeck.getInstance().fillDeck(1);

            Field dealerField = Game.class.getDeclaredField("dealer");
            dealerField.setAccessible(true);
            dealerField.set(null, new Dealer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        Game.startNewRound();

        Player[] players = Game.getPlayers();
        Dealer dealer = Game.getDealer();

        for (Player p : players) {
            assertDoesNotThrow(() -> assertTrue(p == null || p.getHand(0).size() == 2),
                    "Player has not the right amount of cards");
        }
        assertDoesNotThrow(() -> assertEquals(2, dealer.getHand(0).size()),
                "Dealer has not the right amount of cards");
    }

    @Test
    void checkPlayerLeft() {
        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest"), null, null, null, null};
            playersField.set(null, p);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        assertTrue(Game.checkPlayerLeft());

        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{null, null, null, null, null};
            playersField.set(null, p);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        assertFalse(Game.checkPlayerLeft());
    }

    @Test
    void takeBets() {
        String input = "50\n100\n150\n20\n0\n10\n-50\nleave";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, p);

            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        for (int i = 0; i < 5; ++i) Game.takeBets(i);

        Player[] players = Game.getPlayers();
        assertEquals(50, players[0].getBalance());
        assertEquals(0, players[1].getBalance());
        assertEquals(80, players[2].getBalance());
        assertEquals(90, players[3].getBalance());
        assertNull(players[4]);
    }

    @Test
    void play() {
        String input = "x\n2\n1\n1\n2\n1\n1\n1\n2\n1\n1\n1\n2\n1\n1\n1\n1\n1\n1\n1\n1\n1\n0\n0\n0\n0\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, p);

            Field deckField = Game.class.getDeclaredField("deck");
            deckField.setAccessible(true);
            deckField.set(null, CardDeck.getInstance());
            CardDeck.getInstance().fillDeck(1);

            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertDoesNotThrow(() -> {for (int i = 0; i < 5; ++i) Game.play(i, 0);}, "Play could not be executed!");
    }

    @Test
    void playDealer() {
        try {
            Field deckField = Game.class.getDeclaredField("deck");
            deckField.setAccessible(true);
            deckField.set(null, CardDeck.getInstance());
            CardDeck.getInstance().fillDeck(1);

            Field dealerField = Game.class.getDeclaredField("dealer");
            dealerField.setAccessible(true);
            dealerField.set(null, new Dealer());
            Game.startNewRound();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertDoesNotThrow(() -> {Game.playDealer(); assertTrue(Game.getDealer().getHand(0).points() >= 17);},
                "Dealer could not be played!");
    }

    @Test
    void distributeCards() {
        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, p);

            Field deckField = Game.class.getDeclaredField("deck");
            deckField.setAccessible(true);
            deckField.set(null, CardDeck.getInstance());
            CardDeck.getInstance().fillDeck(1);

            Method takeNewPlayersField = Game.class.getDeclaredMethod("distributeCards", int.class, int.class);
            takeNewPlayersField.setAccessible(true);
            for (int i = 0; i < 5; ++i) takeNewPlayersField.invoke(null, i, 0);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void evaluate() {
        String input = "1\n1\n1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] players = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, players);

            Field betsField = Game.class.getDeclaredField("bets");
            betsField.setAccessible(true);
            Map<Player, Integer> bets = new HashMap<>();
            for (Player p : Game.getPlayers()) bets.put(p, new Random().nextInt(1, 101));
            betsField.set(null, bets);

            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        Game.play(0, 0);
        Game.playDealer();
        assertDoesNotThrow(Game::evaluate, "Evaluation could not be executed!");
    }

    @Test
    void conclude() {
        String input = "\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field playersField = Game.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] p = new Player[]{new Player("UnitTest1"), new Player("UnitTest2"),
                    new Player("UnitTest3"), new Player("UnitTest4"), new Player("UnitTest5")};
            playersField.set(null, p);

            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertDoesNotThrow(Game::conclude, "Conclude could not be played!");
        try {Game.getPlayers()[0].bet(Game.getPlayers()[0].getBalance());} catch (BrokeException e) {e.getMessage();}
        assertDoesNotThrow(Game::conclude, "Conclude could not be played!");
    }
}