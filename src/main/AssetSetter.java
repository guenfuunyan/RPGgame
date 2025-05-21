package main;

import object.*;

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

        // Thêm các đối tượng
        gp.objects.add(new OBJ_Key(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(23 * gp.tileSize, 7 * gp.tileSize);

        gp.objects.add(new OBJ_Key(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(23 * gp.tileSize, 40 * gp.tileSize);

        gp.objects.add(new OBJ_Key(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(37 * gp.tileSize, 7 * gp.tileSize);

        gp.objects.add(new OBJ_Door(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(10 * gp.tileSize, 11 * gp.tileSize);

        gp.objects.add(new OBJ_Door(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(8 * gp.tileSize, 28 * gp.tileSize);

        gp.objects.add(new OBJ_Door(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(12 * gp.tileSize, 22 * gp.tileSize);

        gp.objects.add(new OBJ_Chest(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(10 * gp.tileSize, 7 * gp.tileSize);

        gp.objects.add(new OBJ_Boots(gp));
        gp.objects.get(gp.objects.size() - 1).setPosition(37 * gp.tileSize, 42 * gp.tileSize);
    }

    public void setEnemy() {
        // Thêm logic đặt kẻ địch
    }

    public void setItem() {
        // Thêm logic đặt vật phẩm
    }
}