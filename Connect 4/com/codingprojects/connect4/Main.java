package com.codingprojects.connect4;

import com.codingprojects.connect4.agents.Agent;
import com.codingprojects.connect4.agents.RandomAgent;

import java.util.*;

public class Main {

    private static final Random random = new Random(System.nanoTime());

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
                new RandomAgent(PlayerColor.White, random.nextLong())
        );

        PlayerColor currPlrColor = PlayerColor.first();
        Agent currentPlayer;

        int         playerChosenColumn;
        boolean     validMove;
        PlayerColor winner;

        // Main Game Loop
        do {
            currPlrColor  = PlayerColor.getNext(currPlrColor);
            currentPlayer = players.get(currPlrColor);
            copyOfBoard   = connect4Board.getCopyOfBoard();

            do {
                playerChosenColumn = currentPlayer.perceiveAndAct(copyOfBoard);
                validMove = connect4Board.insertToken(
                        playerChosenColumn,
                        currentPlayer.getPlayerColor()
                );
            } while (!validMove);

            winner = connect4Board.winner();
        } while (winner == PlayerColor.None && !connect4Board.isFull());

        System.out.println(connect4Board.printableBoard());
        System.out.println(winner.name() + " is the winner!");
    }
}