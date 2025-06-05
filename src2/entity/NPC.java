package entity;

import main.GamePanel;
import util.GameImage;
import util.Vector2;

import java.awt.*;

public class NPC {
    protected GamePanel gp;
    protected int id;
    protected String name;
    protected int worldX;
    protected int worldY;
    protected int screenX;
    protected int screenY;
    protected Vector2 position;
    protected GameImage image;
    protected String dialogue;

    // Constructor
    public NPC(GamePanel gp, int id, String name, Vector2 position, GameImage image) {
        this.gp = gp;
        this.id = id;
        this.name = name;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // Đặt vị trí màn hình mặc định
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2); // Đặt vị trí màn hình mặc định
        this.image = image;
        this.dialogue = "Hello, traveler! Welcome to our village.";
    }

    // Vẽ NPC lên màn hình
    public void draw(Graphics2D g2) {
        if (gp != null && gp.player != null) {
            screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
        }

        if (image != null && image.isLoaded()) {
            image.draw(g2, screenX, screenY);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    // Hiển thị hội thoại
    /**public void speak() {
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(name + ": " + dialogue);
        } else {
            System.err.println("UI is null. Showing message in console: " + name + ": " + dialogue);
        }
    }**/

    // Getters và Setters
    public GamePanel getGp() { return gp; }
    public void setGp(GamePanel gp) { this.gp = gp; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; syncWorldPosition(); }
    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; syncWorldPosition(); }
    public int getScreenX() { return screenX; }
    public void setScreenX(int screenX) { this.screenX = screenX; }
    public int getScreenY() { return screenY; }
    public void setScreenY(int screenY) { this.screenY = screenY; }
    public Vector2 getPosition() { return position != null ? new Vector2(position.getX(), position.getY()) : null; }
    public void setPosition(Vector2 position) {
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
    }
    public void setPosition(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        syncWorldPosition();
    }
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
    public String getDialogue() { return dialogue; }
    public void setDialogue(String dialogue) { this.dialogue = dialogue != null ? dialogue : ""; }

    // Đồng bộ vị trí
    protected void syncWorldPosition() {
        if (position != null) {
            this.position.setX(worldX);
            this.position.setY(worldY);
        }
    }
}