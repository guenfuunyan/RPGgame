package skill;

import entity.Character;

public abstract class Skill {
    protected String name;          // Tên kỹ năng
    protected int damage;           // Sát thương của kỹ năng
    protected int manaCost;         // Chi phí mana để sử dụng
    protected float cooldown;       // Thời gian hồi chiêu (giây)
    protected float cooldownTimer;  // Bộ đếm thời gian hồi chiêu hiện tại
    protected boolean isReady;      // Trạng thái sẵn sàng để sử dụng

    public Skill(String name, int damage, int manaCost, float cooldown) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.cooldownTimer = 0;
        this.isReady = true;
    }

    // Phương thức trừu tượng để sử dụng kỹ năng
    public abstract void use(Character user, Character target);

    // Cập nhật thời gian hồi chiêu
    public void updateCooldown(float deltaTime) {
        if (!isReady) {
            cooldownTimer -= deltaTime;
            if (cooldownTimer <= 0) {
                isReady = true;
                cooldownTimer = 0;
            }
        }
    }

    // Kiểm tra kỹ năng có sẵn sàng không
    public boolean canUse() {
        return isReady;
    }

    // Đặt kỹ năng vào trạng thái hồi chiêu
    protected void startCooldown() {
        isReady = false;
        cooldownTimer = cooldown;
    }

    // Getters và Setters
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getManaCost() { return manaCost; }
    public float getCooldown() { return cooldown; }
    public float getCooldownTimer() { return cooldownTimer; }
    public boolean isReady() { return isReady; }
}