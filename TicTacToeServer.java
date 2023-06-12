import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TicTacToeServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            TicTacToe ticTacToe = new TicTacToeImpl();

            // Start the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);

            // Bind the remote object to the name "TicTacToe"
            Naming.rebind("TicTacToe", ticTacToe);

            System.out.println("TicTacToe server is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
