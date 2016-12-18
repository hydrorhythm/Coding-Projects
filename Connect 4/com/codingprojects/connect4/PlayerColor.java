package com.codingprojects.connect4;

public enum PlayerColor {
    None,
    Red,
    Yellow;

    public static PlayerColor getNext(PlayerColor currentPlayer) {
        return (currentPlayer == Red)? Yellow : Red;
    }

    public static PlayerColor first() {
        return Red;
    }
}