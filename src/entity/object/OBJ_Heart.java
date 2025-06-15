package entity.object;

import entity.Player;
import main.GamePanel;

public class OBJ_Heart extends Object {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        type = type_pickupOnly;
        name = "HealthPotion";
        value = 2;
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
    }

    public void use(Player entity) {
        gp.playSE(2);
        gp.ui.addMessage("Máu +" + value);
        entity.life += value;
    }
}
