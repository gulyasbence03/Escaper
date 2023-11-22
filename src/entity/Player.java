package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public int hasKey = 0; // Number of Key Objects Player has

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        name = "player";
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
        // These are the starting attributes, that the player gets
        x = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE / 2;
        y = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.TILE_SIZE / 2;
        speed = DEFAULT_SPEED;

        direction = "down";

        // Player status
        maxLife = 3;
        life = maxLife;
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

        // Check tile collision, and reset at beginning to check every frame
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;

        if (attacking) {
            attacking();
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {    // If any movement key is being pressed
            // Top left corner of the screen is (0,0)
            // X value increases to the right
            // Y value increases as we go down

            if(!attacking){
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
            keyH.spacePressed = false;
        }
    }

    private void checkCollision(String direction) {
        // Check tile collision
        gp.collisionChecker.checkTile(this);

        // Check object collision, boolean parameter is true, if entity is player
        int objIndex = gp.collisionChecker.checkObject(this, true);

        // Interaction with object if there is one at the same tile
        interactWithObject(objIndex);

        // Check npc collision
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npcPolice);

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

    public void attacking() {
        attackSpriteCounter++;
        if (attackSpriteCounter <= 5) {
            spriteNum = 1;
        }
        if (attackSpriteCounter > 5 && attackSpriteCounter <= 20) {
            spriteNum = 2;

            // saving the current x,y for attackArea
            int savedX = x;
            int savedY = y;
            int savedWidth = solidArea.width;
            int savedHeight = solidArea.height;

            // adjust player's x,y for the area
            switch (direction){
                case "up": y -= attackArea.height; break;
                case "down": y += attackArea.height; break;
                case "left": x -= attackArea.width; break;
                case "right": x += attackArea.width; break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // Check police collision with updated x,y and solidArea
            int npcIndex = gp.collisionChecker.checkEntity(this,gp.npcPolice);
            damageNPC(npcIndex);

            x = savedX;
            y = savedY;
            solidArea.width = savedWidth;
            solidArea.height = savedHeight;
        }
        if(attackSpriteCounter>25){
            attacking = false;
            attackSpriteCounter = 0;
        }
    }

    private void damageNPC(int i) {
        if(i != -1){
            if(!gp.npcPolice[i].invincible){
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
            switch (gp.objectArray[i].name) {
                case "Key":
                    // If Player has touched the key remove it from screen,
                    // add one to the number of keys the player has
                    hasKey++;
                    gp.playSoundEffect(2); // keys_pickup sound
                    gp.objectArray[i] = null;
                    break;
                case "Door":
                    // If Player has touched the door and has any key(>0)
                    // then remove the door, and use one key(-1 key)
                    if (hasKey > 0) {
                        if (gp.objectArray[i].objectSpriteNum == 0) {
                            gp.playSoundEffect(3);
                            gp.objectArray[i].objectSpriteNum = 1;
                        }
                        if (gp.objectArray[i].objectSpriteNum == 1) {
                            hasKey--;
                            gp.objectArray[i] = null;
                        }

                    }
                    break;

                default:
                    break;
            }
        }
    }
}
