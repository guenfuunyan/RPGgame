package object;

import entity.base.GameObject;
import main.GamePanel;

public class WP_ThanhKiem extends GameObject {

    public WP_ThanhKiem(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Thánh Kiếm";
        down1 = setup("/objects/ThanhKiem", gp.tileSize, gp.tileSize);
        amount = 1;
        attackValue = 10;
        critPercent = 0;
        thanhKiem = true;
        attackArea.width = 50;
        attackArea.height = 50;
        range = 50;
        description = "[" + name + "]\nThanh kiếm của vị anh hùng đã từng một mình \nđẩy lùi cả đội quân quái vật.\n"
        		+ "Tăng kinh nghiệm sau khi hạ gục quái lên 500%";

        price = 1;
        knockBackPower = 2;
    }

}
