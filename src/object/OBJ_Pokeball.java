package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pokeball extends Entity {
	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
    public OBJ_Pokeball(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        
        type = type_obstacle;
        name = "Chest";
        image = setup("/objects/pokeball", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/pokeball-open", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
        
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact() {
    	gp.gameState = gp.dialogueState;
    	if(opened == false) {
    		gp.playSE(3);
    		
    		StringBuilder sb = new StringBuilder();
    		sb.append("Bạn đã nhận được " + loot.name +"!");
    		if ( gp.player.canObtainItem(loot) == false) {
    			sb.append("Túi đồ của bạn đã đầy, hãy bán bớt đồ trước khi quay lại");
    		}
    		else {
    			down1 = image2;
    			opened = true;
    		}
    		gp.ui.currentDialogue = sb.toString();
    	}
    	else {
    		gp.ui.currentDialogue = "Chúc may mắn lần sau";
    	}
    }
}
