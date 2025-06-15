package object;

import entity.Entity;
import main.GamePanel;

public class WP_KiemKhoiNguyen extends Entity {

    public WP_KiemKhoiNguyen(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Kiếm Trường Sinh";
        down1 = setup("/objects/KiemKhoiNguyen", gp.tileSize, gp.tileSize);
        amount = 1;
        attackValue = 4;
        healingAmount = 1;
        critPercent = 0;
        attackArea.width = 60;
        attackArea.height = 60;
        range = 60;
        description = "[" + name + "]\nLà sự sống ắt muốn trường sinh.\n"
        		+ "Thanh gươm có khả năng hồi máu sau mỗi đòn\nđánh cho chủ nhân của chúng\n";
        price = 1000;
        knockBackPower = 3;
    }

}
