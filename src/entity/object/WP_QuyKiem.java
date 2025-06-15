package entity.object;

import main.GamePanel;

public class WP_QuyKiem extends Object {

    public WP_QuyKiem(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Quỷ Đao";
        down1 = setup("/objects/QuyKiem", gp.tileSize, gp.tileSize);
        amount = 1;
        attackValue = 20;
        healingAmount = -6;
        critPercent = 0;
        attackArea.width = 80;
        attackArea.height = 80;
        range = 80;
        description = "[" + name + "]\nSinh lực nhà ngươi là sức mạnh của đại đao này.\n"
        		+ "-6 máu mỗi lần đánh trúng địch, đổi lại sát\nthương vô cùng lớn";
        		
        price = 4000;
        knockBackPower = 2;
    }

}
