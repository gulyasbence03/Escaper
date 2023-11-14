package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public int hasKey = 0; // Number of Key Objects Player has

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues(); // set up coordinates, speed, direction
        getPlayerImage(); // load images to animate and draw character

        // Solid area(hitbox) is the area that is collidable with solid tiles(like walls) and objects
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 24;
        solidArea.width = 32;
        solidArea.height = 24;

        // These are needed, because we change the player's solid area
        // to check if it's capable of moving that way( if there is solid object/tile the next step)
        // Then reset these coordinates back on player
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY= solidArea.y;
    }

    public void setDefaultValues(){
        // These are the starting attributes, that the player gets
        x = GamePanel.SCREEN_WIDTH /2 - GamePanel.TILE_SIZE /2;
        y = GamePanel.SCREEN_HEIGHT /2 - GamePanel.TILE_SIZE /2;
        speed = DEFAULT_SPEED;

        direction = "down";
    }

    public void getPlayerImage(){
            up1 = setup("up1");
            up2 = setup("up2");
            down1 = setup("down1");
            down2 = setup("down2");
            left1 = setup("left1");
            left2 = setup("left2");
            right1 = setup("right1");
            right2 = setup("right2");
    }

    public BufferedImage setup(String imageName){
        // Read images into variables from resource directory
        // Object.requireNonNull() to ensure that it is not null
        // 2 sprites(image) for every direction to be able to animate movement
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png")));
            image = uTool.scaleImage(image,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public void update(){

        // Check tile collision, and reset at beginning to check every frame
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){    // If any movement key is being pressed
            // Top left corner of the screen is (0,0)
            // X value increases to the right
            // Y value increases as we go down

            // Set direction based on key pressed
            if(keyH.upPressed){
                direction = "up";
                checkCollision(direction);
            }
            if(keyH.downPressed){
                direction = "down";
                checkCollision(direction);
            }
            if(keyH.leftPressed){
                direction = "left";
                checkCollision(direction);
            }
            if(keyH.rightPressed){
                direction = "right";
                checkCollision(direction);
            }
            if(keyH.shiftPressed){
                speed = (int) (DEFAULT_SPEED * 1.8);
            }else{
                speed = DEFAULT_SPEED;
            }


            // The update() is called 60(FPS) times a second
            spriteCounter++; // every frame this is increased by 1
            spriteCounter = changeSpriteCounter(spriteCounter,gp);
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

        if(direction.equals("up") && !collisionOnUp){ // If collision upwards false, player can move up
            y -= speed;
        }
        if(direction.equals("down") && !collisionOnDown){ // If collision downwards false, player can move down
            y += speed;
        }
        if(direction.equals("left") && !collisionOnLeft){ // If collision to the left is false, player can move left
            x -= speed;
        }
        if(direction.equals("right") && !collisionOnRight){ // If collision to the right is false, player can move right
            x += speed;
        }
    }

    private int changeSpriteCounter(int spriteCounter, GamePanel gp) {
        if(keyH.shiftPressed?spriteCounter>10:spriteCounter>20){
            if(spriteNum == 1){
                spriteNum = 2;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            }
            else if(spriteNum == 2){
                spriteNum = 1;
                gp.playSoundEffect(1); // on every second step, step sound effect is being played
            }
            return 0; // reset after sprite has been changes
        }
        // Player image is changed every (10) frames.

        return spriteCounter;
    }

    // Parameter is the i. number of object in the Objects array(ObjectArray)
    public void interactWithObject(int i){
        if(i!=(-1) && gp.objectArray[i] != null){ // if remains -1(base), did not touch any object
            switch (gp.objectArray[i].name){
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
                    if(hasKey >0){
                        if(gp.objectArray[i].spriteNum ==0){
                            gp.playSoundEffect(3);
                        }
                        gp.objectArray[i].animationON = true;
                        if(gp.objectArray[i].spriteNum == 1){
                            hasKey--;
                        }

                    }
                    break;

                default:
                    break;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null; // holds the current image of animation

        // Draw the right image for animation, depending on which state the animation is in.
        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                else{
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                else{
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                else{
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                else{
                    image = right2;
                }
                break;
            default:
                break;
        }
        // Draw the image at (x,y), width = tileSize, height = tileSize, and no Image observer = so null
        g2.drawImage(image,x,y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
}
