package object;

import entity.Entity;
import main.GamePanel;

public class ObjDoor extends Entity {
    GamePanel gp;

    public ObjDoor(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Door";
        down1 = setup("objects/door",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        isSolidObject = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
