package pack1;

import java.util.List;

public class Ally extends Character {
    private int loyalty;
    private String role;

    // Constructor
    public Ally(int id, String name, float x, float y, int maxHp, int attackPower, int defense, float speed, int loyalty, String role) {
        super(id, name, x, y, maxHp, attackPower, defense, speed);
        this.loyalty = loyalty;
        this.role = role;
    }

    // Follow the player by moving towards their position
    public void followPlayer() {
        if (!isActive) return;
        if (getPlayers().isEmpty()) {
            System.out.println(name + " has no player to follow.");
            return;
        }
        Player player = getPlayers().get(0); // Assume single player for simplicity
        Vector2 playerPos = player.getPosition();
        Vector2 allyPos = getPosition();
        float distance = playerPos.distanceTo(allyPos);

        if (distance > 2.0f) { // Follow if distance > 2 units
            float dx = playerPos.x - allyPos.x;
            float dy = playerPos.y - allyPos.y;
            float moveDistance = Math.min(speed, distance); // Move up to speed or full distance
            float ratio = moveDistance / distance;
            move(allyPos.x + dx * ratio, allyPos.y + dy * ratio);
            System.out.println(name + " is following player to " + getPosition());
        } else {
            System.out.println(name + " is already near the player.");
        }
    }

    // Assist in combat by attacking the target
    public void assistInCombat(Character target) {
        if (!isActive || target == null || !target.isActive()) {
            System.out.println(name + " cannot assist in combat.");
            return;
        }
        // Loyalty affects attack efficiency (e.g., higher loyalty increases damage)
        int bonusDamage = (loyalty > 50) ? (loyalty / 10) : 0;
        int totalDamage = attackPower + bonusDamage;
        System.out.println(name + " assists in combat with bonus damage (" + bonusDamage + ") due to loyalty " + loyalty);
        target.takeDamage(totalDamage);
    }

    // Use buff skill on the player based on role
    public void useBuffSkill(Player player) {
        if (!isActive || player == null || !player.isActive()) {
            System.out.println(name + " cannot use buff skill.");
            return;
        }
        int healAmount = 0;
        int defenseBoost = 0;

        switch (role.toLowerCase()) {
            case "healer":
                healAmount = 30 + (loyalty / 20); // Heal based on loyalty
                player.heal(healAmount);
                System.out.println(name + " (Healer) heals " + player.getName() + " for " + healAmount + " HP.");
                break;
            case "tank":
                defenseBoost = 10 + (loyalty / 50); // Boost defense based on loyalty
                player.defense += defenseBoost;
                System.out.println(name + " (Tank) boosts " + player.getName() + "'s defense by " + defenseBoost + ".");
                break;
            case "support":
                healAmount = 15 + (loyalty / 30); // Moderate heal
                player.heal(healAmount);
                System.out.println(name + " (Support) heals " + player.getName() + " for " + healAmount + " HP.");
                break;
            default:
                System.out.println(name + " has no defined buff role: " + role);
        }
    }

    // Getter for testing
    public int getLoyalty() { return loyalty; }
    public String getRole() { return role; }

    // Helper method to get players (assuming GameManager provides access)
    private List<Player> getPlayers() {
        // This is a placeholder; in a real implementation, it should integrate with GameManager
        return new java.util.ArrayList<>(); // Replace with actual player list access
    }
}