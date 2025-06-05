package skill;

import entity.Character;
import main.GamePanel;

public class HealSkill extends Skill {
    private int healAmount;

    public HealSkill() {
        super("Heal", 0, 15, 10); // Tên, sát thương (0), chi phí mana, thời gian hồi chiêu
        this.healAmount = 25; // Lượng HP hồi phục
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
        user.heal(healAmount);
        startCooldown();

        // Hiển thị thông báo
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(user.getName() + " used " + name + "! Restored " + healAmount + " HP. Current HP: " + user.getHp());
        }
    }

    // Getter và Setter cho healAmount
    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}