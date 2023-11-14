package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage[] images;
    public int x;
    public int y; // Position of object at screen
    public String name; // names type of object
    public boolean isSolidObject; // means Object is solid, can not go through

    // solidArea Rectangle to detect collision with object
    // solidAreaDefault coordinates to reset after collision check
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int spriteNum = 0;
    public boolean animationON = false;
    public int animationLength = 0;

    UtilityTool uTool = new UtilityTool();

    public SuperObject(){
        this.images = new BufferedImage[20];
    }

    public void draw(Graphics2D g2, int spriteNum){
        // Draw object to screen
        if(animationON && spriteNum < animationLength){
            g2.drawImage(this.images[spriteNum],x,y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,null);
        }
        else if(spriteNum<=animationLength){
            g2.drawImage(this.images[0],x,y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,null);

        }
    }
}
