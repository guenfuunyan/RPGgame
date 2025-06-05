package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Weapon extends Item {
    private int durability; // Độ bền của vũ khí
    private int attackSpeed; // Tốc độ tấn công (giảm cooldown tấn công, nếu có)

    // Constructor đầy đủ
    public Weapon(String name, int value, int attackIncrease, int durability, int attackSpeed, Vector2 position, GameImage image) {
        super(name, "Weapon", value, 0, attackIncrease, false, position, image); // secondaryEffect là attackIncrease
        this.durability = durability > 0 ? durability : 100; // Mặc định 100
        this.attackSpeed = attackSpeed > 0 ? attackSpeed : 0; // Mặc định 0 (không giảm cooldown)
    }

    // Constructor mặc định
    public Weapon(String name, int value, int attackIncrease) {
        this(name, value, attackIncrease, 100, 0, new Vector2(0, 0), null);
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

            // Tăng attackPower
            player.setAttackPower(player.getAttackPower() + getSecondaryEffect());
            if (attackSpeed > 0) {
                // Giảm attackCooldown nếu attackSpeed có giá trị
                int newCooldown = Math.max(1, player.getAttackCooldown() - attackSpeed);
                player.setAttackCooldown(newCooldown);
            }
            durability -= 5; // Giảm độ bền mỗi lần sử dụng

            if (player.getGp() != null && player.getGp().ui != null) {
                String message = player.getName() + " equipped " + getName() + "! Attack +" + getSecondaryEffect();
                if (attackSpeed > 0) {
                    message += ", Attack Speed increased!";
                }
                message += ", Durability: " + durability;
                player.getGp().ui.showMessage(message);
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
    public int getAttackSpeed() { return attackSpeed; }
    public void setAttackSpeed(int attackSpeed) { this.attackSpeed = attackSpeed > 0 ? attackSpeed : 0; }
}