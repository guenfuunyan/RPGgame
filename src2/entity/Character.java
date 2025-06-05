package entity;

import main.GamePanel;
import util.Animation;
import util.GameImage;
import item.Item;
import util.Vector2;
import skill.Skill;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    protected GamePanel gp;
    protected int id;
    protected String name;
    protected int worldX;
    protected int worldY;
    protected int screenX;
    protected int screenY;
    protected Vector2 position;
    protected int hp;
    protected int maxHp;
    protected int attackPower;
    protected int defense;
    protected int speed;
    protected String direction;
    protected Rectangle solidArea;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collisionOn;
    protected List<Item> inventory;
    protected boolean isActive;
    protected GameImage image;       // Hình ảnh của nhân vật
    protected int mana;              // Mana hiện tại
    protected int maxMana;           // Mana tối đa
    protected List<Skill> skills;    // Danh sách kỹ năng

    protected String state = "idle"; // trạng thái cơ bản: idle, walking, ready, attacking
    protected boolean moving = false;


    // Constructor
    public Character(GamePanel gp, int id, String name, Vector2 position, GameImage image, int hp, int maxHp, int mana, int maxMana, int attackPower, int defense, int speed) {
        this.gp = gp;
        this.id = id;
        this.name = name;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);  // Đặt vị trí màn hình mặc định
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2); // Đặt vị trí màn hình mặc định
        this.hp = hp;
        this.maxHp = maxHp;
        this.mana = mana;
        this.maxMana = maxMana;
        this.attackPower = attackPower;
        this.defense = defense;
        this.speed = speed;
        this.direction = "down";
        this.solidArea = new Rectangle(8, 8, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.collisionOn = false;
        this.inventory = new ArrayList<>();
        this.isActive = true;
        this.image = image;
        this.skills = new ArrayList<>();
    }

    // Constructor giữ nguyên

    // Thêm các phương thức trừu tượng cho animation
    protected abstract void updateAnimation();
    protected abstract GameImage getCurrentAnimationFrame();

    // Sửa lại phương thức update để bao gồm animation
    public void update() {
        // Cập nhật trạng thái di chuyển
        if (moving) {
            state = "walking";
        } else if (state.equals("walking")) {
            state = "idle";
        }

        // Cập nhật animation
        updateAnimation();
    }

    // Sửa lại phương thức draw để sử dụng frame animation
    public void draw(Graphics2D g2) {
        GameImage currentFrame = getCurrentAnimationFrame();
        if (currentFrame != null) {
            currentFrame.draw(g2, screenX, screenY);
        }
    }

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
        if (target.hp <= 0) {
            target.die();
        }
    }

    protected void performAttack(Character target) {
        int damage = calculateDamage(target);
        target.takeDamage(damage);

        if (target.getHp() <= 0) {
            target.die();
        }
    }

    protected int calculateDamage(Character target) {
        return Math.max(0, this.attackPower - target.getDefense());
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
        this.hp = Math.min(maxHp, this.hp + amount);    }

    public void die() {
        this.isActive = false;
    }

    public void interact(Item item) {
        if (item != null && isActive) {
            inventory.add(item);
        }
    }

    protected void syncPosition() {
        if (position != null) {
            worldX = position.getX();
            worldY = position.getY();
        }
    }

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
        for (Skill skill : skills) {
            if (skill != null && skill.getName().equals(skillName)) {
                skill.use(this, target);
                return;
            }
        }
    }

    // Getters và Setters
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public boolean isMoving() { return moving; }
    public void setMoving(boolean moving) { this.moving = moving; }
    public GamePanel getGp() { return gp; }
    public void setGp(GamePanel gp) { this.gp = gp; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; syncWorldPosition(); }
    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; syncWorldPosition(); }
    public int getScreenX() { return screenX; }
    public void setScreenX(int screenX) { this.screenX = screenX; }
    public int getScreenY() { return screenY; }
    public void setScreenY(int screenY) { this.screenY = screenY; }
    public Vector2 getPosition() { return position != null ? new Vector2(position.getX(), position.getY()) : null; }
    public void setPosition(Vector2 position) {
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
    }
    public void setPosition(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        syncWorldPosition();
    }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }
    public Rectangle getSolidArea() { return solidArea; }
    public void setSolidArea(Rectangle solidArea) { this.solidArea = solidArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public void setSolidAreaDefaultX(int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
    public void setSolidAreaDefaultY(int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public int getAttackPower() { return attackPower; }
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public List<Item> getInventory() { return new ArrayList<>(inventory); }
    public void setInventory(List<Item> inventory) { this.inventory = inventory != null ? new ArrayList<>(inventory) : new ArrayList<>(); }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public GameImage getImage() { return image; }
    public void setImage(GameImage image) { this.image = image; }
    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = Math.min(maxMana, Math.max(0, mana)); }
    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }
    public List<Skill> getSkills() { return new ArrayList<>(skills); }
    public void setSkills(List<Skill> skills) { this.skills = skills != null ? new ArrayList<>(skills) : new ArrayList<>(); }
}