package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

// This stores position and speed of entity(player and npc classes).
public class Entity {
    public int x,y;
    public int speed;
    public static final int DEFAULT_SPEED = 2;

    // Variables for sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0; // this number is increased every frame
    public int spriteNum = 1; // this is the current state of an animation

    // Collision/hitbox with direction
    public Rectangle solidArea; // hitbox Rectangle for entity
    public boolean collisionOnUp = false;
    public boolean collisionOnDown = false;
    public boolean collisionOnLeft = false;
    public boolean collisionOnRight = false;

    // to be able to reset hitbox after moving it
    public  int solidAreaDefaultX, solidAreaDefaultY;
}
