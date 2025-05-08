package pack1;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends NPC {
    private List<Item> shopInventory;
    private String currencyType;

    // Constructor
    public Merchant(int id, String name, float x, float y, List<String> dialogue, float interactionRange) {
        super(id, name, x, y, dialogue, interactionRange);
        this.shopInventory = new ArrayList<>();
        this.currencyType = "Gold";
        // Initialize some default items for testing
        this.shopInventory.add(new Item("Health Potion", 50));
        this.shopInventory.add(new Item("Mana Crystal", 75));
        this.shopInventory.add(new Item("Iron Sword", 200));
    }

    // Buy an item from the merchant
    public void buy(Item item, Player player) {
        if (!isActive || !player.isActive() || item == null) {
            System.out.println(name + " cannot process the buy request.");
            return;
        }

        // Check if item is in stock
        if (!shopInventory.contains(item)) {
            System.out.println(name + ": Sorry, " + item.getName() + " is out of stock.");
            return;
        }

        // Check player's gold
        int itemPrice = item.getValue();
        if (player.getGold() < itemPrice) {
            System.out.println(name + ": You need " + itemPrice + " " + currencyType + ", but you have " + player.getGold() + ".");
            return;
        }

        // Check interaction range
        Vector2 merchantPos = getPosition();
        Vector2 playerPos = player.getPosition();
        if (merchantPos.distanceTo(playerPos) > interactionRange) {
            System.out.println(name + ": You are too far to trade!");
            return;
        }

        // Perform the transaction
        player.removeGold(itemPrice);
        player.addItem(item);
        shopInventory.remove(item); // Remove item from stock
        System.out.println(name + ": Sold " + item.getName() + " for " + itemPrice + " " + currencyType + " to " + player.getName() + ".");
    }

    // Sell an item to the merchant
    public void sell(Item item, Player player) {
        if (!isActive || !player.isActive() || item == null) {
            System.out.println(name + " cannot process the sell request.");
            return;
        }

        // Check if player has the item
        if (!player.getInventory().contains(item)) {
            System.out.println(name + ": You don't have " + item.getName() + " to sell.");
            return;
        }

        // Calculate sell price (e.g., 70% of value)
        int sellPrice = (int)(item.getValue() * 0.7);
        if (sellPrice <= 0) sellPrice = 1; // Minimum price

        // Check interaction range
        Vector2 merchantPos = getPosition();
        Vector2 playerPos = player.getPosition();
        if (merchantPos.distanceTo(playerPos) > interactionRange) {
            System.out.println(name + ": You are too far to trade!");
            return;
        }

        // Perform the transaction
        player.removeItem(item);
        player.addGold(sellPrice);
        shopInventory.add(item); // Add item to stock
        System.out.println(name + ": Bought " + item.getName() + " for " + sellPrice + " " + currencyType + " from " + player.getName() + ".");
    }

    // Update stock by adding or removing items randomly
    public void updateStock() {
        if (!isActive) {
            System.out.println(name + " cannot update stock.");
            return;
        }

        // 30% chance to add a new item
        if (Math.random() < 0.3) {
            Item newItem = new Item("Mystery Item " + (shopInventory.size() + 1), 100 + (int)(Math.random() * 200));
            shopInventory.add(newItem);
            System.out.println(name + ": Added " + newItem.getName() + " to stock.");
        }

        // 20% chance to remove an item
        if (!shopInventory.isEmpty() && Math.random() < 0.2) {
            Item removedItem = shopInventory.remove((int)(Math.random() * shopInventory.size()));
            System.out.println(name + ": Removed " + removedItem.getName() + " from stock.");
        }
    }

    // Getter for testing
    public List<Item> getShopInventory() { return shopInventory; }
    public String getCurrencyType() { return currencyType; }
}