package entity;

import main.GamePanel;
import util.GameImage;
import item.Item;
import util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends NPC {
    private List<Item> shopInventory;
    private int gold;

    public Merchant(GamePanel gp, int id, String name, Vector2 position, GameImage image) {
        super(gp, id, name, position, image);
        this.setDialogue("Welcome to my shop! What would you like to buy?");
        this.shopInventory = new ArrayList<>();
        this.gold = 1000;
        initializeShop();
    }

    private void initializeShop() {
        shopInventory.add(new Item("Health Potion", "Health", 20, 50, 0, true, new Vector2(0, 0), null));  // Giả định effectValue = 50 (hồi 50 HP)
        shopInventory.add(new Item("Mana Potion", "Mana", 15, 30, 0, true, new Vector2(0, 0), null));     // Giả định effectValue = 30 (hồi 30 Mana)
        shopInventory.add(new Item("Sword", "Weapon", 0, 10, 0, false, new Vector2(0, 0), null));         // effectValue = 10 (tăng 10 sát thương)
    }

    public void buy(Player player, String itemName) {
        if (player == null) return;

        Item itemToBuy = null;
        for (Item item : shopInventory) {
            if (item.getName().equals(itemName)) {
                itemToBuy = item;
                break;
            }
        }

        int price = calculatePrice(itemToBuy, true);
        if (player.getInventory().stream().anyMatch(item -> "Gold".equals(item.getType()) && item.getEffectValue() >= price)) {
            player.getInventory().add(itemToBuy);
            player.getInventory().removeIf(item -> "Gold".equals(item.getType()) && item.getEffectValue() >= price);
            gold += price;
        }
    }

    public void sell(Player player, String itemName) {
        if (player == null) return;

        Item itemToSell = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equals(itemName)) {
                itemToSell = item;
                break;
            }
        }

        int price = calculatePrice(itemToSell, false);
        if (gold >= price) {
            player.getInventory().remove(itemToSell);
            player.getInventory().add(new Item("Gold", "Gold", price, 0, 0, false, new Vector2(0, 0), null));
            gold -= price;
        }
    }

    private int calculatePrice(Item item, boolean isBuying) {
        int basePrice = 0;
        if ("Health".equals(item.getType()) || "Mana".equals(item.getType())) {
            basePrice = item.getEffectValue() * 2;
        } else if ("Weapon".equals(item.getType())) {
            basePrice = item.getSecondaryEffect() * 5;
        }
        return isBuying ? basePrice : basePrice / 2;
    }

    // Getter
    public List<Item> getShopInventory() { return new ArrayList<>(shopInventory); }
    public int getGold() { return gold; }
}