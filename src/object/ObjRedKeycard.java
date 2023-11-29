package object;

import entity.Entity;
import main.GamePanel;

import java.io.Serializable;

/** The red keycard that can open red doors
 */
public class ObjRedKeycard extends Entity {
    /** Sets attributes and image of red keycard
     * @param gp - the main game panel
     */
    public ObjRedKeycard(GamePanel gp){
        super(gp);
        type = Type.RedKeycard;
        down1 = setup("objects/red_keycard",GamePanel.TILE_SIZE/2,GamePanel.TILE_SIZE/2);
    }
}
