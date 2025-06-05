package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Potion extends Item {
    private String potionType; // Loại thuốc: "health", "mana", "both"

    // Constructor đầy đủ
    public Potion(String name, int value, int healthRestore, int manaRestore, String potionType, Vector2 position, GameImage image) {
        super(name, "Potion", value, healthRestore, manaRestore, true, position, image); // Tiêu thụ, effectValue cho healthRestore, secondaryEffect cho manaRestore
        this.potionType = potionType != null ? potionType : "health"; // Mặc định là "health"
    }

    // Constructor mặc định (chỉ hồi HP)
    public Potion(String name, int value, int healthRestore) {
        this(name, value, healthRestore, 0, "health", new Vector2(0, 0), null);
    }

    // Constructor cho cả HP và mana
    public Potion(String name, int value, int healthRestore, int manaRestore) {
        this(name, value, healthRestore, manaRestore, "both", new Vector2(0, 0), null);
    }

    public void applyItemEffect(Character character) {
        if (character != null) {
            boolean applied = false;
            if ("health".equals(potionType) || "both".equals(potionType)) {
                character.heal(getEffectValue());
                applied = true;
            }
            if ("mana".equals(potionType) || "both".equals(potionType)) {
                character.setMana(character.getMana() + getSecondaryEffect());
                applied = true;
            }
            if (applied && character.getGp() != null && character.getGp().ui != null) {
                String message = character.getName() + " used a " + potionType + " Potion!";
                if (getEffectValue() > 0) message += " Healed for " + getEffectValue() + " HP.";
                if (getSecondaryEffect() > 0) message += " Restored " + getSecondaryEffect() + " Mana.";
                character.getGp().ui.showMessage(message);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public String getPotionType() { return potionType; }
    public void setPotionType(String potionType) { this.potionType = potionType != null ? potionType : "health"; }
}