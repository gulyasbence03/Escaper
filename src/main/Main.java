package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Setting up Window, with title and be able to close it
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Escaper");

        // The screen of the game
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // Causes this window to be sized the preferred size and layouts of its components(=GamePanel)

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread(); // To be able to stop and start processes (FPS control)
    }
}
