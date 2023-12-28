package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** The main game panel, which is a JPanel, where the game plays and draws images,texts
 */
public class GamePanel extends JPanel implements Runnable {
    //GAME SCREEN SETTINGS
    /** Original size of a tile (pixels)
     */
    static final int ORIGINAL_TILE_SIZE = 48; //16x16 tile
    /** Scale for scaling images,tiles up
     */
    static final int SCALE = 1;
    /** Current size of tile (pixels)
     */
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48 tile
    /** Maximum number of columns in the game screen (tiles)
     */
    public static final int MAX_SCREEN_COL = 16;
    /** Maximum number of rows in the game screen (tiles)
     */
    public static final int MAX_SCREEN_ROW = 12;
    /** The width of the screen (pixels)
     */
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //768 pixels
    /** The height of the screen (pixels)
     */
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; //576 pixels

    /** The number of fps (Frames Per Seconds)
     */
    int fps = 60;

    // SYSTEM instantiations
    /** The TileManager that holds methods for tiles
     */
    public TileManager tileM = new TileManager(this); // Tile methods
    /** The KeyHandler that handles key(input) made events
     */
    public KeyHandler keyH = new KeyHandler(this); // Controls keyboard events
    /** The AssetSetter that places assets(objects,npc-s) to the map
     */
    AssetSetter assetSetter = new AssetSetter(this); // Places Objects to right place on screen
    /** The Sound that manages sound effects
     */
    Sound soundEffect = new Sound(); // Sound-effects
    /** The CollisionChecker that checks different type of collisions
     */
    public CollisionChecker collisionChecker= new CollisionChecker(this); // Player collision with tile and object
    /** The UI that displays the ui (inventory, health, currentItem) on screen
     */
    public UI ui = new UI(this);
    /** The SaveLoad saves game data to file, and loads back it, creating saving possible
     */
    public SaveLoad saveLoad = new SaveLoad(this);
    /** The main and only thread that helps displaying
     */
    Thread gameThread;  // Main-game thread, provides sleep for fps control

    // ENTITY AND OBJECT
    /** The maximum numbers of objects that can hold the objects array
     */
    private static final int MAX_OBJECTS = 35; // maximum number of objects that can be stored
    /** The array that holds every object
     */
    public Entity[] objectArray = new Entity[MAX_OBJECTS]; // Stores every Object
    /** The player that the user controls
     */
    public Player player = new Player(this, keyH); // Player Entity
    /** The array that holds every NPC entity (blue police,red police, fixing police, chef)
     */
    public Entity[] npcPolice = new Entity[10]; // NPC Entity
    /** This list is used to create an order of entites, which order tells which entity should be drawn first/on the other
     */
    ArrayList<Entity> entityList = new ArrayList<>();

    // PATH FINDING
    /** The pathfinder that creates path finding for npc possible, and entity can follow player
     */
    public PathFinder pathFinder = new PathFinder(this);

    // GAME STATE

    /** States that the game can be in, for example the game can be paused(PAUSE_STATE)
     */
    public enum GameState{
        PLAY_STATE,
        PAUSE_STATE,
        TITLE_STATE,
        GAME_OVER,
        ESCAPED,
        SAVING
    }
    public GameState gameState;

    /** Sets the game in the beginning state, and place entities and objects into place
     */
    public void setupGame(){
        gameState = GameState.TITLE_STATE;
        ui.command = UI.Command.NEW_GAME;
        assetSetter.setObject(); // Places every Object to right place on screen
        assetSetter.setNPC(); // Places every npc to right place on screen
    }

    /** Constructor that sets game window's attributes
     * @throws FileNotFoundException
     */
    public GamePanel() throws FileNotFoundException {
        //Set game screen up with attributes
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // If set true, all the drawing from this component will be done in an offscreen painting buffer.
        // Improves game's rendering performance.
        this.setDoubleBuffered(true);

        // Handling key events(Pressed, Released)
        this.addKeyListener(keyH);
        this.setFocusable(true); // With this GamePanel can be "focused" to receive key input.
    }

    /** Starts the main and only game thread, that controls fps
     */
    public void startGameThread(){ // Main thread for fps control
        gameThread = new Thread(this);
        gameThread.start();
    }

