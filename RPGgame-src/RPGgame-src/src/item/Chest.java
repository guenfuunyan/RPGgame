package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Chest extends Item {
    private boolean isOpened; // Trạng thái rương (mở hay chưa)

    // Constructor đầy đủ
    public Chest(String name, int value, Vector2 position, GameImage image) {
        super(name, "Chest", value, 0, 0, false, position, image); // Không tiêu thụ
        this.isOpened = false; // Mặc định chưa mở
    }

    // Constructor mặc định
    public Chest(String name, int value) {
        this(name, value, new Vector2(0, 0), null);
    }

    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            if (!isOpened) {
                isOpened = true; // Đánh dấu rương đã mở
                if (player.getGp() != null && player.getGp().ui != null) {
                    player.getGp().ui.showMessage(player.getName() + " opened the Chest!");
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public boolean isOpened() { return isOpened; }
    public void setOpened(boolean opened) { this.isOpened = opened; }
}