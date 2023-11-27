package main;

import entity.NPC_Police;
import object.*;

import java.io.Serializable;

public class AssetSetter  {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        // Place objects' coordinates on screen
        // KEY object
        gp.objectArray[0] = new ObjBlueKeycard(gp);
        gp.objectArray[0].x = 7 * GamePanel.TILE_SIZE;
        gp.objectArray[0].y = 2 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[1] = new ObjBlueDoor(gp);
        gp.objectArray[1].x = 14 * GamePanel.TILE_SIZE;
        gp.objectArray[1].y = 5 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[2] = new ObjBlueDoor(gp);
        gp.objectArray[2].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[2].y = 3 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[3] = new ObjBlueDoor(gp);
        gp.objectArray[3].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[3].y = 4 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[4] = new ObjBlueDoor(gp);
        gp.objectArray[4].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[4].y = 12 * GamePanel.TILE_SIZE;

        // Door object
        gp.objectArray[4] = new ObjRedDoor(gp);
        gp.objectArray[4].x = 10 * GamePanel.TILE_SIZE;
        gp.objectArray[4].y = 4 * GamePanel.TILE_SIZE;

        // KEY object
        gp.objectArray[5] = new ObjBlueKeycard(gp);
        gp.objectArray[5].x = -1 * GamePanel.TILE_SIZE;
        gp.objectArray[5].y = -1 * GamePanel.TILE_SIZE;

        // KEY object - hide and reveal when police drops it
        gp.objectArray[6] = new ObjBlueKeycard(gp);
        gp.objectArray[6].x = -1 * GamePanel.TILE_SIZE;
        gp.objectArray[6].y = -1 * GamePanel.TILE_SIZE;

        // KEY object
        gp.objectArray[7] = new ObjRedKeycard(gp);
        gp.objectArray[7].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[7].y = 2 * GamePanel.TILE_SIZE;
    }

    public void setNPC(){
        gp.npcPolice[0] = new NPC_Police(gp);
        gp.npcPolice[0].x = GamePanel.TILE_SIZE*11;
        gp.npcPolice[0].y = GamePanel.TILE_SIZE*2;
        gp.npcPolice[0].currentItem = gp.objectArray[6];

        gp.npcPolice[1] = new NPC_Police(gp);
        gp.npcPolice[1].x = GamePanel.TILE_SIZE*3;
        gp.npcPolice[1].y = GamePanel.TILE_SIZE*9;
        gp.npcPolice[1].currentItem = gp.objectArray[5];
    }

    public void setCells(){
        gp.objectArray[8] = new ObjCell(gp);
        gp.objectArray[8].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[8].y = 3 * GamePanel.TILE_SIZE;

        gp.objectArray[9] = new ObjCell(gp);
        gp.objectArray[9].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[9].y = 3 * GamePanel.TILE_SIZE;

        gp.objectArray[10] = new ObjCell(gp);
        gp.objectArray[10].x = 5 * GamePanel.TILE_SIZE;
        gp.objectArray[10].y = 3 * GamePanel.TILE_SIZE;
    }
}
