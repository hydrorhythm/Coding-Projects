package com.codingprojects.connect4;

/**
 * Specifies the possible colors for tokens in a connect 4 board. The classic colors are actually Red and Yellow, but
 * Black and White were chosen, since they have the same number of letters.
 */
public enum PlayerColor {
    None,
    Black,
    White;

    /**
     * Returns the color of the next player, given the color of the current player.
     *
     * @param currentPlayer Current player's color.
     * @return Next player's color.
     */
    public static PlayerColor getNext(PlayerColor currentPlayer) {
        return (currentPlayer == Black)? White : Black;
    }

    /**
     * Returns the color of the player that plays the first move.
     * @return The color of the player that plays the first move.
     */
    public static PlayerColor first() {
        return Black;
    }
}