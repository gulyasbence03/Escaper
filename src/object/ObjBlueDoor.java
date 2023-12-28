package object;

import entity.Entity;
import main.GamePanel;

import java.io.Serializable;
/** The blue door, that can be opened with blue keycard
 */
public class ObjBlueDoor extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of blue door
     * @param gp - the main game panel
     */
    public ObjBlueDoor(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.BlueDoor;
        down1 = setup("objects/blue_door",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 32;
        solidArea.width = 48;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
