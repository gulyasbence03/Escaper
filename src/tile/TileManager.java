package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    private final Integer MAX_TILE_TYPES = 10; // The maximum number of tile types, can be less but not more of this

    public TileManager(GamePanel gp) throws FileNotFoundException {
        this.gp = gp;
        tile = new Tile[MAX_TILE_TYPES]; // array of type of tiles exists
        mapTileNum = new int[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW]; // 2D array of all tiles(it's type) in current map
        getTileImage(); // loads in all the images of tile types
        loadMap("res/maps/map01.txt"); // Loads first map in
    }

    public void getTileImage(){
        try{
            // Reading in tile images from res directory
            //STONE WALL
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/stone.png")));
            tile[0].isSolidTile = true;
            //GRASS FLOOR
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));
            // CONCRETE FLOOR
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/concrete.png")));

        } catch (IOException e) {
            throw new RuntimeException(e); // needed because of reading in from files
        }
    }

    public void loadMap(String filePath) throws FileNotFoundException {
            // Load map from txt file, that is given in parameter(path)
        try {
            Scanner scanner = new Scanner(new File(filePath)); // reading in map txt
            int col = 0;
            int row = 0;

            // Looping through each column and row
            while (col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW) {
                String line = scanner.nextLine(); // One line of map txt
                String[] numbers = line.split(" "); // These numbers refer to what type of tile they are
                while (col < GamePanel.MAX_SCREEN_COL) {
                    int num = Integer.parseInt(numbers[col]); // One "type of tile" in integer variable
                    mapTileNum[col][row] = num; // Store it in the 2D Matrix
                    col++; // goes to next column
                }
                if (col == GamePanel.MAX_SCREEN_COL) { // if current row has reached end go to the next line
                    col = 0;
                    row++;
                }
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){
        // Draw all tiles
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW) {
            // Current type of tile to draw
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);

            // After drawing move to the next column
            col++;
            x += GamePanel.TILE_SIZE;
            if (col == GamePanel.MAX_SCREEN_COL) {
                // if reached end of current row, reset column and move to next row
                col = 0;
                x = 0;
                row++;
                y += GamePanel.TILE_SIZE;
            }
        }
    }

}
