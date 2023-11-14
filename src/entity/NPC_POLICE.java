package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_POLICE extends Entity{
    public  NPC_POLICE(GamePanel gp){
        super(gp);
        direction = "down";
        speed = DEFAULT_SPEED;
        getNpcImage();

        // Solid area(hitbox) is the area that is collidable with solid tiles(like walls) and objects
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 24;
        solidArea.width = 32;
        solidArea.height = 24;

        // These are needed, because we change the npc's solid area
        // to check if it's capable of moving that way( if there is solid object/tile the next step)
        // Then reset these coordinates back on npc
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY= solidArea.y;
    }
    public void getNpcImage(){
        up1 = setup("police_up1");
        up2 = setup("police_up2");
        down1 = setup("police_down1");
        down2 = setup("police_down2");
        left1 = setup("police_left1");
        left2 = setup("police_left2");
        right1 = setup("police_right1");
        right2 = setup("police_right2");
    }

    public BufferedImage setup(String imageName){
        // Read images into variables from resource directory
        // Object.requireNonNull() to ensure that it is not null
        // 2 sprites(image) for every direction to be able to animate movement
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("npc/" + imageName + ".png")));
            image = uTool.scaleImage(image,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }


    public void setAction(){
        actionLockCounter++;
        Random random = new Random();
        int i = random.nextInt(7)+1; // 1<-->4
        if(actionLockCounter == 60){
            switch (i){
                case 1:
                    direction = "up";
                    break;
                case 2:
                    direction = "down";
                    break;
                case 3:
                    direction = "left";
                    break;
                case 4:
                    direction = "right";
                    break;
                default:
                    direction = "idle";
                    break;
            }
            actionLockCounter = 0;
        }

    }
}
