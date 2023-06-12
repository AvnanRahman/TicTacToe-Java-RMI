import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.*;

public class TicTacToeClientGui {
    private static TicTacToe ticTacToe;
    private static char symbol;
    private static JFrame frame;
    private static JButton[][] buttons;

    public static void main(String[] args) {
        try {
            // Specify the RMI registry URL with the custom port number
            String registryURL = "rmi://localhost:1099/TicTacToe";

            // Look up the remote object by its URL
            ticTacToe = (TicTacToe) Naming.lookup(registryURL);

            // Create and initialize the GUI
            SwingUtilities.invokeLater(() -> createAndShowGUI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        // Create and add buttons to the board panel
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
                button.addActionListener(new ButtonClickListener(row, col));
                boardPanel.add(button);
                buttons[row][col] = button;
            }
        }

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new RestartButtonClickListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // Prompt for the player's symbol
        symbol = promptForSymbol();
    }

    private static char promptForSymbol() {
        String symbolStr = JOptionPane.showInputDialog(frame, "Choose your symbol (X or O):");
        if (symbolStr != null && (symbolStr.equalsIgnoreCase("X") || symbolStr.equalsIgnoreCase("O"))) {
            return symbolStr.toUpperCase().charAt(0);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid symbol. Defaulting to X.");
            return 'X';
        }
    }

    private static class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
            button.setText(String.valueOf(symbol));

            try {
                ticTacToe.makeMove(row, col, symbol);
                char winner = ticTacToe.checkWin();
                if (winner != 'N') {
                    displayWinner(winner);
                    disableButtons();
                } else if (ticTacToe.isBoardFull()) {
                    displayDraw();
                    disableButtons();
                } else {
                    symbol = (symbol == 'X') ? 'O' : 'X';
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class RestartButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ticTacToe.restartGame();
                enableButtons();
                clearButtons();
                symbol = promptForSymbol();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void displayWinner(char winner) {
        String message = (winner == symbol) ? "You win!" : "Opponent wins!";
        JOptionPane.showMessageDialog(frame, message);
    }

    private static void displayDraw() {
        JOptionPane.showMessageDialog(frame, "It's a draw!");
    }

    private static void disableButtons() {
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                button.setEnabled(false);
            }
        }
    }

    private static void enableButtons() {
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                button.setEnabled(true);
            }
        }
    }

    private static void clearButtons() {
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                button.setText("");
            }
        }
    }
}
