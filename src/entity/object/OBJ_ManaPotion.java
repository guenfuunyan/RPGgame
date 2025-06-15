package entity.object;

import entity.Player;
import main.GamePanel;

public class OBJ_ManaPotion extends Object {

    public OBJ_ManaPotion(GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = "Bình mana";
        value = 5;
        down1 = setup("/objects/mana", gp.tileSize, gp.tileSize);
        image = setup("/objects/mana", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/mana", gp.tileSize, gp.tileSize);
        description = "[Bình Mana]\nHồi phục năng lượng " + value + " điểm.";
        price = 50;
        stackable = true;

    }

    public void use(Player entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Bạn đã uống " + name + "!\n" + "Năng lượng được phục hồi " + value + " điểm.";
        entity.mana += value;
        gp.playSE(2);
    }
}
