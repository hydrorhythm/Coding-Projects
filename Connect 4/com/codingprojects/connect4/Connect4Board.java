package com.codingprojects.connect4;

/**
 * Connect4Board class, representing a hollow, NxN Connect 4 game board.
 */
public class Connect4Board {

    /**
     * Game board is a square with this dimension on a side.
     */
    public static final int BoardSize = 7;

    /**
     * Vertical connect 4 game board.
     */
    private PlayerColor[][] board;

    public Connect4Board() {

        this.board = new PlayerColor[BoardSize][BoardSize];

        for (int i = 0; i < BoardSize; ++i) {
            for (int j = 0; j < BoardSize; ++j) {
                this.board[i][j] = PlayerColor.None;
            }
        }
    }

    /**
     * Allows a player to drop a token into a column.
     *
     * @param column Column number in which to drop a token. Should be 0 to (com.codingprojects.connect4.Connect4Board.BoardSize - 1)
     * @param player Color of token to drop into column.
     */
    public void insertToken(int column, PlayerColor player) {
       if (column >= BoardSize || column < 0) {
           throw new IndexOutOfBoundsException();
       }

       if (this.board[column][0] != PlayerColor.None) {
           throw new IllegalArgumentException();
       }

       for (int row = 1; row < BoardSize; ++row) {
           if (this.board[column][row] != PlayerColor.None) {
               this.board[column][row - 1] = player;
           } else if (row == BoardSize - 1) {
               this.board[column][row] = player;
           }
       }
    }

    /**
     * Returns the color of the player that has won the game. If both players have a connect 4 or neither do, then
     * this will return PlayerColor.None
     *
     * @return the color of the winner.
     */
    public PlayerColor winner() {
        boolean redHas4 = false;
        boolean blkHas4 = false;

        for (int col = 0; col < BoardSize; ++col) {
            for (int row = 0; row < BoardSize; ++row) {
                PlayerColor currPT = this.board[col][row];

                boolean skipCondition =
                        currPT == PlayerColor.None ||
                        (currPT == PlayerColor.Red && redHas4) ||
                        (currPT == PlayerColor.Black && blkHas4);

                if (!skipCondition) {
                    boolean currPTFound4 = false;

                    // Check 3 tiles to the NE
                    if ((col <= BoardSize - 4) && (row >= 3)) {
                        currPTFound4 |= (this.board[col + 1][row - 1] == currPT) &&
                                (this.board[col + 2][row - 2] == currPT) &&
                                (this.board[col + 3][row - 3] == currPT);
                    }

                    // Check 3 tiles to the E
                    if (col <= BoardSize - 4) {
                        currPTFound4 |= (this.board[col + 1][row] == currPT) &&
                                (this.board[col + 2][row] == currPT) &&
                                (this.board[col + 3][row] == currPT);
                    }

                    // Check 3 tiles to the SE
                    if ((col <= BoardSize - 4) && (row <= BoardSize - 4)) {
                        currPTFound4 |= (this.board[col + 1][row + 1] == currPT) &&
                                (this.board[col + 2][row + 2] == currPT) &&
                                (this.board[col + 3][row + 3] == currPT);
                    }

                    // Check 3 tiles to the S
                    if (row <= BoardSize - 4) {
                        currPTFound4 |= (this.board[col][row + 1] == currPT) &&
                                (this.board[col][row + 2] == currPT) &&
                                (this.board[col][row + 3] == currPT);

                    }

                    if (currPT == PlayerColor.Black) {
                        blkHas4 |= currPTFound4;
                    } else {
                        redHas4 |= currPTFound4;
                    }
                }
            }
        }


        PlayerColor winner =
                (redHas4 && blkHas4)?   PlayerColor.None :
                (redHas4)?              PlayerColor.Red :
                (blkHas4)?              PlayerColor.Black :
                                        PlayerColor.None;

        return winner;
    }

    /**
     * Returns a simple ASCII representation of the connect 4 board.
     *
     * @return String with R
     */
    public String printableBoard() {

        StringBuilder output = new StringBuilder();
        for (int row = 0; row < BoardSize; ++row) {

            output.append('|');
            for (int col = 0; col < BoardSize; ++col) {
                PlayerColor currPT = this.board[col][row];
                char currPTchar =
                        (currPT == PlayerColor.Red)? 'R' :
                        (currPT == PlayerColor.Black)? 'B' :
                        ' ';
                output.append(currPTchar);
            }
            output.append('|');

            output.append(System.lineSeparator());
        }

        return output.toString();
    }
}