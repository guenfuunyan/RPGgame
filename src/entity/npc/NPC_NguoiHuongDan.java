package entity.npc;

import java.awt.Rectangle;

import entity.base.Entity;
import main.GamePanel;

public class NPC_NguoiHuongDan extends Entity {
	

    public NPC_NguoiHuongDan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        id = 1;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 32;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/nhanvatvang", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
    	   dialogues[0] = "Kính chào anh Jack, chào mừng đến vùng đất này";
           dialogues[1] = "Thay mặt dân chúng khốn khổ nơi đây, cầu xin người\nhãy giúp đỡ chúng tôi giải cứu khỏi sự xâm lăng\ncủa đám Đom Đóm đột biến";
           dialogues[2] = "Chúng tàn phá khắp nơi, mang đến bao lầm than cho \ndân làng";
           dialogues[3] = "Bên phải kia có một quả bóng pokemon, hãy đến gần nó\nvà "
           				+ "bấm J để tương tác với nó";
           dialogues[4] = "Ngoài ra, bạn có sẵn một kỹ năng trong bộ kỹ năng,\n"
           		+ 			"hãy thử bấm Q và dùng phím F hoặc I để chọn phím\n"
           		+ "thi triển kỹ năng đó";
}}
