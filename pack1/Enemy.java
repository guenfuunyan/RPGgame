package pack1;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character {
    private float aggressionLevel;
    private List<Item> lootTable;
    private Character target;
    private float staggerTime;

    // Constructor
    public Enemy(int id, String name, float x, float y, int maxHp, int attackPower, int defense, float speed) {
        super(id, name, x, y, maxHp, attackPower, defense, speed);
        this.aggressionLevel = 0.5f; // Default aggression level (0.0 to 1.0)
        this.lootTable = new ArrayList<>();
        this.target = null;
        this.staggerTime = 0.0f;
        // Initialize some default loot for testing
        this.lootTable.add(new Item("Health Potion"));
        this.lootTable.add(new Item("Gold Coin"));
    }

    // Chase the target based on aggression level
    public void chase(Character target) {
        if (!isActive || target == null || !target.isActive() || staggerTime > 0) {
            System.out.println(name + " cannot chase target.");
            return;
        }
        this.target = target;
        Vector2 targetPos = target.getPosition();
        Vector2 enemyPos = getPosition();
        float distance = targetPos.distanceTo(enemyPos);

        // Aggression level affects chase range (e.g., higher aggression -> chase from farther)
        float chaseRange = 5.0f + aggressionLevel * 5.0f; // Range from 5 to 10 units
        if (distance <= chaseRange && distance > 1.0f) { // Chase if within range, stop if too close
            float dx = targetPos.x - enemyPos.x;
            float dy = targetPos.y - enemyPos.y;
            float moveDistance = Math.min(speed * (1.0f + aggressionLevel), distance); // Speed boosted by aggression
            float ratio = moveDistance / distance;
            move(enemyPos.x + dx * ratio, enemyPos.y + dy * ratio);
            System.out.println(name + " chases " + target.getName() + " to " + getPosition());
        } else if (distance <= 1.0f) {
            System.out.println(name + " is close enough to attack " + target.getName());
        } else {
            System.out.println(name + " cannot see " + target.getName() + " (distance: " + distance + ")");
        }
    }

    // Attack a player with damage influenced by aggression level
    public void attack(Player player) {
        if (!isActive || player == null || !player.isActive() || staggerTime > 0) {
            System.out.println(name + " cannot attack.");
            return;
        }
        // Aggression level boosts damage (e.g., +50% damage at max aggression)
        int bonusDamage = (int)(attackPower * aggressionLevel * 0.5f);
        int totalDamage = attackPower + bonusDamage;
        System.out.println(name + " attacks " + player.getName() + " for " + totalDamage + " damage (bonus: " + bonusDamage + ")");
        player.takeDamage(totalDamage);
    }

    // Drop loot when defeated
    public void dropLoot() {
        if (!isActive || lootTable.isEmpty()) {
            System.out.println(name + " has no loot to drop.");
            return;
        }
        // Randomly select an item to drop (50% chance to drop an item)
        if (Math.random() < 0.5) {
            Item loot = lootTable.get((int)(Math.random() * lootTable.size()));
            System.out.println(name + " drops " + loot.getName() + "!");
        } else {
            System.out.println(name + " drops nothing.");
        }
    }

    // Patrol the area if no target is present
    public void patrol() {
        if (!isActive || staggerTime > 0) {
            System.out.println(name + " cannot patrol.");
            return;
        }
        if (target != null && target.isActive()) {
            System.out.println(name + " is chasing a target, cannot patrol.");
            return;
        }
        // Move randomly in one of four directions
        String[] directions = {"up", "down", "left", "right"};
        String direction = directions[(int)(Math.random() * directions.length)];
        move(direction);
        System.out.println(name + " patrols " + direction);
    }

    // Stagger the enemy, temporarily stopping actions
    public void stagger() {
        if (!isActive) {
            System.out.println(name + " cannot be staggered.");
            return;
        }
        staggerTime = 3.0f; // Stagger for 3 seconds (or 3 update cycles, depending on game loop)
        System.out.println(name + " is staggered for " + staggerTime + " seconds!");
    }

    // Update method override to handle stagger timing
    @Override
    public void update() {
        super.update();
        if (staggerTime > 0) {
            staggerTime -= 0.1f; // Assume 0.1 seconds per update (adjust based on game loop)
            if (staggerTime <= 0) {
                System.out.println(name + " recovers from stagger.");
            }
        }
    }

    // Getter for testing
    public float getAggressionLevel() { return aggressionLevel; }
    public List<Item> getLootTable() { return lootTable; }
    public Character getTarget() { return target; }
    public float getStaggerTime() { return staggerTime; }
}