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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DoAndDontsVideoPage {

    private final Stage stage;

    public DoAndDontsVideoPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        // Updated background with soft pastel pink and blue gradient
        root.setStyle("-fx-background-color: linear-gradient(to right, #fce4ec, #e1f5fe);");

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setPadding(new Insets(20, 20, 0, 20));

        Button backBtn = new Button("← Back");
        backBtn.setPrefWidth(100);
        backBtn.setPrefHeight(40);
        backBtn.setFont(Font.font("Arial Rounded MT Bold", 14));
        backBtn.setTextFill(Color.WHITE);
        // Updated backBtn with kid-friendly pink-to-blue gradient and rounded corners
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #ff87b0, #81d4fa);" +
            "-fx-background-radius: 20; -fx-cursor: hand;"
        );
        backBtn.setEffect(new DropShadow(5, Color.web("#ffb6c166")));
        backBtn.setOnAction(e -> {
            HealthAndWellnessPage wellnessPage = new HealthAndWellnessPage(stage);
            stage.setScene(wellnessPage.getScene());
        });

        headerBox.getChildren().add(backBtn);

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 10, 0));

        Text title = new Text("Do's and Don'ts in Pregnancy");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 36));
        // Pinkish purple for kid-friendly bright feel
        title.setFill(Color.web("#e91e63"));

        Text subtitle = new Text("Watch videos on what to eat and avoid during pregnancy");
        subtitle.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 18));
        // Soft blue with some purple tint
        subtitle.setFill(Color.web("#4a148c"));

        titleBox.getChildren().addAll(title, subtitle);

        // Box container for video links
        VBox videosBox = new VBox(30);
        videosBox.setAlignment(Pos.CENTER);
        videosBox.setPadding(new Insets(10, 40, 40, 40));

        // Prepare boxes for animation with updated design
        List<VBox> animatedBoxes = new ArrayList<>();
        animatedBoxes.add(createVideoButton("Foods To Eat During Pregnancy", "https://www.youtube.com/watch?v=ZHPHH5WZtQk", 0));
        animatedBoxes.add(createVideoButton("Foods To Avoid During Pregnancy", "https://www.youtube.com/watch?v=lv4xrmvdamY", 1));
        animatedBoxes.add(createVideoButton("Top 10 Pregnancy Nutrition Tips", "https://www.youtube.com/watch?v=3GTK6MLPJ9g", 2));

        videosBox.getChildren().addAll(animatedBoxes);

        // Animation: drop the boxes in one by one
        playDropDownAnimation(animatedBoxes);

        ScrollPane scrollPane = new ScrollPane(videosBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox mainContent = new VBox(20, titleBox, scrollPane);
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setTop(headerBox);
        root.setCenter(mainContent);

        return new Scene(root, 1550, 800);
    }

    // Added parameter 'index' to vary colors on each box for more kid-friendly look
    private VBox createVideoButton(String videoTitle, String videoUrl, int index) {
        VBox block = new VBox(20);
        block.setAlignment(Pos.CENTER);
        block.setPadding(new Insets(25));
        block.setPrefWidth(1000);
        block.setMaxWidth(1000);
        block.setMinHeight(180);

        // Pinkish and blueish pastel palettes for boxes
        String[] bgGradients = new String[] {
                "linear-gradient(to bottom right, #ffb6c1, #87ceeb)",  // soft pink to sky blue
                "linear-gradient(to bottom right, #f4c2c2, #add8e6)",  // light rose to light blue
                "linear-gradient(to bottom right, #ffc0cb, #b0e0e6)"   // classic pink to powder blue
        };

        // Drop shadows with complementing pastel hues
        Color[] shadowColors = new Color[] {
                Color.web("#ff8da8aa"),
                Color.web("#a0c8f5aa"),
                Color.web("#ffb3b3aa")
        };

        block.setStyle("-fx-background-radius: 30; -fx-background-color: " + bgGradients[index] + ";");

        DropShadow shadow = new DropShadow();
        shadow.setRadius(15);
        shadow.setOffsetX(0);
        shadow.setOffsetY(5);
        shadow.setColor(shadowColors[index]);
        block.setEffect(shadow);

        Text title = new Text(videoTitle);
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
        // Strong pinkish text colors for friendliness and visibility
        Color[] titleColors = new Color[] {
                Color.web("#880e4f"),
                Color.web("#6a1b9a"),
                Color.web("#ad1457")
        };
        title.setFill(titleColors[index]);

        Button watchButton = new Button("▶️ Watch on YouTube");
        watchButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
        watchButton.setTextFill(Color.WHITE);

        // Gradient backgrounds for buttons to match the box palette but more vibrant
        String[] btnGradients = new String[] {
                "linear-gradient(to right, #ec407a, #42a5f5)",  // bright pink to medium blue
                "linear-gradient(to right, #f06292, #64b5f6)",
                "linear-gradient(to right, #e91e63, #1e88e5)"
        };
        watchButton.setStyle(
                "-fx-background-color: " + btnGradients[index] + ";" +
                "-fx-background-radius: 35; -fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);"
        );
        watchButton.setPrefSize(280, 55);

        watchButton.setOnAction(e -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(videoUrl));
                }
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        block.getChildren().addAll(title, watchButton);

        return block;
    }

    private void playDropDownAnimation(List<VBox> boxes) {
        double startOffset = -220; // initial Y-translated position (above)
        double bounceHeight = 45;  // how much the box "bounces up" after landing

        for (int i = 0; i < boxes.size(); i++) {
            VBox box = boxes.get(i);
            box.setOpacity(0);
            box.setTranslateY(startOffset);

            // Animation for drop-down + slight bounce
            Timeline dropAnim = new Timeline(
                new KeyFrame(Duration.millis(50 * i), e -> {
                    // Initial delay based on order
                }),
                new KeyFrame(Duration.millis(50 * i + 10), e -> {
                    box.setOpacity(1);
                }),
                new KeyFrame(Duration.millis(280 + 50 * i),
                    new KeyValue(box.translateYProperty(), 6, Interpolator.EASE_IN)),
                new KeyFrame(Duration.millis(350 + 50 * i),
                    new KeyValue(box.translateYProperty(), -bounceHeight, Interpolator.EASE_OUT)),
                new KeyFrame(Duration.millis(430 + 50 * i),
                    new KeyValue(box.translateYProperty(), 0, Interpolator.EASE_BOTH))
            );
            dropAnim.play();
        }
    }
}