package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

    @Test
    @DisplayName("Current Player draws Prosperity Event Card")
    void RESP_08_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // Use some cards so Queens' favour will work without trimming, later tests will test trimming hand
        for (Player player : game.getPlayers()){
            player.playAdventureCard(0);
            player.playAdventureCard(0);
            player.playAdventureCard(0);
        }

        game.overwriteEventDeckCard(0, "E", "Prosperity: All players immediately draw 2 adventure cards.");

        game.gameStart(printWriter);
        System.out.println(output.toString());

        // Verify the players received 2 additional adventure cards
        for (Player player : game.getPlayers()){
            assertEquals(11, player.getHandSize());
        }
    }

    @Test
    @DisplayName("Tests turn ending")
    void RESP_09_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);
        // DUMMY CARD
        game.overwriteEventDeckCard(0,"NULL","");
        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("Player P1's turn has ended."));
        assertTrue(outputContent.contains("P2's turn"));
    }

    @Test
    @DisplayName("At the end of a turn the game checks if players have won")
    void RESP_10_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);
        //Give player 1 7 shields
        game.givePlayerShields(game.getCurrentPlayer(), 7);
        // DUMMY CARD
        game.overwriteEventDeckCard(0,"NULL","");
        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("Player P1 has won the game!"));
    }

    @Test
    @DisplayName("Two Winners")
    void RESP_10_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);
        //Give player 1 and player 2 7 shields
        game.givePlayerShields(game.getCurrentPlayer(), 7);
        game.givePlayerShields(game.getPlayers().get(1), 7);
        // DUMMY CARD
        game.overwriteEventDeckCard(0,"NULL","");
        game.gameStart(printWriter);
        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("Player P1 has won the game!"));
        assertTrue(outputContent.contains("Player P2 has won the game!"));
    }

    @Test
    @DisplayName("Tests trimming of hand")
    void RESP_11_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "1\n1\n\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // rig the deck
        game.overwriteEventDeckCard(0, "E", "Queen's favor: The player who draws this card immediately draws 2 adventure cards.");
        game.overwriteEventDeckCard(1, "Q", "2");

        game.gameStart(printWriter);

        try (Scanner scanner = new Scanner(input)) {
            game.promptPlayer(scanner, printWriter);
        }

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertEquals(12, game.getPlayers().getFirst().getHandSize());
    }

    @Test
    @DisplayName("Tests trimming of hand for all players")
    void RESP_11_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "1\n1\n\n1\n1\n\n1\n1\n\n1\n1\n\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        //rig the deck so no more draw Event cards are pulled
        game.overwriteEventDeckCard(0, "E", "Prosperity: All players immediately draw 2 adventure cards.");
        // DUMMY CARD
        game.overwriteEventDeckCard(1,"NULL","");
        game.overwriteEventDeckCard(2,"NULL","");
        game.overwriteEventDeckCard(3,"NULL","");
        game.overwriteEventDeckCard(4,"NULL","");


        game.gameStart(printWriter);

        try (Scanner scanner = new Scanner(input)) {
            for (int i = 0; i < game.getPlayers().size(); i++) {
                game.promptPlayer(scanner, printWriter);
            }
        }

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        for (Player player : game.getPlayers()) {
            assertEquals(12, player.getHandSize(), "Player " + player.getName() + " does not have the correct hand size.");
        }
    }

    @Test
    @DisplayName("Q Card drawn, all players decline")
    void RESP_12_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "no\nno\nno\nno\n\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        game.overwriteEventDeckCard(0,"Q3","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P2, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P3, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P4, do you want to sponsor the quest? (yes/no)"));
    }

    @Test
    // I watched the demo lecture again and realized the prof said this is needed
    @DisplayName("Check hands are displayed in sorted order and sorted")
    void RESP_13_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 5));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("F", 30));
        testHand.add(new Deck.Card("F", 35));
        testHand.add(new Deck.Card("F", 20));
        testHand.add(new Deck.Card("H", 10));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));

        game.getPlayers().get(0).setHand(testHand);

        ArrayList<Deck.Card> expectedHand = new ArrayList<>();
        expectedHand.add(new Deck.Card("F", 5));
        expectedHand.add(new Deck.Card("F", 10));
        expectedHand.add(new Deck.Card("F", 20));
        expectedHand.add(new Deck.Card("F", 30));
        expectedHand.add(new Deck.Card("F", 35));
        expectedHand.add(new Deck.Card("D", 5));
        expectedHand.add(new Deck.Card("D", 5));
        expectedHand.add(new Deck.Card("H", 10));
        expectedHand.add(new Deck.Card("S", 10));
        expectedHand.add(new Deck.Card("L", 20));
        expectedHand.add(new Deck.Card("L", 20));
        expectedHand.add(new Deck.Card("L", 20));

        assertEquals(expectedHand.toString(), game.getPlayers().get(0).getHand().toString());
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and is eligible")
    void RESP_14_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        //rig P1 hand so that they are eligible
        game.getPlayers().get(0).setCardInHand(0, new Deck.Card("F", 10));
        game.getPlayers().get(0).setCardInHand(1, new Deck.Card("F", 10));

        game.overwriteEventDeckCard(0,"Q2","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P1 has chosen to sponsor the quest."));
    }

    @Test
    @DisplayName("P2 decides to sponsor the quest and is eligible")
    void RESP_14_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "no\nyes\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        //rig P2 hand so that they are eligible
        game.getPlayers().get(1).setCardInHand(0, new Deck.Card("F", 10));
        game.getPlayers().get(1).setCardInHand(1, new Deck.Card("F", 10));

        game.overwriteEventDeckCard(0,"Q2","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P2, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P2 has chosen to sponsor the quest."));
    }

    @Test
    @DisplayName("P1 is ineligible to sponsor quest skip the player")
    void RESP_14_test_03() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "no\nyes\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // set p1's hand to one foe card
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 10));

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteEventDeckCard(0,"Q2","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1 does not have enough 'Foe' cards to sponsor the quest."));
        assertTrue(outputContent.contains("P1 declines to sponsor the quest."));
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and starts the setup")
    void RESP_15_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // give p1 set hand
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 5));
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 20));
        testHand.add(new Deck.Card("F", 30));
        testHand.add(new Deck.Card("F", 35));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("H", 10));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteEventDeckCard(0,"Q2","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P1 has chosen to sponsor the quest."));
        assertTrue(outputContent.contains("Stage 1 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Stage 1 set with cards: [F5, D5]"));
        assertTrue(outputContent.contains("Stage 2 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Stage 2 set with cards: [F10, S10]"));
    }

    @Test
    @DisplayName("P2 decides to sponsor the quest and starts the setup")
    void RESP_15_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "no\nyes\n1\n6\nQuit\n1\n6\nquit";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // give p2 set hand
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 5));
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 20));
        testHand.add(new Deck.Card("F", 30));
        testHand.add(new Deck.Card("F", 35));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("H", 10));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));

        game.getPlayers().get(1).setHand(testHand);

        game.overwriteEventDeckCard(0,"Q2","");

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P2, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P2 has chosen to sponsor the quest."));
        assertTrue(outputContent.contains("Stage 1 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Stage 1 set with cards: [F5, D5]"));
        assertTrue(outputContent.contains("Stage 2 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Stage 2 set with cards: [F10, S10]"));
    }
}