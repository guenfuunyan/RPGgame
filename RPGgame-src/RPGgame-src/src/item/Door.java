package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Door extends Item {
    private String requiredKeyType; // Loại khóa cần để mở cửa
    private boolean locked;         // Trạng thái khóa

    // Constructor đầy đủ
    public Door(String name, int value, String requiredKeyType, Vector2 position, GameImage image) {
        super(name, "Door", value, 0, 0, false, position, image); // Không tiêu thụ
        this.requiredKeyType = requiredKeyType != null ? requiredKeyType : "wooden";
        this.locked = true; // Mặc định cửa bị khóa
    }

    // Constructor mặc định
    public Door(String name, int value, String requiredKeyType) {
        this(name, value, requiredKeyType, new Vector2(0, 0), null);
    }

    // Mở khóa cửa
    public void unlock() {
        this.locked = false;
    }

    public void applyItemEffect(Character character) {
        // Door không có hiệu ứng trực tiếp khi nhặt, chỉ tương tác qua Player
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            if (player.getGp() != null && player.getGp().ui != null) {
                player.getGp().ui.showMessage(player.getName() + " encountered a Door!");
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public String getRequiredKeyType() { return requiredKeyType; }
    public void setRequiredKeyType(String requiredKeyType) { this.requiredKeyType = requiredKeyType != null ? requiredKeyType : "wooden"; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
}