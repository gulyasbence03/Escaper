import ai.Node;
import ai.PathFinder;
import data.DataStorage;
import data.SaveLoad;
import entity.Entity;
import entity.NPC_Police;
import entity.Player;
import main.AssetSetter;
import main.CollisionChecker;
import main.GamePanel;
import main.KeyHandler;
import object.ObjBlueKeycard;
import object.ObjRedKeycard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class MyTest {
    GamePanel gp;
    KeyHandler keyH;
    Player player;

    @Before
    public void setup() throws FileNotFoundException {
        gp = new GamePanel();
        keyH = new KeyHandler(gp);
        player = new Player(gp,keyH);
    }

    @Test
    public void defaultValues() {
        Player player = new Player(gp,keyH);
        int defaultX = player.x;
        int defaultY = player.y;
        player.x = 123;
        player.y = 321;
        player.setDefaultPositions();
        Assert.assertEquals(defaultX,player.x);
        Assert.assertEquals(defaultY,player.y);
    }
    @Test
    public void checkEscapedTest() {
        player.x = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE - GamePanel.TILE_SIZE/4;
        Assert.assertTrue(player.checkEscaped());
    }

    @Test
    public void swapObjectTest(){
        Entity blueKeycard = new ObjBlueKeycard(gp);
        blueKeycard.x = 1;
        Entity redKeycard = new ObjRedKeycard(gp);
        redKeycard.x = GamePanel.TILE_SIZE * 3;
        player.swapObjects(blueKeycard,redKeycard);
        Assert.assertTrue(1!=blueKeycard.x);
    }

    @Test
    public void setDefaultHealthTest(){
        player.life = 0;
        player.setDefaultHealthAndItem();
        Assert.assertEquals(player.maxLife,player.life);
    }

    @Test
    public void attackCounterTest(){
        int counter = player.actionLockCounter;
        player.attacking();
        Assert.assertEquals(counter+1,player.attackSpriteCounter);
    }

    @Test
    public void assetSetterTest(){
        AssetSetter as = new AssetSetter(gp);
        as.setObject();
        int x = 3 * GamePanel.TILE_SIZE;
        Assert.assertEquals(x,gp.objectArray[0].x);
    }

    @Test
    public void setNpcTest(){
        AssetSetter as = new AssetSetter(gp);
        as.setNPC();
        int x = GamePanel.TILE_SIZE*13 - 18;
        Assert.assertEquals(x,gp.npcPolice[0].x);
    }

    @Test
    public void retryTest(){
        gp.gameState = GamePanel.GameState.GAME_OVER;
        gp.retry();
        Assert.assertEquals(GamePanel.GameState.PLAY_STATE,gp.gameState);
    }

    @Test
    public void restartTest(){
        AssetSetter as = new AssetSetter(gp);
        as.setNPC();
        gp.npcPolice[0].x = 300;
        gp.restart();
        Assert.assertFalse(gp.npcPolice[0].x == 300);
    }

    @Test
    public void loadMap() throws FileNotFoundException {
        gp.tileM.loadMap("res/maps/map01.txt");
        Assert.assertEquals(9,gp.tileM.mapTileNum[0][0]);
    }
}