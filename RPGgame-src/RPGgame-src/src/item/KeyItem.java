package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class KeyItem extends Item {
    private String keyType; // Loại khóa (ví dụ: "wooden", "iron", "golden")
    private String rarity;  // Độ hiếm (ví dụ: "common", "rare", "epic")

    // Constructor đầy đủ
    public KeyItem(String name, int value, String keyType, String rarity, Vector2 position, GameImage image) {
        super(name, "Key", value, 0, 0, false, position, image); // Không tiêu thụ
        this.keyType = keyType != null ? keyType : "wooden"; // Mặc định là "wooden"
        this.rarity = rarity != null ? rarity : "common";    // Mặc định là "common"
    }

    // Constructor mặc định
    public KeyItem(String name, int value) {
        this(name, value, "wooden", "common", new Vector2(0, 0), null); // Mặc định
    }

    // Kiểm tra xem khóa có thể mở cửa không
    public boolean canUnlock(Door door) {
        if (door == null) return false;
        return door.isLocked() && keyType.equals(door.getRequiredKeyType());
    }

    // Sử dụng khóa để mở cửa
    public boolean useKey(Player player, Door door) {
        if (player == null || door == null || !canUnlock(door)) return false;

        door.unlock();
        player.setHasKey(player.getHasKey() - 1); // Giảm số lượng khóa
        if (player.getGp() != null && player.getGp().ui != null) {
            player.getGp().ui.showMessage(player.getName() + " used a " + rarity + " " + keyType + " Key to unlock the door! Keys remaining: " + player.getHasKey());
        }
        return true;
    }

    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            player.setHasKey(player.getHasKey() + 1);
            if (character.getGp() != null && character.getGp().ui != null) {
                character.getGp().ui.showMessage(character.getName() + " picked up a " + rarity + " " + keyType + " Key! Total keys: " + player.getHasKey());
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType != null ? keyType : "wooden"; }
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity != null ? rarity : "common"; }
}