package entity.monster;

import java.util.Random;

import main.GamePanel;
import entity.object.OBJ_Coin_Bronze;
import entity.object.OBJ_Heart;
import entity.object.OBJ_Mana;
import entity.object.OBJ_RockBoss;

public class MON_GolemBoss extends Monster {
	 GamePanel gp;
	
	    private int projectileCount = 0; // Đếm số lượng projectile đã bắn trong chu kỳ
	    private final int MAX_PROJECTILES = 3; // Số lượng projectile tối đa mỗi lần bắn

	    public MON_GolemBoss(GamePanel gp) {
	        super(gp);

	        this.gp = gp;
        
	        type = type_monster;
	        name = "Golem";
	        boss = true;
	        defaultSpeed = 1;
	        speed = defaultSpeed;
	        maxLife = 500;
	        life = maxLife;
	        attack = 55;
	        defense = 16;
	        exp = 20;
	        projectile = new OBJ_RockBoss(gp);

	        solidArea.x = 0; // Đặt lại giá trị bắt đầu
	        solidArea.y = 0;
	        solidArea.width = gp.tileSize * 3; // Phù hợp với kích thước ảnh Zoom
	        solidArea.height = gp.tileSize * 3;
	        solidAreaDefaultX = solidArea.x;
	        solidAreaDefaultY = solidArea.y;
	        
	        
	        

	        getImage();
	        getAttackImage();
	        setAction();
	        checkAlive(life);
	    }

	    public void checkAlive(int life) {
			if(life <= 0) gp.bossAlive = false;
		}

