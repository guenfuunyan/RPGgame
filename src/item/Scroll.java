package item;

import entity.Character;
import entity.Player;
import skill.Skill;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Scroll extends Item {
    private String skillName; // Tên kỹ năng mà cuộn giấy dạy

    // Constructor đầy đủ
    public Scroll(String name, int value, String skillName, Vector2 position, GameImage image) {
        super(name, "Scroll", value, 0, 0, true, position, image); // Tiêu thụ khi sử dụng
        this.skillName = skillName != null ? skillName : "Fireball"; // Mặc định là "Fireball"
    }

    // Constructor mặc định
    public Scroll(String name, int value, String skillName) {
        this(name, value, skillName, new Vector2(0, 0), null);
    }

    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            // Kiểm tra xem người chơi đã có kỹ năng chưa
            if (player.hasSkill(skillName)) {
                if (player.getGp() != null && player.getGp().ui != null) {
                    player.getGp().ui.showMessage(player.getName() + " already knows the skill: " + skillName + "!");
                }
                return;
            }

            // Học kỹ năng mới (giả định Skill có constructor phù hợp)
            Skill newSkill;
            switch (skillName.toLowerCase()) {
                case "fireball":
                    newSkill = new Skill("Fireball", 20, 10, 60); // Tên, sát thương, manaCost, cooldown
                    break;
                case "heal":
                    newSkill = new Skill("Heal", 30, 15, 120); // Tên, lượng hồi phục, manaCost, cooldown
                    break;
                default:
                    newSkill = new Skill(skillName, 10, 5, 60); // Mặc định
                    break;
            }

            player.addSkill(newSkill);
            if (player.getGp() != null && player.getGp().ui != null) {
                player.getGp().ui.showMessage(player.getName() + " learned the skill: " + skillName + " from " + getName() + "!");
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName != null ? skillName : "Fireball"; }
}