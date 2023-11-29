package object;

import entity.Entity;
import main.GamePanel;

import java.io.Serializable;
/** The blue keycard that can open blue doors
 */
public class ObjBlueKeycard extends Entity {
    public  ObjBlueKeycard(GamePanel gp){
        /** Sets the main game panel
         */
        super(gp);
        type = Type.BlueKeycard;
        down1 = setup("objects/blue_keycard",GamePanel.TILE_SIZE/2,GamePanel.TILE_SIZE/2);
    }
}
