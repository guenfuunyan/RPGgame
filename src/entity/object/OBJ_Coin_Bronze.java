package entity.object;

import java.util.Random;

import entity.Player;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Object {
    public OBJ_Coin_Bronze(GamePanel gp, int heSoNhanValue) {
        super(gp);

        type = type_pickupOnly;
        name = "Vàng";
        int gold = new Random().nextInt(100) + 1;
        value = gold * heSoNhanValue;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public void use(Player entity) {
        gp.playSE(1);
        gp.ui.addMessage("Vàng +" + value);
        gp.player.coin += value;
    }
}
