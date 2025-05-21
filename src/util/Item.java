package util;

import entity.Entity;
import java.awt.Graphics2D;

public class Item {
    private String name;       // Tên của vật phẩm (ví dụ: "Key", "Potion")
    private int value;         // Giá trị của vật phẩm (có thể dùng để giao dịch)
    private String type;       // Loại vật phẩm (ví dụ: "Key", "Health", "Weapon")
    private int effectValue;   // Giá trị hiệu ứng (ví dụ: lượng HP hồi phục)
    private boolean consumable; // Có thể tiêu thụ không (true nếu dùng một lần)
    private Vector2 position;  // Vị trí của vật phẩm trong thế giới
    private GameImage image;   // Hình ảnh của vật phẩm

    // Constructor đầy đủ
    public Item(String name, String type, int value, int effectValue, boolean consumable, Vector2 position, GameImage image) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.effectValue = effectValue;
        this.consumable = consumable;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.image = image;
    }

    // Constructor mặc định (cho trường hợp chỉ có tên)
    public Item(String name) {
        this(name, "Generic", 0, 0, false, new Vector2(0, 0), null);
    }

    // Constructor cho vật phẩm không cần hình ảnh ngay lập tức
    public Item(String name, String type, int value, int effectValue, boolean consumable) {
        this(name, type, value, effectValue, consumable, new Vector2(0, 0), null);
    }

    // Phương thức vẽ vật phẩm lên màn hình
    public void draw(Graphics2D g2, int screenX, int screenY) {
        if (image != null && image.isLoaded() && image.getBufferedImage() != null) {
            g2.drawImage(image.getBufferedImage(), screenX, screenY, image.getWidth(), image.getHeight(), null);
        } else {
            // Vẽ hình mặc định nếu không có hình ảnh
            g2.setColor(java.awt.Color.YELLOW);
            g2.fillRect(screenX, screenY, 16, 16); // Kích thước mặc định
            if (image == null) {
                System.err.println("⚠️ Item image is null for: " + name);
            } else {
                System.err.println("⚠️ Item image failed to load for: " + name + " at " + image.getSourcePath());
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
    public boolean isConsumable() { return consumable; }
    public void setConsumable(boolean consumable) { this.consumable = consumable; }
    public Vector2 getPosition() { return new Vector2(position.getX(), position.getY()); }
    public void setPosition(Vector2 position) { this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0); }
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
}