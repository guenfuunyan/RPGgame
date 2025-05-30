package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Shield extends Item {
    private int durability; // Độ bền của khiên
    private double blockChance; // Xác suất chặn sát thương (0.0 đến 1.0)

    // Constructor đầy đủ
    public Shield(String name, int value, int defenseIncrease, int durability, double blockChance, Vector2 position, GameImage image) {
        super(name, "Shield", value, 0, defenseIncrease, false, position, image); // secondaryEffect là defenseIncrease
        this.durability = durability > 0 ? durability : 100; // Mặc định 100
        this.blockChance = Math.max(0.0, Math.min(blockChance, 1.0)); // Giới hạn từ 0.0 đến 1.0
    }

    // Constructor mặc định
    public Shield(String name, int value, int defenseIncrease) {
        this(name, value, defenseIncrease, 100, 0.2, new Vector2(0, 0), null); // Mặc định blockChance 20%
    }

    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            if (durability <= 0) {
                if (player.getGp() != null && player.getGp().ui != null) {
                    player.getGp().ui.showMessage("The " + getName() + " is broken and cannot be used!");
                }
                return;
            }

            // Tăng defense
            player.setDefense(player.getDefense() + getSecondaryEffect());
            durability -= 5; // Giảm độ bền mỗi lần sử dụng

            if (player.getGp() != null && player.getGp().ui != null) {
                player.getGp().ui.showMessage(player.getName() + " equipped " + getName() + "! Defense +" + getSecondaryEffect() + ", Durability: " + durability);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public int getDurability() { return durability; }
    public void setDurability(int durability) { this.durability = durability > 0 ? durability : 100; }
    public double getBlockChance() { return blockChance; }
    public void setBlockChance(double blockChance) { this.blockChance = Math.max(0.0, Math.min(blockChance, 1.0)); }
}