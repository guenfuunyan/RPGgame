package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.npc.NPC;
import entity.Player;
import entity.monster.Monster;
import entity.object.Object;
import entity.object.Particle;
import entity.object.Projectile;
import entity.object.WeaponProjectile;
import tile.TileManager;
import entity.object.tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 32; // 16x16 tile
    final int scale = 2;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20; // Left to right
    public final int maxScreenRow = 12; // Top to bottom
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    //CHECKPOINT
    public boolean checkPointChange = false;
    
    //DISPLAY RANGE
    public boolean OIndex = false;
    
    // FPS
    int FPS = 60;
    //shooting
    long lastShotTime = 0;
    long shootDelay = 200;
    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Object obj[][] = new Object[maxMap][20];
    public NPC npc[][] = new NPC[maxMap][30];
    public Monster monster[][] = new Monster[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][100];
    public Projectile projectile[][] = new Projectile[maxMap][20];
    public WeaponProjectile weaponProjectile[][] = new WeaponProjectile[maxMap][20];
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Particle> particleList = new ArrayList<>();
    public ArrayList<Player> playerList = new ArrayList<>();
    public ArrayList<NPC> npcList = new ArrayList<>();
    public ArrayList<Monster> monsterList = new ArrayList<>();
    public ArrayList<Object> objectList = new ArrayList<>();
    public ArrayList<WeaponProjectile> weaponProjectileList = new ArrayList<>();
    
    // SKILL 
    public int skillDarkmatterAppear = 0, skillFireballAppear = 0, skillWaterballAppear = 0,
    			skillBlueflameAppear = 0, skillPlasmaAppear = 0;
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int skillState = 9;
    public final int endGame = 10;
    public final int ranniState = 11;
    
    //TITLE SCREEN
    public boolean newGameChoose = false;
    public int newGameTimes = 1;
    
    //END GAME ID
    public int endGameID = 0;
    public boolean bossContact = false;
    public boolean bossAlive = true;
    
    
    public GamePanel() {
        // Size of panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Background Color to black
        this.setBackground(Color.black);

        // Add rendering performance
        this.setDoubleBuffered(true);

        // Add keyboard recognition
        this.addKeyListener(keyH);

        // Make GamePanel focused to receive input
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn == true) {
            setFullScreen();
        }
    }

    public void retry() {
    	bossAlive = true;
        player.setDefaultPositionsForRetry();
        player.restoreLifeAndMana();
        aSetter.setMonster();
    }

    public void restart() {
    	bossAlive = true;
        player.setDefaultValues();
        player.setDefaultPositionsForRestart();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. UPDATE: update the information such as character position
                update();

                // 2. DRAW: draw the screen with updated information
                drawToTempScreen();
                drawToScreen();

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if (projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }
            
            for (int i = 0; i < weaponProjectile[1].length; i++) {
                if (weaponProjectile[currentMap][i] != null) {
                    if (weaponProjectile[currentMap][i].alive == true) {
                    	weaponProjectile[currentMap][i].update();
                    }
                    if (weaponProjectile[currentMap][i].alive == false) {
                    	weaponProjectile[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }

        if (gameState == pauseState) {
        }
    }

    public void drawToTempScreen() {
        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // OTHERS
        else {
            // TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD ENTITIES TO THE LIST
            playerList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npcList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    objectList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    monsterList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    projectileList.add(projectile[currentMap][i]);
                }
            }
            
            for (int i = 0; i < weaponProjectile[1].length; i++) {
                if (weaponProjectile[currentMap][i] != null) {
                    weaponProjectileList.add(weaponProjectile[currentMap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    particleList.add(particleList.get(i));
                }
            }

            // SORT ALL LISTS
            Collections.sort(playerList, new Comparator<Player>() {
                @Override
                public int compare(Player e1, Player e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(npcList, new Comparator<NPC>() {
                @Override
                public int compare(NPC e1, NPC e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(monsterList, new Comparator<Monster>() {
                @Override
                public int compare(Monster e1, Monster e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(objectList, new Comparator<Object>() {
                @Override
                public int compare(Object e1, Object e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(weaponProjectileList, new Comparator<WeaponProjectile>() {
                @Override
                public int compare(WeaponProjectile e1, WeaponProjectile e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(projectileList, new Comparator<Projectile>() {
                @Override
                public int compare(Projectile e1, Projectile e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            Collections.sort(particleList, new Comparator<Particle>() {
                @Override
                public int compare(Particle e1, Particle e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

// DRAW ALL ENTITIES
            for (int i = 0; i < playerList.size(); i++) {
                playerList.get(i).draw(g2);
            }

            for (int i = 0; i < npcList.size(); i++) {
                npcList.get(i).draw(g2);
            }

            for (int i = 0; i < monsterList.size(); i++) {
                monsterList.get(i).draw(g2);
            }

            for (int i = 0; i < objectList.size(); i++) {
                objectList.get(i).draw(g2);
            }

            for (int i = 0; i < weaponProjectileList.size(); i++) {
                weaponProjectileList.get(i).draw(g2);
            }

            for (int i = 0; i < projectileList.size(); i++) {
                projectileList.get(i).draw(g2);
            }

            for (int i = 0; i < particleList.size(); i++) {
                particleList.get(i).draw(g2);
            }

// EMPTY ALL LISTS
            playerList.clear();
            npcList.clear();
            monsterList.clear();
            objectList.clear();
            weaponProjectileList.clear();
            projectileList.clear();
            particleList.clear();

// UI
            ui.draw(g2);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
