package entity.object;

import main.GamePanel;

public class OBJ_Axe extends Object {

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Rìu kim cương";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        range = 30;
        description = "[ " + name +" ]"+"\nLấp lánh và bóng loáng nhưng lại sứt mẻ khá\nnhiều, liệu có phải kim cương thật không?";
        price = 800;
        knockBackPower = 5;
    }
}
