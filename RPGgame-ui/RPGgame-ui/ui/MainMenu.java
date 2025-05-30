package ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pack9.GameManager;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hunter Adventure - Main Menu");

        // Load background image
        Image backgroundImage = new Image("C:/Users/dumbe/OneDrive/Desktop/RPGgame-ui/RPGgame-ui/BackGround/div2 (1).jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1024);
        backgroundView.setFitHeight(768);
        backgroundView.setPreserveRatio(false);

        // Title text
        Text title = new Text("Hunter Adventure");
        title.setFill(Color.RED);
        title.setFont(Font.font("Verdana", 48));

        // Buttons
        Button newGameButton = createStyledButton("New Game");
        newGameButton.setOnAction(e -> {
            GameScene gameScene = new GameScene(1024, 768);
            primaryStage.setScene(gameScene.getScene());
            primaryStage.setFullScreen(false); // hoặc true nếu bạn muốn
            GameManager gameManager = new GameManager();
            gameManager.startGame();
        });

        Button loadGameButton = createStyledButton("Load Game");
        loadGameButton.setOnAction(e -> System.out.println("Loading game..."));

        Button optionButton = createStyledButton("Option");
        optionButton.setOnAction(e -> System.out.println("Opening settings..."));

        Button creditButton = createStyledButton("Credit");
        creditButton.setOnAction(e -> System.out.println("Showing credits..."));

        Button exitButton = createStyledButton("Quit Game");
        exitButton.setOnAction(e -> primaryStage.close());

        // VBox for menu
        VBox menuBox = new VBox(15, newGameButton, loadGameButton, optionButton, creditButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(200,200,200,0.85); -fx-padding: 30px; -fx-background-radius: 10;");

        VBox container = new VBox(30, title, menuBox);
        container.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(backgroundView, container);
        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #4da3ff;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;"
        );
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
