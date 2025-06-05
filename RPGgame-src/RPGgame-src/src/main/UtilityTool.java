package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    /**
     * Co giãn hình ảnh về kích thước mới với chất lượng tối ưu.
     *
     * @param original Hình ảnh gốc
     * @param width    Chiều rộng mới
     * @param height   Chiều cao mới
     * @return Hình ảnh đã được co giãn
     * @throws IllegalArgumentException nếu hình ảnh gốc là null hoặc kích thước không hợp lệ
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // Kiểm tra null và kích thước không hợp lệ
        if (original == null) {
            throw new IllegalArgumentException("Original image cannot be null");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive: width=" + width + ", height=" + height);
        }

        // Kiểm tra nếu kích thước mới giống với kích thước gốc
        if (original.getWidth() == width && original.getHeight() == height) {
            return original; // Trả về hình ảnh gốc để tối ưu hiệu suất
        }

        try {
            // Tạo hình ảnh mới với TYPE_INT_ARGB để hỗ trợ trong suốt
            BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = scaledImage.createGraphics();

            // Thiết lập rendering hints để cải thiện chất lượng hình ảnh
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Co giãn hình ảnh
            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();

            return scaledImage;
        } catch (Exception e) {
            // Xử lý trường hợp lỗi khi co giãn (hiếm xảy ra)
            System.err.println("Error scaling image: original size=" + original.getWidth() + "x" + original.getHeight() +
                    ", target size=" + width + "x" + height + ", error=" + e.getMessage());
            // Tạo hình ảnh mặc định màu đỏ nếu thất bại
            BufferedImage fallback = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = fallback.createGraphics();
            g2.setColor(Color.RED);
            g2.fillRect(0, 0, width, height);
            g2.dispose();
            return fallback;
        }
    }
}