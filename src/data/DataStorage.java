package data;

import entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/** Holds the values that are being stored/saved in a file
 */
public class DataStorage implements Serializable {
    /**
     * Player stats
     */
    int maxLife;
    int life;
    int x;
    int y;
    String direction;
    int currentItemIndex;

    /** Holds if it is still in the game(not removed) for each object and npc
     */
    ArrayList<Boolean> objectStillThere;
    ArrayList<Boolean> npcStillThere;
}
