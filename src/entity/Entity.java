package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

// This stores position and speed of entity(player and npc classes).
public class Entity {

    GamePanel gp;
    public int x,y;
    public int speed;
    public static final int DEFAULT_SPEED = 2;

    // Variables for sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0; // this number is increased every frame
    public int spriteNum = 1; // this is the current state of an animation

    // Collision/hitbox with direction
    public Rectangle solidArea = new Rectangle(0,0,48,48); // hitbox Rectangle for entity
    public boolean collisionOnUp = false;
    public boolean collisionOnDown = false;
    public boolean collisionOnLeft = false;
    public boolean collisionOnRight = false;

    // to be able to reset hitbox after moving it
    public  int solidAreaDefaultX, solidAreaDefaultY;

    public int actionLockCounter = 0;

    public BufferedImage image;
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }
    public void update(){
        setAction();
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
        gp.collisionChecker.checkPlayer(this);

        if(direction.equals("up") && !collisionOnUp){ // If collision upwards false, player can move up
            y -= speed;
        }
        if(direction.equals("down") && !collisionOnDown){ // If collision downwards false, player can move down
            y += speed;
        }
        if(direction.equals("left") && !collisionOnLeft){ // If collision to the left is false, player can move left
            x -= speed;
        }
        if(direction.equals("right") && !collisionOnRight){ // If collision to the right is false, player can move right
            x += speed;
        }
        if(direction.equals("idle")){
            // DO NOTHING
        }

        spriteCounter++;
        if(spriteCounter>20 && !direction.equals("idle")){
            if(spriteNum == 1){
                spriteNum = 2;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            }
            else if(spriteNum == 2){
                spriteNum = 1;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2){

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                else{
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                else{
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                else{
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                else{
                    image = right2;
                }
                break;
            default:
                break;
        }
        g2.drawImage(image,x,y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,null);
    }
}
