//package org.example;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.Map;
//
//
//@RestController
//@CrossOrigin(origins = "*")
//public class GameController {
//
//    private Game game;
//
//    @GetMapping("/scenarioTwo")
//    public String scenarioTwo(){
//        // Start scenario
//        Game game = new Game(4);
//        game.distributeCards();
//
//        // p1 initial hand
//        ArrayList<Deck.Card> p1hand = new ArrayList<>();
//        p1hand.add(new Deck.Card("F", 5));
//        p1hand.add(new Deck.Card("F", 5));
//        p1hand.add(new Deck.Card("F", 10));
//        p1hand.add(new Deck.Card("F", 10));
//        p1hand.add(new Deck.Card("F", 15));
//        p1hand.add(new Deck.Card("F", 15));
//        p1hand.add(new Deck.Card("D", 5));
//        p1hand.add(new Deck.Card("H", 10));
//        p1hand.add(new Deck.Card("H", 10));
//        p1hand.add(new Deck.Card("B", 15));
//        p1hand.add(new Deck.Card("B", 15));
//        p1hand.add(new Deck.Card("L", 20));
//        game.getPlayers().get(0).setHand(p1hand);
//
//        // p2 initial hand
//        ArrayList<Deck.Card> p2hand = new ArrayList<>();
//        p2hand.add(new Deck.Card("F", 40));
//        p2hand.add(new Deck.Card("F", 50));
//        p2hand.add(new Deck.Card("H", 10));
//        p2hand.add(new Deck.Card("H", 10));
//        p2hand.add(new Deck.Card("S", 10));
//        p2hand.add(new Deck.Card("S", 10));
//        p2hand.add(new Deck.Card("S", 10));
//        p2hand.add(new Deck.Card("B", 15));
//        p2hand.add(new Deck.Card("B", 15));
//        p2hand.add(new Deck.Card("L", 20));
//        p2hand.add(new Deck.Card("L", 20));
//        p2hand.add(new Deck.Card("E", 30));
//        game.getPlayers().get(1).setHand(p2hand);
//
//        // p3 initial hand
//        ArrayList<Deck.Card> p3hand = new ArrayList<>();
//        p3hand.add(new Deck.Card("F", 5));
//        p3hand.add(new Deck.Card("F", 5));
//        p3hand.add(new Deck.Card("F", 5));
//        p3hand.add(new Deck.Card("F", 5));
//        p3hand.add(new Deck.Card("D", 5));
//        p3hand.add(new Deck.Card("D", 5));
//        p3hand.add(new Deck.Card("D", 5));
//        p3hand.add(new Deck.Card("H", 10));
//        p3hand.add(new Deck.Card("H", 10));
//        p3hand.add(new Deck.Card("H", 10));
//        p3hand.add(new Deck.Card("H", 10));
//        p3hand.add(new Deck.Card("H", 10));
//        game.getPlayers().get(2).setHand(p3hand);
//
//        // p4 initial hand
//        ArrayList<Deck.Card> p4hand = new ArrayList<>();
//        p4hand.add(new Deck.Card("F", 50));
//        p4hand.add(new Deck.Card("F", 70));
//        p4hand.add(new Deck.Card("H", 10));
//        p4hand.add(new Deck.Card("H", 10));
//        p4hand.add(new Deck.Card("S", 10));
//        p4hand.add(new Deck.Card("S", 10));
//        p4hand.add(new Deck.Card("S", 10));
//        p4hand.add(new Deck.Card("B", 15));
//        p4hand.add(new Deck.Card("B", 15));
//        p4hand.add(new Deck.Card("L", 20));
//        p4hand.add(new Deck.Card("L", 20));
//        p4hand.add(new Deck.Card("E", 30));
//        game.getPlayers().get(3).setHand(p4hand);
//
//        StringWriter logWriter = new StringWriter();
//        PrintWriter output = new PrintWriter(logWriter);
//
//        game.gameStart(output);
//        output.flush();
//        return logWriter.toString();
//    }
//
//    @GetMapping("/start")
//    public String startGame() {
//        this.game = new Game(4);
//        this.game.distributeCards();
//
//        StringWriter logWriter = new StringWriter();
//        PrintWriter output = new PrintWriter(logWriter);
//
//        game.gameStart(output);
//        output.flush();
//        return logWriter.toString();
//    }
//
//    @GetMapping("/onePressed")
//    int onePressed(){
//        return 1;
//    }
//
//    @GetMapping("/promptPlayer")
//    private String promptPlayer(@RequestParam int cardIndex) {
//        if (Objects.equals(game.currentEventCard.description, "Prosperity: All players immediately draw 2 adventure cards.")){
//            game.drawAdventureCardsForAllPlayers( 2);
//            for (Player player : game.getPlayers()) {
//                int MAX = 12;
//                if(player.getHandSize()>= 12){
//                    int amountToTrim = player.getHandSize() - MAX;
//                    trim(amountToTrim);
//                }
//                game.trimIfNeeded(player,input,output);
//                game.flushDisplay(output);
//            }
//        }
//        game.trimIfNeeded(getCurrentPlayer(),input, output);
//
//        if (game.QcardDrawn) {
//            handleQuestSponsorship(input, output);
//        } else {
//            output.print("Hit <return> to end turn: ");
//            output.flush();
//
//            String inputStr = input.nextLine();
//            if (inputStr.isEmpty()) {
//                endCurrentPlayerTurn(output);
//            }
//        }
//
//        output.flush();
//        return logWriter.toString();
//        return("");
//    }
//    @PostMapping("/discardCard")
//    public String discardCard(@RequestParam int playerId, @RequestParam int cardIndex) {
//        Player player = this.game.getPlayers().get(playerId);
//        if (player.getHandSize() > 12) {
//            if (cardIndex >= 0 && cardIndex < player.getHandSize()) {
//                player.getHand().remove(cardIndex);
//                return "Card discarded.";
//            } else {
//                return "Invalid card index.";
//            }
//        }
//        return "No need to discard cards.";
//    }
//
//    @PostMapping("/input")
//    public String handleInput(@RequestBody String input) {
//
//        // Return the response
//        return input;
//    }
//
//    private String gameLogicMethod(String input) {
//        // Implement your game logic here and use the input string
//        return "Hit received with input: " + input;
//    }
//
//
////    @GetMapping("/start")
////    public void startGame() {
////        this.game = new Game(4);
////        this.game.distributeCards();
////        sendGameLog("Game started with four players");
////
////        StringWriter gameStartOutput = new StringWriter();
////        game.gameStart(new PrintWriter(gameStartOutput));
////        sendGameLog(gameStartOutput.toString());
////
////        executor.submit(() -> {
////            while (!game.getWinner()) {
////                synchronized (inputLock) {
////                    while (true) {
////                        try {
////                            if (!(game.getUserInput() == null)) break;
////                        } catch (InterruptedException e) {
////                            throw new RuntimeException(e);
////                        }
////                        try {
////                            inputLock.wait();
////                        } catch (InterruptedException e) {
////                            Thread.currentThread().interrupt();
////                            return;
////                        }
////                    }
////
////                    StringWriter output = new StringWriter();
////                    PrintWriter writer = new PrintWriter(output);
////                    game.promptPlayerWithInput(writer);
////                    sendGameLog(output.toString());
////                }
////            }
////        });
////    }
//
////    @GetMapping("/events")
////    public SseEmitter streamGameEvents() {
////        SseEmitter emitter = new SseEmitter();
////        emitters.add(emitter);
////        emitter.onCompletion(() -> emitters.remove(emitter));
////        emitter.onTimeout(() -> emitters.remove(emitter));
////        return emitter;
////    }
//
////    @PostMapping("/input")
////    public void handleInput(@RequestBody String input) {
////        synchronized (inputLock) {
////            game.setUserInput(input.trim());
////            inputLock.notifyAll();
////        }
////    }
////
////    @PostMapping("/promptPlayer")
////    public void promptPlayer() {
////        executor.submit(() -> {
////            try {
////                StringWriter output = new StringWriter();
////                PrintWriter writer = new PrintWriter(output);
////                game.promptPlayerWithInput(writer);
////                sendGameLog(output.toString());
////            } catch (Exception e) {
////                sendGameLog("An error occurred: " + e.getMessage());
////            }
////        });
////    }
////
////    private void sendGameLog(String log) {
////        for (SseEmitter emitter : emitters) {
////            try {
////                emitter.send(SseEmitter.event().data(log));
////            } catch (Exception e) {
////                emitters.remove(emitter);
////            }
////        }
////    }
//
//
//}