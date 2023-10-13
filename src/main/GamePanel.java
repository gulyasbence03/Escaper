package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //GAME SCREEN SETTINGS
    final int originalTiteSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTiteSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    int FPS = 60;
    KeyHandler keyH = new KeyHandler(); // Controls keyboard event
    Thread gameThread;  // Main game thread, provides sleep for fps control
    Player player = new Player(this, keyH);
    TileManager tileM = new TileManager(this); // Tile object methods and management
    public CollisionChecker collisionChecker= new CollisionChecker(this); // Player collision with tile and object
    public SuperObject[] obj = new SuperObject[10]; // All type of object array
    AssetSetter assetSetter = new AssetSetter(this); // Places Objects to right place on screen

    public void setupGame(){
        assetSetter.setObject(); // Places Object to right place on screen
    }

    public GamePanel()  {
        //Set game screen up with attributes
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

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
        double drawInternal = (double) 1000000000 /FPS; //  ~0.0166 seconds
        double nextDrawTime = System.nanoTime() + drawInternal;

        while(gameThread != null){
            //UPDATE: update information such as character positions
            update();
            //DRAW: draw the screen with updated information
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
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        // Draws a given component
        super.paintComponent(g); // The parent class means JPanel here

        Graphics2D g2 = (Graphics2D) g;
        // Tile
        tileM.draw(g2);
        // Objects
        for (SuperObject object: obj
             ) {
            if(object != null){
                    object.draw(g2,this);
            }

        }
        // Player
        player.draw(g2);

        g2.dispose(); // Dispose of this graphics context and release any system resources that it is using.
    }
}
