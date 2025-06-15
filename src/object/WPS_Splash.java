package object;

import java.awt.Color;

import entity.Entity;
import entity.WeaponProjectile;
import main.GamePanel;

public class WPS_Splash extends WeaponProjectile {
    GamePanel gp;

    public WPS_Splash(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = "Splash";
        speed = 10;
        maxLife = 50 ;
        life = maxLife;
        attack = 5;
        useCost = 6;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/watersplashup", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/watersplashup", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/watesplashdown", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/watesplashdown", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/watersplashup", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/watersplashup", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/watesplashdown", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/watesplashdown", gp.tileSize, gp.tileSize);
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
