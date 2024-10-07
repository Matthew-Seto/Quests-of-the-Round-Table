package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private final Deck advDeck;
    private final ArrayList<Player> players;
    private int currentPlayerIndex;

    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("P" + i)); // Create players P1, P2, P3, ...
        }
        this.advDeck = new Deck();
        Deck eventDeck = new Deck();
        this.advDeck.initializeAdventureDeck();
        eventDeck.initializeEventDeck();
        this.currentPlayerIndex = 0;
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

    public void gameStart(PrintWriter output) {
        displayCurrentPlayerHand(output);
    }

    public void displayCurrentPlayerHand(PrintWriter output) {
        Player currentPlayer = players.get(currentPlayerIndex);
        output.println(currentPlayer.getName() + "'s turn");
        output.println(currentPlayer.getName() + "'s hand:");
        for (Deck.Card card : currentPlayer.getHand()) {
            output.print(card + " ");
        }
        output.println();
    }

    public void nextTurn(PrintWriter output) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        displayCurrentPlayerHand(output);
    }


    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    };

    public Deck getAdventureDeck() {
        return advDeck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}