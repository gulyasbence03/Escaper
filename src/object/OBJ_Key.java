package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{
    public OBJ_Key(){
        name = "Key";
        try{
            // Reading in Key image from res directory
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/key.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
