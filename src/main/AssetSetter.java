package main;

import entity.NPC_Chef;
import entity.NPC_FIXING;
import entity.NPC_Police;
import entity.RED_Police;
import object.*;

import java.io.Serializable;

/** Setting objets and NPC-s to the right place of the map
 */
public class AssetSetter  {
    /** The main game panel
     */
    GamePanel gp;

    /** Constructor that sets the main game panel
     * @param gp - the main game panel
     */
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    /** Place objects to fix screen positions at the beginning of the new game
     */
    public void setObject(){
        // Place objects' coordinates on screen

        // DOORS
        // CELL ONE
        gp.objectArray[0] = new ObjBlueDoor(gp);
        gp.objectArray[0].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[0].y = 4 * GamePanel.TILE_SIZE;

        // CELL TWO
        gp.objectArray[1] = new ObjBlueDoor(gp);
        gp.objectArray[1].x = 7 * GamePanel.TILE_SIZE;
        gp.objectArray[1].y = 4 * GamePanel.TILE_SIZE;

        // STORAGE
        gp.objectArray[2] = new ObjBlueDoor(gp);
        gp.objectArray[2].x = 10 * GamePanel.TILE_SIZE;
        gp.objectArray[2].y = 7 * GamePanel.TILE_SIZE;

        // CCTV ROOM
        gp.objectArray[3] = new ObjRedDoor(gp);
        gp.objectArray[3].x = 10 * GamePanel.TILE_SIZE;
        gp.objectArray[3].y = 3 * GamePanel.TILE_SIZE;

        gp.objectArray[4] = new ObjRedDoor(gp);
        gp.objectArray[4].x = 13 * GamePanel.TILE_SIZE;
        gp.objectArray[4].y = 5 * GamePanel.TILE_SIZE;

        // CELLS
        // CELL ONE
        gp.objectArray[5] = new ObjCell(gp);
        gp.objectArray[5].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[5].y = 4 * GamePanel.TILE_SIZE;

        gp.objectArray[6] = new ObjCell(gp);
        gp.objectArray[6].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[6].y = 4 * GamePanel.TILE_SIZE;

        // CELL TWO
        gp.objectArray[7] = new ObjCell(gp);
        gp.objectArray[7].x = 6 * GamePanel.TILE_SIZE;
        gp.objectArray[7].y = 4 * GamePanel.TILE_SIZE;

        gp.objectArray[8] = new ObjCell(gp);
        gp.objectArray[8].x = 8 * GamePanel.TILE_SIZE;
        gp.objectArray[8].y = 4 * GamePanel.TILE_SIZE;

        // KEYCARDS
        // BLUES
        // BY THE POLICE
        gp.objectArray[9] = new ObjBlueKeycard(gp);
        gp.objectArray[9].x = -1 * GamePanel.TILE_SIZE;
        gp.objectArray[9].y = -1 * GamePanel.TILE_SIZE;

        gp.objectArray[10] = new ObjBlueKeycard(gp);
        gp.objectArray[10].x = -1 * GamePanel.TILE_SIZE;
        gp.objectArray[10].y = -1 * GamePanel.TILE_SIZE;

        gp.objectArray[11] = new ObjBlueKeycard(gp);
        gp.objectArray[11].x = 7 * GamePanel.TILE_SIZE;
        gp.objectArray[11].y = 2 * GamePanel.TILE_SIZE;

        // RED KEYCARDS
        // BY POLICE
        gp.objectArray[12] = new ObjRedKeycard(gp);
        gp.objectArray[12].x = -1 * GamePanel.TILE_SIZE;
        gp.objectArray[12].y = -1 * GamePanel.TILE_SIZE;
        // IN STORAGE
        gp.objectArray[13] = new ObjRedKeycard(gp);
        gp.objectArray[13].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[13].y = 9 * GamePanel.TILE_SIZE;

        // TOILETS
        // CELL ONE
        gp.objectArray[14] = new ObjToilet(gp);
        gp.objectArray[14].x = 4 * GamePanel.TILE_SIZE;
        gp.objectArray[14].y = 2 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        // CELL TWO
        gp.objectArray[15] = new ObjToilet(gp);
        gp.objectArray[15].x = 8 * GamePanel.TILE_SIZE;
        gp.objectArray[15].y = 2 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        //BEDS
        // CELL ONE
        gp.objectArray[16] = new ObjBedTop(gp);
        gp.objectArray[16].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[16].y = 2 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        gp.objectArray[17] = new ObjBedDown(gp);
        gp.objectArray[17].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[17].y = 3 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        // CELL TWO
        gp.objectArray[18] = new ObjBedTop(gp);
        gp.objectArray[18].x = 6 * GamePanel.TILE_SIZE;
        gp.objectArray[18].y = 2 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        gp.objectArray[19] = new ObjBedDown(gp);
        gp.objectArray[19].x = 6 * GamePanel.TILE_SIZE;
        gp.objectArray[19].y = 3 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2;

        // CCTV ROOM
        // MONITORS
        gp.objectArray[20] = new ObjMonitorTop(gp);
        gp.objectArray[20].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[20].y = 3 * GamePanel.TILE_SIZE;

        gp.objectArray[21] = new ObjMonitorDown(gp);
        gp.objectArray[21].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[21].y = 4 * GamePanel.TILE_SIZE;

        //BOXES IN STORAGE
        gp.objectArray[22] = new ObjBox(gp);
        gp.objectArray[22].x = 12 * GamePanel.TILE_SIZE;
        gp.objectArray[22].y = 8 * GamePanel.TILE_SIZE;

        gp.objectArray[23] = new ObjBox(gp);
        gp.objectArray[23].x = 13 * GamePanel.TILE_SIZE;
        gp.objectArray[23].y = 8 * GamePanel.TILE_SIZE;

        gp.objectArray[24] = new ObjBox(gp);
        gp.objectArray[24].x = 13 * GamePanel.TILE_SIZE;
        gp.objectArray[24].y = 9 * GamePanel.TILE_SIZE;

        gp.objectArray[25] = new ObjBox(gp);
        gp.objectArray[25].x = 10 * GamePanel.TILE_SIZE;
        gp.objectArray[25].y = 9 * GamePanel.TILE_SIZE;

        // Dining tables
        gp.objectArray[26] = new ObjTable(gp);
        gp.objectArray[26].x = 5 * GamePanel.TILE_SIZE;
        gp.objectArray[26].y = 8 * GamePanel.TILE_SIZE;

        gp.objectArray[27] = new ObjTable(gp);
        gp.objectArray[27].x = 7 * GamePanel.TILE_SIZE;
        gp.objectArray[27].y = 8 * GamePanel.TILE_SIZE;

        // BOX
        gp.objectArray[28] = new ObjBox(gp);
        gp.objectArray[28].x = 2 * GamePanel.TILE_SIZE;
        gp.objectArray[28].y = 6 * GamePanel.TILE_SIZE -1;

        // FOOD
        gp.objectArray[29] = new ObjFood(gp);
        gp.objectArray[29].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[29].y = 8 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2 - 1;

        gp.objectArray[30] = new ObjFood(gp);
        gp.objectArray[30].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[30].y = 9 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2 - 1;

        gp.objectArray[31] = new ObjHalfFood(gp);
        gp.objectArray[31].x = 3 * GamePanel.TILE_SIZE;
        gp.objectArray[31].y = 10 * GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/2 - 1;
    }

