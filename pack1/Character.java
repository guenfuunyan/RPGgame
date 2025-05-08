package pack1;

import java.util.List;

// ======= Common Classes =======

class Image {}

class Item {

	public Item(String string, int i) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}}

class Skill {

	public Skill(String string, int i, String string2) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getEffect() {
		// TODO Auto-generated method stub
		return null;
	}}

class Quest {}

// ======= Base Class (Merged Entity + Character) =======
public class Character {
    protected int id;
    protected String name;
    protected Vector2 position;
    protected Image sprite;
    protected boolean isActive;

    protected int hp;
    protected int maxHp;
    protected int attackPower;
    protected int defense;
    protected float speed;
    protected List<Item> inventory;

    // Constructor
    public Character(int id, String name, float x, float y, int maxHp, int attackPower, int defense, float speed) {
        this.id = id;
        this.name = name;
        this.position = new Vector2(x, y);
        this.sprite = new Image();
        this.isActive = true;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.speed = speed;
        this.inventory = new java.util.ArrayList<>();
    }

    // Update character state each frame
    public void update() {
        if (!isActive) return;
        if (hp <= 0) {
            die();
        }
        // Additional logic for updating status effects or AI can be added here
    }

    // Render character to screen (simulated with console output)
    public void render() {
        if (!isActive) return;
        System.out.println("Rendering " + name + " at position (" + position.x + ", " + position.y + ")");
        System.out.println("HP: " + hp + "/" + maxHp + ", Attack: " + attackPower + ", Defense: " + defense);
    }

    // Move to specific coordinates
    public void move(float x, float y) {
        if (!isActive) return;
        // Move with speed limit
        float distance = (float) Math.sqrt(Math.pow(x - position.x, 2) + Math.pow(y - position.y, 2));
        if (distance <= speed) {
            position.x = x;
            position.y = y;
        } else {
            // Move towards target proportional to speed
            float ratio = speed / distance;
            position.x += (x - position.x) * ratio;
            position.y += (y - position.y) * ratio;
        }
        System.out.println(name + " moved to (" + position.x + ", " + position.y + ")");
    }

    // Move in a direction (up, down, left, right)
    public void move(String direction) {
        if (!isActive) return;
        float moveDistance = speed;
        switch (direction.toLowerCase()) {
            case "up":
                position.y -= moveDistance;
                break;
            case "down":
                position.y += moveDistance;
                break;
            case "left":
                position.x -= moveDistance;
                break;
            case "right":
                position.x += moveDistance;
                break;
            default:
                System.out.println("Invalid direction: " + direction);
                return;
        }
        System.out.println(name + " moved " + direction + " to (" + position.x + ", " + position.y + ")");
    }

    // Destroy character (remove from game)
    public void destroy() {
        if (!isActive) return;
        isActive = false;
        System.out.println(name + " has been destroyed.");
    }

    // Attack a target character
    public void attack(Character target) {
        if (!isActive || !target.isActive) {
            System.out.println("Cannot attack: " + name + " or target is inactive.");
            return;
        }
        // Calculate damage: attackPower - (target.defense / 2)
        int damage = Math.max(0, attackPower - (target.defense / 2));
        System.out.println(name + " attacks " + target.name + " for " + damage + " damage.");
        target.takeDamage(damage);
    }

    // Take damage from an attack
    public void takeDamage(int amount) {
        if (!isActive) return;
        hp = Math.max(0, hp - amount);
        System.out.println(name + " takes " + amount + " damage. HP: " + hp + "/" + maxHp);
        if (hp <= 0) {
            die();
        }
    }

    // Heal the character
    public void heal(int amount) {
        if (!isActive) return;
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " heals for " + amount + ". HP: " + hp + "/" + maxHp);
    }

    // Handle character death
    public void die() {
        if (!isActive) return;
        isActive = false;
        System.out.println(name + " has died.");
    }

    // Getters for testing or external use
    public int getId() { return id; }
    public String getName() { return name; }
    public Vector2 getPosition() { return position; }
    public boolean isActive() { return isActive; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public float getSpeed() { return speed; }
    public List<Item> getInventory() { return inventory; }
}