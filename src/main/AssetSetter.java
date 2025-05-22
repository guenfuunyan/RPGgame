package main;

import entity.Boss;
import entity.Enemy;
import item.*;
import util.GameImage;
import util.Vector2;

import java.util.ArrayList;

public class AssetSetter {
    private GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        if (gp == null || gp.objects == null) {
            System.err.println("GamePanel or objects list is null in AssetSetter");
            return;
        }

        gp.objects.clear();

        // Thêm KeyItem
        gp.objects.add(new KeyItem("Key", 50, "wooden", "common", new Vector2(23 * gp.tileSize, 7 * gp.tileSize),
                new GameImage("/objects/key.png", gp.tileSize, gp.tileSize)));
        gp.objects.add(new KeyItem("Key", 50, "wooden", "common", new Vector2(23 * gp.tileSize, 40 * gp.tileSize),
                new GameImage("/objects/key.png", gp.tileSize, gp.tileSize)));
        gp.objects.add(new KeyItem("Key", 50, "wooden", "common", new Vector2(37 * gp.tileSize, 7 * gp.tileSize),
                new GameImage("/objects/key.png", gp.tileSize, gp.tileSize)));

        // Thêm Door
        gp.objects.add(new Door("Wooden Door", 0, "wooden", new Vector2(10 * gp.tileSize, 11 * gp.tileSize),
                new GameImage("/objects/door.png", gp.tileSize, gp.tileSize)));
        gp.objects.add(new Door("Wooden Door", 0, "wooden", new Vector2(8 * gp.tileSize, 28 * gp.tileSize),
                new GameImage("/objects/door.png", gp.tileSize, gp.tileSize)));
        gp.objects.add(new Door("Wooden Door", 0, "wooden", new Vector2(12 * gp.tileSize, 22 * gp.tileSize),
                new GameImage("/objects/door.png", gp.tileSize, gp.tileSize)));

        // Thêm Chest
        gp.objects.add(new Chest("Treasure Chest", 100, new Vector2(10 * gp.tileSize, 7 * gp.tileSize),
                new GameImage("/objects/chest.png", gp.tileSize, gp.tileSize)));

        // Thêm Boots
        gp.objects.add(new Boots("Speed Boots", 20, 5, 600, new Vector2(37 * gp.tileSize, 42 * gp.tileSize),
                new GameImage("/objects/boots.png", gp.tileSize, gp.tileSize)));

        // Thêm Armor
        gp.objects.add(new Armor("Leather Armor", 80, 10, 20, 100, new Vector2(15 * gp.tileSize, 15 * gp.tileSize),
                new GameImage("/objects/armor.png", gp.tileSize, gp.tileSize)));

        // Thêm Shield
        gp.objects.add(new Shield("Wooden Shield", 60, 5, 100, 0.3, new Vector2(18 * gp.tileSize, 18 * gp.tileSize),
                new GameImage("/objects/shield.png", gp.tileSize, gp.tileSize)));
    }

    public void setEnemy() {
        if (gp == null || gp.enemies == null) {
            System.err.println("GamePanel or enemies list is null in AssetSetter");
            return;
        }
        gp.enemies.clear();

        // Thêm một Boss
        gp.enemies.add(new Boss(
                gp,
                1, // ID
                "Dark Overlord", // Tên
                new Vector2(gp.tileSize * 30, gp.tileSize * 30), // Vị trí
                new GameImage("/enemies/boss.png", gp.tileSize * 2, gp.tileSize * 2), // Hình ảnh (lớn hơn enemy thường)
                500, 500, // HP và maxHP
                30, 15, 2, // attackPower, defense, speed
                0.8f, // aggressionLevel
                new ArrayList<>(), // lootTable (có thể thêm sau)
                120, // staggerTime (2 giây)
                300, // detectionRange (300 pixel)
                2.0f, // powerMultiplier (tăng gấp đôi sức mạnh)
                "Dark Blast" // specialSkillName
        ));

        // Thêm Enemy thường (ví dụ)
        gp.enemies.add(new Enemy(
                gp,
                2, // ID
                "Goblin", // Tên
                new Vector2(gp.tileSize * 10, gp.tileSize * 10), // Vị trí
                new GameImage("/enemies/goblin.png", gp.tileSize, gp.tileSize), // Hình ảnh
                100, 100, // HP và maxHP
                15, 5, 3, // attackPower, defense, speed
                0.5f, // aggressionLevel
                new ArrayList<>(), // lootTable
                60, // staggerTime (1 giây)
                200 // detectionRange
        ));
    }

    public void setItem() {
        if (gp == null || gp.items == null) {
            System.err.println("GamePanel or items list is null in AssetSetter");
            return;
        }
        gp.items.clear();

        // Thêm Scroll
        gp.items.add(new Scroll("Fireball Scroll", 50, "Fireball", new Vector2(20 * gp.tileSize, 20 * gp.tileSize),
                new GameImage("/items/scroll.png", gp.tileSize, gp.tileSize)));

        // Thêm Weapon
        gp.items.add(new Sword("Iron Sword", 70, 15, 100, 10, 0.15, new Vector2(22 * gp.tileSize, 22 * gp.tileSize),
                new GameImage("/items/sword.png", gp.tileSize, gp.tileSize)));
        gp.items.add(new Bow("Wooden Bow", 80, 12, 100, 0, 150, new Vector2(25 * gp.tileSize, 25 * gp.tileSize),
                new GameImage("/items/bow.png", gp.tileSize, gp.tileSize)));
        gp.items.add(new MagicWand("Beginner Wand", 90, 10, 100, 0, 0.1, new Vector2(28 * gp.tileSize, 28 * gp.tileSize),
                new GameImage("/items/wand.png", gp.tileSize, gp.tileSize)));

        // Thêm Potion
        gp.items.add(new Potion("Health Potion", 10, 50, 0, "health", new Vector2(30 * gp.tileSize, 30 * gp.tileSize), new GameImage("/items/potion.png", gp.tileSize, gp.tileSize)));
    }
}