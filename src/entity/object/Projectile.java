package entity.object;

import entity.Entity;
import main.GamePanel;

public class Projectile extends Object {
    Entity user;
    public int life;
    public int maxLife;
    public int spriteCounter = 0;
    public int spriteNum = 0;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void setMonster(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;

        //vi tri collision (tinh tu goc tren ben trai)
        switch(direction) {
            case "up":
                solidArea.width = gp.tileSize;
                solidArea.height = range;
                break;
            case "down":
                solidArea.width = gp.tileSize;
                solidArea.height = gp.tileSize  + range;
                break;
            case "left":
                solidArea.width = range;
                solidArea.height = gp.tileSize;
                break;
            case "right":
                solidArea.width = gp.tileSize + range;
                solidArea.height = gp.tileSize;
                break;
        }
    }

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

        if(this.stand !=true) {
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

        }


        life --;
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

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        return haveResource;
    }

    public void subtractResource(Entity user) {
    }
}