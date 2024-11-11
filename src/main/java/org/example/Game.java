package org.example;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private final Deck advDeck;
    private final Deck eventDeck;
    private final ArrayList<Player> players;
    private int currentPlayerIndex;
    private boolean QcardDrawn;
    public Deck.Card currentEventCard;
    private final ArrayList<Player> ineligiblePlayers;
    public int cardsUsedInQuest;
    private boolean winnerCheck;
    private Player sponsor;
    public ArrayList<ArrayList<Deck.Card>> stages;
    public ArrayList<Player> eligiblePlayers;

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
        this.currentEventCard = new Deck.Card("Null", 0);
        this.ineligiblePlayers = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.eligiblePlayers = new ArrayList<>();
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
        if (Objects.equals(currentEventCard.description, "Prosperity: All players immediately draw 2 adventure cards.")){
            drawAdventureCardsForAllPlayers( 2);
            for (Player player : getPlayers()) {
                trimIfNeeded(player,input,output);
                flushDisplay(output);
            }
        }
        trimIfNeeded(getCurrentPlayer(),input, output);

        if (QcardDrawn) {
            handleQuestSponsorship(input, output);
        } else {
            output.print("Hit <return> to end turn: ");
            output.flush();

            String inputStr = input.nextLine();
            if (inputStr.isEmpty()) {
                endCurrentPlayerTurn(output);
            }
        }

    }

    public void trimIfNeeded(Player player, Scanner input, PrintWriter output){
        final int maxHandSize = 12;
        if (player.getHandSize() > 12) {
            output.println("Your hand has exceeded the maximum size of " + maxHandSize + " cards!");
            output.println("You need to discard " + (player.getHandSize() - maxHandSize) + " card(s).");

            while (player.getHandSize() > maxHandSize) {
                player.displayHand(output);
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
        Player currentPlayer = getCurrentPlayer();
        boolean anyPlayerSponsors = false;
        int startingIndex = players.indexOf(currentPlayer);

        int numberOfStages = getNumberOfStagesFromQuest(currentEventCard);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get((startingIndex + i) % players.size());
            boolean wantsToSponsor = promptForSponsorship(player, input, output, numberOfStages);
            if (wantsToSponsor) {
                anyPlayerSponsors = true;
                output.println(player.getName() + " has chosen to sponsor the quest.");
                flushDisplay(output);
                QcardDrawn = false;
                sponsorSetsUpQuestStages(player, input, output, numberOfStages);
                break;
            } else {
                output.println(player.getName() + " declines to sponsor the quest.");
                ineligiblePlayers.add(player);
            }
        }

        if (!anyPlayerSponsors) {
            QcardDrawn = false;
            output.println("No players sponsored the quest. The quest has ended.");
        }
        endCurrentPlayerTurn(output);
    }

    public void playerDecideToSponsor(int index, boolean wantsToSponsor) {
        int numberOfStages = getNumberOfStagesFromQuest(currentEventCard);
        Player player = players.get(index);

        // Check if the player has enough 'Foe' cards for the sponsorship
        if (player.countFoeCards() < numberOfStages) {
            System.out.println(player.getName() + " does not have enough 'Foe' cards to sponsor the quest.");
            ineligiblePlayers.add(player);
            return; // Player cannot sponsor, return early
        }

        // Call the appropriate method based on the decision (wantsToSponsor)
        if (wantsToSponsor) {
            acceptSponsorship(player);
        } else {
            declineSponsorship(player);
        }
    }

    public void acceptSponsorship(Player player) {
        System.out.println(player.getName() + " has chosen to sponsor the quest.");
        sponsor = player;
        QcardDrawn = false;
    }

    public void declineSponsorship(Player player) {
        System.out.println(player.getName() + " declines to sponsor the quest.");
        ineligiblePlayers.add(player);
    }

    public boolean isPlayerTheSponsor(Player player){
        return player == sponsor;
    }

    private boolean promptForSponsorship(Player player, Scanner input, PrintWriter output, int numberOfStages) {
        if (player.countFoeCards() < numberOfStages) {
            output.println(player.getName() + " does not have enough 'Foe' cards to sponsor the quest.");
            ineligiblePlayers.add(player);
            return false;
        }

        output.print(player.getName() + ", do you want to sponsor the quest? (yes/no): ");
        output.flush();

        String response = input.nextLine().trim().toLowerCase();
        return response.equals("yes");
    }

    private void sponsorSetsUpQuestStages(Player sponsor, Scanner input, PrintWriter output, int numberOfStages) {
        ArrayList<ArrayList<Deck.Card>> stages = new ArrayList<>();
        int previousStageValue = 0;

        for (int stage = 1; stage <= numberOfStages; stage++) {
            ArrayList<Deck.Card> currentStage = new ArrayList<>();
            int currentStageValue = 0;

            while (true) {
                sponsor.displayHand(output);
                output.print("Stage " + stage + " - Enter the position of the next card to include in this stage or 'Quit' to end: ");
                output.flush();

                String response = input.nextLine().trim().toLowerCase();
                if (response.equals("quit")) {
                    if (currentStage.isEmpty()) {
                        output.println("A stage cannot be empty.");
                    } else if (currentStageValue <= previousStageValue) {
                        output.println("Insufficient value for this stage.");
                    } else {
                        stages.add(currentStage);
                        previousStageValue = currentStageValue;
                        output.println("Stage " + stage + " set with cards: " + currentStage);
                        flushDisplay(output);
                        break;
                    }
                } else {
                    try {
                        int position = Integer.parseInt(response);
                        if (position > 0 && position <= sponsor.getHandSize()) {
                            Deck.Card selectedCard = sponsor.getHand().get(position - 1);
                            if (isValidCardForStage(selectedCard, currentStage, output)) {
                                currentStage.add(selectedCard);
                                sponsor.playAdventureCard(position - 1);
                                cardsUsedInQuest++;
                                currentStageValue += selectedCard.value;
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
        }

        // Set up the quest with the stages
        setUpQuest(stages,input,output,sponsor);
    }

    private boolean isValidCardForStage(Deck.Card card, ArrayList<Deck.Card> currentStage, PrintWriter output) {
        if (card.type.equals("F") && currentStage.stream().anyMatch(c -> c.type.equals("F"))) {
            output.println("Two foes cannot be on the same stage");
            return false;
        }
        if (weaponCards(card.type) && currentStage.stream().anyMatch(c -> c.type.equals(card.type))) {
            output.println("The same weapon cannot be selected twice in one stage");
            return false;
        }
        return true;
    }

    private boolean weaponCards(String cardType) {
        return cardType.equals("D") || cardType.equals("H") || cardType.equals("S") ||
                cardType.equals("B") || cardType.equals("L") || cardType.equals("E");
    }

    private void setUpQuest(ArrayList<ArrayList<Deck.Card>> stages, Scanner input, PrintWriter output, Player sponsor) {
        ineligiblePlayers.clear();
        ineligiblePlayers.add(sponsor);

        for (Player player : getPlayers()) {
            if (!player.equals(sponsor)) {
                if (player.countFoeCards() < stages.size()) {
                    output.println(player.getName() + " does not have enough 'Foe' cards to participate in the quest.");
                    ineligiblePlayers.add(player);
                }
            }
        }

        ArrayList<Player> eligibleParticipants = new ArrayList<>(players);
        eligibleParticipants.removeAll(ineligiblePlayers);

        for (int stage = 1; stage <= stages.size(); stage++) {
            output.println("Stage " + stage + ":");

            ArrayList<Player> participantsForStage = new ArrayList<>();
            for (Player participant : eligibleParticipants) {
                output.print(participant.getName() + ", do you want to withdraw from the quest? (yes/no): ");
                output.flush();
                String response = input.nextLine().trim().toLowerCase();
                if (response.equals("no")) {
                    participantsForStage.add(participant);
                    drawAdventureCardsForPlayer(participant, 1);
                    trimIfNeeded(participant, input, output);
                    flushDisplay(output);
                } else {
                    output.println(participant.getName() + " has withdrawn from the quest.");
                    ineligiblePlayers.add(participant);
                }
            }

            if (participantsForStage.isEmpty()) {
                output.println("No participants for the current stage. The quest ends.");
                handleEndOfQuest(stages, input, output, sponsor);
                return;
            }

            for (Player participant : participantsForStage) {
                participant.setupAttack(input, output);
                flushDisplay(output);
            }

            ArrayList<Player> newEligibleParticipants = new ArrayList<>();
            for (Player participant : participantsForStage) {
                if (resolveAttack(participant, stages.get(stage - 1), stages, output)) {
                    newEligibleParticipants.add(participant);
                } else {
                    ineligiblePlayers.add(participant);
                }
            }

            eligibleParticipants = newEligibleParticipants;
        }

        handleEndOfQuest(stages,input,output,sponsor);
        ineligiblePlayers.clear();
        eligibleParticipants.clear();
    }

    public boolean resolveAttack(Player participant, ArrayList<Deck.Card> stage, ArrayList<ArrayList<Deck.Card>> stages, PrintWriter output) {
        int attackValue = participant.calculateAttackValue();
        int stageValue = calculateStageValue(stage);

        output.println(participant.getName() + "'s attack value: " + attackValue);
        output.println("Stage value: " + stageValue);

        if (attackValue >= stageValue) {
            output.println(participant.getName() + " passes the stage.");
            if (stage == stages.get(stages.size() - 1)) {
                int shieldsEarned = stages.size();
                participant.addShields(shieldsEarned);
                output.println(participant.getName() + " wins the quest and earns " + shieldsEarned + " shields.");
            }
            return true;
        } else {
            output.println(participant.getName() + " fails the stage and is ineligible for the next stage.");
            return false;
        }
    }

    public int calculateStageValue(ArrayList<Deck.Card> stage) {
        int value = 0;
        for (Deck.Card card : stage) {
            value += card.value;
        }
        return value;
    }

    public void handleEndOfQuest(ArrayList<ArrayList<Deck.Card>> stages, Scanner input, PrintWriter output, Player sponsor){
        int numberOfCardsToDraw = cardsUsedInQuest + stages.size();
        output.println("The quest has ended. " + sponsor.getName() + " has drawn " + numberOfCardsToDraw + " new cards.");
        output.flush();
        drawAdventureCardsForPlayer(sponsor, numberOfCardsToDraw);

        trimIfNeeded(sponsor, input, output);
        ineligiblePlayers.clear();
        cardsUsedInQuest = 0;
    }

    public void endCurrentPlayerTurn(PrintWriter output) {
        output.println("Player " + getCurrentPlayer().getName() + "'s turn has ended.");
        flushDisplay(output);

        ArrayList<Player> winners = checkForWinners();
        if (!winners.isEmpty()) {
            for (Player winner : winners) {
                output.println("Player " + winner.getName() + " has won the game!");
                output.flush();
                winnerCheck = true;
            }
        } else {
            nextTurn(output);
        }
    }

    public ArrayList<Player> checkForWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }
        return winners;
    }

    public void flushDisplay(PrintWriter output) {
        for (int i = 0; i < 10; i++) {
            output.println();
        }
        output.flush();
    }

    public void gameStart(PrintWriter output) {
        getCurrentPlayer().displayHand(output);
        output.flush();
        drawEventCard(getCurrentPlayer(), output);
    }

    public void nextTurn(PrintWriter output) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        output.println(getCurrentPlayer().getName() + "'s turn");
        getCurrentPlayer().displayHand(output);
        drawEventCard(getCurrentPlayer(), output);
    }

    public void overwriteEventDeckCard(int index, String type, String description){
        Deck.Card e = new Deck.Card(type, description);
        eventDeck.setEventCard(index,e);
    }

    public void overwriteQuestEventDeckCard(int index, String type, int value){
        Deck.Card e = new Deck.Card(type, value);
        eventDeck.setEventCard(index,e);
    }

    public void overwriteAdventureDeckCard(int index, String type, int value){
        Deck.Card a = new Deck.Card(type, value);
        advDeck.setAdventureCard(index,a);
    }

    private void drawEventCard(Player player, PrintWriter output) {
        if (eventDeck.getEventDeckSize() > 0) {
            Deck.Card drawnCard = eventDeck.eventDeck.removeFirst();
            output.println(player.getName() + " drew an event card: " + drawnCard);
            output.flush();
            player.receiveEventCard(drawnCard);
            currentEventCard = drawnCard;
            returnEventCardToBottom(drawnCard);
        } else {
            output.println("No more event cards to draw.");
        }
    }

    public void drawEventCard(Player player) {
        if (eventDeck.getEventDeckSize() > 0) {
            Deck.Card drawnCard = eventDeck.eventDeck.removeFirst();
            System.out.println(player.getName() + " drew an event card: " + drawnCard);
            player.receiveEventCard(drawnCard);

            currentEventCard = drawnCard;
            returnEventCardToBottom(drawnCard);
        } else {
            System.out.println("No more event cards to draw.");
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
        QcardDrawn = true;
    }

    private int getNumberOfStagesFromQuest(Deck.Card questCard) {
        return questCard.value;
    }

    public boolean getWinner(){
        return winnerCheck;
    }

    public Player getSponsor() {
        return sponsor;
    }

}
