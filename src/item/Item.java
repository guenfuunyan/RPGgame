package item;

import entity.Character;
import entity.Entity;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Item {
    private String name;
    private int value;
    private String type;
    private int effectValue;
    private int secondaryEffect;
    private boolean consumable;
    private Vector2 position;
    private GameImage image;

    // Constructor đầy đủ
    public Item(String name, String type, int value, int effectValue, int secondaryEffect, boolean consumable, Vector2 position, GameImage image) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.effectValue = effectValue;
        this.secondaryEffect = secondaryEffect;
        this.consumable = consumable;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.image = image;
    }

    // Constructor mặc định (cho trường hợp chỉ có tên)
    public Item(String name) {
        this(name, "Generic", 0, 0, 0, false, new Vector2(0, 0), null);
    }

    // Constructor cho vật phẩm không cần hình ảnh ngay lập tức
    public Item(String name, String type, int value, int effectValue, int secondaryEffect, boolean consumable) {
        this(name, type, value, effectValue, secondaryEffect, consumable, new Vector2(0, 0), null);
    }

    // Phương thức vẽ vật phẩm lên màn hình
    public void draw(Graphics2D g2, int screenX, int screenY) {
        if (image != null && image.isLoaded() && image.getBufferedImage() != null) {
            g2.drawImage(image.getBufferedImage(), screenX, screenY, image.getWidth(), image.getHeight(), null);
        } else {
            g2.setColor(java.awt.Color.YELLOW);
            g2.fillRect(screenX, screenY, 16, 16);
            if (image == null) {
                System.err.println("⚠️ Item image is null for: " + name);
            } else {
                System.err.println("⚠️ Item image failed to load for: " + name + " at " + image.getSourcePath());
            }
        }
    }

    // Phương thức áp dụng hiệu ứng của vật phẩm, chỉ cho Character
    public void applyItemEffect(Entity entity) {
        if (entity == null) return;

        if (entity instanceof Character) {
            Character character = (Character) entity;

            if ("Health".equals(getType())) {
                character.heal(getEffectValue());
            } else if ("Weapon".equals(getType())) {
                character.setAttackPower(character.getAttackPower() + getSecondaryEffect());
                if (character.getGp() != null && character.getGp().ui != null) {
                    character.getGp().ui.showMessage("Attack power increased by " + getSecondaryEffect() + "!");
                }
            }
        } else {
            if (entity.getGp() != null && entity.getGp().ui != null) {
                entity.getGp().ui.showMessage(entity.getName() + " cannot use " + getType() + " items!");
            }
        }
    }

    // Getters và Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getEffectValue() { return effectValue; }
    public void setEffectValue(int effectValue) { this.effectValue = effectValue; }
    public int getSecondaryEffect() { return secondaryEffect; }
    public void setSecondaryEffect(int secondaryEffect) { this.secondaryEffect = secondaryEffect; }
    public boolean isConsumable() { return consumable; }
    public void setConsumable(boolean consumable) { this.consumable = consumable; }
    public Vector2 getPosition() { return new Vector2(position.getX(), position.getY()); }
    public void setPosition(Vector2 position) { this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0); }
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
}