package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.IConnect4BoardInquiry;
import com.codingprojects.connect4.PlayerColor;

import java.util.HashMap;

/**
 * The PerfectAgent is to be designed with utilities that allow it to detect threats and determine which player controls
 * the "Zugzwang". The hope is to develop a connect 4 agent that can play a perfect game of connect 4 without performing
 * deep, expensive graph searches. This class will implement concepts such as:
 *
 * Zugzwang    - A situation where a player must make a move, when it would be advantageous for them to make no move.
 * Claimeven   - The controller of the zugzwang can get all empty even squares by letting his opponent play all empty
 *               odd squares.
 * Baseinverse - Based on the fact that a player cannot play two directly playable moves in one turn. The controller of
 *               the zugzwang is guarenteed to have the ability to grab one of those 2 squares. This is helpful if both
 *               of the squares are part of the same group, i.e. a run of 4 consecutive positions).
 *
 * These strategies were applied from Victor Allis's Master's Thesis, Vrije University, Amsterdam, The Netherlands.
 * Not including the Zugzwang concept, there are 9 strategic rules outlined in this paper. Each should be implemented
 * and applied.
 *
 * Created by austi on 2016-12-24.
 */
public class PerfectAgent extends Agent {


    /**
     * This class sees the world differently.
     *
     * ...
     *   _ _ _ _ _ _ _
     * 6|_|_|_|_|_|_|_|
     * 5|_|_|_|_|_|_|_|
     * 4|_|_|_|_|_|_|_|
     * 3|_|_|_|_|_|_|_|
     * 2|_|_|_|_|_|_|_|
     * 1|_|_|_|_|_|_|_|
     *   a b c d e f g  ...
     */


    // private static final HashMap<Character, Integer> columnXref;
    // static {
    //    columnXref = new HashMap<>();
    //    columnXref.put('a', 0);
    //    columnXref.put('b', 1);
    //    columnXref.put('c', 2);
    //    columnXref.put('d', 3);
    //    columnXref.put('e', 4);
    //    columnXref.put('f', 5);
    //    columnXref.put('g', 6);
    //}


    /**
     * Initializes this agent with a token color.
     *
     * @param color Color of the tokens used by this player.
     */
    public PerfectAgent(PlayerColor color) {
        super(color);
    }


    @Override
    public int perceive(IConnect4BoardInquiry board) {
        return (int) 'd' - 'a';
    }
}
