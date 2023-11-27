package entity;

import data.SaveLoad;
import main.GamePanel;
import main.KeyHandler;
import main.UI;
import object.ObjBlueKeycard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        type = Type.PLAYER;
        maxLife = 3;
        setDefaultValues(); // set up coordinates, speed, direction
        getPlayerImage(); // load images to animate and draw character
        getPlayerAttackImage(); // load images to animate attacking

        // Solid area(hitbox) is the area that is collidable with solid tiles(like walls) and objects
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 24;
        solidArea.width = 32;
        solidArea.height = 24;

        attackArea.width = 36;
        attackArea.height = 36;

        // These are needed, because we change the player's solid area
        // to check if it's capable of moving that way( if there is solid object/tile the next step)
        // Then reset these coordinates back on player
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues() {
        speed = DEFAULT_SPEED;

        // Player status
        setDefaultPositions();
        setDefaultHealthAndItem();
    }

    public void getPlayerImage() {
        up1 = setup("player/up1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        up2 = setup("player/up2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        down1 = setup("player/down1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        down2 = setup("player/down2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        left1 = setup("player/left1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        left2 = setup("player/left2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        right1 = setup("player/right1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        right2 = setup("player/right2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("player/hit_up1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackUp2 = setup("player/hit_up2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackDown1 = setup("player/hit_down1", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackDown2 = setup("player/hit_down2", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE * 2);
        attackLeft1 = setup("player/hit_left1", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackLeft2 = setup("player/hit_left2", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackRight1 = setup("player/hit_right1", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
        attackRight2 = setup("player/hit_right2", GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE);
    }


    public void update() {
        if(checkEscaped()){
            gp.gameState = GamePanel.GameState.ESCAPED;
            gp.ui.command = UI.Command.MENU;
        }

        // Check tile collision, and reset at beginning to check every frame
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;

        if(knockBack) {
            checkCollision();

            if (collisionOnUp || collisionOnDown || collisionOnLeft || collisionOnRight) {
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
            else{
                if(knockBackDirection.equals("up") && !collisionOnUp){ // If collision upwards false, player can move up
                    y -= speed;
                }
                else if(knockBackDirection.equals("down") && !collisionOnDown){ // If collision downwards false, player can move down
                    y += speed;
                }
                else if(knockBackDirection.equals("left") && !collisionOnLeft){ // If collision to the left is false, player can move left
                    x -= speed;
                }
                else if(knockBackDirection.equals("right") && !collisionOnRight){ // If collision to the right is false, player can move right
                    x += speed;
                }
                spriteCounter++;
                if (spriteCounter > 20 && !direction.equals("idle")) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                        gp.playSoundEffect(1); // on every second step, step sound effect is being played
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                        gp.playSoundEffect(1); // on every second step, step sound effect is being played
                    }
                    spriteCounter = 0;
                }
            }

            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
        }
        else if (attacking) {
            attacking();
        }
        else if(interacting){
            interacting();
        }
        else{
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed || keyH.interactPressed) {    // If any movement key is being pressed
                // Top left corner of the screen is (0,0)
                // X value increases to the right
                // Y value increases as we go down

                if(!attacking && !keyH.interactPressed){
                    // Set direction based on key pressed
                    if (keyH.upPressed) {
                        direction = "up";
                        checkCollision(direction);
                    }
                    if (keyH.downPressed) {
                        direction = "down";
                        checkCollision(direction);
                    }
                    if (keyH.leftPressed) {
                        direction = "left";
                        checkCollision(direction);
                    }
                    if (keyH.rightPressed) {
                        direction = "right";
                        checkCollision(direction);
                    }
                    if (keyH.shiftPressed && !attacking) {
                        speed = (int) (DEFAULT_SPEED * 1.8);
                    } else {
                        speed = DEFAULT_SPEED;
                    }
                    if (keyH.spacePressed) {
                        attacking = true;
                        gp.playSoundEffect(4);
                    }


                    // The update() is called 60(FPS) times a second
                    spriteCounter++; // every frame this is increased by 1
                    spriteCounter = changeSpriteCounter(spriteCounter, gp);
                }
                if(keyH.interactPressed){
                    interacting = true;
                }
                keyH.spacePressed = false;

            }
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            if(life <= 0){
                gp.gameState = GamePanel.GameState.GAME_OVER;
                gp.ui.command =  UI.Command.RETRY;
            }
        }
    }

    private boolean checkEscaped() {
        if(this.x >= GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/4){
            gp.gameState = GamePanel.GameState.ESCAPED;
            return true;
        }
        return false;
    }

    private void checkCollision(String direction) {
        // Check tile collision
        gp.collisionChecker.checkTile(this);

        // Check object collision, boolean parameter is true, if entity is player
        int objIndex = gp.collisionChecker.checkObject(this, true);


        // Check npc collision
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcPolice);
        if(!keyH.interactPressed) {
            if (direction.equals("up") && !collisionOnUp) { // If collision upwards false, player can move up
                y -= speed;
            }
            if (direction.equals("down") && !collisionOnDown) { // If collision downwards false, player can move down
                y += speed;
            }
            if (direction.equals("left") && !collisionOnLeft) { // If collision to the left is false, player can move left
                x -= speed;
            }
            if (direction.equals("right") && !collisionOnRight) { // If collision to the right is false, player can move right
                x += speed;
            }
        }
    }

    private int changeSpriteCounter(int spriteCounter, GamePanel gp) {
        if (keyH.shiftPressed ? spriteCounter > 10 : spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            } else if (spriteNum == 2) {
                spriteNum = 1;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            }
            return 0; // reset after sprite has been changes
        }
        // Player image is changed every (10) frames.

        return spriteCounter;
    }

    private void interacting() {
        interactCounter++;

        if (interactCounter == 1) {
            // Check object collision, boolean parameter is true, if entity is player
            int objIndex = gp.collisionChecker.checkObject(this, true);

            // Interaction with object if there is one at the same tile

            interactWithObject(objIndex);
        }
        if(interactCounter>20){
            interacting = false;
            interactCounter = 0;
        }
    }

    public void damageNPC(int i) {
        if(i != -1){
            if(!gp.npcPolice[i].invincible){
                if(gp.npcPolice[i].life > 1){
                    knockBack(gp.npcPolice[i],this);
                }
                gp.npcPolice[i].onPath = true;
                gp.npcPolice[i].life -= 1;
                gp.npcPolice[i].invincible = true;
                gp.playSoundEffect(5);
                if(gp.npcPolice[i].life <= 0){
                    gp.npcPolice[i].status = Status.DYING;
                }
            }
        }
    }

    // Parameter is the i. number of object in the Objects array(ObjectArray)
    public void interactWithObject(int i) {
        if (i != (-1) && gp.objectArray[i] != null) { // if remains -1(base), did not touch any object
            switch (gp.objectArray[i].type) {
                case BlueKeycard, RedKeycard:
                    if(currentItem == null){
                        // If Player has touched the keycard remove it from screen,
                        gp.playSoundEffect(2); // pickup sound
                        gp.objectArray[i].x = -1 * GamePanel.TILE_SIZE;
                        currentItem = gp.objectArray[i];
                    }
                    else{
                        swapObjects(currentItem,gp.objectArray[i]);
                        gp.playSoundEffect(2); // pickup sound
                        currentItem = gp.objectArray[i];
                    }
                    break;

                case BlueDoor:
                    // If Player has touched the door and has any key(>0)
                    // then remove the door, and use one key(-1 key)
                    if (currentItem!=null && currentItem.type == Type.BlueKeycard) {
                        if (gp.objectArray[i].objectSpriteNum == 0) {
                            gp.playSoundEffect(3);
                            gp.objectArray[i].objectSpriteNum = 1;
                        }
                        if (gp.objectArray[i].objectSpriteNum == 1) {
                            for(int deleteIndex =0;i<gp.objectArray.length; deleteIndex++){
                                if(gp.objectArray[deleteIndex] == this.currentItem){
                                    gp.objectArray[deleteIndex] = null;
                                    currentItem = null;
                                    break;
                                }
                            }
                            gp.objectArray[i] = null;
                        }
                    }
                    else{
                        gp.playSoundEffect(7); // denied
                    }
                    break;
                case RedDoor:
                    // If Player has touched the door and has any key(>0)
                    // then remove the door, and use one key(-1 key)
                    if (currentItem!=null && currentItem.type == Type.RedKeycard) {
                        if (gp.objectArray[i].objectSpriteNum == 0) {
                            gp.playSoundEffect(3);
                            gp.objectArray[i].objectSpriteNum = 1;
                        }
                        if (gp.objectArray[i].objectSpriteNum == 1) {
                            currentItem = null;
                            gp.objectArray[i] = null;
                            gp.saveLoad.save();
                        }
                    }
                    else{
                        gp.playSoundEffect(7); // denied
                    }
                    break;

                default:
                    break;
            }
        }
        interacting = false;
    }

    public void swapObjects(Entity currentItem, Entity entity) {

        int tempX = currentItem.x;
        currentItem.x = ((this.x+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
        entity.x = tempX;

        int tempY = currentItem.y;
        currentItem.y = ((this.y+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
        entity.y = tempY;
    }

    public void setDefaultPositions(){
        x = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE / 2;
        y = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.TILE_SIZE / 2;
        direction = "down";
    }
    public void setDefaultHealthAndItem(){
        this.life = maxLife;
        this.currentItem = null;
    }
}
