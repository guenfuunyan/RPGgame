package skill;

import entity.Character;
import entity.Player;

public class SpeedBoostSkill extends Skill {
    private int speedIncrease;  // Lượng tăng tốc độ
    private int duration;       // Thời gian kéo dài hiệu ứng
    
    public SpeedBoostSkill(String name, int speedIncrease, int duration, int manaCost, int cooldown) {
        super(name, 0, manaCost, cooldown); // Damage = 0 vì đây là skill buff
        this.speedIncrease = validateSpeedIncrease(speedIncrease);
        this.duration = validateDuration(duration);
    }

    @Override
    protected void applyEffect(Character user, Character target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.applySpeedBoost(speedIncrease, duration);
        }
    }

    @Override
    public void use(Character user, Character target) {
        if (!validateUse(user, target)) {
            return;
        }

        if (user.getMana() >= manaCost) {
            user.setMana(user.getMana() - manaCost);
            applyEffect(user, target);
            startCooldown();
        }
    }

    private boolean validateUse(Character user, Character target) {
        return user != null && 
               target != null && 
               target instanceof Player && 
               target.isActive() && 
               canUse();
    }

    private int validateSpeedIncrease(int value) {
        return Math.max(1, value);
    }

    private int validateDuration(int value) {
        return Math.max(1, value);
    }

    // Getters và Setters
    public int getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(int value) {
        this.speedIncrease = validateSpeedIncrease(value);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int value) {
        this.duration = validateDuration(value);
    }
}