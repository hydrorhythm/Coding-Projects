package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.Connect4BoardInquiry;
import com.codingprojects.connect4.PlayerColor;

import java.util.*;

/**
 * The PrimitivePreventionAgent will select moves that prevent the opponent from immediately winning.
 *
 *   1. The agent will first try to select a column that will allow it to win immediately. This will definitely prevent
 *   future agents from winning.
 *
 *   2. The agent will try to find a column that the next opponent can select and immediately win. If one is found, the
 *   agent will play in that column. Only the very next opponent is checked in this stage.
 *
 *   3. The agent will try to find all columns that, when a token is dropped in, alter the board in such a way that
 *   allows the next opponent to immediately win. The agent will keep record of all the columns satisfy this condition
 *   and will randomly select a column that both (a) not in this list and (b) a column that is not already full
 *   as its next move.
 *
 * This solution is not very elegant, but demonstrates some simple decision making that a person typically makes when
 * playing Connect 4.
 *
 * Created by austi on 2016-12-19.
 */
public class PrimitivePreventionAgent extends Agent {

    private Random random;


    /**
     * Initializes this agent with a token color.
     *
     * @param color Color of the tokens used by this player.
     */
    public PrimitivePreventionAgent(PlayerColor color) {
        this(color, System.nanoTime());
    }


    /**
     * Initializes this agent with a token color and with a seed for random number generation.
     *
     * @param color Color of the tokens used by this player.
     * @param seed Seed for random number generation.
     */
    public PrimitivePreventionAgent(PlayerColor color, long seed) {
        super(color);
        this.random = new Random(seed);
    }


    @Override
    public int perceive(Connect4BoardInquiry board) {

        super.perceive(board);


        // Check for columns that will allow this agent to win.
        int winningColumn = this.winningColumn(this.color, board);
        if (winningColumn != -1) return winningColumn;


        // Check possibility of the very next opponent winning in their next turn.
        int opponentWinCol = this.winningColumn(PlayerColor.getNext(this.color), board);
        if (opponentWinCol != -1) return opponentWinCol;


        // Finds all columns that, if used, allows the opponent to win in their next turn. Then picks
        List<Integer> safeColumns = new ArrayList<>();
        for (int col = 0; col < board.numColumns(); ++col) {

            int numSpaces = board.numSpacesInColumn(col);
            PlayerColor opponentColor = PlayerColor.getNext(this.color);

            // If the column has exactly one empty space OR the position above the next available position in the column
            // will NOT give the opponent access to win, then it is safe to play in the column
            if (numSpaces > 0)
                if (numSpaces == 1 || !this.playerWinsGivenToken(board, opponentColor, col, numSpaces - 2))
                    safeColumns.add(col);
        }
        if (safeColumns.size() > 0) return safeColumns.get(random.nextInt(safeColumns.size()));


        // If execution reaches this point, the other player is guarenteed to win.
        for (int firstValidColumn = 0; firstValidColumn < board.numColumns(); ++firstValidColumn)
            if (board.getTokenColor(firstValidColumn,0) == PlayerColor.None)
                return firstValidColumn;


        // If execution reaches this point, there are no valid columns.
        return -1;
    }


    /**
     * Will return the number of a column that will allow this agent to win on this turn. Returns -1 if no columns will
     * allow this agent to win this turn.
     *
     * @param board Current state of the Connect 4 board.
     * @return Returns a valid column number if it will allow the agent to win this turn. Returns -1 if no columns
     * will allow this agent to win.
     */
    private int winningColumn(PlayerColor player, Connect4BoardInquiry board) {

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
    private boolean playerWinsGivenToken(Connect4BoardInquiry connect4Board, PlayerColor player, int col, int row) {

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
}