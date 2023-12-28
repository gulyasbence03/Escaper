package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/** Image scaling tool
 */
public class UtilityTool {
    /** Scaling images(original) to the right width and height,
     * @param original - original size image
     * @param width - the wanted width of the scaled image
     * @param height - the wanted height of the scaled image
     * @return scaled image
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width,height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();

        return scaledImage;
    }
}
