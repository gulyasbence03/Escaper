package main;

import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        // Place objects' coordinates on screen
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].x = 9 * gp.tileSize;
        gp.obj[0].y = 4 * gp.tileSize;
        gp.obj[1] = new OBJ_Door();
        gp.obj[1].x = 7 * gp.tileSize;
        gp.obj[1].y = 8 * gp.tileSize;
    }
}
