package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}