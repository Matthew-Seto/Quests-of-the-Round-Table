package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Player {
    private final String name;
    private ArrayList<Deck.Card> hand = new ArrayList<>();
    private int shields;
    private final Game game;

    public Player(String name, Game game) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.shields = 0;
        this.game = game;
    }

    public void receiveCard(Deck.Card card) {
        hand.add(card);
        sortHand();
    }

    public void receiveEventCard(Deck.Card card) {
        if (Objects.equals(card.description, "Plague: The player who draws this card immediately loses 2 shields.")) {
            loseShields(2);
        }
        if (Objects.equals(card.description, "Queen's favor: The player who draws this card immediately draws 2 adventure cards.")) {
            game.drawAdventureCardsForPlayer(this, 2); // Draw 2 adventure cards
        }
        if (Objects.equals(card.description, "Prosperity: All players immediately draw 2 adventure cards.")) {
            game.drawAdventureCardsForAllPlayers( 2); // Draw 2 adventure cards for every player
        }
        if (card.type.contains("Q")) {
            game.QcardisDrawn();
        }
    }

    public int countFoeCards() {
        int count = 0;
        for (Deck.Card card : hand) {
            if (card.type.equals("F")) {
                count++;
            }
        }
        return count;
    }

    public void playAdventureCard(int index){
        hand.remove(index);
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

    public void sortHand() {
        hand.sort(new Comparator<Deck.Card>() {
            @Override
            public int compare(Deck.Card c1, Deck.Card c2) {
                if (c1.type.equals("F") && !c2.type.equals("F")) return -1;
                if (!c1.type.equals("F") && c2.type.equals("F")) return 1;

                int valueComparison = Integer.compare(c1.value, c2.value);
                if (valueComparison != 0) {
                    return valueComparison;
                }

                return c1.type.compareTo(c2.type);
            }
        });
    }

    public void setHand(ArrayList<Deck.Card> newHand) {
        this.hand = newHand;
        sortHand();
    }

    public void setCardInHand(int index, Deck.Card card){
        hand.set(index,card);
        sortHand();
    }
}