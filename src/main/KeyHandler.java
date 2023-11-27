package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class KeyHandler implements KeyListener{
    GamePanel gp;
    // WASD for controlling character up,left,down,right
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, interactPressed, shiftPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e){
        //Automatically generated, need to override, even if not used
    }
    @Override
    public void keyPressed(KeyEvent e){
        // KEY BEING PRESSED
        int code = e.getKeyCode(); // Returns the integer keyCode associated with the pressed key in this event.

        if(gp.gameState == GamePanel.GameState.TITLE_STATE){
            switch (gp.ui.command){
                case NEW_GAME:
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.QUIT;
                    }
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.LOAD_GAME;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                    }
                    break;
                case LOAD_GAME:
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.NEW_GAME;
                    }
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.QUIT;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        File f = new File("save.dat");
                        if(f.exists()){
                            gp.saveLoad.load();
                        }
                        else{
                            gp.retry();
                        }
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                    }
                    break;
                case QUIT:
                    if(code == KeyEvent.VK_W){
                        gp.ui.command = UI.Command.LOAD_GAME;
                    }
                    if(code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.NEW_GAME;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        System.exit(0);
                    }
                    break;
                default:
                    break;
            }
        }

        if(gp.gameState == GamePanel.GameState.GAME_OVER){
            switch (gp.ui.command){
                case RETRY:
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.MENU;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.PLAY_STATE;
                        gp.retry();
                        gp.ui.command = UI.Command.NEW_GAME;
                    }
                    break;
                case MENU:
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.RETRY;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.TITLE_STATE;
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.restart();
                    }
                    break;
                default:
                    break;
            }
        }
        if(gp.gameState == GamePanel.GameState.ESCAPED){
            switch (gp.ui.command){
                case MENU:
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.QUIT;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        gp.gameState = GamePanel.GameState.TITLE_STATE;
                        gp.ui.command = UI.Command.NEW_GAME;
                        gp.restart();
                    }
                    break;
                case QUIT:
                    if(code == KeyEvent.VK_W || code == KeyEvent.VK_S){
                        gp.ui.command = UI.Command.MENU;
                    }
                    if(code == KeyEvent.VK_ENTER) {
                        System.exit(0);
                    }
                    break;
                default:
                    break;
            }
        }

        if(gp.gameState == GamePanel.GameState.PLAY_STATE){
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
                upPressed = false;
                downPressed = false;
                leftPressed = false;
                rightPressed = false;
            }
            if(code == KeyEvent.VK_W){  upPressed = true; }
            if(code == KeyEvent.VK_S){ downPressed = true; }
            if(code == KeyEvent.VK_A){ leftPressed = true; }
            if(code == KeyEvent.VK_D){ rightPressed = true; }
            if(code == KeyEvent.VK_SHIFT){ shiftPressed = true; }
            if(code == KeyEvent.VK_E){ interactPressed = true; }
        }
        if(gp.gameState == GamePanel.GameState.PLAY_STATE && code == KeyEvent.VK_ESCAPE){
            gp.gameState = GamePanel.GameState.PAUSE_STATE;
        }
        else if(gp.gameState == GamePanel.GameState.PAUSE_STATE && (code == KeyEvent.VK_ESCAPE)){
                gp.gameState = GamePanel.GameState.PLAY_STATE;
        }
    }
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

