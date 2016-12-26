package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.IConnect4BoardInquiry;
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
    PrimitivePreventionAgent(PlayerColor color) {
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
    public int perceive(IConnect4BoardInquiry board) {

        super.perceive(board);


        // Check for columns that will allow this agent to win.
        int winningColumn = Agent.winningColumn(this.color, board);
        if (winningColumn != -1) return winningColumn;


        // Check possibility of the very next opponent winning in their next turn.
        int opponentWinCol = Agent.winningColumn(PlayerColor.getNext(this.color), board);
        if (opponentWinCol != -1) return opponentWinCol;


        // Finds all columns that, if used, allows the opponent to win in their next turn. These columns are unsafe to
        // play. The agent will store all columns that do not have this criteria, and will pick one at random.
        List<Integer> safeColumns = new ArrayList<>();
        for (int col = 0; col < board.numColumns(); ++col) {

            int numSpaces = Agent.numSpacesInColumn(board, col);
            PlayerColor opponentColor = PlayerColor.getNext(this.color);

            // If the column has exactly one empty space OR the position above the next available position in the column
            // will NOT give the opponent access to win, then it is safe to play in the column
            if (numSpaces > 0)
                if (numSpaces == 1 || !Agent.playerWinsGivenToken(board, opponentColor, col, numSpaces - 2))
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
}