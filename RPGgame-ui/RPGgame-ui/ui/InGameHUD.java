// HUDComponent.java — Dùng để hiển thị HUD với thanh máu, mana, stamina, vàng, ngày

package ui;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.Player;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class HUDComponent extends StackPane {
    // ======= Thanh chỉ số =======
    private Rectangle healthBarFill; // Thanh máu
    private Rectangle manaBarFill;   // Thanh mana
    private Rectangle staminaBarFill; // Thanh năng lượng

    // ======= Thông tin người chơi =======
    private Label goldLabel; // Vàng
    private Label dayLabel;  // Ngày

    // ======= Bố cục giao diện =======
    private VBox barContainer;
    private HBox topBar;

    // ======= Trạng thái HUD =======
    private boolean isExpanded = true;
    private int day = 1;

    // ======= Dữ liệu người chơi =======
    private Player player;

    public HUDComponent(Player player) {
        this.player = player;

        // Load HUD nền
        ImageView hudImage = new ImageView(new Image("C:/Users/dumbe/OneDrive/Desktop/RPGgame-ui/RPGgame-ui/Hud_Overlay_Ui/PNG/HudHealth.png"));
        hudImage.setFitWidth(200);
        hudImage.setFitHeight(150);

        // ====== Khởi tạo các thanh chỉ số ======
        healthBarFill = createBar(Color.RED);
        manaBarFill = createBar(Color.BLUE);
        staminaBarFill = createBar(Color.GOLD);

        barContainer = new VBox(5,
                wrapBar(healthBarFill),
                wrapBar(manaBarFill),
                wrapBar(staminaBarFill));
        barContainer.setTranslateX(55);
        barContainer.setTranslateY(20);

        // ====== Nhãn ngày ======
        dayLabel = new Label("DAY " + day);
        dayLabel.setTextFill(Color.BLACK);
        dayLabel.setTranslateX(700);
        dayLabel.setTranslateY(120);

        // ====== Nhãn vàng + icon ======
        ImageView coinIcon = new ImageView(new Image("C:/Users/dumbe/OneDrive/Desktop/RPGgame-ui/RPGgame-ui/Hud_Overlay_Ui/PNG/Coin_video_game.png"));
        coinIcon.setFitWidth(20);
        coinIcon.setFitHeight(20);
        goldLabel = new Label(String.valueOf(player.getGold()));
        goldLabel.setGraphic(coinIcon);
        goldLabel.setTextFill(Color.BLACK);
        goldLabel.setTranslateX(20);
        goldLabel.setTranslateY(120);

        // ====== Nút thu gọn/mở rộng HUD ======
        Button toggleButton = new Button("≡");
        toggleButton.setOnAction(e -> toggleHUD());

        topBar = new HBox(toggleButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setTranslateX(5);
        topBar.setTranslateY(5);

        // ====== Thêm vào HUD ======
        getChildren().addAll(hudImage, barContainer, goldLabel, dayLabel, topBar);
        updateBars();
    }

    // ======= Tạo thanh chỉ số =======
    private Rectangle createBar(Color color) {
        Rectangle rect = new Rectangle(90, 10, color);
        rect.setArcWidth(5);
        rect.setArcHeight(5);
        return rect;
    }

    // ======= Gói thanh vào nền xám =======
    private StackPane wrapBar(Rectangle bar) {
        Rectangle bg = new Rectangle(90, 10, Color.LIGHTGRAY);
        bg.setArcWidth(5);
        bg.setArcHeight(5);
        return new StackPane(bg, bar);
    }

    // ======= Cập nhật thanh máu/mana/năng lượng và vàng =======
    public void updateBars() {
        animateBarChange(healthBarFill, player.getHp() / 100.0);
        animateBarChange(manaBarFill, player.getMp() / 100.0);
        animateBarChange(staminaBarFill, player.getStamina() / 100.0);
        goldLabel.setText(String.valueOf(player.getGold()));
    }

    // ======= Hiệu ứng tụt thanh mượt =======
    private void animateBarChange(Rectangle bar, double percent) {
        double newWidth = percent * 90;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(bar.widthProperty(), newWidth)));
        timeline.play();
    }

    // ======= Hiệu ứng phát sáng khi hồi máu =======
    public void healEffect() {
        Glow glow = new Glow(0.7);
        healthBarFill.setEffect(glow);
        Timeline t = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(glow.levelProperty(), 0))
        );
        t.setOnFinished(e -> healthBarFill.setEffect(null));
        t.play();
    }

    // ======= Tăng ngày =======
    public void nextDay() {
        day++;
        dayLabel.setText("DAY " + day);
    }

    // ======= Thu gọn / mở rộng giao diện =======
    private void toggleHUD() {
        isExpanded = !isExpanded;
        barContainer.setVisible(isExpanded);
        goldLabel.setVisible(isExpanded);
        dayLabel.setVisible(isExpanded);
    }
}

public class InGameHUD extends VBox {
    private ProgressBar hpBar;
    private ProgressBar mpBar;
    private ProgressBar expBar;
    private Label dayLabel;

    public InGameHUD() {
        setPadding(new Insets(10));
        setSpacing(5);

        hpBar = new ProgressBar(0.7);
        mpBar = new ProgressBar(0.5);
        expBar = new ProgressBar(0.3);
        dayLabel = new Label("Day: 1");

        getChildren().addAll(dayLabel, hpBar, mpBar, expBar);
    }
}