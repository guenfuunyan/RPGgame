package entity.monster;

import java.util.Random;

import entity.base.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_ManaPotion;
import object.OBJ_HealthPotion;
import object.OBJ_Rock;

public class MON_Pinkky extends Entity {
	GamePanel gp;

    public MON_Pinkky(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Pinkky";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 5;
        life = maxLife;
        attack = 3;
        defense = 0;
        exp = 10;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 76;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/Pinkky1", 85, 85);
        up2 = setup("/monster/Pinkky2", 85, 85);
        down1 = setup("/monster/Pinkky3", 85, 85);
        down2 = setup("/monster/Pinkky4", 85, 85);
        left1 = setup("/monster/Pinkky1", 85, 85);
        left2 = setup("/monster/Pinkky2", 85, 85);
        right1 = setup("/monster/Pinkky3", 85, 85);
        right2 = setup("/monster/Pinkky4", 85, 85);
    }

    public void setAction() {
    	checkAndChasePlayer();
    	if(onPath == true) {
    		int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
    		int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
    		searchPath(goalCol,goalRow);
    	}
    	else {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;

        if (i > 99 && projectile.alive == false && shotAvailableCounter == 10) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);

            // CHECK VACANCY
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
        }
    }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void checkAndChasePlayer() {
        int tileSize = gp.tileSize;

        // Calculate tile positions for orc and player
        int orcCol = worldX / tileSize;
        int orcRow = worldY / tileSize;
        int playerCol = gp.player.worldX / tileSize;
        int playerRow = gp.player.worldY / tileSize;

        // Calculate distance in tiles
        int deltaX = Math.abs(orcCol - playerCol);
        int deltaY = Math.abs(orcRow - playerRow);

        // If within 5-tile radius, chase the player
        if (deltaX <= 5 && deltaY <= 5) {
            onPath = true;  // Use pathfinding to move towards the player
            gp.pFinder.setNodes(orcCol, orcRow, playerCol, playerRow);
            gp.pFinder.search();
        }
        else {onPath = false;
        }
        }
    
    public void checkDrop() {
        int i = new Random().nextInt(200) + 1;
        if (i >= 0 && i < 100) {
            dropItem(new OBJ_Coin_Bronze(gp, 1));
        }
        else if (i >= 100 && i < 150) {
            dropItem(new OBJ_Heart(gp));
        }
        else if (i >= 150 && i < 165) {
            dropItem(new OBJ_Mana(gp));
        }
        else if (i >= 165 && i < 185) {
            dropItem(new OBJ_HealthPotion(gp));
        }
        else if (i >= 185 && i < 200) {
            dropItem(new OBJ_ManaPotion(gp));
        }

    }
}

