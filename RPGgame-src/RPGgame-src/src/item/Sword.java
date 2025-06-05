package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Sword extends Weapon {
    private double criticalChance; // Xác suất gây sát thương chí mạng (0.0 đến 1.0)

    // Constructor đầy đủ
    public Sword(String name, int value, int attackIncrease, int durability, int attackSpeed, double criticalChance, Vector2 position, GameImage image) {
        super(name, value, attackIncrease, durability, attackSpeed, position, image);
        this.criticalChance = Math.max(0.0, Math.min(criticalChance, 1.0)); // Giới hạn từ 0.0 đến 1.0
    }

    // Constructor mặc định
    public Sword(String name, int value, int attackIncrease) {
        this(name, value, attackIncrease, 100, 0, 0.15, new Vector2(0, 0), null); // Mặc định criticalChance 15%
    }

    @Override
    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            if (getDurability() <= 0) {
                if (player.getGp() != null && player.getGp().ui != null) {
                    player.getGp().ui.showMessage("The " + getName() + " is broken and cannot be used!");
                }
                return;
            }

            // Tăng attackPower và criticalChance
            player.setAttackPower(player.getAttackPower() + getSecondaryEffect());
            player.setCriticalChance(player.getCriticalChance() + criticalChance);
            setDurability(getDurability() - 5); // Giảm độ bền

            if (getAttackSpeed() > 0) {
                int newCooldown = Math.max(1, player.getAttackCooldown() - getAttackSpeed());
                player.setAttackCooldown(newCooldown);
            }

            if (player.getGp() != null && player.getGp().ui != null) {
                String message = player.getName() + " equipped " + getName() + "! Attack +" + getSecondaryEffect();
                if (criticalChance > 0) {
                    message += ", Critical Chance +" + (criticalChance * 100) + "%";
                }
                if (getAttackSpeed() > 0) {
                    message += ", Attack Speed increased!";
                }
                message += ", Durability: " + getDurability();
                player.getGp().ui.showMessage(message);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public double getCriticalChance() { return criticalChance; }
    public void setCriticalChance(double criticalChance) { this.criticalChance = Math.max(0.0, Math.min(criticalChance, 1.0)); }
}