package entity;

import main.GamePanel;
import util.GameImage;
import util.Vector2;

import java.awt.*;

public class NPC extends Entity {
    protected GameImage image; // Hình ảnh của NPC
    protected String dialogue; // Hội thoại của NPC

    public NPC(GamePanel gp, int id, String name, Vector2 position, GameImage image) {
        super(gp, id, name, position, 1, 1, 0, 0, 0); // hp=1, maxHp=1, attackPower=0, defense=0, speed=0
        this.image = image;
        this.collisionOn = true; // NPC có va chạm
        this.solidArea = new Rectangle(8, 8, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.dialogue = "Hello, traveler! Welcome to our village.";
    }

    @Override
    public void update() {
        // NPC không di chuyển hoặc chiến đấu
    }

    @Override
    public void draw(Graphics2D g2) {
        // Tính tọa độ màn hình dựa trên vị trí của người chơi
        if (gp != null && gp.player != null) {
            screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
        }

        if (image != null && image.isLoaded()) {
            image.draw(g2, screenX, screenY); // Vẽ tại tọa độ màn hình
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    public void talk() {
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(dialogue);
        }
    }

    // Getter và Setter cho các thuộc tính đặc thù của NPC
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
    public String getDialogue() { return dialogue; }
    public void setDialogue(String dialogue) { this.dialogue = dialogue; }
}