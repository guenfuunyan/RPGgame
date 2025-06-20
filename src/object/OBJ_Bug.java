package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bug extends Entity {

    public OBJ_Bug(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_sword;
        name = "Bọ";
        down1 = setup("/objects/bug", gp.tileSize, gp.tileSize);
        key = 1;
        attackValue = 0;
        critPercent = 0;
        attackArea.width = 50;
        attackArea.height = 50;
        range = 50;
        description = "[" + name + "]\nMột con bọ được cho là biểu tượng của may mắn\n";	
        price = 0;
    }
    public void use(Entity entity) {
    	gp.gameState = gp.dialogueState;    	
    }

}
