package entity;

import item.Item;
import main.GamePanel;
import util.GameImage;
import util.Vector2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Character {
    // Thuộc tính cơ bản
    private float aggressionLevel;
    private List<Item> lootTable;
    private int detectionRange;
    private Character target;
    
    // Thuộc tính AI
    private AIState currentState;
    private int attackRange;
    private int attackCooldown;
    private int currentAttackCooldown;

    public enum AIState {
        PATROL,      // Tuần tra trong khu vực
        AGGRESSIVE,  // Tấn công tích cực
        DEFENSIVE    // Phòng thủ
    }

    public Enemy(GamePanel gp, int id, String name, Vector2 position, GameImage image, 
                int hp, int maxHp, int attackPower, int defense, int speed, 
                float aggressionLevel, List<Item> lootTable, int detectionRange) {
        super(gp, id, name, position, image, hp, maxHp, 0, 0, attackPower, defense, speed);
        
        this.aggressionLevel = Math.max(0.0f, Math.min(aggressionLevel, 1.0f));
        this.lootTable = new ArrayList<>(lootTable != null ? lootTable : new ArrayList<>());
        this.detectionRange = detectionRange > 0 ? detectionRange : 200;
        this.attackRange = 50;
        this.attackCooldown = 60;
        this.currentAttackCooldown = 0;
        this.currentState = AIState.PATROL;
    }

    @Override
    public void update() {
        if (!isActive || gp == null) return;

        updateAI();
        syncWorldPosition();
    }

    private void updateAI() {
        // Giảm cooldown tấn công
        if (currentAttackCooldown > 0) {
            currentAttackCooldown--;
        }

        switch (currentState) {
            case PATROL:
                if (detectPlayer()) {
                    currentState = AIState.AGGRESSIVE;
                }
                break;

            case AGGRESSIVE:
                if (target != null && target.isActive()) {
                    if (isInAttackRange(target)) {
                        if (currentAttackCooldown <= 0) {
                            attack(target);
                            currentAttackCooldown = attackCooldown;
                        }
                    } else {
                        moveTowardTarget();
                    }

                    // Chuyển sang phòng thủ nếu HP thấp
                    if (hp < maxHp * 0.3) {
                        currentState = AIState.DEFENSIVE;
                    }
                } else {
                    currentState = AIState.PATROL;
                    target = null;
                }
                break;

            case DEFENSIVE:
                // Tăng defense và giảm speed khi phòng thủ
                setDefense(defense * 2);
                setSpeed(speed / 2);

                // Nếu HP hồi phục đủ, quay lại tấn công
                if (hp > maxHp * 0.5 && target != null && target.isActive()) {
                    setDefense(defense / 2);
                    setSpeed(speed * 2);
                    currentState = AIState.AGGRESSIVE;
                }
                break;
        }
    }

    private boolean detectPlayer() {
        if (gp.player != null && isInDetectionRange(gp.player)) {
            target = gp.player;
            return true;
        }
        return false;
    }

    private boolean isInDetectionRange(Character character) {
        if (character == null || !character.isActive()) return false;
        return calculateDistanceSquared(character) <= detectionRange * detectionRange;
    }

    private boolean isInAttackRange(Character character) {
        if (character == null || !character.isActive()) return false;
        return calculateDistanceSquared(character) <= attackRange * attackRange;
    }

    private int calculateDistanceSquared(Character character) {
        int dx = character.getWorldX() - worldX;
        int dy = character.getWorldY() - worldY;
        return dx * dx + dy * dy;
    }

    private void moveTowardTarget() {
        if (target == null) return;
        
        int dx = target.getWorldX() - worldX;
        int dy = target.getWorldY() - worldY;
        
        // Di chuyển theo hướng của target
        String direction = getMovementDirection(dx, dy);
        move(direction);
    }

    private String getMovementDirection(int dx, int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? "right" : "left";
        } else {
            return dy > 0 ? "down" : "up";
        }
    }

    @Override
    protected void performAttack(Character target) {
        // Sử dụng logic tấn công từ lớp cha
        super.performAttack(target);
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

    public void dropLoot() {
        if (!lootTable.isEmpty() && gp != null) {
            Random random = new Random();
            Item droppedItem = lootTable.get(random.nextInt(lootTable.size()));
            if (droppedItem != null) {
                droppedItem.setPosition(new Vector2(worldX, worldY));
                gp.items.add(droppedItem);
            }
        }
    }

    // Getters và Setters
    public Character getTarget() { return target; }
    public AIState getCurrentState() { return currentState; }
    public int getDetectionRange() { return detectionRange; }
    public int getAttackRange() { return attackRange; }
    public float getAggressionLevel() { return aggressionLevel; }

    protected boolean canAttack(Character target) {
        // Kiểm tra các điều kiện cơ bản
        if (!isActive || target == null || !target.isActive()) {
            return false;
        }

        // Kiểm tra khoảng cách tấn công
        return calculateDistanceSquared(target) <= attackRange * attackRange;
    }
}