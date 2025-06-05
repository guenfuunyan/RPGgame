package main;

import entity.Character;
import entity.Enemy;
import entity.Player;
import item.Item;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public int checkObject(Player entity, Item item) {
        if (entity == null || item == null) return 0;

        // Tọa độ của Entity (bao gồm solidArea)
        int entityLeftWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x);
        int entityRightWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width);
        int entityTopWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y);
        int entityBottomWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height);

        // Tọa độ của Item (sử dụng position, giả định kích thước 16x16 như trong checkItem)
        int itemLeftWorldX = (int) item.getPosition().getX();
        int itemRightWorldX = (int) (item.getPosition().getX() + 16); // Kích thước mặc định 16x16
        int itemTopWorldY = (int) item.getPosition().getY();
        int itemBottomWorldY = (int) (item.getPosition().getY() + 16);

        // Kiểm tra va chạm
        if (entityLeftWorldX >= itemRightWorldX || entityRightWorldX <= itemLeftWorldX ||
                entityTopWorldY >= itemBottomWorldY || entityBottomWorldY <= itemTopWorldY) {
            return 0; // Không va chạm
        }
        return 1; // Va chạm
    }

    public boolean checkEntity(Player entity1, Enemy entity2) {
        if (entity1 == null || entity2 == null) return false;

        int entity1LeftWorldX = (int) (entity1.getWorldX() + entity1.getSolidArea().x);
        int entity1RightWorldX = (int) (entity1.getWorldX() + entity1.getSolidArea().x + entity1.getSolidArea().width);
        int entity1TopWorldY = (int) (entity1.getWorldY() + entity1.getSolidArea().y);
        int entity1BottomWorldY = (int) (entity1.getWorldY() + entity1.getSolidArea().y + entity1.getSolidArea().height);

        int entity2LeftWorldX = (int) (entity2.getWorldX() + entity2.getSolidArea().x);
        int entity2RightWorldX = (int) (entity2.getWorldX() + entity2.getSolidArea().x + entity2.getSolidArea().width);
        int entity2TopWorldY = (int) (entity2.getWorldY() + entity2.getSolidArea().y);
        int entity2BottomWorldY = (int) (entity2.getWorldY() + entity2.getSolidArea().y + entity2.getSolidArea().height);

        return entity1LeftWorldX < entity2RightWorldX && entity1RightWorldX > entity2LeftWorldX &&
                entity1TopWorldY < entity2BottomWorldY && entity1BottomWorldY > entity2TopWorldY;
    }

    public boolean checkItem(Player entity, Item item) {
        if (entity == null || item == null) return false;

        int entityLeftWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x);
        int entityRightWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width);
        int entityTopWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y);
        int entityBottomWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height);

        int itemLeftWorldX = (int) (item.getPosition().getX());
        int itemRightWorldX = (int) (item.getPosition().getX() + 16); // Giả sử kích thước mặc định 16x16
        int itemTopWorldY = (int) (item.getPosition().getY());
        int itemBottomWorldY = (int) (item.getPosition().getY() + 16);

        return entityLeftWorldX < itemRightWorldX && entityRightWorldX > itemLeftWorldX &&
                entityTopWorldY < itemBottomWorldY && entityBottomWorldY > itemTopWorldY;
    }

    public void checkTile(Character character) {
        int characterLeftWorldX = character.getWorldX() + character.getSolidArea().x;
        int characterRightWorldX = characterLeftWorldX + character.getSolidArea().width;
        int characterTopWorldY = character.getWorldY() + character.getSolidArea().y;
        int characterBottomWorldY = characterTopWorldY + character.getSolidArea().height;

        int characterLeftCol = characterLeftWorldX / gp.tileSize;
        int characterRightCol = characterRightWorldX / gp.tileSize;
        int characterTopRow = characterTopWorldY / gp.tileSize;
        int characterBottomRow = characterBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (character.getDirection()) {
            case "up":
                characterTopRow = (characterTopWorldY - (int)character.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.setCollisionOn(true);
                }
                break;
            case "down":
                characterBottomRow = (characterBottomWorldY + (int)character.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.setCollisionOn(true);
                }
                break;
            case "left":
                characterLeftCol = (characterLeftWorldX - (int)character.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterLeftCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.setCollisionOn(true);
                }
                break;
            case "right":
                characterRightCol = (characterRightWorldX + (int)character.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[characterRightCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[characterRightCol][characterBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    character.setCollisionOn(true);
                }
                break;
        }
    }
}