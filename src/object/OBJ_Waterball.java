package object;

import java.awt.Color;

import entity.base.Entity;
import entity.base.GameObject;
import entity.base.Projectile;
import main.GamePanel;

public class OBJ_Waterball extends Projectile {
    GamePanel gp;
    private GameObject gameObject;

    public OBJ_Waterball(GamePanel gp) {
        super(gp);

        this.gp = gp;
        this.gameObject = new GameObject(gp);

        name = "Fireball";
        type = gameObject.type_skill;
        speed = 10;
        maxLife = 50 ;
        life = maxLife;
        attack = 3;
        useCost = 1;
        range = 1;
        alive = false;
        pierce = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/Explosion_3", gp.tileSize, gp.tileSize);
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
