package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        try {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("2D Adventure");

            main.GamePanel gamePanel = new main.GamePanel();
            window.add(gamePanel);

            // Gọi setupGame() trước khi hiển thị giao diện
            gamePanel.setupGame(); // Sửa từ setUpGame() thành setupGame()

            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            // Khởi động luồng game
            gamePanel.startGameThread();
        } catch (Exception e) {
            System.err.println("Error starting the game: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}