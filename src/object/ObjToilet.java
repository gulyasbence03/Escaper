package object;

import entity.Entity;
import main.GamePanel;

/** The toilet object in cell rooms
 */
public class ObjToilet extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of toilet
     * @param gp - the main game panel
     */
    public ObjToilet(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Toilet;
        down1 = setup("objects/toilet",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
