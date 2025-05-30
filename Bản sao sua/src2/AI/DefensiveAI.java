package AI;

import entity.Enemy;
import entity.Character;
import entity.Player;
import util.Vector2;

public class DefensiveAI {
    // Thuộc tính
    private Enemy enemy;
    private int dodgeRange;        // Tầm né tránh (đơn vị: pixel)
    private int dodgeSpeed;        // Tốc độ né tránh (đơn vị: pixel mỗi khung hình)
    private int dodgeCooldown;     // Số khung hình giữa các lần né tránh
    private int currentDodgeCooldown; // Số khung hình còn lại
    private int attackDetectionTime; // Số khung hình để phát hiện tấn công
    private boolean isDodging;

    // Constructor
    public DefensiveAI(Enemy enemy, int dodgeRange, int dodgeSpeed, int dodgeCooldown, int attackDetectionTime) {
        this.enemy = enemy;
        this.dodgeRange = dodgeRange;
        this.dodgeSpeed = dodgeSpeed;
        this.dodgeCooldown = dodgeCooldown;
        this.currentDodgeCooldown = 0;
        this.attackDetectionTime = attackDetectionTime;
        this.isDodging = false;
    }

    // Constructor với giá trị mặc định
    public DefensiveAI(Enemy enemy) {
        this(enemy, 50, 5, 60, 60); // Giá trị mặc định: 50 pixel, 5 speed, 1 giây cooldown (60 khung hình), 1 giây detection (60 khung hình)
    }

    // Phương thức phát hiện tấn công
    public boolean detectAttack(Enemy owner) {
        if (owner == null || owner.getGp() == null || owner.getGp().player == null) return false;

        Player player = owner.getGp().player;
        Vector2 enemyPos = owner.getPosition();
        Vector2 playerPos = player.getPosition();

        // Tính khoảng cách bình phương
        int dx = playerPos.getX() - enemyPos.getX();
        int dy = playerPos.getY() - enemyPos.getY();
        int distanceSquared = dx * dx + dy * dy;
        int dodgeRangeSquared = dodgeRange * dodgeRange;

        // Kiểm tra xem người chơi có đang tấn công không
        return distanceSquared <= dodgeRangeSquared && player.isAttacking();
    }

    // Phương thức thực hiện hành vi phòng thủ (né tránh)
    public void defend(Enemy owner) {
        if (owner == null || owner.getGp() == null || owner.getGp().player == null) return;

        // Cập nhật cooldown né tránh
        if (currentDodgeCooldown > 0) {
            currentDodgeCooldown--;
            return;
        }

        // Nếu đang né tránh, tiếp tục di chuyển
        if (isDodging) {
            continueDodge(owner);
            return;
        }

        // Kiểm tra xem có cần né tránh không
        if (detectAttack(owner)) {
            startDodge(owner);
        }
    }

    // Bắt đầu né tránh
    private void startDodge(Enemy owner) {
        if (owner == null || owner.getGp() == null || owner.getGp().player == null) return;

        Character player = owner.getGp().player;
        Vector2 enemyPos = owner.getPosition();
        Vector2 playerPos = player.getPosition();

        // Tính vector hướng né tránh (ngược với hướng người chơi)
        int dx = enemyPos.getX() - playerPos.getX();
        int dy = enemyPos.getY() - playerPos.getY();

        // Tính độ dài vector
        int distanceSquared = dx * dx + dy * dy;
        if (distanceSquared == 0) return; // Tránh chia cho 0

        // Chuẩn hóa vector và di chuyển
        int moveX = (dx * dodgeSpeed) / (int)Math.sqrt(distanceSquared); // Di chuyển theo hướng ngược
        int moveY = (dy * dodgeSpeed) / (int)Math.sqrt(distanceSquared);

        // Cập nhật vị trí mới
        Vector2 newPos = new Vector2(enemyPos.getX() + moveX, enemyPos.getY() + moveY);
        owner.setPosition(newPos);

        // Kiểm tra va chạm
        owner.setCollisionOn(false);
        if (owner.getGp().cChecker != null) {
            owner.getGp().cChecker.checkTile(owner);
            if (owner.isCollisionOn()) {
                owner.setPosition(enemyPos); // Hoàn tác nếu va chạm
            }
        }

        // Đặt trạng thái né tránh
        isDodging = true;
        currentDodgeCooldown = dodgeCooldown;
        System.out.println(owner.getName() + " is dodging!");
    }

    // Tiếp tục né tránh
    private void continueDodge(Enemy owner) {
        if (owner == null || owner.getGp() == null || owner.getGp().player == null) return;

        // Tiếp tục di chuyển trong nửa thời gian cooldown
        if (currentDodgeCooldown > dodgeCooldown / 2) {
            startDodge(owner); // Tiếp tục di chuyển né tránh
        } else {
            isDodging = false; // Kết thúc né tránh
            owner.setSpeed(owner.getSpeed() * 2); // Khôi phục tốc độ
            System.out.println(owner.getName() + " finished dodging.");
        }
    }

    // Getter và Setter
    public int getDodgeRange() { return dodgeRange; }
    public void setDodgeRange(int dodgeRange) { this.dodgeRange = dodgeRange; }
    public int getDodgeSpeed() { return dodgeSpeed; }
    public void setDodgeSpeed(int dodgeSpeed) { this.dodgeSpeed = dodgeSpeed; }
    public int getDodgeCooldown() { return dodgeCooldown; }
    public void setDodgeCooldown(int dodgeCooldown) { this.dodgeCooldown = dodgeCooldown; }
    public int getAttackDetectionTime() { return attackDetectionTime; }
    public void setAttackDetectionTime(int attackDetectionTime) { this.attackDetectionTime = attackDetectionTime; }
}