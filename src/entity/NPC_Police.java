package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
/** The Police NPC in the dining room going around
 */
public class NPC_Police extends Entity {
    /** Constructor of the NPC that sets default values
     * @param gp - the main game panel
     */
    public NPC_Police(GamePanel gp){
        super(gp);
        direction = "down";
        speed = DEFAULT_SPEED;
        maxLife = 3;
        life = maxLife;
        type = Type.POLICE;
        getNpcImage();
        getNPCAttackImage();

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
        up1 = setup("npc/police_up1");
        up2 = setup("npc/police_up2");
        down1 = setup("npc/police_down1");
        down2 = setup("npc/police_down2");
        left1 = setup("npc/police_left1");
        left2 = setup("npc/police_left2");
        right1 = setup("npc/police_right1");
        right2 = setup("npc/police_right2");
        idle1 = down1;
        idle2 = down1;
    }
    /** Sets up NPC's attacking images using setup() function
     */
    public void getNPCAttackImage() {
        attackUp1 = setup("npc/police_attack_up1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackUp2 = setup("npc/police_attack_up1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackDown1 = setup("npc/police_attack_down1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackDown2 = setup("npc/police_attack_down2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackLeft1 = setup("npc/police_attack_left1", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackLeft2 = setup("npc/police_attack_left2", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackRight1 = setup("npc/police_attack_right1", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackRight2 = setup("npc/police_attack_right2", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
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

    /** The NPC Police's own action (looking around dining room), and if being hit than chase player and attack
     */
    public void setAction(){
        if(onPath){ // chase player
            int goalCol = (gp.player.x + gp.player.solidAreaDefaultX) / GamePanel.TILE_SIZE;
            int goalRow = (gp.player.y + gp.player.solidAreaDefaultY) / GamePanel.TILE_SIZE;

            searchPath(goalCol,goalRow);
        }
        else{ // going in circles in dining room
            actionLockCounter++;
            if(actionLockCounter <= 60){ direction = "down"; }
            if(actionLockCounter >60 && actionLockCounter <= 75){ direction = "idle"; }
            if(actionLockCounter >75 && actionLockCounter <= 120){ direction = "left"; }
            if(actionLockCounter >120 && actionLockCounter <= 140){ direction = "idle"; }
            if(actionLockCounter > 140 && actionLockCounter <= 210){ direction = "down"; }
            if(actionLockCounter > 210 && actionLockCounter <= 300){ direction = "idle"; }
            if(actionLockCounter >300 && actionLockCounter <= 400){direction = "left"; }
            if(actionLockCounter > 400 && actionLockCounter <= 450){ direction = "up"; }
            if(actionLockCounter > 450 && actionLockCounter <= 550){ direction = "right"; }
            if(actionLockCounter > 550) { actionLockCounter = 160; }

        }
        if(!attacking && life<maxLife){ // check if npc should attack player
            checkAttackOrNot(GamePanel.TILE_SIZE*2,GamePanel.TILE_SIZE);
        }
    }
}
