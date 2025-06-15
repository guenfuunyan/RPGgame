package entity.npc;

import java.awt.Rectangle;

import entity.base.Entity;
import main.GamePanel;
import object.OBJ_HealthPotion;
import object.OBJ_ManaPotion;

public class NPC_ThuongNhan2 extends Entity {
	
	

    public NPC_ThuongNhan2(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        up1 = setup("/npc/yta1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/yta2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/yta1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/yta2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/yta1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/yta2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/yta1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/yta2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Ở đây em chuyên bán thuốc\n";
    }

    public void setItems() {
        inventory.add(new OBJ_HealthPotion(gp));
        inventory.add(new OBJ_ManaPotion(gp));
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
