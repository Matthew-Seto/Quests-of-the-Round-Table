package com.example.messaging_stomp_websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
public class GreetingController implements InitializingBean {

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Autowired
    private SimpMessagingTemplate playerTemplate;

    @Autowired
    private BlockingQueue<HelloMessage> messageQueue = new LinkedBlockingQueue<>();

    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
        // Add the incoming message to the queue
        messageQueue.put(message);
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

    private Game currentGame;

    @MessageMapping("/disconnect")
    public void handleDisconnect() {
        if (currentGame != null) {
            currentGame.reset();  // Assuming you have a reset method in your Game class to reset the state
            currentGame = null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Create a thread to simulate a delay before sending a message
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String firstMessage = waitForMessage();
            System.out.println(firstMessage);

            switch (firstMessage) {
                case "scenerio1":
                    //Start game, decks are created, hands of the 4 players are set up with random cards
                    Game game1 = new Game(4, messageTemplate, messageQueue, playerTemplate);
                    game1.distributeCards();

                    // p1 initial hand
                    ArrayList<Deck.Card> game1p1hand = new ArrayList<>();
                    game1p1hand.add(new Deck.Card("F", 5));
                    game1p1hand.add(new Deck.Card("F", 5));
                    game1p1hand.add(new Deck.Card("F", 15));
                    game1p1hand.add(new Deck.Card("F", 15));
                    game1p1hand.add(new Deck.Card("D", 5));
                    game1p1hand.add(new Deck.Card("S", 10));
                    game1p1hand.add(new Deck.Card("S", 10));
                    game1p1hand.add(new Deck.Card("H", 10));
                    game1p1hand.add(new Deck.Card("H", 10));
                    game1p1hand.add(new Deck.Card("B", 15));
                    game1p1hand.add(new Deck.Card("B", 15));
                    game1p1hand.add(new Deck.Card("L", 20));
                    game1.getPlayers().get(0).setHand(game1p1hand);

                    // p2 initial hand
                    ArrayList<Deck.Card> game1p2hand = new ArrayList<>();
                    game1p2hand.add(new Deck.Card("F", 5));
                    game1p2hand.add(new Deck.Card("F", 5));
                    game1p2hand.add(new Deck.Card("F", 15));
                    game1p2hand.add(new Deck.Card("F", 15));
                    game1p2hand.add(new Deck.Card("F", 40));
                    game1p2hand.add(new Deck.Card("D", 5));
                    game1p2hand.add(new Deck.Card("S", 10));
                    game1p2hand.add(new Deck.Card("H", 10));
                    game1p2hand.add(new Deck.Card("H", 10));
                    game1p2hand.add(new Deck.Card("B", 15));
                    game1p2hand.add(new Deck.Card("B", 15));
                    game1p2hand.add(new Deck.Card("E", 30));
                    game1.getPlayers().get(1).setHand(game1p2hand);

                    // p3 initial hand
                    ArrayList<Deck.Card> game1p3hand = new ArrayList<>();
                    game1p3hand.add(new Deck.Card("F", 5));
                    game1p3hand.add(new Deck.Card("F", 5));
                    game1p3hand.add(new Deck.Card("F", 5));
                    game1p3hand.add(new Deck.Card("F", 15));
                    game1p3hand.add(new Deck.Card("D", 5));
                    game1p3hand.add(new Deck.Card("S", 10));
                    game1p3hand.add(new Deck.Card("S", 10));
                    game1p3hand.add(new Deck.Card("S", 10));
                    game1p3hand.add(new Deck.Card("H", 10));
                    game1p3hand.add(new Deck.Card("H", 10));
                    game1p3hand.add(new Deck.Card("B", 15));
                    game1p3hand.add(new Deck.Card("L", 20));
                    game1.getPlayers().get(2).setHand(game1p3hand);

                    // p4 initial hand
                    ArrayList<Deck.Card> game1p4hand = new ArrayList<>();
                    game1p4hand.add(new Deck.Card("F", 5));
                    game1p4hand.add(new Deck.Card("F", 15));
                    game1p4hand.add(new Deck.Card("F", 15));
                    game1p4hand.add(new Deck.Card("F", 40));
                    game1p4hand.add(new Deck.Card("D", 5));
                    game1p4hand.add(new Deck.Card("D", 5));
                    game1p4hand.add(new Deck.Card("S", 10));
                    game1p4hand.add(new Deck.Card("H", 10));
                    game1p4hand.add(new Deck.Card("H", 10));
                    game1p4hand.add(new Deck.Card("B", 15));
                    game1p4hand.add(new Deck.Card("L", 20));
                    game1p4hand.add(new Deck.Card("E", 30));
                    game1.getPlayers().get(3).setHand(game1p4hand);

                    // draw quest of 4
                    game1.overwriteQuestEventDeckCard(0, "Q", 4);

                    // make sure game1 doesn't draw another card that may affect outcome
                    game1.overwriteEventDeckCard(1, "NULL", "");

                    // rig adventure deck cards
                    // Stage 1:
                    game1.overwriteAdventureDeckCard(0, "F", 30);
                    game1.overwriteAdventureDeckCard(1, "S", 10);
                    game1.overwriteAdventureDeckCard(2, "B", 15);

                    // Stage 2:
                    game1.overwriteAdventureDeckCard(3, "F", 10);
                    game1.overwriteAdventureDeckCard(4, "L", 20);
                    game1.overwriteAdventureDeckCard(5, "L", 20);

                    // Stage 3:
                    game1.overwriteAdventureDeckCard(6, "B", 15);
                    game1.overwriteAdventureDeckCard(7, "S", 10);

                    // Stage 4:
                    game1.overwriteAdventureDeckCard(8, "F", 30);
                    game1.overwriteAdventureDeckCard(9, "L", 20);

                    // full up p2 hand with f5
                    game1.overwriteAdventureDeckCard(10, "F", 5);
                    game1.overwriteAdventureDeckCard(11, "F", 5);
                    game1.overwriteAdventureDeckCard(12, "F", 5);
                    game1.overwriteAdventureDeckCard(13, "F", 5);
                    game1.overwriteAdventureDeckCard(14, "F", 5);
                    game1.overwriteAdventureDeckCard(15, "F", 5);
                    game1.overwriteAdventureDeckCard(16, "F", 5);
                    game1.overwriteAdventureDeckCard(17, "F", 5);
                    game1.overwriteAdventureDeckCard(18, "F", 5);
                    game1.overwriteAdventureDeckCard(19, "F", 5);
                    game1.overwriteAdventureDeckCard(20, "F", 5);
                    game1.overwriteAdventureDeckCard(21, "F", 5);
                    game1.overwriteAdventureDeckCard(22, "F", 5);
                    game1.overwriteAdventureDeckCard(23, "F", 5);
                    game1.overwriteAdventureDeckCard(24, "F", 5);

                    game1.gameStart();

                    game1.promptPlayer();
                    game1.displayHandOfAllPlayers();
   
                    break;
                case "scenerio2":
                    // Start scenario
                    Game game2 = new Game(4, messageTemplate, messageQueue, playerTemplate);
                    game2.distributeCards();
                    System.out.println(firstMessage);
                    // p1 initial hand
                    ArrayList<Deck.Card> game2p1hand = new ArrayList<>();
                    game2p1hand.add(new Deck.Card("F", 5));
                    game2p1hand.add(new Deck.Card("F", 5));
                    game2p1hand.add(new Deck.Card("F", 10));
                    game2p1hand.add(new Deck.Card("F", 10));
                    game2p1hand.add(new Deck.Card("F", 15));
                    game2p1hand.add(new Deck.Card("F", 15));
                    game2p1hand.add(new Deck.Card("D", 5));
                    game2p1hand.add(new Deck.Card("H", 10));
                    game2p1hand.add(new Deck.Card("H", 10));
                    game2p1hand.add(new Deck.Card("B", 15));
                    game2p1hand.add(new Deck.Card("B", 15));
                    game2p1hand.add(new Deck.Card("L", 20));
                    game2.getPlayers().get(0).setHand(game2p1hand);

                    // p2 initial hand
                    ArrayList<Deck.Card> game2p2hand = new ArrayList<>();
                    game2p2hand.add(new Deck.Card("F", 40));
                    game2p2hand.add(new Deck.Card("F", 50));
                    game2p2hand.add(new Deck.Card("H", 10));
                    game2p2hand.add(new Deck.Card("H", 10));
                    game2p2hand.add(new Deck.Card("S", 10));
                    game2p2hand.add(new Deck.Card("S", 10));
                    game2p2hand.add(new Deck.Card("S", 10));
                    game2p2hand.add(new Deck.Card("B", 15));
                    game2p2hand.add(new Deck.Card("B", 15));
                    game2p2hand.add(new Deck.Card("L", 20));
                    game2p2hand.add(new Deck.Card("L", 20));
                    game2p2hand.add(new Deck.Card("E", 30));
                    game2.getPlayers().get(1).setHand(game2p2hand);

                    // p3 initial hand
                    ArrayList<Deck.Card> game2p3hand = new ArrayList<>();
                    game2p3hand.add(new Deck.Card("F", 5));
                    game2p3hand.add(new Deck.Card("F", 5));
                    game2p3hand.add(new Deck.Card("F", 5));
                    game2p3hand.add(new Deck.Card("F", 5));
                    game2p3hand.add(new Deck.Card("D", 5));
                    game2p3hand.add(new Deck.Card("D", 5));
                    game2p3hand.add(new Deck.Card("D", 5));
                    game2p3hand.add(new Deck.Card("H", 10));
                    game2p3hand.add(new Deck.Card("H", 10));
                    game2p3hand.add(new Deck.Card("H", 10));
                    game2p3hand.add(new Deck.Card("H", 10));
                    game2p3hand.add(new Deck.Card("H", 10));
                    game2.getPlayers().get(2).setHand(game2p3hand);

                    // p4 initial hand
                    ArrayList<Deck.Card> game2p4hand = new ArrayList<>();
                    game2p4hand.add(new Deck.Card("F", 50));
                    game2p4hand.add(new Deck.Card("F", 70));
                    game2p4hand.add(new Deck.Card("H", 10));
                    game2p4hand.add(new Deck.Card("H", 10));
                    game2p4hand.add(new Deck.Card("S", 10));
                    game2p4hand.add(new Deck.Card("S", 10));
                    game2p4hand.add(new Deck.Card("S", 10));
                    game2p4hand.add(new Deck.Card("B", 15));
                    game2p4hand.add(new Deck.Card("B", 15));
                    game2p4hand.add(new Deck.Card("L", 20));
                    game2p4hand.add(new Deck.Card("L", 20));
                    game2p4hand.add(new Deck.Card("E", 30));
                    game2.getPlayers().get(3).setHand(game2p4hand);

                    game2.overwriteQuestEventDeckCard(0, "Q", 4);
                    game2.overwriteQuestEventDeckCard(1, "Q", 3);

                    // rig adventure deck cards
                    // Stage 1:
                    game2.overwriteAdventureDeckCard(0, "F", 5);
                    game2.overwriteAdventureDeckCard(1, "F", 40);
                    game2.overwriteAdventureDeckCard(2, "F", 10);

                    // Stage 2:
                    game2.overwriteAdventureDeckCard(3, "F", 10);
                    game2.overwriteAdventureDeckCard(4, "F", 30);

                    // Stage 3:
                    game2.overwriteAdventureDeckCard(5, "F", 30);
                    game2.overwriteAdventureDeckCard(6, "F", 15);

                    // Stage 4:
                    game2.overwriteAdventureDeckCard(7, "F", 15);
                    game2.overwriteAdventureDeckCard(8, "F", 20);

                    // P1 card draw
                    game2.overwriteAdventureDeckCard(9, "F", 5);
                    game2.overwriteAdventureDeckCard(10, "F", 10);

                    game2.overwriteAdventureDeckCard(11, "F", 15);
                    game2.overwriteAdventureDeckCard(12, "F", 15);
                    game2.overwriteAdventureDeckCard(13, "F", 20);
                    game2.overwriteAdventureDeckCard(14, "F", 20);
                    game2.overwriteAdventureDeckCard(15, "F", 20);
                    game2.overwriteAdventureDeckCard(16, "F", 20);
                    game2.overwriteAdventureDeckCard(17, "F", 25);
                    game2.overwriteAdventureDeckCard(18, "F", 25);
                    game2.overwriteAdventureDeckCard(19, "F", 30);
                    
                    // Quest 2 Stage 1:
                    game2.overwriteAdventureDeckCard(20, "D", 5);
                    game2.overwriteAdventureDeckCard(21, "D", 5);

                    // Stage 2:
                    game2.overwriteAdventureDeckCard(22, "F", 15);
                    game2.overwriteAdventureDeckCard(23, "F", 15);

                    // Stage 3:
                    game2.overwriteAdventureDeckCard(24, "F", 25);
                    game2.overwriteAdventureDeckCard(25, "F", 25);

                    // P3 card draw
                    game2.overwriteAdventureDeckCard(26, "F", 20);
                    game2.overwriteAdventureDeckCard(27, "F", 20);
                    game2.overwriteAdventureDeckCard(28, "F", 25);
                    game2.overwriteAdventureDeckCard(29, "F", 30);
                    game2.overwriteAdventureDeckCard(30, "S", 10);
                    game2.overwriteAdventureDeckCard(31, "B", 15);
                    game2.overwriteAdventureDeckCard(32, "B", 15);
                    game2.overwriteAdventureDeckCard(33, "L", 20);

                    game2.gameStart();

                    while (!game2.getWinner()) {
                        game2.promptPlayer();
                    }

                    game2.displayHandOfAllPlayers();
                    break;
                case "scenerio3":
                // Start scenario
                    Game game3 = new Game(4, messageTemplate, messageQueue, playerTemplate);
                    game3.distributeCards();

                    // p1 initial hand
                    ArrayList<Deck.Card> game3p1hand = new ArrayList<>();
                    game3p1hand.add(new Deck.Card("F", 5));
                    game3p1hand.add(new Deck.Card("F", 5));
                    game3p1hand.add(new Deck.Card("F", 10));
                    game3p1hand.add(new Deck.Card("F", 10));
                    game3p1hand.add(new Deck.Card("F", 15));
                    game3p1hand.add(new Deck.Card("F", 15));
                    game3p1hand.add(new Deck.Card("F", 20));
                    game3p1hand.add(new Deck.Card("F", 20));
                    game3p1hand.add(new Deck.Card("D", 5));
                    game3p1hand.add(new Deck.Card("D", 5));
                    game3p1hand.add(new Deck.Card("D", 5));
                    game3p1hand.add(new Deck.Card("D", 5));
                    game3.getPlayers().get(0).setHand(game3p1hand);

                    // p2 initial hand
                    ArrayList<Deck.Card> game3p2hand = new ArrayList<>();
                    game3p2hand.add(new Deck.Card("F", 25));
                    game3p2hand.add(new Deck.Card("F", 30));
                    game3p2hand.add(new Deck.Card("H", 10));
                    game3p2hand.add(new Deck.Card("H", 10));
                    game3p2hand.add(new Deck.Card("S", 10));
                    game3p2hand.add(new Deck.Card("S", 10));
                    game3p2hand.add(new Deck.Card("S", 10));
                    game3p2hand.add(new Deck.Card("B", 15));
                    game3p2hand.add(new Deck.Card("B", 15));
                    game3p2hand.add(new Deck.Card("L", 20));
                    game3p2hand.add(new Deck.Card("L", 20));
                    game3p2hand.add(new Deck.Card("E", 30));
                    game3.getPlayers().get(1).setHand(game3p2hand);

                    // p3 initial hand
                    ArrayList<Deck.Card> game3p3hand = new ArrayList<>();
                    game3p3hand.add(new Deck.Card("F", 25));
                    game3p3hand.add(new Deck.Card("F", 30));
                    game3p3hand.add(new Deck.Card("H", 10));
                    game3p3hand.add(new Deck.Card("H", 10));
                    game3p3hand.add(new Deck.Card("S", 10));
                    game3p3hand.add(new Deck.Card("S", 10));
                    game3p3hand.add(new Deck.Card("S", 10));
                    game3p3hand.add(new Deck.Card("B", 15));
                    game3p3hand.add(new Deck.Card("B", 15));
                    game3p3hand.add(new Deck.Card("L", 20));
                    game3p3hand.add(new Deck.Card("L", 20));
                    game3p3hand.add(new Deck.Card("E", 30));
                    game3.getPlayers().get(2).setHand(game3p3hand);

                    // p4 initial hand
                    ArrayList<Deck.Card> game3p4hand = new ArrayList<>();
                    game3p4hand.add(new Deck.Card("F", 25));
                    game3p4hand.add(new Deck.Card("F", 30));
                    game3p4hand.add(new Deck.Card("F", 70));
                    game3p4hand.add(new Deck.Card("H", 10));
                    game3p4hand.add(new Deck.Card("H", 10));
                    game3p4hand.add(new Deck.Card("S", 10));
                    game3p4hand.add(new Deck.Card("S", 10));
                    game3p4hand.add(new Deck.Card("S", 10));
                    game3p4hand.add(new Deck.Card("B", 15));
                    game3p4hand.add(new Deck.Card("B", 15));
                    game3p4hand.add(new Deck.Card("L", 20));
                    game3p4hand.add(new Deck.Card("L", 20));
                    game3.getPlayers().get(3).setHand(game3p4hand);

                    game3.overwriteQuestEventDeckCard(0, "Q", 4);
                    game3.overwriteEventDeckCard(1, "E", "Plague: The player who draws this card immediately loses 2 shields.");
                    game3.overwriteEventDeckCard(2, "E", "Prosperity: All players immediately draw 2 adventure cards.");
                    game3.overwriteEventDeckCard(3, "E", "Queen's favor: The player who draws this card immediately draws 2 adventure cards.");
                    game3.overwriteQuestEventDeckCard(4, "Q", 3);

                    // rig adventure deck cards
                    // Stage 1:
                    game3.overwriteAdventureDeckCard(0, "F", 5);
                    game3.overwriteAdventureDeckCard(1, "F", 10);
                    game3.overwriteAdventureDeckCard(2, "F", 20);

                    // Stage 2:
                    game3.overwriteAdventureDeckCard(3, "F", 15);
                    game3.overwriteAdventureDeckCard(4, "F", 5);
                    game3.overwriteAdventureDeckCard(5, "F", 25);


                    // Stage 3:
                    game3.overwriteAdventureDeckCard(6, "F", 5);
                    game3.overwriteAdventureDeckCard(7, "F", 10);
                    game3.overwriteAdventureDeckCard(8, "F", 20);

                    // Stage 4:
                    game3.overwriteAdventureDeckCard(9, "F", 5);
                    game3.overwriteAdventureDeckCard(10, "F", 10);
                    game3.overwriteAdventureDeckCard(11, "F", 20);

                    // P1 card draw
                    game3.overwriteAdventureDeckCard(12, "F", 5);
                    game3.overwriteAdventureDeckCard(13, "F", 5);
                    game3.overwriteAdventureDeckCard(14, "F", 10);
                    game3.overwriteAdventureDeckCard(15, "F", 10);
                    game3.overwriteAdventureDeckCard(16, "F", 15);
                    game3.overwriteAdventureDeckCard(17, "F", 15);
                    game3.overwriteAdventureDeckCard(18, "F", 15);
                    game3.overwriteAdventureDeckCard(19, "F", 15);

                    // Prosperity Draw
                    game3.overwriteAdventureDeckCard(20, "F", 25);
                    game3.overwriteAdventureDeckCard(21, "F", 25);

                    game3.overwriteAdventureDeckCard(22, "H", 10);
                    game3.overwriteAdventureDeckCard(23, "S", 10);

                    game3.overwriteAdventureDeckCard(24, "B", 15);
                    game3.overwriteAdventureDeckCard(25, "F", 40);

                    game3.overwriteAdventureDeckCard(26, "D", 5);
                    game3.overwriteAdventureDeckCard(27, "D", 5);

                    // Queens favor draw
                    game3.overwriteAdventureDeckCard(28, "F", 30);
                    game3.overwriteAdventureDeckCard(29, "F", 25);
                    
                    // Quest 2 Stage 1:
                    game3.overwriteAdventureDeckCard(30, "B", 15);
                    game3.overwriteAdventureDeckCard(31, "H", 10);
                    game3.overwriteAdventureDeckCard(32, "F", 50);

                    // Stage 2:
                    game3.overwriteAdventureDeckCard(33, "S", 10);
                    game3.overwriteAdventureDeckCard(34, "S", 10);

                    // Stage 3:
                    game3.overwriteAdventureDeckCard(35, "F", 40);
                    game3.overwriteAdventureDeckCard(36, "F", 50);

                    // P1 card draw
                    game3.overwriteAdventureDeckCard(37, "H", 10);
                    game3.overwriteAdventureDeckCard(38, "H", 10);
                    game3.overwriteAdventureDeckCard(39, "H", 10);
                    game3.overwriteAdventureDeckCard(40, "S", 10);
                    game3.overwriteAdventureDeckCard(41, "S", 10);
                    game3.overwriteAdventureDeckCard(42, "S", 10);
                    game3.overwriteAdventureDeckCard(43, "S", 10);
                    game3.overwriteAdventureDeckCard(44, "F", 35);

                    game3.gameStart();

                    while (!game3.getWinner()) {
                        game3.promptPlayer();
                    }

                    game3.displayHandOfAllPlayers();
                    break;
                case "scenerio4":
                // Start scenario
                    Game game4 = new Game(4, messageTemplate, messageQueue, playerTemplate);
                    game4.distributeCards();

                    // p1 initial hand
                    ArrayList<Deck.Card> game4p1hand = new ArrayList<>();
                    game4p1hand.add(new Deck.Card("F", 50));
                    game4p1hand.add(new Deck.Card("F", 70));
                    game4p1hand.add(new Deck.Card("D", 5));
                    game4p1hand.add(new Deck.Card("D", 5));
                    game4p1hand.add(new Deck.Card("H", 10));
                    game4p1hand.add(new Deck.Card("H", 10));
                    game4p1hand.add(new Deck.Card("S", 10));
                    game4p1hand.add(new Deck.Card("S", 10));
                    game4p1hand.add(new Deck.Card("B", 15));
                    game4p1hand.add(new Deck.Card("B", 15));
                    game4p1hand.add(new Deck.Card("L", 20));
                    game4p1hand.add(new Deck.Card("L", 20));
                    game4.getPlayers().get(0).setHand(game4p1hand);

                    // p2 initial hand
                    ArrayList<Deck.Card> game4p2hand = new ArrayList<>();
                    game4p2hand.add(new Deck.Card("F", 5));
                    game4p2hand.add(new Deck.Card("F", 5));
                    game4p2hand.add(new Deck.Card("F", 10));
                    game4p2hand.add(new Deck.Card("F", 15));
                    game4p2hand.add(new Deck.Card("F", 15));
                    game4p2hand.add(new Deck.Card("F", 20));
                    game4p2hand.add(new Deck.Card("F", 20));
                    game4p2hand.add(new Deck.Card("F", 25));
                    game4p2hand.add(new Deck.Card("F", 30));
                    game4p2hand.add(new Deck.Card("F", 30));
                    game4p2hand.add(new Deck.Card("F", 40));
                    game4p2hand.add(new Deck.Card("E", 30));
                    game4.getPlayers().get(1).setHand(game4p2hand);

                    // p3 initial hand
                    ArrayList<Deck.Card> game4p3hand = new ArrayList<>();
                    game4p3hand.add(new Deck.Card("F", 5));
                    game4p3hand.add(new Deck.Card("F", 5));
                    game4p3hand.add(new Deck.Card("F", 10));
                    game4p3hand.add(new Deck.Card("F", 15));
                    game4p3hand.add(new Deck.Card("F", 15));
                    game4p3hand.add(new Deck.Card("F", 20));
                    game4p3hand.add(new Deck.Card("F", 20));
                    game4p3hand.add(new Deck.Card("F", 25));
                    game4p3hand.add(new Deck.Card("F", 25));
                    game4p3hand.add(new Deck.Card("F", 30));
                    game4p3hand.add(new Deck.Card("F", 40));
                    game4p3hand.add(new Deck.Card("L", 20));
                    game4.getPlayers().get(2).setHand(game4p3hand);

                    // p4 initial hand
                    ArrayList<Deck.Card> game4p4hand = new ArrayList<>();
                    game4p4hand.add(new Deck.Card("F", 5));
                    game4p4hand.add(new Deck.Card("F", 5));
                    game4p4hand.add(new Deck.Card("F", 10));
                    game4p4hand.add(new Deck.Card("F", 15));
                    game4p4hand.add(new Deck.Card("F", 15));
                    game4p4hand.add(new Deck.Card("F", 20));
                    game4p4hand.add(new Deck.Card("F", 20));
                    game4p4hand.add(new Deck.Card("F", 25));
                    game4p4hand.add(new Deck.Card("F", 25));
                    game4p4hand.add(new Deck.Card("F", 30));
                    game4p4hand.add(new Deck.Card("F", 50));
                    game4p4hand.add(new Deck.Card("E", 30));
                    game4.getPlayers().get(3).setHand(game4p4hand);

                    game4.overwriteQuestEventDeckCard(0, "Q", 2);
                    game4.overwriteQuestEventDeckCard(1, "Q", 3);

                    // rig adventure deck cards
                    // Stage 1:
                    game4.overwriteAdventureDeckCard(0, "F", 5);
                    game4.overwriteAdventureDeckCard(1, "F", 15);
                    game4.overwriteAdventureDeckCard(2, "F", 10);

                    // P1 draw
                    game4.overwriteAdventureDeckCard(3, "F", 5);
                    game4.overwriteAdventureDeckCard(4, "F", 10);
                    game4.overwriteAdventureDeckCard(5, "F", 15);
                    game4.overwriteAdventureDeckCard(6, "D", 5);

                    // Stage 4:
                    game4.overwriteAdventureDeckCard(7, "D", 5);
                    game4.overwriteAdventureDeckCard(8, "D", 5);

                    // P1 card draw
                    game4.overwriteAdventureDeckCard(9, "D", 5);
                    game4.overwriteAdventureDeckCard(10, "H", 10);

                    game4.overwriteAdventureDeckCard(11, "H", 10);
                    game4.overwriteAdventureDeckCard(12, "H", 10);
                    game4.overwriteAdventureDeckCard(13, "H", 10);
                    game4.overwriteAdventureDeckCard(14, "S", 10);
                    game4.overwriteAdventureDeckCard(15, "S", 10);
                    game4.overwriteAdventureDeckCard(16, "S", 10);
                    
                    game4.gameStart();

                    game4.promptPlayer();
                    
                    game4.displayHandOfAllPlayers();
                    break;
                case "default":
                    Game game = new Game(4, messageTemplate, messageQueue, playerTemplate);
                    game.distributeCards();

                    game.gameStart();

                    while (!game.getWinner()) {
                        game.promptPlayer();
                    }
                    break;
                default:
                    break;
            }
        }).start();
    }
}