package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

import java.awt.*;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.name = "Key";
        this.collision = true;
        this.solidArea = new Rectangle(8, 8, 32, 32);
        this.solidAreaDefaultX = 8;
        this.solidAreaDefaultY = 8;
        this.GameImage = loadImage(gp.tileSize, gp.tileSize);
    }

    // Phương thức tải hình ảnh với giá trị mặc định nếu thất bại
    private GameImage loadImage(int width, int height) {
        try {
            GameImage image = new GameImage("/objects/key.png", width, height);
            if (!image.isLoaded()) {
                throw new Exception("Image not loaded");
            }
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load key image: " + e.getMessage());
            return null; // SuperObject.draw() sẽ xử lý hình ảnh dự phòng
        }
    }

    @Override
    public Item interact() {
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage("Picked up a " + name + "!");
        } else {
            System.out.println("Picked up a " + name);
        }
        return new Item(name, "Key", 1, 0, false); // effectValue = 1 để biểu thị giá trị chìa khóa
    }

    // Getter và Setter bổ sung
    public void setPosition(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
}