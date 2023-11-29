package object;

import entity.Entity;
import main.GamePanel;
/** The cell object
 */
public class ObjCell extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of cell
     * @param gp - the main game panel
     */
    public ObjCell(GamePanel gp){
        /** The main game panel
         */
        super(gp);
        this.gp = gp;

        type = Type.Cell;
        down1 = setup("objects/cell",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 32;
        solidArea.width = 48;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
