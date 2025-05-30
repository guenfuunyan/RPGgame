package AI;

import entity.Enemy;
import entity.Character;
import util.Vector2;

public class AggressiveAI {
    // Thuộc tính
    private int attackRange;      // Khoảng cách bình phương để tấn công người chơi
    private int attackDamage;     // Sát thương mỗi lần tấn công
    private int attackCooldown;   // Số khung hình giữa các lần tấn công
    private int currentCooldown;  // Số khung hình còn lại
    private Enemy enemy;          // Kẻ địch sử dụng hành vi tấn công
    private int speed;            // Tốc độ di chuyển của kẻ địch

    // Constructor
    public AggressiveAI(Enemy enemy, int attackRange, int attackDamage, int attackCooldown, int speed) {
        this.enemy = enemy;
        this.attackRange = attackRange * attackRange; // Lưu bình phương để so sánh
        this.attackDamage = attackDamage;
        this.attackCooldown = attackCooldown;
        this.currentCooldown = 0;
        this.speed = speed;
    }

    // Getter và Setter
    public int getAttackRange() {
        return (int) Math.sqrt(attackRange); // Trả về giá trị gốc
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange * attackRange; // Lưu bình phương
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Phương thức kiểm tra xem người chơi có trong tầm tấn công không
    public boolean isPlayerInRange(Character target) {
        if (target == null || enemy == null) {
            return false;
        }
        Vector2 entityPos = enemy.getPosition();
        Vector2 targetPos = target.getPosition();
        int dx = targetPos.getX() - entityPos.getX();
        int dy = targetPos.getY() - entityPos.getY();
        int distanceSquared = dx * dx + dy * dy;
        return distanceSquared <= attackRange;
    }

    // Phương thức đuổi theo người chơi
    public void chasePlayer(Character target, int deltaTime) {
        if (target == null || enemy == null) {
            return;
        }
        Vector2 entityPos = enemy.getPosition();
        Vector2 targetPos = target.getPosition();
        int dx = targetPos.getX() - entityPos.getX();
        int dy = targetPos.getY() - entityPos.getY();
        int distanceSquared = dx * dx + dy * dy;

        // Tránh chia cho 0
        if (distanceSquared == 0) {
            return;
        }

        // Tính toán vector di chuyển
        int moveX = (dx * speed) / (int)Math.sqrt(distanceSquared);
        int moveY = (dy * speed) / (int)Math.sqrt(distanceSquared);

        // Lưu vị trí cũ
        int prevWorldX = enemy.getWorldX();
        int prevWorldY = enemy.getWorldY();

        // Cập nhật vị trí của kẻ địch
        enemy.setWorldX(prevWorldX + moveX);
        enemy.setWorldY(prevWorldY + moveY);

        // Kiểm tra va chạm
        enemy.setCollisionOn(false);
        if (enemy.getGp() != null && enemy.getGp().cChecker != null) {
            enemy.getGp().cChecker.checkTile(enemy);
            if (enemy.isCollisionOn()) {
                enemy.setWorldX(prevWorldX);
                enemy.setWorldY(prevWorldY); // Hoàn tác nếu va chạm
            }
        }

        System.out.println("Chasing player to position: (" + targetPos.getX() + ", " + targetPos.getY() + ")");
    }

    // Phương thức tấn công người chơi
    public void performAttack(Character target) {
        if (target == null || !target.isActive() || currentCooldown > 0 || enemy == null) {
            return;
        }

        // Gọi phương thức attack của Character để gây sát thương
        enemy.attack(target);

        // Kích hoạt cooldown
        currentCooldown = attackCooldown;
        System.out.println(enemy.getName() + " attacked " + target.getName() + ", dealing " + attackDamage + " damage. " +
                target.getName() + " HP: " + target.getHp());
    }

    // Cập nhật cooldown mỗi khung hình
    public void updateCooldown(int deltaTime) {
        if (currentCooldown > 0) {
            currentCooldown = Math.max(0, currentCooldown - deltaTime);
        }
    }

    // Phương thức chính: Thực hiện hành vi tấn công
    public void performAggressiveBehavior(Character target, int deltaTime) {
        updateCooldown(deltaTime); // Cập nhật cooldown
        if (isPlayerInRange(target)) {
            performAttack(target);
        } else {
            chasePlayer(target, deltaTime);
        }
    }
}