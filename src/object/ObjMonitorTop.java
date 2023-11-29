package object;

import entity.Entity;
import main.GamePanel;
/** The top part of monitors in CCTV room
 */
public class ObjMonitorTop extends Entity {
    GamePanel gp;
    /** Sets attributes and image of top part of monitors
     * @param gp - the main game panel
     */
    public ObjMonitorTop(GamePanel gp) {
        /** The main game panel
         */
        super(gp);
        this.gp = gp;

        type = Type.Monitor;
        down1 = setup("objects/monitor_top", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 36;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
