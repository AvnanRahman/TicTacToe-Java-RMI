import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TicTacToeImpl extends UnicastRemoteObject implements TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToeImpl() throws RemoteException {
        board = new char[3][3];
        currentPlayer = 'X';
    }

    public void makeMove(int row, int col, char symbol) throws RemoteException {
        // Update the board with the player's move
        board[row][col] = symbol;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char checkWin() throws RemoteException {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != '\u0000' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                return board[row][0];
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] != '\u0000' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                return board[0][col];
            }
        }

        // Check diagonals
        if (board[0][0] != '\u0000' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }

        if (board[2][0] != '\u0000' && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            return board[2][0];
        }

        // Check for a draw
        boolean draw = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\u0000') {
                    draw = false;
                    break;
                }
            }
        }
        if (draw) {
            return 'D';
        }

        // No winner yet
        return 'N';
    }

    public boolean isBoardFull() throws RemoteException {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

    public void restartGame() throws RemoteException {
        // Reset the game board and current player
        board = new char[3][3];
        currentPlayer = 'X';
    }
}
