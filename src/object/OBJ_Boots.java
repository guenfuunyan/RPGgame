package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

public class OBJ_Boots extends SuperObject {
    private int speedBoost = 2; // Giá trị tăng tốc mặc định
    private int duration = 60 * 10; // Thời gian hiệu lực mặc định (10 giây tại 60 FPS)
    private boolean pickedUp = false;

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        this.name = "Boots";
        this.collision = true; // Đặt va chạm cho boots
        this.setImage(loadImage(gp.tileSize, gp.tileSize));
        this.setPosition(0, 0); // Vị trí mặc định, sẽ được đặt lại bởi AssetSetter
    }

    // Phương thức tải hình ảnh với giá trị mặc định nếu thất bại
    private GameImage loadImage(int width, int height) {
        try {
            return new GameImage("/objects/boots.png", width, height);
        } catch (Exception e) {
            System.err.println("Failed to load boots image: " + e.getMessage());
            return null; // SuperObject.draw() sẽ xử lý hình ảnh dự phòng
        }
    }

    @Override
    public Item interact() {
        // Tăng tốc độ người chơi khi nhặt boots
        if (gp != null && gp.player != null) {
            gp.player.applyBootsEffect(speedBoost); // Gọi phương thức trong Player
            String message = "Picked up " + name + "! Speed increased by " + speedBoost + " for " + (duration / 60) + " seconds!";
            if (gp.ui != null) {
                gp.ui.showMessage(message);
            } else {
                System.err.println("UI is null. Showing message in console: " + message);
            }
            this.collision = false; // Loại bỏ boots sau khi nhặt
            pickedUp = true; // Đánh dấu đã nhặt
        }
        return new Item(name, "Boots", speedBoost, 0, false); // Trả về Item với effectValue là speedBoost
    }

    // Phương thức bổ sung: Lấy thời gian hiệu lực
    public int getDuration() {
        return duration;
    }

    // Phương thức bổ sung: Kiểm tra trạng thái đã nhặt
    public boolean isPickedUp() {
        return pickedUp;
    }

    // Phương thức bổ sung: Reset trạng thái
    public void resetState() {
        this.collision = true;
        this.pickedUp = false;
    }

    // Getter và Setter bổ sung
    public int getSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}