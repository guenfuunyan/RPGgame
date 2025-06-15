package entity.npc;

import java.awt.Rectangle;

import main.GamePanel;

public class NPC_ThuongNhanBanBo extends NPC {
	

    public NPC_ThuongNhanBanBo(GamePanel gp) {
        super(gp);

        direction = "left";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 36;
        solidArea.height = 32;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/banbo1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/banbo2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/banbo1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/banbo2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/banbo1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/banbo2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/banbo1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/banbo2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Xứ ta coi bọ là một biểu tượng của may mắn. "
        		+ "Cậu sắp \nsửa có một trận chiến cam go nhỉ, vậy "
        		+ "cậu có thể\nlấy mề đay này, nó ở quả bóng cạnh ngôi nhà phía trên.\n"
        		+ "Ta tặng nó cho cậu, nếu cậu muốn";
    }

}
