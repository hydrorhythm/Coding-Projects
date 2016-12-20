package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.Connect4Board;
import com.codingprojects.connect4.PlayerColor;

import java.security.InvalidParameterException;
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


    /**
     * Will return the number of a column that will allow this agent to win on this turn. Returns -1 if no columns will
     * allow this agent to win this turn.
     *
     * @param board Current state of the Connect 4 board.
     * @return Returns a valid column number if it will allow the agent to win this turn. Returns -1 if no columns
     * will allow this agent to win.
     */
    public int winningColumn(PlayerColor player, PlayerColor[][] board) {

        for (int col = 0; col < board.length; ++col) {

            // Increment row until row is equal to the length of the column, or a token that is not air is found
            // Then subtract 1. This finds the next blank space in the column.
            int row = 0;
            while (row < board[col].length && board[col][row] == PlayerColor.None) {
                row += 1;
            }
            row -= 1;

            if (row != -1) {
                // Column is not yet full.

                board[col][row] = player;

                PlayerColor winner = Connect4Board.winner(board);

                board[col][row] = PlayerColor.None;
                if (winner == player) return col;
            }
        }

        return -1;
    }

    @Override
    public int perceive(PlayerColor[][] board) {

        super.perceive(board);

        // Check for columns that will allow this agent to win.
        int winningColumn = this.winningColumn(this.color, board);
        if (winningColumn != -1) {
            return winningColumn;
        }

        // Check possibility of the very next opponent winning in their next turn.
        int opponentWinCol = winningColumn(PlayerColor.getNext(this.color), board);
        if (opponentWinCol != -1) {
            return opponentWinCol;
        }

        // Finds all columns that, if used, allows the opponent to win in their next turn.
        List<Integer> goodColumns = new ArrayList<>();
        for (int col = 0; col < board.length; ++col) {
            int row = 1;
            while (row < board[col].length && board[col][row] == PlayerColor.None) {
                row += 1;
            }
            row -= 1;

            if (row >= 1) {
                board[col][row  ] = this.color;
                board[col][row-1] = PlayerColor.getNext(this.color);

                PlayerColor winnerTest = Connect4Board.winner(board);
                if (winnerTest == PlayerColor.None || winnerTest == this.color)
                    goodColumns.add(col);

                board[col][row  ] = PlayerColor.None;
                board[col][row-1] = PlayerColor.None;

            }
        }

        int firstValidColumn = 0; // If it comes down to this, I've already lost.
        for (; firstValidColumn < board.length; ++firstValidColumn)
            if (board[firstValidColumn][0] == PlayerColor.None)
                break;

        return (goodColumns.size() > 0)?
                goodColumns.get(random.nextInt(goodColumns.size())) :
                firstValidColumn;
    }

}
