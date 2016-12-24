package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.IConnect4BoardInquiry;
import com.codingprojects.connect4.PlayerColor;

import java.util.*;

/**
 * RandomAgent will arbitrarily select an open column.
 *
 * Created by austi on 2016-12-18.
 */
public class RandomAgent extends Agent {


    /**
     * Internal random number generator.
     */
    private Random random;


    /**
     * Initializes this agent with a token color and with a seed for random number generation.
     *
     * @param color Color of the tokens used by this player.
     * @param seed Seed for random number generation.
     */
    public RandomAgent(PlayerColor color, long seed) {
       super(color);
       this.random = new Random(seed);
    }


    @Override
    public int perceive(IConnect4BoardInquiry board) {

        super.perceive(board);

        // Determine possible moves.
        List<Integer> possibleMoves = new ArrayList<>();

        for (int col = 0; col < board.numColumns(); ++col)
            if (board.getTokenColor(col,0) == PlayerColor.None)
                possibleMoves.add(col);

        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }
}