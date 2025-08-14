package com.priti.view;

import com.priti.utils.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VitalsFeaturePage {

    public Scene getScene(Stage stage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f5f0ff;");

        Label heading = new Label("Mother's Health Vitals");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#7c5295"));

        Label details = new Label("Track blood pressure, weight, oxygen, heart rate, and more.");
        details.setFont(Font.font("Arial", 16));
        details.setWrapText(true);

        Button back = new Button("⬅ Back to Tracker");
        back.setOnAction(e -> stage.setScene(new PregnancyMonitoringPage(stage).getScene())); // ✅ fixed
        back.setStyle("-fx-background-color:#7c5295; -fx-text-fill: white; -fx-padding: 8 24 8 24;");
        back.setFont(Font.font("Arial Rounded MT Bold", 15));

        Button exploreButton = new Button("Explore with AI 🤖");
        exploreButton.setStyle("-fx-background-color: #A53860; -fx-text-fill: white; -fx-padding: 10 28 10 28;");
        exploreButton.setFont(Font.font("Arial Rounded MT Bold", 17));
        // 👉 Removed exploreButton action so it does nothing

        root.getChildren().addAll(heading, details, exploreButton, back);
        return new Scene(root, 1550, 800);
    }
}
