package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(){
        name = "Door";
        try{
            // Reading in Door image from res directory
            image = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door.png"))));
            }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
