package main;

import object.OBJ_Key;
import entity.Player;
import util.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B, arial_30;
    GameImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public boolean gamePaused = false;

    long playTimeFrames;
    long bestTimeFrames = Long.MAX_VALUE; // Lưu thời gian tốt nhất
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.getImage();
        if (keyImage == null || !keyImage.isLoaded()) {
            System.err.println("Warning: Key image not loaded! Using fallback.");
            // Tạo hình ảnh dự phòng thủ công thay vì dựa vào GameImage
            BufferedImage fallbackImage = new BufferedImage(gp.tileSize, gp.tileSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = fallbackImage.createGraphics();
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(0, 0, gp.tileSize, gp.tileSize);
            g2d.dispose();
            keyImage = new GameImage(fallbackImage, gp.tileSize, gp.tileSize);
        }
        playTimeFrames = 0;
    }

    public void showMessage(String text) {
        if (text != null) {
            message = text;
            messageOn = true;
        }
    }

    public void resetGame() {
        gameFinished = false;
        gamePaused = false;
        playTimeFrames = 0;
        messageOn = false;
        messageCounter = 0;
        if (gp != null && gp.player != null) {
            gp.player.setHasKey(0);
        }
    }

    public void draw(Graphics2D g2) {
        // Cập nhật thời gian chơi
        if (!gameFinished && !gamePaused) {
            playTimeFrames++;
        }

        // Thêm hiệu ứng mờ khi tạm dừng hoặc kết thúc
        if (gameFinished || gamePaused) {
            g2.setColor(new Color(0, 0, 0, 150)); // Màu đen mờ (alpha = 150)
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        if (gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text = "You found the treasure!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            double playTimeSeconds = playTimeFrames / 60.0;
            // Cập nhật thời gian tốt nhất
            if (playTimeFrames < bestTimeFrames) {
                bestTimeFrames = playTimeFrames;
            }
            double bestTimeSeconds = bestTimeFrames / 60.0;

            text = "Your Time: " + dFormat.format(playTimeSeconds) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            text = "Best Time: " + dFormat.format(bestTimeSeconds) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 1);
            g2.drawString(text, x, y);

            g2.setFont(arial_30);
            g2.setColor(Color.white);
            text = "Press R to Restart";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            // Vẽ hình ảnh chìa khóa và số lượng
            int keyX = gp.tileSize / 2;
            int keyY = gp.tileSize / 2;
            if (gp.player != null) {
                if (keyImage != null && keyImage.isLoaded()) {
                    keyImage.draw(g2, keyX, keyY);
                } else {
                    // Vẽ hình ảnh dự phòng trực tiếp
                    g2.setColor(Color.YELLOW);
                    g2.fillRect(keyX, keyY, gp.tileSize, gp.tileSize);
                }
                g2.setColor(Color.white);
                g2.drawString("x " + gp.player.getHasKey(), keyX + gp.tileSize + 10, keyY + 35);
            }

            // Hiển thị thời gian chơi
            double playTimeSeconds = playTimeFrames / 60.0;
            int timeX = gp.screenWidth - gp.tileSize * 4;
            int timeY = gp.tileSize / 2 + 35;
            g2.drawString("Time: " + dFormat.format(playTimeSeconds), timeX, timeY);

            // Hiển thị thông báo nếu có
            if (messageOn) {
                g2.setFont(arial_30);
                int messageX = gp.tileSize / 2;
                int messageY = gp.screenHeight - gp.tileSize * 2;
                g2.drawString(message, messageX, messageY);
                messageCounter++;
                if (messageCounter > 120) { // 2 giây tại 60 FPS
                    messageCounter = 0;
                    messageOn = false;
                }
            }

            // Hiển thị thông báo tạm dừng nếu gamePaused
            if (gamePaused) {
                g2.setFont(arial_80B);
                g2.setColor(Color.red);
                String pauseText = "Paused";
                int pauseTextLength = (int) g2.getFontMetrics().getStringBounds(pauseText, g2).getWidth();
                int pauseX = gp.screenWidth / 2 - pauseTextLength / 2;
                int pauseY = gp.screenHeight / 2;
                g2.drawString(pauseText, pauseX, pauseY);

                g2.setFont(arial_30);
                g2.setColor(Color.white);
                String resumeText = "Press P to Resume";
                int resumeTextLength = (int) g2.getFontMetrics().getStringBounds(resumeText, g2).getWidth();
                int resumeX = gp.screenWidth / 2 - resumeTextLength / 2;
                int resumeY = gp.screenHeight / 2 + gp.tileSize * 2;
                g2.drawString(resumeText, resumeX, resumeY);
            }
        }
    }

    // Getter và Setter
    public long getPlayTimeFrames() { return playTimeFrames; }
    public void setPlayTimeFrames(long playTimeFrames) { this.playTimeFrames = playTimeFrames; }
    public boolean isGameFinished() { return gameFinished; }
    public void setGameFinished(boolean gameFinished) { this.gameFinished = gameFinished; }
    public boolean isGamePaused() { return gamePaused; }
    public void setGamePaused(boolean gamePaused) { this.gamePaused = gamePaused; }
    public long getBestTimeFrames() { return bestTimeFrames; }
    public void setBestTimeFrames(long bestTimeFrames) { this.bestTimeFrames = bestTimeFrames; }
}