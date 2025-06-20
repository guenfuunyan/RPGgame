package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public abstract class Entity {
    public GamePanel gp;

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
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];
    public boolean isReversing = false;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean boss;
    public boolean orc;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int barWidth;
    public int barHeight;
    public int x;
    public int y;
    public double maxMana;
    public double mana;
    public int sizeRatio;
    public long lastManaRestoreTime;
    public int ammo;
    public boolean pierce;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int healingAmount;
    public int critPercent;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int id; //NPC id: id = 1 impact lore, id = 0 no impact
    public boolean thanhKiem;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile currentSkillI;
    public Projectile currentSkillF;
    public Projectile projectile;
    public WeaponProjectile projectileWeapon;
    
    //END GAME
    public int key = 0;

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public ArrayList<Entity> skillInventory = new ArrayList<>();
    public final int maxSkillInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public double useCost;
    public int price;
    public int knockBackPower = 0;
    public int range = 0;
    public boolean stand;
    public boolean stackable = false;
    public int amount = 1;

    // TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_skill = 8;
    public final int type_obstacle = 9;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public int getScreenX()//trong phan draw co screenX, chuyen ra day 
    {
    	int screenX = worldX - gp.player.worldX + gp.player.screenX;
    	return screenX;
    }
    public int getScreenY()
    {
    	int screenY = worldY - gp.player.worldY + gp.player.screenY;
    	return screenY;
    }
    
    
    public void setAction() {
    }

    public void damageReaction() {
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }
    
    public int getTileDistance(Entity target)
    {
    	int TileDistance =(getXdistance(target) + getYdistance(target));
    	return TileDistance;
    }
     
    public void interact() {
    	
    }
    public void use(Entity entity) {
    }

    public void checkDrop() {
    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {
        Color color = null;

        return color;
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

    public int getParticleSize() {
        int size = 0;

        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;

        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;

        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {
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

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }

    public void update() {
        if (knockBack == true) {
            checkCollision();
            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (collisionOn == false) {
                switch (gp.player.direction) {
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
            knockBackCounter++;

            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else {
            setAction();
            checkCollision();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
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
        }

        spriteCounter++;
        if (orc == false && boss == false) {
        if (spriteCounter > 26) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
        else if (orc == true)
        {
        	
        	 if (spriteCounter > 12) {
        	if(spriteNum == 1 && isReversing ==false) {
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
	    	}	
	    	else if(spriteNum == 2 && isReversing == true ) {
	    		spriteNum = 1;
	    		isReversing = false;
	    	}
        	spriteCounter = 0;
        	 }
        }
        else if (boss == true)
        {
        	 if (spriteCounter > 20) {
        	if(spriteNum == 1 && isReversing ==false) {
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
	    	}	
	    	else if(spriteNum == 5 && isReversing == false) {
	    		spriteNum = 6;
		    	}	
	    	else if(spriteNum == 6 && isReversing == false) {
	    		spriteNum = 7;
	    		isReversing = true;
		    	}	
	    	else if(spriteNum == 7 && isReversing == true ) {
    		spriteNum = 6;
	    	}
	    	else if(spriteNum == 6 && isReversing == true ) {
    		spriteNum = 5;
	    	}
	    	else if(spriteNum == 5 && isReversing == true ) {
	    		spriteNum = 4;
	    	}	
	    	else if(spriteNum == 4 && isReversing == true ) {
	    		spriteNum = 3;
	    	}
	    	else if(spriteNum == 3 && isReversing == true ) {
	    		spriteNum = 2;
	    	}
	    	else if(spriteNum == 2 && isReversing == true ) {
	    		spriteNum = 1;
	    		isReversing = false;
	    	}
        	spriteCounter = 0;
        	 }
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 10) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            // ADD DAMAGE
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    
    public boolean inCamera()
    {
    	boolean inCamera = false;
    	if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
    	{
    		inCamera = true;
    	}
    	return inCamera;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        
        int tempScreenY = getScreenY();
        int tempScreenX = getScreenX();

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        	
        	
        	if(orc == false && boss==false)
        	{
        	//vi tri ve
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }

                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (attacking == true) {
                    	 tempScreenY = getScreenY() - up1.getHeight();
                        if (spriteNum == 1) {
                            image = attackUp1;
                        }

                        if (spriteNum == 2) {
                            image = attackUp2;
                        }
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }

                    if (spriteNum == 2) {
                        image = down2;
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
                    if (spriteNum == 1) {
                        image = left1;
                    }

                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (attacking == true) {
                    	tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNum == 1) {
                            image = attackLeft1;
                        }

                        if (spriteNum == 2) {
                            image = attackLeft2;
                        }
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }

                    if (spriteNum == 2) {
                        image = right2;
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
       } 
        		else if (boss == true)
        	{
        			switch (direction) {
                    case "up":
                        if (spriteNum == 1) {
                            image = up1;
                        }

                        if (spriteNum == 2) {
                            image = up2;
                        }
                        if (spriteNum == 3) {
                            image = up3;
                        }
                        if (spriteNum == 4) {
                            image = up4;
                        }
                        if (spriteNum == 5) {
                            image = up5;
                        }
                        if (spriteNum == 6) {
                            image = up6;
                        }
                        if (spriteNum == 7) {
                            image = up7;
                        }
                        if (attacking == true) {
                        	 tempScreenY = getScreenY() - up1.getHeight();
                            if (spriteNum == 1) {
                                image = attackUp1;
                            }
                            if (spriteNum == 2) {
                                image = attackUp2;
                            }
                            if (spriteNum == 3) {
                                image = attackUp3;
                            }
                            if (spriteNum == 4) {
                                image = attackUp4;
                            }
                            if (spriteNum == 5) {
                                image = attackUp5;
                            }
                            if (spriteNum == 6) {
                                image = attackUp6;
                            }
                            if (spriteNum == 7) {
                                image = attackUp7;
                            }
                        }
                        break;
                    case "down":
                        if (spriteNum == 1) {
                            image = down1;
                        }
                        if (spriteNum == 2) {
                            image = down2;
                        }
                        if (spriteNum == 3) {
                            image = down3;
                        }
                        if (spriteNum == 4) {
                            image = down4;
                        }
                        if (spriteNum == 5) {
                            image = down5;
                        }
                        if (spriteNum == 6) {
                            image = down6;
                        }
                        if (spriteNum == 7) {
                            image = down7;
                        }
                        if (attacking == true) {
                            if (spriteNum == 1) {
                                image = attackDown1;
                            }

                            if (spriteNum == 2) {
                                image = attackDown2;
                            }
                            if (spriteNum == 3) {
                                image = attackDown3;
                            }

                            if (spriteNum == 4) {
                                image = attackDown4;
                            }
                            if (spriteNum == 5) {
                                image = attackDown5;
                            }

                            if (spriteNum == 6) {
                                image = attackDown6;
                            }
                            if (spriteNum == 7) {
                                image = attackDown7;
                            }
                        }
                        break;
                    case "left":
                        if (spriteNum == 1) {
                            image = left1;
                        }

                        if (spriteNum == 2) {
                            image = left2;
                        }
                        if (spriteNum == 3) {
                            image = left3;
                        }
                        if (spriteNum == 4) {
                            image = left4;
                        }
                        if (spriteNum == 5) {
                            image = left5;
                        }
                        if (spriteNum == 6) {
                            image = left6;
                        }
                        if (spriteNum == 7) {
                            image = left7;
                        }
                        if (attacking == true) {
                        	tempScreenX = getScreenX() - left1.getWidth();
                            if (spriteNum == 1) {
                                image = attackLeft1;
                            }

                            if (spriteNum == 2) {
                                image = attackLeft2;
                            }
                            if (spriteNum == 3) {
                                image = attackLeft3;
                            }

                            if (spriteNum == 4) {
                                image = attackLeft4;
                            }
                            if (spriteNum == 5) {
                                image = attackLeft5;
                            }

                            if (spriteNum == 6) {
                                image = attackLeft6;
                            }
                            if (spriteNum == 7) {
                                image = attackLeft7;
                            }
                        }
                        break;
                    case "right":
                        if (spriteNum == 1) {
                            image = right1;
                        }
                        if (spriteNum == 2) {
                            image = right2;
                        }
                        if (spriteNum == 3) {
                            image = right3;
                        }
                        if (spriteNum == 4) {
                            image = right4;
                        }
                        if (spriteNum == 5) {
                            image = right5;
                        }
                        if (spriteNum == 6) {
                            image = right6;
                        }
                        if (spriteNum == 7) {
                            image = right7;
                        }
                        if (attacking == true) {
                            if (spriteNum == 1) {
                                image = attackRight1;
                            }

                            if (spriteNum == 2) {
                                image = attackRight2;
                            }
                            if (spriteNum == 3) {
                                image = attackRight3;
                            }

                            if (spriteNum == 4) {
                                image = attackRight4;
                            }
                            if (spriteNum == 5) {
                                image = attackRight5;
                            }

                            if (spriteNum == 6) {
                                image = attackRight6;
                            }
                            if (spriteNum == 7) {
                                image = attackRight7;
                            }
                        }
                        break;
                }
        	}
        		else if (orc == true) {
			switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }

                if (spriteNum == 2) {
                    image = up2;
                }
                if (spriteNum == 3) {
                    image = up3;
                }
                if (spriteNum == 4) {
                    image = up4;
                }
                if (spriteNum == 5) {
                    image = up5;
                }
                if (spriteNum == 6) {
                    image = up6;
                }
                if (attacking == true) {
                	 tempScreenY = getScreenY() - up1.getHeight();
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                    if (spriteNum == 3) {
                        image = attackUp3;
                    }
                    if (spriteNum == 4) {
                        image = attackUp4;
                    }
                    if (spriteNum == 5) {
                        image = attackUp5;
                    }
                    if (spriteNum == 6) {
                        image = attackUp6;
                    }
                    if (spriteNum == 7) {
                        image = attackUp7;
                    }
                    if (spriteNum == 8) {
                        image = attackUp8;
                    }
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = down3;
                }
                if (spriteNum == 4) {
                    image = down4;
                }
                if (spriteNum == 5) {
                    image = down5;
                }
                if (spriteNum == 6) {
                    image = down6;
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }

                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                    if (spriteNum == 3) {
                        image = attackDown3;
                    }

                    if (spriteNum == 4) {
                        image = attackDown4;
                    }
                    if (spriteNum == 5) {
                        image = attackDown5;
                    }

                    if (spriteNum == 6) {
                        image = attackDown6;
                    }
                    if (spriteNum == 7) {
                        image = attackDown7;
                    }

                    if (spriteNum == 8) {
                        image = attackDown8;
                    }
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }

                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                if (spriteNum == 4) {
                    image = left4;
                }
                if (spriteNum == 5) {
                    image = left5;
                }
                if (spriteNum == 6) {
                    image = left6;
                }
                if (attacking == true) {
                	tempScreenX = getScreenX() - left1.getWidth();
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }

                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        image = attackLeft3;
                    }

                    if (spriteNum == 4) {
                        image = attackLeft4;
                    }
                    if (spriteNum == 5) {
                        image = attackLeft5;
                    }

                    if (spriteNum == 6) {
                        image = attackLeft6;
                    }
                    if (spriteNum == 7) {
                        image = attackLeft7;
                    }

                    if (spriteNum == 8) {
                        image = attackLeft8;
                    }
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
                }
                if (spriteNum == 4) {
                    image = right4;
                }
                if (spriteNum == 5) {
                    image = right5;
                }
                if (spriteNum == 6) {
                    image = right6;
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }

                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                    if (spriteNum == 3) {
                        image = attackRight3;
                    }

                    if (spriteNum == 4) {
                        image = attackRight4;
                    }
                    if (spriteNum == 5) {
                        image = attackRight5;
                    }

                    if (spriteNum == 6) {
                        image = attackRight6;
                    }
                    if (spriteNum == 7) {
                        image = attackRight7;
                    }

                    if (spriteNum == 8) {
                        image = attackRight8;
                    }
                    
                }
                break;
        }
        		}
	
            //Chuyen Monster health bar vao UI
            // MOSNTER HEALTH BAR

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
            }

            if (dying == true) {
                dyingAnimation(g2);
            }
            if (orc == false && boss == false) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            }
            else if (orc==true) {
            	g2.drawImage(image, tempScreenX+gp.tileSize, tempScreenY+gp.tileSize, null);
            }
            else if (boss == true) {
            	  
            	  g2.drawImage(image, tempScreenX-gp.tileSize, tempScreenY-gp.tileSize*2, null);
            }
            changeAlpha(g2, 1f);
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

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search() == true) {
            // NEXT WORLD X / Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // ENTITY SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // LEFT OR RIGHT
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // UP OR LEFT
                direction = "up";
                checkCollision();

                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // UP OR RIGHT
                direction = "up";
                checkCollision();

                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // DOWN OR LEFT
                direction = "down";
                checkCollision();

                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // DOWN OR RIGHT
                direction = "down";
                checkCollision();

                if (collisionOn == true) {
                    direction = "right";
                }
            }

            // IF REACH ThE GOAL, STOP THE SEARCH
            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;

            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }
    
    public int collisionFrameSizeX(int tilesize, int sizeratio, int solidarea ) {
    	return tilesize * sizeratio - solidarea*2;
    }
    
    public int collisionFrameSizeY(int tilesize, int sizeratio, int solidarea) {
    	return tilesize * sizeratio - solidarea*2;
    }
}
