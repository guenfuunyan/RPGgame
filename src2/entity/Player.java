package entity;

import item.*;
import main.GamePanel;
import quest.Quest;
import skill.Skill;
import util.Animation;
import util.GameImage;
import util.Vector2;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Player extends Character {
    // Thuộc tính chiến đấu
    private boolean attacking;
    private int attackCooldown;
    private int currentAttackCooldown;
    private int attackRange;
    private double criticalChance;
    private double blockChance;

    // Thuộc tính ma thuật
    private double manaCostReduction;

    // Thuộc tính tăng tốc
    private int speedBoost;
    private int speedBoostTimer;

    // Thuộc tính nhiệm vụ và vật phẩm
    private int hasKey;
    private List<Quest> quests;
    private int killCount;

    protected List<Skill> skills;
    protected int maxSkillSlots;

    // Các thuộc tính animation chỉ dành cho Player
    private Animation idleUpAnim;
    private Animation idleDownAnim;
    private Animation idleLeftAnim;
    private Animation idleRightAnim;
    private Animation walkUpAnim;
    private Animation walkDownAnim;
    private Animation walkLeftAnim;
    private Animation walkRightAnim;
    private Animation attackUpAnim;
    private Animation attackDownAnim;
    private Animation attackLeftAnim;
    private Animation attackRightAnim;
    private Animation readyUpAnim;
    private Animation readyDownAnim;
    private Animation readyLeftAnim;
    private Animation readyRightAnim;

    public Player(GamePanel gp, int id, String name, Vector2 position, GameImage image,
                 int hp, int maxHp, int mana, int maxMana, int attackPower, int defense, int speed) {
        super(gp, id, name, position, image, hp, maxHp, mana, maxMana, attackPower, defense, speed);
        initializeStats();
        initializeAnimations();
    }

    private void initializeAnimations() {
        // IDLE animations (đứng yên) - mỗi hướng 1 frame
        GameImage[] idleDown = new GameImage[] {
                new GameImage("/player/idle_down.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] idleUp = new GameImage[] {
                new GameImage("/player/idle_up.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] idleLeft = new GameImage[] {
                new GameImage("/player/idle_left.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] idleRight = new GameImage[] {
                new GameImage("/player/idle_right.png", gp.tileSize, gp.tileSize)
        };

        idleDownAnim = new Animation(idleDown, 10, true);
        idleUpAnim = new Animation(idleUp, 10, true);
        idleLeftAnim = new Animation(idleLeft, 10, true);
        idleRightAnim = new Animation(idleRight, 10, true);

        // WALKING animations (đi bộ) - mỗi hướng 4 frame
        GameImage[] walkDown = new GameImage[] {
                new GameImage("/player/walk_down_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_down_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_down_3.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_down_4.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] walkUp = new GameImage[] {
                new GameImage("/player/walk_up_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_up_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_up_3.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_up_4.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] walkLeft = new GameImage[] {
                new GameImage("/player/walk_left_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_left_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_left_3.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_left_4.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] walkRight = new GameImage[] {
                new GameImage("/player/walk_right_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_right_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_right_3.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/walk_right_4.png", gp.tileSize, gp.tileSize)
        };

        walkDownAnim = new Animation(walkDown, 10, true);
        walkUpAnim = new Animation(walkUp, 10, true);
        walkLeftAnim = new Animation(walkLeft, 10, true);
        walkRightAnim = new Animation(walkRight, 10, true);

        // READY animations (chuẩn bị tấn công) - mỗi hướng 2 frame
        GameImage[] readyDown = new GameImage[] {
                new GameImage("/player/ready_down_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/ready_down_2.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] readyUp = new GameImage[] {
                new GameImage("/player/ready_up_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/ready_up_2.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] readyLeft = new GameImage[] {
                new GameImage("/player/ready_left_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/ready_left_2.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] readyRight = new GameImage[] {
                new GameImage("/player/ready_right_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/ready_right_2.png", gp.tileSize, gp.tileSize)
        };

        readyDownAnim = new Animation(readyDown, 15, true);
        readyUpAnim = new Animation(readyUp, 15, true);
        readyLeftAnim = new Animation(readyLeft, 15, true);
        readyRightAnim = new Animation(readyRight, 15, true);

        // ATTACKING animations (đang tấn công) - mỗi hướng 3 frame
        GameImage[] attackDown = new GameImage[] {
                new GameImage("/player/attack_down_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_down_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_down_3.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] attackUp = new GameImage[] {
                new GameImage("/player/attack_up_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_up_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_up_3.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] attackLeft = new GameImage[] {
                new GameImage("/player/attack_left_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_left_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_left_3.png", gp.tileSize, gp.tileSize)
        };
        GameImage[] attackRight = new GameImage[] {
                new GameImage("/player/attack_right_1.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_right_2.png", gp.tileSize, gp.tileSize),
                new GameImage("/player/attack_right_3.png", gp.tileSize, gp.tileSize)
        };

        // Tấn công nhanh hơn các animation khác (frameDelay = 8)
        attackDownAnim = new Animation(attackDown, 8, false);
        attackUpAnim = new Animation(attackUp, 8, false);
        attackLeftAnim = new Animation(attackLeft, 8, false);
        attackRightAnim = new Animation(attackRight, 8, false);
    }

    @Override
    protected void updateAnimation() {
        Animation currentAnim = getCurrentAnimation();
        if (currentAnim != null) {
            currentAnim.update();
        }
    }

    private Animation getCurrentAnimation() {
        switch (state) {
            case "idle":
                switch (direction) {
                    case "up": return idleUpAnim;
                    case "down": return idleDownAnim;
                    case "left": return idleLeftAnim;
                    case "right": return idleRightAnim;
                }
                break;
            case "walking":
                switch (direction) {
                    case "up": return walkUpAnim;
                    case "down": return walkDownAnim;
                    case "left": return walkLeftAnim;
                    case "right": return walkRightAnim;
                }
                break;
            case "ready":
                switch (direction) {
                    case "up": return readyUpAnim;
                    case "down": return readyDownAnim;
                    case "left": return readyLeftAnim;
                    case "right": return readyRightAnim;
                }
                break;
            case "attacking":
                switch (direction) {
                    case "up": return attackUpAnim;
                    case "down": return attackDownAnim;
                    case "left": return attackLeftAnim;
                    case "right": return attackRightAnim;
                }
                break;
        }
        return null;
    }

    @Override
    protected GameImage getCurrentAnimationFrame() {
        Animation currentAnim = getCurrentAnimation();
        return currentAnim != null ? currentAnim.getCurrentFrame() : super.image;
    }


    private void initializeStats() {
        // Khởi tạo thuộc tính chiến đấu
        this.attacking = false;
        this.attackCooldown = 60;
        this.currentAttackCooldown = 0;
        this.attackRange = 50;
        this.criticalChance = 0.0;
        this.blockChance = 0.0;

        // Khởi tạo thuộc tính ma thuật
        this.manaCostReduction = 0.0;

        // Khởi tạo thuộc tính tăng tốc
        this.speedBoost = 0;
        this.speedBoostTimer = 0;

        // Khởi tạo thuộc tính nhiệm vụ và vật phẩm
        this.hasKey = 0;
        this.quests = new ArrayList<>();
        this.killCount = 0;
    }

    @Override
    public void update() {
        if (!isActive || gp == null) return;

        updateCooldowns();
        updateSpeedBoost();
        updateSkills(1.0f / 60.0f);
        syncWorldPosition();
    }

    private void updateCooldowns() {
        if (currentAttackCooldown > 0) {
            currentAttackCooldown--;
        }
    }

    private void updateSpeedBoost() {
        if (speedBoostTimer > 0) {
            speedBoostTimer--;
            if (speedBoostTimer <= 0) {
                removeSpeedBoost();
            }
        }
    }

    @Override
    protected void performAttack(Character target) {
        if (!canAttack(target)) return;

        attacking = true;
        currentAttackCooldown = attackCooldown;

        int damage = calculateDamage(target);
        target.takeDamage(damage);

        // Hiển thị thông báo tấn công
        showAttackMessage(target, damage);

        // Kiểm tra và xử lý tiêu diệt
        if (target.getHp() <= 0) {
            handleKill(target);
        }

        attacking = false;
    }

    @Override
    protected int calculateDamage(Character target) {
        int damage = super.calculateDamage(target);
        
        // Tính toán sát thương chí mạng
        if (Math.random() < criticalChance) {
            damage *= 2;
            if (gp.ui != null) {
                gp.ui.showMessage("Đòn đánh chí mạng!");
            }
        }
        
        return damage;
    }

    private boolean canAttack(Character target) {
        if (target == null || !isActive || currentAttackCooldown > 0) return false;
        
        // Kiểm tra khoảng cách
        int distanceSquared = calculateDistanceSquared(target);
        if (distanceSquared > attackRange * attackRange) {
            return false;
        }
        
        return true;
    }

    private void showAttackMessage(Character target, int damage) {
        if (gp.ui != null) {
            gp.ui.showMessage(name + " tấn công " + target.getName() + " gây " + damage + " sát thương!");
        }
    }

    private void handleKill(Character target) {
        target.die();
        killCount++;
        updateQuestProgress();
    }

    @Override
    public void takeDamage(int damage) {
        // Kiểm tra khả năng đỡ đòn
        if (Math.random() < blockChance) {
            if (gp.ui != null) {
                gp.ui.showMessage(name + " đã đỡ được đòn tấn công!");
            }
            return;
        }

        super.takeDamage(damage);
    }

    // Xử lý kỹ năng
    @Override
    public void useSkill(String skillName, Character target) {
        if (!canUseSkill(skillName, target)) return;

        Skill skill = findSkill(skillName);
        if (skill != null) {
            useSkillWithMana(skill, target);
        }
    }

    private boolean canUseSkill(String skillName, Character target) {
        return isActive && skillName != null && target != null;
    }

    private Skill findSkill(String skillName) {
        return skills.stream()
                .filter(s -> s.getName().equalsIgnoreCase(skillName) && s.canUse())
                .findFirst()
                .orElse(null);
    }

    private void useSkillWithMana(Skill skill, Character target) {
        int manaCost = calculateManaCost(skill);
        if (mana >= manaCost) {
            mana -= manaCost;
            skill.use(this, target);
            showSkillMessage(skill, target);
        } else {
            showInsufficientManaMessage(skill);
        }
    }

    private int calculateManaCost(Skill skill) {
        return (int)(skill.getManaCost() * (1.0 - manaCostReduction));
    }

    // Xử lý vật phẩm
    public void interactWithItem(Item item) {
        if (item == null || !isActive || gp == null) return;

        if (item instanceof Door) {
            handleDoorInteraction((Door) item);
        } else {
            handleItemPickup(item);
        }
    }

    private void handleDoorInteraction(Door door) {
        if (door.isLocked()) {
            if (hasKey > 0) {
                tryUnlockDoor(door);
            } else {
                showMessage("Cần có chìa khóa để mở cửa này!");
            }
        } else {
            showMessage("Cửa đã được mở khóa!");
        }
    }

    private void tryUnlockDoor(Door door) {
        for (Item item : inventory) {
            if (item instanceof KeyItem) {
                KeyItem key = (KeyItem) item;
                if (key.canUnlock(door) && key.useKey(this, door)) {
                    inventory.remove(key);
                    return;
                }
            }
        }
        showMessage("Không có chìa khóa phù hợp!");
    }

    private void handleItemPickup(Item item) {
        inventory.add(item);
        applyItemEffect(item);
        showMessage("Nhặt được " + item.getName());
    }

    public void applyItemEffect(Item item) {
        if (item != null) {
            item.applyItemEffect(this);
            if (item instanceof Chest && ((Chest) item).isOpened()) {
                showMessage("Đã mở rương!");
            }
        }
    }

    // Xử lý tăng tốc
    public void applySpeedBoost(int increase, int duration) {
        removeSpeedBoost();
        speedBoost = increase;
        setSpeed(getSpeed() + speedBoost);
        speedBoostTimer = duration;
        showMessage("Tốc độ tăng " + increase + " trong " + (duration / 60) + " giây!");
    }

    private void removeSpeedBoost() {
        if (speedBoost > 0) {
            setSpeed(getSpeed() - speedBoost);
            speedBoost = 0;
            showMessage("Hiệu ứng tăng tốc đã hết!");
        }
    }

    // Xử lý nhiệm vụ
    private void updateQuestProgress() {
        for (Quest quest : quests) {
            quest.updateProgress(this);
        }
    }

    public void addQuest(Quest quest) {
        if (quest != null && !quests.contains(quest)) {
            quests.add(quest);
            showMessage("Nhận nhiệm vụ mới: " + quest.getName());
        }
    }

    public void removeQuest(Quest quest) {
        if (quests.remove(quest)) {
            showMessage("Hoàn thành nhiệm vụ: " + quest.getName());
        }
    }

    // Tiện ích
    private int calculateDistanceSquared(Character target) {
        int dx = target.getWorldX() - worldX;
        int dy = target.getWorldY() - worldY;
        return dx * dx + dy * dy;
    }

    private void showMessage(String message) {
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(message);
        }
    }

    private void showSkillMessage(Skill skill, Character target) {
        showMessage(name + " sử dụng " + skill.getName() + " vào " + target.getName());
    }

    private void showInsufficientManaMessage(Skill skill) {
        showMessage("Không đủ mana để sử dụng " + skill.getName());
    }

    @Override
    public void draw(Graphics2D g2) {
        if (gp == null) return;
        
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        if (image != null && image.isLoaded()) {
            image.draw(g2, screenX, screenY);
        } else {
            g2.setColor(Color.BLUE);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    // Getters và Setters
    public boolean isAttacking() { return attacking; }
    public void setAttacking(boolean attacking) { this.attacking = attacking; }
    public int getAttackCooldown() { return attackCooldown; }
    public void setAttackCooldown(int cooldown) { this.attackCooldown = Math.max(1, cooldown); }
    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int range) { this.attackRange = Math.max(1, range); }
    public double getManaCostReduction() { return manaCostReduction; }
    public void setManaCostReduction(double value) { this.manaCostReduction = Math.min(1.0, Math.max(0.0, value)); }
    public double getCriticalChance() { return criticalChance; }
    public void setCriticalChance(double value) { this.criticalChance = Math.min(1.0, Math.max(0.0, value)); }
    public double getBlockChance() { return blockChance; }
    public void setBlockChance(double value) { this.blockChance = Math.min(1.0, Math.max(0.0, value)); }
    public int getHasKey() { return hasKey; }
    public void setHasKey(int value) { this.hasKey = Math.max(0, value); }
    public List<Quest> getQuests() { return new ArrayList<>(quests); }
    public int getKillCount() { return killCount; }
    public void resetKillCount() { this.killCount = 0; }
}