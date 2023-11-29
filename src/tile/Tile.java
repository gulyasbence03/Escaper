package tile;

import java.awt.image.BufferedImage;

/** Represents a Tile, that has an image and a solidness attribute
 */
public class Tile {
    public BufferedImage image; // Image of tile(depending on it's type)
    public boolean isSolidTile; // means that this tile is solid, can not go through
}
