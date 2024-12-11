package com.example.connectfour;

public class ConnectFourGame {

    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    private final int[][] boardGrid = new int[ROW][COL];
    private int player = BLUE;

    public void newGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY; // Reset all positions to EMPTY
            }
        }
        player = BLUE; // Reset to the first player
    }

    int getDisc(int row, int col){
        return boardGrid[row][col];
    }

    public boolean isGameOver(){
        if(isBoardFull()){
            return true;
        }
       return isWin();
    }

    boolean isWin(){
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    // Checks for a horizontal win condition
    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL - 3; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row][col + 1] &&
                        boardGrid[row][col] == boardGrid[row][col + 2] &&
                        boardGrid[row][col] == boardGrid[row][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for a vertical win condition
    private boolean checkVertical() {
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col] &&
                        boardGrid[row][col] == boardGrid[row + 2][col] &&
                        boardGrid[row][col] == boardGrid[row + 3][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for a diagonal win condition
    private boolean checkDiagonal() {
        // Top-left to bottom-right
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL - 3; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col + 1] &&
                        boardGrid[row][col] == boardGrid[row + 2][col + 2] &&
                        boardGrid[row][col] == boardGrid[row + 3][col + 3]) {
                    return true;
                }
            }
        }
        // Top-right to bottom-left
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 3; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col - 1] &&
                        boardGrid[row][col] == boardGrid[row + 2][col - 2] &&
                        boardGrid[row][col] == boardGrid[row + 3][col - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    public  void setState(String gameState){
        String[] rows = gameState.split("\n");
        for (int row = 0; row < ROW; row++) {
            String[] cols = rows[row].split(",");
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = Integer.parseInt(cols[col]);
            }
        }
    }

    // Handles a disc placement
    public void selectDisc(int row, int col) {
        for (int r = ROW - 1; r >= 0; r--) {
            if (boardGrid[r][col] == EMPTY) {
                boardGrid[r][col] = player;
                player = (player == BLUE) ? RED : BLUE; // Switch player
                break;
            }
        }
    }

    public String getState() {
        StringBuilder state = new StringBuilder();

        for (int[] ints : boardGrid) {
            for (int anInt : ints) {
                state.append(anInt).append(" ");
            }
            state.append("\n");
        }
        return state.toString();
    }

    // Checks if the board is full
    private boolean isBoardFull() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

}
