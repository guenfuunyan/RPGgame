package entity.monster;

import java.util.Random;

import entity.base.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_HealthPotion;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_ManaPotion;
import object.OBJ_Rock;

public class MON_ORC extends Entity{
	 GamePanel gp;

	    public MON_ORC(GamePanel gp) {
	        super(gp);

	        this.gp = gp;
           
	        type = type_monster;
	        name = "ORC";
	        defaultSpeed = 1;
	        speed = defaultSpeed;
	        maxLife = 4;
	        life = maxLife;
	        attack = 0;
	        defense = 0;
	        exp = 2;
	        projectile = new OBJ_Rock(gp);

	        solidArea.x = 3;
	        solidArea.y = 18;
	        solidArea.width = 42;
	        solidArea.height = 30;
	        solidAreaDefaultX = solidArea.x;
	        solidAreaDefaultY = solidArea.y;
	        
	        

	        getImage();
	        getAttackImage();
	    }

	    public void getImage() {
	        up1 = setup("/monster/OrcUp1", gp.tileSize, gp.tileSize);
	        up2 = setup("/monster/OrcUp2", gp.tileSize, gp.tileSize);
	        down1 = setup("/monster/OrcDown1", gp.tileSize, gp.tileSize);
	        down2 = setup("/monster/OrcDown2", gp.tileSize, gp.tileSize);
	        left1 = setup("/monster/OrcLeft1", gp.tileSize, gp.tileSize);
	        left2 = setup("/monster/OrcLeft2", gp.tileSize, gp.tileSize);
	        right1 = setup("/monster/OrcRight1", gp.tileSize, gp.tileSize);
	        right2 = setup("/monster/OrcRight2", gp.tileSize, gp.tileSize);
	    }
	    
	    public void getAttackImage()
	    {
	    	attackUp1 = setup("/monster/OrcAttackUp1", gp.tileSize, gp.tileSize*2);
	    	attackUp2 = setup("/monster/OrcAttackUp2", gp.tileSize, gp.tileSize*2);
           
	    	attackDown1 = setup("/monster/OrcAttackDown1", gp.tileSize, gp.tileSize);
	    	attackDown2 = setup("/monster/OrcAttackDown2", gp.tileSize, gp.tileSize);
	    	
	    	attackLeft1 = setup("/monster/OrcAttackLeft1", gp.tileSize*2, gp.tileSize);
	    	attackLeft2 = setup("/monster/OrcAttackLeft2", gp.tileSize*2, gp.tileSize);
	    	
	    	attackRight1 = setup("/monster/OrcAttackRight1", gp.tileSize, gp.tileSize);
	    	attackRight2 = setup("/monster/OrcAttackRight2", gp.tileSize, gp.tileSize);
           
	    }
	    
	    public void attacking() {
	    	spriteCounter++;  // Increment the sprite counter for animation frames

	        if (spriteCounter <= 5) {
	            spriteNum = 1;  // Use the first attack frame
	        }
	        if (spriteCounter > 5 && spriteCounter <= 25) {
	            spriteNum = 2;  // Use the second attack frame

	            // Save current position to revert after the animation
	            int currentWorldX = worldX;
	            int currentWorldY = worldY;
	           

	            // Adjust position based on direction for visual attack effect
	            switch (direction) {
	                case "up":
	                	attackArea.y = worldY - attackArea.height;
	                    attackArea.x = worldX;
	                    break;
	                case "down":
	                	attackArea.y = worldY + gp.tileSize;
	                    attackArea.x = worldX;
	                    break;
	                case "left":
	                	attackArea.x = worldX - attackArea.width;
	                    attackArea.y = worldY;
	                    break;
	                case "right":
	                	attackArea.x = worldX + gp.tileSize;
	                    attackArea.y = worldY;
	                    break;
	            }

	            // Check for player proximity and damage
	            int tileSize = gp.tileSize;
	            int orcCol = worldX / tileSize;
	            int orcRow = worldY / tileSize;
	            int playerCol = gp.player.worldX / tileSize;
	            int playerRow = gp.player.worldY / tileSize;

	            if (Math.abs(orcCol - playerCol) <= 1 && Math.abs(orcRow - playerRow) <= 1) {
//	                damagePlayer();  // Damage player when within 1 tile
	            }

	            // Revert to original position after animation
	            worldX = currentWorldX;
	            worldY = currentWorldY;
	        }

	        if (spriteCounter > 25) {
	            spriteNum = 1;  // Reset to the first sprite
	            spriteCounter = 0;  // Reset the sprite counter
	            attacking = false;  // End the attack
	        }
	    }
	    
	    int attackCoolDown = 0;
	    
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
	        else {onPath = false;}

	        // If within 1-tile radius, attack the player
	        if (deltaX <= 1 && deltaY <= 1) {
	            if (!attacking && attackCoolDown == 0) {
	            	Random random = new Random();
	            	int i = random.nextInt(100);
	            	if (i>95 && i<100)
	            	{
	                attacking = true;
	                spriteCounter = 0;  // Reset sprite counter for animation
	                direction = determineDirection(playerCol, playerRow);
	                damagePlayer(); 
	                attackCoolDown = 120;
	            	}
	            }
	            // Damage the player
	        } else {
	            attacking = false;  // Stop attacking if player is out of range
	        }
	    }

	    private String determineDirection(int playerCol, int playerRow) {
	        int orcCol = worldX / gp.tileSize;
	        int orcRow = worldY / gp.tileSize;

	        if (orcCol < playerCol) return "right";
	        if (orcCol > playerCol) return "left";
	        if (orcRow < playerRow) return "down";
	        if (orcRow > playerRow) return "up";
	        return direction;
	    }

	    private void damagePlayer() {
	        if (!gp.player.invincible) {
	            int damage = attack - gp.player.defense;
	            damage = Math.max(damage, 0);  // Ensure damage is not negative
	            gp.player.life -= damage;
	            gp.player.invincible = true;  // Player becomes temporarily invincible
	            gp.playSE(6);  // Play sound effect for attack (adjust as needed)
	        }
	    }


	    @Override
	    public void update() {
	    	if (attackCoolDown != 0)
	    	{
	    		attackCoolDown--;
	    	}
	        if (onPath) {
	        	 int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
	             int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

	             searchPath(goalCol, goalRow);  // Make orc follow the path
	        }
	        if (attacking) {
	            attacking();  // Handle attack animation
	        }
	        super.update();  // Normal update when not chasing or attacking
	    }


	    
	    public void setAction() {
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
	        
	        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
	            projectile.setMonster(worldX, worldY, direction, true, this);
	            //gp.projectileList.add(projectile);

	            // CHECK VACANCY
	            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
	                if (gp.projectile[gp.currentMap][ii] == null) {
	                    gp.projectile[gp.currentMap][ii] = projectile;
	                    break;
	                }
	            }
	            shotAvailableCounter = 0;
	        }
	        
	        checkAndChasePlayer();  
	    }

	    public void damageReaction() {
	        actionLockCounter = 0;
	        direction = gp.player.direction;
	    }

	    public void checkDrop() {
	        int i = new Random().nextInt(100) + 1;
	        if (i >= 0 && i < 35) {
	            dropItem(new OBJ_Coin_Bronze(gp,1));
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
	    }

}
