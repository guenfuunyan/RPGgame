package entity;

import java.awt.Rectangle;

import main.GamePanel;


public class NPC_bloodthirsty extends Entity {
	

    public NPC_bloodthirsty(GamePanel gp) {
        super(gp);

        direction = "left";
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
    }

    public void getImage() {
        up1 = setup("/npc/janne1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/janne2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/janne1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/janne2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/janne1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/janne2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/janne1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/janne2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Nếu một ngày anh thấy thế giới này thật khắc nghiệt\n"
        		+"em có thể cho anh một cái ôm";
    }

   
    public void speak() {
        super.speak();
        gp.gameState = gp.ranniState;
        gp.ui.npc = this;
    }
}
