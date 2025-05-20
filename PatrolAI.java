package AI;

import entity.Enemy;
import main.GamePanel;
import util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PatrolAI {
    // Thuộc tính
    private List<Vector2> patrolPoints; // Danh sách các điểm tuần tra
    private Vector2 currentPoint;       // Điểm tuần tra hiện tại
    private int waitTime;             // Thời gian chờ tại mỗi điểm (giây)
    private int currentWaitTimer;     // Bộ đếm thời gian chờ hiện tại
    private int currentPointIndex;      // Chỉ số của điểm tuần tra hiện tại
    private Enemy entity;               // Kẻ địch sử dụng hành vi tuần tra
    private int speed;                // Tốc độ di chuyển của kẻ địch
    private GamePanel gp;               // Tham chiếu đến GamePanel để kiểm tra va chạm

    // Constructor
    public PatrolAI(Enemy entity, List<Vector2> patrolPoints, int waitTime, int speed) {
        this.entity = entity;
        this.gp = entity != null ? entity.getGp() : null; // Lấy GamePanel từ Enemy
        this.patrolPoints = new ArrayList<>(patrolPoints != null ? patrolPoints : new ArrayList<>());
        this.waitTime = waitTime;
        this.speed = speed;
        this.currentPointIndex = 0;
        this.currentWaitTimer = 0;
        // Khởi tạo currentPoint dựa trên vị trí hiện tại của entity hoặc điểm đầu tiên
        this.currentPoint = this.patrolPoints.isEmpty() && entity != null
                ? new Vector2(entity.getWorldX(), entity.getWorldY())
                : this.patrolPoints.get(0);
    }
    // Getter và Setter
    public List<Vector2> getPatrolPoints() {
        return new ArrayList<>(patrolPoints); // Trả về bản sao để bảo vệ dữ liệu
    }

    public void setPatrolPoints(List<Vector2> patrolPoints) {
        this.patrolPoints = new ArrayList<>(patrolPoints != null ? patrolPoints : new ArrayList<>());
        this.currentPointIndex = 0;
        this.currentPoint = this.patrolPoints.isEmpty() ? new Vector2(entity.getWorldX(), entity.getWorldY()) : this.patrolPoints.get(0);
        this.currentWaitTimer = 0;
    }

    public Vector2 getCurrentPoint() {
        return currentPoint;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Phương thức di chuyển đến điểm tuần tra hiện tại
    public void moveToPoint(int deltaTime) {
        if (patrolPoints.isEmpty() || entity == null || gp == null) {
            return; // Không có điểm tuần tra hoặc không hợp lệ
        }

        int currentX = entity.getWorldX();
        int currentY = entity.getWorldY();
        int dx = currentPoint.getX() - currentX;
        int dy = currentPoint.getY() - currentY;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        // Nếu đã gần đến điểm tuần tra (khoảng cách nhỏ hơn ngưỡng)
        if (distance < 5.0f) { // Ngưỡng 5 pixel
            currentWaitTimer += deltaTime;
            if (currentWaitTimer >= waitTime) {
                nextPoint(); // Chuyển sang điểm tuần tra tiếp theo
                currentWaitTimer = 0;
            }
            return;
        }

        // Tính toán vector di chuyển
        int moveDistance = speed * deltaTime * 60; // Điều chỉnh dựa trên FPS (60 FPS)
        if (distance > 0) {
            int moveX = (dx / distance) * moveDistance;
            int moveY = (dy / distance) * moveDistance;

            // Lưu vị trí tạm để kiểm tra va chạm
            int nextX = currentX + moveX;
            int nextY = currentY + moveY;

            // Kiểm tra va chạm với bản đồ
            entity.setWorldX(nextX);
            entity.setWorldY(nextY);
            entity.setCollisionOn(false);
            gp.cChecker.checkTile(entity);
            if (entity.isCollisionOn()) {
                entity.setWorldX(currentX); // Hoàn tác nếu va chạm
                entity.setWorldY(currentY);
            } else {
                entity.setWorldX(nextX);
                entity.setWorldY(nextY);
            }
            entity.syncWorldPosition();
        }
    }

    // Phương thức chuyển sang điểm tuần tra tiếp theo
    public void nextPoint() {
        if (patrolPoints.isEmpty()) {
            return;
        }
        currentPointIndex = (currentPointIndex + 1) % patrolPoints.size(); // Quay vòng danh sách
        currentPoint = patrolPoints.get(currentPointIndex);
    }

    // Phương thức chính: Thực hiện hành vi tuần tra
    public void patrol(int deltaTime) {
        if (entity == null || gp == null) {
            return;
        }
        moveToPoint(deltaTime);
        System.out.println("Patrolling to point: (" + currentPoint.getX() + ", " + currentPoint.getY() + ")");
    }
}