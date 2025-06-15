package entity.base;

import main.GamePanel;

public class WeaponProjectile extends Projectile {

    public WeaponProjectile(GamePanel gp) {
        super(gp);
    }

    // No need to override setMonster and set methods as they're inherited from Projectile

    // The update method is similar to Projectile's but doesn't have the stand check
    @Override
    public void update() {
        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack, knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                if(pierce == false) alive = false;
            }
        }

        if (user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);

            if (gp.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                generateParticle(user.projectile, gp.player);
                alive = false;
            }
        }

        // Always move (no stand check)
        switch(direction) {
        case "up" :
        	worldY -= speed;
        	break;
        case "down" :
        	worldY += speed;
        	break;
        case "left" :
        	worldX -= speed;
        	break;
        case "right" :
        	worldX += speed;
        	break;
        }

        life--;
        if (life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 6) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }

    @Override
    public void subtractResource(Entity user) {
        // To be implemented by subclasses
    }
}
