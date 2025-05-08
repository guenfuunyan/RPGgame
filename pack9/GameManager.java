package pack9;

import pack1.*;
import pack1.Character;

import java.util.*;

// Package 9: Game Management
public class GameManager {
    private String currentState;
    private List<Player> players;
    private List<Enemy> enemies;
    private List<NPC> npcs;
    private float gameTime;
    private int score;
    private int waveNumber;

    // Constructor
    public GameManager() {
        this.currentState = "Playing";
        this.players = new ArrayList<Player>();
        this.enemies = new ArrayList<Enemy>();
        this.npcs = new ArrayList<NPC>();
        this.gameTime = 0.0f;
        this.score = 0;
        this.waveNumber = 1;
    }

    // Start the game
    public void startGame() {
        currentState = "Playing";
        gameTime = 0.0f;
        score = 0;
        waveNumber = 1;
        Player defaultPlayer = new Player(1, "Hero", 0, 0, 100, 20, 10, 5.0f);
        players.add(defaultPlayer);
        System.out.println("Game started! Welcome, " + defaultPlayer.getName());
    }

    // Pause the game
    public void pauseGame() {
        if (currentState.equals("Playing")) {
            currentState = "Paused";
            System.out.println("Game paused.");
        } else {
            System.out.println("Cannot pause: Game is in " + currentState + " state.");
        }
    }

    // Resume the game
    public void resumeGame() {
        if (currentState.equals("Paused")) {
            currentState = "Playing";
            System.out.println("Game resumed.");
        } else {
            System.out.println("Cannot resume: Game is in " + currentState + " state.");
        }
    }

    // End the game
    public void endGame() {
        currentState = "GameOver";
        System.out.println("Game over! Final score: " + score + ", Time: " + gameTime + "s, Wave: " + waveNumber);
        players.clear();
        enemies.clear();
        npcs.clear();
    }

    // Spawn an enemy
    public void spawnEnemy(String enemyType, Vector2 position) {
        if (!currentState.equals("Playing")) return;
        Enemy enemy;
        if (enemyType.equalsIgnoreCase("Boss")) {
            enemy = new Boss(100 + enemies.size(), "Boss " + (enemies.size() + 1), position.x, position.y, 200, 30, 15, 3.0f);
        } else {
            enemy = new Enemy(100 + enemies.size(), "Enemy " + (enemies.size() + 1), position.x, position.y, 50, 15, 5, 4.0f);
        }
        enemies.add(enemy);
        System.out.println(enemyType + " spawned at (" + position.x + ", " + position.y + ")");
    }

    // Update game state
    public void updateGameState(String newState) {
        if (newState.equals("Playing") || newState.equals("Paused") || newState.equals("GameOver")) {
            currentState = newState;
            System.out.println("Game state updated to: " + currentState);
        } else {
            System.out.println("Invalid game state: " + newState);
        }
    }

    // Handle player input
    public void handleInput(String input) {
        if (!currentState.equals("Playing") || players.isEmpty()) return;
        Player player = players.get(0);
        switch (input.toLowerCase()) {
            case "move_up":
                player.move("up");
                break;
            case "move_down":
                player.move("down");
                break;
            case "move_left":
                player.move("left");
                break;
            case "move_right":
                player.move("right");
                break;
            case "attack":
                Enemy nearestEnemy = findNearestEnemy(player);
                if (nearestEnemy != null) {
                    player.attack(nearestEnemy);
                } else {
                    System.out.println("No enemies nearby to attack!");
                }
                break;
            default:
                System.out.println("Invalid input: " + input);
        }
    }

    // Update game each frame
    public void update(float deltaTime) {
        if (!currentState.equals("Playing")) return;
        gameTime += deltaTime;
        score += (int)(deltaTime * 10);

        for (Player player : players) {
            player.update();
        }
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.isActive() && !players.isEmpty()) {
                enemy.chase(players.get(0));
            }
        }
        for (NPC npc : npcs) {
            npc.update();
        }

        enemies.removeIf(enemy -> !enemy.isActive());
        players.removeIf(player -> !player.isActive());
        npcs.removeIf(npc -> !npc.isActive());

        if (players.isEmpty()) {
            endGame();
        }
    }

    // Helper: Find nearest enemy to player
    private Enemy findNearestEnemy(Player player) {
        Enemy nearest = null;
        float minDistance = Float.MAX_VALUE;
        Vector2 playerPos = player.getPosition();
        for (Enemy enemy : enemies) {
            if (!enemy.isActive()) continue;
            Vector2 enemyPos = enemy.getPosition();
            float distance = (float) Math.sqrt(Math.pow(playerPos.x - enemyPos.x, 2) + Math.pow(playerPos.y - enemyPos.y, 2));
            if (distance < minDistance && distance <= 5.0f) {
                minDistance = distance;
                nearest = enemy;
            }
        }
        return nearest;
    }

    // Getters
    public String getCurrentState() { return currentState; }
    public List<Player> getPlayers() { return players; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<NPC> getNpcs() { return npcs; }
    public float getGameTime() { return gameTime; }
    public int getScore() { return score; }
    public int getWaveNumber() { return waveNumber; }
}

