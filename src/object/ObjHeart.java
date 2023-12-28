package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
/** Heart object for displaying player's health
 */
public class ObjHeart extends Entity {
    GamePanel gp;
    /** Sets attributes and image of heart
     * @param gp - the main game panel
     */
    public ObjHeart(GamePanel gp){
        /** The main game panel
         */
        super(gp);
        this.gp = gp;

        type = Type.Heart;
        imageHeart = setup("objects/heart",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        imageBlankHeart = setup("objects/blank_heart",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
    }
}
