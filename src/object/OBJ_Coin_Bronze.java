package object;

import java.util.Random;

import entity.base.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp, int heSoNhanValue) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Vàng";
        int gold = new Random().nextInt(100) + 1;
        value = gold * heSoNhanValue;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Vàng +" + value);
        gp.player.coin += value;
    }
}
