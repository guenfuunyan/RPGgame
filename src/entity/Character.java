package entity;

import main.GamePanel;
import util.GameImage;
import util.Item;
import util.Vector2;
import skill.Skill;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Entity {
    protected GamePanel gp;
    protected int id;
    protected String name;
    protected int worldX, worldY;
    protected int screenX, screenY;
    protected Vector2 position;
    protected GameImage image;
    protected int hp, maxHp;
    protected int attackPower, defense;
    protected int speed;
    protected List<Item> inventory;
    protected List<Skill> skills; // Danh sách kỹ năng
    protected boolean isActive;
    protected boolean collisionOn;
    protected Rectangle solidArea;
    protected int solidAreaDefaultX, solidAreaDefaultY; // Thêm thuộc tính mặc định cho solidArea
    protected String direction;

    public Character(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp, int attackPower, int defense, int speed) {
        super(gp, id, name, position, hp, maxHp, attackPower, defense, speed);
        this.gp = gp;
        this.id = id;
        this.name = name;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // Khởi tạo vị trí trung tâm màn hình
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        this.image = image;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.speed = speed;
        this.inventory = new ArrayList<>();
        this.skills = new ArrayList<>(); // Khởi tạo danh sách kỹ năng
        this.isActive = true;
        this.collisionOn = false;
        this.solidArea = new Rectangle(8, 8, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.direction = "down";
    }

    public abstract void update();

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
        System.out.println(this.name + " attacked " + target.name + " for " + damage + " damage!");
        if (target.hp <= 0) {
            target.die();
        }
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, this.hp + amount);
        System.out.println(name + " healed for " + amount + " HP. Current HP: " + hp);
    }

    public void die() {
        this.isActive = false;
        System.out.println(name + " has died!");
    }

    public void interact(Item item) {
        if (item != null) {
            inventory.add(item);
            System.out.println(name + " picked up " + item.getName());
        }
    }

    public void syncWorldPosition() {
        if (position != null) {
            this.position.setX(worldX);
            this.position.setY(worldY);
        }
    }

    // Cập nhật thời gian hồi chiêu cho tất cả kỹ năng
    public void updateSkills(int deltaTime) {
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
        if (skills != null) {
            for (Skill skill : skills) {
                if (skill != null && skill.getName().equals(skillName)) {
                    skill.use(this, target);
                    break;
                }
            }
        }
    }

    // Getters và Setters
    public GamePanel getGp() { return gp; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; syncWorldPosition(); }
    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; syncWorldPosition(); }
    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }
    public Vector2 getPosition() { return position != null ? new Vector2(position.getX(), position.getY()) : null; }
    public void setPosition(Vector2 position) {
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
    }
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public List<Item> getInventory() { return new ArrayList<>(inventory); }
    public void setInventory(List<Item> inventory) { this.inventory = inventory != null ? new ArrayList<>(inventory) : new ArrayList<>(); }
    public List<Skill> getSkills() { return new ArrayList<>(skills); }
    public void setSkills(List<Skill> skills) { this.skills = skills != null ? new ArrayList<>(skills) : new ArrayList<>(); }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }
    public Rectangle getSolidArea() { return solidArea; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
}