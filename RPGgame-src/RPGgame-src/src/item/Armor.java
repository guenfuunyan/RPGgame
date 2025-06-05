package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Armor extends Item {
    private int durability; // Độ bền của áo giáp

    // Constructor đầy đủ
    public Armor(String name, int value, int defenseIncrease, int hpIncrease, int durability, Vector2 position, GameImage image) {
        super(name, "Armor", value, hpIncrease, defenseIncrease, false, position, image); // effectValue là hpIncrease, secondaryEffect là defenseIncrease
        this.durability = durability > 0 ? durability : 100; // Mặc định 100
    }

    // Constructor mặc định
    public Armor(String name, int value, int defenseIncrease, int hpIncrease) {
        this(name, value, defenseIncrease, hpIncrease, 100, new Vector2(0, 0), null);
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

            // Tăng defense và maxHp
            player.setDefense(player.getDefense() + getSecondaryEffect());
            player.setMaxHp(player.getMaxHp() + getEffectValue());
            player.setHp(Math.min(player.getMaxHp(), player.getHp() + getEffectValue())); // Phục hồi HP nhưng không vượt quá maxHp
            durability -= 10; // Giảm độ bền mỗi lần sử dụng

            if (player.getGp() != null && player.getGp().ui != null) {
                player.getGp().ui.showMessage(player.getName() + " equipped " + getName() + "! Defense +" + getSecondaryEffect() + ", Max HP +" + getEffectValue() + ", Durability: " + durability);
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
}