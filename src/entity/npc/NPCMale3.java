package entity.npc;

import java.awt.Rectangle;

import main.GamePanel;
import entity.object.OBJ_Axe;

public class NPCMale3 extends NPC {
    public NPCMale3(GamePanel gp) {
        super(gp);
        
        direction = "up";
        speed = 0;
        id = 0;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 36;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();

    }

    public void getImage() {
        
        up1 = setup("/npc/Male3Up1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/Male3Up1", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/Male3Down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/Male3Down1", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/Male3Left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/Male3Left1", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/Male3Right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/Male3Right1", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Đường này đã bị cây chắn ngang đường, tôi biết có\n"
        		+ "người bán rìu, đó là ... tôi. Bạn muốn mua chứ? He he. ";
    }
    
    public void setItems() {
        inventory.add(new OBJ_Axe(gp));
    }
    
    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
    
}
