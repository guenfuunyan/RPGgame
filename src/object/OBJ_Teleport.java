package object;

import entity.base.Entity;
import main.GamePanel;

public class OBJ_Teleport extends Entity {
	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
    public OBJ_Teleport(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = "Teleport";
        image = setup("/objects/teleport", gp.tileSize*5, gp.tileSize*5);
        down1 = image;
        collision = true;
        
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 360;
        solidArea.height = 260;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact() {
    	gp.gameState = gp.transitionState;
    	
    }
}
