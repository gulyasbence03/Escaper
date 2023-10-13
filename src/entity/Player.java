package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues(); // set up coordinates, speed, direction
        getPlayerImage(); // load images to animate and draw character

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY= solidArea.y;
        solidArea.width =32;
        solidArea.height = 24;
    }

    public void setDefaultValues(){
        x = gp.screenWidth/2 - gp.tileSize/2;
        y = gp.screenHeight/2 - gp.tileSize/2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        // Read images into variables from resource directory
        try{
            // Object.requireNonNull() to ensure that it is not null
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/up2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/down2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/right2.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        // Check tile collision, and reset at beginning to check every frame
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            // Top right corner of the screen is (0,0)
            // X value increases to the right
            // Y value increases as they go down
            if(keyH.upPressed){
                direction = "up";
                // Check tile collision
                gp.collisionChecker.CheckTile(this);
                // Check object collision
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);
                if(!collisionOnUp){ // If collision false, player can move
                    y -= speed;
                }
            }
            if(keyH.downPressed){
                direction = "down";
                // Check tile collision
                gp.collisionChecker.CheckTile(this);
                // Check object collision
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);
                if(!collisionOnDown){ // If collision false, player can move
                    y += speed;
                }
            }
            if(keyH.leftPressed){
                direction = "left";
                // Check tile collision
                gp.collisionChecker.CheckTile(this);
                // Check object collision
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);
                if(!collisionOnLeft){ // If collision false, player can move
                    x -= speed;
                }
            }
            if(keyH.rightPressed){
                direction = "right";
                // Check tile collision
                gp.collisionChecker.CheckTile(this);
                // Check object collision
                int objIndex = gp.collisionChecker.checkObject(this, true);
                pickUpObject(objIndex);
                if(!collisionOnRight){ // If collision false, player can move
                    x += speed;
                }
            }

            // The update() is called 60(FPS) times a second
            spriteCounter++; // every frame this is increased by 1
            if(spriteCounter > 10){ // if it hits 10, it changes sprite
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            // Player image is changed every (10) frames.
        }


    }

    public void pickUpObject(int i){
        if(i!=999 && gp.obj[i] != null){ // if remains 999, did not touch object
            switch (gp.obj[i].name){
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if(hasKey >0){
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    break;
            }


        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        // Draw the right image for animation, depending on which state is the animation is in.
        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        // Draw the image at (x,y), width = tileSize, height = tileSize, and no Image observer = so null
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize, null);
    }
}
