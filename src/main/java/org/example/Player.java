package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Player {
    private final String name;
    private ArrayList<Deck.Card> hand = new ArrayList<>();
    private int shields;
    private final Game game;
    private ArrayList<Deck.Card> attack;

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

    public void displayHand(PrintWriter output){
        output.println(this.getName() + "'s hand:");
        for (Deck.Card card : this.getHand()) {
            output.print(card + " ");
        }
        output.println();
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

    public void setupAttack(Scanner input, PrintWriter output) {
        ArrayList<Deck.Card> attack = new ArrayList<>();
        while (true) {
            displayHand(output);
            output.print("Enter the position of the next card to include in the attack or 'quit' to end: ");
            output.flush();

            String response = input.nextLine().trim().toLowerCase();
            if (response.equals("quit")) {
                if (!attack.isEmpty()) {
                    output.println("Attack set with cards: " + attack);
                } else {
                    output.println("No cards selected for the attack.");
                }
                break;
            } else {
                try {
                    int position = Integer.parseInt(response) - 1;
                    if (position >= 0 && position < getHandSize()) {
                        Deck.Card selectedCard = hand.get(position);
                        if (isValidCardForAttack(selectedCard, attack, output)) {
                            attack.add(selectedCard);
                            playAdventureCard(position);
                            output.println("Card added: " + selectedCard);
                        }
                    } else {
                        output.println("Invalid position. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    output.println("Invalid input. Please enter a valid position or 'quit'.");
                }
            }
        }
        this.attack = attack;
    }

    private boolean isValidCardForAttack(Deck.Card card, ArrayList<Deck.Card> currentAttack, PrintWriter output) {
        if (card.type.equals("F")) {
            output.println("Foes cannot be used in a attack");
            return false;
        }
        if (weaponCards(card.type) && currentAttack.stream().anyMatch(c -> c.type.equals(card.type))) {
            output.println("The same weapon cannot be selected twice in one attack.");
            return false;
        }
        return true;
    }

    private boolean weaponCards(String cardType) {
        return cardType.equals("D") || cardType.equals("H") || cardType.equals("S") ||
                cardType.equals("B") || cardType.equals("L") || cardType.equals("E");
    }

    public ArrayList<Deck.Card> getAttack() {
        return attack;
    }

    public int calculateAttackValue() {
        int attackValue = 0;
        for (Deck.Card c : getAttack()){
            attackValue += c.value;
        }
        return attackValue;
    }
}