    /** The main thread being handled, fps being calculated here
     */
    @Override
    public void run(){
        // THE MAIN GAME LOOP

        // For precise calculation:
        // 1000000000 (one billion nanoseconds) = 1 second
        // 1000 (one thousand milliseconds) = 1 second
        double drawInternal = (double) 1000000000 / fps; //  ~0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInternal; // now + drawInternal

        while(gameThread != null){
            //UPDATE: update information, such as character positions
            update();
            //DRAW: draw/paint the screen with updated information
            repaint();

            try {
                // How much time left after the "update()" and "repaint()" and before the next drawing starts
                double remainingTime = nextDrawTime - System.nanoTime();

                // Divide by one million to convert it to milliseconds.
                remainingTime = remainingTime/1000000;

                // if "update()" and "repaint()" takes more time and no remaining time left,
                // then there should be no time to be slept
                if(remainingTime <0){
                    remainingTime = 0;
                }
                // It should sleep the remaining time until the next draw happens
                Thread.sleep((long) remainingTime);

                // The next draw time will be around ~0.0166 seconds later
                nextDrawTime += drawInternal;

            } catch (InterruptedException e) {
                // needed for the Thread.sleep()
                Thread.currentThread().interrupt(); // Thread got interrupted (sonar suggestion)
            }
        }
    }

    /** After each frame new calculations are being made before drawing to screen
     */
    public void update(){
        if(gameState == GameState.PLAY_STATE){
            // Player
            player.update();
            // NPC
            for(int i = 0;i<npcPolice.length;i++){
                if(npcPolice[i] != null){
                    if(npcPolice[i].status == Entity.Status.ALIVE){
                        npcPolice[i].update();
                    }
                    if(npcPolice[i].status == Entity.Status.DEAD){
                        if(npcPolice[i] != null){
                            npcPolice[i].currentItem.x = ((npcPolice[i].x+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
                            npcPolice[i].currentItem.y = ((npcPolice[i].y+GamePanel.TILE_SIZE/2) / GamePanel.TILE_SIZE) * GamePanel.TILE_SIZE;
                            npcPolice[i] = null;
                        }
                    }
                }
            }
        }
    } // updates the player's position, direction, animation sprite etc.

    @Override
    /** Draws each component to screen
     */
    public void paintComponent(Graphics g){
        // Draws every component
        super.paintComponent(g); // The parent class means JPanel here
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if(gameState == GameState.TITLE_STATE){
            ui.draw(g2);
        }

        //OTHERS
        else{
            // Draw tiles
            tileM.draw(g2);

            // Add entities to the list
            entityList.add(player);

            for(int i = 0; i<npcPolice.length;i++){
                if(npcPolice[i] != null){
                    entityList.add(npcPolice[i]);
                }
            }

            // don't add keycards first, so they are draw before every entity
            for(int i = 0; i<objectArray.length; i++){
                if(objectArray[i] != null){
                    if(objectArray[i].type != Entity.Type.BlueKeycard && objectArray[i].type != Entity.Type.RedKeycard){
                        entityList.add(objectArray[i]);
                    }
                }
            }

            // Sort entities in an order to display them
            Collections.sort(entityList, Comparator.comparingInt(e -> e.y));

            // add keycards later, so they are draw before every entity
            for(int i = 0; i<objectArray.length; i++){
                if(objectArray[i] != null){
                    if(objectArray[i].type == Entity.Type.BlueKeycard || objectArray[i].type == Entity.Type.RedKeycard){
                        entityList.add(0,objectArray[i]);
                    }
                }
            }

            // Draw entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            // Empty entityList
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using.
    }

    /** Playing a sound-effect of a given index in the sounds array
     * @param i - the given index of the sounds array
     */
    public void playSoundEffect(int i){ // plays i. number of sound effect in the array of sounds
        soundEffect.setFile(i);
        soundEffect.play();
    }

    /** Loads back from save, and retry from latest save, if there is a save
     */
    public void retry(){
        player.setDefaultValues();
        player.setDefaultHealthAndItem();
        assetSetter.setObject();
        assetSetter.setNPC();
        File f = new File("save.dat");
        if(f.exists()){
            this.saveLoad.load();
        }

        gameState = GameState.PLAY_STATE;
    }

    /** Restart the whole game from the beginning
     */
    public void restart(){
        player.setDefaultValues();
        player.setDefaultHealthAndItem();
        assetSetter.setObject();
        assetSetter.setNPC();
    }
}
