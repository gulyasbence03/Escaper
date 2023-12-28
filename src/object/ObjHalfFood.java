package object;

import entity.Entity;
import main.GamePanel;
/** Half part of a food object
 */
public class ObjHalfFood extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of the half part of the food
     * @param gp - the main game panel
     */
    public ObjHalfFood(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Box;
        down1 = setup("objects/halffood",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