class SaveSystem {
    private String saveFilePath;
    private float autoSaveInterval;
    private float timeSinceLastSave;

    public SaveSystem(String saveFilePath, float autoSaveInterval) {
        this.saveFilePath = saveFilePath;
        this.autoSaveInterval = autoSaveInterval;
        this.timeSinceLastSave = 0.0f;
    }

    public void saveGame(Player player, GameManager gameManager) {
        if (player == null || gameManager == null) {
            System.out.println("Cannot save: Invalid player or game manager.");
            return;
        }
        System.out.println("Saving game to: " + saveFilePath);
        System.out.println("Player: " + player.getName() + ", HP: " + player.getHp() + ", Position: (" + 
                          player.getPosition().x + ", " + player.getPosition().y + ")");
        System.out.println("Game: State: " + gameManager.getCurrentState() + ", Score: " + gameManager.getScore() + 
                          ", Time: " + gameManager.getGameTime() + ", Wave: " + gameManager.getWaveNumber());
        System.out.println("Enemies: " + gameManager.getEnemies().size() + ", NPCs: " + gameManager.getNpcs().size());
        timeSinceLastSave = 0.0f;
    }

    public Map<String, Object> loadGame() {
        System.out.println("Loading game from: " + saveFilePath);
        Map<String, Object> gameData = new HashMap<>();
        gameData.put("player_name", "Hero");
        gameData.put("player_hp", 100);
        gameData.put("player_x", 0.0f);
        gameData.put("player_y", 0.0f);
        gameData.put("game_state", "Playing");
        gameData.put("score", 0);
        gameData.put("game_time", 0.0f);
        gameData.put("wave_number", 1);
        System.out.println("Loaded game data: " + gameData);
        return gameData;
    }

    public void deleteSave() {
        System.out.println("Deleted save file: " + saveFilePath);
        saveFilePath = null;
    }

    public void autoSave(Player player, GameManager gameManager, float deltaTime) {
        timeSinceLastSave += deltaTime;
        if (timeSinceLastSave >= autoSaveInterval) {
            saveGame(player, gameManager);
        }
    }

    public String getSaveFilePath() { return saveFilePath; }
    public float getAutoSaveInterval() { return autoSaveInterval; }
}

class Collision {
    private List<Character> collidables;
    private Map<String, List<Character>> collisionMap;

    public Collision() {
        this.collidables = new ArrayList<>();
        this.collisionMap = new HashMap<>();
    }

    public boolean checkCollision(Character entity1, Character entity2) {
        if (!entity1.isActive() || !entity2.isActive()) return false;
        Vector2 pos1 = entity1.getPosition();
        Vector2 pos2 = entity2.getPosition();
        float distance = (float) Math.sqrt(Math.pow(pos1.x - pos2.x, 2) + Math.pow(pos1.y - pos2.y, 2));
        return distance < 1.0f;
    }

    public void resolveCollision(Character entity) {
        if (!entity.isActive()) return;
        for (Character other : collidables) {
            if (other == entity || !other.isActive()) continue;
            if (checkCollision(entity, other)) {
                System.out.println(entity.getName() + " collided with " + other.getName() + ". Movement blocked.");
            }
        }
    }

    public void handlePlayerCollision(Player player, Character object) {
        if (!player.isActive() || !object.isActive()) return;
        if (checkCollision(player, object)) {
            if (object instanceof Enemy) {
                player.takeDamage(10);
                System.out.println(player.getName() + " collided with " + object.getName() + " and took 10 damage.");
            } else if (object instanceof NPC) {
                player.interact(object);
            }
        }
    }

    public void handleEnemyCollision(Enemy enemy, Character object) {
        if (!enemy.isActive() || !object.isActive()) return;
        if (checkCollision(enemy, object)) {
            if (object instanceof Player) {
                enemy.attack((Player) object);
            } else if (object instanceof Enemy) {
                System.out.println(enemy.getName() + " collided with another enemy: " + object.getName());
            }
        }
    }

    public void updateCollisions() {
        collisionMap.clear();
        collisionMap.put("map", new ArrayList<>(collidables));

        for (Character entity : collidables) {
            if (!entity.isActive()) continue;
            if (entity instanceof Player) {
                for (Character other : collidables) {
                    if (other != entity) {
                        handlePlayerCollision((Player) entity, other);
                    }
                }
            } else if (entity instanceof Enemy) {
                for (Character other : collidables) {
                    if (other != entity) {
                        handleEnemyCollision((Enemy) entity, other);
                    }
                }
            }
        }
    }

    public void addCollidable(Character entity) {
        if (!collidables.contains(entity)) {
            collidables.add(entity);
        }
    }

    public List<Character> getCollidables() { return collidables; }
}