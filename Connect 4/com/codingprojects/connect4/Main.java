package com.codingprojects.connect4;

import java.util.EnumMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running...");

        Connect4Board connect4Board = new Connect4Board();
        PlayerColor playersTurn = PlayerColor.getNext(null);
        PlayerColor winner;
        PlayerColor[][] copyOfBoard;

        EnumMap<PlayerColor, Agent> players = new EnumMap<>(PlayerColor.class);
        players.put(PlayerColor.Red,    null);
        players.put(PlayerColor.Yellow, null);

        Agent currentPlayer;

        int     playerChosenColumn;
        boolean validMove;

        do {
            // Change who's turn.
            playersTurn   = PlayerColor.getNext(playersTurn);
            currentPlayer = players.get(playersTurn);

            // Copy the game board, so the player can browse move options.
            copyOfBoard = connect4Board.getCopyOfBoard();

            // Force the player to make a valid move.
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

    public static void testGame() {
        Connect4Board c4b = new Connect4Board();

        c4b.insertToken(0, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(1, PlayerColor.Yellow);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(1, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(2, PlayerColor.Yellow);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(3, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(2, PlayerColor.Yellow);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(2, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(3, PlayerColor.Yellow);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(0, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(3, PlayerColor.Yellow);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(3, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
    }
}