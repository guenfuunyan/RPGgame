package entity;

import item.Item;
import main.GamePanel;
import skill.Skill;
import util.GameImage;
import util.Vector2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AI.AIController;
import AI.AIState;

public class Enemy extends Character {
    private float aggressionLevel; // Mức độ hung hãn (0.0 đến 1.0)
    private List<Item> lootTable; // Bảng vật phẩm rơi khi chết
    private int staggerTime; // Thời gian choáng (frame)
    private int staggerTimer; // Bộ đếm thời gian choáng
    private int detectionRange; // Phạm vi phát hiện người chơi (pixel)
    private Character target; // Mục tiêu (thường là Player)
    private AIController aiController; // Điều khiển AI
    private float powerMultiplier; // Hệ số tăng sức mạnh (mặc định 1.0)
    private Skill specialSkill; // Kỹ năng đặc biệt (mặc định null)
    private List<Enemy> minions; // Danh sách minion (mặc định rỗng)
    private int summonCooldown; // Thời gian chờ triệu hồi minion (mặc định 0)
    private int summonTimer; // Bộ đếm thời gian triệu hồi (mặc định 0)

    public Enemy(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp,
                 int attackPower, int defense, int speed, float aggressionLevel, List<Item> lootTable,
                 int staggerTime, int detectionRange) {
        super(gp, id, name, position, image, hp, maxHp, 0, 0, attackPower, defense, speed); // Thêm mana=0, maxMana=0
        this.aggressionLevel = Math.max(0.0f, Math.min(aggressionLevel, 1.0f)); // Giới hạn từ 0.0 đến 1.0
        this.lootTable = new ArrayList<>(lootTable != null ? lootTable : new ArrayList<>()); // Sao chép để tránh tham chiếu trực tiếp
        this.staggerTime = staggerTime > 0 ? staggerTime : 120; // Mặc định 2 giây tại 60 FPS
        this.staggerTimer = 0;
        this.detectionRange = detectionRange > 0 ? detectionRange : 200; // Mặc định 200 pixel
        this.target = null;
        this.powerMultiplier = 1.0f; // Mặc định không tăng sức mạnh
        this.specialSkill = null; // Mặc định không có kỹ năng đặc biệt
        this.minions = new ArrayList<>(); // Mặc định không có minion
        this.summonCooldown = 0; // Mặc định không triệu hồi
        this.summonTimer = 0;

        // Điểm tuần tra mặc định (có thể mở rộng sau)
        List<Vector2> patrolPoints = new ArrayList<>();
        patrolPoints.add(new Vector2(position.getX() + 50, position.getY()));
        patrolPoints.add(new Vector2(position.getX() - 50, position.getY()));

        // Khởi tạo AIController với các tham số phù hợp
        this.aiController = new AIController(
                this, speed, detectionRange, 50, attackPower, 120, patrolPoints, staggerTime // Sử dụng staggerTime thay vì hardcode 120
        );
    }

    @Override
    public void update() {
        if (!isActive || gp == null) return;

        // Cập nhật thời gian bị choáng
        if (staggerTimer > 0) {
            staggerTimer--; // Giảm 1 khung hình mỗi lần
            return;
        }

        // Cập nhật hành vi AI
        if (aiController != null) {
            aiController.updateAI(this, 1); // Truyền 1 khung hình
        }

        // Kiểm tra và tấn công Player nếu nằm trong tầm
        if (gp.player != null && gp.player.isActive() && staggerTimer <= 0) {
            int dx = gp.player.getWorldX() - worldX;
            int dy = gp.player.getWorldY() - worldY;
            int distanceSquared = dx * dx + dy * dy;
            int attackRangeSquared = 50 * 50; // Tầm tấn công bình phương

            if (distanceSquared <= attackRangeSquared && aiController.getCurrentState() == AIState.AGGRESSIVE) {
                attack(gp.player);
            }
        }

        // Cập nhật kỹ năng đặc biệt (nếu có)
        if (specialSkill != null) {
            specialSkill.update(1.0f / 60.0f); // Giả định 60 FPS
        }

        // Cập nhật thời gian triệu hồi minion (nếu có)
        if (summonTimer > 0) {
            summonTimer--;
        }
        if (summonTimer <= 0 && !minions.isEmpty() && summonCooldown > 0) {
            summonMinion(); // Gọi summonMinion nếu có cooldown
            summonTimer = summonCooldown;
        }

        // Cập nhật minion
        for (Enemy minion : minions) {
            if (minion != null && minion.isActive()) {
                minion.update();
            }
        }

        // Đồng bộ vị trí
        syncWorldPosition();
    }

    public void chase(Character target) {
        if (target == null || !isActive || gp == null) return;
        this.target = target;
        aiController.setTarget(target);
        aiController.setCurrentState(AIState.AGGRESSIVE);
        System.out.println(name + " is chasing " + target.getName());
    }

