package main;

import object.ObjDoor;
import object.ObjKey;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        // Place objects' coordinates on screen
        // KEY object
        gp.objectArray[0] = new ObjKey();
        gp.objectArray[0].x = 7 * GamePanel.TILE_SIZE;
        gp.objectArray[0].y = 3 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[1] = new ObjDoor();
        gp.objectArray[1].x = 14 * GamePanel.TILE_SIZE;
        gp.objectArray[1].y = 5 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[2] = new ObjDoor();
        gp.objectArray[2].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[2].y = 4 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[3] = new ObjDoor();
        gp.objectArray[3].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[3].y = 4 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[4] = new ObjDoor();
        gp.objectArray[4].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[4].y = 12 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[4] = new ObjDoor();
        gp.objectArray[4].x = 10 * GamePanel.TILE_SIZE;
        gp.objectArray[4].y = 4 * GamePanel.TILE_SIZE;

        // KEY object
        gp.objectArray[5] = new ObjKey();
        gp.objectArray[5].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[5].y = 3 * GamePanel.TILE_SIZE;

        // KEY object
        gp.objectArray[6] = new ObjKey();
        gp.objectArray[6].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[6].y = 2 * GamePanel.TILE_SIZE;

        // KEY object
        gp.objectArray[7] = new ObjKey();
        gp.objectArray[7].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[7].y = 3 * GamePanel.TILE_SIZE;
    }
}
