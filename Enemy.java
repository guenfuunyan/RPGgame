package entity;

import main.GamePanel;
import util.Vector2;
import util.GameImage;
import util.Item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AI.AIController;
import AI.AIState;

public class Enemy extends Character {
    private float aggressionLevel;
    private List<Item> lootTable;
    private int staggerTime;  // Số khung hình bị choáng
    private int staggerTimer; // Số khung hình còn lại trước khi hết choáng
    private int detectionRange;
    private AIController aiController;
    private Character target;

    public Enemy(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp, int attackPower, int defense, int speed,
                 float aggressionLevel, List<Item> lootTable, int staggerTime, int detectionRange) {
        super(gp, id, name, position, image, hp, maxHp, attackPower, defense, speed);
        this.aggressionLevel = aggressionLevel;
        this.lootTable = new ArrayList<>(lootTable != null ? lootTable : new ArrayList<>());
        this.staggerTime = staggerTime; // Đơn vị: khung hình (ví dụ: 120 = 2 giây tại 60 FPS)
        this.staggerTimer = 0;
        this.detectionRange = detectionRange;
        this.target = null;

        List<Vector2> patrolPoints = new ArrayList<>();
        patrolPoints.add(new Vector2(position.getX() + 50, position.getY()));
        patrolPoints.add(new Vector2(position.getX() - 50, position.getY()));

        this.aiController = new AIController(
                this, speed, detectionRange, 50, attackPower, 120, patrolPoints, 120 // Sử dụng int cho các tham số
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

    // Getter và Setter
    public float getAggressionLevel() { return aggressionLevel; }
    public void setAggressionLevel(float aggressionLevel) { this.aggressionLevel = aggressionLevel; }
    public List<Item> getLootTable() { return new ArrayList<>(lootTable); }
    public void setLootTable(List<Item> lootTable) { this.lootTable = new ArrayList<>(lootTable != null ? lootTable : new ArrayList<>()); }
    public Character getTarget() { return target; }
    public void setTarget(Character target) {
        this.target = target;
        if (aiController != null) aiController.setTarget(target);
    }
    public int getStaggerTime() { return staggerTime; }
    public void setStaggerTime(int staggerTime) { this.staggerTime = staggerTime; }
}