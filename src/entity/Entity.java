package entity;

import entity.object.Projectile;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    public GamePanel gp;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Projectile projectile;

    // Các thuộc tính cần thiết cho CollisionChecker
    public String direction;
    public int speed;
    public boolean collisionOn = false;
    public int range = 0;

    // TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_skill = 8;
    public final int type_obstacle = 9;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // UTILITY METHODS - Chung cho tất cả các lớp con
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // POSITION METHODS - Chung cho tất cả các lớp con
    public int getScreenX() {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY() {
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }

    public int getCenterX() {
        int centerX = worldX + gp.tileSize / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = worldY + gp.tileSize / 2;
        return centerY;
    }

    // CAMERA CHECK - Chung cho tất cả các lớp con
    public boolean inCamera() {
        boolean inCamera = false;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }

    // DISTANCE CALCULATIONS - Chung cho tất cả các lớp con
    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return tileDistance;
    }

    // GRAPHICS UTILITIES - Chung cho tất cả các lớp con
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}