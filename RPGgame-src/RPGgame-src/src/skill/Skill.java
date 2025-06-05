package skill;

import entity.Character;

public class Skill {
    public String name;
    public int damage; // Sát thương hoặc hiệu ứng
    public int manaCost; // Chi phí mana
    public int cooldown; // Thời gian chờ (frame)
    public int currentCooldown; // Bộ đếm cooldown

    // Constructor
    public Skill(String name, int damage, int manaCost, int cooldown) {
        this.name = name != null ? name : "Default Skill";
        this.damage = damage > 0 ? damage : 10;
        this.manaCost = manaCost >= 0 ? manaCost : 0;
        this.cooldown = cooldown > 0 ? cooldown : 60;
        this.currentCooldown = 0;
    }

    // Phương thức mới để tạo hoặc cập nhật kỹ năng
    public Skill newSkill(String name, int damage, int manaCost, int cooldown) {
        this.name = name != null ? name : this.name;
        this.damage = damage > 0 ? damage : this.damage;
        this.manaCost = manaCost >= 0 ? manaCost : this.manaCost;
        this.cooldown = cooldown > 0 ? cooldown : this.cooldown;
        this.currentCooldown = 0; // Reset cooldown khi tạo kỹ năng mới
        return this; // Hỗ trợ chaining
    }

    // Kiểm tra xem kỹ năng có thể sử dụng không
    public boolean canUse() {
        return currentCooldown <= 0;
    }

    // Bắt đầu thời gian hồi chiêu
    public void startCooldown() {
        this.currentCooldown = this.cooldown;
    }

    // Sử dụng kỹ năng (khuôn mẫu cơ bản, để lớp con tùy chỉnh)
    public void use(Character user, Character target) {
        if (user == null || target == null || !canUse()) return;

        // Kiểm tra mana
        if (user.getMana() >= manaCost) {
            applyEffect(user, target); // Gọi phương thức ảo để áp dụng hiệu ứng
            user.setMana(user.getMana() - manaCost); // Giảm mana
            startCooldown(); // Bắt đầu cooldown
        }
    }

    // Phương thức ảo để áp dụng hiệu ứng (để lớp con ghi đè)
    protected void applyEffect(Character user, Character target) {
        // Mặc định gây sát thương
        if (damage > 0) {
            target.takeDamage(damage);
        }
    }

    public void updateCooldown(float deltaTime) {
        if (currentCooldown > 0) {
            currentCooldown = Math.max(0, currentCooldown - (int)(60 * deltaTime)); // Giảm dựa trên deltaTime, giả định 60 FPS
        }
    }

    // Cập nhật cooldown
    public void update(float deltaTime) {
        updateCooldown(deltaTime); // Sử dụng phương thức mới
    }

    // Getters và Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name != null ? name : "Default Skill"; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage > 0 ? damage : 10; }
    public int getManaCost() { return manaCost; }
    public void setManaCost(int manaCost) { this.manaCost = manaCost >= 0 ? manaCost : 0; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown > 0 ? cooldown : 60; }
    public int getCurrentCooldown() { return currentCooldown; }
    public void setCurrentCooldown(int currentCooldown) { this.currentCooldown = currentCooldown; }
}