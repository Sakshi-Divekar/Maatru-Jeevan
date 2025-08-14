package com.priti.view;

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

public class TimelineFeaturePage {
    public Scene getScene(Stage stage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #ffeaf0;");

        Label heading = new Label("Pregnancy Timeline");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#d58b95"));

        Label details = new Label("View trimester details, doctor visits, expected milestones, and more.");
        details.setFont(Font.font("Arial", 16));
        details.setWrapText(true);

        Button back = new Button("⬅ Back to Tracker");
        back.setOnAction(e -> stage.setScene(new PregnancyMonitoringPage(stage).getScene()));
        back.setStyle("-fx-background-color:#d58b95; -fx-text-fill: white; -fx-padding: 8 24 8 24;");
        back.setFont(Font.font("Arial Rounded MT Bold", 15));

        root.getChildren().addAll(heading, details, back);
        return new Scene(root, 900, 700);
    }
}
