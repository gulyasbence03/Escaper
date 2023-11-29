package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/** Represents an Entity that can be NPC or Player
 */
public class Entity {
    /** The main game panel
     */
    GamePanel gp;
    /** The coordinates of the entity on the screen
     */
    public int x,y;
    /** The current speed of the entity
     */
    public int speed;
    /** The default speed of the entity, that can not be changed later
     */
    public static final int DEFAULT_SPEED = 2;

    /** Maximum life of the entity
     */
    public int maxLife;
    /** Current number of hearts/hp an entity has
     */
    public int life;
    /** The direction the entity is currently faces / or idle
     */
    public String direction = "down";

    /** The players current item, which is an Entity object
     */
    public Entity currentItem;

    /** Describes the current status of the entity
     */
    public enum Status{
        ALIVE,
        DYING,
        DEAD
    }
    public Status status;

    /** Hitbox and collision area of an entity
     */
    public Rectangle solidArea = new Rectangle(0,0,48,48); // hitbox Rectangle for entity
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    /** Default solid area (hitbox /collisonbox) to set back to after checking a collision
     */
    public  int solidAreaDefaultX, solidAreaDefaultY;

    /** Collision for top of the entity
     */
    public boolean collisionOnUp = false;
    /** Collision for bottom of the entity
     */
    public boolean collisionOnDown = false;
    /** Collision for left side of the entity
     */
    public boolean collisionOnLeft = false;
    /** Collision for right side of the entity
     */
    public boolean collisionOnRight = false;

    /** Describes the type of the entity
     */
    public enum Type{
        PLAYER,
        POLICE,
        BlueKeycard,
        RedKeycard,
        BlueDoor,
        RedDoor,
        Cell,
        Heart,
        Toilet,
        Bed,
        Monitor,
        Box,
        Table
    }
    public Type type;

    /** Attacking status
     */
    boolean attacking = false;
    /** Interacting status
     */
    boolean interacting = false;
    /** Invincible status
     */
    public boolean invincible = false;
    /** Being knockbacked status
     */
    public boolean knockBack = false;
    /** Which entity attacked the other
     */
    public Entity attacker;
    /** The direction that the entity is being knocked back
     */
    public String knockBackDirection;

    /** If object is solid or not
     */
    public boolean isSolidObject; // means Object is solid, can not go through
    /** If npc is being on a path ( following/agros player)
     */
    public boolean onPath = false;

    /**
     * Walking and idle images of entity(npc/player)
     */
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    /** Attacking images of entity (npc/player)
     */
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    /** The current image of the entity based on its direction or activity
     */
    public BufferedImage image;
    /** Image for displaying current number of Health the player has in number of Hearts.
     */
    public BufferedImage imageHeart;
    /** Image for displaying current number of Health the player has in number of Hearts.
     */
    public BufferedImage imageBlankHeart;

    /** Current state of sprite (every action has 2 image) change it by time
     */
    public int spriteNum = 1; // this is the current state of an animation
    /**  Counter for walking animation
     */
    public int spriteCounter = 0;
    /**  Counter for attacking animation
     */
    public int attackSpriteCounter = 0;
    /**  Counter for interacting animation
     */
    public int interactCounter = 0;
    /**  Counter for object sound or interaction
     */
    public int objectSpriteNum = 0;
    /**  Counter for invincibility
     */
    public int invincibleCounter = 0;
    /**  Counter for attacking delay
     */
    int attackDelayCounter = 0;
    /**  Counter for dying animation
     */
    int dyingCounter = 0;
    /**  Counter for HP bar animation
     */
    int hpBarCounter = 0;
    /**  Counter for knock back event
     */
    int knockBackCounter = 0;

    /**  Counter for npc action
     */
    public int actionLockCounter = 0;

    /** If HP bar is being displayed on top of npc or not
     */
    boolean hpBarON = false;

    /** Gets distance from target (X - horizontal)
     * @param target - the target of the entity (player)
     * @return X coordinate
     */
    public int getXdistance(Entity target){
        return Math.abs(this.x - target.x);
    }
    /** Gets distance from target (Y - vertical)
     * @param target - the target of the entity (player)
     * @return Y coordinate
     */
    public int getYdistance(Entity target){
        return Math.abs(this.y - target.y);
    }

    /** Constructor of Entity, sets game panel, and sets status to alive as default
     * @param gp - the main panel of the game
     */
    public Entity(GamePanel gp){
        this.gp = gp;
        status = Status.ALIVE;
    }

