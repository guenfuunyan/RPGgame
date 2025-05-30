package model;

public class Player {
    private double hp;
    private double mp;
    private double stamina;

    public Player() {
        this.hp = 1.0;       // 100%
        this.mp = 1.0;
        this.stamina = 1.0;
    }

    public double getHp() { return hp; }
    public double getMp() { return mp; }
    public double getStamina() { return stamina; }

    public void takeDamage(double amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(double amount) {
        hp = Math.min(1.0, hp + amount);
    }
}
