package main;

import javax.swing.JFrame;
import java.io.FileNotFoundException;

/** The main class of the application, creates window and starts game
 */
public class Main {
    /** The main function of the whole program
     * @param args - arguments of application (not used)
     * @throws FileNotFoundException - if file is not being found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Setting up Window, with title and be able to close it
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Escaper");

        // The screen of the game
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // Causes this window to be sized the preferred size and layouts of its components(=GamePanel)

        window.setLocationRelativeTo(null); // Center window on host computer screen
        window.setVisible(true);

        gamePanel.setupGame(); // Get objects to "spawn", draw them on screen
        gamePanel.startGameThread(); // To be able to stop and start processes (FPS control)
    }
}