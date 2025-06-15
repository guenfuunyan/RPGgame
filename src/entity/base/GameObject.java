package entity.base;

import main.GamePanel;

public class GameObject extends Entity {
    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public double useCost;
    public int price;
    public boolean stackable = false;
    public int amount = 1;
    public boolean stand;

    // GAME OBJECT TYPES
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_skill = 8;
    public final int type_obstacle = 9;

    public GameObject(GamePanel gp) {
        super(gp);
    }

    @Override
    public void use(Entity entity) {
        // To be overridden by subclasses
    }
}
