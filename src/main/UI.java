package main;

import object.ObjKey;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arialFont;
    BufferedImage keyImage;
    // Command
    enum Command{
        NEW_GAME,
        LOAD_GAME,
        QUIT
    }
    Command command = Command.NEW_GAME;

    public UI(GamePanel gp){
        this.gp = gp;
        this.arialFont = new Font("Arial", Font.BOLD, 18);
        ObjKey key = new ObjKey();
        keyImage = key.images[0];
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        if(gp.gameState == GamePanel.GameState.TITLE_STATE){
            drawTitleScreen();
        }

        if(gp.gameState == GamePanel.GameState.PLAY_STATE){
            g2.setFont(arialFont);
            g2.setColor(Color.orange);
            g2.drawImage(keyImage, -10,-18,GamePanel.TILE_SIZE*2,GamePanel.TILE_SIZE*2,null);
            g2.drawString("x " + (gp.player.hasKey), 68, 34);
        }
        if(gp.gameState == GamePanel.GameState.PAUSE_STATE){
            drawPauseScreen();
        }
    }

    private void drawTitleScreen() {
        // TITLE NAME
        g2.setColor(Color.GRAY);
        g2.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
        String text = "Escaper";
        int x = getXforCenteredText(text);
        int y = GamePanel.TILE_SIZE*3;
        // SHADOW
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        // PLAYER IMAGE
        x = GamePanel.SCREEN_WIDTH/2 - GamePanel.TILE_SIZE;
        y += GamePanel.TILE_SIZE;
        g2.drawImage(gp.player.down1, x,y, GamePanel.TILE_SIZE*2,GamePanel.TILE_SIZE*2,null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE*3;
        g2.drawString(text,x,y);
        if(command == Command.NEW_GAME){
            g2.drawString(">",x-GamePanel.TILE_SIZE,y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.LOAD_GAME){
            g2.drawString(">",x-GamePanel.TILE_SIZE,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.QUIT){
            g2.drawString(">",x-GamePanel.TILE_SIZE,y);
        }

    }

    private void drawPauseScreen() {
        g2.setColor(new Color(0f,0f,0f,0.5f));
        g2.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "Paused";
        g2.setColor(Color.white);
        int y = GamePanel.SCREEN_HEIGHT/2;
        int x = getXforCenteredText(text);
        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = GamePanel.SCREEN_WIDTH/2 - length/2;
        return x;
    }
}
