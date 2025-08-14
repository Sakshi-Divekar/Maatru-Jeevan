package com.priti.view;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;

public class MonthlyVaccination {

    private Stage stage;

    public MonthlyVaccination(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox rootContent = new VBox(20);
        rootContent.setPadding(new Insets(20));
        rootContent.setAlignment(Pos.TOP_CENTER);
        rootContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #E0F7FA, #F1F8E9);");

        // Back Button
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setStyle("-fx-background-color: #AED581; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            try {
                RecoveryTrackerPage recoveryPage = new RecoveryTrackerPage(stage);
                Scene recoveryScene = recoveryPage.getScene();
                stage.setScene(recoveryScene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox backContainer = new HBox(backButton);
        backContainer.setAlignment(Pos.TOP_LEFT);
        backContainer.setPadding(new Insets(0, 0, 0, 10));
        rootContent.getChildren().add(backContainer);

        // Title
        Label title = new Label("Monthly Vaccination");
        title.setFont(Font.font("Arial Rounded MT Bold", 36));
        title.setTextFill(Color.DARKBLUE);
        rootContent.getChildren().add(title);

        // Image
        InputStream imageStream = getClass().getResourceAsStream("/assets/images/child_vaccine2.jpeg");
        if (imageStream != null) {
            Image image = new Image(imageStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(400);
            imageView.setFitHeight(250);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0.3, 4, 4);");
            rootContent.getChildren().add(imageView);
        }

        // Grid of vaccine info
        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.TOP_CENTER);

        String[][] vaccineData = {
            {"At Birth", "• BCG\n• Hepatitis B (1st dose)\n• OPV-0 (Polio)"},
            {"6 Weeks", "• DTP (1st)\n• IPV (1st)\n• Hep B (2nd)\n• Hib (1st)\n• Rota (1st)\n• PCV (1st)"},
            {"10 Weeks", "• DTP (2nd)\n• IPV (2nd)\n• Hib (2nd)\n• Rota (2nd)\n• PCV (2nd)"},
            {"14 Weeks", "• DTP (3rd)\n• IPV (3rd)\n• Hep B (3rd)\n• Hib (3rd)\n• Rota (3rd)\n• PCV (3rd)"},
            {"6 Months", "• Influenza (1st dose)"},
            {"7 Months", "• Influenza (2nd dose)"},
            {"9 Months", "• MMR (1st)\n• Typhoid (1st)\n• Vitamin A (1st)"},
            {"12 Months", "• Hep A (1st)\n• Varicella (1st)\n• PCV Booster"},
            {"15 Months", "• MMR (2nd)\n• Hib Booster\n• Varicella (2nd)\n• Vit A (2nd)"},
            {"18 Months", "• DTP Booster (1st)\n• IPV Booster\n• Hep A (2nd)\n• Typhoid Booster\n• Vit A (3rd)"},
            {"2–5 Years", "• Annual Influenza\n• Vitamin A (every 6 months)"},
            {"4–5 Years", "• DTP Booster (2nd)\n• Optional: MMR (3rd)"}
        };

        int col = 0, row = 0;
        for (int i = 0; i < vaccineData.length; i++) {
            VBox card = createVaccineCard(vaccineData[i][0], vaccineData[i][1], i);
            animateCard(card, i * 100);
            gridPane.add(card, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
        rootContent.getChildren().add(gridPane);

        ScrollPane scrollPane = new ScrollPane(rootContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(1550, 800);

        return new Scene(scrollPane);
    }

    private VBox createVaccineCard(String title, String content, int index) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Verdana", 20));
        titleLabel.setTextFill(Color.web("#263238"));

        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("Segoe UI", 15));
        contentLabel.setTextFill(Color.web("#37474F"));
        contentLabel.setWrapText(true);

        VBox card = new VBox(10, titleLabel, contentLabel);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefSize(230, 160);

        String[] colors = {
            "#FFE0B2", "#FFCCBC", "#F8BBD0", "#E1BEE7",
            "#D1C4E9", "#BBDEFB", "#B2EBF2", "#C8E6C9",
            "#DCEDC8", "#F0F4C3", "#FFECB3", "#CFD8DC"
        };
        card.setStyle("-fx-background-color: " + colors[index % colors.length] + ";" +
                "-fx-background-radius: 16; " +
                "-fx-border-radius: 16; " +
                "-fx-border-color: #90A4AE; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.5, 3, 3);");

        return card;
    }

    private void animateCard(VBox card, int delayMillis) {
        FadeTransition fade = new FadeTransition(Duration.millis(700), card);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(Duration.millis(delayMillis));

        ScaleTransition scale = new ScaleTransition(Duration.millis(700), card);
        scale.setFromX(0.9);
        scale.setFromY(0.9);
        scale.setToX(1);
        scale.setToY(1);
        scale.setDelay(Duration.millis(delayMillis));

        fade.play();
        scale.play();
    }
}