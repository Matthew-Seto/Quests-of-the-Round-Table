package com.example.messaging_stomp_websocket;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class Game {
    private final Deck advDeck;
    private final Deck eventDeck;
    public final ArrayList<Player> players;
    private int currentPlayerIndex;
    private boolean QcardDrawn;
    private Deck.Card currentEventCard;
    private final ArrayList<Player> ineligiblePlayers;
    private int cardsUsedInQuest;
    private boolean winnerCheck;

    private SimpMessagingTemplate messageTemplate;
    private SimpMessagingTemplate playerTemplate;

    private BlockingQueue<HelloMessage> messageQueue;

    public void sendMessage(String message) {
        messageTemplate.convertAndSend("/topic/gameInput", new Greeting(message));
    }

    public void notifyPlayerUpdate() {
        playerTemplate.convertAndSend("/topic/playerUpdates", players);
    }

    public String waitForMessage() {
        try {

            HelloMessage nextMessage = messageQueue.take();
            return nextMessage.getName();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public Game(int numberOfPlayers, SimpMessagingTemplate messageTemplate, BlockingQueue<HelloMessage> messageQueue, SimpMessagingTemplate playerTemplate) {
        this.messageTemplate = messageTemplate;
        this.messageQueue = messageQueue;
        this.playerTemplate = playerTemplate; 

        this.players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("P" + i, this)); // Create players P1, P2, P3, ...
        }
        this.advDeck = new Deck();
        this.eventDeck = new Deck();
        this.advDeck.initializeAdventureDeck();
        this.eventDeck.initializeEventDeck();
        this.currentPlayerIndex = 0;
        this.currentEventCard = new Deck.Card("Null", 0);
        this.ineligiblePlayers = new ArrayList<>();
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

    public void promptPlayer() {
        notifyPlayerUpdate();
        if (Objects.equals(currentEventCard.description,
                "Prosperity: All players immediately draw 2 adventure cards.")) {
            drawAdventureCardsForAllPlayers(2);
            for (Player player : getPlayers()) {
                trimIfNeeded(player);
                flushDisplay();
            }
        }
        trimIfNeeded(getCurrentPlayer());
        
        if (QcardDrawn) {
            handleQuestSponsorship();
        } else {
            this.sendMessage("Hit <return> to end turn: ");
            endCurrentPlayerTurn();

        }

    }

    public void trimIfNeeded(Player player) {
        final int maxHandSize = 12;
        if (player.getHandSize() > 12) {
            this.sendMessage("Your hand has exceeded the maximum size of " + maxHandSize + " cards!");
            this.sendMessage("You need to discard " + (player.getHandSize() - maxHandSize) + " card(s).");
            while (player.getHandSize() > maxHandSize) {
                player.displayHand();
                this.sendMessage("Select a card to discard (1-" + player.getHandSize() + "): ");
                notifyPlayerUpdate();
                try {
                    String positionString = this.waitForMessage();
                    int position = Integer.parseInt(positionString);

                    if (position > 0 && position <= player.getHandSize()) {
                        player.getHand().remove(position - 1);
                        this.sendMessage("Card discarded.");
                        notifyPlayerUpdate();
                    } else {
                        this.sendMessage("Invalid position. Please try again.");
                    }
                } catch (Exception e) {
                    this.sendMessage("Invalid input. Please enter a number.");
                }
            }
        }
    }

    public void handleQuestSponsorship() {
        Player currentPlayer = getCurrentPlayer();
        boolean anyPlayerSponsors = false;
        int startingIndex = players.indexOf(currentPlayer);

        int numberOfStages = getNumberOfStagesFromQuest(currentEventCard);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get((startingIndex + i) % players.size());
            boolean wantsToSponsor = promptForSponsorship(player, numberOfStages);
            if (wantsToSponsor) {
                anyPlayerSponsors = true;
                this.sendMessage(player.getName() + " has chosen to sponsor the quest.");
                flushDisplay();
                QcardDrawn = false;
                sponsorSetsUpQuestStages(player, numberOfStages);
                break;
            } else {
                this.sendMessage(player.getName() + " declines to sponsor the quest.");
                ineligiblePlayers.add(player);
            }
        }

        if (!anyPlayerSponsors) {
            QcardDrawn = false;
            this.sendMessage("No players sponsored the quest. The quest has ended.");
        }
        endCurrentPlayerTurn();
    }

    private boolean promptForSponsorship(Player player, int numberOfStages) {
        if (player.countFoeCards() < numberOfStages) {
            this.sendMessage(player.getName() + " does not have enough 'Foe' cards to sponsor the quest.");
            ineligiblePlayers.add(player);
            return false;
        }

        this.sendMessage(player.getName() + ", do you want to sponsor the quest? (yes/no): ");

        String response = this.waitForMessage();
        response = response.trim().toLowerCase();
        return response.equals("yes");
    }

    private void sponsorSetsUpQuestStages(Player sponsor, int numberOfStages) {
        ArrayList<ArrayList<Deck.Card>> stages = new ArrayList<>();
        int previousStageValue = 0;

        for (int stage = 1; stage <= numberOfStages; stage++) {
            ArrayList<Deck.Card> currentStage = new ArrayList<>();
            int currentStageValue = 0;

            while (true) {
                sponsor.displayHand();
                this.sendMessage("Stage " + stage
                        + " - Enter the position of the next card to include in this stage or 'Quit' to end: ");
                notifyPlayerUpdate();
                String response = this.waitForMessage();
                response = response.trim().toLowerCase();
                if (response.equals("quit")) {
                    if (currentStage.isEmpty()) {
                        this.sendMessage("A stage cannot be empty.");
                    } else if (currentStageValue <= previousStageValue) {
                        this.sendMessage("Insufficient value for this stage.");
                    } else {
                        stages.add(currentStage);
                        previousStageValue = currentStageValue;
                        this.sendMessage("Stage " + stage + " set with cards: " + currentStage);
                        flushDisplay();
                        break;
                    }
                } else {
                    try {
                        int position = Integer.parseInt(response);
                        if (position > 0 && position <= sponsor.getHandSize()) {
                            Deck.Card selectedCard = sponsor.getHand().get(position - 1);
                            if (isValidCardForStage(selectedCard, currentStage)) {
                                currentStage.add(selectedCard);
                                sponsor.playAdventureCard(position - 1);
                                cardsUsedInQuest++;
                                currentStageValue += selectedCard.value;
                                this.sendMessage("Card added: " + selectedCard);
                                notifyPlayerUpdate();
                            }
                        } else {
                            this.sendMessage("Invalid position. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        this.sendMessage("Invalid input. Please enter a valid position or 'quit'.");
                    }
                }
            }
        }

        // Set up the quest with the stages
        setUpQuest(stages, sponsor);
    }

    private boolean isValidCardForStage(Deck.Card card, ArrayList<Deck.Card> currentStage) {
        if (card.type.equals("F") && currentStage.stream().anyMatch(c -> c.type.equals("F"))) {
            this.sendMessage("Two foes cannot be on the same stage");
            return false;
        }
        if (weaponCards(card.type) && currentStage.stream().anyMatch(c -> c.type.equals(card.type))) {
            this.sendMessage("The same weapon cannot be selected twice in one stage");
            return false;
        }
        return true;
    }

    private boolean weaponCards(String cardType) {
        return cardType.equals("D") || cardType.equals("H") || cardType.equals("S") ||
                cardType.equals("B") || cardType.equals("L") || cardType.equals("E");
    }

    private void setUpQuest(ArrayList<ArrayList<Deck.Card>> stages, Player sponsor) {
        ineligiblePlayers.clear();
        ineligiblePlayers.add(sponsor);

        // for (Player player : getPlayers()) {
        //     if (!player.equals(sponsor)) {
        //         if (player.countFoeCards() < stages.size()) {
        //             this.sendMessage(
        //                     player.getName() + " does not have enough 'Foe' cards to participate in the quest.");
        //             ineligiblePlayers.add(player);
        //         }
        //     }
        // }

        ArrayList<Player> eligibleParticipants = new ArrayList<>(players);
        eligibleParticipants.removeAll(ineligiblePlayers);

        for (int stage = 1; stage <= stages.size(); stage++) {
            this.sendMessage("Stage " + stage + ":");

            ArrayList<Player> participantsForStage = new ArrayList<>();
            for (Player participant : eligibleParticipants) {
                this.sendMessage(participant.getName() + ", do you want to withdraw from the quest? (yes/no): ");
                String response = this.waitForMessage();
                response = response.trim().toLowerCase();
                if (response.equals("no")) {
                    participantsForStage.add(participant);
                    drawAdventureCardsForPlayer(participant, 1);
                    notifyPlayerUpdate();
                    trimIfNeeded(participant);
                    flushDisplay();
                } else {
                    this.sendMessage(participant.getName() + " has withdrawn from the quest.");
                    ineligiblePlayers.add(participant);
                }
            }

            if (participantsForStage.isEmpty()) {
                this.sendMessage("No participants for the current stage. The quest ends.");
                handleEndOfQuest(stages, sponsor);
                return;
            }

            for (Player participant : participantsForStage) {
                participant.setupAttack();
                notifyPlayerUpdate();
                flushDisplay();
            }

            ArrayList<Player> newEligibleParticipants = new ArrayList<>();
            for (Player participant : participantsForStage) {
                if (resolveAttack(participant, stages.get(stage - 1), stages)) {
                    newEligibleParticipants.add(participant);
                } else {
                    ineligiblePlayers.add(participant);
                }
            }

            eligibleParticipants = newEligibleParticipants;
        }

        handleEndOfQuest(stages, sponsor);
        ineligiblePlayers.clear();
        eligibleParticipants.clear();
    }

    public boolean resolveAttack(Player participant, ArrayList<Deck.Card> stage,
            ArrayList<ArrayList<Deck.Card>> stages) {
        int attackValue = participant.calculateAttackValue();
        int stageValue = calculateStageValue(stage);

        this.sendMessage(participant.getName() + "'s attack value: " + attackValue);
        this.sendMessage("Stage value: " + stageValue);

        if (attackValue >= stageValue) {
            this.sendMessage(participant.getName() + " passes the stage.");
            if (stage == stages.get(stages.size() - 1)) {
                int shieldsEarned = stages.size();
                participant.addShields(shieldsEarned);
                this.sendMessage(participant.getName() + " wins the quest and earns " + shieldsEarned + " shields.");
            }
            return true;
        } else {
            this.sendMessage(participant.getName() + " fails the stage and is ineligible for the next stage.");
            return false;
        }
    }

    private int calculateStageValue(ArrayList<Deck.Card> stage) {
        int value = 0;
        for (Deck.Card card : stage) {
            value += card.value;
        }
        return value;
    }

    public void handleEndOfQuest(ArrayList<ArrayList<Deck.Card>> stages, Player sponsor) {
        int numberOfCardsToDraw = cardsUsedInQuest + stages.size();
        this.sendMessage(
                "The quest has ended. " + sponsor.getName() + " has drawn " + numberOfCardsToDraw + " new cards.");
        drawAdventureCardsForPlayer(sponsor, numberOfCardsToDraw);
        notifyPlayerUpdate();
        trimIfNeeded(sponsor);
        ineligiblePlayers.clear();
        cardsUsedInQuest = 0;
    }

    public void endCurrentPlayerTurn() {
        this.sendMessage("Player " + getCurrentPlayer().getName() + "'s turn has ended.");
        flushDisplay();

        ArrayList<Player> winners = checkForWinners();
        if (!winners.isEmpty()) {
            for (Player winner : winners) {
                this.sendMessage("Player " + winner.getName() + " has won the game!");
                winnerCheck = true;
            }
        } else {
            nextTurn();
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

    public void flushDisplay() {
    }

    public void gameStart() {
        getCurrentPlayer().displayHand();
        drawEventCard(getCurrentPlayer());
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        this.sendMessage(getCurrentPlayer().getName() + "'s turn");
        getCurrentPlayer().displayHand();
        drawEventCard(getCurrentPlayer());
    }

    public void overwriteEventDeckCard(int index, String type, String description) {
        Deck.Card e = new Deck.Card(type, description);
        eventDeck.setEventCard(index, e);
    }

    public void overwriteQuestEventDeckCard(int index, String type, int value) {
        Deck.Card e = new Deck.Card(type, value);
        eventDeck.setEventCard(index, e);
    }

    public void overwriteAdventureDeckCard(int index, String type, int value) {
        Deck.Card a = new Deck.Card(type, value);
        advDeck.setAdventureCard(index, a);
    }

    private void drawEventCard(Player player) {
        if (eventDeck.getEventDeckSize() > 0) {
            Deck.Card drawnCard = eventDeck.eventDeck.removeFirst();
            this.sendMessage(player.getName() + " drew an event card: " + drawnCard);
            player.receiveEventCard(drawnCard);
            currentEventCard = drawnCard;
            returnEventCardToBottom(drawnCard);
        } else {
            this.sendMessage("No more event cards to draw.");
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

    void givePlayerShields(Player player, int shields) {
        player.addShields(shields);
    }

    public Player getCurrentPlayer() {
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

    public boolean getWinner() {
        return winnerCheck;
    }

    public void reset() {
        for (Player player : players) {
            player.clearHand();
        }

        advDeck.initializeAdventureDeck();
        eventDeck.initializeEventDeck();

        currentPlayerIndex = 0;
        currentEventCard = new Deck.Card("Null", 0);
        ineligiblePlayers.clear();

        // Optionally, clear the message queue if needed
        messageQueue.clear();

        System.out.println("Game has been reset.");
    }

    public void displayHandOfAllPlayers()
    {
        for (Player player : players) {
            player.displayHand();
        }
    }
}