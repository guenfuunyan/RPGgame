package entity.object.tile_interactive;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.object.Object;
import main.GamePanel;

public class InteractiveTile extends Object {
    public boolean invincible;
    public int invincibleCounter;
    public int life = 2;
    int row;
    int col;

    public InteractiveTile(GamePanel gp, int col, int row, BufferedImage down1) {
        super(gp);
        this.down1 = down1;
        this.row = row;
        this.col = col;
        
        // Thiết lập vị trí world dựa trên col, row
        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void playSe() {
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    @Override
    public void update() {
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (inCamera()) {
            g2.drawImage(down1, getScreenX(), getScreenY(), null);
        }
    }
}