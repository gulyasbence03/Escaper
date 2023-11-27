package main;

import entity.Entity;
import object.ObjHeart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arialFont;
    BufferedImage keyImage;
    BufferedImage heartImage;
    BufferedImage blankHeartImage;
    // Command
    public enum Command{
        NO_COMMAND,
        NEW_GAME,
        LOAD_GAME,
        QUIT,
        RETRY,
        MENU
    }
    public Command command = Command.NO_COMMAND;

    public UI(GamePanel gp){
        this.gp = gp;
        this.arialFont = new Font("Arial", Font.BOLD, 18);

        Entity heart = new ObjHeart(gp);
        heartImage = heart.imageHeart;
        blankHeartImage = heart.imageBlankHeart;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        if(gp.gameState == GamePanel.GameState.TITLE_STATE){
            drawTitleScreen();
        }

        if(gp.gameState == GamePanel.GameState.PLAY_STATE){
            drawPlayerLife();
            drawInventory();
        }
        if(gp.gameState == GamePanel.GameState.PAUSE_STATE){
            drawPauseScreen();
        }
        if(gp.gameState == GamePanel.GameState.GAME_OVER){
            drawGameOverScreen();
        }
        if(gp.gameState == GamePanel.GameState.ESCAPED){
            drawEscapedScreen();
        }
    }

    private void drawEscapedScreen() {
        // CUTSCENE


        g2.setColor(new Color(0f,0f,0f,0.5f));
        g2.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "You Escaped!";
        g2.setColor(Color.white);
        int y = GamePanel.SCREEN_HEIGHT/2;
        int x = getXforCenteredText(text);
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));

        text = "MENU";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.MENU){
            g2.drawString(">",x-GamePanel.TILE_SIZE/2,y);
        }
        text = "QUIT";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.QUIT){
            g2.drawString(">",x-GamePanel.TILE_SIZE/2,y);
        }
    }

    private void drawGameOverScreen() {
        g2.setColor(new Color(0f,0f,0f,0.5f));
        g2.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "Game Over";
        g2.setColor(Color.white);
        int y = GamePanel.SCREEN_HEIGHT/2;
        int x = getXforCenteredText(text);
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));

        text = "RETRY";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.RETRY){
            g2.drawString(">",x-GamePanel.TILE_SIZE/2,y);
        }

        text = "MENU";
        x = getXforCenteredText(text);
        y += GamePanel.TILE_SIZE;
        g2.drawString(text,x,y);
        if(command == Command.MENU){
            g2.drawString(">",x-GamePanel.TILE_SIZE/2,y);
        }
    }

    private void drawInventory() {
        int x = GamePanel.TILE_SIZE/2-10;
        int y = GamePanel.TILE_SIZE/4;

        g2.setColor(new Color(2,2,2,200));
        g2.fillRoundRect(x,y,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE,10,10);

        g2.setColor(new Color(190,190,190));
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x-2,y-2,GamePanel.TILE_SIZE+2,GamePanel.TILE_SIZE+2,10,10);
        if(gp.player.currentItem != null){
            g2.drawImage(gp.player.currentItem.down1,x+2,y,GamePanel.TILE_SIZE-8,GamePanel.TILE_SIZE-8,null);
        }
    }


    private void drawPlayerLife() {
        int x = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE*3 - GamePanel.TILE_SIZE/3;
        int y = GamePanel.TILE_SIZE/4;

        g2.setColor(new Color(2,2,2,200));
        g2.fillRoundRect(x,y,GamePanel.TILE_SIZE*gp.player.maxLife+4,GamePanel.TILE_SIZE,10,10);

        g2.setColor(new Color(190,190,190));
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x-2,y-2,GamePanel.TILE_SIZE*gp.player.maxLife+8,GamePanel.TILE_SIZE+4,10,10);


        int i = 0;
        while(i<gp.player.maxLife){
            g2.drawImage(gp.ui.blankHeartImage,x,y,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE,null);
            i++;
            x += GamePanel.TILE_SIZE;
        }

        x = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE*3 - GamePanel.TILE_SIZE/3;
        y = GamePanel.TILE_SIZE/4;
        i = 0;
        while(i<gp.player.life){
            g2.drawImage(gp.ui.heartImage,x,y,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE,null);
            i++;
            x += GamePanel.TILE_SIZE;
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
