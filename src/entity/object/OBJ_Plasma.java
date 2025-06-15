package entity.object;

import java.awt.Color;

import entity.Player;
import main.GamePanel;

public class OBJ_Plasma extends Projectile {

    public OBJ_Plasma(GamePanel gp) {
        super(gp);

        name = "Plasma";
        type = type_skill;
        speed = 7;
        maxLife = 75;
        life = maxLife;
        attack = 40;
        useCost = 6;
        range = 1;  	//range = 1 la toi thieu de co collision
        alive = false;
        pierce = false;
        description = "[ " + name +" ]";
        
        getImage();
    }

    public void getImage() {
        up1 = down1 = left1 = right1  
        	= setup("/projectile/plasma", gp.tileSize, gp.tileSize);
        up2 = down2 = left2 = right2 
        	= setup("/projectile/plasmaThumb", gp.tileSize, gp.tileSize);
        

        appear = setup("/projectile/plasmaThumb", gp.tileSize, gp.tileSize);
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
