package util;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameImage {
    private BufferedImage bufferedImage;
    private String sourcePath;
    private int width;
    private int height;
    private boolean isLoaded;

    // Constructor từ đường dẫn và kích thước
    public GameImage(String sourcePath, int width, int height) {
        this.sourcePath = sourcePath;
        this.width = width;
        this.height = height;
        this.isLoaded = false;
        if (sourcePath != null) {
            loadImage();
        }
    }

    // Constructor từ BufferedImage hiện có
    public GameImage(BufferedImage bufferedImage, int width, int height) {
        this.sourcePath = null;
        this.width = width;
        this.height = height;
        this.isLoaded = false;
        setBufferedImage(bufferedImage);
    }

    private void loadImage() {
        if (sourcePath == null) return;

        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream(sourcePath));
            if (tempImage == null) {
                System.err.println("Resource not found for path: " + sourcePath);
                createFallbackImage(width, height, Color.RED);
                return;
            }

            UtilityTool uTool = new UtilityTool();
            BufferedImage scaledImage = uTool.scaleImage(tempImage, width, height);
            if (scaledImage != null) {
                this.bufferedImage = scaledImage;
                isLoaded = true;
            } else {
                System.err.println("Failed to scale image from: " + sourcePath + " to " + width + "x" + height);
                createFallbackImage(width, height, Color.RED);
            }
        } catch (IOException e) {
            System.err.println("Error loading image from " + sourcePath + ": " + e.getMessage());
            createFallbackImage(width, height, Color.RED);
        }
    }

    public void createFallbackImage(int width, int height, Color color) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        isLoaded = true;
    }

    public void draw(Graphics2D g2, float x, float y) {
        if (isLoaded && bufferedImage != null) {
            int drawX = Math.max(0, (int) x);
            int drawY = Math.max(0, (int) y);
            g2.drawImage(bufferedImage, drawX, drawY, width, height, null);
        } else {
            int drawX = Math.max(0, (int) x);
            int drawY = Math.max(0, (int) y);
            g2.setColor(Color.RED);
            g2.fillRect(drawX, drawY, width, height);
        }
    }

    public void updateImage(BufferedImage newImage) {
        if (newImage != null) {
            UtilityTool uTool = new UtilityTool();
            this.bufferedImage = uTool.scaleImage(newImage, width, height);
            this.isLoaded = (this.bufferedImage != null);
        } else {
            System.err.println("Attempted to update with null BufferedImage");
            createFallbackImage(width, height, Color.RED);
        }
    }

    public boolean isLoaded() { return isLoaded; }
    public BufferedImage getBufferedImage() { return bufferedImage; }
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        if (bufferedImage != null) {
            UtilityTool uTool = new UtilityTool();
            this.bufferedImage = uTool.scaleImage(bufferedImage, width, height);
            isLoaded = (this.bufferedImage != null);
        } else {
            isLoaded = false;
            createFallbackImage(width, height, Color.RED);
        }
    }
    public String getSourcePath() { return sourcePath; }
    public void setSourcePath(String sourcePath) {
        if (!sourcePath.equals(this.sourcePath)) {
            this.sourcePath = sourcePath;
            loadImage();
        }
    }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; if (bufferedImage != null) loadImage(); }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; if (bufferedImage != null) loadImage(); }
}