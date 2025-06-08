package object;

import main.GamePanel;
import main.GameState;

public class OBJ_Chest extends SuperObject{
    
    public OBJ_Chest(GamePanel gp){
        super(gp);
        name = "Chest";
        image = makeSprite("objects/treasureChest.png");
    }
    @Override
    public boolean executeAction() {
       gp.gameState = GameState.YOU_WIN;
       gp.playSoundEffect("chestUnlock",1);
       return true;
    }
}