    @Override
    public void attack(Character target) {
        if (target == null || !isActive || aiController.getCurrentState() != AIState.AGGRESSIVE) return;
        super.attack(target);
    }

    public void dropLoot() {
        if (!isActive && !lootTable.isEmpty() && gp != null) {
            Random random = new Random();
            Item droppedItem = lootTable.get(random.nextInt(lootTable.size()));
            droppedItem.setPosition(new Vector2(worldX, worldY));
            if (droppedItem.getImage() == null) {
                droppedItem.setImage(new GameImage("/items/" + droppedItem.getType().toLowerCase() + ".png", gp.tileSize, gp.tileSize));
            }
            gp.items.add(droppedItem);
            System.out.println(name + " dropped " + droppedItem.getName());
        }
    }

    public void patrol() {
        if (!isActive || aiController.getCurrentState() != AIState.PATROL) return;
        aiController.setCurrentState(AIState.PATROL);
        System.out.println(name + " is patrolling");
    }

    public void stagger() {
        if (!isActive) return;
        this.staggerTimer = staggerTime;
        aiController.setCurrentState(AIState.DEFENSIVE);
        System.out.println(name + " is staggered for " + (staggerTime / 60) + " seconds");
    }

    @Override
    public void draw(Graphics2D g2) {
        if (gp == null || gp.player == null) return;
        screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
        if (image != null && image.isLoaded()) {
            image.draw(g2, screenX, screenY);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    @Override
    public void die() {
        super.die();
        dropLoot();
    }

    // Thêm phương thức từ Boss
    public void useSpecialSkill(Character target) {
        if (target == null || specialSkill == null || !specialSkill.canUse()) return;
        int damage = specialSkill.getDamage();
        target.takeDamage(damage);
        specialSkill.use(this, target); // Xử lý cooldown và mana
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(name + " used " + specialSkill.getName() + " on " + target.getName() + " for " + damage + " damage!");
        }
    }

    public void summonMinion() {
        if (gp == null || gp.enemies == null || summonCooldown <= 0) return;

        // Tạo một minion (Enemy cấp thấp)
        Enemy minion = new Enemy(
                gp,
                gp.enemies.size() + 1, // ID mới
                "Minion of " + name, // Tên minion
                new Vector2(worldX + 50, worldY), // Vị trí gần enemy
                new GameImage("/enemies/minion.png", gp.tileSize, gp.tileSize), // Hình ảnh
                50, 50, // HP và maxHP
                10, 5, 3, // attackPower, defense, speed
                0.5f, // aggressionLevel
                new ArrayList<>(), // Không có lootTable
                60, // staggerTime (1 giây)
                100 // detectionRange
        );
        minions.add(minion);
        gp.enemies.add(minion);
        if (gp.ui != null) {
            gp.ui.showMessage(name + " summoned a minion!");
        }
    }

    // Getter và Setter
    public float getAggressionLevel() { return aggressionLevel; }
    public void setAggressionLevel(float aggressionLevel) { this.aggressionLevel = Math.max(0.0f, Math.min(aggressionLevel, 1.0f)); }
    public List<Item> getLootTable() { return new ArrayList<>(lootTable); }
    public void setLootTable(List<Item> lootTable) { this.lootTable = new ArrayList<>(lootTable != null ? lootTable : new ArrayList<>()); }
    public Character getTarget() { return target; }
    public void setTarget(Character target) {
        this.target = target;
        if (aiController != null) aiController.setTarget(target);
    }
    public int getStaggerTime() { return staggerTime; }
    public void setStaggerTime(int staggerTime) { this.staggerTime = staggerTime > 0 ? staggerTime : 120; }
    public int getStaggerTimer() { return staggerTimer; }
    public void setStaggerTimer(int staggerTimer) { this.staggerTimer = staggerTimer; }
    public int getDetectionRange() { return detectionRange; }
    public void setDetectionRange(int detectionRange) { this.detectionRange = detectionRange > 0 ? detectionRange : 200; }
    public AIController getAiController() { return aiController; }
    public void setAiController(AIController aiController) { this.aiController = aiController; }
    public float getPowerMultiplier() { return powerMultiplier; }
    public void setPowerMultiplier(float powerMultiplier) { this.powerMultiplier = powerMultiplier > 1.0f ? powerMultiplier : 1.0f; }
    public Skill getSpecialSkill() { return specialSkill; }
    public void setSpecialSkill(Skill specialSkill) { this.specialSkill = specialSkill; }
    public List<Enemy> getMinions() { return new ArrayList<>(minions); }
    public void setMinions(List<Enemy> minions) { this.minions = new ArrayList<>(minions != null ? minions : new ArrayList<>()); }
    public int getSummonCooldown() { return summonCooldown; }
    public void setSummonCooldown(int summonCooldown) { this.summonCooldown = summonCooldown > 0 ? summonCooldown : 0; }
    public int getSummonTimer() { return summonTimer; }
    public void setSummonTimer(int summonTimer) { this.summonTimer = summonTimer; }
}