package data;

import entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // Player status
    int maxLife;
    int life;
    int x;
    int y;

    String direction;

    int currentItemIndex;

    ArrayList<Boolean> objectStillThere;
    ArrayList<Boolean> npcStillThere;
}
