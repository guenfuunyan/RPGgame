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

    public void initializeGame() {
        setObject();
        setEnemy(); 
        setItem();
    }

    protected void setObject() {
        if (gp == null || gp.objects == null) {
            System.err.println("GamePanel or objects list is null in AssetSetter");
            return;
        }

        gp.objects.clear();
        initKeys();
        initDoors();
        initChests();
        initBoots();
        initArmors();
        initShields();
    }

    private void initKeys() {
        Vector2[] keyPositions = {
            new Vector2(23, 7),
            new Vector2(23, 40),
            new Vector2(37, 7)
        };

        for (Vector2 pos : keyPositions) {
            gp.objects.add(new KeyItem(
                "Key",
                50,
                "wooden",
                "common", 
                new Vector2(pos.getX() * gp.tileSize, pos.getY() * gp.tileSize),
                new GameImage("/Objects/key.png", gp.tileSize, gp.tileSize)
            ));
        }
    }

    private void initDoors() {
        Vector2[] doorPositions = {
            new Vector2(10, 11),
            new Vector2(8, 28),
            new Vector2(12, 22)
        };

        for (Vector2 pos : doorPositions) {
            gp.objects.add(new Door(
                "Wooden Door",
                0,
                "wooden",
                new Vector2(pos.getX() * gp.tileSize, pos.getY() * gp.tileSize),
                new GameImage("/Objects/door.png", gp.tileSize, gp.tileSize)
            ));
        }
    }

    private void initChests() {
        gp.objects.add(new Chest(
            "Treasure Chest",
            100,
            new Vector2(10 * gp.tileSize, 7 * gp.tileSize),
            new GameImage("/Objects/chest.png", gp.tileSize, gp.tileSize)
        ));
    }

    private void initBoots() {
        gp.objects.add(new Boots(
            "Speed Boots",
            20,
            5,
            600,
            new Vector2(37 * gp.tileSize, 42 * gp.tileSize),
            new GameImage("/Objects/boots.png", gp.tileSize, gp.tileSize)
        ));
    }

    private void initArmors() {
        gp.objects.add(new Armor(
            "Leather Armor",
            80,
            10,
            20,
            100,
            new Vector2(15 * gp.tileSize, 15 * gp.tileSize),
            new GameImage("/Objects/shield_blue.png", gp.tileSize, gp.tileSize)
        ));
    }

    private void initShields() {
        gp.objects.add(new Shield(
            "Wooden Shield",
            60,
            5,
            100,
            0.3,
            new Vector2(18 * gp.tileSize, 18 * gp.tileSize),
            new GameImage("/Objects/shield_wood.png", gp.tileSize, gp.tileSize)
        ));
    }

    protected void setEnemy() {
        if (gp == null || gp.enemies == null) {
            System.err.println("GamePanel or enemies list is null in AssetSetter");
            return;
        }
        gp.enemies.clear();
        initBosses();
        initNormalEnemies();
    }

    private void initBosses() {
        gp.enemies.add(new Boss(
                gp,                    // GamePanel
                1,                     // id
                "Dark Overlord",       // name
                new Vector2(30 * gp.tileSize, 30 * gp.tileSize),  // position
                new GameImage("/enemies/orc_down_1.png", gp.tileSize * 2, gp.tileSize * 2),  // image
                500,                   // hp
                500,                   // maxHp
                30,                    // attackPower
                15,                    // defense
                2,                     // speed
                0.8f,                  // aggressionLevel
                new ArrayList<>(),     // lootTable
                120,                   // detectionRange
                300,                    // rageThreshold
                120                    // specialAttackCooldown
        ));
    }

    private void initNormalEnemies() {
        Vector2[] enemyPositions = {
                new Vector2(10, 10),
                new Vector2(15, 15),
                new Vector2(20, 20)
        };

        for (Vector2 pos : enemyPositions) {
            gp.enemies.add(new Enemy(
                    gp,                // GamePanel
                    2,                // id
                    "Goblin",         // name
                    new Vector2(pos.getX() * gp.tileSize, pos.getY() * gp.tileSize),  // position
                    new GameImage("/enemies/skeletonlord_down_1.png", gp.tileSize, gp.tileSize),  // image
                    100,              // hp
                    100,              // maxHp
                    15,               // attackPower
                    5,                // defense
                    3,                // speed
                    0.5f,             // aggressionLevel
                    new ArrayList<>(), // lootTable
                    200               // detectionRange
            ));
        }

        // Thêm Skeleton
        gp.enemies.add(new Enemy(
                gp,
                3,
                "Skeleton",
                new Vector2(25 * gp.tileSize, 25 * gp.tileSize),
                new GameImage("/enemies/skeleton_down_1.png", gp.tileSize, gp.tileSize),
                80,               // hp
                80,               // maxHp
                12,              // attackPower
                3,               // defense
                4,               // speed
                0.7f,            // aggressionLevel
                new ArrayList<>(),
                180              // detectionRange
        ));
    }



    protected void setItem() {
        if (gp == null || gp.items == null) {
            System.err.println("GamePanel or items list is null in AssetSetter");
            return;
        }
        gp.items.clear();
        initWeapons();
        initPotions();
    }

    private void initWeapons() {
        gp.items.add(new Sword(
            "Iron Sword",
            70,
            15,
            100,
            10,
            0.15,
            new Vector2(22 * gp.tileSize, 22 * gp.tileSize),
            new GameImage("/Objects/sword_normal.png", gp.tileSize, gp.tileSize)
        ));

        gp.items.add(new Bow(
            "Wooden Bow",
            80,
            12,
            100,
            0,
            150,
            new Vector2(25 * gp.tileSize, 25 * gp.tileSize),
            new GameImage("/Objects/pickaxe.png", gp.tileSize, gp.tileSize)
        ));

        gp.items.add(new MagicWand(
            "Beginner Wand",
            90,
            10,
            100,
            0,
            0.1,
            new Vector2(28 * gp.tileSize, 28 * gp.tileSize),
            new GameImage("/Objects/manacrystal_full.png", gp.tileSize, gp.tileSize)
        ));
    }

    private void initPotions() {
        gp.items.add(new Potion(
            "Health Potion",
            10,
            50,
            0,
            "health",
            new Vector2(30 * gp.tileSize, 30 * gp.tileSize),
            new GameImage("/Objects/potion_red.png", gp.tileSize, gp.tileSize)
        ));
    }
}