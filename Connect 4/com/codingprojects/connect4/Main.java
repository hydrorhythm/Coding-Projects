package com.codingprojects.connect4;

import com.codingprojects.connect4.agents.Agent;
import com.codingprojects.connect4.agents.PrimitivePreventionAgent;
import com.codingprojects.connect4.agents.RandomAgent;

import java.util.*;

public class Main {

    private static final Random random = new Random(System.nanoTime());
    private static final int NUM_GAMES = 500000;

    public static void main(String[] args) {
        System.out.println("Running...");

        Connect4Board connect4Board = new Connect4Board();
        PlayerColor[][] copyOfBoard;

        EnumMap<PlayerColor, Agent> players = new EnumMap<>(PlayerColor.class);

        players.put(
                PlayerColor.Black,
                new RandomAgent(PlayerColor.Black, random.nextLong())
        );
        players.put(
                PlayerColor.White,
                new PrimitivePreventionAgent(PlayerColor.White, random.nextLong())
        );

        PlayerColor currPlrColor = PlayerColor.first();
        Agent currentPlayer;

        int         playerChosenColumn;
        boolean     validMove;
        PlayerColor winner;

        EnumMap<PlayerColor, Integer> gameStats = new EnumMap<>(PlayerColor.class);
        gameStats.put(PlayerColor.None,  0);
        gameStats.put(PlayerColor.White, 0);
        gameStats.put(PlayerColor.Black, 0);

        // Stats Loop
        for (int iteration = 1; iteration <= NUM_GAMES; ++iteration) {

            if (iteration % 10000 == 1) {
                System.out.println("Starting game " +iteration+ " of " +NUM_GAMES);
            }

            // Main Game Loop
            do {
                currPlrColor = PlayerColor.getNext(currPlrColor);
                currentPlayer = players.get(currPlrColor);
                copyOfBoard = connect4Board.getCopyOfBoard();

                do {
                    playerChosenColumn = currentPlayer.perceive(copyOfBoard);
                    validMove = connect4Board.insertToken(
                            playerChosenColumn,
                            currentPlayer.getPlayerColor()
                    );
                } while (!validMove);

                winner = connect4Board.winner();
            } while (winner == PlayerColor.None && !connect4Board.isFull());

            gameStats.put(winner, gameStats.remove(winner) + 1);
            connect4Board.drop();
        }

        System.out.println("After " +NUM_GAMES+ " games of Connect 4:");
        System.out.println("Draws: ");
        System.out.println("  " + gameStats.get(PlayerColor.None).toString() + " game(s) with no winner");
        System.out.println("White: ");
        System.out.println("  " + players.get(PlayerColor.White).getClass().toString());
        System.out.println("  " + gameStats.get(PlayerColor.White).toString() + " wins");
        System.out.println("Black: ");
        System.out.println("  " + players.get(PlayerColor.Black).getClass().toString());
        System.out.println("  " + gameStats.get(PlayerColor.Black).toString() + " wins");
    }
}