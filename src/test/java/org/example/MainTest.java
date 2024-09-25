package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}