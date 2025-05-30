package ui;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GameScene {

    private final Scene scene;
    private final Canvas canvas;
    private final AnimationTimer gameLoop;

    public GameScene(double width, double height) {
        canvas = new Canvas(width, height);
        BorderPane root = new BorderPane(canvas);
        scene = new Scene(root, width, height);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    public Scene getScene() {
        return scene;
    }

    private void update() {
        // TODO: cập nhật logic nhân vật, enemy, items
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKSLATEGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // TODO: vẽ player, enemy, HUD...
    }
}
