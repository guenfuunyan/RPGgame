package pack1;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Enemy {
    private List<Skill> specialAbilities;
    private boolean rageMode;

    // Constructor
    public Boss(int id, String name, float x, float y, int maxHp, int attackPower, int defense, float speed) {
        super(id, name, x, y, maxHp, attackPower, defense, speed);
        this.specialAbilities = new ArrayList<>();
        this.rageMode = false;
        // Initialize some default special abilities for testing
        this.specialAbilities.add(new Skill("Fire Blast", 40, "Burn"));
        this.specialAbilities.add(new Skill("Thunder Strike", 50, "Stun"));
    }

    // Use a special ability on a target
    public void useSpecialAbility() {
        if (!isActive || specialAbilities.isEmpty()) {
            System.out.println(name + " cannot use special ability.");
            return;
        }
        // Select a random ability from the list
        Skill ability = specialAbilities.get((int)(Math.random() * specialAbilities.size()));
        System.out.println(name + " uses special ability: " + ability.getName());

        // Find target (assume targeting the first player for simplicity)
        List<Player> players = getPlayers();
        if (players.isEmpty()) {
            System.out.println("No players to target with " + ability.getName());
            return;
        }
        Player target = players.get(0);
        if (!target.isActive()) return;

        // Apply damage and effect
        target.takeDamage(ability.getDamage());
        System.out.println(target.getName() + " is affected by " + ability.getEffect() + " (damage: " + ability.getDamage() + ")");
    }

    // Enter rage mode if HP is low
    public void enrage() {
        if (!isActive || rageMode) {
            System.out.println(name + " cannot enrage.");
            return;
        }
        // Enrage if HP is below 30% of max HP
        if (hp <= maxHp * 0.3) {
            rageMode = true;
            attackPower = (int)(attackPower * 1.5); // Increase attack power by 50%
            speed = speed * 1.2f; // Increase speed by 20%
            System.out.println(name + " enters rage mode! Attack power: " + attackPower + ", Speed: " + speed);
        } else {
            System.out.println(name + " is not ready to enrage yet (HP: " + hp + "/" + maxHp + ")");
        }
    }

    // Summon minions to assist in combat
    public void summonMinions() {
        if (!isActive) {
            System.out.println(name + " cannot summon minions.");
            return;
        }
        // Summon 2 minions around the boss's position
        int numMinions = 2;
        for (int i = 0; i < numMinions; i++) {
            float offsetX = (float)(Math.random() * 2 - 1); // Random offset between -1 and 1
            float offsetY = (float)(Math.random() * 2 - 1);
            Vector2 minionPosition = new Vector2(position.x + offsetX, position.y + offsetY);
            Enemy minion = new Enemy(1000 + i, "Minion " + (i + 1), minionPosition.x, minionPosition.y, 30, 10, 5, 3.0f);
            addEnemy(minion);
            System.out.println(name + " summons " + minion.getName() + " at " + minionPosition);
        }
    }

    // Helper method to get players (placeholder, should integrate with GameManager)
    private List<Player> getPlayers() {
        return new ArrayList<>(); // Replace with actual player list access
    }

    // Helper method to add enemy (placeholder, should integrate with GameManager)
    private void addEnemy(Enemy enemy) {
        // Simulate adding to GameManager's enemy list
        System.out.println("Adding " + enemy.getName() + " to enemy list.");
    }
}