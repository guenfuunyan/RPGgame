package entity;

import main.GamePanel;
import util.Item;
import util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected GamePanel gp;
    protected int id;
    protected String name;
    protected int worldX;
    protected int worldY;
    protected int screenX; // Thêm screenX
    protected int screenY; // Thêm screenY
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
    protected boolean collisionOn; // Thêm collisionOn
    protected List<Item> inventory;
    protected boolean isActive;

    // Constructor
    public Entity(GamePanel gp, int id, String name, Vector2 position, int hp, int maxHp, int attackPower, int defense, int speed) {
        this.gp = gp;
        this.id = id;
        this.name = name;
        this.position = position != null ? new Vector2(position.getX(), position.getY()) : new Vector2(0, 0);
        this.worldX = this.position.getX();
        this.worldY = this.position.getY();
        this.screenX = 0; // Khởi tạo mặc định
        this.screenY = 0; // Khởi tạo mặc định
        this.hp = hp;
        this.maxHp = maxHp;
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
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);

    public void interact(Item item) {
        if (item != null && isActive) {
            inventory.add(item);
            System.out.println(name + " picked up " + item.getName());
        }
    }

    public void takeDamage(int amount) {
        if (!isActive) return;
        int damage = Math.max(0, amount - defense);
        hp = Math.max(0, hp - damage);
        System.out.println(name + " takes " + damage + " damage, HP: " + hp);
        if (hp == 0) {
            die();
        }
    }

    public void heal(int amount) {
        if (!isActive) return;
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " heals for " + amount + ", HP: " + hp);
    }

    protected void die() {
        System.out.println(name + " has died");
        isActive = false; // Đặt trạng thái không hoạt động
    }

    protected void syncPosition() {
        if (position != null) {
            worldX = position.getX();
            worldY = position.getY();
        }
    }

    protected void syncWorldPosition() {
        if (position != null) {
            position.setX(worldX);
            position.setY(worldY);
        }
    }

    // Getters và Setters
    public int getId() { return id; }
    public String getName() { return name; }
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
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
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
}