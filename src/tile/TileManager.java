package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/** Manages tiles and has the methods for tiles.
 */
public class TileManager {
    /** The main game panel
     */
    GamePanel gp;
    /** Stores tiles
     */
    public Tile[] tile;
    /** Stores the type of tiles in the map at position
     */
    public int[][] mapTileNum;
    /** Maximum number of tile types, can be less but not more of this
     */
    private final Integer MAX_TILE_TYPES = 10;

    /** Constructor that creates the tile type holder array, get images of tiles, and map layout
     * @param gp
     * @throws FileNotFoundException
     */
    public TileManager(GamePanel gp) throws FileNotFoundException {
        this.gp = gp;
        tile = new Tile[MAX_TILE_TYPES]; // array of type of tiles exists
        mapTileNum = new int[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW]; // 2D array of all tiles(it's type) in current map
        getTileImage(); // loads in all the images of tile types
        loadMap("res/maps/map01.txt"); // Loads first map in
    }

    /** Set images of tiles using setup() function
     */
    public void getTileImage(){
            // Reading in tile images from res directory
            //STONE WALL
            setup(0,"stone_front",true);
            setup(1,"stone_front_t",true);
            setup(2,"stone_middle",true);
            setup(3,"stone_top_left",true);
            setup(4,"stone_top_right",true);
            setup(5,"stone_middle_right",true);
            setup(6,"stone_top_rightcorner",true);
            //GRASS FLOOR
            setup(9,"grass",false);
            // CONCRETE FLOOR
            setup(8,"concrete",false);
    }

    /** Loads images for tiles from "res" dictionary
     * @param index - which tile in tiles matrix
     * @param imagePath - the name of the image without ".png"
     * @param isSolidTile - whether the tile is solid
     */
    public void setup(int index, String imagePath,boolean isSolidTile){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/" + imagePath + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image,GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
            tile[index].isSolidTile = isSolidTile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Loads the map layout from the map txt
     * @param filePath - the path to the map file
     * @throws FileNotFoundException - if map file is not found
     */
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

    /** Drawing all tiles to the screen
     * @param g2
     */
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
