package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import entity.object.*;
import entity.object.Object;
import main.GamePanel;
import main.KeyHandler;
import entity.monster.Monster;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean isReversing = false;

    // SPRITE IMAGES
    public BufferedImage up1, up2, up3, up4, up5,up6,up7,
            down1, down2,down3, down4, down5,down6,down7,
            left1, left2,left3, left4, left5,left6,left7,
            right1, right2,right3,right4,right5,right6,right7,
            appear;
    public BufferedImage attackUp1, attackUp2,attackUp3,attackUp4,attackUp5,attackUp6,attackUp7,attackUp8,
            attackDown1, attackDown2,attackDown3,attackDown4,attackDown5, attackDown6,attackDown7,attackDown8,
            attackLeft1, attackLeft2,attackLeft3,attackLeft4,attackLeft5, attackLeft6,attackLeft7,attackLeft8,
            attackRight1,attackRight2,attackRight3,attackRight4,attackRight5,attackRight6,attackRight7,attackRight8,
            slashup, slashdown;
    public boolean weaponProjectile;
    public BufferedImage image, image2, image3;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    String dialogues[] = new String[20];

    // STATE
    int dialogueIndex = 0;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean onPath = false;
    public boolean boss;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int defaultSpeed;
    public int maxLife;
    public int life;
    public int barWidth;
    public int barHeight;
    public double maxMana;
    public double mana;
    public long lastManaRestoreTime;
    public boolean pierce;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int id;
    public Object currentWeapon;
    public Object currentShield;
    public Projectile currentSkillI;
    public Projectile currentSkillF;
    public Projectile projectile;

    //END GAME
    public int dyingCounter = 0;

    // ITEM ATTRIBUTES
    public ArrayList<Object> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public ArrayList<Object> skillInventory = new ArrayList<>();
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public double useCost;
    public int price;
    public int knockBackPower = 0;

    // TYPE
    public int type;
    public final int type_player = 0;
    public int x;
    public int y;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // Gọi hàm khởi tạo của lớp cha (Entity)
        this.keyH = keyH;
        this.type = type_player;
        this.name = "Player";

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define collision area
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }


    public void setDefaultValues() {
        worldX = gp.tileSize * 21;
        worldY = gp.tileSize * 80;
        gp.currentMap = 0;
        defaultSpeed = 5;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 1000; // Maximum health
        barWidth = 200; // Width of the health bar
        barHeight = 25; // Height of the health bar
        x = 20; // X position of the health bar
        y = 20; // Y position of the health bar
        life = maxLife;
        maxMana = 12;
        mana = maxMana;
        range = 0;
        lastManaRestoreTime = 0;
        strength = 2;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 50;
        coin = 800;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        currentSkillI = new OBJ_Rock(gp);
        currentSkillF = new OBJ_Rock(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setDefaultPositionsForRestart() {
    	gp.currentMap = 0;
        worldX = gp.tileSize * 21;
        worldY = gp.tileSize * 80;
        direction = "down";
    }
    
    public void setDefaultPositionsForRetry() {
    	if (gp.checkPointChange == false) {
    		gp.currentMap = 0;
    		worldX = gp.tileSize * 21;
            worldY = gp.tileSize * 80;
            direction = "down";
    	}
    	else {
    		gp.currentMap = 0;
    		worldX = gp.tileSize * 81;
            worldY = gp.tileSize * 30;
            direction = "left";
    	}
    }

    public void restoreLifeAndMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
//        inventory.add(new WP_KiemKhoiNguyen(gp));
//        inventory.add(new WP_NgocLamBaoKiem(gp));
//        inventory.add(new WP_HanBangKiem(gp));
//        inventory.add(new WP_Duanhkiem(gp));
//        inventory.add(new WP_QuyKiem(gp));
//        inventory.add(new OBJ_Axe(gp));
//        inventory.add(new OBJ_Bug(gp));
        skillInventory.add(new OBJ_Plasma(gp));
//        skillInventory.add(new OBJ_Blueflame(gp));
        skillInventory.add(new OBJ_Darkmatter(gp));
//        skillInventory.add(new OBJ_Fireball(gp));
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength + currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense += currentShield.defenseValue;
    }

    public void getPlayerImage() {
    	down1 = setup("/player/BoyDown1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/BoyDown3", gp.tileSize, gp.tileSize);
        down3 = setup("/player/BoyDown2", gp.tileSize, gp.tileSize);
        down4 = setup("/player/BoyDown4", gp.tileSize, gp.tileSize);
        down5 = setup("/player/BoyDown5", gp.tileSize, gp.tileSize);
        
        up1 = setup("/player/BoyUp1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/BoyUp2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/BoyUp3", gp.tileSize, gp.tileSize);
        up4 = setup("/player/BoyUp4", gp.tileSize, gp.tileSize);
        up5 = setup("/player/BoyUp5", gp.tileSize, gp.tileSize);
        
        left1 = setup("/player/BoyLeft1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/BoyLeft2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/BoyLeft3", gp.tileSize, gp.tileSize);
        left4 = setup("/player/BoyLeft4", gp.tileSize, gp.tileSize);
        left5 = setup("/player/BoyLeft5", gp.tileSize, gp.tileSize);
        
        right1 = setup("/player/BoyRight1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/BoyRight2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/BoyRight3", gp.tileSize, gp.tileSize);
        right4 = setup("/player/BoyRight4", gp.tileSize, gp.tileSize);
        right5 = setup("/player/BoyRight5", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/BoyUpSlash1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/BoyUpSlash2", gp.tileSize, gp.tileSize*2 );
            attackUp3 = setup("/player/BoyUpSlash3", gp.tileSize, gp.tileSize*2 );
            attackUp4 = setup("/player/BoyUpSlash4", gp.tileSize, gp.tileSize*2 );
            attackDown1 = setup("/player/BoyDownSlash1", gp.tileSize, gp.tileSize );
            attackDown2 = setup("/player/BoyDownSlash2", gp.tileSize, gp.tileSize );
            attackDown3 = setup("/player/BoyDownSlash3", gp.tileSize, gp.tileSize );
            attackDown4 = setup("/player/BoyDownSlash4", gp.tileSize, gp.tileSize );
            attackLeft1 = setup("/player/BoyLeftSlash1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/BoyLeftSlash2", gp.tileSize*2, gp.tileSize);
            attackLeft3 = setup("/player/BoyLeftSlash3", gp.tileSize*2 , gp.tileSize);
            attackLeft4 = setup("/player/BoyLeftSlash4", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/BoyRightSlash1", gp.tileSize , gp.tileSize);
            attackRight2 = setup("/player/BoyRightSlash2", gp.tileSize , gp.tileSize);
            attackRight3 = setup("/player/BoyRightSlash3", gp.tileSize , gp.tileSize);
            attackRight4 = setup("/player/BoyRightSlash4", gp.tileSize , gp.tileSize);
            
        }

        if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/BoyUpAxe1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/BoyUpAxe2", gp.tileSize, gp.tileSize*2 );
            attackUp3 = setup("/player/BoyUpAxe1", gp.tileSize, gp.tileSize*2 );
            attackUp4 = setup("/player/BoyUpAxe2", gp.tileSize, gp.tileSize*2 );
            attackDown1 = setup("/player/BoyDownAxe1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/BoyDownAxe2", gp.tileSize, gp.tileSize*2);
            attackDown3 = setup("/player/BoyDownAxe1", gp.tileSize, gp.tileSize*2);
            attackDown4 = setup("/player/BoyDownAxe2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/BoyLeftAxe1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/BoyLeftAxe2", gp.tileSize*2, gp.tileSize);
            attackLeft3 = setup("/player/BoyLeftAxe1", gp.tileSize*2, gp.tileSize);
            attackLeft4 = setup("/player/BoyLeftAxe2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/BoyRightAxe1", gp.tileSize*2 , gp.tileSize);
            attackRight2 = setup("/player/BoyRightAxe2", gp.tileSize*2, gp.tileSize);
            attackRight3 = setup("/player/BoyRightAxe1", gp.tileSize*2, gp.tileSize);
            attackRight4 = setup("/player/BoyRightAxe2", gp.tileSize*2, gp.tileSize);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }

        if (dyingCounter > i * 8) {
            alive = false;
            if(this.boss == true) gp.bossAlive = false;
        }
    }

    public void update() {
        if (attacking == true) {
            attacking();
        } else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||
                keyH.rightPressed == true || keyH.JPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
            	direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            if (keyH.shift == true) {
		        speed = 8;
		        if(spriteCounter > 6) {
			    	if(spriteNum == 1 ) {
			    		spriteNum = 2;
			    	}
			    	else if(spriteNum == 2 && isReversing == false ) {
			    		spriteNum = 3;
			    	}
			    	else if(spriteNum == 3 && isReversing == false) {
			    		spriteNum = 4;
			    	}   
			    	else if(spriteNum == 4 && isReversing == false) {
		    		spriteNum = 5;
		    		isReversing = true;
			    	}	
			    	else if(spriteNum == 5 && isReversing == true) {
		    		spriteNum = 4;
			    	}
			    	else if(spriteNum == 4 && isReversing == true) {
		    		spriteNum = 3;
			    	}
			    	else if(spriteNum == 3 && isReversing == true) {
			    		spriteNum = 2;
			    		isReversing = false;
			    	}	
			    	spriteCounter = 0;
			    }
		    }else {
		    	speed = 5;
		    }
            if(keyH.upPressed == true && keyH.downPressed == true || keyH.leftPressed == true && keyH.rightPressed == true) {
				spriteNum = 1;
				speed = 0;
				isReversing = false;
				spriteCounter = 0;
			}
		    

		    if(keyH.buttonK == true) {
		    	speed = 0;
		    	spriteNum = 1;
		    	spriteCounter = 0;
		    }


            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false && keyH.JPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (keyH.JPressed == true && attackCanceled == false) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
                isReversing = false;
            }
            attackCanceled = false;

            gp.keyH.JPressed = false;
            

		    spriteCounter++;
		    if(spriteCounter > 12) {
		    	if(spriteNum == 1) {
		    		spriteNum = 2;
		    	}
		    	else if(spriteNum == 2 && isReversing == false ) {
		    		spriteNum = 3;
		    	}
		    	else if(spriteNum == 3 && isReversing == false) {
		    		spriteNum = 4;
		    	}   
		    	else if(spriteNum == 4 && isReversing == false) {
	    		spriteNum = 5;
	    		isReversing = true;
		    	}	
		    	else if(spriteNum == 5 && isReversing == true ) {
	    		spriteNum = 4;
		    	}
		    	else if(spriteNum == 4 && isReversing == true ) {
	    		spriteNum = 3;
		    	}
		    	else if(spriteNum == 3 && isReversing == true ) {
		    		spriteNum = 2;
		    		isReversing = false;
		    	}	
		    	spriteCounter = 0;
		    }
		}else {
			spriteNum = 1;
			isReversing = false;
		}
        if (keyH.buttonK) {

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastManaRestoreTime > 300) { // 200 ms delay
                if (gp.player.mana < gp.player.maxMana) {
                    gp.player.mana += 1;
                    if (gp.player.mana > gp.player.maxMana) {
                        gp.player.mana = gp.player.maxMana;
                    }
                }
                lastManaRestoreTime = currentTime;
            }
        }
        if (gp.keyH.IPressed == true && currentSkillI.alive == false && shotAvailableCounter == 20
                && currentSkillI.haveResource(this) == true) {
        	currentSkillI.set(worldX, worldY, direction, true, this);
            
            //Skill2
        	currentSkillI.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = currentSkillI;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(10);
           
            
        }
        
        if (gp.keyH.shotKeyPressed == true && currentSkillF.alive == false && shotAvailableCounter == 20
                && currentSkillF.haveResource(this) == true) {
        	currentSkillF.set(worldX, worldY, direction, true, this);
            
            //Skill1
        	currentSkillF.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = currentSkillF;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(10);
        }
        
        if (currentWeapon.weaponProjectile == true && gp.keyH.JPressed == true 
        		&& currentWeapon.projectileWeapon.alive == false && shotAvailableCounter == 20
        		&& currentWeapon.projectileWeapon.haveResource(this) == true) {
        	currentWeapon.projectileWeapon.set(worldX, worldY, direction, true, this);
            
            //WeaponSkill
        	currentWeapon.projectileWeapon.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.weaponProjectile[1].length; i++) {
                if (gp.weaponProjectile[gp.currentMap][i] == null) {
                    gp.weaponProjectile[gp.currentMap][i] = currentWeapon.projectileWeapon;
                    break;
                }
            }

            shotAvailableCounter = 0;
            gp.playSE(10);
        
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 20) {
            shotAvailableCounter++;
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }

        if (life <= 0 && gp.bossContact == false && gp.bossAlive == true) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }
        else if(life <=0 && gp.bossContact == true ) {
        	gp.endGameID = 1;
        	gp.newGameTimes++;
        	gp.gameState = gp.endGame;
        }
        else if(life > 0 && gp.bossContact == true && gp.bossAlive == false) {
        	gp.endGameID = 2;
        	gp.gameState = gp.endGame;
        }
    }
    


    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            
            // SAVE CURRENT worldX/Y & solidArea
            int currentworldX = worldX;
            int currentworldY = worldY;
