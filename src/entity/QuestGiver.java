package entity;

import item.Item;
import main.GamePanel;
import quest.Quest;
import util.GameImage;
import util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class QuestGiver extends NPC {
    private List<Quest> quests;

    public QuestGiver(GamePanel gp, int id, String name, Vector2 position, GameImage image) {
        super(gp, id, name, position, image);
        this.setDialogue("I have a task for you. Will you help me?");
        this.quests = new ArrayList<>();
        initializeQuests();
    }

    private void initializeQuests() {
        quests.add(new Quest("Slay 5 Enemies", "Kill 5 enemies to protect the village.", 5, 0));
        quests.add(new Quest("Collect 3 Herbs", "Gather 3 herbs for medicine.", 0, 3));
    }

    public void giveQuest(Player player, String questName) {
        Quest questToGive = null;
        for (Quest quest : quests) {
            if (quest.getName().equals(questName)) {
                questToGive = quest;
                break;
            }
        }

        if (questToGive == null) {
            gp.ui.showMessage("Quest not found: " + questName);
            return;
        }

        if (player.getQuests().contains(questToGive)) {
            gp.ui.showMessage("You already have this quest: " + questName);
            return;
        }

        player.getQuests().add(questToGive);
        gp.ui.showMessage("Quest accepted: " + questToGive.getName() + " - " + questToGive.getDescription());
    }

    public void checkQuestProgress(Player player) {
        for (Quest quest : player.getQuests()) {
            if (quests.contains(quest)) {
                if (quest.isCompleted(player)) {
                    completeQuest(player, quest);
                }
            }
        }
    }

    private void completeQuest(Player player, Quest quest) {
        player.getQuests().remove(quest);
        player.getInventory().add(new Item("Gold", "Gold", 50, 0, 0, false, new Vector2(0, 0), null));
        gp.ui.showMessage("Quest completed: " + quest.getName() + "! Received 50 gold.");
    }

    // Getter
    public List<Quest> getQuests() { return new ArrayList<>(quests); }
}