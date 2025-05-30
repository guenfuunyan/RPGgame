package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class MagicWand extends Weapon {
    private double manaCostReduction; // Giảm chi phí mana khi sử dụng kỹ năng (0.0 đến 1.0)

    // Constructor đầy đủ
    public MagicWand(String name, int value, int attackIncrease, int durability, int attackSpeed, double manaCostReduction, Vector2 position, GameImage image) {
        super(name, value, attackIncrease, durability, attackSpeed, position, image);
        this.manaCostReduction = Math.max(0.0, Math.min(manaCostReduction, 1.0)); // Giới hạn từ 0.0 đến 1.0
    }

    // Constructor mặc định
    public MagicWand(String name, int value, int attackIncrease) {
        this(name, value, attackIncrease, 100, 0, 0.1, new Vector2(0, 0), null); // Mặc định manaCostReduction 10%
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

            // Tăng attackPower và manaCostReduction
            player.setAttackPower(player.getAttackPower() + getSecondaryEffect());
            player.setManaCostReduction(player.getManaCostReduction() + manaCostReduction);
            setDurability(getDurability() - 5); // Giảm độ bền

            if (getAttackSpeed() > 0) {
                int newCooldown = Math.max(1, player.getAttackCooldown() - getAttackSpeed());
                player.setAttackCooldown(newCooldown);
            }

            if (player.getGp() != null && player.getGp().ui != null) {
                String message = player.getName() + " equipped " + getName() + "! Attack +" + getSecondaryEffect();
                if (manaCostReduction > 0) {
                    message += ", Mana Cost Reduced by " + (manaCostReduction * 100) + "%";
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
    public double getManaCostReduction() { return manaCostReduction; }
    public void setManaCostReduction(double manaCostReduction) { this.manaCostReduction = Math.max(0.0, Math.min(manaCostReduction, 1.0)); }
}