package com.codingprojects.connect4;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running...");
        Connect4Board c4b = new Connect4Board();

        c4b.insertToken(0, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(1, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(1, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(2, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(3, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(2, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(2, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(3, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(0, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(3, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());

        c4b.insertToken(3, PlayerColor.Red);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
        c4b.insertToken(0, PlayerColor.Black);
        System.out.println(c4b.printableBoard());
        System.out.println(c4b.winner().name());
    }
}