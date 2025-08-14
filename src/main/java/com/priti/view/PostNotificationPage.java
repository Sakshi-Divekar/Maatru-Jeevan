package com.priti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class PostNotificationPage {

    private final Map<String, Boolean> completedReminders = new HashMap<>();

    public Scene getScene(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8fcff, #e0f7ff);");

        // Back Button (Top Left)
        Button back = new Button("← Back");
        back.setFont(Font.font("Poppins", 16));
        back.setStyle("-fx-background-color: #00B4D8; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 12;");
        back.setOnAction(e -> stage.setScene(new PostHomePage().getScene(stage)));

        HBox backBox = new HBox(back);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(0, 0, 10, 0));

        // Header
        Label title = new Label("🍼 Post Delivery Routine & Reminders");
        title.setFont(Font.font("Poppins", 30));
        title.setTextFill(Color.web("#023E8A"));

        // Notification container
        VBox notificationBox = new VBox(20);
        notificationBox.setPadding(new Insets(30));
        notificationBox.setAlignment(Pos.TOP_CENTER);
        notificationBox.setStyle(
                "-fx-background-color: #caf0f8;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(three-pass-box, #90e0ef, 15, 0.4, 0, 5);");

        List<String[]> reminders = List.of(
                new String[]{"👩 Mother", "💊 Iron Tablets", "2:00 PM"},
                new String[]{"👩 Mother", "🍽️ Lunch Reminder", "1:00 PM"},
                new String[]{"👶 Baby", "🛁 Baby Bath Time", "10:00 AM"},
                new String[]{"👩 Mother", "🧘‍♀️ Gentle Yoga Session", "6:30 AM"},
                new String[]{"👶 Baby", "🍼 Feeding Time", "Every 2 hours"},
                new String[]{"👶 Baby", "💤 Nap Time", "3:00 PM"},
                new String[]{"👩 Mother", "🥛 Drink Warm Milk", "9:00 PM"}
        );

        for (String[] item : reminders) {
            notificationBox.getChildren().add(createNotificationItem(item[0], item[1], item[2]));
        }

        root.getChildren().addAll(backBox, title, notificationBox);
        return new Scene(root, 1550, 800);
    }

    private HBox createNotificationItem(String type, String text, String time) {
        HBox box = new HBox(20);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(1000);
        box.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0.3, 0, 2);");

        Label typeLabel = new Label(type);
        typeLabel.setFont(Font.font("Poppins", 16));
        typeLabel.setTextFill(type.contains("Baby") ? Color.web("#f77f00") : Color.web("#0077b6"));

        Label content = new Label(text);
        content.setFont(Font.font("Poppins", 16));
        content.setTextFill(Color.web("#03045e"));

        Label timeLabel = new Label("⏰ " + time);
        timeLabel.setFont(Font.font("Poppins", 14));
        timeLabel.setTextFill(Color.web("#495057"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button doneBtn = new Button("✔ Mark as Done");
        doneBtn.setFont(Font.font("Poppins", 13));
        doneBtn.setStyle(
                "-fx-background-color: #48cae4;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;");

        String key = type + text + time;

        doneBtn.setOnAction(e -> {
            completedReminders.put(key, true);
            doneBtn.setText("✅ Done");
            doneBtn.setDisable(true);
            box.setStyle(
                    "-fx-background-color: #d4edda;" +
                            "-fx-border-color: #28a745;" +
                            "-fx-border-radius: 12;" +
                            "-fx-background-radius: 12;" +
                            "-fx-effect: dropshadow(gaussian, rgba(40, 167, 69, 0.4), 10, 0.5, 0, 2);"
            );
        });

        box.getChildren().addAll(typeLabel, content, spacer, timeLabel, doneBtn);
        return box;
    }
}
