package data;

import entity.Entity;
import main.GamePanel;

import java.io.*;
import java.util.ArrayList;

/** Responsible for saving the values in dataStorage into a file
 *  And read back them into the actual game values
 */
public class SaveLoad {
    /** The main game panel
     */
    GamePanel gp;

    /** Constructor sets main game panel
     * @param gp - the main game panel
     */
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    /** Saves the values stored ind DataStorage into "save.dat" file
     */
    public void save(){
        try{
            FileOutputStream fos = new FileOutputStream("save.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            DataStorage dataStorage = new DataStorage();
            // Player Stats
            dataStorage.maxLife = gp.player.maxLife;
            dataStorage.life = gp.player.life;
            dataStorage.x = gp.player.x;
            dataStorage.y = gp.player.y;
            dataStorage.direction = gp.player.direction;
            // find the index of the current item in the object array, so later can read this index
            int i = -1;
            if(gp.player.currentItem != null){
                for(i = 0; i< gp.objectArray.length; i++){
                    if(gp.objectArray[i] != null && gp.player.currentItem.x == gp.objectArray[i].x &&
                            gp.player.currentItem.y == gp.objectArray[i].y &&
                            gp.player.currentItem.type == gp.objectArray[i].type){
                        break;
                    }
                }
            }
            dataStorage.currentItemIndex = i;

            dataStorage.objectStillThere = new ArrayList<>();
            dataStorage.npcStillThere = new ArrayList<>();
            // Check for each object if still in game (if null then set to false)
            for(Entity obj : gp.objectArray){
                if(obj == null){
                    dataStorage.objectStillThere.add(false);
                }
                else{
                    dataStorage.objectStillThere.add(true);
                }
            }
            // Check for each npc if still in game (if null then set to false)
            for(Entity obj : gp.npcPolice){
                if(obj == null){
                    dataStorage.npcStillThere.add(false);
                }
                else{
                    dataStorage.npcStillThere.add(true);
                }
            }

            // Write the DataStorage object to file
            oos.writeObject(dataStorage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Loads data back from file to game's actual values
     *
     */
    public void load(){
        try{
           FileInputStream fis = new FileInputStream("save.dat");
           ObjectInputStream ois = new ObjectInputStream(fis);

            DataStorage dataStorage = (DataStorage)ois.readObject();

            // Set player's stats
            gp.player.x = dataStorage.x;
            gp.player.y = dataStorage.y;
            gp.player.life = dataStorage.life;
            gp.player.maxLife = dataStorage.maxLife;
            gp.player.direction = dataStorage.direction;
            // find current item by index
            if(dataStorage.currentItemIndex != -1){
                gp.player.currentItem = gp.objectArray[dataStorage.currentItemIndex];
            }
            // delete objects that are no longer in the current save
            for(int i = 0; i<gp.objectArray.length; i++){
                if(Boolean.FALSE.equals(dataStorage.objectStillThere.get(i))){
                    gp.objectArray[i] = null;
                }
            }
            // delete npcs that are no longer in the current save
            for(int i = 0; i<gp.npcPolice.length; i++){
                if(Boolean.FALSE.equals(dataStorage.npcStillThere.get(i))){
                    gp.npcPolice[i] = null;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
