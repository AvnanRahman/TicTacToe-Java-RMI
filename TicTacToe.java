import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToe extends Remote {
    void makeMove(int row, int col, char symbol) throws RemoteException;
    char checkWin() throws RemoteException;
    boolean isBoardFull() throws RemoteException;
    void restartGame() throws RemoteException;
}
