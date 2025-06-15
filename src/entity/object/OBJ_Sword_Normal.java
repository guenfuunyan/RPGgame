package entity.object;

import main.GamePanel;

public class OBJ_Sword_Normal extends Object {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Kiếm Gỗ";
        down1 = setup("/objects/kiemgo", gp.tileSize, gp.tileSize);
        attackValue = 1;
        critPercent = 0;
        attackArea.width = 30;
        attackArea.height = 30;
        range = 30;
        description = "[" + name + "]\nMột cây kiếm gỗ dùng để thi đấu trong bộ môn\nkiếm đạo,"
        		+ " thô kệch và gần như sắp hết tuổi đời\ncủa chính nó.";
        price = 35;
        knockBackPower = 4;
    }

}
