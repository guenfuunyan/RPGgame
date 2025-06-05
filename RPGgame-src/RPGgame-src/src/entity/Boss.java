package entity;

import AI.AIController;
import item.Item;
import main.GamePanel;
import skill.Skill;
import util.GameImage;
import util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Enemy {

    public Boss(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp,
                int attackPower, int defense, int speed, float aggressionLevel, List<Item> lootTable,
                int staggerTime, int detectionRange, float powerMultiplier, String specialSkillName) {
        super(gp, id, name, position, image, hp, maxHp, attackPower, defense, speed, aggressionLevel, lootTable,
                staggerTime, detectionRange);

        // Tăng sức mạnh cho Boss
        this.setPowerMultiplier(powerMultiplier > 1.0f ? powerMultiplier : 1.5f); // Mặc định tăng 50%
        setAttackPower((int) (getAttackPower() * this.getPowerMultiplier()));
        setDefense((int) (getDefense() * this.getPowerMultiplier()));
        setHp((int) (getHp() * this.getPowerMultiplier()));
        setMaxHp((int) (getMaxHp() * this.getPowerMultiplier()));

        // Khởi tạo kỹ năng đặc biệt
        this.setSpecialSkill(new Skill(
                specialSkillName != null ? specialSkillName : "Dark Blast", // Tên kỹ năng
                (int) (30 * this.getPowerMultiplier()), // Sát thương (hoặc hiệu ứng khác)
                20, // Chi phí mana (giả định boss có mana nếu cần)
                300 // Cooldown (5 giây tại 60 FPS)
        ));

        // Khởi tạo minion và triệu hồi
        this.setSummonCooldown(600); // 10 giây tại 60 FPS
        this.setSummonTimer(0);
    }

    @Override
    public void update() {
        if (!isActive() || gp == null) return;

        // Gọi update từ Enemy để xử lý logic chung
        super.update();

        // Triệu hồi minion nếu cooldown đã hết và số lượng minion dưới 3
        if (getSummonTimer() <= 0 && getMinions().size() < 3) { // Giới hạn tối đa 3 minion
            summonMinion();
            setSummonTimer(getSummonCooldown());
        }

        // Cập nhật minion đã được xử lý trong super.update()
    }

    @Override
    public void attack(Character target) {
        if (target == null || !target.isActive() || getStaggerTimer() > 0) return;
        int damage = Math.max(0, getAttackPower() - target.getDefense());
        target.takeDamage(damage);
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(getName() + " attacked " + target.getName() + " for " + damage + " damage!");
        }
        setStaggerTimer(getStaggerTime()); // Kích hoạt choáng sau khi tấn công
    }

    @Override
    public void die() {
        super.die();
        // Xóa tất cả minion khi boss chết
        for (Enemy minion : getMinions()) {
            if (minion != null && minion.isActive()) {
                minion.die();
            }
        }
        getMinions().clear();
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(getName() + " has been defeated! All minions destroyed!");
        }
    }
}