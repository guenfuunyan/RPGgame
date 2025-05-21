package object;

import main.GamePanel;
import util.GameImage;
import util.Item;

import java.awt.*;

public abstract class SuperObject {
    protected GamePanel gp;
    protected GameImage GameImage; // Đồng bộ tên biến
    protected String name;
    protected boolean collision = false;
    protected int worldX, worldY;
    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Kích thước mặc định 48x48
    protected int solidAreaDefaultX, solidAreaDefaultY;

    public SuperObject(GamePanel gp) {
        this.gp = gp;
        this.solidAreaDefaultX = 0;
        this.solidAreaDefaultY = 0;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + gp.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - gp.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - gp.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) {
            if (GameImage != null && GameImage.isLoaded()) {
                GameImage.draw(g2, screenX, screenY);
            } else {
                g2.setColor(Color.YELLOW);
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }

    public abstract Item interact();

    // Thêm phương thức setPosition
    public void setPosition(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    // Getters và Setters
    public GameImage getImage() { return GameImage; }
    public void setImage(GameImage gameImage) { this.GameImage = gameImage; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isCollision() { return collision; }
    public void setCollision(boolean collision) { this.collision = collision; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; }
    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; }
    public Rectangle getSolidArea() { return solidArea; }
    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }
}