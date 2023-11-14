package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjKey extends SuperObject{
    public ObjKey(){
        name = "Key";
        try{
            // Reading in Key image from res directory
            images[0] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/key.png")));
            uTool.scaleImage(images[0], GamePanel.TILE_SIZE,GamePanel.TILE_SIZE);
            animationON = false;
        } catch (IOException e) {
            throw new RuntimeException(e); // reading in requires
        }
    }
}
