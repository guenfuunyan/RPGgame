package quest;

import entity.Player;

public class Quest {
    private String name;
    private String description;
    private int requiredKills;
    private int requiredItems;
    private int currentKills;
    private int currentItems;

    public Quest(String name, String description, int requiredKills, int requiredItems) {
        this.name = name;
        this.description = description;
        this.requiredKills = requiredKills;
        this.requiredItems = requiredItems;
        this.currentKills = 0;
        this.currentItems = 0;
    }

    public boolean isCompleted(Player player) {
        currentKills = player.getEnemyKills();
        currentItems = (int) player.getInventory().stream().filter(item -> "Herb".equals(item.getName())).count();
        return currentKills >= requiredKills && currentItems >= requiredItems;
    }

    // Getter
    public String getName() { return name; }
    public String getDescription() { return description; }
}