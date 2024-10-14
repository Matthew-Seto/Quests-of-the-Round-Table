package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

        String input = "\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // Use some cards so prosperity will work without trimming, later tests will test trimming hand
        for (Player player : game.getPlayers()){
            player.playAdventureCard(0);
            player.playAdventureCard(0);
            player.playAdventureCard(0);
        }

        game.overwriteEventDeckCard(0, "E", "Prosperity: All players immediately draw 2 adventure cards.");
        game.overwriteEventDeckCard(1, "NULL", "");

        game.gameStart(printWriter);
        System.out.println(output.toString());

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

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

        String input = "1\n1\n1\n1\n1\n1\n1\n1\n\n";
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
        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);


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

        game.overwriteQuestEventDeckCard(0,"Q",1);

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
        assertTrue(outputContent.contains("No players sponsored the quest. The quest has ended."));
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

        String input = "yes\n1\n6\nquit\n1\n6\nquit\nyes\nyes\nyes\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        //rig P1 hand so that they are eligible
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 5));
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 20));
        testHand.add(new Deck.Card("F", 25));
        testHand.add(new Deck.Card("F", 35));
        testHand.add(new Deck.Card("D", 5));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("B", 15));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

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

        String input = "no\nyes\n1\n6\nquit\n1\n6\n1\nquit\nyes\nyes\n1\n1\n1\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make p1 eligible but p1 declines sponsorship
        game.getPlayers().get(0).setCardInHand(0, new Deck.Card("F", 10));
        game.getPlayers().get(0).setCardInHand(0, new Deck.Card("F", 10));

        //rig P2 hand so that they are eligible
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 10));
        testHand.add(new Deck.Card("F", 15));
        testHand.add(new Deck.Card("F", 15));
        testHand.add(new Deck.Card("F", 20));
        testHand.add(new Deck.Card("F", 25));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("S", 10));
        testHand.add(new Deck.Card("B", 15));
        testHand.add(new Deck.Card("B", 15));
        testHand.add(new Deck.Card("L", 20));
        testHand.add(new Deck.Card("L", 20));

        game.getPlayers().get(1).setHand(testHand);

        game.overwriteQuestEventDeckCard(0,"Q",2);
        game.overwriteEventDeckCard(1,"NULL","");

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

        String input = "yes\n1\n6\nquit\n1\n6\nquit\nyes\nyes\n1\n1\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // set p1's hand to one foe card
        ArrayList<Deck.Card> testHand = new ArrayList<>();
        testHand.add(new Deck.Card("F", 10));

        game.getPlayers().get(0).setHand(testHand);

        ArrayList<Deck.Card> p2Hand = new ArrayList<>();
        p2Hand.add(new Deck.Card("F", 5));
        p2Hand.add(new Deck.Card("F", 10));
        p2Hand.add(new Deck.Card("F", 15));
        p2Hand.add(new Deck.Card("D", 5));
        p2Hand.add(new Deck.Card("D", 5));
        p2Hand.add(new Deck.Card("D", 5));
        p2Hand.add(new Deck.Card("H", 10));
        p2Hand.add(new Deck.Card("S", 10));
        p2Hand.add(new Deck.Card("S", 10));
        p2Hand.add(new Deck.Card("S", 10));
        p2Hand.add(new Deck.Card("L", 20));
        p2Hand.add(new Deck.Card("E", 30));

        game.getPlayers().get(1).setHand(p2Hand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

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

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nyes\nyes\nyes\n1\n1\n";
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

        game.overwriteQuestEventDeckCard(0,"Q",2);

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

        String input = "no\nyes\n1\n6\nQuit\n1\n6\nquit\nyes\nyes\nyes\n1\n1\n\n";
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

        game.overwriteQuestEventDeckCard(0,"Q",2);

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

    @Test
    @DisplayName("Test to check input validation")
    void RESP_16_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        // test entering two foes and repeating the same weapon
        String input = "yes\n13\n1\n1\n6\n5\nQuit\n1\n6\nquit\nyes\nyes\nyes\n1\n1\n";
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

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P1 has chosen to sponsor the quest."));
        assertTrue(outputContent.contains("Stage 1 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Invalid position. Please try again"));
        assertTrue(outputContent.contains("Two foes cannot be on the same stage"));
        assertTrue(outputContent.contains("The same weapon cannot be selected twice in one stage"));
    }

    @Test
    @DisplayName("Empty Stage test")
    void RESP_17_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\nQuit\nquit\n1\n6\nQuit\n1\n6\nquit\nyes\nyes\nyes\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

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

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P1 has chosen to sponsor the quest."));
        assertTrue(outputContent.contains("Stage 1 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("A stage cannot be empty."));
    }

    @Test
    @DisplayName("Insufficient value for the stages check")
    void RESP_18_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n9\nQuit\n1\n6\nquit\n6\nquit\nyes\nyes\nyes\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

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

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        assertTrue(outputContent.contains("P1, do you want to sponsor the quest? (yes/no)"));
        assertTrue(outputContent.contains("P1 has chosen to sponsor the quest."));
        assertTrue(outputContent.contains("Stage 1 - Enter the position of the next card to include in this stage or 'Quit' to end:"));
        assertTrue(outputContent.contains("Insufficient value for this stage."));
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and sets it up, remove ineligible players, draw cards for participants")
    void RESP_19_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nno\n1\nno\n1\n12\nquit\n12\nquit\nyes\nyes\n1\n1\n";
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // check that trim and asking for participation is working
        assertTrue(outputContent.contains("P3, do you want to withdraw from the quest? (yes/no): "));
        assertTrue(outputContent.contains("P4, do you want to withdraw from the quest? (yes/no): "));
        assertTrue(outputContent.contains("P3's hand:"));
        assertTrue(outputContent.contains("P4's hand:"));
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and sets it up, no players want to participate")
    void RESP_20_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nyes\nyes\n1\n1\n";
        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make sure 2nd card is not prosperity this will change p1 hand size.
        game.overwriteEventDeckCard(1,"Q3", "");
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        Scanner scanner = new Scanner(input);
        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // make sure p1 has hand size 12 after getting new cards and trimming
        assertEquals(12, game.getPlayers().get(0).getHandSize());
        assertTrue(outputContent.contains("No participants for the current stage. The quest ends."));
        assertTrue(outputContent.contains("P1's hand:"));
        assertTrue(outputContent.contains("Player P1's turn has ended."));
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and sets it up, P3 and P4 want to participate and start setting up attacks")
    void RESP_21_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nno\nno\n1\n1\n12\nquit\n12\nquit\nyes\nyes\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make sure 2nd card is not prosperity this will change p1 hand size.
        game.overwriteEventDeckCard(1,"Q3", "");
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);

        game.getPlayers().get(0).setHand(testHand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // make sure p1 has hand size 12 after getting new cards and trimming
        assertEquals(12, game.getPlayers().get(0).getHandSize());
        assertTrue(outputContent.contains("No participants for the current stage. The quest ends."));
        assertTrue(outputContent.contains("Enter the position of the next card to include in the attack or 'quit' to end:"));
        assertTrue(outputContent.contains("Attack set with cards:"));
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and sets it up, P3 and P4 want to participate, sets up attacks and resolves them, both players lose first stage")
    void RESP_22_test_01() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nno\nno\n4\nquit\n4\nquit\n1\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make sure 2nd card is not prosperity this will change p1 hand size.
        game.overwriteEventDeckCard(1,"Q3", "");
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);
        game.getPlayers().get(0).setHand(testHand);

        // give p3 set hand so they will lose the stage and don't have to trim
        ArrayList<Deck.Card> p3badhand = new ArrayList<>();
        p3badhand.add(new Deck.Card("F", 5));
        p3badhand.add(new Deck.Card("F", 10));
        p3badhand.add(new Deck.Card("D", 5));
        p3badhand.add(new Deck.Card("D", 5));
        p3badhand.add(new Deck.Card("D", 5));
        p3badhand.add(new Deck.Card("D", 5));

        game.getPlayers().get(2).setHand(p3badhand);

        // same for p4
        ArrayList<Deck.Card> p4badhand = new ArrayList<>();
        p4badhand.add(new Deck.Card("F", 5));
        p4badhand.add(new Deck.Card("F", 10));
        p4badhand.add(new Deck.Card("D", 5));
        p4badhand.add(new Deck.Card("D", 5));
        p4badhand.add(new Deck.Card("D", 5));
        p4badhand.add(new Deck.Card("D", 5));

        game.getPlayers().get(3).setHand(p4badhand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // make sure p1 has hand size 12 after getting new cards and trimming
        assertEquals(12, game.getPlayers().get(0).getHandSize());
        assertTrue(outputContent.contains("No participants for the current stage. The quest ends."));
        assertTrue(outputContent.contains("Enter the position of the next card to include in the attack or 'quit' to end:"));
        assertTrue(outputContent.contains("Attack set with cards:"));
        assertEquals(6, game.getPlayers().get(2).getHandSize());
    }

    @Test
    @DisplayName("P1 decides to sponsor the quest and sets it up, P3 and P4 want to participate, sets up attacks and resolves them, both players win first stage leave second stage")
    void RESP_22_test_02() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nno\nno\n6\nquit\n6\nquit\nyes\nyes\n1\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make sure 2nd card is not prosperity this will change p1 hand size.
        game.overwriteEventDeckCard(1,"Q3", "");
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);
        game.getPlayers().get(0).setHand(testHand);

        // give p3 set hand so they will lose the stage and don't have to trim
        ArrayList<Deck.Card> p3badhand = new ArrayList<>();
        p3badhand.add(new Deck.Card("F", 5));
        p3badhand.add(new Deck.Card("F", 10));
        p3badhand.add(new Deck.Card("L", 20));
        p3badhand.add(new Deck.Card("L", 20));
        p3badhand.add(new Deck.Card("D", 5));
        p3badhand.add(new Deck.Card("D", 5));

        game.getPlayers().get(2).setHand(p3badhand);

        // same for p4
        ArrayList<Deck.Card> p4badhand = new ArrayList<>();
        p4badhand.add(new Deck.Card("F", 5));
        p4badhand.add(new Deck.Card("F", 10));
        p4badhand.add(new Deck.Card("L", 20));
        p4badhand.add(new Deck.Card("L", 20));
        p4badhand.add(new Deck.Card("D", 5));
        p4badhand.add(new Deck.Card("D", 5));

        game.getPlayers().get(3).setHand(p4badhand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // make sure p1 has hand size 12 after getting new cards and trimming
        assertEquals(12, game.getPlayers().get(0).getHandSize());
        assertTrue(outputContent.contains("No participants for the current stage. The quest ends."));
        assertTrue(outputContent.contains("Enter the position of the next card to include in the attack or 'quit' to end:"));
        assertTrue(outputContent.contains("Attack set with cards:"));
        assertEquals(6, game.getPlayers().get(2).getHandSize());
    }

    @Test
    @DisplayName("^^^^^ p3 wins second stage and gets shields")
    void RESP_22_test_03() {
        Game game = new Game(4);
        game.distributeCards();

        String input = "yes\n1\n6\nQuit\n1\n6\nquit\nno\nno\n6\nquit\n6\nquit\nno\nyes\n6\nquit\n1\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        StringWriter output = new StringWriter();
        PrintWriter printWriter = new PrintWriter(output);

        // make sure 2nd card is not prosperity this will change p1 hand size.
        game.overwriteEventDeckCard(1, "Q3", "");
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

        ArrayList<Deck.Card> p2badhand = new ArrayList<>();
        p2badhand.add(new Deck.Card("F", 5));
        // make p2 ineligible
        game.getPlayers().get(1).setHand(p2badhand);
        game.getPlayers().get(0).setHand(testHand);

        // give p3 set hand so they will lose the stage and don't have to trim
        ArrayList<Deck.Card> p3badhand = new ArrayList<>();
        p3badhand.add(new Deck.Card("F", 5));
        p3badhand.add(new Deck.Card("F", 10));
        p3badhand.add(new Deck.Card("L", 20));
        p3badhand.add(new Deck.Card("L", 20));
        p3badhand.add(new Deck.Card("E", 30));
        p3badhand.add(new Deck.Card("E", 30));

        game.getPlayers().get(2).setHand(p3badhand);

        // same for p4
        ArrayList<Deck.Card> p4badhand = new ArrayList<>();
        p4badhand.add(new Deck.Card("F", 5));
        p4badhand.add(new Deck.Card("F", 10));
        p4badhand.add(new Deck.Card("L", 20));
        p4badhand.add(new Deck.Card("L", 20));
        p4badhand.add(new Deck.Card("D", 5));
        p4badhand.add(new Deck.Card("D", 5));

        game.getPlayers().get(3).setHand(p4badhand);

        game.overwriteQuestEventDeckCard(0,"Q",2);

        game.gameStart(printWriter);

        game.promptPlayer(scanner, printWriter);

        printWriter.flush();

        String outputContent = output.toString();
        System.out.println(outputContent);

        // make sure p1 has hand size 12 after getting new cards and trimming
        assertEquals(12, game.getPlayers().get(0).getHandSize());
        assertTrue(outputContent.contains("Enter the position of the next card to include in the attack or 'quit' to end:"));
        assertTrue(outputContent.contains("Attack set with cards:"));
        assertEquals(6, game.getPlayers().get(2).getHandSize());
        assertEquals(2, game.getPlayers().get(2).getShields());
    }
}