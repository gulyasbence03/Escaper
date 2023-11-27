package data;

import entity.Entity;
import main.GamePanel;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public void save(){
        try{
            FileOutputStream fos = new FileOutputStream("save.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            DataStorage dataStorage = new DataStorage();
            // Player Status
            dataStorage.maxLife = gp.player.maxLife;
            dataStorage.life = gp.player.life;
            dataStorage.x = gp.player.x;
            dataStorage.y = gp.player.y;
            dataStorage.direction = gp.player.direction;
            int i = -1;
            if(gp.player.currentItem != null){
                for(i = 0; i< gp.objectArray.length; i++){
                    if(gp.player.currentItem.x == gp.objectArray[i].x &&
                            gp.player.currentItem.y == gp.objectArray[i].y &&
                            gp.player.currentItem.type == gp.objectArray[i].type){
                        break;
                    }
                }
            }
            dataStorage.currentItemIndex = i;
            dataStorage.objectStillThere = new ArrayList<>();
            dataStorage.npcStillThere = new ArrayList<>();
            for(Entity obj : gp.objectArray){
                if(obj == null){
                    dataStorage.objectStillThere.add(false);
                }
                else{
                    dataStorage.objectStillThere.add(true);
                }
            }
            for(Entity obj : gp.npcPolice){
                if(obj == null){
                    dataStorage.npcStillThere.add(false);
                }
                else{
                    dataStorage.npcStillThere.add(true);
                }
            }

            // Write the DataStorage object
            oos.writeObject(dataStorage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void load(){
        try{
           FileInputStream fis = new FileInputStream("save.dat");
           ObjectInputStream ois = new ObjectInputStream(fis);

            DataStorage dataStorage = (DataStorage)ois.readObject();

            gp.player.x = dataStorage.x;
            gp.player.y = dataStorage.y;
            gp.player.life = dataStorage.life;
            gp.player.maxLife = dataStorage.maxLife;
            gp.player.direction = dataStorage.direction;
            if(dataStorage.currentItemIndex != -1){
                gp.player.currentItem = gp.objectArray[dataStorage.currentItemIndex];
            }
            for(int i = 0; i<gp.objectArray.length; i++){
                if(Boolean.FALSE.equals(dataStorage.objectStillThere.get(i))){
                    gp.objectArray[i] = null;
                }
            }
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
