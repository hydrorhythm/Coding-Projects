package com.codingprojects.connect4;

/**
 * Connect4Board class, representing a hollow, NxN Connect 4 game board.
 */
public class Connect4Board implements IConnect4BoardInquiry {

    // TODO swap all [col][row] with this.getTokenColor(col, row)

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
     * Keeps track of the number of tokens dropped into the board.
     */
    private int numberOfTokens;


    /**
     * Initializes a new instance of a connect 4 board.
     */
    public Connect4Board() {
        this.board = new PlayerColor[BoardWidth][BoardHeight];
        this.drop();
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

        this.numberOfTokens += 1;
        return true;
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
        this.numberOfTokens = 0;
    }


    @Override
    public PlayerColor winner() {

        if (null == this.board || 0 == this.board.length || 0 == this.board[0].length)
            return PlayerColor.None;

        boolean blackHas4 = false;
        boolean whiteHas4 = false;

        for (int col = 0; col < this.board.length; ++col) {
            for (int row = 0; row < this.board[col].length; ++row) {
                PlayerColor currPT = this.board[col][row];

                boolean skipCondition =
                        currPT == PlayerColor.None ||
                                (currPT == PlayerColor.Black && blackHas4) ||
                                (currPT == PlayerColor.White && whiteHas4);

                if (!skipCondition) {
                    boolean currPTFound4 = false;

                    // Check 3 tiles to the NE
                    if ((col <= this.board.length - 4) && (row >= 3)) {
                        currPTFound4 |= (this.board[col + 1][row - 1] == currPT) &&
                                (this.board[col + 2][row - 2] == currPT) &&
                                (this.board[col + 3][row - 3] == currPT);
                    }

                    // Check 3 tiles to the E
                    if (col <= this.board.length - 4) {
                        currPTFound4 |= (this.board[col + 1][row] == currPT) &&
                                (this.board[col + 2][row] == currPT) &&
                                (this.board[col + 3][row] == currPT);
                    }

                    // Check 3 tiles to the SE
                    if ((col <= this.board.length - 4) && (row <= this.board[col].length - 4)) {
                        currPTFound4 |= (this.board[col + 1][row + 1] == currPT) &&
                                (this.board[col + 2][row + 2] == currPT) &&
                                (this.board[col + 3][row + 3] == currPT);
                    }

                    // Check 3 tiles to the S
                    if (row <= this.board[col].length - 4) {
                        currPTFound4 |= (this.board[col][row + 1] == currPT) &&
                                (this.board[col][row + 2] == currPT) &&
                                (this.board[col][row + 3] == currPT);

                    }

                    if (currPT == PlayerColor.White) {
                        whiteHas4 |= currPTFound4;
                    } else {
                        blackHas4 |= currPTFound4;
                    }
                }
            }
        }

        return (blackHas4 && whiteHas4)? PlayerColor.None :
                blackHas4? PlayerColor.Black :
                        whiteHas4? PlayerColor.White :
                                PlayerColor.None;
    }

    @Override
    public boolean isFull() { return (this.numberOfTokens == (BoardHeight * BoardWidth)); }

    @Override
    public PlayerColor getTokenColor(int col, int row) { return this.board[col][row]; }

    @Override
    public int numColumns() { return Connect4Board.BoardWidth; }

    @Override
    public int numRows() { return Connect4Board.BoardHeight; }


    /**
     * Returns an ASCII representation of the connect 4 board.
     *
     * @return String ASCII representation.
     */
    public String printableBoard() {

        StringBuilder output = new StringBuilder();
        for (int row = 0; row < BoardHeight; ++row) {

            output.append('|');
            for (int col = 0; col < BoardWidth; ++col) {
                PlayerColor currToken = this.board[col][row];
                char currTokenChar =
                        (currToken == PlayerColor.Black)? PlayerColor.Black.name().charAt(0) :
                                (currToken == PlayerColor.White)? PlayerColor.White.name().charAt(0) :
                                        ' ';
                output.append(currTokenChar);
            }
            output.append('|');
            output.append(System.lineSeparator());
        }

        return output.toString();
    }

}