package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50]; // Tăng số lượng ô để hỗ trợ các loại trong tương lai
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    // Định nghĩa lớp Tile
    public class Tile {
        public BufferedImage image;
        public boolean collision;

        public Tile() {
            this.image = null;
            this.collision = false;
        }
    }

    public void getTileImage() {
        setup(0, "grass00.png", false);
        setup(1, "wall.png", true);
        setup(2, "water00.png", false);
        setup(3, "earth.png", false);
        setup(4, "tree.png", true);
        setup(5, "road00.png", false);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            if (tile[index].image != null) {
                tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize); // Sửa lỗi chính tả "scareImage" thành "scaleImage"
            } else {
                System.err.println("Failed to load image: /tiles/" + imageName);
            }
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new Exception("Cannot find map file: " + filePath);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            String line;
            while (row < gp.maxWorldRow && (line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                if (numbers.length != gp.maxWorldCol) {
                    System.err.println("Invalid map row length at row " + row + ": expected " + gp.maxWorldCol + ", got " + numbers.length);
                    continue;
                }
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
                row++;
            }
            if (row < gp.maxWorldRow) {
                System.err.println("Map file incomplete: expected " + gp.maxWorldRow + " rows, got " + row);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Giả lập bản đồ mặc định nếu tải thất bại
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    mapTileNum[col][row] = (col == 0 || col == gp.maxWorldCol - 1 || row == 0 || row == gp.maxWorldRow - 1) ? 1 : 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        // Tính toán khung nhìn một lần để tối ưu
        int playerWorldX = gp.player.getWorldX();
        int playerWorldY = gp.player.getWorldY();
        int playerScreenX = gp.player.getScreenX();
        int playerScreenY = gp.player.getScreenY();
        int tileSize = gp.tileSize;

        int leftWorldX = playerWorldX - playerScreenX - tileSize;
        int rightWorldX = playerWorldX + playerScreenX + tileSize;
        int topWorldY = playerWorldY - playerScreenY - tileSize;
        int bottomWorldY = playerWorldY + playerScreenY + tileSize;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * tileSize;
            int worldY = worldRow * tileSize;
            int screenX = worldX - playerWorldX + playerScreenX;
            int screenY = worldY - playerWorldY + playerScreenY;

            if (worldX + tileSize > leftWorldX &&
                    worldX - tileSize < rightWorldX &&
                    worldY + tileSize > topWorldY &&
                    worldY - tileSize < bottomWorldY) {
                if (tile[tileNum] != null && tile[tileNum].image != null) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                } else {
                    g2.setColor(Color.GRAY); // Vẽ màu xám nếu hình ảnh không tải được
                    g2.fillRect(screenX, screenY, tileSize, tileSize);
                }
            }
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}