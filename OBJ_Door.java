package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

public class OBJ_Door extends SuperObject {
    private boolean isLocked = true;

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.name = "Door";
        this.collision = true; // Đặt va chạm cho cửa
        this.setImage(loadImage(gp.tileSize, gp.tileSize));
        this.setPosition(0, 0); // Vị trí mặc định, sẽ được đặt lại bởi AssetSetter
    }

    // Phương thức tải hình ảnh với giá trị mặc định nếu thất bại
    private GameImage loadImage(int width, int height) {
        try {
            GameImage image = new GameImage("/objects/door.png", width, height);
            if (!image.isLoaded()) {
                throw new Exception("Image not loaded");
            }
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load door image: " + e.getMessage());
            return null; // SuperObject.draw() sẽ xử lý hình ảnh dự phòng
        }
    }

    @Override
    public Item interact() {
        if (gp != null && gp.player != null) {
            if (isLocked) {
                boolean hasKey = false;
                for (Item item : gp.player.getInventory()) {
                    if ("Key".equals(item.getType())) {
                        hasKey = true;
                        break;
                    }
                }
                if (hasKey) {
                    String message = "The " + name + " is unlocked with a Key!";
                    if (gp.ui != null) {
                        gp.ui.showMessage(message);
                    } else {
                        System.err.println("UI is null. Showing message in console: " + message);
                    }
                    unlock();
                    return null; // Không trả về Item khi mở cửa
                } else {
                    String message = "The " + name + " is locked. Requires a Key to open.";
                    if (gp.ui != null) {
                        gp.ui.showMessage(message);
                    } else {
                        System.err.println("UI is null. Showing message in console: " + message);
                    }
                    return new Item(name, "Door", 0, 0, false); // Trả về Item khi bị khóa
                }
            } else {
                String message = "The " + name + " is already unlocked!";
                if (gp.ui != null) {
                    gp.ui.showMessage(message);
                } else {
                    System.err.println("UI is null. Showing message in console: " + message);
                }
                return null; // Không trả về Item khi đã mở
            }
        }
        return null; // Trả về null nếu gp hoặc gp.player là null
    }

    public void unlock() {
        if (isLocked) {
            isLocked = false;
            collision = false;
            String message = name + " has been unlocked!";
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage(message);
            } else {
                System.err.println("UI is null. Showing message in console: " + message);
            }
        }
    }

    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { this.isLocked = locked; }
}