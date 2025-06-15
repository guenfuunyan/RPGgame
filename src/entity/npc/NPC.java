package entity.npc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.object.Object;
import entity.object.WeaponProjectile;
import main.GamePanel;

public abstract class NPC extends Entity {
    // SPRITE IMAGES
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage image, image2, image3;

    // DIALOGUE
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    // POSITION AND MOVEMENT
    public int spriteNum = 1;
    public int defaultSpeed;

    // STATE
    public boolean alive = true;
    public boolean dying = false;
    public boolean invincible = false;
    public boolean onPath = false;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;

    // NPC ATTRIBUTES
    public String name;
    public int maxLife;
    public int life;
    public int id; // NPC id: id = 1 impact lore, id = 0 no impact

    // INVENTORY AND ITEMS - ĐÃ CẬP NHẬT CHO drawNPCInventory
    public ArrayList<Object> inventory = new ArrayList<>(); // Đổi từ Entity sang Object
    public final int maxInventorySize = 20;
    public int value;
    public String description = "";
    public int price;
    public boolean stand = true; // NPCs usually stand in place

    // THUỘC TÍNH BỔ SUNG CHO INVENTORY DISPLAY
    public int attackValue = 0;
    public int defenseValue = 0;
    public int range = 0;
    public int knockBackPower = 0;
    public boolean weaponProjectile = false;
    public WeaponProjectile projectileWeapon;
    public int amount = 1;

    // EQUIPMENT ATTRIBUTES (nếu NPC có thể trang bị)
    public Object currentWeapon;
    public Object currentShield;
    public BufferedImage appear;


    public NPC(GamePanel gp) {
        super(gp);
        type = type_npc;
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 1;
        defaultSpeed = speed;
        direction = "down";
        alive = true;
        stand = true;
    }

    public void setAction() {
        // Default NPC behavior - can be overridden in specific NPC classes
        if (stand == false) {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                // Change direction randomly every 2 seconds (120 frames at 60 FPS)
                int i = (int)(Math.random() * 100) + 1;

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

    public void speak() {
        // Face the player when speaking
        facePlayer();

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void facePlayer() {
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

    public void interact() {
        speak();
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        gp.cChecker.checkPlayer(this);
    }

    public void update() {
        setAction();

        if (stand == false) {
            checkCollision();

            // IF COLLISION IS FALSE, NPC CAN MOVE
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

        // Sprite animation
        spriteCounter++;
        if (spriteCounter > 26) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
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

            // NPC chỉ có logic đơn giản như quái thường (orc == false && boss == false)
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Vẽ NPC tại vị trí chuẩn (như quái thường)
            g2.drawImage(image, tempScreenX, tempScreenY, null);

            changeAlpha(g2, 1f);
        }
    }


    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search() == true) {
            // NEXT WORLD X / Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // NPC SOLID AREA POSITION
            int npcLeftX = worldX + solidArea.x;
            int npcRightX = worldX + solidArea.x + solidArea.width;
            int npcTopY = worldY + solidArea.y;
            int npcBottomY = worldY + solidArea.y + solidArea.height;

            if (npcTopY > nextY && npcLeftX >= nextX && npcRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (npcTopY < nextY && npcLeftX >= nextX && npcRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (npcTopY >= nextY && npcBottomY < nextY + gp.tileSize) {
                // LEFT OR RIGHT
                if (npcLeftX > nextX) {
                    direction = "left";
                }
                if (npcLeftX < nextX) {
                    direction = "right";
                }
            } else if (npcTopY > nextY && npcLeftX > nextX) {
                // UP OR LEFT
                direction = "up";
                checkCollision();

                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (npcTopY > nextY && npcLeftX < nextX) {
                // UP OR RIGHT
                direction = "up";
                checkCollision();

                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (npcTopY < nextY && npcLeftX > nextX) {
                // DOWN OR LEFT
                direction = "down";
                checkCollision();

                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (npcTopY < nextY && npcLeftX < nextX) {
                // DOWN OR RIGHT
                direction = "down";
                checkCollision();

                if (collisionOn == true) {
                    direction = "right";
                }
            }
        }
    }
}