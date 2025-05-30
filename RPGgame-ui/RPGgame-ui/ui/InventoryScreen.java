package ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pack1.Item; //fix later // Assumes Item has getName(), getDescription(), use(Player), isConsumable()
import pack1.Player; // Assumes Player has getInventory() returning ObservableList<Item>

public class InventoryScreen extends Pane {
    private ListView<Item> listView;
    private Label descriptionLabel;
    private Button useButton;
    private Button dropButton;

    public InventoryScreen(Player player) {
        // Create ListView to display items
        listView = new ListView<>();
        ObservableList<Item> inventory = player.getInventory();
        listView.setItems(inventory);

        // Set cell factory to show item names
        listView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Label for item description
        descriptionLabel = new Label("Select an item to see description");
        descriptionLabel.setWrapText(true);

        // Buttons for actions, initially disabled
        useButton = new Button("Use");
        useButton.setDisable(true);
        dropButton = new Button("Drop");
        dropButton.setDisable(true);

        // Listener for item selection
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                descriptionLabel.setText(newItem.getDescription());
                useButton.setDisable(false);
                dropButton.setDisable(false);
            } else {
                descriptionLabel.setText("Select an item to see description");
                useButton.setDisable(true);
                dropButton.setDisable(true);
            }
        });

        // Action for "Use" button
        useButton.setOnAction(e -> {
            Item selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.use(player);
                if (selected.isConsumable()) {
                    inventory.remove(selected); // Remove if consumable
                }
            }
        });

        // Action for "Drop" button
        dropButton.setOnAction(e -> {
            Item selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                inventory.remove(selected); // Remove item from inventory
            }
        });

        // Arrange buttons in HBox
        HBox buttons = new HBox(10, useButton, dropButton);

        // Layout using VBox
        VBox layout = new VBox(10, listView, descriptionLabel, buttons);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.setStyle("-fx-background-color: #333333;");

        // Style components
        listView.setStyle("-fx-control-inner-background: #222222; -fx-text-fill: white;");
        descriptionLabel.setStyle("-fx-text-fill: white;");
        useButton.setStyle("-fx-base: #555555;");
        dropButton.setStyle("-fx-base: #555555;");

        // Allow ListView to grow vertically
        VBox.setVgrow(listView, Priority.ALWAYS);

        // Add layout to pane
        getChildren().add(layout);
        setPadding(new Insets(10));
        setSpacing(5);

        Label inventoryLabel = new Label("Inventory");
        Button closeButton = new Button("Close");

        getChildren().addAll(inventoryLabel, closeButton);
    }
}