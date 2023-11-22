package object;

import entity.Entity;
import main.GamePanel;

public class ObjKey extends Entity {
    public ObjKey(GamePanel gp){
        super(gp);
        name = "Key";
        down1 = setup("objects/key",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
    }
}
