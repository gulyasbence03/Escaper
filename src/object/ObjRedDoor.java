package object;

import entity.Entity;
import main.GamePanel;

import java.io.Serializable;

/** The red door that can be opened with red keycard
 */
public class ObjRedDoor extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of red door
     * @param gp - the main game panel
     */
    public ObjRedDoor(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.RedDoor;
        down1 = setup("objects/red_door",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 32;
        solidArea.width = 48;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
