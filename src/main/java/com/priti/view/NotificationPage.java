package com.priti.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.utils.UserSession;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NotificationPage {

    private VBox notificationBox = new VBox(15);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final Map<String, Boolean> completedTasks = new HashMap<>();

    public Scene getScene(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FFF8F0;");

        // Back Button
        Button backButton = new Button("← Back");
        backButton.setStyle(
                "-fx-background-color: #A53860;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: 'Segoe UI Semibold';" +
                        "-fx-background-radius: 18;" +
                        "-fx-padding: 8 22;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, #A53860AA, 8, 0.6, 0, 2);");

        backButton.setOnAction(e -> {
            PreHomePage preHomePage = new PreHomePage();
            primaryStage.setScene(preHomePage.getScene(primaryStage));
        });

        // Title
        Label title = new Label("Daily Routine & Diet Plan");
        title.setFont(new Font("Arial", 28));
        title.setTextFill(Color.web("#8E3200"));

        // Header layout (Back button + Title)
        HBox header = new HBox(20, backButton, title);
        header.setAlignment(Pos.CENTER_LEFT);

        ScrollPane scrollPane = new ScrollPane(notificationBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        notificationBox.setAlignment(Pos.TOP_LEFT);

        root.getChildren().addAll(header, scrollPane);
        loadUserData();

        return new Scene(root, 1550, 800);
    }

    private void loadUserData() {
        String email = UserSession.getUserEmail();
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(email);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        new Thread(() -> {
            try {
                DocumentSnapshot snapshot = future.get();
                if (snapshot.exists()) {
                    Platform.runLater(this::showFixedDietAndTips);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showFixedDietAndTips() {
        notificationBox.getChildren().clear();

        Label todayLabel = new Label("Your Daily Plan for Today:");
        todayLabel.setFont(new Font("Arial", 22));
        todayLabel.setTextFill(Color.DARKGREEN);
        notificationBox.getChildren().add(todayLabel);

        // Tips Section
        Label tips = new Label("Tips:");
        tips.setFont(new Font("Arial", 20));
        tips.setTextFill(Color.web("#7B3F00"));
        notificationBox.getChildren().add(tips);

        addTip("Eat fresh fruits and vegetables.");
        addTip("Drink at least 8 glasses of water.");
        addTip("Avoid junk food and stay active.");
        addTip("Take iron and calcium supplements on time.");

        // Diet Plan Section
        Label dietLabel = new Label("Today's Diet & Routine Plan:");
        dietLabel.setFont(new Font("Arial", 20));
        dietLabel.setTextFill(Color.web("#7B3F00"));
        dietLabel.setPadding(new Insets(20, 0, 5, 0));
        notificationBox.getChildren().add(dietLabel);

        List<String[]> dailyTasks = getHardcodedDailyPlan();
        for (String[] entry : dailyTasks) {
            addDietTask(entry[0], entry[1]);
        }
    }

    private void addTip(String message) {
        Label tip = new Label("• " + message);
        tip.setFont(new Font("Arial", 18));
        tip.setWrapText(true);
        tip.setTextFill(Color.web("#4B2E2E"));
        notificationBox.getChildren().add(tip);
    }

    private void addDietTask(String timeStr, String task) {
        HBox taskBox = new HBox(15);
        taskBox.setPadding(new Insets(10));
        taskBox.setAlignment(Pos.CENTER_LEFT);
        taskBox.setStyle("-fx-background-color: #FCEED8; -fx-border-color: #DEB887; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label timeLabel = new Label(timeStr + ":");
        timeLabel.setFont(new Font("Arial", 17));
        timeLabel.setTextFill(Color.web("#6A2E1F"));
        timeLabel.setPrefWidth(90);

        Label taskLabel = new Label(task);
        taskLabel.setFont(new Font("Arial", 17));
        taskLabel.setTextFill(Color.web("#4B2E2E"));
        taskLabel.setWrapText(true);
        taskLabel.setMaxWidth(600);

        Button doneBtn = new Button("Mark as Done");
        doneBtn.setStyle("-fx-background-color: #D7B19D; -fx-text-fill: white; -fx-background-radius: 8;");
        String key = timeStr + task;

        doneBtn.setOnAction(e -> {
            completedTasks.put(key, true);
            doneBtn.setText("Done");
            doneBtn.setDisable(true);
            taskBox.setStyle("-fx-background-color: #DFF0D8; -fx-border-color: #7AB97A; -fx-border-radius: 10; -fx-background-radius: 10;");
        });

        // Upcoming Reminder Check
        LocalTime taskTime = LocalTime.parse(timeStr, TIME_FORMATTER);
        LocalTime now = LocalTime.now();

        if (!completedTasks.getOrDefault(key, false)) {
            if (now.isBefore(taskTime) && now.plusHours(1).isAfter(taskTime)) {
                taskBox.setStyle("-fx-background-color: #FFE4B5; -fx-border-color: red; -fx-border-width: 2; -fx-background-radius: 10;");
                Tooltip tip = new Tooltip("Upcoming in less than 1 hour!");
                Tooltip.install(taskBox, tip);
            }
        }

        taskBox.getChildren().addAll(timeLabel, taskLabel, doneBtn);
        notificationBox.getChildren().add(taskBox);
    }

    private List<String[]> getHardcodedDailyPlan() {
        return Arrays.asList(
                new String[]{"08:00", "Drink warm water with lemon"},
                new String[]{"09:00", "Breakfast: Poha + fruit + milk"},
                new String[]{"11:00", "Mid-morning snack: Banana or nuts"},
                new String[]{"13:00", "Lunch: Roti + dal + green sabzi + salad"},
                new String[]{"16:00", "Evening snack: Coconut water or fruit juice"},
                new String[]{"19:00", "Dinner: Khichdi or soft chapati + paneer curry"},
                new String[]{"21:00", "Bedtime: Warm milk with turmeric"}
        );
    }
}
