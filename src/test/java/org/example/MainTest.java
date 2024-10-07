package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    @DisplayName("Check Adventure deck size is 100")
    void RESP_01_test_01(){
        Deck advDeck = new Deck();
        advDeck.initializeAdventureDeck();
        int adventureDeckSize = advDeck.getAdventureDeckSize();
        assertEquals(100, adventureDeckSize);
    }

    @Test
    @DisplayName("Check Event deck size is 17")
    void RESP_01_test_02(){
        Deck eventDeck = new Deck();
        eventDeck.initializeEventDeck();
        int eventDeckSize = eventDeck.getEventDeckSize();
        assertEquals(17, eventDeckSize);
    }

    @Test
    @DisplayName("Distribute 12 cards to from the adventure deck to each player")
    void RESP_02_test_01(){
        Game game = new Game(4);
        game.distributeCards();

        for (Player player : game.getPlayers()) {
            assertEquals(12, player.getHandSize());
        }

        int expectedDeckSize = 100 - (4 * 12);
        assertEquals(expectedDeckSize, game.getAdventureDeck().getAdventureDeckSize());
    }

    //RESP_05 Test's will clump together each players hand this is just to test that turns are working in the correct order
    @Test
    @DisplayName("Test that P2 plays after P1")
    void RESP_05_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        game.gameStart(printWriter);
        printWriter.flush();
        System.out.println(output.toString());
        assertEquals("P1", game.getCurrentPlayer().getName());

        game.nextTurn(printWriter);
        printWriter.flush();
        System.out.println(output.toString());
        assertEquals("P2", game.getCurrentPlayer().getName());
    }

    @Test
    @DisplayName("Test that P3 plays after P2")
    void RESP_05_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        game.gameStart(printWriter);
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P2's turn
        System.out.println(output.toString());
        assertEquals("P2", game.getCurrentPlayer().getName());

        game.nextTurn(printWriter);
        System.out.println(output.toString());
        assertEquals("P3", game.getCurrentPlayer().getName());
    }

    @Test
    @DisplayName("Test that P4 plays after P3")
    void RESP_05_test_03() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        game.gameStart(printWriter);
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P2's turn
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P3's turn
        System.out.println(output.toString());
        assertEquals("P3", game.getCurrentPlayer().getName());

        game.nextTurn(printWriter);
        System.out.println(output.toString());
        assertEquals("P4", game.getCurrentPlayer().getName());
    }

    @Test
    @DisplayName("Test that P1 plays after P4")
    void RESP_05_test_04() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        game.gameStart(printWriter);
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P2's turn
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P3's turn
        System.out.println(output.toString());
        game.nextTurn(printWriter); // P4's turn
        System.out.println(output.toString());
        assertEquals("P4", game.getCurrentPlayer().getName());

        game.nextTurn(printWriter);
        System.out.println(output.toString());
        assertEquals("P1", game.getCurrentPlayer().getName());
    }

    @Test
    @DisplayName("Current Player draws Plague Event Card")
    void RESP_06_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);
        Player currentPlayer = game.getCurrentPlayer();

        game.givePlayerShields(currentPlayer, 4);

        game.overwriteEventDeckCard(0, "E", "Plague: The player who draws this card immediately loses 2 shields.");

        game.gameStart(printWriter);
        System.out.println(output.toString());

        assertEquals(2, currentPlayer.getShields(), "Player should have 2 shields after drawing Plague card");
    }

    @Test
    @DisplayName("Current Player draws Queen's Favour Event Card")
    void RESP_07_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);
        Player currentPlayer = game.getCurrentPlayer();
        // Use some cards so Queens' favour will work without trimming, later tests will test trimming hand
        currentPlayer.playAdventureCard(0);
        currentPlayer.playAdventureCard(0);
        currentPlayer.playAdventureCard(0);

        game.overwriteEventDeckCard(0, "E", "Queen's favor: The player who draws this card immediately draws 2 adventure cards.");

        game.gameStart(printWriter);
        System.out.println(output.toString());

        // Verify the player received 2 additional adventure cards
        assertEquals(11, currentPlayer.getHandSize());
    }
}