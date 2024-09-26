package org.example;

import java.util.ArrayList;

public class Game {
    private final Deck advDeck;
    private final ArrayList<Player> players;

    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("P" + i)); // Create players P1, P2, P3, ...
        }
        this.advDeck = new Deck();
        Deck eventDeck = new Deck();
        this.advDeck.initializeAdventureDeck();
        eventDeck.initializeEventDeck();
    }

    public void distributeCards() {
        for (Player player : players) {
            for (int i = 0; i < 12; i++) {
                if (advDeck.getAdventureDeckSize() > 0) {
                    player.receiveCard(advDeck.adventureDeck.removeFirst());
                }
            }
        }
    }

    public Deck getAdventureDeck() {
        return advDeck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}