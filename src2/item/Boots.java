package item;

import entity.Character;
import entity.Player;
import util.GameImage;
import util.Vector2;

import java.awt.Graphics2D;

public class Boots extends Item {
    private int effectDuration; // Thời gian hiệu ứng (tính bằng frame, ví dụ: 60 FPS * 10 giây = 600)

    // Constructor đầy đủ
    public Boots(String name, int value, int speedBoost, int effectDuration, Vector2 position, GameImage image) {
        super(name, "Boots", value, 0, speedBoost, false, position, image); // Không tiêu thụ, secondaryEffect cho speedBoost
        this.effectDuration = effectDuration > 0 ? effectDuration : 600; // Mặc định 10 giây (60 FPS * 10)
    }

    // Constructor mặc định
    public Boots(String name, int value, int speedBoost) {
        this(name, value, speedBoost, 600, new Vector2(0, 0), null); // Mặc định 10 giây
    }

    public void applyItemEffect(Character character) {
        if (character != null && character instanceof Player) {
            Player player = (Player) character;
            player.applySpeedBoost(getSecondaryEffect(), effectDuration); // Truyền cả speedBoost và effectDuration
        }
    }

    @Override
    public void draw(Graphics2D g2, int screenX, int screenY) {
        super.draw(g2, screenX, screenY);
    }

    // Getters và Setters
    public int getEffectDuration() { return effectDuration; }
    public void setEffectDuration(int effectDuration) { this.effectDuration = effectDuration > 0 ? effectDuration : 600; }
}