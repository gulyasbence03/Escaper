package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable {
    //GAME SCREEN SETTINGS
    static final int ORIGINAL_TILE_SIZE = 48; //16x16 tile
    static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48 tile
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //768 pixels
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; //576 pixels

    // FPS
    int fps = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this); // Tile object methods
    public KeyHandler keyH = new KeyHandler(this); // Controls keyboard events
    AssetSetter assetSetter = new AssetSetter(this); // Places Objects to right place on screen
    Sound music = new Sound(); // Game song
    Sound soundEffect = new Sound(); // Sound-effects
    public CollisionChecker collisionChecker= new CollisionChecker(this); // Player collision with tile and object
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;  // Main-game thread, provides sleep for fps control


    // ENTITY AND OBJECT
    private static final int MAX_OBJECTS = 10; // maximum number of objects that can be stored
    Player player = new Player(this, keyH); // Player Entity
    public Entity[] objectArray = new Entity[MAX_OBJECTS]; // Stores every Object
    public Entity[] npcPolice = new Entity[10]; // NPC Entity
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    enum GameState{
        PLAY_STATE,
        PAUSE_STATE,
        TITLE_STATE
    }
    GameState gameState;

    public void setupGame(){
        gameState = GameState.TITLE_STATE;
        assetSetter.setObject(); // Places every Object to right place on screen
        assetSetter.setNPC(); // Places every npc to right place on screen
    }

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

    public void startGameThread(){ // Main thread for fps control
        gameThread = new Thread(this);
        gameThread.start();
    }

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
                        npcPolice[i] = null;
                    }
                }
            }
        }
    } // updates the player's position, direction, animation sprite etc.

    @Override
    public void paintComponent(Graphics g){
        // Draws a given component
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

            for(int i = 0; i<objectArray.length; i++){
                if(objectArray[i] != null){
                    entityList.add(objectArray[i]);
                }
            }

            // Sort
            Collections.sort(entityList, Comparator.comparingInt(e -> e.y));

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

    public void playMusic(int i){ // plays the given i. number of song in the array of sounds.
        music.setFile(i);
        music.play();
        music.loop(); // the song needs to be looped
    }

    public void playSoundEffect(int i){ // plays i. number of sound effect in the array of sounds
        soundEffect.setFile(i);
        soundEffect.play();
        // not need to be looped
    }
}
