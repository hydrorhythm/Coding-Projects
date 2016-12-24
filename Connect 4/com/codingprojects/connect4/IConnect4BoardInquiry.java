package com.codingprojects.connect4;

/**
 * Interface defines external behaviors for the connect 4 board that do not change the state of the board.
 *
 * Created by austi on 2016-12-20.
 */
public interface Connect4BoardInquiry {

    /**
     * Returns the color of the player that has won the game. If both players have a connect 4 or neither do, then
     * this will return PlayerColor.None.
     *
     * @return the color of the winner.
     */
    PlayerColor winner();


    /**
     * Returns true if the board has no more available spaces.
     * @return True if the board has no more available spaces.
     */
    boolean isFull();


    /**
     * Returns the color of the token that's in the specified column and row of the connect 4 board.
     *
     * @param col Column, numbered 0 (Left) to N-1 (Right)
     * @param row Row, numbered 0 (Top) to M-1 (Bottom)
     * @return Color of the token in the specified row and column.
     */
    PlayerColor getTokenColor(int col, int row);


    /**
     * Returns the number of columns in the board.
     * @return The number of columns in the board.
     */
    int numColumns();


    /**
     * Returns the number of rows in the board.
     * @return The number of rows in the board.
     */
    int numRows();


    /**
     * Returns the number of empty spaces that are in the specified column.
     * @param col Column to check for spaces.
     * @return The number of empty spaces in the column.
     */
    int numSpacesInColumn(int col);
}
