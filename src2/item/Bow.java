package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Bow extends Weapon {
    private int range; // Phạm vi tấn công (tăng khoảng cách tấn công)

    // Constructor đầy đủ
    public Bow(String name, int value, int attackIncrease, int durability, int attackSpeed, int range, Vector2 position, GameImage image) {
        super(name, value, attackIncrease, durability, attackSpeed, position, image);
        this.range = range > 0 ? range : 100; // Mặc định phạm vi 100 pixel
    }

    // Constructor mặc định
    public Bow(String name, int value, int attackIncrease) {
        this(name, value, attackIncrease, 100, 0, 100, new Vector2(0, 0), null);
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

            // Tăng attackPower và range
            player.setAttackPower(player.getAttackPower() + getSecondaryEffect());
            player.setAttackRange(player.getAttackRange() + range);
            setDurability(getDurability() - 5); // Giảm độ bền

            if (getAttackSpeed() > 0) {
                int newCooldown = Math.max(1, player.getAttackCooldown() - getAttackSpeed());
                player.setAttackCooldown(newCooldown);
            }

            if (player.getGp() != null && player.getGp().ui != null) {
                String message = player.getName() + " equipped " + getName() + "! Attack +" + getSecondaryEffect();
                if (range > 0) {
                    message += ", Attack Range +" + range + " pixels";
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
    public int getRange() { return range; }
    public void setRange(int range) { this.range = range > 0 ? range : 100; }
}