    /** Each npc can overwrite this function, that determines their action
     */
    public void setAction(){
    }

    /** Each frame this update function runs
     * Checks collision
     * Changes coordinates
     * Knock back, animation, invincibility, attacking
     */
    public void update(){
        // If entity is being knock backed
        if(knockBack){
            checkCollision();

            // if collided with anything stop the knock back movement
            if(collisionOnUp || collisionOnDown || collisionOnLeft || collisionOnRight){
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
            else{ // if not collided move back to the knockBackDirection
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
            }
            // Length of knock back
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = DEFAULT_SPEED;
            }
        }
        else if(attacking){ // if npc is being in attacking status
            attacking();
        }
        else {
            // if neither of these do the default action of the npc
            setAction();
            checkCollision();

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
            // Length of invincible effect
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    /** Searching algorithm to follow player after being attacked.
     * @param goalCol - the column of goalNode (player's current Node)
     * @param goalRow - the row of goalNode (player's current Node)
     */
    public void searchPath(int goalCol, int goalRow){
        int startCol = (x + solidArea.x) / GamePanel.TILE_SIZE;
        int startRow = (y + solidArea.y) / GamePanel.TILE_SIZE;

        // Set up all nodes, because meanwhile the solid tiles could have changed
        gp.pathFinder.setNodes(startCol,startRow,goalCol,goalRow);

        // if goal has not been reached
        if(gp.pathFinder.search()){
            int nextX = gp.pathFinder.pathList.get(0).col * GamePanel.TILE_SIZE;
            int nextY = gp.pathFinder.pathList.get(0).row * GamePanel.TILE_SIZE;

            // Entity's solidArea position
            int enLeftX = x + solidArea.x;
            int enRightX = x + solidArea.x + solidArea.width;
            int enTopY = y + solidArea.y;
            int enBottomY = y + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + GamePanel.TILE_SIZE){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + GamePanel.TILE_SIZE){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + GamePanel.TILE_SIZE){
                // Left or Right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOnUp){ // up is blocked go to the left
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOnUp){ // up is blocked go to the right
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOnDown){ // down is blocked go to the left
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOnDown){ // down is blocked go to the right
                    direction = "right";
                }
            }
        }
    }

    /** Reset collisions and then check current collision values for each side
     */
    public void checkCollision() {
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
        if(type != Type.PLAYER){ // only npc check if contacts with player
            gp.collisionChecker.checkPlayer(this);
        }
    }

