package object;

import entity.Entity;
import main.GamePanel;
/** The top part of the bed object ( it is being drawn partly)
 */
public class ObjBedTop extends Entity {
    /** The main game panel
     */
    GamePanel gp;

    /** Sets attributes and image of top part of the bed
     * @param gp - the main game panel
     */
    public ObjBedTop(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = Entity.Type.Bed;
        down1 = setup("objects/bedtop", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
