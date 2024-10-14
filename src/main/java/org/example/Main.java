package org.example;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(4);
        game.distributeCards();

        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);

        game.gameStart(output);

        while (!game.getWinner()) {
            game.promptPlayer(input, output);
        }
    }
}