package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ObjDoor extends SuperObject{
    public ObjDoor(){
        name = "Door";
        try{
            // Reading in Door image from res directory
            images[0] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim00.png"))));
            images[1] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim01.png"))));
            images[2] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim02.png"))));
            images[3] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim03.png"))));
            images[4] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim04.png"))));
            images[5] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim05.png"))));
            images[6] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim06.png"))));
            images[7] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim07.png"))));
            images[8] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim08.png"))));
            images[9] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim09.png"))));
            images[10] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim10.png"))));
            images[11] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim11.png"))));
            images[12] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim12.png"))));
            images[13] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim13.png"))));
            images[14] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim14.png"))));
            images[15] = ImageIO.read((Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("objects/door/dooranim15.png"))));
            animationLength = 16;
        } catch (IOException e) {
            throw new RuntimeException(e); // reading in requires
        }
        isSolidObject = true;
    }
}
