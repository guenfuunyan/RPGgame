package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_ManaPotion;
import object.OBJ_HealthPotion;
import object.OBJ_Rock;

public class MON_Fourarms extends Entity {
	GamePanel gp;

    public MON_Fourarms(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Fourarms";
        defaultSpeed = 0;
        speed = defaultSpeed;
        maxLife = 120;
        life = maxLife;
        attack = 3;
        defense = 8;
        exp = 40;
        sizeRatio = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 122;
        solidArea.height = 108;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/4tay1", 128, 128);
        up2 = setup("/monster/4tay2", 128, 128);
        down1 = setup("/monster/4tay3", 128, 128);
        down2 = setup("/monster/4tay4", 128, 128);
        left1 = setup("/monster/4tay1", 128, 128);
        left2 = setup("/monster/4tay2", 128, 128);
        right1 = setup("/monster/4tay3", 128, 128);
        right2 = setup("/monster/4tay4", 128, 128);
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
    	}
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    
    
    public void checkDrop() {
        int i = new Random().nextInt(150) + 1;
        if (i >= 0 && i < 100) {
            dropItem(new OBJ_Coin_Bronze(gp,5));
        }
        else if (i >= 100 && i < 120) {
            dropItem(new OBJ_Heart(gp));
        }
        else if (i >= 120 && i < 130) {
            dropItem(new OBJ_Mana(gp));
        }
        else if (i >= 130 && i < 140) {
            dropItem(new OBJ_HealthPotion(gp));
        }
        else if (i >= 140 && i < 145) {
            dropItem(new OBJ_ManaPotion(gp));
        }

    }
}

