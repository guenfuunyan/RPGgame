package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Darkmatter;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_ManaPotion;
import object.OBJ_HealthPotion;
import object.OBJ_Rock;

public class MON_StarGuardian extends Entity {
	GamePanel gp;

    public MON_StarGuardian(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "StarGuardian";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 160;
        life = maxLife;
        attack = 25;
        defense = 4;
        exp = 51;
        projectile = new OBJ_Darkmatter(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 79;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/cucgai1", 85, 85);
        up2 = setup("/monster/cucgai2", 85, 85);
        down1 = setup("/monster/cucgai3", 85, 85);
        down2 = setup("/monster/cucgai4", 85, 85);
        left1 = setup("/monster/cucgai1", 85, 85);
        left2 = setup("/monster/cucgai2", 85, 85);
        right1 = setup("/monster/cucgai3", 85, 85);
        right2 = setup("/monster/cucgai4", 85, 85);
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
        int i = new Random().nextInt(860) + 1;
        if (i >= 0 && i < 35) {
            dropItem(new OBJ_Coin_Bronze(gp,3));
        }
        else if (i >= 35 && i < 50) {
            dropItem(new OBJ_Heart(gp));
        }
        else if (i >= 50 && i < 65) {
            dropItem(new OBJ_Mana(gp));
        }
        else if (i >= 65 && i < 85) {
            dropItem(new OBJ_HealthPotion(gp));
        }
        else if (i >= 85 && i < 100) {
            dropItem(new OBJ_ManaPotion(gp));
        }
        else {
        	if( gp.skillDarkmatterAppear == 0) {
        		dropItem(projectile);
        		gp.skillDarkmatterAppear = 1;
        		
        	}else {
        		dropItem(new OBJ_Coin_Bronze(gp,3));
        	}			
        }

    }
}

