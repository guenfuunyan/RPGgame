package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPCLinhHon extends Entity {
	
    public NPCLinhHon(GamePanel gp) {
        super(gp);

        direction = "left";
        speed = 0;
        id = 0;
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
        down1 = setup("/npc/linhhon1", gp.tileSize, gp.tileSize);
        up1 = setup("/npc/linhhon1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/linhhon2", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/linhhon2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/linhhon1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/linhhon2", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {
        dialogues[0] = "Chủ nhân của thanh kiếm này ... không còn nữa";
        dialogues[1] = "Ta là mảnh ý chí còn vương vấn ... thế gian này, mang sự \nu uất ... của kẻ hi sinh";
        dialogues[2] = "Vòng lặp ... vẫn tiếp tục ... nhưng linh lực của \nta dần hao kiệt";
        dialogues[3] = "Không ... thời gian nữa, chàng trai, mau ... nhặt thanh \nkiếm của ta và ... chấm dứt vòng lặp này";
        dialogues[4] = "Kẻ thù ... hướng ... Đông, ... diệt h..";
    }


}
