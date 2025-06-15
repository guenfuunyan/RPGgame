package entity.object;

import java.awt.Color;

import entity.Player;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        name = "Fireball";
        type = type_skill;
        speed = 5;
        maxLife = 50;
        life = maxLife;
        attack = 4;
        useCost = 6;
        range = 1;  	//range = 1 la toi thieu de co collision
        alive = false;
        pierce = false;
        description = "[ " + name +" ]"+"\nNgọn lửa có sức nóng cả\nngàn độ đủ để thiêu cháy\ntrái tim của những sinh viên\ntrượt môn.";
        
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
        appear = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Player user) {
        boolean haveResource = false;

        if (user.mana >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }

    public void subtractResource(Player user) {
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
