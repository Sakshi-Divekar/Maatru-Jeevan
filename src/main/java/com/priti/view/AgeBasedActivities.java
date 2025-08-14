package com.priti.view;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AgeBasedActivities {

    private Stage stage;

    public AgeBasedActivities(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFE0B2, #E1BEE7);");

        // Back Button at top-left
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setStyle("-fx-background-color: #CE93D8; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            RecoveryTrackerPage recoveryPage = new RecoveryTrackerPage(stage);
            stage.setScene(recoveryPage.getScene());
        });

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        Label title = new Label("Age-Based Activities for Baby");
        title.setFont(Font.font("Arial Rounded MT Bold", 34));
        title.setTextFill(Color.DARKSLATEBLUE);

        root.getChildren().addAll(topBar, title);

        GridPane videoGrid = new GridPane();
        videoGrid.setHgap(30);
        videoGrid.setVgap(30);
        videoGrid.setPadding(new Insets(10));
        videoGrid.setAlignment(Pos.TOP_CENTER);

        String[][] videoData = {
                {"0–3 Months", "Tummy Time for Newborns", "https://www.youtube.com/watch?v=qhtoqGPjB4g&pp=ygUXdHVtbXkgdGltZSBmb3IgbmV3Ym9ybnM%3D", "https://www.youtube.com/watch?v=qhtoqGPjB4g&pp=ygUXdHVtbXkgdGltZSBmb3IgbmV3Ym9ybnM%3D", "/assets/images/tummytime.jpeg"},
                {"4–6 Months", "Fun Sensory Play", "https://www.youtube.com/watch?v=BE5gRCMqsCU&pp=ygUmZnVuIHNlbnNvcnkgcGxheSBmb3IgNCB0byA2IG1vbnRoIGJhYnk%3D","https://www.youtube.com/watch?v=BE5gRCMqsCU&pp=ygUmZnVuIHNlbnNvcnkgcGxheSBmb3IgNCB0byA2IG1vbnRoIGJhYnk%3D", "/assets/images/funsensoryplay.jpeg"},
                {"7–9 Months", "Crawling Games", "https://www.youtube.com/watch?v=9T52OgZ6Rxg&pp=ygUaY3J3YWxpbmcgZ2FtZXMgZm9yIG5ld2Jvcm7SBwkJxwkBhyohjO8%3D", "https://www.youtube.com/watch?v=9T52OgZ6Rxg&pp=ygUaY3J3YWxpbmcgZ2FtZXMgZm9yIG5ld2Jvcm7SBwkJxwkBhyohjO8%3D", "/assets/images/crowling_games.jpeg"},
                {"10–12 Months", "First Steps Encouragement", "https://www.youtube.com/watch?v=hK3br6kpP1g&pp=ygUvZmlyc3Qgc3RlcHMgZW5jb3VyYWdlbWVudCBmb3IgMTAgdG8gMTIgbW9udGhzICA%3D", "https://www.youtube.com/watch?v=hK3br6kpP1g&pp=ygUvZmlyc3Qgc3RlcHMgZW5jb3VyYWdlbWVudCBmb3IgMTAgdG8gMTIgbW9udGhzICA%3D", "/assets/images/first_step.jpeg"},
                {"1–2 Years", "Outdoor Activities", "https://www.youtube.com/watch?v=wImig5FDUBA&pp=ygUjb3V0ZG9vb3IgYWN0aXZpdGllcyBmb3IgNSB5ZWFyIGtpZHM%3D", "https://www.youtube.com/watch?v=wImig5FDUBA&pp=ygUjb3V0ZG9vb3IgYWN0aXZpdGllcyBmb3IgNSB5ZWFyIGtpZHM%3D", "/assets/images/outdoor_activity3.jpeg"},
                {"2–3 Years", "Learning Colors & Shapes", "https://www.youtube.com/watch?v=wImig5FDUBA&pp=ygUjb3V0ZG9vb3IgYWN0aXZpdGllcyBmb3IgNSB5ZWFyIGtpZHM%3D", "https://www.youtube.com/watch?v=wImig5FDUBA&pp=ygUjb3V0ZG9vb3IgYWN0aXZpdGllcyBmb3IgNSB5ZWFyIGtpZHM%3D", "/assets/images/learning colours.jpeg"}
        };

        int col = 0, row = 0;

        // Collect all cards for sequential animation
        List<VBox> allCards = new ArrayList<>();

        for (int i = 0; i < videoData.length; i++) {
            VBox videoCard = createVideoCard(videoData[i][0], videoData[i][1], videoData[i][2], videoData[i][3], videoData[i][4]);
            videoGrid.add(videoCard, col, row);
            allCards.add(videoCard);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(videoGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        root.getChildren().add(scrollPane);

        // ---- Animate cards on page load ----
        playTranslateIn(allCards);

        return new Scene(root, 1550, 800);
    }

    private VBox createVideoCard(String age, String title, String thumbnailURL, String videoURL, String localImagePath) {
        Label ageLabel = new Label(age);
        ageLabel.setFont(Font.font("Verdana", 16));
        ageLabel.setTextFill(Color.DARKCYAN);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI Semibold", 14));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setWrapText(true);

        ImageView thumbnail = new ImageView(new Image(thumbnailURL, 200, 120, false, true));

        Button watchButton = new Button("▶ Watch Now");
        watchButton.setStyle("-fx-background-color: #64B5F6; -fx-text-fill: white;");
        watchButton.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(videoURL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ImageView extraImage = new ImageView(new Image(localImagePath));
        extraImage.setFitWidth(100);
        extraImage.setFitHeight(100);

        VBox card = new VBox(20, ageLabel, thumbnail, titleLabel, watchButton, extraImage);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #F3E5F5; -fx-background-radius: 12; -fx-border-color: #CE93D8; -fx-border-radius: 12;");
        card.setPrefSize(250, 300);
        return card;
    }

    /** Animate each card sliding up and fading in, staggered for effect */
    private void playTranslateIn(java.util.List<VBox> cards) {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);
            card.setOpacity(0);
            card.setTranslateY(120);

            TranslateTransition tt = new TranslateTransition(Duration.millis(540), card);
            tt.setFromY(120);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fade = new FadeTransition(Duration.millis(540), card);
            fade.setFromValue(0);
            fade.setToValue(1);

            ParallelTransition anim = new ParallelTransition(tt, fade);
            anim.setDelay(Duration.millis(i * 120));
            anim.play();
        }
    }
}