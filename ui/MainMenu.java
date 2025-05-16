package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pack9.GameManager; // Import GameManager từ pack9

public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RPG Game - Main Menu");

        // Tạo các nút cho menu chính
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> {
            System.out.println("Starting new game...");
            GameManager gameManager = new GameManager();
            gameManager.startGame(); // Gọi startGame từ GameManager
        });

        Button loadGameButton = new Button("Load Game");
        loadGameButton.setOnAction(e -> {
            System.out.println("Loading game...");
            // Logic để tải trò chơi đã lưu (sẽ được triển khai sau)
        });

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> {
            System.out.println("Opening settings...");
            // Logic để mở cài đặt (sẽ được triển khai sau)
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            primaryStage.close(); // Đóng ứng dụng
        });

        // Thiết lập bố cục cho các nút
        VBox menuLayout = new VBox(10); // Khoảng cách giữa các nút là 10px
        menuLayout.getChildren().addAll(newGameButton, loadGameButton, settingsButton, exitButton);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Thêm hình nền hoặc logo (giả định có file hình ảnh)
        Image backgroundImage = new Image("file:assets/background.png"); // Đường dẫn đến hình nền
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800); // Điều chỉnh kích thước hình nền
        backgroundView.setFitHeight(600);

        // Sử dụng StackPane để xếp chồng các thành phần
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, menuLayout); // Hình nền ở dưới, menu ở trên

        // Tạo scene và hiển thị
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}