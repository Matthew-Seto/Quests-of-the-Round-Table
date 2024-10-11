package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private final Deck advDeck;
    private final Deck eventDeck;
    private final ArrayList<Player> players;
    private int currentPlayerIndex;
    private boolean QcardDrawn;

    public Game(int numberOfPlayers) {
        this.players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("P" + i,this)); // Create players P1, P2, P3, ...
        }
        this.advDeck = new Deck();
        this.eventDeck = new Deck();
        this.advDeck.initializeAdventureDeck();
        this.eventDeck.initializeEventDeck();
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

    public void promptPlayer(Scanner input, PrintWriter output) {
        if (getCurrentPlayer().getHandSize() > 12){
            trimIfNeeded(input,output);
        }
        output.print("Make your move (to end turn hit <return>): ");
        output.flush();

        String inputStr = input.nextLine();
        if (inputStr.isEmpty()) {
            endCurrentPlayerTurn(output);
        }
        else{
        }
    }

    public void trimIfNeeded(Scanner input, PrintWriter output){
        Player player = getCurrentPlayer();
        final int maxHandSize = 12;
        if (player.getHandSize() > 12) {
            output.println("Your hand has exceeded the maximum size of " + maxHandSize + " cards!");
            output.println("You need to discard " + (player.getHandSize() - maxHandSize) + " card(s).");

            while (player.getHandSize() > maxHandSize) {
                displayCurrentPlayerHand(output);
                output.print("Select a card to discard (1-" + player.getHandSize() + "): ");
                output.flush();

                try {
                    int position = input.nextInt();
                    input.nextLine();

                    if (position > 0 && position <= player.getHandSize()) {
                        player.getHand().remove(position - 1);
                        output.println("Card discarded.");
                    } else {
                        output.println("Invalid position. Please try again.");
                    }
                } catch (Exception e) {
                    output.println("Invalid input. Please enter a number.");
                    input.nextLine();
                }
            }
        }
    }

    public void handleQuestSponsorship(Scanner input, PrintWriter output) {

    }

    private boolean promptForSponsorship(Player player, Scanner input, PrintWriter output) {
        return false;
    }

    public void endCurrentPlayerTurn(PrintWriter output) {
        output.println("Player " + getCurrentPlayer().getName() + "'s turn has ended.");
        flushDisplay(output);

        ArrayList<Player> winners = checkForWinners();
        if (!winners.isEmpty()) {
            for (Player winner : winners) {
                output.println("Player " + winner.getName() + " has won the game!");
            }
        } else {
            nextTurn(output);
        }
    }

    private ArrayList<Player> checkForWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }
        return winners;
    }

    public void flushDisplay(PrintWriter output) {
        for (int i = 0; i < 8; i++) {
            output.println();
        }
        output.flush();
    }

    public void gameStart(PrintWriter output) {
        displayCurrentPlayerHand(output);
        drawEventCard(getCurrentPlayer(), output);
    }

    public void displayCurrentPlayerHand(PrintWriter output) {
        Player currentPlayer = players.get(currentPlayerIndex);
        output.println(currentPlayer.getName() + "'s hand:");
        for (Deck.Card card : currentPlayer.getHand()) {
            output.print(card + " ");
        }
        output.println();
    }

    public void nextTurn(PrintWriter output) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        output.println(getCurrentPlayer().getName() + "'s turn");
        displayCurrentPlayerHand(output);
        drawEventCard(getCurrentPlayer(), output);
    }

    public void overwriteEventDeckCard(int index, String type, String description){
        Deck.Card e = new Deck.Card(type, description);
        eventDeck.setEventCard(index,e);
    }

    private void drawEventCard(Player player, PrintWriter output) {
        if (eventDeck.getEventDeckSize() > 0) {
            Deck.Card drawnCard = eventDeck.eventDeck.removeFirst();
            output.println(player.getName() + " drew an event card: " + drawnCard);
            output.flush();
            player.receiveEventCard(drawnCard);
            returnEventCardToBottom(drawnCard);
        } else {
            output.println("No more event cards to draw.");
        }
    }

    public Deck.Card drawAdventureCard() {
        if (advDeck.getAdventureDeckSize() > 0) {
            return advDeck.adventureDeck.removeFirst();
        }
        return null;
    }

    public void drawAdventureCardsForPlayer(Player player, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            Deck.Card card = drawAdventureCard();
            if (card != null) {
                player.receiveCard(card);
            }
        }
    }

    public void drawAdventureCardsForAllPlayers(int numberOfCards) {
        for (Player player : players) {
            drawAdventureCardsForPlayer(player, numberOfCards);
        }
    }

    private void returnEventCardToBottom(Deck.Card card) {
        eventDeck.eventDeck.add(card);
    }

    void givePlayerShields(Player player, int shields){
        player.addShields(shields);
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

    public void QcardisDrawn() {

    }
}