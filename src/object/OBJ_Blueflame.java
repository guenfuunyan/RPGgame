package object;

import java.awt.Color;

import entity.base.Entity;
import entity.base.GameObject;
import entity.base.Projectile;
import main.GamePanel;

public class OBJ_Blueflame extends Projectile {
    GamePanel gp;
    public String description = "";
    private GameObject gameObject;

    public OBJ_Blueflame(GamePanel gp) {
        super(gp);

        this.gp = gp;
        this.gameObject = new GameObject(gp);

        name = "Blueflame";
        type = gameObject.type_skill;
        speed = 7;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        useCost = 5;
        range = 1;  	//range = 1 la toi thieu de co collision
        alive = false;
        pierce = false;
        description = "[ " + name +" ]\nNgọn lửa sắc xanh này đúng\nlà hợp với mấy ông cô đơn,\nbởi tình yêu màu đỏ mà.";

        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/luaup1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/luaup2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/luadown1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/luadown2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/lualeft1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/lualeft2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/luaright1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/luaright2", gp.tileSize, gp.tileSize);
        appear = setup("/projectile/luaright1", gp.tileSize, gp.tileSize);
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
