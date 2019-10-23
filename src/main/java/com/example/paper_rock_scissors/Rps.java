package com.example.paper_rock_scissors;

import java.util.*;

public class Rps {

    private int computerRoundResult;
    private int playerRoundResult;
    private int rundNumber;
    List<Move> playerMoves = new ArrayList<>();
    List<Integer> playerRoundResults = new ArrayList<>();
    private Map<GameRound, RoundResult> gameMap = new HashMap<>();
    private Map<Move, Move> reactionOnPlayerMove = new HashMap<>();

    public Rps() {
        gameMap.put(new GameRound(Move.STONE, Move.PAPER), RoundResult.PLAYER_WIN);
        gameMap.put(new GameRound(Move.STONE, Move.SCISSORS), RoundResult.COMPUTER_WIN);
        gameMap.put(new GameRound(Move.STONE, Move.STONE), RoundResult.TIE);

        gameMap.put(new GameRound(Move.PAPER, Move.STONE), RoundResult.COMPUTER_WIN);
        gameMap.put(new GameRound(Move.PAPER, Move.SCISSORS), RoundResult.PLAYER_WIN);
        gameMap.put(new GameRound(Move.PAPER, Move.PAPER), RoundResult.TIE);

        gameMap.put(new GameRound(Move.SCISSORS, Move.STONE), RoundResult.PLAYER_WIN);
        gameMap.put(new GameRound(Move.SCISSORS, Move.PAPER), RoundResult.COMPUTER_WIN);
        gameMap.put(new GameRound(Move.SCISSORS, Move.SCISSORS), RoundResult.TIE);

        reactionOnPlayerMove.put(Move.STONE, Move.PAPER);
        reactionOnPlayerMove.put(Move.PAPER, Move.SCISSORS);
        reactionOnPlayerMove.put(Move.SCISSORS, Move.STONE);
    }


    public void playRps(Player player, Player computer, int numberOfWonRunds) throws Exception {
        boolean end = false;
        int computerResult = 0;
        int playerResult = 0;

        while (!end) {
            System.out.println("Wykonaj ruch");

            Move playerMove = player.getMove();
            Move computerMove = getComputerMove(computer);

            playerMoves.add(playerMove);

            RoundResult roundResult = gameMap.get(new GameRound(computerMove, playerMove));

            switch (roundResult) {
                case COMPUTER_WIN:
                    computerRoundResult = 1;
                    playerRoundResult = 0;
                    break;
                case PLAYER_WIN:
                    computerRoundResult = 0;
                    playerRoundResult = 1;
                    break;
            }

            computerResult += computerRoundResult;
            playerResult += playerRoundResult;
            playerRoundResults.add(playerRoundResult);

            rundNumber++;
            if (Math.max(playerResult, computerResult) == numberOfWonRunds) {
                end = true;
            }

            System.out.println("Wykonałeś ruch: " + playerMove.getTranslation()
                    + ", Twój przeciwnik zaś: " + computerMove.getTranslation());
            System.out.println("Wynik tej rundy to: " + player.getName()
                    + " " + playerRoundResult + ", " + computer.getName()
                    + " " + computerRoundResult);
            System.out.println("Masymalna liczba rund wygranych przez jednego z przeciwników wynosi obecnie: "
                    + Math.max(playerResult, computerResult));

        }

        Player winner;
        if (computerResult > playerResult) {
            winner = computer;
        } else {
            winner = player;
        }
        System.out.println("Wynik " + player.getName() + ": " + playerResult);
        System.out.println("Wynik " + computer.getName() + ": " + computerResult);
        System.out.println("Zwycięzcą jest " + winner.getName() + "!");

        scanEndGameDecision(player, computer);

    }

    public char scanEndGameDecision(Player player, Player computer) throws Exception {
        List<Character> availableResults = new ArrayList(Arrays.asList('n', 'x'));
        Scanner endGameScanner = new Scanner(System.in);
        Character result;
        do {
            System.out.println("Jeśli chcesz rozpocząć nową grę wciśnij n, aby zakończyć wybierz x");
            result = endGameScanner.next().charAt(0);
        } while (!availableResults.contains(result));

        if (result == 'n') {
            System.out.println("Do ilu wygranych rund gramy?");
            int numberOfWonRunds = scanWonRounds();
            playRps(player, computer, numberOfWonRunds);
        } else {
            System.out.println("Koniec gry");
        }

        return result;

    }

    public Move getComputerMove(Player computer) {
        Move computerMove = null;

        if (rundNumber >= 2 && playerRoundResult == 1) {
            computerMove = reactionOnPlayerMove.get(playerMoves.get(rundNumber - 1));
        } else if (rundNumber >= 2 && (playerRoundResults.get(rundNumber - 1) + playerRoundResults.get(rundNumber - 2)) == 0
                && playerMoves.get(rundNumber - 2).

                equals(playerMoves.get(rundNumber - 1))) {
            while (computerMove != reactionOnPlayerMove.get(playerMoves.get(rundNumber - 1))) {
                computerMove = computer.randomMove();
            }
            computerMove = computer.randomMove();
        } else {
            computerMove = computer.randomMove();
        }

        return computerMove;

    }


    public static int scanWonRounds() {
        int wonRunds = 0;
        boolean ok = false;
        while (!ok) {
            try {
                Scanner scanner = new Scanner(System.in);
                wonRunds = scanner.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println("Wprowadź liczbę całkowitą");
            }
        }
        return wonRunds;
    }

}
