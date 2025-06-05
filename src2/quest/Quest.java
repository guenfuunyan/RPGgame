package quest;

import entity.Player;

public class Quest {
    private String name;
    private String description;
    private int requiredKills;
    private int requiredItems;
    private int currentKills;
    private int currentItems;
    private boolean completed;
    private String requiredItemName;
    private int rewardGold;
    private int rewardExp;

    public Quest(String name, String description, int requiredKills, String requiredItemName, 
                int requiredItems, int rewardGold, int rewardExp) {
        this.name = name;
        this.description = description;
        this.requiredKills = requiredKills;
        this.requiredItemName = requiredItemName;
        this.requiredItems = requiredItems;
        this.rewardGold = rewardGold;
        this.rewardExp = rewardExp;
        this.currentKills = 0;
        this.currentItems = 0;
        this.completed = false;
    }

    public void updateProgress(Player player) {
        if (!completed) {
            currentKills = player.getKillCount();
            currentItems = countRequiredItems(player);
            checkCompletion();
        }
    }

    private int countRequiredItems(Player player) {
        return (int) player.getInventory().stream()
                .filter(item -> requiredItemName.equals(item.getName()))
                .count();
    }

    private void checkCompletion() {
        completed = currentKills >= requiredKills && currentItems >= requiredItems;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getProgressText() {
        StringBuilder progress = new StringBuilder();
        if (requiredKills > 0) {
            progress.append(String.format("Kills: %d/%d", currentKills, requiredKills));
        }
        if (requiredItems > 0) {
            if (progress.length() > 0) {
                progress.append(", ");
            }
            progress.append(String.format("%s: %d/%d", requiredItemName, currentItems, requiredItems));
        }
        return progress.toString();
    }

    // Getters và Setters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getRequiredKills() { return requiredKills; }
    public int getRequiredItems() { return requiredItems; }
    public String getRequiredItemName() { return requiredItemName; }
    public int getCurrentKills() { return currentKills; }
    public int getCurrentItems() { return currentItems; }
    public int getRewardGold() { return rewardGold; }
    public int getRewardExp() { return rewardExp; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quest quest = (Quest) obj;
        return name.equals(quest.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s - %s [%s]", name, description, getProgressText());
    }
}