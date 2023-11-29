package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;

import java.awt.*;

/** The playable character in the game
 */
public class Player extends Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** The key(input) handler
     */
    KeyHandler keyH;

    /** The constructor of the player, setting default values
     * @param gp - the main game panel
     * @param keyH - the key(input) handler
     */
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

    /** Setting default position and health/item of player
     */
    public void setDefaultValues() {
        speed = DEFAULT_SPEED;

        // Player status
        setDefaultPositions();
        setDefaultHealthAndItem();
    }

    /** Setting up player's images using setup() function
     */
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
    /** Setting up player's attacking images using setup() function
     */
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

    /** Overriding the main update(), every frame
     * checking collision
     * knock back effect
     * walking animation
     * interacting
     * checking health status
     * invincibility
     * attacking
     */
    public void update() {
        if(checkEscaped()){ // IF player has escaped the prison
            gp.gameState = GamePanel.GameState.ESCAPED;
            gp.ui.command = UI.Command.MENU;
            gp.playSoundEffect(13); // Victory sound effect
        }

        // Check tile collision, and reset at beginning to check every frame
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;

        if(knockBack) { // being knock backed
            checkCollision();

            // hit something while knocked back, so stop knock back
            if (collisionOnUp || collisionOnDown || collisionOnLeft || collisionOnRight) {
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
            else{ // did not collide with anything so knock back
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
                // Walking animation and sound
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

            // Length of knock back
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
        }
        else if (attacking) { // Player is currently attacking
            attacking();
        }
        else if(interacting){ // Player is currently interacting
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
                    if (keyH.shiftPressed && !attacking) { // Sprinting
                        speed = (int) (DEFAULT_SPEED * 1.8);
                    } else {
                        speed = DEFAULT_SPEED;
                    }
                    if (keyH.spacePressed) { // Attacking
                        attacking = true;
                        gp.playSoundEffect(4);
                    }

                    // The update() is called 60(FPS) times a second
                    spriteCounter++; // every frame this is increased by 1
                    spriteCounter = changeSpriteCounter(spriteCounter, gp);
                }
                if(keyH.interactPressed){ // interacting, block movement
                    interacting = true;
                }
                keyH.spacePressed = false;

            }
            if (invincible) { // if player is currently invincible, check for how long
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            // Player dies
            if(life <= 0){
                gp.gameState = GamePanel.GameState.GAME_OVER;
                gp.playSoundEffect(12);
                gp.ui.command =  UI.Command.RETRY;
            }
        }
    }

    /** Check if player has escaped from prison or not, by checking if player has reached a tile
     * @return if player has escaped or not
     */
    private boolean checkEscaped() {
        if(this.x >= GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/4){
            return true;
        }
        return false;
    }

    /** Check if player is colliding with anything
     * @param direction - the player is facing
     */
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

    /** Change sprite based on player is sprinting or not
     * @param spriteCounter - counter for walking animation
     * @param gp - the main game panel
     * @return the current spriteCounter, because it could be set to zero
     */
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

    /** Player is interacting with an object
     */
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

    /** Player is damaging the npc, in the array of npc-s, at a given index
     *  And knock back it, if it has more than 1 health
     * @param i - index of npc in the npc array
     */
    public void damageNPC(int i) {
        if(i != -1){ // if player has hit any npc
            if(!gp.npcPolice[i].invincible){ // npc is not invincible
                if(gp.npcPolice[i].life > 1){ // npc has more than 1 life the knockback
                    knockBack(gp.npcPolice[i],this);
                }
                gp.npcPolice[i].onPath = true; // now npc follows player
                if(gp.npcPolice[i].life == gp.npcPolice[i].maxLife){
                    gp.playSoundEffect(9); // npc whistles
                }
                gp.npcPolice[i].life -= 1;
                gp.npcPolice[i].invincible = true; // npc is now invincible
                gp.playSoundEffect(5); // npc hurt sound effect
                if(gp.npcPolice[i].life <= 0){
                    gp.npcPolice[i].status = Status.DYING; // NPC is dying
                }
            }
        }
    }

    /** Player interacts with an object, so check which object
     * @param i -index of object in the Objects array(ObjectArray)
     */
    public void interactWithObject(int i) {
        if (i != (-1) && gp.objectArray[i] != null) { // if remains -1(base), did not touch any object
            switch (gp.objectArray[i].type) {
                case BlueKeycard, RedKeycard: // Any keycard
                    // Player has no item
                    if(currentItem == null){
                        // If Player has touched the keycard remove it from screen,
                        gp.playSoundEffect(2); // pickup sound
                        gp.objectArray[i].x = -1 * GamePanel.TILE_SIZE;
                        currentItem = gp.objectArray[i];
                    }
                    else{ // player has already a keycard so swap them
                        swapObjects(currentItem,gp.objectArray[i]);
                        gp.playSoundEffect(2); // pickup sound
                        currentItem = gp.objectArray[i];
                    }
                    break;

                case BlueDoor:
                    // If Player has touched a blue door and has blue keycard
                    // then remove the door, and use blue keycard
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
                        gp.playSoundEffect(7); // denied sound effect
                    }
                    break;
                case RedDoor:
                    // If Player has touched the red door and has red keycard
                    // then remove the door, and use the red keycard
                    if (currentItem!=null && currentItem.type == Type.RedKeycard) {
                        if (gp.objectArray[i].objectSpriteNum == 0) {
                            gp.playSoundEffect(3);
                            gp.objectArray[i].objectSpriteNum = 1;
                        }
                        if (gp.objectArray[i].objectSpriteNum == 1) {
                            currentItem = null;
                            gp.objectArray[i] = null;
                        }
                    }
                    else{
                        gp.playSoundEffect(7); // denied sound effect
                    }
                    break;
                case Bed:
                    gp.saveLoad.save();
                    gp.player.x = gp.objectArray[i].x;
                    gp.player.y = GamePanel.TILE_SIZE*2;
                    gp.gameState = GamePanel.GameState.SAVING;
                    gp.playSoundEffect(14);
                    break;

                default:
                    break;
            }
        }
        interacting = false; // interacting is over
    }

    /** Swap the current item of the player with the one the player interacts with
     * @param currentItem - player's current item
     * @param entity - the keycard(object) to swap to
     */
    public void swapObjects(Entity currentItem, Entity entity) {
        // Basic x and y coordinate change
        int tempX = currentItem.x;
        currentItem.x = ((this.x+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
        entity.x = tempX;

        int tempY = currentItem.y;
        currentItem.y = ((this.y+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
        entity.y = tempY;
    }

    /** Set the default starting postion when new game starts, and direction
     */
    public void setDefaultPositions(){
        x = GamePanel.TILE_SIZE * 2;
        y = GamePanel.TILE_SIZE * 3;
        direction = "down";
    }
    public void setDefaultHealthAndItem(){
        this.life = maxLife;
        this.currentItem = null;
    }
}
