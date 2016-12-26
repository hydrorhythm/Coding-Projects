package com.codingprojects.connect4;

import com.codingprojects.connect4.agents.Agent;
import com.codingprojects.connect4.agents.PerfectAgent;
import com.codingprojects.connect4.agents.PrimitivePreventionAgent;
import com.codingprojects.connect4.agents.RandomAgent;

import java.util.*;

public class Main {

    private static final Random random = new Random(System.nanoTime());
    private static final int NUM_GAMES = 1000000;

    public static void main(String[] args) {
        System.out.println("Running...");

        Connect4Board connect4Board = new Connect4Board();

        EnumMap<PlayerColor, Agent> players = new EnumMap<>(PlayerColor.class);
        players.put(PlayerColor.Black,
                new PerfectAgent(PlayerColor.Black)
        );
        players.put(PlayerColor.White,
                new PrimitivePreventionAgent(PlayerColor.White, random.nextLong())
        );

        PlayerColor currPlrColor = PlayerColor.first();
        Agent currentPlayer;

        int         playerChosenColumn;
        boolean     validMove;
        PlayerColor winner;

        EnumMap<GameStatistics, Integer> gameStatistics = new EnumMap<>(GameStatistics.class);
        gameStatistics.put(GameStatistics.Ties,  0);
        gameStatistics.put(GameStatistics.BlackWins, 0);
        gameStatistics.put(GameStatistics.WhiteWins, 0);

        // Stats Loop
        for (int iteration = 1; iteration <= NUM_GAMES; ++iteration) {

            if (iteration % 10000 == 0  ){
                System.out.println("Starting game " +iteration+ " of " +NUM_GAMES);
            }

            // Main Game Loop
            do {
                currPlrColor = PlayerColor.getNext(currPlrColor);
                currentPlayer = players.get(currPlrColor);

                do {
                    playerChosenColumn = currentPlayer.perceive(connect4Board);
                    validMove = connect4Board.insertToken(
                            playerChosenColumn,
                            currentPlayer.getPlayerColor()
                    );
                } while (!validMove);

                winner = connect4Board.winner();
            } while (winner == PlayerColor.None && !connect4Board.isFull());

            // Update statistics
            // TODO statistics for average game length given a
            switch (winner) {
                case Black:
                    int prevNumBlkWins = gameStatistics.remove(GameStatistics.BlackWins);
                    gameStatistics.put(GameStatistics.BlackWins, prevNumBlkWins + 1);
                    break;
                case White:
                    int prevNumWhtWins = gameStatistics.remove(GameStatistics.WhiteWins);
                    gameStatistics.put(GameStatistics.WhiteWins, prevNumWhtWins + 1);
                    break;
                case None:
                    int prevNumTies = gameStatistics.remove(GameStatistics.Ties);
                    gameStatistics.put(GameStatistics.Ties, prevNumTies + 1);
                    break;
            }


            connect4Board.drop();
        }

        System.out.println("After " +NUM_GAMES+ " games of Connect 4:");
        System.out.println("Draws: ");
        System.out.println("  " + gameStatistics.get(GameStatistics.Ties).toString() + " game(s) with no winner");
        System.out.println("White: ");
        System.out.println("  " + players.get(PlayerColor.White).getClass().toString());
        System.out.println("  " + gameStatistics.get(GameStatistics.WhiteWins).toString() + " wins");
        System.out.println("Black: ");
        System.out.println("  " + players.get(PlayerColor.Black).getClass().toString());
        System.out.println("  " + gameStatistics.get(GameStatistics.BlackWins).toString() + " wins");
    }
}