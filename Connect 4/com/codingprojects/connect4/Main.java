package com.codingprojects.connect4;

import java.util.EnumMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running...");

        Connect4Board connect4Board = new Connect4Board();
        PlayerColor[][] copyOfBoard;

        EnumMap<PlayerColor, Agent> players = new EnumMap<>(PlayerColor.class);
        players.put(PlayerColor.Red,    null);
        players.put(PlayerColor.Yellow, null);

        PlayerColor playersTurn = PlayerColor.first();
        Agent currentPlayer;

        int         playerChosenColumn;
        boolean     validMove;
        PlayerColor winner;

        // Main Game Loop
        do {
            playersTurn   = PlayerColor.getNext(playersTurn);
            currentPlayer = players.get(playersTurn);
            copyOfBoard   = connect4Board.getCopyOfBoard();

            do {
                playerChosenColumn = currentPlayer.perceiveAndAct(copyOfBoard);
                validMove = connect4Board.insertToken(
                        playerChosenColumn,
                        currentPlayer.getPlayerColor()
                );
            } while (!validMove);

            winner = connect4Board.winner();
        } while (winner == PlayerColor.None);

        System.out.println(winner.name() + " is the winner!");
    }
}