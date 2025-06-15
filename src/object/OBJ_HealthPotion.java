package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HealthPotion extends Entity {
    GamePanel gp;

    public OBJ_HealthPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Bình hồi máu";
        value = 5;
        down1 = setup("/objects/health", gp.tileSize, gp.tileSize);
        description = "["+name+"]\nHồi bản thân " + value + " máu.";
        price = 50;
        stackable = true;

    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Bạn đã uống " + name + "!\n" + "Hồi " + value + " máu.";
        entity.life += value;
        gp.playSE(2);
    }
}
