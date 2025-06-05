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

public class Boss extends Enemy {
    // Thuộc tính đặc biệt của Boss
    private int rageThreshold;      // Ngưỡng máu kích hoạt trạng thái phẫn nộ
    private boolean isEnraged;      // Trạng thái phẫn nộ
    private int specialAttackCooldown;    // Thời gian chờ đòn đánh đặc biệt
    private int currentSpecialAttackCooldown;
    private List<Skill> specialSkills;    // Danh sách kỹ năng đặc biệt

    public Boss(GamePanel gp, int id, String name, Vector2 position, GameImage image,
               int hp, int maxHp, int attackPower, int defense, int speed,
               float aggressionLevel, List<Item> lootTable, int detectionRange,
               int rageThreshold, int specialAttackCooldown) {
        super(gp, id, name, position, image, hp, maxHp, attackPower, defense, speed,
              aggressionLevel, lootTable, detectionRange);
        
        initializeBossStats(rageThreshold, specialAttackCooldown);
    }

    private void initializeBossStats(int rageThreshold, int specialAttackCooldown) {
        this.rageThreshold = Math.max(1, Math.min(rageThreshold, getMaxHp()));
        this.isEnraged = false;
        this.specialAttackCooldown = Math.max(1, specialAttackCooldown);
        this.currentSpecialAttackCooldown = 0;
        this.specialSkills = new ArrayList<>();
    }

    @Override
    public void update() {
        if (!isActive()) return;

        updateBossState();
        super.update();
    }

    private void updateBossState() {
        // Cập nhật cooldown đòn đánh đặc biệt
        if (currentSpecialAttackCooldown > 0) {
            currentSpecialAttackCooldown--;
        }

        // Kiểm tra và kích hoạt trạng thái phẫn nộ
        if (!isEnraged && getHp() <= rageThreshold) {
            enterRageMode();
        }

        // Cập nhật các kỹ năng đặc biệt
        updateSpecialSkills(1.0f / 60.0f);
    }

    private void enterRageMode() {
        isEnraged = true;
        setAttackPower(getAttackPower() * 2);
        setDefense(getDefense() * 2);
        setSpeed((int)(getSpeed() * 1.2));
        
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(getName() + " đã vào trạng thái phẫn nộ!");
        }
    }

    @Override
    protected void performAttack(Character target) {
        if (!canAttack(target)) return;

        // Thực hiện đòn đánh thường
        super.performAttack(target);

        // Xử lý đòn đánh đặc biệt
        if (canUseSpecialAttack()) {
            performSpecialAttack(target);
        }
    }

    @Override
    protected boolean canAttack(Character target) {
        if (!super.canAttack(target)) return false;

        if (!isActive() || target == null || !target.isActive()) {
            return false;
        }

        // Kiểm tra khoảng cách tấn công
        int distanceSquared = calculateDistanceSquared(target);
        if (distanceSquared > getAttackRange() * getAttackRange()) {
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage(getName() + " không thể tấn công mục tiêu ở xa!");
            }
            return false;
        }

        return true;
    }

    private boolean canUseSpecialAttack() {
        return currentSpecialAttackCooldown <= 0;
    }

    private void performSpecialAttack(Character target) {
        if (target == null || !target.isActive()) return;

        int specialDamage = calculateSpecialDamage();
        target.takeDamage(specialDamage);
        currentSpecialAttackCooldown = specialAttackCooldown;

        showSpecialAttackMessage(target, specialDamage);
    }

    private int calculateSpecialDamage() {
        return getAttackPower() * (isEnraged ? 3 : 2);
    }

    private void showSpecialAttackMessage(Character target, int damage) {
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(getName() + " sử dụng đòn đánh đặc biệt vào " + 
                            target.getName() + " gây " + damage + " sát thương!");
        }
    }

    private void updateSpecialSkills(float deltaTime) {
        if (specialSkills != null) {
            for (Skill skill : specialSkills) {
                if (skill != null) {
                    skill.update(deltaTime);
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        
        // Vẽ hiệu ứng phẫn nộ
        if (isEnraged) {
            int screenX = getScreenX();
            int screenY = getScreenY();
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    // Getters và Setters
    public int getRageThreshold() { 
        return rageThreshold; 
    }
    
    public void setRageThreshold(int value) { 
        this.rageThreshold = Math.max(1, Math.min(value, getMaxHp())); 
    }
    
    public boolean isEnraged() { 
        return isEnraged; 
    }
    
    public void setEnraged(boolean enraged) { 
        this.isEnraged = enraged; 
    }
    
    public int getSpecialAttackCooldown() { 
        return specialAttackCooldown; 
    }
    
    public void setSpecialAttackCooldown(int value) { 
        this.specialAttackCooldown = Math.max(1, value); 
    }
    
    public List<Skill> getSpecialSkills() { 
        return new ArrayList<>(specialSkills); 
    }
    
    public void setSpecialSkills(List<Skill> skills) { 
        this.specialSkills = skills != null ? new ArrayList<>(skills) : new ArrayList<>(); 
    }

    private int calculateDistanceSquared(Character target) {
        int dx = target.getWorldX() - getWorldX();
        int dy = target.getWorldY() - getWorldY();
        return dx * dx + dy * dy;
    }
}