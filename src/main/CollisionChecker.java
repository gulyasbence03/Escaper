package main;

import entity.Entity;

import java.io.Serializable;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        // Stores the surrounding coordinates of entity, and row,column to check collision on them
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entityLeftX + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entityTopY + entity.solidArea.y;

        int entityLeftCol = entityLeftX/ GamePanel.TILE_SIZE;
        int entityRightCol = entityRightX/ GamePanel.TILE_SIZE;
        int entityTopRow = entityTopY/ GamePanel.TILE_SIZE;
        int entityBottomRow = entityBottomY/ GamePanel.TILE_SIZE;

        int tileNum1;
        int tileNum2; // type of tile close to entity
        // There is two, because if player is between two tiles, both tiles needs to be checked

        // use a temporal diresction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack){
            direction = entity.knockBackDirection;
        }

        switch (direction){
            // At direction checks if tile type is solid(by entity.collision attribute)
            // If one of 2 tiles are blocking the way, then to that direction do not let entity move
            case "up":
                entityTopRow = (entityTopY - entity.speed)/ GamePanel.TILE_SIZE; // Row above entity
                if(entityTopRow < GamePanel.MAX_SCREEN_ROW && entityTopRow > 0){
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // Top left tile
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // Top right tile
                    if(gp.tileM.tile[tileNum1].isSolidTile || gp.tileM.tile[tileNum2].isSolidTile){
                        entity.collisionOnUp = true; // can not move up
                    }
                }
                else{
                    if((double) (entityTopY - entity.speed) / GamePanel.TILE_SIZE < entity.solidArea.height*0.9 / GamePanel.TILE_SIZE){
                        entity.collisionOnUp = true; // can not move up
                    }
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.speed)/ GamePanel.TILE_SIZE; // Row under entity
                if (entityBottomRow < GamePanel.MAX_SCREEN_ROW && entityBottomRow > 0) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // Bottom left tile
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // Bottom right tile
                    if(gp.tileM.tile[tileNum1].isSolidTile || gp.tileM.tile[tileNum2].isSolidTile){
                        entity.collisionOnDown = true; // can not move down
                    }
                    else{
                        entity.collisionOnDown = false;
                    }
                }
                else{
                    entity.collisionOnDown = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed)/ GamePanel.TILE_SIZE; // Column to the left from entity
                if(entityLeftCol < GamePanel.MAX_SCREEN_COL && entityLeftCol > 0){
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // Left side upper tile
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // Left side bottom tile
                    if(gp.tileM.tile[tileNum1].isSolidTile || gp.tileM.tile[tileNum2].isSolidTile){
                        entity.collisionOnLeft = true; // can not move left
                    }
                }
                else{
                    if((double) (entityLeftX - entity.speed)/ GamePanel.TILE_SIZE < 0){
                        entity.collisionOnLeft = true; // can not move up
                    }
                }
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed)/ GamePanel.TILE_SIZE; // Column to the right from entity
                if(entityRightCol < GamePanel.MAX_SCREEN_COL && entityRightCol >= 0){
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // Right side top tile
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // Right side bottom tile
                    if(gp.tileM.tile[tileNum1].isSolidTile || gp.tileM.tile[tileNum2].isSolidTile){
                        entity.collisionOnRight = true; // can not move right
                    }
                }
                else{
                    entity.collisionOnRight = true; // can not move right
                }
                break;

            default:
                break;
        }

    }

    public int checkObject(Entity entity, boolean isPlayer){
        // Check which object is player colliding with
        int index = -1; // base number -1 (not possible)

        // use a temporal diresction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack){
            direction = entity.knockBackDirection;
        }

        // Looping through every object
        for(int i = 0; i<gp.objectArray.length; i++){

            if(gp.objectArray[i] != null){ // can be null if the array is not full

                // Get the entity's solid area position(Entity hitbox)
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
                // Get the object's solid area position(Object hitbox)
                gp.objectArray[i].solidArea.x = gp.objectArray[i].x + gp.objectArray[i].solidArea.x;
                gp.objectArray[i].solidArea.y = gp.objectArray[i].y + gp.objectArray[i].solidArea.y;


                switch (direction){
                    case "up":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.y -= entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(gp.objectArray[i].solidArea)){
                            if(gp.objectArray[i].isSolidObject){ // it means current object is solid
                                entity.collisionOnUp = true; // entity can not go through it
                            }
                            if(isPlayer){ // if entity is player
                                index = i; // store the index of that object
                            }
                        }
                        break;
                    case "down":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.y += entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(gp.objectArray[i].solidArea)){
                            if(gp.objectArray[i].isSolidObject){ // it means current object is solid
                                entity.collisionOnDown = true; // entity can not go through it
                            }
                            if(isPlayer){ // if entity is player
                                index = i; // store the index of that object
                            }
                        }
                        break;
                    case "left":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.x -= entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(gp.objectArray[i].solidArea)){
                            if(gp.objectArray[i].isSolidObject){ // it means current object is solid
                                entity.collisionOnLeft = true; // entity can not go through it
                            }
                            if(isPlayer){ // if entity is player
                                index = i; // store the index of that object
                            }
                        }
                        break;
                    case "right":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.x += entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(gp.objectArray[i].solidArea)){
                            if(gp.objectArray[i].isSolidObject){ // it means current object is solid
                                entity.collisionOnRight = true; // entity can not go through it
                            }
                            if(isPlayer){ // if entity is player
                                index = i; // store the index of that object
                            }
                        }
                        break;
                }
                // Reset solidArea attributes
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objectArray[i].solidArea.x = gp.objectArray[i].solidAreaDefaultX;
                gp.objectArray[i].solidArea.y = gp.objectArray[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //NPC
    public int checkEntity(Entity entity, Entity[] target){
        // Check which object is player colliding with
        int index = -1; // base number -1 (not possible)

        // use a temporal diresction when it's being knockbacked
        String direction = entity.direction;
        if(entity.knockBack){
            direction = entity.knockBackDirection;
        }

        // Looping through every object
        for(int i = 0; i<target.length; i++){

            if(target[i] != null){ // can be null if the array is not full

                // Get the entity's solid area position(Entity hitbox)
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
                // Get the object's solid area position(Object hitbox)
                target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                target[i].solidArea.y = target[i].y + target[i].solidArea.y;

                switch (direction){
                    case "up":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.y -= entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOnUp = true; // entity can not go through it
                            index = i;
                        }
                        break;
                    case "down":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.y += entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOnDown = true; // entity can not go through it
                            index = i;
                        }
                        break;
                    case "left":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.x -= entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOnLeft = true; // entity can not go through it
                            index = i;
                        }
                        break;
                    case "right":
                        // check if the next step of player is possible,
                        // so move it's hitbox to check there collision
                        entity.solidArea.x += entity.speed;
                        // if true(2 rectangle intersects) these two collide
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOnRight = true; // entity can not go through it
                            index = i;
                        }
                        break;
                }
                // Reset solidArea attributes
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity){
        // Looping through every object

        if(gp.player != null){ // can be null if the array is not full

            // Get the entity's solid area position(Entity hitbox)
            entity.solidArea.x = entity.x + entity.solidArea.x;
            entity.solidArea.y = entity.y + entity.solidArea.y;
            // Get the object's solid area position(Object hitbox)
            gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;

            // use a temporal diresction when it's being knockbacked
            String direction = entity.direction;

            if(entity.knockBack){
                direction = entity.knockBackDirection;
            }


            switch (direction){
                case "up":
                    // check if the next step of player is possible,
                    // so move it's hitbox to check there collision
                    entity.solidArea.y -= entity.speed;
                    // if true(2 rectangle intersects) these two collide
                    if(entity.solidArea.intersects(gp.player.solidArea)){
                        entity.collisionOnUp = true; // entity can not go through it
                    }
                    break;
                case "down":
                    // check if the next step of player is possible,
                    // so move it's hitbox to check there collision
                    entity.solidArea.y += entity.solidAreaDefaultY;
                    // if true(2 rectangle intersects) these two collide
                    if(entity.solidArea.intersects(gp.player.solidArea)){
                        entity.collisionOnDown = true; // entity can not go through it
                    }
                    break;
                case "left":
                    // check if the next step of player is possible,
                    // so move it's hitbox to check there collision
                    entity.solidArea.x -= entity.speed;
                    // if true(2 rectangle intersects) these two collide
                    if(entity.solidArea.intersects(gp.player.solidArea)){
                        entity.collisionOnLeft = true; // entity can not go through it
                    }
                    break;
                case "right":
                    // check if the next step of player is possible,
                    // so move it's hitbox to check there collision
                    entity.solidArea.x += entity.speed;
                    // if true(2 rectangle intersects) these two collide
                    if(entity.solidArea.intersects(gp.player.solidArea)){
                        entity.collisionOnRight = true; // entity can not go through it
                    }
                    break;
            }
            // Reset solidArea attributes
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        }
        return entity.collisionOnRight || entity.collisionOnDown || entity.collisionOnLeft || entity.collisionOnUp;
    }
}
