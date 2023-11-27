package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjHeart extends Entity {
    GamePanel gp;

    public ObjHeart(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = Type.Heart;
        imageHeart = setup("objects/heart",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        imageBlankHeart = setup("objects/blank_heart",GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
    }
}
