package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.SuperObject;
import skill.Skill;
import util.GameImage;
import util.Item;
import util.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private KeyHandler keyH;
    private boolean isAttacking;
    private int attackCooldown;
    private int currentAttackCooldown;
    private int hasKey;
    private List<Item> inventory;
    private List<String> skills;
    private int speedBoost = 0; // Tăng tốc từ OBJ_Boots
    private int speedBoostDuration = 0; // Thời gian hiệu lực của tốc độ tăng (frames)

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp, 1, "Player", new Vector2(gp.tileSize * 5, gp.tileSize * 5),
                new GameImage("/player/down1.png", gp.tileSize, gp.tileSize), 100, 100, 20, 10, 4);
        this.keyH = keyH;
        this.hasKey = 0;
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        this.isAttacking = false;
        this.attackCooldown = 60; // 1 giây tại 60 FPS
        this.currentAttackCooldown = 0;
        this.skills = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    @Override
    public void update() {
        if (!isActive || gp == null) return;

        // Cập nhật cooldown tấn công
        if (currentAttackCooldown > 0) {
            currentAttackCooldown--;
            if (currentAttackCooldown <= 0) {
                isAttacking = false;
            }
        }

        // Cập nhật thời gian hiệu lực của tốc độ tăng
        if (speedBoostDuration > 0) {
            speedBoostDuration--;
            if (speedBoostDuration <= 0) {
                resetSpeed();
            }
        }

        // Xử lý di chuyển
        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
            String direction = "down";
            if (keyH.isUpPressed()) direction = "up";
            else if (keyH.isDownPressed()) direction = "down";
            else if (keyH.isLeftPressed()) direction = "left";
            else if (keyH.isRightPressed()) direction = "right";

            moveWithCollision(direction);
        }

        // Xử lý tấn công
        if (keyH.isSpacePressed() && currentAttackCooldown <= 0) {
            isAttacking = true;
            currentAttackCooldown = attackCooldown;
            attackNearbyEnemy();
            gp.ui.showMessage("Player attacked!");
        }

        // Xử lý tương tác khi nhấn phím E
        if (keyH.isInteractPressed()) {
            interactWithNearbyItems();
            interactWithNearbyObjects();
        }

        // Cập nhật kỹ năng
        updateSkills(1);
    }

    private void moveWithCollision(String direction) {
        this.direction = direction;
        int prevWorldX = worldX;
        int prevWorldY = worldY;

        move(direction);

        collisionOn = false;
        if (gp.cChecker != null) {
            gp.cChecker.checkTile(this);
        }

        if (!collisionOn && gp.enemies != null) {
            for (Enemy enemy : gp.enemies) {
                if (enemy != null && enemy.isActive() && gp.cChecker.checkEntity(this, enemy)) {
                    collisionOn = true;
                    break;
                }
            }
        }

        if (!collisionOn && gp.objects != null) {
            for (SuperObject obj : gp.objects) {
                if (obj != null && obj.isCollision() && gp.cChecker.checkObject(this, obj) != 0) {
                    collisionOn = true;
                    break;
                }
            }
        }

        if (collisionOn) {
            worldX = prevWorldX;
            worldY = prevWorldY;
        }

        syncWorldPosition();
    }

    public void move(String direction) {
        switch (direction) {
            case "up": worldY -= speed + speedBoost; break;
            case "down": worldY += speed + speedBoost; break;
            case "left": worldX -= speed + speedBoost; break;
            case "right": worldX += speed + speedBoost; break;
        }
    }

    public void syncWorldPosition() {
        // Đồng bộ vị trí thế giới với vị trí màn hình
    }

    private void attackNearbyEnemy() {
        if (gp == null || gp.enemies == null) return;

        for (Enemy enemy : gp.enemies) {
            if (enemy != null && enemy.isActive()) {
                int dx = enemy.getWorldX() - worldX;
                int dy = enemy.getWorldY() - worldY;
                int distanceSquared = dx * dx + dy * dy;
                int attackRangeSquared = 50 * 50;

                boolean inDirection = false;
                switch (direction) {
                    case "up": inDirection = dy < 0 && Math.abs(dy) > Math.abs(dx); break;
                    case "down": inDirection = dy > 0 && Math.abs(dy) > Math.abs(dx); break;
                    case "left": inDirection = dx < 0 && Math.abs(dx) > Math.abs(dy); break;
                    case "right": inDirection = dx > 0 && Math.abs(dx) > Math.abs(dy); break;
                }

                if (distanceSquared <= attackRangeSquared && inDirection) {
                    attack(enemy);
                    break;
                }
            }
        }
    }

    private void interactWithNearbyItems() {
        if (gp == null || gp.items == null) return;
        for (int i = gp.items.size() - 1; i >= 0; i--) {
            Item item = gp.items.get(i);
            if (item != null && gp.cChecker.checkItem(this, item)) {
                inventory = inventory != null ? inventory : new ArrayList<>();
                inventory.add(item);
                gp.ui.showMessage("Picked up: " + item.getName());

                if ("Health".equals(item.getType())) {
                    heal(item.getEffectValue());
                    gp.ui.showMessage("Healed for " + item.getEffectValue() + " HP. Current HP: " + hp);
                }

                gp.items.remove(i);
            }
        }
    }

    private void interactWithNearbyObjects() {
        if (gp == null || gp.objects == null) return;
        for (int i = 0; i < gp.objects.size(); i++) {
            SuperObject obj = gp.objects.get(i);
            if (obj != null && gp.cChecker.checkObject(this, obj) != 0) {
                if (obj instanceof OBJ_Door) {
                    OBJ_Door door = (OBJ_Door) obj;
                    if (door.isLocked()) {
                        Item keyItem = null;
                        for (Item item : inventory) {
                            if ("Key".equals(item.getType())) {
                                keyItem = item;
                                break;
                            }
                        }
                        if (keyItem != null) {
                            door.unlock();
                            inventory.remove(keyItem);
                            hasKey = Math.max(0, hasKey - 1);
                            gp.ui.showMessage("Door unlocked! Keys remaining: " + hasKey);
                            if (!door.isLocked()) {
                                gp.objects.set(i, null);
                            }
                        } else {
                            gp.ui.showMessage("Need a key to unlock!");
                        }
                    }
                } else {
                    Item item = obj.interact();
                    if (item != null) {
                        inventory = inventory != null ? inventory : new ArrayList<>();
                        inventory.add(item);
                        gp.ui.showMessage("Picked up: " + item.getName());

                        if ("Health".equals(item.getType())) {
                            heal(item.getEffectValue());
                            gp.ui.showMessage("Healed for " + item.getEffectValue() + " HP. Current HP: " + hp);
                        } else if ("Key".equals(item.getType())) {
                            hasKey++;
                            gp.ui.showMessage("Obtained a Key! Total keys: " + hasKey);
                        } else if (obj instanceof OBJ_Boots) {
                            applyBootsEffect(((OBJ_Boots) obj).getSpeedBoost()); // Sửa từ getHealAmount() thành getSpeedBoost()
                        }

                        gp.objects.set(i, null);
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null && image.isLoaded()) {
            BufferedImage sprite = image.getBufferedImage();
            if (sprite != null) {
                if (isAttacking) {
                    g2.setColor(Color.RED);
                    g2.fillRect(screenX - 5, screenY - 5, gp.tileSize + 10, gp.tileSize + 10);
                }
                g2.drawImage(sprite, screenX, screenY, image.getWidth(), image.getHeight(), null);
            } else {
                g2.setColor(Color.RED);
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
                System.err.println("⚠️ Player sprite is null: " + (image.getSourcePath() != null ? image.getSourcePath() : "unknown path"));
            }
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            System.err.println("⚠️ Player image failed to load or is null: " + (image == null ? "image is null" : "not loaded"));
        }
    }

    // Phương thức bổ sung: Áp dụng hiệu ứng từ OBJ_Boots
    public void applyBootsEffect(int boostAmount) {
        speedBoost = boostAmount;
        speedBoostDuration = 60 * 10; // 10 giây tại 60 FPS
        gp.ui.showMessage("Speed increased by " + boostAmount + " for 10 seconds!");
    }

    // Phương thức bổ sung: Reset tốc độ khi hết hiệu lực
    public void resetSpeed() {
        speedBoost = 0;
        speedBoostDuration = 0;
        gp.ui.showMessage("Speed boost has worn off!");
    }

    // Phương thức bổ sung: Áp dụng hiệu ứng từ vật phẩm
    public void applyItemEffect(Item item) {
        if (item != null) {
            if ("Health".equals(item.getType())) {
                heal(item.getEffectValue());
                gp.ui.showMessage("Healed for " + item.getEffectValue() + " HP. Current HP: " + hp);
            } else if ("Boots".equals(item.getType())) {
                applyBootsEffect(item.getEffectValue());
            }
        }
    }

    // Phương thức bổ sung: Nhận sát thương từ kẻ địch
    public void takeDamage(int damage) {
        if (isActive) {
            hp = Math.max(0, hp - damage);
            gp.ui.showMessage("Player took " + damage + " damage! Current HP: " + hp);
            if (hp <= 0) {
                isActive = false;
                gp.ui.showMessage("Player defeated! Game Over!");
                gp.ui.setGameFinished(true);
            }
        }
    }

    // Phương thức bổ sung: Kiểm tra trạng thái sống/chết
    public boolean isAlive() {
        return isActive && hp > 0;
    }

    // Phương thức bổ sung: Hồi sinh người chơi
    public void revive() {
        isActive = true;
        hp = maxHp;
        gp.ui.showMessage("Player revived! HP restored to " + maxHp);
    }

    // Phương thức bổ sung: Thêm kỹ năng
    public void addSkill(String skillName) {
        if (!skills.contains(skillName)) {
            skills.add(skillName);
            gp.ui.showMessage("Learned skill: " + skillName);
        }
    }

    // Phương thức bổ sung: Kích hoạt kỹ năng
    public void activateSkill(String skillName) {
        if (skills.contains(skillName)) {
            gp.ui.showMessage("Activated skill: " + skillName);
            // Logic cho từng kỹ năng (cần bổ sung Skill class)
        } else {
            gp.ui.showMessage("Skill not available: " + skillName);
        }
    }

    // Phương thức bổ sung: Kiểm tra kỹ năng
    public boolean hasSkill(String skillName) {
        return skills.contains(skillName);
    }

    // Phương thức bổ sung: Hiển thị trạng thái
    public void displayStatus() {
        if (gp.ui != null) {
            String status = "HP: " + hp + "/" + maxHp + ", Keys: " + hasKey + ", Speed: " + (speed + speedBoost);
            gp.ui.showMessage(status);
        }
    }

    // Phương thức bổ sung: Reset trạng thái người chơi
    public void resetPlayer() {
        isActive = true;
        hp = maxHp;
        speedBoost = 0;
        speedBoostDuration = 0;
        hasKey = 0;
        if (inventory != null) inventory.clear();
        if (skills != null) skills.clear();
        gp.ui.showMessage("Player reset!");
    }

    // Getter và Setter
    public int getHasKey() { return hasKey; }
    public void setHasKey(int hasKey) { this.hasKey = Math.max(0, hasKey); }
    public boolean isAttacking() { return isAttacking; }
    public void setAttacking(boolean attacking) { this.isAttacking = attacking; }
    public int getAttackCooldown() { return attackCooldown; }
    public void setAttackCooldown(int attackCooldown) { this.attackCooldown = attackCooldown; }
    public int getCurrentAttackCooldown() { return currentAttackCooldown; }
    public void setCurrentAttackCooldown(int currentAttackCooldown) { this.currentAttackCooldown = currentAttackCooldown; }
    public List<Item> getInventory() { return inventory; }
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }
}