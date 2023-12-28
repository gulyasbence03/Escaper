package object;

import entity.Entity;
import main.GamePanel;
/** The food object in the kitchen
 */
public class ObjFood extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of food
     * @param gp - the main game panel
     */
    public ObjFood(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Box;
        down1 = setup("objects/food",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
