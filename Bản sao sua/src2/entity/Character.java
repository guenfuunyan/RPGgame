package entity;

import main.GamePanel;
import util.GameImage;
import item.Item;
import util.Vector2;
import skill.Skill;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity {
    public GameImage image;       // Hình ảnh của nhân vật
    private int mana;             // Mana hiện tại
    private int maxMana;          // Mana tối đa
    private List<Skill> skills;   // Danh sách kỹ năng

    public Character(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp, int mana, int maxMana, int attackPower, int defense, int speed) {
        super(gp, id, name, position, hp, maxHp, attackPower, defense, speed);
        this.image = image;
        this.mana = mana;
        this.maxMana = maxMana;
        this.skills = new ArrayList<>();
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);  // Đặt vị trí màn hình mặc định
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2); // Đặt vị trí màn hình mặc định
    }

    @Override
    public abstract void update();

    @Override
    public abstract void draw(Graphics2D g2);

    public void move(String direction) {
        this.direction = direction;
        int prevWorldX = worldX;
        int prevWorldY = worldY;

        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        // Kiểm tra va chạm
        collisionOn = false;
        if (gp != null && gp.cChecker != null) {
            gp.cChecker.checkTile(this);
        }

        if (collisionOn) {
            worldX = prevWorldX;
            worldY = prevWorldY;
        }

        syncWorldPosition();
    }

    public void attack(Character target) {
        if (target == null || !target.isActive()) return;
        int damage = Math.max(0, this.attackPower - target.defense);
        target.hp = Math.max(0, target.hp - damage);
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(this.name + " attacked " + target.name + " for " + damage + " damage!");
        } else {
            System.err.println("UI is null. Showing message in console: " + this.name + " attacked " + target.name + " for " + damage + " damage!");
        }
        if (target.hp <= 0) {
            target.die();
        }
    }

    public void takeDamage(int damage) {
        if (!isActive) return;

        int effectiveDamage = Math.max(0, damage); // Không cho phép sát thương âm
        this.hp = Math.max(0, this.hp - effectiveDamage);

        if (this.hp <= 0) {
            this.isActive = false;
        }
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, this.hp + amount);
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(name + " healed for " + amount + " HP. Current HP: " + hp);
        } else {
            System.err.println("UI is null. Showing message in console: " + name + " healed for " + amount + " HP. Current HP: " + hp);
        }
    }

    public void die() {
        this.isActive = false;
        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(name + " has died!");
            if (this instanceof Player) {
                gp.ui.setGameFinished(true); // Kết thúc game nếu là Player
            }
        } else {
            System.err.println("UI is null. Showing message in console: " + name + " has died!");
        }
    }

    @Override
    public void interact(Item item) {
        if (item != null) {
            inventory.add(item);
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage(name + " picked up " + item.getName());
            } else {
                System.err.println("UI is null. Showing message in console: " + name + " picked up " + item.getName());
            }
        }
    }

    @Override
    public void syncWorldPosition() {
        if (position != null) {
            this.position.setX(worldX);
            this.position.setY(worldY);
        }
    }

    // Cập nhật thời gian hồi chiêu cho tất cả kỹ năng
    public void updateSkills(float deltaTime) {
        if (skills != null) {
            for (Skill skill : skills) {
                if (skill != null) {
                    skill.updateCooldown(deltaTime);
                }
            }
        }
    }

    // Sử dụng kỹ năng
    public void useSkill(String skillName, Character target) {
        if (skills == null || skills.isEmpty()) {
            if (gp != null && gp.ui != null) {
                gp.ui.showMessage(name + " has no skills!");
            }
            return;
        }

        for (Skill skill : skills) {
            if (skill != null && skill.getName().equals(skillName)) {
                skill.use(this, target);
                return;
            }
        }

        if (gp != null && gp.ui != null) {
            gp.ui.showMessage(name + " does not have the skill: " + skillName);
        }
    }

    // Getters và Setters
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
    public int getMana() { return mana; } // Thêm phương thức getMana
    public void setMana(int mana) { this.mana = Math.min(maxMana, Math.max(0, mana)); }
    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }
    public List<Skill> getSkills() { return new ArrayList<>(skills); }
    public void setSkills(List<Skill> skills) { this.skills = skills != null ? new ArrayList<>(skills) : new ArrayList<>(); }
}