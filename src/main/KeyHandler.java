package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/** Handles key(input) events
 */
public class KeyHandler implements KeyListener{
    /** The main game panel
     */
    GamePanel gp;
    // WASD for controlling character up,left,down,right
    /** Stores if a key is pressed
     */
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, interactPressed, shiftPressed;

    /** Contructor that sets the main game panel
     * @param gp - the main game panel
     */
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    /** Not being used, but needs to be overriden
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e){
        //Automatically generated, need to override, even if not used
    }

    /** Checks which keys are being pressed
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e){
        // KEY BEING PRESSED
        int code = e.getKeyCode(); // Returns the integer keyCode associated with the pressed key in this event.

        // The MENU
        if(gp.gameState == GamePanel.GameState.TITLE_STATE){
            switch (gp.ui.command){
                case NEW_GAME:
                    // Stepping up in menu
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.QUIT;
                        gp.playSoundEffect(10);
                    }
                    // Stepping down in menu
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.LOAD_GAME;
                        gp.playSoundEffect(10);
                    }
                    // Selecting current from menu
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                        gp.playSoundEffect(11);
                    }
                    break;
                case LOAD_GAME:
                    // Stepping up in menu
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.playSoundEffect(10);
                    }
                    // Stepping down in menu
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.QUIT;
                        gp.playSoundEffect(10);
                    }
                    // Selecting current from menu
                    if(code == KeyEvent.VK_ENTER) {
                        File f = new File("save.dat");
                        if(f.exists()){
                            gp.saveLoad.load();
                        }
                        else{
                            gp.retry();
                        }
                        gp.playSoundEffect(11);
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                    }
                    break;
                case QUIT:
                    // Stepping up in menu
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.LOAD_GAME;
                        gp.playSoundEffect(10);
                    }
                    // Stepping down in menu
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.playSoundEffect(10);
                    }
                    // Selecting current from menu
                    if(code == KeyEvent.VK_ENTER) {
                        System.exit(0);
                        gp.playSoundEffect(11);
                    }
                    break;
                default:
                    break;
            }
        }
        // GAME OVER - player died
        if(gp.gameState == GamePanel.GameState.GAME_OVER){
            switch (gp.ui.command){
                case RETRY:
                    // Step to the other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.MENU;
                        gp.playSoundEffect(10);
                    }
                    // Select current one
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                        gp.retry();
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.playSoundEffect(11);
                    }
                    break;
                case MENU:
                    // Step to the other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.RETRY;
                        gp.playSoundEffect(10);
                    }
                    // Select current one
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.TITLE_STATE;
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.restart();
                        gp.playSoundEffect(11);
                    }
                    break;
                default:
                    break;
            }
        }
        // Player has escaped - won the game
        if(gp.gameState == GamePanel.GameState.ESCAPED){
            switch (gp.ui.command){
                case MENU:
                    // Step to the other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.QUIT;
                        gp.playSoundEffect(10);
                    }
                    // Select current one
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.TITLE_STATE;
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.restart();
                        gp.playSoundEffect(11);
                    }
                    break;
                case QUIT:
                    // Step to the other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.MENU;
                        gp.playSoundEffect(10);
                    }
                    // Select current one
                    if(code == KeyEvent.VK_ENTER) {
                        System.exit(0);
                        gp.playSoundEffect(11);
                    }
                    break;
                default:
                    break;
            }
        }
        // Player in bed, saving the game
        if(gp.gameState == GamePanel.GameState.SAVING){
            // if any movement step out of bed
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_S ||
               code == KeyEvent.VK_A || code == KeyEvent.VK_D ||
               code == KeyEvent.VK_SHIFT || code == KeyEvent.VK_E){
                gp.gameState = GamePanel.GameState.PLAY_STATE;
                gp.player.x += GamePanel.TILE_SIZE;
                gp.player.y += GamePanel.TILE_SIZE;
                gp.playSoundEffect(14);
            }
        }
        // The game is in playing mode
        if(gp.gameState == GamePanel.GameState.PLAY_STATE){
            // if space is being pressed(attack), block other movement
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
                upPressed = false;
                downPressed = false;
                leftPressed = false;
                rightPressed = false;
            }
            // Check which button is being pressed
            if(code == KeyEvent.VK_W){  upPressed = true; }
            if(code == KeyEvent.VK_S){ downPressed = true; }
            if(code == KeyEvent.VK_A){ leftPressed = true; }
            if(code == KeyEvent.VK_D){ rightPressed = true; }
            if(code == KeyEvent.VK_SHIFT){ shiftPressed = true; }
            if(code == KeyEvent.VK_E){ interactPressed = true; }
            // Esc = Pause screen and menu
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = GamePanel.GameState.PAUSE_STATE;
                gp.ui.command = UI.Command.MENU;
            }
        }
        // IF Pause state -> Esc = play state
        else if(gp.gameState == GamePanel.GameState.PAUSE_STATE){
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = GamePanel.GameState.PLAY_STATE;
            }
            switch (gp.ui.command){
                case MENU:
                    // Step to other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.QUIT;
                        gp.playSoundEffect(10);
                    }
                    // select current one
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.TITLE_STATE;
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.restart();
                        gp.playSoundEffect(11);
                    }
                    break;
                case QUIT:
                    // step to other
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.MENU;
                        gp.playSoundEffect(10);
                    }
                    // select current one
                    if(code == KeyEvent.VK_ENTER) {
                        System.exit(0);
                        gp.playSoundEffect(11);
                    }
                    break;
                default:
                    break;
            }

        }
    }

    /** Which key is being released
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e){
        // KEY BEING RELEASED
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){ upPressed = false; }
        if(code == KeyEvent.VK_S){ downPressed = false; }
        if(code == KeyEvent.VK_A){ leftPressed = false; }
        if(code == KeyEvent.VK_D){ rightPressed = false; }
        if(code == KeyEvent.VK_SHIFT){ shiftPressed = false; }
        if(code == KeyEvent.VK_SPACE){ spacePressed = false; }
        if(code == KeyEvent.VK_E){ interactPressed = false; }
    }
}

