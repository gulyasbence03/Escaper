package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/** The Chef NPC in the kitchen
 */
public class NPC_Chef extends Entity {
    /** Constructor of the NPC that sets default values
     * @param gp - the main game panel
     */
    public NPC_Chef(GamePanel gp){
        super(gp);
        direction = "down";
        speed = DEFAULT_SPEED;
        maxLife = 3;
        life = maxLife;
        type = Type.POLICE;
        getNpcImage(); // loads images in


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

    /** Sets up NPC's images using setup() function
     */
    public void getNpcImage(){
        idle1 = setup("npc/chef1",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE*2);
        idle2 = setup("npc/chef2",GamePanel.TILE_SIZE*2,GamePanel.TILE_SIZE*2);
    }

    /** Loads in given image from "res" directory
     * @param imageName - the image name without ".png"
     * @return the loaded image
     */
    public BufferedImage setup(String imageName){
        // Read images into variables from resource directory
        // Object.requireNonNull() to ensure that it is not null
        // 2 sprites(image) for every direction to be able to animate movement
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imageName + ".png")));
            image = uTool.scaleImage(image,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    /** The chef's own action (cooking)
     */
    public void setAction(){
        // cooking animation of chef
        actionLockCounter++;
        direction = "idle";
        if(actionLockCounter <=60){
            spriteNum = 1;
        }
        if(actionLockCounter >60 && actionLockCounter <= 140){
            spriteNum = 2;
        }
        if (actionLockCounter > 160){
            actionLockCounter = 0;
        }
    }
}
