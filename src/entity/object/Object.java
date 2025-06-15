package entity.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class Object extends Entity {
    // SPRITE IMAGES - Chỉ giữ lại những hình ảnh cụ thể cho Object
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, appear;

    // COLLISION AND AREAS - Riêng cho Object
    public boolean collision = false;
    public int attack;

    // OBJECT STATES
    public boolean alive = true;
    public boolean destructible = false;

    // OBJECT ATTRIBUTES
    public String name;
    public String description = "";

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public double useCost;
    public int price;
    public int knockBackPower = 0;
    public int healingAmount = 0;
    public int critPercent = 0;
    public boolean stackable = false;
    public int amount = 1;
    public boolean weaponProjectile = false;
    public boolean thanhKiem = false;
    public boolean pierce = false;
    public boolean stand = false;
    public int key = 0;

    // PROJECTILE ATTRIBUTES
    public WeaponProjectile projectileWeapon;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public Object(GamePanel gp) {
        super(gp);
        direction = "down";
        this.type = type_object;
    }

    // OBJECT INTERACTIONS
    public void interact() {
        // Phương thức tương tác cơ bản - có thể override
    }

    public void use(Player player) {
        // Phương thức sử dụng object - có thể override
    }

    // PARTICLE GENERATION
    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);
        return color;
    }

    public int getParticleSize() {
        int size = 6; // 6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

    public void generateParticle(Object generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    // UPDATE METHOD
    public void update() {
        // Cập nhật cơ bản cho object (nếu cần)
        // Phương thức này có thể được override cho các object phức tạp hơn
    }

    // ITEM DROP FUNCTIONALITY - Đồng bộ với GamePanel
    public void dropItem(Object droppedItem) {
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int tempScreenY = getScreenY();
        int tempScreenX = getScreenX();

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Không vẽ nếu object đã chết
            if (alive == false) {
                return;
            }

            // Chọn sprite theo hướng (chỉ áp dụng cho object có animation)
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Fallback cho object tĩnh
            if (image == null) {
                if (this.image != null) {
                    image = this.image;
                } else if (appear != null) {
                    image = appear;
                }
            }

            // Vẽ object tại vị trí chuẩn (không có offset như monsters)
            if (image != null) {
                g2.drawImage(image, tempScreenX, tempScreenY, null);
            }

            // Reset alpha về bình thường
            changeAlpha(g2, 1f);
        }
    }

    // RESOURCE CHECK
    public boolean haveResource(Player user) {
        boolean haveResource = false;
        // Override này trong các subclass để kiểm tra tài nguyên cụ thể
        return haveResource;
    }

    public void subtractResource(Player user) {
        // Override này trong các subclass để trừ tài nguyên cụ thể
    }
}