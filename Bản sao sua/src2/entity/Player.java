package entity;

import item.*;
import main.GamePanel;
import main.KeyHandler;
import quest.Quest;
import skill.Skill;
import util.GameImage;
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
    private int speedBoost;
    private int speedBoostDuration;
    private int enemyKills;
    private double criticalChance; // Xác suất chí mạng (0.0 đến 1.0)
    private int attackRange; // Phạm vi tấn công
    private double manaCostReduction; // Giảm chi phí mana (0.0 đến 1.0)
    private List<Quest> quests; // Danh sách nhiệm vụ

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp, 1, "Player", new Vector2(gp.tileSize * 5, gp.tileSize * 5),
                new GameImage("/player/boy_down_1.png", gp.tileSize, gp.tileSize), 100, 100, 100, 100, 20, 10, 4);
        this.keyH = keyH;
        this.hasKey = 0;
        this.isAttacking = false;
        this.attackCooldown = 60; // 1 giây tại 60 FPS
        this.currentAttackCooldown = 0;
        this.speedBoost = 0;
        this.speedBoostDuration = 0;
        this.enemyKills = 0;
        this.criticalChance = 0.0; // Mặc định
        this.attackRange = 50; // Mặc định
        this.manaCostReduction = 0.0; // Mặc định
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
        updateSkills(1.0f / 60.0f); // Giả định 60 FPS, deltaTime = 1/60 giây
    }

    private void moveWithCollision(String direction) {
        this.setDirection(direction);
        int prevWorldX = getWorldX();
        int prevWorldY = getWorldY();

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
            for (Item obj : gp.objects) {
                if (obj != null && gp.cChecker.checkObject(this, obj) != 0) {
                    collisionOn = true;
                    break;
                }
            }
        }

        if (collisionOn) {
            setWorldX(prevWorldX);
            setWorldY(prevWorldY);
        }
    }

    @Override
    public void move(String direction) {
        switch (direction) {
            case "up": setWorldY(getWorldY() - (getSpeed() + speedBoost)); break;
            case "down": setWorldY(getWorldY() + (getSpeed() + speedBoost)); break;
            case "left": setWorldX(getWorldX() - (getSpeed() + speedBoost)); break;
            case "right": setWorldX(getWorldX() + (getSpeed() + speedBoost)); break;
        }
    }

    private void attackNearbyEnemy() {
        if (gp == null || gp.enemies == null) return;

        for (Enemy enemy : gp.enemies) {
            if (enemy != null && enemy.isActive()) {
                int dx = enemy.getWorldX() - getWorldX();
                int dy = enemy.getWorldY() - getWorldY();
                int distanceSquared = dx * dx + dy * dy;
                int attackRangeSquared = attackRange * attackRange;

                boolean inDirection = false;
                switch (getDirection()) {
                    case "up": inDirection = dy < 0 && Math.abs(dy) > Math.abs(dx); break;
                    case "down": inDirection = dy > 0 && Math.abs(dy) > Math.abs(dx); break;
                    case "left": inDirection = dx < 0 && Math.abs(dx) > Math.abs(dy); break;
                    case "right": inDirection = dx > 0 && Math.abs(dx) > Math.abs(dy); break;
                }

                if (distanceSquared <= attackRangeSquared && inDirection) {
                    attack(enemy); // Gọi attack() từ Character, tăng enemyKills khi enemy chết
                    break;
                }
            }
        }
    }

    @Override
    public void attack(Character target) {
        if (target == null || !target.isActive()) return;
        int damage = Math.max(0, getAttackPower() - target.getDefense());
        // Áp dụng criticalChance
        if (Math.random() < criticalChance) {
            damage *= 2; // Gây sát thương gấp đôi khi chí mạng
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage(getName() + " landed a critical hit on " + target.getName() + " for " + damage + " damage!");
            }
        }
        target.setHp(Math.max(0, target.getHp() - damage));
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(getName() + " attacked " + target.getName() + " for " + damage + " damage!");
        }
        if (target.getHp() <= 0) {
            target.die();
            if (target instanceof Enemy) {
                enemyKills++;
                if (gp.ui != null) {
                    gp.ui.showMessage("Enemy defeated! Total kills: " + enemyKills);
                }
            }
        }
    }

    private void interactWithNearbyItems() {
        if (gp == null || gp.items == null) return;
        for (int i = gp.items.size() - 1; i >= 0; i--) {
            Item item = gp.items.get(i);
            if (item != null && gp.cChecker.checkItem(this, item)) {
                getInventory().add(item);
                gp.ui.showMessage("Picked up: " + item.getName());

                if ("Potion".equals(item.getType()) && item instanceof Potion) {
                    Potion potion = (Potion) item;
                    applyItemEffect(potion);
                    gp.items.remove(i);
                    gp.ui.showMessage("Used " + potion.getPotionType() + " Potion!");
                } else if ("Health".equals(item.getType())) {
                    heal(item.getEffectValue());
                    gp.ui.showMessage("Healed for " + item.getEffectValue() + " HP. Current HP: " + getHp());
                    gp.items.remove(i);
                }

                gp.items.remove(i);
            }
        }
    }

    private void interactWithNearbyObjects() {
        if (gp == null || gp.objects == null) return;
        for (int i = 0; i < gp.objects.size(); i++) {
            Item obj = gp.objects.get(i);
            if (obj != null && gp.cChecker.checkObject(this, obj) != 0) {
                if (obj instanceof Door) {
                    Door door = (Door) obj;
                    if (door.isLocked()) {
                        Item keyItem = null;
                        for (Item item : getInventory()) {
                            if ("Key".equals(item.getType()) && item instanceof KeyItem) {
                                KeyItem key = (KeyItem) item;
                                if (key.canUnlock(door)) {
                                    keyItem = item;
                                    break;
                                }
                            }
                        }
                        if (keyItem != null) {
                            KeyItem key = (KeyItem) keyItem;
                            if (key.useKey(this, door)) {
                                getInventory().remove(keyItem);
                                hasKey = Math.max(0, hasKey - 1);
                                if (!door.isLocked()) {
                                    gp.objects.set(i, null);
                                }
                            }
                        } else {
                            gp.ui.showMessage("Need a matching key to unlock!");
                        }
                    }
                } else if (obj instanceof Boots) {
                    getInventory().add(obj);
                    gp.ui.showMessage("Picked up: " + obj.getName());
                    applyItemEffect(obj);
                    gp.objects.set(i, null);
                } else {
                    getInventory().add(obj);
                    gp.ui.showMessage("Picked up: " + obj.getName());
                    applyItemEffect(obj);
                    gp.objects.set(i, null);
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getImage() != null && getImage().isLoaded()) {
            BufferedImage sprite = getImage().getBufferedImage();
            if (sprite != null) {
                if (isAttacking) {
                    g2.setColor(Color.RED);
                    g2.fillRect(getScreenX() - 5, getScreenY() - 5, gp.tileSize + 10, gp.tileSize + 10);
                }
                g2.drawImage(sprite, getScreenX(), getScreenY(), getImage().getWidth(), getImage().getHeight(), null);
            } else {
                g2.setColor(Color.RED);
                g2.fillRect(getScreenX(), getScreenY(), gp.tileSize, gp.tileSize);
                System.err.println("⚠️ Player sprite is null: " + (getImage().getSourcePath() != null ? getImage().getSourcePath() : "unknown path"));
            }
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX(), getScreenY(), gp.tileSize, gp.tileSize);
            System.err.println("⚠️ Player image failed to load or is null: " + (getImage() == null ? "image is null" : "not loaded"));
        }
    }

    public void applyBootsEffect(int boostAmount, int duration) {
        speedBoost = boostAmount;
        speedBoostDuration = duration;
        gp.ui.showMessage("Speed increased by " + boostAmount + " for " + (duration / 60) + " seconds!");
    }

    public void resetSpeed() {
        speedBoost = 0;
        speedBoostDuration = 0;
        gp.ui.showMessage("Speed boost has worn off!");
    }

    public void applyItemEffect(Item item) {
        if (item != null) {
            if ("Potion".equals(item.getType()) && item instanceof Potion) {
                Potion potion = (Potion) item;
                potion.applyItemEffect(this);
            } else if ("Health".equals(item.getType())) {
                heal(item.getEffectValue());
                gp.ui.showMessage("Healed for " + item.getEffectValue() + " HP. Current HP: " + getHp());
            } else if ("Boots".equals(item.getType()) && item instanceof Boots) {
                applyItemEffect(item);
            } else if ("Weapon".equals(item.getType()) && item instanceof Weapon) {
                item.applyItemEffect(this);
            } else if ("Shield".equals(item.getType()) && item instanceof Shield) {
                item.applyItemEffect(this);
            } else if ("Armor".equals(item.getType()) && item instanceof Armor) {
                item.applyItemEffect(this);
            } else if ("Scroll".equals(item.getType()) && item instanceof Scroll) {
                item.applyItemEffect(this);
            } else if ("Key".equals(item.getType()) && item instanceof KeyItem) {
                item.applyItemEffect(this);
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (isActive) {
            setHp(Math.max(0, getHp() - damage));
            gp.ui.showMessage("Player took " + damage + " damage! Current HP: " + getHp());
            if (getHp() <= 0) {
                isActive = false;
                gp.ui.showMessage("Player defeated! Game Over!");
                gp.ui.setGameFinished(true);
            }
        }
    }

    public boolean isAlive() {
        return isActive && getHp() > 0;
    }

    public void revive() {
        setActive(true);
        setHp(getMaxHp());
        setMana(getMaxMana());
        gp.ui.showMessage("Player revived! HP restored to " + getMaxHp() + ", Mana restored to " + getMaxMana());
    }

    public void addSkill(Skill skill) {
        if (!getSkills().contains(skill)) {
            getSkills().add(skill);
            gp.ui.showMessage("Learned skill: " + skill.getName());
        }
    }

    public void activateSkill(String skillName) {
        for (Skill skill : getSkills()) {
            if (skill.getName().equals(skillName)) {
                Character target = null;
                if (skillName.equals("Fireball")) {
                    target = findNearestEnemy();
                } else {
                    target = this;
                }

                skill.use(this, target);
                return;
            }
        }
        gp.ui.showMessage("Skill not available: " + skillName);
    }

    public Character findNearestEnemy() {
        if (gp == null || gp.enemies == null) return null;

        Character nearestEnemy = null;
        double minDistanceSquared = Double.MAX_VALUE;
        int skillRangeSquared = 100 * 100;

        for (Enemy enemy : gp.enemies) {
            if (enemy != null && enemy.isActive()) {
                int dx = enemy.getWorldX() - getWorldX();
                int dy = enemy.getWorldY() - getWorldY();
                double distanceSquared = dx * dx + dy * dy;

                if (distanceSquared <= skillRangeSquared && distanceSquared < minDistanceSquared) {
                    minDistanceSquared = distanceSquared;
                    nearestEnemy = enemy;
                }
            }
        }
        return nearestEnemy;
    }

    public boolean hasSkill(String skillName) {
        for (Skill skill : getSkills()) {
            if (skill.getName().equals(skillName)) {
                return true;
            }
        }
        return false;
    }

    public void displayStatus() {
        if (gp.ui != null) {
            String status = "HP: " + getHp() + "/" + getMaxHp() + ", Mana: " + getMana() + "/" + getMaxMana() +
                    ", Keys: " + hasKey + ", Speed: " + (getSpeed() + speedBoost) + ", Kills: " + enemyKills +
                    ", Crit Chance: " + (criticalChance * 100) + "%, Attack Range: " + attackRange +
                    ", Mana Cost Red: " + (manaCostReduction * 100) + "%";
            gp.ui.showMessage(status);
        }
    }

    public void resetPlayer() {
        setActive(true);
        setHp(getMaxHp());
        setMana(getMaxMana());
        speedBoost = 0;
        speedBoostDuration = 0;
        hasKey = 0;
        getInventory().clear();
        getSkills().clear();
        enemyKills = 0;
        criticalChance = 0.0;
        attackRange = 50;
        manaCostReduction = 0.0;
        gp.ui.showMessage("Player reset!");
    }

    // Phương thức getQuests
    public List<Quest> getQuests() {
        return new ArrayList<>(quests); // Trả về bản sao để bảo vệ danh sách gốc
    }

    public void setQuests(List<Quest> quests) {
        this.quests = new ArrayList<>(quests != null ? quests : new ArrayList<>());
    }

    // Phương thức để thêm một nhiệm vụ mới
    public void addQuest(Quest quest) {
        if (quest != null) {
            quests.add(quest);
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage("New quest added: " + quest.getName());
            }
        }
    }

    // Getters và Setters
    public KeyHandler getKeyH() { return keyH; }
    public int getHasKey() { return hasKey; }
    public void setHasKey(int hasKey) { this.hasKey = Math.max(0, hasKey); }
    public boolean isAttacking() { return isAttacking; }
    public void setAttacking(boolean attacking) { this.isAttacking = attacking; }
    public int getAttackCooldown() { return attackCooldown; }
    public void setAttackCooldown(int attackCooldown) { this.attackCooldown = attackCooldown; }
    public int getCurrentAttackCooldown() { return currentAttackCooldown; }
    public void setCurrentAttackCooldown(int currentAttackCooldown) { this.currentAttackCooldown = currentAttackCooldown; }
    public int getSpeedBoost() { return speedBoost; }
    public void setSpeedBoost(int speedBoost) { this.speedBoost = speedBoost; }
    public int getSpeedBoostDuration() { return speedBoostDuration; }
    public void setSpeedBoostDuration(int speedBoostDuration) { this.speedBoostDuration = speedBoostDuration; }
    public int getEnemyKills() { return enemyKills; }
    public double getCriticalChance() { return criticalChance; }
    public void setCriticalChance(double criticalChance) { this.criticalChance = Math.max(0.0, Math.min(criticalChance, 1.0)); }
    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int attackRange) { this.attackRange = attackRange > 0 ? attackRange : 50; }
    public double getManaCostReduction() { return manaCostReduction; }
    public void setManaCostReduction(double manaCostReduction) { this.manaCostReduction = Math.max(0.0, Math.min(manaCostReduction, 1.0)); }
}