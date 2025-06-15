package entity.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public abstract class Object extends Entity {
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

    // DRAWING - Sử dụng các phương thức từ Entity
    public void draw(Graphics2D g2) {
        if (inCamera()) {
            BufferedImage objectImage = null;
            
            // Chọn sprite dựa trên hướng (nếu có)
            switch(direction) {
                case "up":
                    objectImage = up1 != null ? up1 : image;
                    break;
                case "down":
                    objectImage = down1 != null ? down1 : image;
                    break;
                case "left":
                    objectImage = left1 != null ? left1 : image;
                    break;
                case "right":
                    objectImage = right1 != null ? right1 : image;
                    break;
                default:
                    objectImage = image;
                    break;
            }

            // Vẽ object sử dụng phương thức từ Entity
            if (objectImage != null) {
                g2.drawImage(objectImage, getScreenX(), getScreenY(), null);
            }

            // Reset alpha về mức ban đầu
            changeAlpha(g2, 1f);
        }
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