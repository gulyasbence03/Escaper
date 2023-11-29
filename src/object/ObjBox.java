package object;

import entity.Entity;
import main.GamePanel;
/** The box object
 */
public class ObjBox extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of box
     * @param gp - the main game panel
     */
    public ObjBox(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Box;
        down1 = setup("objects/box",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
