package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ExerciseVideosPage {

    private final Stage stage;

    public ExerciseVideosPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        // Kid-friendly pink and blue gradient background
        root.setStyle("-fx-background-color: linear-gradient(to right, #f9e4ff, #e0f7fa, #f8bbd0, #b3e5fc);");

        // Back Button (Top Left)
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setPadding(new Insets(20, 20, 0, 20));

        Button backBtn = new Button("← Back");
        backBtn.setPrefWidth(100);
        backBtn.setPrefHeight(40);
        backBtn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #f06292, #64b5f6);" +
            "-fx-background-radius: 22; -fx-border-radius: 22; -fx-cursor: hand;"
        );
        backBtn.setEffect(new DropShadow(8, Color.web("#ff80ab55")));
        backBtn.setOnAction(e -> {
            HealthAndWellnessPage wellnessPage = new HealthAndWellnessPage(stage);
            stage.setScene(wellnessPage.getScene());
        });

        headerBox.getChildren().add(backBtn);

        // Title Section
        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 10, 0));

        Text title = new Text("Pregnancy Exercise & Yoga Videos");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 34));
        title.setFill(Color.web("#ec407a"));

        Text subtitle = new Text("Yoga for Normal Delivery, Healthy Baby & Mother's Wellness");
        subtitle.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 18));
        subtitle.setFill(Color.web("#1976d2"));

        titleBox.getChildren().addAll(title, subtitle);

        // Video Buttons Section - now kid friendly!
        VBox videosBox = new VBox(30);
        videosBox.setAlignment(Pos.CENTER);
        videosBox.setPadding(new Insets(10, 40, 40, 40));

        // Gather blocks for animation
        List<VBox> animatedBlocks = new ArrayList<>();
        animatedBlocks.add(createVideoCard(
            "🧸 Yoga for Normal Delivery",
            "https://www.youtube.com/watch?v=YWt3qH-glTw&pp=ygUUeW9nYSBub3JtYWwgZGVsaXZlcnk%3D",
            "#f8bbd0", "#b2ebf2", "#ff80ab")); // pink and blue pastels
        animatedBlocks.add(createVideoCard(
            "🐻 Daily Pregnancy Yoga for Healthy Baby",
            "https://www.youtube.com/watch?v=_dVuHFdUN0c&pp=ygUkZGFpbHkgcHJlZ25hbmN5IHlvZ2Egb3IgaGVhbHRoeSBiYWJ5",
            "#b2ebf2", "#fce4ec", "#4fc3f7"
        ));
        animatedBlocks.add(createVideoCard(
            "🐤 Prenatal Yoga to Avoid Complications",
            "https://www.youtube.com/watch?v=FRQbPjsyzZ8&pp=ygUkcGFyZW50YWwgeW9nYSB0byBhdm9pZCBjb21wbGljYXRpb25z0gcJCccJAYcqIYzv",
            "#fff9c4", "#f8bbd0", "#ffd54f"
        ));

        videosBox.getChildren().addAll(animatedBlocks);
        playDropDownAnimation(animatedBlocks);

        ScrollPane scrollPane = new ScrollPane(videosBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox mainContent = new VBox(20, titleBox, scrollPane);
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setTop(headerBox);
        root.setCenter(mainContent);
        return new Scene(root, 1500, 800);
    }

    // Kid-friendly, visually distinctive cards
    private VBox createVideoCard(String videoTitle, String videoUrl, String fromColor, String toColor, String borderColor) {
        VBox block = new VBox(15);
        block.setAlignment(Pos.CENTER);
        block.setPadding(new Insets(30, 30, 30, 30));
        block.setPrefWidth(700);
        block.setMaxWidth(700);

        // Playful gradient, bold border, drop shadow, extra roundness
        block.setStyle(
            "-fx-background-radius: 36; " +
            "-fx-border-radius: 36;" +
            "-fx-background-color: linear-gradient(to bottom right, " + fromColor + ", " + toColor + ");" +
            "-fx-border-color: " + borderColor + ";" +
            "-fx-border-width: 6;"
        );
        block.setEffect(new DropShadow(24, Color.web("#ba68c866")));

        Text title = new Text(videoTitle);
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        title.setFill(Color.web("#3949ab")); // fun purple-blue

        Button watchButton = new Button("▶️ Watch on YouTube");
        watchButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 17));
        watchButton.setTextFill(Color.WHITE);
        watchButton.setStyle(
            "-fx-background-color: linear-gradient(to right, #ff80ab, #4fc3f7, #ba68c8);" +
            "-fx-background-radius: 26; -fx-cursor: hand;" +
            "-fx-border-radius: 30; -fx-border-color: #ba68c8; -fx-border-width: 2;"
        );
        watchButton.setPrefSize(280, 54);
        watchButton.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(videoUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        block.getChildren().addAll(title, watchButton);
        return block;
    }

    // Drop-down animation!
    private void playDropDownAnimation(List<VBox> blocks) {
        double startOffset = -230.0;
        double bounceHeight = 34.0;
        double blockDelaySeconds = 0.08;

        for (int i = 0; i < blocks.size(); i++) {
            VBox block = blocks.get(i);
            block.setOpacity(0);
            block.setTranslateY(startOffset);

            Timeline anim = new Timeline(
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.01),
                    e -> block.setOpacity(1)),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.24),
                    new KeyValue(block.translateYProperty(), 8, Interpolator.EASE_IN)
                ),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.33),
                    new KeyValue(block.translateYProperty(), -bounceHeight, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.39),
                    new KeyValue(block.translateYProperty(), 0, Interpolator.EASE_BOTH)
                )
            );
            anim.play();
        }
    }
}