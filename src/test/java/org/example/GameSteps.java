package org.example;
import io.cucumber.java.en.*;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSteps {
    private Game game;

    @Given("A1 Scenario Starts")
    public void a1_Scenario() {
        // Start game, decks are created, hands of the 4 players are set up with random cards
        game = new Game(4);
        game.distributeCards();

        // p1 initial hand
        ArrayList<Deck.Card> p1hand = new ArrayList<>();
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("D", 5));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(0).setHand(p1hand);

        // p2 initial hand
        ArrayList<Deck.Card> p2hand = new ArrayList<>();
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 40));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("S", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(1).setHand(p2hand);

        // p3 initial hand
        ArrayList<Deck.Card> p3hand = new ArrayList<>();
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 15));
        p3hand.add(new Deck.Card("D", 5));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("B", 15));
        p3hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(2).setHand(p3hand);

        // p4 initial hand
        ArrayList<Deck.Card> p4hand = new ArrayList<>();
        p4hand.add(new Deck.Card("F", 5));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 40));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("S", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("B", 15));
        p4hand.add(new Deck.Card("L", 20));
        p4hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(3).setHand(p4hand);

        // draw quest of 4
        game.overwriteQuestEventDeckCard(0, "Q", 4);

        // make sure game doesn't draw another card that may affect outcome
        game.overwriteEventDeckCard(1, "NULL", "");

        // rig adventure deck cards
        // Stage 1:
        game.overwriteAdventureDeckCard(0, "F", 30);
        game.overwriteAdventureDeckCard(1, "S", 10);
        game.overwriteAdventureDeckCard(2, "B", 15);

        // Stage 2:
        game.overwriteAdventureDeckCard(3, "F", 10);
        game.overwriteAdventureDeckCard(4, "L", 20);
        game.overwriteAdventureDeckCard(5, "L", 20);

        // Stage 3:
        game.overwriteAdventureDeckCard(6, "B", 15);
        game.overwriteAdventureDeckCard(7, "S", 10);

        // Stage 4:
        game.overwriteAdventureDeckCard(8, "F", 30);
        game.overwriteAdventureDeckCard(9, "L", 20);
    }

    @When("P{int} draws quest with {int} stages")
    public void drawQuestWithStages(int index, int arg1) {
        game.drawEventCard(game.getPlayers().get(index - 1));
    }

    @And("P{int} draws event card")
    public void drawEventCard(int index){
        Player player = game.getPlayers().get(index-1);
        // drawEventCard function handles Plague and Queen Favour
        game.drawEventCard(game.getPlayers().get(index-1));

        // hand queen favours trimming if over 12
        if (player.getHandSize() >= 12){
            int cardsToPlay = player.getHandSize() - 12;
            for (int i = 0; i < cardsToPlay; i++){
                player.playAdventureCard(12);
            }
        }
    }

    @And("P{int} draws prosperity")
    public void drawProsperity(int index){
        game.drawEventCard(game.getPlayers().get(index-1));
        if (Objects.equals(game.currentEventCard.description, "Prosperity: All players immediately draw 2 adventure cards.")){
            game.drawAdventureCardsForAllPlayers( 2);
            for (Player player : game.getPlayers()) {
                //will implement logic to trim
                if (player.getHandSize() >= 12){
                    int cardsToPlay = player.getHandSize() - 12;
                    for (int i = 0; i < cardsToPlay; i++){
                        player.playAdventureCard(12);
                    }
                }
            }
        }
    }

    @Then("P{int} declines to sponsor")
    public void declinesToSponsor(int index){
        game.playerDecideToSponsor(index - 1, false);
    }

    @And("P{int} decides to sponsor")
    public void decidesToSponsor(int index){
        game.playerDecideToSponsor(index - 1, true);
        game.getPlayers().get(index-1).displayHandCucumber();
    }

    @And("P{int} is the sponsor")
    public void isTheSponsor(int index){
        assertTrue(game.isPlayerTheSponsor(game.getPlayers().get(index - 1)));
    }

    @And("P{int} builds stage {int} with cards {string}")
    public void buildStages(int index, int stage, String cards){
        ArrayList<Deck.Card> currentStage = new ArrayList<>();

        List<String> cardIdentifiers = Arrays.asList(cards.split(","));
        for (String cardIdentifier : cardIdentifiers) {
            cardIdentifier = cardIdentifier.trim();
            String type = cardIdentifier.substring(0, 1);
            int value = Integer.parseInt(cardIdentifier.substring(1));

            int cardIndex = game.getSponsor().getIndexByCardTypeAndValue(type,value);
            if (cardIndex != -1) {
                Deck.Card c = game.getSponsor().playReturnAdventureCard(cardIndex);
                currentStage.add(c);
                game.cardsUsedInQuest++;
                System.out.println("Card added to stage " + stage + ": " + c);
            } else {
                System.out.println("Invalid card identifier: " + cardIdentifier);
            }
        }

        game.stages.add(currentStage);
    }

    @And("P{int} built {int} valid stages with cards {string}")
    public void checkStages(int index, int stages, String cards){
        List<String> expectedCardIdentifiers = Arrays.asList(cards.split(","));
        expectedCardIdentifiers = expectedCardIdentifiers.stream().map(String::trim).toList();

        List<ArrayList<Deck.Card>> builtStages = game.stages;
        assertEquals(game.stages.size(), stages);

        for (String expectedCard : expectedCardIdentifiers) {
            String type = expectedCard.substring(0, 1);
            int value = Integer.parseInt(expectedCard.substring(1));
            boolean cardsFound = false;

            for (ArrayList<Deck.Card> stage : builtStages) {
                for (Deck.Card card : stage) {
                    if (card.type.equals(type) && card.value == value) {
                        cardsFound = true;
                        break;
                    }
                }
                if (cardsFound) {
                    break;
                }
            }

            assertTrue(cardsFound);
        }
    }

    @And("P{int} decides to participate in stage {int} and trims {string}")
    public void decidesToParticipate(int index, int stage, String cards){
        Player player = game.getPlayers().get(index - 1);
        if (!game.eligiblePlayers.contains(player)){
            game.eligiblePlayers.add(player);
        }
        game.drawAdventureCardsForPlayer(player, 1);

        if (!cards.trim().isEmpty()) {
            List<String> cardIdentifiers = Arrays.asList(cards.split(","));
            for (String cardIdentifier : cardIdentifiers) {
                cardIdentifier = cardIdentifier.trim();
                String type = cardIdentifier.substring(0, 1);
                int value = Integer.parseInt(cardIdentifier.substring(1));
                int cardIndex = player.getIndexByCardTypeAndValue(type, value);

                if (cardIndex != -1) {
                    Deck.Card c = player.playReturnAdventureCard(cardIndex);
                    System.out.println("Player " + player.getName() + " trimmed: " + c);
                } else {
                    System.out.println("Invalid card identifier: " + cardIdentifier);
                }
            }
        } else {
            System.out.println("No cards to trim for player " + player.getName());
        }
    }

    @And("P{int} declines to participate")
    public void declinesToParticipate(int index){
        Player player = game.getPlayers().get(index - 1);
        game.eligiblePlayers.remove(player);
    }

    @And("Check participants are {string}")
    public void checkParticipants(String participants){
        String trimmedParticipants = participants.replace(",", "");
        StringBuilder peoplePlaying = new StringBuilder();
        for (Player p : game.eligiblePlayers){
            peoplePlaying.append(p.getName());
        }
        assertEquals(peoplePlaying.toString(), trimmedParticipants);
    }

    @And("P{int} builds an attack with {string}")
    public void buildsAttack(int index, String cards){
        Player player = game.getPlayers().get(index-1);
        player.displayHandCucumber();
        ArrayList<Deck.Card> attack = new ArrayList<>();

        List<String> cardIdentifiers = Arrays.asList(cards.split(","));
        for (String cardIdentifier : cardIdentifiers) {
            cardIdentifier = cardIdentifier.trim();
            String type = cardIdentifier.substring(0, 1);
            int value = Integer.parseInt(cardIdentifier.substring(1));

            int cardIndex = player.getIndexByCardTypeAndValue(type, value);
            if (cardIndex != -1) {
                Deck.Card selectedCard = player.playReturnAdventureCard(cardIndex);
                attack.add(selectedCard);
                System.out.println("Player " + player.getName() + " added to attack: " + selectedCard);
            } else {
                System.out.println("Invalid card identifier: " + cardIdentifier);
            }
        }

        player.setAttack(attack);
    }

    @And("P{int} resolves attack for stage {int}")
    public void resolveAttack(int index, int stage){
        int attackValue = game.getPlayers().get(index - 1).calculateAttackValue();
        int stageValue = game.calculateStageValue(game.stages.get(stage - 1));

        System.out.println(game.getPlayers().get(index-1).getName() + "'s attack value: " + attackValue);
        System.out.println("Stage value: " + stageValue);

        if (attackValue >= stageValue) {
            System.out.println(game.getPlayers().get(index - 1).getName() + " passes the stage.");
            if (stage == game.stages.size()) {
                int shieldsEarned = game.stages.size();
                game.getPlayers().get(index - 1).addShields(shieldsEarned);
                System.out.println(game.getPlayers().get(index - 1).getName() + " wins the quest and earns " + shieldsEarned + " shields.");
            }
        } else {
            System.out.println(game.getPlayers().get(index - 1).getName() + " fails the stage and is ineligible for the next stage.");
            game.eligiblePlayers.remove(game.getPlayers().get(index-1));
        }
    }

    @And("P{int} should have {int} shields")
    public void validateShields(int index, int shields){
        assertEquals(game.getPlayers().get(index - 1).getShields(), shields);
    }

    @And("P{int} should have hand size {int}")
    public void validateHandSize(int index, int handSize){
        assertEquals(game.getPlayers().get(index - 1).getHandSize(), handSize);
    }

    @And("P{int} handles the end of quest")
    public void handleEnd(int index){
        int sizeBeforeDraw = game.getPlayers().get(index-1).getHandSize();
        int numberOfCardsToDraw = game.cardsUsedInQuest + game.stages.size();
        game.drawAdventureCardsForPlayer(game.getPlayers().get(index-1),numberOfCardsToDraw);
        int handMax = 12;
        int numberOfCardsToTrim = (numberOfCardsToDraw + sizeBeforeDraw)  - handMax;
        // simulate trimming hand
        for (int i = 0; i < numberOfCardsToTrim; i++){
            game.getPlayers().get(index-1).playAdventureCard(12);
        }
        game.getPlayers().get(index-1).displayHandCucumber();

        ArrayList<Player> winners = game.checkForWinners();
        if (!winners.isEmpty()) {
            for (Player winner : winners) {
                System.out.println("Player " + winner.getName() + " has won the game!");
                assertTrue(winner.getShields() >= 7, "Winner " + winner.getName() + " has 7 shields and wins!");
            }
        }

        game.stages.clear();
    }

    @And("P{int} is a winner")
    public void isWinner(int index){
        Player player = game.getPlayers().get(index-1);
        ArrayList<Player> winners = game.checkForWinners();
        if (!winners.isEmpty()) {
            for (Player winner : winners) {
                if (winner.equals(player)){
                    assertEquals(winner, player);
                }
            }
        }
    }

    @Given("2winner game 2winner quest starts")
    public void twoWinnerGameTwoWinnerQuest(){
        // Start game, decks are created, hands of the 4 players are set up with random cards
        game = new Game(4);
        game.distributeCards();

        // p1 initial hand
        ArrayList<Deck.Card> p1hand = new ArrayList<>();
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 10));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("D", 5));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(0).setHand(p1hand);

        // p2 initial hand
        ArrayList<Deck.Card> p2hand = new ArrayList<>();
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 40));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("S", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(1).setHand(p2hand);

        // p3 initial hand
        ArrayList<Deck.Card> p3hand = new ArrayList<>();
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 15));
        p3hand.add(new Deck.Card("D", 5));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("B", 15));
        p3hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(2).setHand(p3hand);

        // p4 initial hand
        ArrayList<Deck.Card> p4hand = new ArrayList<>();
        p4hand.add(new Deck.Card("F", 5));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 40));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("S", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("B", 15));
        p4hand.add(new Deck.Card("L", 20));
        p4hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(3).setHand(p4hand);

        // draw quest of 4
        game.overwriteQuestEventDeckCard(0, "Q", 4);

        // draw quest of 3
        game.overwriteQuestEventDeckCard(1, "Q", 3);

        // rig adventure deck cards
        // Quest 1 Stage 1:
        game.overwriteAdventureDeckCard(0, "F", 30);
        game.overwriteAdventureDeckCard(1, "S", 10);
        game.overwriteAdventureDeckCard(2, "B", 15);

        // Quest 1 Stage 2:
        game.overwriteAdventureDeckCard(3, "F", 10);
        game.overwriteAdventureDeckCard(4, "L", 20);
        game.overwriteAdventureDeckCard(5, "L", 20);

        // Quest 1 Stage 3:
        game.overwriteAdventureDeckCard(6, "B", 15);
        game.overwriteAdventureDeckCard(7, "S", 10);

        // Quest 1 Stage 4:
        game.overwriteAdventureDeckCard(8, "F", 30);
        game.overwriteAdventureDeckCard(9, "L", 20);

        // Quest 2 Stage 1
        game.overwriteAdventureDeckCard(20, "D",5);
        game.overwriteAdventureDeckCard(21, "D",5);

        // Quest 2 Stage 2
        game.overwriteAdventureDeckCard(22, "E",30);
        game.overwriteAdventureDeckCard(23, "E",30);
    }

    @Given("1winner game with events starts")
    public void oneWinnerGameWithEvents(){
        // Start game, decks are created, hands of the 4 players are set up with random cards
        game = new Game(4);
        game.distributeCards();

        // p1 initial hand
        ArrayList<Deck.Card> p1hand = new ArrayList<>();
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("D", 5));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(0).setHand(p1hand);

        // p2 initial hand
        ArrayList<Deck.Card> p2hand = new ArrayList<>();
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 40));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(1).setHand(p2hand);

        // p3 initial hand
        ArrayList<Deck.Card> p3hand = new ArrayList<>();
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 15));
        p3hand.add(new Deck.Card("D", 5));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("B", 15));
        p3hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(2).setHand(p3hand);

        // p4 initial hand
        ArrayList<Deck.Card> p4hand = new ArrayList<>();
        p4hand.add(new Deck.Card("F", 5));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 40));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("S", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("B", 15));
        p4hand.add(new Deck.Card("L", 20));
        p4hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(3).setHand(p4hand);

        // draw quest of 4
        game.overwriteQuestEventDeckCard(0, "Q", 4);

        // rig to test event cards
        game.overwriteEventDeckCard(1, "E", "Plague: The player who draws this card immediately loses 2 shields.");
        game.overwriteEventDeckCard(2, "E", "Prosperity: All players immediately draw 2 adventure cards.");
        game.overwriteEventDeckCard(3, "E", "Queen's favor: The player who draws this card immediately draws 2 adventure cards.");

        // draw quest of 3 next
        game.overwriteQuestEventDeckCard(4, "Q", 3);

        // rig adventure deck cards
        // Quest 1 Stage 1:
        game.overwriteAdventureDeckCard(0, "F", 30);
        game.overwriteAdventureDeckCard(1, "S", 10);
        game.overwriteAdventureDeckCard(2, "B", 15);

        // Quest 1 Stage 2:
        game.overwriteAdventureDeckCard(3, "L", 20);
        game.overwriteAdventureDeckCard(4, "L", 20);
        game.overwriteAdventureDeckCard(5, "L", 20);

        // Quest 1 Stage 3:
        game.overwriteAdventureDeckCard(6, "L", 20);
        game.overwriteAdventureDeckCard(7, "L", 20);
        game.overwriteAdventureDeckCard(8, "S", 10);

        // Quest 1 Stage 4:
        game.overwriteAdventureDeckCard(8, "L", 20);
        game.overwriteAdventureDeckCard(9, "L", 20);
        game.overwriteAdventureDeckCard(10, "L",20);

        // rig cards to ensure P1 has proper hand to build stages
        game.overwriteAdventureDeckCard(11, "F", 5);
        game.overwriteAdventureDeckCard(12, "F", 10);
        game.overwriteAdventureDeckCard(13, "F", 10);
        game.overwriteAdventureDeckCard(14, "F", 15);
        game.overwriteAdventureDeckCard(15, "D", 5);
        game.overwriteAdventureDeckCard(16, "D", 5);

        // rig in prosperity
        game.overwriteAdventureDeckCard(22, "D", 5);
        game.overwriteAdventureDeckCard(23, "S", 10);
        game.overwriteAdventureDeckCard(24, "S", 10);
        game.overwriteAdventureDeckCard(25, "D", 5);
        game.overwriteAdventureDeckCard(26, "D", 5);
        game.overwriteAdventureDeckCard(27, "S", 10);


        // Rig Cards for Quest 2 Stage 1
        game.overwriteAdventureDeckCard(32, "S",10);
        game.overwriteAdventureDeckCard(33, "S",10);
        game.overwriteAdventureDeckCard(34, "D",5);

        // Makes sure they have L20 for stage 3
        game.overwriteAdventureDeckCard(37, "L",20);
        game.overwriteAdventureDeckCard(38, "L",20);
    }

    @Given("0 winner quest")
    public void zeroWinnerQuest(){
        game = new Game(4);
        game.distributeCards();

        // p1 initial hand
        ArrayList<Deck.Card> p1hand = new ArrayList<>();
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 5));
        p1hand.add(new Deck.Card("F", 15));
        p1hand.add(new Deck.Card("F", 40));
        p1hand.add(new Deck.Card("D", 5));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("S", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("H", 10));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("B", 15));
        p1hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(0).setHand(p1hand);

        // p2 initial hand
        ArrayList<Deck.Card> p2hand = new ArrayList<>();
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 5));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 15));
        p2hand.add(new Deck.Card("F", 40));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("D", 5));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("H", 10));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("B", 15));
        p2hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(1).setHand(p2hand);

        // p3 initial hand
        ArrayList<Deck.Card> p3hand = new ArrayList<>();
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 5));
        p3hand.add(new Deck.Card("F", 15));
        p3hand.add(new Deck.Card("D", 5));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("S", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("H", 10));
        p3hand.add(new Deck.Card("B", 15));
        p3hand.add(new Deck.Card("L", 20));
        game.getPlayers().get(2).setHand(p3hand);

        // p4 initial hand
        ArrayList<Deck.Card> p4hand = new ArrayList<>();
        p4hand.add(new Deck.Card("F", 5));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 15));
        p4hand.add(new Deck.Card("F", 40));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("D", 5));
        p4hand.add(new Deck.Card("S", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("H", 10));
        p4hand.add(new Deck.Card("B", 15));
        p4hand.add(new Deck.Card("L", 20));
        p4hand.add(new Deck.Card("E", 30));
        game.getPlayers().get(3).setHand(p4hand);

        game.overwriteQuestEventDeckCard(0, "Q", 2);
    }
}
