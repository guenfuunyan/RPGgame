package entity;

import main.GamePanel;
import util.GameImage;
import util.Vector2;

import java.awt.*;

public class Ally extends Character {
    private Character target; // Mục tiêu tấn công
    private int followDistance; // Khoảng cách tối đa để theo Player
    private Player leader; // Player mà Ally theo sau

    public Ally(GamePanel gp, int id, String name, Vector2 position, GameImage image, Player leader) {
        super(gp, id, name, position, image, 150, 150, 100, 100, 15, 5, 5); // Chỉ số trung bình
        this.leader = leader;
        this.followDistance = 100; // 100 pixel
        this.target = null;
    }

    @Override
    public void update() {
        if (!isActive || gp == null || leader == null) return;

        // Tìm mục tiêu tấn công
        findTarget();

        // Nếu có mục tiêu, tấn công
        if (target != null && target.isActive()) {
            chaseTarget();
        } else {
            // Nếu không có mục tiêu, theo sau leader
            followLeader();
        }

        // Cập nhật kỹ năng
        updateSkills(1.0f / 60.0f);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null && image.isLoaded()) {
            g2.drawImage(image.getBufferedImage(), worldX, worldY, image.getWidth(), image.getHeight(), null);
        } else {
            g2.setColor(Color.CYAN);
            g2.fillRect(worldX, worldY, gp.tileSize, gp.tileSize);
        }
    }

    private void findTarget() {
        target = null;
        double minDistanceSquared = Double.MAX_VALUE;
        int attackRangeSquared = 100 * 100; // Phạm vi tấn công 100 pixel

        for (Enemy enemy : gp.enemies) {
            if (enemy != null && enemy.isActive()) {
                int dx = enemy.getWorldX() - worldX;
                int dy = enemy.getWorldY() - worldY;
                double distanceSquared = dx * dx + dy * dy;

                if (distanceSquared <= attackRangeSquared && distanceSquared < minDistanceSquared) {
                    minDistanceSquared = distanceSquared;
                    target = enemy;
                }
            }
        }
    }

    private void chaseTarget() {
        int dx = target.getWorldX() - worldX;
        int dy = target.getWorldY() - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        int attackRange = 50;

        if (distance <= attackRange) {
            attack(target);
        } else {
            String direction = dx > 0 ? "right" : "left";
            if (Math.abs(dy) > Math.abs(dx)) {
                direction = dy > 0 ? "down" : "up";
            }
            move(direction);
        }
    }

    private void followLeader() {
        int dx = leader.getWorldX() - worldX;
        int dy = leader.getWorldY() - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > followDistance) {
            String direction = dx > 0 ? "right" : "left";
            if (Math.abs(dy) > Math.abs(dx)) {
                direction = dy > 0 ? "down" : "up";
            }
            move(direction);
        }
    }

    // Getter và Setter
    public Character getTarget() { return target; }
    public int getFollowDistance() { return followDistance; }
    public void setFollowDistance(int followDistance) { this.followDistance = followDistance; }
}