    /** Draws every type of entity to the screen
     * @param g2 - using Graphics2D
     */
    public void draw(Graphics2D g2){
        int tempScreenX = x;
        int tempScreenY = y;

        switch (direction){
            case "up":
                if(attacking){ // if attacking, use different image, and based on animation
                    tempScreenY -= GamePanel.TILE_SIZE; // beacuse of the animation has bigger size of image
                    if(spriteNum == 1){ image = attackUp1; }
                    if(spriteNum == 2){ image = attackUp2; }
                }
                else{
                    if(spriteNum == 1){ image = up1; }
                    if(spriteNum == 2){ image = up2; }
                }
                break;
            case "down":
                if(attacking){ // if attacking, use different image, and based on animation
                    if(spriteNum == 1){ image = attackDown1; }
                    if(spriteNum == 2){ image = attackDown2; }
                }
                else{
                    if(spriteNum == 1){ image = down1; }
                    if(spriteNum == 2){ image = down2; }
                }
                break;
            case "left":
                if(attacking){ // if attacking, use different image, and based on animation
                    tempScreenX -= GamePanel.TILE_SIZE; // beacuse of the animation has bigger size of image
                    if(spriteNum == 1){ image = attackLeft1; }
                    if(spriteNum == 2){ image = attackLeft2; }
                }
                else{
                    if(spriteNum == 1){ image = left1; }
                    if(spriteNum == 2){ image = left2; }
                }
                break;
            case "right":
                if(attacking){ // if attacking, use different image, and based on animation
                    if(spriteNum == 1){ image = attackRight1; }
                    if(spriteNum == 2){ image = attackRight2; }
                }
                else{
                    if(spriteNum == 1){ image = right1; }
                    if(spriteNum == 2){ image = right2; }
                }
                break;
            case "idle": // if idle, use different image, and based on animation
                if(spriteNum == 1){ image = idle1; }
                if(spriteNum == 2){ image = idle2; }
                break;
            default:
                break;
        }
        if(type == Type.POLICE && hpBarON){ // display npc health if being attacked and timer is still on

            double oneScale = ((double)GamePanel.TILE_SIZE-9)/maxLife;
            double hpBarValue = oneScale*life;

            g2.setColor(new Color(35,35,35));
            g2.fillRect(x+3,y-12,GamePanel.TILE_SIZE-5,8);
            g2.setColor(new Color(255,0,30));
            g2.fillRect(x+5,y-10, (int) hpBarValue,5);

            // after 500 frames do not display hp bar
            hpBarCounter++;
            if(hpBarCounter>500){
                hpBarCounter = 0;
                hpBarON = false;
            }
        }

        if(invincible){ // means getting hit
            hpBarON = true;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); //animation of getting hit
        }
        if(status == Status.DYING){
            dyingAnimation(g2); // animation of dying
        }
        if(this.image != null){
            g2.drawImage(image,tempScreenX,tempScreenY, image.getWidth(), image.getHeight(),null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /** Entity is attacking (player or npc), damaging HP, knock back, animation and sound
     */
    public void attacking() {
        attackSpriteCounter++;
        if(this.type == Type.POLICE && attackSpriteCounter == 1){ // police is following now player, whistle sound
            gp.playSoundEffect(8);
        }
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

            if(type == Type.POLICE){ // if npc is the entity attack player if they collide
                if(gp.collisionChecker.checkPlayer(this)){
                    damagePlayer(1,this);
                }
            }
            else if(type == Type.PLAYER){ // if player check collision and attack npc
                // Check police collision with updated x,y and solidArea
                int npcIndex = gp.collisionChecker.checkEntity(this,gp.npcPolice);
                gp.player.damageNPC(npcIndex);
            }

            // set back the coordinates and hitbox after checks
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

    /** NPC damages player
     * @param damage - number of damage it deals
     * @param attacker - the attacker npc that dealt damage
     */
    public void damagePlayer(int damage, Entity attacker){
        if(!gp.player.invincible){ // if player is not invincible at the moment, then can be hitted
            if(gp.player.life > 1){
                knockBack(gp.player,attacker); // knocks back player
            }
            gp.player.life -= damage;
            gp.player.invincible = true; // now player is invincible for a time
            gp.playSoundEffect(5); // being hit sound
            if(gp.player.life <= 0){
                gp.player.status = Status.DYING; // player is dying
            }
        }
    }

    /** Entity knocks back another entity
     * @param target - the entity that is being knocked back
     * @param attacker - the entity that hit the other one
     */
    public void knockBack(Entity target, Entity attacker){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += 8;
        target.knockBack = true;
    }

    /** NPC checks if player is in range to deal damage to player
     * @param straight - number of tiles to check in front of player
     * @param horizontal - width of tiles checking in the straight direction
     */
    public void checkAttackOrNot(int straight, int horizontal){
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (this.direction){
            case "up":
                if(gp.player.y < this.y && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }break;
            case "down":
                if(gp.player.y > this.y && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }break;
            case "left":
                if(gp.player.x < this.x && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }break;
            case "right":
                if(gp.player.x > this.x && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }break;
        }
        if(targetInRange){
            if(this.currentItem.type == Type.RedKeycard){ // Red Police (npc) attacks even if not being hit, and sees player.
                onPath = true;
            }
            attackDelayCounter++;
            if(this.currentItem.type == Type.RedKeycard && attackDelayCounter == 30 ){ // if npc is Red Police
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
            }
            else if(attackDelayCounter == 40){ // Other NPCs
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
            }
            if(attackDelayCounter > 60){
                attackDelayCounter = 0;
            }
        }
    }

    /** Dying animation of the entity
     * @param g2 - using Graphics2D
     */
    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        // flashing effect on entity, to show it is dying
        if(dyingCounter<=10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(dyingCounter<=20 && dyingCounter>10){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter<=30 && dyingCounter>20){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(dyingCounter<=40 && dyingCounter>30){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        if(dyingCounter>40){
            gp.playSoundEffect(6); // and play the last sound of the entity, meaning it died
            status = Status.DEAD; // now entity is not dying, it is dead
            dyingCounter=0;
        }
    }

    /** Setting up images from the "res" directory
     * @param imageName - name of the image file without the ".png"
     * @param width - width of the image
     * @param height - height of the image
     * @return the loaded image
     */
    public BufferedImage setup(String imageName, int width, int height){
        // Read images into variables from resource directory
        // Object.requireNonNull() to ensure that it is not null
        // 2 sprites(image) for every direction to be able to animate movement
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imageName + ".png")));
            image = uTool.scaleImage(image,width,height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
