package entity;

import main.GamePanel;
import main.UI;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// This stores position and speed of entity(player and npc classes).
public class Entity {
    GamePanel gp;
    public int x,y;
    public int speed;
    public static final int DEFAULT_SPEED = 2;

    // CHARACTER STATUS
    public int maxLife;
    public int life;
    public String direction = "down";

    // Variables for sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public int spriteCounter = 0; // this number is increased every frame
    public int attackSpriteCounter = 0;
    public int spriteNum = 1; // this is the current state of an animation
    public int objectSpriteNum = 0;

    boolean attacking = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;

    public enum Status{
        ALIVE,
        DYING,
        DEAD
    }
    public Status status;
    int dyingCounter = 0;

    boolean hpBarON = false;
    int hpBarCounter = 0;


    // Object vars
    public BufferedImage imageHeart;
    public BufferedImage imageBlankHeart;
    public String name; // names type of object
    public boolean isSolidObject; // means Object is solid, can not go through

    // Collision/hitbox with direction
    public Rectangle solidArea = new Rectangle(0,0,48,48); // hitbox Rectangle for entity
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean collisionOnUp = false;
    public boolean collisionOnDown = false;
    public boolean collisionOnLeft = false;
    public boolean collisionOnRight = false;

    // to be able to reset hitbox after moving it
    public  int solidAreaDefaultX, solidAreaDefaultY;

    // npc action
    public int actionLockCounter = 0;

    public BufferedImage image;
    public Entity(GamePanel gp){
        this.gp = gp;
        status = Status.ALIVE;
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

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2){
        int tempScreenX = x;
        int tempScreenY = y;

        switch (direction){
            case "up":
                if(attacking && attackSpriteCounter !=0){
                    tempScreenY -= GamePanel.TILE_SIZE;
                    if(spriteNum == 1){ image = attackUp1; }
                    if(spriteNum == 2){ image = attackUp2; }
                }
                else{
                    if(spriteNum == 1){ image = up1; }
                    if(spriteNum == 2){ image = up2; }
                }
                break;
            case "down":
                if(attacking && attackSpriteCounter !=0){
                    if(spriteNum == 1){ image = attackDown1; }
                    if(spriteNum == 2){ image = attackDown2; }
                }
                else{
                    if(spriteNum == 1){ image = down1; }
                    if(spriteNum == 2){ image = down2; }
                }
                break;
            case "left":
                if(attacking && attackSpriteCounter !=0){
                    tempScreenX -= GamePanel.TILE_SIZE;
                    if(spriteNum == 1){ image = attackLeft1; }
                    if(spriteNum == 2){ image = attackLeft2; }
                }
                else{
                    if(spriteNum == 1){ image = left1; }
                    if(spriteNum == 2){ image = left2; }
                }
                break;
            case "right":
                if(attacking && attackSpriteCounter !=0){
                    if(spriteNum == 1){ image = attackRight1; }
                    if(spriteNum == 2){ image = attackRight2; }
                }
                else{
                    if(spriteNum == 1){ image = right1; }
                    if(spriteNum == 2){ image = right2; }
                }
                break;
            default:
                break;
        }
        if(name.equals("npc") && hpBarON){

            double oneScale = ((double)GamePanel.TILE_SIZE-9)/maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(new Color(35,35,35));
            g2.fillRect(x+3,y-12,GamePanel.TILE_SIZE-5,8);
            g2.setColor(new Color(255,0,30));
            g2.fillRect(x+5,y-10, (int) hpBarValue,5);

            hpBarCounter++;
            if(hpBarCounter>500){
                hpBarCounter = 0;
                hpBarON = false;
            }
        }


        if(invincible){
            hpBarON = true;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        if(status == Status.DYING){
            dyingAnimation(g2);
        }
        g2.drawImage(image,tempScreenX,tempScreenY, image.getWidth(), image.getHeight(),null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        if(dyingCounter<=10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(dyingCounter<=20 && dyingCounter>10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter<=30 && dyingCounter>20){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(dyingCounter<=40 && dyingCounter>30){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter>40){
            gp.playSoundEffect(6);
            status = Status.DEAD;
        }
    }

    public BufferedImage setup(String imageName, int width, int height){
        // Read images into variables from resource directory
        // Object.requireNonNull() to ensure that it is not null
        // 2 sprites(image) for every direction to be able to animate movement
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imageName + ".png")));
            image = uTool.scaleImage(image,width,height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}
