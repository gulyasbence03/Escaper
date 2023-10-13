package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public int x,y; // Position at screen
    public String name; // names type of object
    public boolean collision; // means Object is solid, can not go through

    // Variables and Rectangle to detect collision with object
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int SolidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp){
        // Draw object to screen
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
