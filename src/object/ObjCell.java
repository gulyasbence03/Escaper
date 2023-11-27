package object;

import entity.Entity;
import main.GamePanel;

public class ObjCell extends Entity {
    GamePanel gp;

    public ObjCell(GamePanel gp){
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
