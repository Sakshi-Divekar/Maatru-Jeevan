package com.priti.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TradRemediesPage {
    private static final String BG_START = "#A3CEF1";
    private static final String BG_MID = "#C9E4C5";
    private static final String BG_END = "#FFDFE6";

    private static final String FOOD_BOX_START = "#FCE4EC";
    private static final String FOOD_BOX_END = "#C5A5A5";

    private static final String HEALTH_BOX_START = "#C8E6C9";
    private static final String HEALTH_BOX_END = "#F8D0D0";

    private static final String TEXT_COLOR = "#2B3A42";
    private static final String BUTTON_GRADIENT_START = "#F48FB1";
    private static final String BUTTON_GRADIENT_END = "#81C784";

    private final Stage stage;

    public TradRemediesPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox root = new VBox(80);
        root.setPadding(new Insets(50, 60, 50, 60));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + BG_START + ", " + BG_MID + ", " + BG_END + ");");

        Label title = new Label("Traditional Tips & Home Remedies");
        title.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 40));
        title.setTextFill(Color.web(TEXT_COLOR));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FF80AB88, 0,0,0,4);");
        title.setTextAlignment(TextAlignment.CENTER);

        HBox boxesContainer = new HBox(50);
        boxesContainer.setAlignment(Pos.CENTER);

        VBox foodBox = createBigBox(
                "               Food for Baby & Mother",
                FOOD_BOX_START,
                FOOD_BOX_END,
                "/assets/images/baby_diet.jpg",
                "                 • Warm, cooked soups, porridges & root veggies.\n" +
                        "                 • Lactation boosters: sesame, fenugreek, garlic.\n" +
                        "                 • Herbal tonics & warm purees for babies.",
                () -> {
                    // Open PostTradeFood page when clicked
                    PostTradeFood postTradeFood = new PostTradeFood(stage);
                    stage.setScene(postTradeFood.getScene());
                }
        );

        VBox healthBox = createBigBox(
                "                 Health Remedies",
                HEALTH_BOX_START,
                HEALTH_BOX_END,
                "/assets/images/tradi2.jpg",
                "                 • Daily oil massage & gentle baby care.\n" +
                        "                 • Herbal baths with neem, turmeric, ginger.\n" +
                        "                 • Rest, warmth & immunity boosters for mother.",
                () -> {
                    // Open PostTradeFood page when clicked
                    PostTradeHealthRemedies postTradeHealthRemedies = new PostTradeHealthRemedies(stage);
                    stage.setScene(postTradeHealthRemedies.getScene());
                }
                
        );

        addHoverEffect(foodBox, Color.web("#F48FB1"), Color.web("#81C784"));
        addHoverEffect(healthBox, Color.web("#F48FB1"), Color.web("#81C784"));

        boxesContainer.getChildren().addAll(foodBox, healthBox);
        root.getChildren().addAll(title, boxesContainer);

        Button backBtn = new Button("⬅ Back");
        backBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        backBtn.setPrefWidth(185);
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, " + BUTTON_GRADIENT_START + ", " + BUTTON_GRADIENT_END + ");" +
                        "-fx-effect: dropshadow(gaussian, #f19ed222, 8, 0.2, 0, 2);" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 24;"
        );
        backBtn.setOnAction(e -> {
            PostHomePage postHome = new PostHomePage();
            stage.setScene(postHome.getScene(stage));
        });

        StackPane container = new StackPane(root);
        StackPane.setAlignment(backBtn, Pos.TOP_LEFT);
        StackPane.setMargin(backBtn, new Insets(25, 0, 0, 25));
        container.getChildren().add(backBtn);

        return new Scene(container, 1550, 800);
    }

    private VBox createBigBox(String heading, String gradientStart, String gradientEnd, String imgPath, String description, Runnable onExplore) {
        VBox box = new VBox(18);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(30, 25, 30, 25));
        box.setPrefSize(560, 500);
        box.setMaxWidth(560);
        box.setStyle("-fx-background-color: linear-gradient(to bottom right, " + gradientStart + ", " + gradientEnd + ");" +
                "-fx-background-radius: 35;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 14, 0.25, 0, 5);");

        ImageView imageView;
        try {
            imageView = new ImageView(new Image(imgPath, 180, 180, false, true));
        } catch (Exception e) {
            imageView = new ImageView();
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            imageView.setStyle("-fx-background-color: #ccc;");
        }
        Circle clip = new Circle(90, 90, 90);
        imageView.setClip(clip);
        imageView.setEffect(new DropShadow(12, Color.web("#7FB77E")));

        StackPane imagePane = new StackPane(imageView);
        imagePane.setPrefHeight(180);
        imagePane.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(heading);
        titleLabel.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 26));
        titleLabel.setTextFill(Color.web(TEXT_COLOR));
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setMaxWidth(480);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 15));
        descLabel.setTextFill(Color.web("#516B75"));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.LEFT);
        descLabel.setMaxWidth(500);
        descLabel.setPadding(new Insets(0, 10, 0, 10));

        Button exploreBtn = new Button("Explore");
        exploreBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
        exploreBtn.setPrefWidth(180);
        exploreBtn.setPadding(new Insets(14, 0, 14, 0));
        exploreBtn.setStyle("-fx-background-color: linear-gradient(to right, " + BUTTON_GRADIENT_START + ", " + BUTTON_GRADIENT_END + ");" +
                "-fx-background-radius: 28;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;" +
                "-fx-font-weight: bold;");
        if(onExplore != null) {
            exploreBtn.setOnAction(e -> onExplore.run());
        } else {
            exploreBtn.setDisable(true);
        }

        exploreBtn.setOnMouseEntered(e -> { });
        exploreBtn.setOnMouseExited(e -> { });

        box.getChildren().addAll(imagePane, titleLabel, descLabel, exploreBtn);
        return box;
    }

    private void addHoverEffect(javafx.scene.layout.Region node, Color glowColor1, Color glowColor2) {
        final double scaleFactor = 1.07;
        final Duration duration = Duration.millis(270);

        DropShadow glowShadow = new DropShadow(24, glowColor1);
        glowShadow.setSpread(0.25);

        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(duration, node);
            st.setToX(scaleFactor);
            st.setToY(scaleFactor);
            st.play();
            node.setEffect(glowShadow);
        });

        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(duration, node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            node.setEffect(null);
        });
    }
}