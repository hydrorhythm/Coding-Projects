package com.codingprojects.connect4.agents;

import com.codingprojects.connect4.IConnect4BoardInquiry;
import com.codingprojects.connect4.PlayerColor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

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
public class PerfectAgent extends PrimitivePreventionAgent {

    private static class Location {
        int column;
        int row;

        Location(int col, int row) {
            this.column = col;
            this.row = row;
        }
    }


    /**
     * Initializes this agent with a token color.
     *
     * @param color Color of the tokens used by this player.
     */
    public PerfectAgent(PlayerColor color) {
        super(color);
    }


    /**
     * Determines locations where a player could place a token and cause themselves to win.
     *
     * @param board Interface for extracting information from a Connect 4 board.
     * @return A List of locations where the player could place a token and win.
     */
    private static List<Location> threats(PlayerColor player, IConnect4BoardInquiry board) {
        List<Location> out = new ArrayList<>();

        for (int col = 0; col < board.numColumns(); ++col)
            for (int row = 0; row < board.numRows(); ++row)
                if (board.getTokenColor(col, row) == PlayerColor.None)
                    if (Agent.playerWinsGivenToken(board, player, col, row))
                        out.add(new Location(col, row));

        return out;
    }



    @Override
    public int perceive(IConnect4BoardInquiry board) {
        return -1;
    }
    }
