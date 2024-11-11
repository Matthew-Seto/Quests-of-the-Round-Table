package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public static class Card {
        String type;
        int value;
        String description;

        Card(String type, int value) {
            this.type = type;
            this.value = value;
        }

        Card(String type, String description) {
            this.type = type;
            this.description = description;
        }

        public String toString() {
            if (description != null) {
                return type + ": " + description;
            } else {
                return type + value;
            }
        }
    }

    ArrayList<Card> adventureDeck = new ArrayList<>();
    ArrayList<Card> eventDeck = new ArrayList<>();

    public void initializeAdventureDeck() {
        int[][] foes = {
                {5, 8}, {10, 7}, {15, 8}, {20, 7}, {25, 7}, {30, 4},
                {35, 4}, {40, 2}, {50, 2}, {70, 1}
        };

        for (int[] foe : foes) {
            int value = foe[0];
            int count = foe[1];

            for (int i = 0; i < count; i++) {
                Card card = new Card("F", value);
                adventureDeck.add(card);
            }
        }

        Object[][] weapons = {
                {"D", 5, 6}, {"H", 10, 12}, {"S", 10, 16},
                {"B", 15, 8}, {"L", 20, 6}, {"E", 30, 2}
        };

        for (Object[] weapon : weapons) {
            String letter = (String) weapon[0];
            int value = (int) weapon[1];
            int count = (int) weapon[2];

            for (int i = 0; i < count; i++) {
                Card card = new Card(letter, value);
                adventureDeck.add(card);
            }
        }
        Collections.shuffle(adventureDeck);
    }

    public void initializeEventDeck() {
        int[][] qCards = {
                {2, 3}, {3, 4}, {4, 3}, {5, 2}
        };

        for (int[] cards : qCards) {
            int value = cards[0];
            int count = cards[1];

            for (int i = 0; i < count; i++) {
                Card card = new Card("Q", value);
                eventDeck.add(card);
            }
        }

        // Adding event cards
        eventDeck.add(new Card("E", "Plague: The player who draws this card immediately loses 2 shields."));
        for (int i = 0; i < 2; i++) {
            eventDeck.add(new Card("E", "Queen's favor: The player who draws this card immediately draws 2 adventure cards."));
            eventDeck.add(new Card("E", "Prosperity: All players immediately draw 2 adventure cards."));
        }
        Collections.shuffle(eventDeck);
    }

    public void setEventCard(int index, Card c){
        eventDeck.set(index,c);
    }

    public void setAdventureCard(int index, Card c){
        adventureDeck.set(index,c);
    }

    public Card peekAtFirstECard(){
        return eventDeck.getFirst();
    }
    public int getAdventureDeckSize() {
        return adventureDeck.size();
    }

    public int getEventDeckSize() {
        return eventDeck.size();
    }
}