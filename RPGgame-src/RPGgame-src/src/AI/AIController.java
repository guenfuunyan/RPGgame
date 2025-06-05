package AI;

import entity.Character;
import entity.Enemy;
import entity.Player;
import util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class AIController {
    private Character character;
    private int speed;
    private int detectionRange;
    private int attackRange;
    private int attackPower;
    private int attackCooldown; // Số khung hình giữa các lần tấn công
    private int currentAttackCooldown; // Số khung hình còn lại
    private List<Vector2> patrolPoints;
    private int patrolCooldown; // Số khung hình giữa các điểm tuần tra
    private int currentPatrolCooldown; // Số khung hình còn lại
    private int currentPatrolIndex;
    private AIState currentState;
    private Character target;

    public AIController(Character character, int speed, int detectionRange, int attackRange,
                        int attackPower, int attackCooldown, List<Vector2> patrolPoints, int patrolCooldown) {
        this.character = character;
        this.speed = speed;
        this.detectionRange = detectionRange;
        this.attackRange = attackRange;
        this.attackPower = attackPower;
        this.attackCooldown = attackCooldown;
        this.currentAttackCooldown = 0;
        this.patrolPoints = patrolPoints != null ? new ArrayList<>(patrolPoints) : new ArrayList<>();
        this.patrolCooldown = patrolCooldown;
        this.currentPatrolCooldown = 0;
        this.currentPatrolIndex = 0;
        this.currentState = AIState.PATROL;
        this.target = null;
    }

    // Phương thức chính: Cập nhật hành vi AI mỗi khung hình
    public void updateAI(Enemy owner, int deltaTime) {
        if (owner == null || !owner.isActive()) return;

        // Cập nhật cooldown tấn công
        if (currentAttackCooldown > 0) {
            currentAttackCooldown -= deltaTime;
        }

        // Cập nhật cooldown tuần tra
        if (currentPatrolCooldown > 0) {
            currentPatrolCooldown -= deltaTime;
        }

        switch (currentState) {
            case PATROL:
                // Thực hiện tuần tra
                if (!patrolPoints.isEmpty() && currentPatrolCooldown <= 0) {
                    Vector2 targetPoint = patrolPoints.get(currentPatrolIndex);
                    int dx = targetPoint.getX() - owner.getWorldX();
                    int dy = targetPoint.getY() - owner.getWorldY();
                    int distanceSquared = dx * dx + dy * dy;

                    if (distanceSquared > speed * speed) {
                        // Di chuyển đến điểm tuần tra
                        String direction = getDirection(dx, dy);
                        owner.move(direction);
                    } else {
                        // Đã đến điểm tuần tra, chuyển sang điểm tiếp theo
                        currentPatrolIndex = (currentPatrolIndex + 1) % patrolPoints.size();
                        currentPatrolCooldown = patrolCooldown;
                    }
                }

                // Kiểm tra phát hiện Player
                if (detectPlayer(owner)) {
                    setCurrentState(AIState.AGGRESSIVE);
                }
                break;

            case AGGRESSIVE:
                if (target != null && target.isActive()) {
                    int dx = target.getWorldX() - owner.getWorldX();
                    int dy = target.getWorldY() - owner.getWorldY();
                    int distanceSquared = dx * dx + dy * dy;
                    int attackRangeSquared = attackRange * attackRange;

                    if (distanceSquared <= attackRangeSquared && currentAttackCooldown <= 0) {
                        // Tấn công nếu trong tầm và không trong thời gian cooldown
                        owner.attack(target);
                        currentAttackCooldown = attackCooldown;
                    } else if (distanceSquared > speed * speed) {
                        // Đuổi theo Player nếu ngoài tầm tấn công
                        String direction = getDirection(dx, dy);
                        owner.move(direction);
                    }

                    // Kiểm tra xem có bị tấn công để chuyển sang DEFENSIVE
                    if (detectAttack(owner)) {
                        setCurrentState(AIState.DEFENSIVE);
                    }
                } else {
                    // Nếu không còn mục tiêu, quay lại tuần tra
                    setCurrentState(AIState.PATROL);
                }
                break;

            case DEFENSIVE:
                // Logic phòng thủ: Giảm tốc độ và không tấn công
                defend(owner);
                if (!detectAttack(owner)) {
                    if (target != null && target.isActive()) {
                        int dx = target.getWorldX() - owner.getWorldX();
                        int dy = target.getWorldY() - owner.getWorldY();
                        int distanceSquared = dx * dx + dy * dy;
                        int attackRangeSquared = attackRange * attackRange;

                        if (distanceSquared <= attackRangeSquared) {
                            setCurrentState(AIState.AGGRESSIVE);
                        } else {
                            setCurrentState(AIState.PATROL);
                        }
                    } else {
                        setCurrentState(AIState.PATROL);
                    }
                }
                break;
        }
    }

    // Kiểm tra xem Player có trong tầm phát hiện không
    public boolean detectPlayer(Enemy owner) {
        if (target == null || owner == null || !target.isActive()) {
            return false;
        }
        int distanceSquared = calculateDistanceSquared(owner, target.getPosition());
        return distanceSquared <= detectionRange * detectionRange;
    }

    // Tính khoảng cách bình phương giữa Enemy và mục tiêu
    private int calculateDistanceSquared(Enemy owner, Vector2 targetPosition) {
        if (owner == null || targetPosition == null) return Integer.MAX_VALUE;
        int dx = targetPosition.getX() - owner.getWorldX();
        int dy = targetPosition.getY() - owner.getWorldY();
        return dx * dx + dy * dy;
    }

    // Logic phòng thủ: Giảm tốc độ và không tấn công
    private void defend(Enemy owner) {
        // Giả sử giảm tốc độ khi phòng thủ
        owner.setSpeed(speed / 2);
        System.out.println(owner.getName() + " is defending, speed reduced to: " + owner.getSpeed());
    }

    // Kiểm tra xem Enemy có bị tấn công không (giả lập)
    private boolean detectAttack(Enemy owner) {
        // Giả sử Enemy bị tấn công nếu Player đang tấn công và trong tầm
        if (target != null && target instanceof Player) {
            Player player = (Player) target;
            if (player.isAttacking()) {
                int dx = player.getWorldX() - owner.getWorldX();
                int dy = player.getWorldY() - owner.getWorldY();
                int distanceSquared = dx * dx + dy * dy;
                int attackRangeSquared = attackRange * attackRange;
                return distanceSquared <= attackRangeSquared;
            }
        }
        return false;
    }

    // Xác định hướng di chuyển dựa trên dx, dy
    private String getDirection(int dx, int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? "right" : "left";
        } else {
            return dy > 0 ? "down" : "up";
        }
    }

    // Getter và Setter
    public AIState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AIState newState) {
        this.currentState = newState;
        System.out.println("AI State changed to: " + newState);
    }

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDetectionRange() {
        return detectionRange;
    }

    public void setDetectionRange(int detectionRange) {
        this.detectionRange = detectionRange;
    }
}