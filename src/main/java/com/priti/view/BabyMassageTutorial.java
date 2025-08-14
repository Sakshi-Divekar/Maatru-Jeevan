package com.priti.view;

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
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

public class BabyMassageTutorial {

    // Each pair is a gradient start and end (soft pastel colors, visually distinct)
    private static final String[][] CARD_GRADIENTS = {
        {"#FFD6E0", "#A1C4FD"},
        {"#FFFEC4", "#B5FFFC"},
        {"#E0C3FC", "#FFD6E0"},
        {"#B5FFFC", "#FFD6E0"},
        {"#A1C4FD", "#FFFEC4"},
        {"#FFD6E0", "#E0C3FC"}
    };
    private final Stage stage;

    public BabyMassageTutorial(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #FAFFD1, #A1C4FD);");

        HBox topBar = new HBox();
        Button backButton = new Button("⬅ Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", 16));
        backButton.setStyle("-fx-background-color: #A1C4FD; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            BabyCarePage babyCarePage = new BabyCarePage(stage);
            stage.setScene(babyCarePage.getScene());
        });
        topBar.getChildren().add(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(topBar);

        Label title = new Label("Baby Massage Tutorials");
        title.setFont(Font.font("Comic Sans MS", 36));
        title.setTextFill(Color.web("#181830")); // deep navy for easy kid reading
        root.getChildren().add(title);

        GridPane grid = new GridPane();
        grid.setHgap(38);
        grid.setVgap(38);
        grid.setPadding(new Insets(22));
        grid.setAlignment(Pos.CENTER);

        String[][] videos = {
            {"Full Body Baby Massage", "https://img.youtube.com/vi/xz2Spn8vagI/0.jpg", "https://www.youtube.com/watch?v=xz2Spn8vagI&pp=ygUWZnVsbCBib2R5IGJhYnkgbWFzc2FnZQ%3D%3D"},
            {"Colic Relief Massage", "https://img.youtube.com/vi/MrpPpjL56QY/0.jpg", "https://www.youtube.com/watch?v=MrpPpjL56QY&pp=ygUdY29saWMgcmVsaWVmIG1hc3NhZ2UgZm9yIGJhYnk%3D"},
            {"Leg & Foot Massage", "https://img.youtube.com/vi/9NK2DYzL5rM/0.jpg", "https://www.youtube.com/watch?v=9NK2DYzL5rM&pp=ygUkbGVnIGFuZCBmb290IG1hc3NhZ2UgZm9yIGJhYnkgdmlkZW9z"},
            {"Back Massage for Baby", "https://img.youtube.com/vi/4_Ou1IgPfjk/0.jpg", "https://www.youtube.com/watch?v=4_Ou1IgPfjk&pp=ygUVQmFjayBNYXNzYWdlIGZvciBCYWJ5"},
            {"Massage Before Bed", "https://img.youtube.com/vi/MSMelUFtqbg/0.jpg", "https://www.youtube.com/watch?v=MSMelUFtqbg&pp=ygUXYmFieSBiZWZvcmUgYmVkIG1hc3NhZ2U%3D"},
            {"Head & Face Massage", "https://img.youtube.com/vi/3LU2-YNvRGg/0.jpg", "https://www.youtube.com/watch?v=3LU2-YNvRGg&pp=ygUlYmFieSBoZWFkIGFuZCBmYWNlIG1hc3NhZ2UgZnVsbCB2aWRlbw%3D%3D"}
        };

        int col = 0, row = 0;
        for (int i = 0; i < videos.length; i++) {
            VBox card = createVideoCard(
                videos[i][0], videos[i][1], videos[i][2],
                CARD_GRADIENTS[i % CARD_GRADIENTS.length][0],
                CARD_GRADIENTS[i % CARD_GRADIENTS.length][1]
            );
            grid.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        root.getChildren().add(scrollPane);

        return new Scene(root, 1550, 800);
    }

    private VBox createVideoCard(String titleText, String thumbUrl, String videoUrl, String gradStart, String gradEnd) {
        Label titleLabel = new Label(titleText);
        titleLabel.setFont(Font.font("Segoe UI", 22)); // larger font
        titleLabel.setTextFill(Color.web("#181830")); // very dark text for visibility
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(340);

        ImageView thumbnail = new ImageView(new Image(thumbUrl));
        thumbnail.setFitWidth(330);
        thumbnail.setFitHeight(180);
        thumbnail.setPreserveRatio(false);

        Button watchButton = new Button("▶ Watch Now");
        watchButton.setStyle("-fx-background-color: #4895EF; -fx-background-radius: 20; -fx-text-fill: white; -fx-font-size: 16px;");
        watchButton.setFont(Font.font("Arial Rounded MT Bold", 17));
        watchButton.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(videoUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox card = new VBox(22, thumbnail, titleLabel, watchButton);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(360);
        card.setPrefHeight(340);
        card.setMaxWidth(370);
        card.setMaxHeight(370);

        // Apply gradient background and strong shadow for "shaded" look
        card.setBackground(new Background(new BackgroundFill(
            new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(gradStart)),
                new Stop(1, Color.web(gradEnd))
            ),
            new CornerRadii(18),
            Insets.EMPTY
        )));
        card.setBorder(new Border(new BorderStroke(Color.web("#4895EF"),
            BorderStrokeStyle.SOLID, new CornerRadii(18), new BorderWidths(2))));
        card.setEffect(new javafx.scene.effect.DropShadow(30, Color.rgb(72, 149, 239, 0.25))); // blue shadow

        return card;
    }
}