    /** Placing different NPCs to their places at the beginning of a new game
     */
    public void setNPC(){
        // CCTV ROOM
        // RED POLICE
        gp.npcPolice[0] = new RED_Police(gp);
        gp.npcPolice[0].x = GamePanel.TILE_SIZE*13 - 18;
        gp.npcPolice[0].y = GamePanel.TILE_SIZE*4;
        gp.npcPolice[0].currentItem = gp.objectArray[12];

        // IN CELL ONE FIXING TOILET
        gp.npcPolice[1] = new NPC_FIXING(gp);
        gp.npcPolice[1].x = GamePanel.TILE_SIZE*3 - GamePanel.TILE_SIZE/6;
        gp.npcPolice[1].y = GamePanel.TILE_SIZE*2 - GamePanel.TILE_SIZE/3;
        gp.npcPolice[1].currentItem = gp.objectArray[10];

        // IN DINING ROOM
        gp.npcPolice[2] = new NPC_Police(gp);
        gp.npcPolice[2].x = GamePanel.TILE_SIZE*10;
        gp.npcPolice[2].y = GamePanel.TILE_SIZE*3 + 22;
        gp.npcPolice[2].currentItem = gp.objectArray[9];

        // CHEF
        gp.npcPolice[3] = new NPC_Chef(gp);
        gp.npcPolice[3].x = GamePanel.TILE_SIZE*2 + 11;
        gp.npcPolice[3].y = GamePanel.TILE_SIZE*7 + GamePanel.TILE_SIZE/3;
    }
}
