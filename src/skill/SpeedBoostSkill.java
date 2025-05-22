package skill;

import entity.Character;
import entity.Player;
import main.GamePanel;

public class SpeedBoostSkill extends Skill {
    private int speedBoost;
    private int duration; // Thời gian hiệu lực (frames)

    public SpeedBoostSkill() {
        super("Speed Boost", 0, 10, 15); // Tên, sát thương (0), chi phí mana, thời gian hồi chiêu
        this.speedBoost = 3; // Giá trị tăng tốc
        this.duration = 60 * 5; // 5 giây tại 60 FPS
    }

    @Override
    public void use(Character user, Character target) {
        GamePanel gp = user.getGp();
        if (gp == null || !canUse() || user.getMana() < manaCost) {
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage("Cannot use " + name + "! " +
                        (user.getMana() < manaCost ? "Not enough mana." : "Skill on cooldown."));
            }
            return;
        }

        // Sử dụng kỹ năng
        user.setMana(user.getMana() - manaCost);
        if (user instanceof Player) {
            ((Player) user).applyBootsEffect(speedBoost, duration);// Tái sử dụng applyBootsEffect trong Player
        } else {
            // Nếu là Enemy, tăng tốc độ trực tiếp
            user.setSpeed(user.getSpeed() + speedBoost);
        }
        startCooldown();

        // Hiển thị thông báo
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(user.getName() + " used " + name + "! Speed increased by " + speedBoost + " for " + (duration / 60) + " seconds!");
        }
    }

    // Getter và Setter cho speedBoost và duration
    public int getSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost > 0 ? speedBoost : 1; // Đảm bảo giá trị dương
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration > 0 ? duration : 60 * 5; // Đảm bảo giá trị dương, mặc định 5 giây
    }
}