package com.example.paper_rock_scissors;

import java.util.*;

public class Player {


    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Move randomMove() {
        Random generator = new Random();
        final Move[] moves = Move.values();
        return moves[generator.nextInt(3)];
    }

    public Move getMove() throws java.lang.Exception {

        Move move = null;
        List<Integer> availableResults = new ArrayList(Arrays.asList(Move.values()));

        while (!availableResults.contains(move)) {

            try {
                Scanner moveScanner = new Scanner(System.in);
                final Move[] moves = Move.values();
                move = moves[moveScanner.nextInt() - 1];

            } catch (InputMismatchException e) {
                System.out.println("Wprowadź 1 - kamień, 2 - papier, 3 - nożyce");

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Ten ruch jest niedozwolony! Spróbuj ponownie.");
            }

        }
        return move;

    }
}
