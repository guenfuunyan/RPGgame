package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.name = "Chest";
        this.collision = true; // Đặt va chạm cho rương
        this.setImage(loadImage(gp.tileSize, gp.tileSize));
        this.setPosition(0, 0); // Vị trí mặc định, sẽ được đặt lại bởi AssetSetter
    }

    // Phương thức tải hình ảnh với giá trị mặc định nếu thất bại
    private GameImage loadImage(int width, int height) {
        try {
            return new GameImage("/objects/chest.png", width, height);
        } catch (Exception e) {
            System.err.println("Failed to load chest image: " + e.getMessage());
            return null; // SuperObject.draw() sẽ xử lý hình ảnh dự phòng
        }
    }

    @Override
    public Item interact() {
        // Kết thúc game và hiển thị thông báo khi mở rương
        if (gp != null) {
            if (gp.ui != null) {
                gp.ui.setGameFinished(true);
                gp.ui.showMessage("You opened the " + name + "! Game Finished!");
            } else {
                System.err.println("UI is null. Showing message in console: You opened the " + name + "! Game Finished!");
            }
            this.collision = false; // Rương đã được mở, không còn va chạm
        }
        return new Item("Treasure", "Chest", 0, 0, false); // Trả về vật phẩm "Treasure"
    }
}