package entity.monster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;
import entity.object.Object;
import entity.object.Projectile;

public class Monster extends Entity {

    // SPRITE IMAGES
    public BufferedImage up1, up2, up3, up4, up5, up6, up7,
    down1, down2, down3, down4, down5, down6, down7,
    left1, left2, left3, left4, left5, left6, left7,
    right1, right2, right3, right4, right5, right6, right7,
    appear;
    
    // ATTACK IMAGES
    public BufferedImage attackUp1, attackUp2, attackUp3, attackUp4, attackUp5, attackUp6, attackUp7, attackUp8,
    attackDown1, attackDown2, attackDown3, attackDown4, attackDown5, attackDown6, attackDown7, attackDown8,
    attackLeft1, attackLeft2, attackLeft3, attackLeft4, attackLeft5, attackLeft6, attackLeft7, attackLeft8,
    attackRight1, attackRight2, attackRight3, attackRight4, attackRight5, attackRight6, attackRight7, attackRight8,
    slashup, slashdown;

    // COLLISION AND AREAS - Giữ lại attackArea vì không có trong Entity
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean collision = false;
    public boolean isReversing = false;

    // MONSTER STATES
    public int spriteNum = 1;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean boss = false;
    public boolean orc = false;

    // COUNTERS
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
    public int maxLife;
    public int life;
    public int attack;
    public int defense;
    public int exp;
    public int knockBackPower = 0;
    public int sizeRatio = 1;

    public Monster(GamePanel gp) {
        super(gp);
        this.type = type_monster;
    }

    // Override getCenterX và getCenterY để sử dụng sprite image thay vì tileSize
    @Override
    public int getCenterX() {
        int centerX = worldX + right1.getWidth()/2;
        return centerX;
    }

    @Override
    public int getCenterY() {
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }

    // Override getTileDistance với logic khác (không chia cho tileSize)
    public int getTileDistance(Monster target) {
        int TileDistance = (getXdistance(target) + getYdistance(target));
        return TileDistance;
    }

    // CHASE BEHAVIOR
    public void checkAndChasePlayer() {
        int tileSize = gp.tileSize;

        // Calculate tile positions for monster and player
        int monsterCol = worldX / tileSize;
        int monsterRow = worldY / tileSize;
        int playerCol = gp.player.worldX / tileSize;
        int playerRow = gp.player.worldY / tileSize;

        // Calculate distance in tiles
        int deltaX = Math.abs(monsterCol - playerCol);
        int deltaY = Math.abs(monsterRow - playerRow);

        // If within 5-tile radius, chase the player
        if (deltaX <= 5 && deltaY <= 5) {
            onPath = true;  // Use pathfinding to move towards the player
            gp.pFinder.setNodes(monsterCol, monsterRow, playerCol, playerRow);
            gp.pFinder.search();
        } else {
            onPath = false;
        }
    }

    // MONSTER BEHAVIOR
    public void setAction() {
        // Base implementation từ MON_Cucda - có thể override trong subclass
        checkAndChasePlayer();
        if(onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                java.util.Random random = new java.util.Random();
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

            // Projectile shooting logic
            if (projectile != null) {
                int i = new java.util.Random().nextInt(100) + 1;

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
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    // PARTICLE GENERATION
    public Color getParticleColor() {
        Color color = new Color(255, 0, 0); // Màu đỏ cho monster
        return color;
    }

    public int getParticleSize() {
        int size = 6;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

    // ITEM DROP
    public void checkDrop() {
        // Override trong subclass - base implementation
    }

    public void dropItem(Object droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    // Overload để drop Projectile
    public void dropItem(Projectile droppedProjectile) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedProjectile;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    // COLLISION CHECK
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

    // UPDATE METHOD
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

        // SPRITE ANIMATION
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
        } else if (orc == true) {
            if (spriteCounter > 12) {
                if(spriteNum == 1 && isReversing == false) {
                    spriteNum = 2;
                } else if(spriteNum == 2 && isReversing == false) {
                    spriteNum = 3;
                } else if(spriteNum == 3 && isReversing == false) {
                    spriteNum = 4;
                } else if(spriteNum == 4 && isReversing == false) {
                    spriteNum = 5;
                    isReversing = true;
                } else if(spriteNum == 5 && isReversing == true) {
                    spriteNum = 4;
                } else if(spriteNum == 4 && isReversing == true) {
                    spriteNum = 3;
                } else if(spriteNum == 3 && isReversing == true) {
                    spriteNum = 2;
                } else if(spriteNum == 2 && isReversing == true) {
                    spriteNum = 1;
                    isReversing = false;
                }
                spriteCounter = 0;
            }
        } else if (boss == true) {
            if (spriteCounter > 20) {
                if(spriteNum == 1 && isReversing == false) {
                    spriteNum = 2;
                } else if(spriteNum == 2 && isReversing == false) {
                    spriteNum = 3;
                } else if(spriteNum == 3 && isReversing == false) {
                    spriteNum = 4;
                } else if(spriteNum == 4 && isReversing == false) {
                    spriteNum = 5;
                } else if(spriteNum == 5 && isReversing == false) {
                    spriteNum = 6;
                } else if(spriteNum == 6 && isReversing == false) {
                    spriteNum = 7;
                    isReversing = true;
                } else if(spriteNum == 7 && isReversing == true) {
                    spriteNum = 6;
                } else if(spriteNum == 6 && isReversing == true) {
                    spriteNum = 5;
                } else if(spriteNum == 5 && isReversing == true) {
                    spriteNum = 4;
                } else if(spriteNum == 4 && isReversing == true) {
                    spriteNum = 3;
                } else if(spriteNum == 3 && isReversing == true) {
                    spriteNum = 2;
                } else if(spriteNum == 2 && isReversing == true) {
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

    // DAMAGE PLAYER
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

    // DYING ANIMATION
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


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int tempScreenY = getScreenY();
        int tempScreenX = getScreenX();

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            if(orc == false && boss == false) {
                // Quái thường - 2 frame animation
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
            else if (boss == true) {
                // Boss - 7 frame animation
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
                // Orc - 6 frame di chuyển, 8 frame tấn công
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

            // Xử lý health bar khi bị tấn công
            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
            }

            // Xử lý dying animation
            if (dying == true) {
                dyingAnimation(g2);
            }

            // Vẽ monster với vị trí khác nhau tùy loại
            if (orc == false && boss == false) {
                g2.drawImage(image, tempScreenX, tempScreenY, null);
            }
            else if (orc == true) {
                g2.drawImage(image, tempScreenX + gp.tileSize, tempScreenY + gp.tileSize, null);
            }
            else if (boss == true) {
                g2.drawImage(image, tempScreenX - gp.tileSize, tempScreenY - gp.tileSize * 2, null);
            }

            // Reset alpha về bình thường
            changeAlpha(g2, 1f);
        }
    }

    // PATHFINDING
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search() == true) {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
        }
    }

    // UTILITY METHODS
    public int collisionFrameSizeX(int tilesize, int sizeratio, int solidarea) {
        return tilesize * sizeratio - solidarea * 2;
    }

    public int collisionFrameSizeY(int tilesize, int sizeratio, int solidarea) {
        return tilesize * sizeratio - solidarea * 2;
    }
}