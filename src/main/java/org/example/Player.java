package org.example;

import java.util.ArrayList;

public class Player {
    private String name;
    private final ArrayList<Deck.Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void receiveCard(Deck.Card card) {
        hand.add(card);
    }

    public int getHandSize() {
        return hand.size();
    }

    public String getName(){return name;}

    public ArrayList<Deck.Card> getHand() {
        return hand;
    }
}
