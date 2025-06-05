package main;

import entity.Player;
import entity.Enemy;
import tile.TileManager;
import util.GameImage;
import item.Chest;
import item.Item;
import util.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    public Player player;
    public List<Item> objects = new ArrayList<>(); // Thay SuperObject bằng Item
    public List<Item> items = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        setupGame();
    }

    public void setupGame() {
        player = new Player(this, keyH);
        aSetter.setObject();

        List<Item> loot = new ArrayList<>();
        loot.add(new Item("Potion", "Health", 10, 20, 0, true));
        enemies.add(new Enemy(this, 2, "Enemy1", new Vector2(tileSize * 10, tileSize * 10),
                new GameImage("/enemies/enemy1.png", tileSize, tileSize), 50, 50, 15, 5, 2,
                1, loot, 2, 200));

        // Thêm Chest vào objects (thay thế OBJ_Chest)
        objects.add(new Chest("Treasure Chest", 100, new Vector2(tileSize * 15, tileSize * 15),
                new GameImage("/objects/chest.png", tileSize, tileSize)));
    }

    public void startGameThread() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        gameThread = new Thread(this);
        gameThread.start();
        if (gameThread.getState() == Thread.State.NEW) {
            System.err.println("Failed to start game thread");
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // Cập nhật người chơi
        if (player != null && player.isActive() && !ui.isGamePaused()) {
            player.update();
        }

        // Cập nhật kẻ địch
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy != null && enemy.isActive() && !ui.isGamePaused()) {
                enemy.update();
                if (player != null && player.isActive() && cChecker.checkEntity(player, enemy)) {
                    float dx = enemy.getWorldX() - player.getWorldX();
                    float dy = enemy.getWorldY() - player.getWorldY();
                    float distanceSquared = dx * dx + dy * dy;
                    int attackRangeSquared = 50 * 50;
                    if (distanceSquared <= attackRangeSquared) {
                        enemy.attack(player);
                        ui.showMessage("Enemy attacked! Player HP: " + player.getHp());
                        if (player.getHp() <= 0) {
                            ui.setGameFinished(true);
                            ui.showMessage("Game Over! You were defeated.");
                        }
                    }
                }
                if (!enemy.isActive()) {
                    enemy.dropLoot();
                    enemies.remove(i);
                }
            }
        }

        // Kiểm tra nếu người chơi mở rương (Chest)
        if (!ui.isGameFinished() && !ui.isGamePaused()) {
            for (Item obj : objects) {
                if (obj instanceof Chest && obj != null) {
                    Chest chest = (Chest) obj;
                    if (chest.isOpened()) { // Kiểm tra trạng thái mở
                        ui.setGameFinished(true);
                        ui.showMessage("You opened the Chest! Game Finished!");
                        break;
                    }
                }
            }
        }

        // Xử lý phím tạm dừng (P) và khởi động lại (R)
        if (keyH.isPPressed() && !ui.isGameFinished()) {
            ui.setGamePaused(!ui.isGamePaused());
            keyH.setPPressed(false);
        }
        if (keyH.isRPressed() && ui.isGameFinished()) {
            ui.resetGame();
            restartGame();
            keyH.setRPressed(false);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Vẽ bản đồ
        if (tileM != null) {
            tileM.draw(g2);
        }

        // Vẽ Item trong objects
        for (Item obj : objects) {
            if (obj != null && player != null) {
                int screenX = (int) (obj.getPosition().getX() - player.getWorldX() + player.getScreenX());
                int screenY = (int) (obj.getPosition().getY() - player.getWorldY() + player.getScreenY());
                if (screenX + tileSize > 0 && screenX < screenWidth &&
                        screenY + tileSize > 0 && screenY < screenHeight) {
                    obj.draw(g2, screenX, screenY);
                }
            }
        }

        // Vẽ Item trong items
        for (Item item : items) {
            if (item != null && player != null) {
                int screenX = (int) (item.getPosition().getX() - player.getWorldX() + player.getScreenX());
                int screenY = (int) (item.getPosition().getY() - player.getWorldY() + player.getScreenY());
                if (screenX + tileSize > 0 && screenX < screenWidth &&
                        screenY + tileSize > 0 && screenY < screenHeight) {
                    item.draw(g2, screenX, screenY);
                }
            }
        }

        // Vẽ Player
        if (player != null) {
            player.draw(g2);
        }

        // Vẽ Enemy
        for (Enemy enemy : enemies) {
            if (enemy != null && enemy.isActive()) {
                enemy.draw(g2);
            }
        }

        // Vẽ UI
        if (ui != null) {
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void restartGame() {
        ui.setGameFinished(false);
        ui.setPlayTimeFrames(0);
        if (player != null) {
            player.setHp(player.getMaxHp());
            if (player.getInventory() != null) {
                player.getInventory().clear();
            }
        }
        objects.clear();
        items.clear();
        enemies.clear();
        setupGame();
        startGameThread();
    }
}