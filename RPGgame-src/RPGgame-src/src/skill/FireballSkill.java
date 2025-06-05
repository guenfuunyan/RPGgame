package skill;

import entity.Character;
import main.GamePanel;

public class FireballSkill extends Skill {
    public FireballSkill() {
        super("Fireball", 30, 20, 5); // Tên, sát thương, chi phí mana, thời gian hồi chiêu (giây)
    }

    @Override
    public void use(Character user, Character target) {
        GamePanel gp = user.getGp(); // Lấy GamePanel từ user
        if (gp == null || !canUse() || user.getMana() < manaCost) {
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage("Cannot use " + name + "! " +
                        (user.getMana() < manaCost ? "Not enough mana." : "Skill on cooldown."));
            }
            return;
        }

        // Sử dụng kỹ năng
        user.setMana(user.getMana() - manaCost);
        target.takeDamage(damage);
        startCooldown();

        // Hiển thị thông báo
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(user.getName() + " used " + name + "! Dealt " + damage + " damage to " + target.getName() + ".");
        }
    }
}