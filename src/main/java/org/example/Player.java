package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final String name;
    private ArrayList<Deck.Card> hand = new ArrayList<>();
    private int shields;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.shields = 0;
    }

    public void receiveCard(Deck.Card card) {
        hand.add(card);
    }

    public void receiveEventCard(Deck.Card card) {
        if (Objects.equals(card.description, "Plague: The player who draws this card immediately loses 2 shields.")) {
            loseShields(2);
        }
    }

    public void loseShields(int amount) {
        shields -= amount;
        if (shields < 0) shields = 0;
    }

    public void addShields(int amount){
        shields += amount;
    }

    public int getShields() {
        return shields;
    }

    public int getHandSize() {
        return hand.size();
    }

    public String getName(){return name;}

    public ArrayList<Deck.Card> getHand() {
        return hand;
    }
}