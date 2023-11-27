package object;

import entity.Entity;
import main.GamePanel;

import java.io.Serializable;

public class ObjRedKeycard extends Entity {
    public ObjRedKeycard(GamePanel gp){
        super(gp);
        type = Type.RedKeycard;
        down1 = setup("objects/red_keycard",GamePanel.TILE_SIZE/2,GamePanel.TILE_SIZE/2);
    }
}