//            int solidAreaWidth = solidArea.width;
//            int solidAreaHeight = solidArea.height;
//            int defaultSAW = solidArea.width;
//            int defaultSAH = solidArea.height;

            // ADJUST PLAYER worldX/Y FOR attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // CHANGE attackArea INTO solidArea
//            solidAreaWidth = attackArea.width;
//            solidAreaHeight = attackArea.height;
            
            
            

        

            // CHECK MONSTER COLLISION WITH THE UPDATED worldX/Y & solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            // RESTORE THE ORIGINAL DATA
            worldX = currentworldX;
            worldY = currentworldY;
            
            
//            solidArea.width = solidAreaWidth;
//            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } 
            else if (gp.obj[gp.currentMap][i].type == type_skill) {
                // SKILL INVENTORY ITEMS
                String text;

                    skillInventory.add(gp.obj[gp.currentMap][i]);
                    gp.playSE(1);
                    text = "Nhận được " + gp.obj[gp.currentMap][i].name + "!";

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
            //OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
            	if(keyH.JPressed == true) {
            		attackCanceled = true;
            		gp.obj[gp.currentMap][i].interact();
            	}
            }
            else {
            	String text;
            	
            	if (canObtainItem(gp.obj[gp.currentMap][i] ) == true) {
                    gp.playSE(1);
                    text = "Bạn nhận được " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "Túi đồ hết chỗ chứa";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.JPressed == true) {
            if (i != 999 && currentWeapon.key == 0) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
            if( i != 999 && currentWeapon.key == 1 && gp.npc[gp.currentMap][i].id == 1 ) {
            	gp.gameState = gp.endGame;
            	gp.endGameID = 3;
            }
        }
        
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack, int knockBackPower) {
        if (i != 999) {
            if (gp.monster[gp.currentMap][i].invincible == false) {
                gp.playSE(5);
                
                if(currentWeapon.healingAmount != 0) {
                	life += currentWeapon.healingAmount;
                }
                
                if(currentWeapon.critPercent !=0) {
                	int ran = new Random().nextInt(100)+1;
                	if(currentWeapon.critPercent<ran) attack = attack*2;
                }

                if (knockBackPower > 0 && boss == false) {
                    knockBack(gp.monster[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " sát thương!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Tiêu diệt " + gp.monster[gp.currentMap][i].name + "!");
                    int bonus = gp.monster[gp.currentMap][i].exp*4;
                    if(currentWeapon.thanhKiem == true) {
                    	gp.ui.addMessage("Kinh nghiệm + " + (gp.monster[gp.currentMap][i].exp + bonus));
                    	exp+= (gp.monster[gp.currentMap][i].exp + bonus);
                    } else {
                    	gp.ui.addMessage("Kinh nghiệm + " + gp.monster[gp.currentMap][i].exp);
                    	exp += gp.monster[gp.currentMap][i].exp;
                    }
                    checkLevelUp();
                }
            }
        }
    }

    public void knockBack(Monster monster, int knockBackPower) {
        if (boss == false)
        {
    monster.direction = direction;
    monster.speed += knockBackPower;
    monster.knockBack = true;
        }
        else if (boss == true)
        {
                monster.knockBack = false;
        }
    }

    public void generateParticle(Object generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
                && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true
                && gp.iTile[gp.currentMap][i].invincible == false) {
            gp.iTile[gp.currentMap][i].playSe();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Projectile projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
        	exp = exp - nextLevelExp;
        	level++;
            nextLevelExp += 20;
            maxLife += 4;
            maxMana	+= 4;
            life = maxLife;
            mana = maxMana;
            strength += 4;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Bạn vừa lên cấp " + level;
            checkLevelUp();
        }
    }

    public void selectInventoryItem() {
    	int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Object selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
           
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }

            if (selectedItem.type == type_consumable) {
            	    // Attempt to use the item
            	    selectedItem.use(this);
            	        // If successfully used, reduce the amount
            	        if (selectedItem.amount > 1) {
            	            selectedItem.amount--;
            	        } else {
            	            // If amount is 1 or less, remove the item from the inventory
            	            inventory.remove(itemIndex);
            	        }
            }
        }
    }
    
    public void selectSkillInventoryItem(int code) {
    	int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < skillInventory.size()) {
            Projectile selectedItem = (Projectile) skillInventory.get(itemIndex);

            if (selectedItem.type == type_skill && code == KeyEvent.VK_I) {
            	currentSkillI = selectedItem;
            }
            else if(selectedItem.type == type_skill && code == KeyEvent.VK_F) {
            	currentSkillF = selectedItem;
            }
        }
    }
    
    public int searchItemInInventory(String itemName) {
	   int itemIndex = 999;
	   for(int i = 0;i < inventory.size();i++) {
		   if(inventory.get(i).name.equals(itemName)) {
			   itemIndex = i;
			   break;
		   }
	   }
	   return itemIndex;
   }
   
   	public boolean canObtainItem(Object item) {
	   boolean canObtain = true;
	   
	   if(item.stackable == true) {
		   int index =searchItemInInventory(item.name);
		   if(index != 999) {
			   inventory.get(index).amount++;
			   canObtain = true;
		   }
		   else {
			   if(inventory.size() < maxInventorySize) {
				    inventory.add(item);
				    canObtain = true;
			   }
		   }
	   }
	   else {
		   if(inventory.size() < maxInventorySize) {
			    inventory.add(item);
			    canObtain = true;
	   }
	   }
		   return canObtain;
   }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        int centerX = gp.screenWidth/2;
        int centerY = gp.screenHeight/2;

        if(gp.OIndex == true ) {
            int radious = currentWeapon.range + 5;
            int diameter = radious*2 ;
            g2.setColor(Color.red);
            g2.drawOval(centerX-radious,centerY-radious, diameter, diameter);
        }

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum ==2) {
                        image = up2;
                    }
                    if(spriteNum == 3) {
                        image = up3;
                    }
                    if(spriteNum == 4) {
                        image = up4;
                    }
                    if(spriteNum == 5) {
                        image = up5;
                    }
                }

                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }

                    if (spriteNum == 2) {
                        image = attackUp2;
                    }

                }
                break;
            case "down":
                if (attacking == false) {
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    if(spriteNum == 3) {
                        image = down3;
                    }
                    if(spriteNum == 4) {
                        image = down4;
                    }
                    if(spriteNum == 5) {
                        image = down5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }

                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum ==2) {
                        image = left2;
                    }
                    if(spriteNum == 3) {
                        image = left3;
                    }
                    if(spriteNum == 4) {
                        image = left4;
                    }
                    if(spriteNum == 5) {
                        image = left5;
                    }
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }

                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    if(spriteNum == 3) {
                        image = right3;
                    }
                    if(spriteNum == 4) {
                        image = right4;
                    }
                    if(spriteNum == 5) {
                        image = right5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }

                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);




        // RESET
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public int getCenterX()
    {
        int centerX = worldX + right1.getWidth()/2;
        return centerX;
    }

    public int getCenterY()
    {
        int centerY = worldY +up1.getHeight()/2;
        return centerY;
    }
}