		public void getImage() {
	    	int Zoom = 5;
	        up1 = setup("/monster/golem-walk-1", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up2 = setup("/monster/golem-walk-2", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up3 = setup("/monster/golem-walk-3", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up4 = setup("/monster/golem-walk-4", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up5 = setup("/monster/golem-walk-5", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up6 = setup("/monster/golem-walk-6", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        up7 = setup("/monster/golem-walk-7", gp.tileSize*Zoom, gp.tileSize*Zoom);
	     
	        down1 = setup("/monster/golem-walk-15", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down2 = setup("/monster/golem-walk-16", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down3 = setup("/monster/golem-walk-17", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down4 = setup("/monster/golem-walk-18", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down5 = setup("/monster/golem-walk-19", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down6 = setup("/monster/golem-walk-20", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        down7 = setup("/monster/golem-walk-21", gp.tileSize*Zoom, gp.tileSize*Zoom);
	       
	        
	        left1 = setup("/monster/golem-walk-8", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left2 = setup("/monster/golem-walk-9", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left3 = setup("/monster/golem-walk-10", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left4 = setup("/monster/golem-walk-11", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left5 = setup("/monster/golem-walk-12", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left6 = setup("/monster/golem-walk-13", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        left7 = setup("/monster/golem-walk-14", gp.tileSize*Zoom, gp.tileSize*Zoom);
	 
	        
	        right1 = setup("/monster/golem-walk-22", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right2 = setup("/monster/golem-walk-23", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right3 = setup("/monster/golem-walk-24", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right4 = setup("/monster/golem-walk-25", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right5 = setup("/monster/golem-walk-26", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right6 = setup("/monster/golem-walk-27", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        right7 = setup("/monster/golem-walk-28", gp.tileSize*Zoom, gp.tileSize*Zoom);
	        
	        
	    }
	    
	    public void getAttackImage()
	    {
	    	int Zoom = 5;
	    	attackUp1 = setup("/monster/golem-atk-1", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp2 = setup("/monster/golem-atk-2", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp3 = setup("/monster/golem-atk-3", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp4 = setup("/monster/golem-atk-4", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp5 = setup("/monster/golem-atk-5", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp6 = setup("/monster/golem-atk-6", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	attackUp7 = setup("/monster/golem-atk-7", gp.tileSize*Zoom, gp.tileSize*2*Zoom);
	    	
           
	    	attackDown1 = setup("/monster/golem-atk-15", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown2 = setup("/monster/golem-atk-16", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown3 = setup("/monster/golem-atk-17", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown4 = setup("/monster/golem-atk-18", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown5 = setup("/monster/golem-atk-19", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown6 = setup("/monster/golem-atk-20", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackDown7 = setup("/monster/golem-atk-21", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	
	    	
	    	attackLeft1 = setup("/monster/golem-atk-8", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft2 = setup("/monster/golem-atk-9", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft3 = setup("/monster/golem-atk-10", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft4 = setup("/monster/golem-atk-11", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft5 = setup("/monster/golem-atk-12", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft6 = setup("/monster/golem-atk-13", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	attackLeft7 = setup("/monster/golem-atk-14", gp.tileSize*2*Zoom, gp.tileSize*Zoom);
	    	
	    	
	    	attackRight1 = setup("/monster/golem-atk-22", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight2 = setup("/monster/golem-atk-23", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight3 = setup("/monster/golem-atk-24", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight4 = setup("/monster/golem-atk-25", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight5 = setup("/monster/golem-atk-26", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight6 = setup("/monster/golem-atk-27", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    	attackRight7 = setup("/monster/golem-atk-28", gp.tileSize*Zoom, gp.tileSize*Zoom);
	    }
	    
	    private void shootProjectile() {
	        if (projectileCount < MAX_PROJECTILES) {
	            projectile.setMonster(worldX, worldY, direction, true, this);
	            for (int i = 0; i < gp.projectile[1].length; i++) {
	                if (gp.projectile[gp.currentMap][i] == null) {
	                    gp.projectile[gp.currentMap][i] = projectile;
	                    break;
	                }
	            }
	            projectileCount++;
	        } else {
	            projectileCount = 0;
	        }
	    }
	    
	    public void moveTowardsPlayer() {
	        int playerX = gp.player.getCenterX();
	        int playerY = gp.player.getCenterY();
	        int golemX = getCenterX();
	        int golemY = getCenterY();

	        // Chỉ di chuyển trên một trục tại một thời điểm
	        if (golemX != playerX) {
	            // Di chuyển trên trục X
	            if (playerX > golemX) {
	                direction = "right";
	                worldX += speed; // Di chuyển sang phải
	            } else if (playerX < golemX) {
	                direction = "left";
	                worldX -= speed; // Di chuyển sang trái
	            }
	        } else if (golemY != playerY) {
	            // Di chuyển trên trục Y khi tọa độ X đã khớp
	            if (playerY > golemY) {
	                direction = "down";
	                worldY += speed; // Di chuyển xuống
	            } else if (playerY < golemY) {
	                direction = "up";
	                worldY -= speed; // Di chuyển lên
	            }
	        }
	    }
	    
	    public void attacking() {
	    	spriteCounter++;  // Increment the sprite counter for animation frames
	    	
	    	if (spriteCounter <= 5) {
	            spriteNum = 1;  // Use the first attack frame
	        }
	        if (spriteCounter > 5 && spriteCounter <= 10) {
	            spriteNum = 2;  // Use the second attack frame
	            }
	            if (spriteCounter > 15 && spriteCounter <= 20) {
		            spriteNum = 3; 
		            }
		            if (spriteCounter > 25 && spriteCounter <= 30) {
			            spriteNum = 4; 
			            }
			            if (spriteCounter > 35 && spriteCounter <= 40) {
				            spriteNum = 5; 
				            shootProjectile();				            }
				            if (spriteCounter >45  && spriteCounter <= 50) {
					            spriteNum = 6;  
					            }
					            if (spriteCounter > 55 && spriteCounter <= 60) {
						            spriteNum = 7;
							    	int golemX = getCenterX();
							        int golemY = getCenterY();
							        int playerX = gp.player.getCenterX();
							        int playerY = gp.player.getCenterY();

							        double distance = Math.sqrt(Math.pow((golemX - playerX) / gp.tileSize, 2) 
							                                  + Math.pow((golemY - playerY) / gp.tileSize, 2));
							        if(distance < 3) {
							        damagePlayer();
							        }
						            attackCoolDown = 45;
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

	            // Revert to original position after animation
	            worldX = currentWorldX;
	            worldY = currentWorldY;
	        }

	        if (spriteCounter > 60) {
	            spriteNum = 1;  // Reset to the first sprite
	            spriteCounter = 0;  // Reset the sprite counter
	            attacking = false; 
	            isReversing = false;// End the attack
	        }
	    }
	    
	    int attackCoolDown = 0;
	    

	    private void checkATK(int rate, int straight, int horizontal) {
			boolean targetInRange = false; 
			int xDis = Math.abs(getCenterX()-gp.player.worldX);
			int yDis = Math.abs(getCenterY()-gp.player.worldY);
			
			
			switch (direction) {
			case "up": {
				if (gp.player.getCenterY() < getCenterY() && yDis <straight && xDis < horizontal)
				{
					targetInRange = true;
				}
				break;
			}
			case "down": {
				if (gp.player.getCenterY() > getCenterY() && yDis <straight && xDis < horizontal)
				{
					targetInRange = true;
				}
				break;
			}
			case "left": {
				if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal)
				{
					targetInRange = true;
				}
				break;
			}
			case "right": {
				if (gp.player.getCenterX() > getCenterX() && xDis <straight && xDis < horizontal)
				{
					targetInRange = true;
				}	
				break;
			}}
			if (targetInRange == true)
			{
				attacking = true;
				spriteCounter = 0;
				spriteNum = 1;
				shotAvailableCounter = 0;
//	            direction = determineDirection(playerX / gp.tileSize, playerY / gp.tileSize);
	            attackCoolDown = 120; 
			}		
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
//	    	int golemX = getCenterX();
//	        int golemY = getCenterY();
//	        int playerX = gp.player.getCenterX();
//	        int playerY = gp.player.getCenterY();
//
//	        // Tính khoảng cách Euclidean giữa Golem và Player
//	        double distance = Math.sqrt(Math.pow((golemX - playerX) / gp.tileSize, 2) 
//	                                  + Math.pow((golemY - playerY) / gp.tileSize, 2));
//
//	        // Nếu trong phạm vi từ 3 đến 7 tile
//	        if (distance > 3 && distance <= 7) {
//	            moveTowardsPlayer(); // Golem di chuyển về phía Player
//	        } 
//	        // Nếu trong phạm vi <= 3 tile, Golem tấn công
//	        else if (distance <= 3) {
//	            if (!attacking && attackCoolDown == 0) {
//	                attacking = true;
//	                spriteCounter = 0; // Reset sprite để bắt đầu hoạt ảnh tấn công
//	                direction = determineDirection(playerX / gp.tileSize, playerY / gp.tileSize);
//	                attackCoolDown = 120; // Reset thời gian hồi chiêu
//	            }
//	        }
	        if (attacking) {
	            attacking();
	            return;// Handle attack animation
	        }
	        super.update();  // Normal update when not chasing or attacking
	    }

	    
	    
	    public void setAction() {
	        
	        
	    	int golemX = getCenterX();
	        int golemY = getCenterY();
	        int playerX = gp.player.getCenterX();
	        int playerY = gp.player.getCenterY();

	        double distance = Math.sqrt(Math.pow((golemX - playerX) / gp.tileSize, 2) 
	                                  + Math.pow((golemY - playerY) / gp.tileSize, 2));
	        if (distance > 3 && distance <= 7) {
	            moveTowardsPlayer();
	        } else if (distance <3)
	        {
	        	if (attackCoolDown == 0)
	        	{
	        		attacking = true;
	        	}
	        	else {
	        		attacking = false;
	        	}
	        }
	        {
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
	        if (attacking == false )
	        {
	        	checkATK(15, gp.tileSize, gp.tileSize);
	        }
	        int i = new Random().nextInt(100) + 1;
	        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
	            projectile.setMonster(worldX, worldY, direction, true, this);
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

	    public void damageReaction() {
	        actionLockCounter = 0;
	    }

	    public void checkDrop() {
	        int i = new Random().nextInt(100) + 1;
	        if (i >= 0 && i < 35) {
	            dropItem(new OBJ_Coin_Bronze(gp, i));
	        }
	        if (i >= 35 && i < 60) {
	            dropItem(new OBJ_Heart(gp));
	        }
	        if (i >= 60 && i < 85) {
	            dropItem(new OBJ_Mana(gp));
	        }
	    }
}
