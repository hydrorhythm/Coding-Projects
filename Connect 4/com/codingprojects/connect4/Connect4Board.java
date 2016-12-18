package com.codingprojects.connect4;

/**
 * Connect4Board class, representing a hollow, NxN Connect 4 game board.
 */
public class Connect4Board {


    /**
     * Number of columns in the board.
     */
    public static final int BoardWidth = 7;

    /**
     * Number of rows in the board.
     */
    public static final int BoardHeight = 6;


    /**
     * Vertical connect 4 game board.
     */
    private PlayerColor[][] board;


    /**
     * Initializes a new instance of a connect 4 board.
     */
    public Connect4Board() {
        this.board = new PlayerColor[BoardWidth][BoardHeight];
        this.drop();
    }


    /**
     * Clears the board, allowing the game to start over.
     */
    public void drop() {
        for (int col = 0; col < BoardWidth; ++col) {
            for (int row = 0; row < BoardHeight; ++row) {
                this.board[col][row] = PlayerColor.None;
            }
        }
    }

    /**
     * Gets a copy of the board.
     */
    public PlayerColor[][] getCopyOfBoard() {
        PlayerColor[][] output = new PlayerColor[BoardWidth][BoardHeight];
        for (int col = 0; col < BoardWidth; ++col) {
            for (int row = 0; row < BoardHeight; ++row) {
                output[col][row] = board[col][row];
            }
        }
        return output;
    }



    /**
     * Allows a player to drop a token into a column.
     *
     * @param column Column number in which to drop a token. Should be 0 to (com.codingprojects.connect4.Connect4Board.BoardSize - 1)
     * @param player Color of token to drop into column.
     * @return True if the move was valid and the board was modified. False otherwise.
     */
    public boolean insertToken(int column, PlayerColor player) {
       if (column >= BoardWidth || column < 0) {
           return false;
       }

       if (this.board[column][0] != PlayerColor.None) {
           return false;
       }

        // Token falls until it rests on top of another token or
        // it rests on the bottom row.
       for (int row = 1; row < BoardHeight; ++row) {

           if (this.board[column][row] != PlayerColor.None) {
               this.board[column][row - 1] = player;
               break;
           }
           else if (row == BoardHeight - 1) {
               this.board[column][row] = player;
               break;
           }
       }

       return true;
    }


    /**
     * Returns the color of the player that has won the game. If both players have a connect 4 or neither do, then
     * this will return PlayerColor.None.
     *
     * @return the color of the winner.
     */
    public PlayerColor winner() {
        boolean redHas4 = false;
        boolean ylwHas4 = false;

        for (int col = 0; col < BoardWidth; ++col) {
            for (int row = 0; row < BoardHeight; ++row) {
                PlayerColor currPT = this.board[col][row];

                boolean skipCondition =
                        currPT == PlayerColor.None ||
                        (currPT == PlayerColor.Red && redHas4) ||
                        (currPT == PlayerColor.Yellow && ylwHas4);

                if (!skipCondition) {
                    boolean currPTFound4 = false;

                    // Check 3 tiles to the NE
                    if ((col <= BoardWidth - 4) && (row >= 3)) {
                        currPTFound4 |= (this.board[col + 1][row - 1] == currPT) &&
                                (this.board[col + 2][row - 2] == currPT) &&
                                (this.board[col + 3][row - 3] == currPT);
                    }

                    // Check 3 tiles to the E
                    if (col <= BoardWidth - 4) {
                        currPTFound4 |= (this.board[col + 1][row] == currPT) &&
                                (this.board[col + 2][row] == currPT) &&
                                (this.board[col + 3][row] == currPT);
                    }

                    // Check 3 tiles to the SE
                    if ((col <= BoardWidth - 4) && (row <= BoardHeight - 4)) {
                        currPTFound4 |= (this.board[col + 1][row + 1] == currPT) &&
                                (this.board[col + 2][row + 2] == currPT) &&
                                (this.board[col + 3][row + 3] == currPT);
                    }

                    // Check 3 tiles to the S
                    if (row <= BoardWidth - 4) {
                        currPTFound4 |= (this.board[col][row + 1] == currPT) &&
                                (this.board[col][row + 2] == currPT) &&
                                (this.board[col][row + 3] == currPT);

                    }

                    if (currPT == PlayerColor.Yellow) {
                        ylwHas4 |= currPTFound4;
                    } else {
                        redHas4 |= currPTFound4;
                    }
                }
            }
        }

        return (redHas4 && ylwHas4)? PlayerColor.None :
               redHas4? PlayerColor.Red :
               ylwHas4? PlayerColor.Yellow :
               PlayerColor.None;
    }

    /**
     * Returns an ASCII representation of the connect 4 board.
     *
     * @return String with R
     */
    public String printableBoard() {

        StringBuilder output = new StringBuilder();
        for (int row = 0; row < BoardHeight; ++row) {

            output.append('|');
            for (int col = 0; col < BoardWidth; ++col) {
                PlayerColor currToken = this.board[col][row];
                char currTokenChar =
                        (currToken == PlayerColor.Red)? PlayerColor.Red.name().charAt(0) :
                        (currToken == PlayerColor.Yellow)? PlayerColor.Yellow.name().charAt(0) :
                        ' ';
                output.append(currTokenChar);
            }
            output.append('|');
            output.append(System.lineSeparator());
        }

        return output.toString();
    }
}