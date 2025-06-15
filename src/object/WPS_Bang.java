package object;

import java.awt.Color;

import entity.base.Entity;
import entity.base.WeaponProjectile;
import main.GamePanel;

public class WPS_Bang extends WeaponProjectile {
    GamePanel gp;

    public WPS_Bang(GamePanel gp) {
        super(gp);

        this.gp = gp;
        
        name = "Băng Chấn";
        speed = 10;
        maxLife = 50 ;
        life = maxLife;
        attack = 15;
        useCost = 8;
        alive = false;
        pierce = true;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/arrowup", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/arrowup", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/arrowdown", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/arrowdown", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/arrowleft", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/arrowleft", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/arrowright", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/arrowright", gp.tileSize, gp.tileSize);
    }
    
    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        if (user.mana >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);

        return color;
    }

    public int getParticleSize() {
        int size = 6;

        return size;
    }

    public int getParticleSpeed() {
        int speed = 2;

        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;

        return maxLife;
    }
}
