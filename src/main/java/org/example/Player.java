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

    }

    public void loseShields(int amount) {

    }

    public void addShields(int amount){

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