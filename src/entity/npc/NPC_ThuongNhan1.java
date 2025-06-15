package entity.npc;

import java.awt.Rectangle;

import entity.base.Entity;
import main.GamePanel;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.WP_Duanhkiem;
import object.WP_QuyKiem;

public class NPC_ThuongNhan1 extends Entity {
	
	

    public NPC_ThuongNhan1(GamePanel gp) {
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
        up1 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchant2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchant2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchant2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchant2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Tôi là người bán dong, vừa hay có mấy món đồ này cho \ncậu đây";
        
    }

    public void setItems() {
        inventory.add(new WP_Duanhkiem(gp));
        inventory.add(new WP_QuyKiem(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
