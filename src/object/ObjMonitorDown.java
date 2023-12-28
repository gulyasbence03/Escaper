package object;

import entity.Entity;
import main.GamePanel;

/** The bottom part of Monitors in CCTV room
 */
public class ObjMonitorDown extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** Sets attributes and image of bottom part of monitors
     * @param gp - the main game panel
     */
    public ObjMonitorDown(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = Type.Monitor;
        down1 = setup("objects/monitor_down", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 36;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
