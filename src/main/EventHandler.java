package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect;
    int previousEventX;
    int previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW];
        int col = 0;
        int row = 0;
        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){
        // Check if the player charachter is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.x - previousEventX);
        int yDistance = Math.abs(gp.player.y - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > GamePanel.TILE_SIZE){
            canTouchEvent = true;
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
        eventRect[eventCol][eventRow].x = eventCol * GamePanel.TILE_SIZE + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow * GamePanel.TILE_SIZE + eventRect[eventCol][eventRow].y;

        if(gp.player.solidArea.intersects(eventRect[eventCol][eventRow]) && eventRect[eventCol][eventCol].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;

                previousEventX = gp.player.x;
                previousEventY = gp.player.y;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;
        return hit;
    }



}
