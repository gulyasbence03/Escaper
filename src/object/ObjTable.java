package object;

import entity.Entity;
import main.GamePanel;
/** The table object in dining room
 */
public class ObjTable extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of table
     * @param gp - the main game panel
     */
    public ObjTable(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Table;
        down1 = setup("objects/table",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
