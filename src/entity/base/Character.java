package entity.base;

import main.GamePanel;

public abstract class Character extends Entity {
    // CHARACTER ATTRIBUTES
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int barWidth;
    public int barHeight;
    public double maxMana;
    public double mana;
    public int sizeRatio;
    public long lastManaRestoreTime;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int healingAmount;
    public int critPercent;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int id; // NPC id: id = 1 impact lore, id = 0 no impact
    public boolean thanhKiem;
    
    public Character(GamePanel gp) {
        super(gp);
    }
    
    @Override
    public void setAction() {
        // To be overridden by subclasses
    }
    
    @Override
    public void damageReaction() {
        // To be overridden by subclasses
    }
    
    @Override
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    
    @Override
    public void checkDrop() {
        // To be overridden by subclasses
    }
}