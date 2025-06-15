package object;

import entity.base.GameObject;
import main.GamePanel;

public class WP_Duanhkiem extends GameObject {

    public WP_Duanhkiem(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Dư ảnh kiếm";
        down1 = setup("/objects/Duanhkiem", gp.tileSize, gp.tileSize);
        amount = 1;
        attackValue = 8;
        critPercent = 80;
        attackArea.width = 60;
        attackArea.height = 60;
        range = 60;
        description = "[" + name + "]\nBóng là con rối của ta nhưng đôi khi ta cũng\n"
        		+ "không kiểm soát được chúng, "
        		+ " có 80% khả năng \nx2 sát thương nhờ vào cái bóng của mình\n";
        price = 2000;
        knockBackPower = 10;
    }

}
