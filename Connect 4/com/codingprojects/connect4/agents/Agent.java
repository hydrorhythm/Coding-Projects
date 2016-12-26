package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.IConnect4BoardInquiry;
import com.codingprojects.connect4.PlayerColor;

import javax.swing.*;
import java.security.InvalidParameterException;

/**
 * Abstract class, defining the behavior of a decision unit for the connect 4 game. Can be implemented as either an
 * artificial intelligence or a human player.
 *
 * Created by austi on 2016-12-18.
 */
public abstract class Agent {

    /**
     * Determines which pieces belong to the player.
     */
    protected PlayerColor color;

    /**
     * Initializes this agent with a token color.
     *
     * @param color Color of the tokens used by this player.
     */
    public Agent(PlayerColor color) {
        this.color = color;
    }

    /**
     * Returns the color of this agent.
     *
     * @return The color of this agent.
     */
    public PlayerColor getPlayerColor() {
        return this.color;
    }

    /**
     * Allow the agent to process the state of the board before making a decision. Returns an integer, representing
     * the column that the player wishes to drop a token into.
     *
     * @param connect4Board A 2D array of type PlayerColor denoting the current state of the game. The first dimension is the
     *              boards column, and the second dimension is the boards row. Row 0 is the top of the board and row n-1
     *              is the bottom.
     *
     * @return An integer representing the column the player wishes to drop a token into.
     */
    public int perceive(IConnect4BoardInquiry connect4Board) {
        if (connect4Board.isFull()) {
            throw new InvalidParameterException("Cannot perform an action. Board is already full.");
        }
        return 0; // valid board return code.
    }


    /**
     * Will return the number of a column that will allow this agent to win on this turn. Returns -1 if no columns will
     * allow this agent to win this turn.
     *
     * @param board Current state of the Connect 4 board.
     * @return Returns a valid column number if it will allow the agent to win this turn. Returns -1 if no columns
     * will allow this agent to win.
     */
    static int winningColumn(PlayerColor player, IConnect4BoardInquiry board) {

        for (int col = 0; col < board.numColumns(); ++col) {

            // Increment row until row is equal to the length of the column, or a token that is not air is found
            // Then subtract 1. This finds the next blank space in the column.
            int row = 0;
            while (row < board.numRows() && board.getTokenColor(col, row) == PlayerColor.None) {
                row += 1;
            }
            row -= 1;

            if (row != -1 && playerWinsGivenToken(board, player, col, row))
                return col;
        }

        return -1;
    }


    /**
     * Returns true if a player will win if a token of their color is placed in the specified row and column of the
     * specified connect 4 board.
     *
     * @param connect4Board The current state of the board
     * @param player The color of the player
     * @param col The specified column where the player could play
     * @param row The specified row where a player could play
     * @return True if a token belonging to a player being placed in the specified column and row of the board will
     * cause them to win.
     */
    static boolean playerWinsGivenToken(IConnect4BoardInquiry connect4Board, PlayerColor player, int col, int row) {

        int [][] searchDirections = new int[][] {
                { 1, -1}, // SW to NE diagonal
                {-1,  1}, // NW to SE diagonal
                { 0,  1}, // N to S line
                { 1,  0}  // W to E line
        };

        for (int[] searchDirection : searchDirections) {

            int colDirec = searchDirection[0];
            int rowDirec = searchDirection[1];
            int numConsecutiveTokens = 0;

            // Check the line of 7 tokens that contains the specified index and the surrounding 6 indices along the
            // search path. Try to find a run of 4.
            for (int delta = -3; delta <= 3; ++delta) {

                int colWithOffset = col + (colDirec*delta);
                int rowWithOffset = row + (rowDirec*delta);

                // Index in bounds check.
                if (colWithOffset >= 0 && colWithOffset < connect4Board.numColumns() &&
                        rowWithOffset >= 0 && rowWithOffset < connect4Board.numRows()) {

                    PlayerColor currToken = connect4Board.getTokenColor(colWithOffset, rowWithOffset);
                    // delta == 0 means the search is currently checking the empty space where player could play.
                    if (delta == 0 || currToken == player) {
                        numConsecutiveTokens += 1;
                    } else {
                        numConsecutiveTokens = 0;
                    }
                }

                if (numConsecutiveTokens >= 4) return true;
            }
        }

        return false;
    }


    /**
     * Returns the number of empty spaces that are in the specified column.
     *
     * @param board Board whose column needs to be check for open positions.
     * @param col Column to check for spaces.
     * @return The number of empty spaces in the column.
     */
    static int numSpacesInColumn(IConnect4BoardInquiry board, int col) {

        if (col < 0 || col >= board.numColumns())
            throw new IndexOutOfBoundsException("Invalid column number passed to method.");

        for (int row = 0; row < board.numRows(); ++row)
            if (board.getTokenColor(col,row) != PlayerColor.None)
                return row;

        return board.numRows();
    }


}
