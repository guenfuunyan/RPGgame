package ui;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pack1.Player; // Giả định Player là lớp chứa dữ liệu nhân vật

public class InGameHUD extends Pane {
    private ProgressBar hpBar;
    private ProgressBar mpBar;
    private ProgressBar expBar;
    private Button healButton;
    private Button skillButton;

    public InGameHUD(Player player) {
        // Tạo các thanh tiến trình
        hpBar = new ProgressBar();
        hpBar.setStyle("-fx-accent: red;"); // Màu đỏ cho HP
        hpBar.setPrefWidth(200);

        mpBar = new ProgressBar();
        mpBar.setStyle("-fx-accent: blue;"); // Màu xanh cho MP
        mpBar.setPrefWidth(200);

        expBar = new ProgressBar();
        expBar.setStyle("-fx-accent: green;"); // Màu xanh lá cho EXP
        expBar.setPrefWidth(200);

        // Liên kết với dữ liệu của player
        hpBar.progressProperty().bind(player.hpProperty().divide(player.maxHpProperty()));
        mpBar.progressProperty().bind(player.mpProperty().divide(player.maxMpProperty()));
        expBar.progressProperty().bind(player.expProperty().divide(player.expToNextLevelProperty()));

        // Tạo các nút hành động nhanh
        healButton = new Button("Heal");
        healButton.setOnAction(e -> player.useHealItem()); // Gọi phương thức hồi máu

        skillButton = new Button("Skill");
        skillButton.setOnAction(e -> player.useSkill()); // Gọi phương thức sử dụng kỹ năng

        // Sắp xếp các thành phần
        HBox statusBars = new HBox(10, hpBar, mpBar, expBar);
        HBox actionButtons = new HBox(10, healButton, skillButton);
        VBox hudLayout = new VBox(10, statusBars, actionButtons);
        hudLayout.setStyle("-fx-padding: 10; -fx-background-color: rgba(0, 0, 0, 0.5);");

        // Thêm vào pane
        getChildren().add(hudLayout);
    }
}