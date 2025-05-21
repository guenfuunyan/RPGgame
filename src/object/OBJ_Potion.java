package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

public class OBJ_Potion extends SuperObject {
    private int healAmount = 20;

    public OBJ_Potion(GamePanel gp) {
        super(gp);
        this.name = "Potion";
        this.collision = true; // Đặt va chạm cho potion
        this.setImage(loadImage(gp.tileSize, gp.tileSize));
        this.setPosition(0, 0); // Vị trí mặc định, sẽ được đặt lại bởi AssetSetter
    }

    // Phương thức tải hình ảnh với giá trị mặc định nếu thất bại
    private GameImage loadImage(int width, int height) {
        try {
            GameImage image = new GameImage("/objects/potion.png", width, height);
            if (!image.isLoaded()) {
                throw new Exception("Image not loaded");
            }
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load potion image: " + e.getMessage());
            return null; // SuperObject.draw() sẽ xử lý hình ảnh dự phòng
        }
    }

    @Override
    public Item interact() {
        if (gp != null && gp.player != null) {
            String message = "Picked up a " + name + ", heals for " + healAmount + " HP";
            if (gp.ui != null) {
                gp.ui.showMessage(message);
            } else {
                System.err.println("UI is null. Showing message in console: " + message);
            }
            this.collision = false; // Loại bỏ potion sau khi nhặt
            return new Item(name, "Health", healAmount, healAmount, true); // Đồng bộ healAmount
        }
        return null; // Trả về null nếu gp hoặc gp.player là null
    }

    public int getHealAmount() { return healAmount; }
    public void setHealAmount(int healAmount) { this.healAmount = healAmount; }
}