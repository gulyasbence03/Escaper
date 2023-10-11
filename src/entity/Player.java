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

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues(); // set up coordinates, speed, direction
        getPlayerImage(); // load images to animate and draw character
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
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
        // If any key is pressed only then change the sprite of character
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            // Top right corner of the screen is (0,0)
            // X value increases to the right
            // Y value increases as they go down
            if(keyH.upPressed){
                direction = "up";
                y -= speed;
            }
            if(keyH.downPressed){
                direction = "down";
                y += speed;
            }
            if(keyH.leftPressed){
                direction = "left";
                x -= speed;
            }
            if(keyH.rightPressed){
                direction = "right";
                x += speed;
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
