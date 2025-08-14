package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MealTrimesterPage {

    private final Stage stage;

    public MealTrimesterPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #ffe0f0, #dbeafe);" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );

        // Header
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(36, 30, 10, 52));

        Button backBtn = new Button("← Back");
        backBtn.setPrefWidth(115);
        backBtn.setPrefHeight(44);
        backBtn.setFont(Font.font("Segoe UI Semibold", FontWeight.SEMI_BOLD, 16));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #ec4899, #3aaeaaff);" +
            "-fx-background-radius: 22; -fx-cursor: hand;"
        );
        backBtn.setEffect(new DropShadow(8, Color.web("#8b5cf644")));
        backBtn.setOnAction(e -> {
            HealthAndWellnessPage wellnessPage = new HealthAndWellnessPage(stage);
            stage.setScene(wellnessPage.getScene());
        });

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Pregnancy Nutrition Guide");
        title.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 38));
        title.setTextFill(Color.web("#1e1b4b"));

        Label subtitle = new Label("Essential dietary recommendations for each trimester");
        subtitle.setFont(Font.font("Segoe UI", FontPosture.ITALIC, 17));
        subtitle.setTextFill(Color.web("#334155"));

        titleBox.getChildren().addAll(title, subtitle);
        headerBox.setSpacing(36);
        headerBox.getChildren().addAll(backBtn, titleBox);

        // Cards container
        VBox cardsBox = new VBox(44);
        cardsBox.setAlignment(Pos.TOP_CENTER);
        cardsBox.setPadding(new Insets(10, 15, 60, 15));

        HBox topRow = new HBox(60);
        topRow.setAlignment(Pos.CENTER);

        HBox bottomRow = new HBox(60);
        bottomRow.setAlignment(Pos.CENTER);

        Image firstTriImage = new Image(getClass().getResource("/assets/images/first_tri_food.jpg").toExternalForm());
        Image secondTriImage = new Image(getClass().getResource("/assets/images/second_tri_food.jpg").toExternalForm());
        Image thirdTriImage = new Image(getClass().getResource("/assets/images/third_tri_food.jpg").toExternalForm());

        // --- Collect card nodes for animation ---
        List<StackPane> cardsToAnimate = new ArrayList<>();

        StackPane firstCard = createTrimesterCard(
            "First Trimester", "#c04283ff", "#3fd5d7ff", "#1e1b4b",
            new String[]{
                "Folic Acid: Crucial for neural tube development",
                "Vitamin B6: Helps with nausea relief",
                "Iron: Supports increased blood volume",
                "Calcium: For baby's bone development"
            },
            new String[]{
                "🍃 Leafy greens (spinach, kale)",
                "🥜 Nuts and seeds (almonds, walnuts)",
                "🍊 Citrus fruits (oranges)",
                "🥛 Fortified dairy products"
            },
            new String[]{
                "Eat small, frequent meals to combat nausea",
                "Stay hydrated with water and herbal teas",
                "Avoid raw seafood and unpasteurized dairy"
            },
            firstTriImage
        );
        cardsToAnimate.add(firstCard);
        topRow.getChildren().add(firstCard);

        StackPane secondCard = createTrimesterCard(
            "Second Trimester", "#a257caff", "#45c7b6ff", "#1e1b4b",
            new String[]{
                "Omega-3: For baby's brain development",
                "Vitamin D: Helps absorb calcium",
                "Protein: Supports rapid growth",
                "Fiber: Prevents constipation"
            },
            new String[]{
                "🐟 Fatty fish (salmon, sardines)",
                "🥚 Eggs (esp. yolks)",
                "🥑 Healthy fats (avocados, olive oil)",
                "🌾 Whole grains (quinoa, brown rice)"
            },
            new String[]{
                "Increase calorie intake by 300-350/day",
                "Continue prenatal vitamins",
                "Limit caffeine to 200mg daily"
            },
            secondTriImage
        );
        StackPane thirdCard = createTrimesterCard(
            "Third Trimester", "#b42793ff", "#1d7b87ff", "#1e1b4b",
            new String[]{
                "Vitamin K: For blood clotting",
                "Magnesium: Helps with muscle cramps",
                "Complex carbs: For sustained energy",
                "Probiotics: Supports digestion"
            },
            new String[]{
                "🥬 Green vegetables (broccoli, Brussels sprouts)",
                "🍌 Potassium-rich foods (bananas, sweet potatoes)",
                "🥛 Probiotic foods (yogurt, kefir)",
                "🍗 Lean proteins (chicken, lentils)"
            },
            new String[]{
                "Eat smaller portions more frequently",
                "Focus on iron-rich foods",
                "Stay hydrated to prevent contractions"
            },
            thirdTriImage
        );
        cardsToAnimate.add(secondCard);
        cardsToAnimate.add(thirdCard);

        bottomRow.getChildren().addAll(secondCard, thirdCard);
        cardsBox.getChildren().addAll(topRow, bottomRow);

        // Animate the cards into view:
        playDropDownAnimation(cardsToAnimate);

        ScrollPane scrollPane = new ScrollPane(cardsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setTop(headerBox);
        root.setCenter(scrollPane);

        return new Scene(root, 1550, 800);
    }

    private StackPane createTrimesterCard(
        String trimesterName, String colorFrom, String colorTo, String txtColor,
        String[] nutrients, String[] foods, String[] notes, Image image
    ) {
        final double WIDTH = 500, HEIGHT = 700;
        StackPane card = new StackPane();
        card.setPrefSize(WIDTH, HEIGHT);

        Rectangle background = new Rectangle(WIDTH, HEIGHT);
        background.setArcWidth(40);
        background.setArcHeight(40);
        background.setFill(new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(colorFrom)),
            new Stop(1, Color.web(colorTo))
        ));
        background.setStroke(Color.web("#ffffff55"));
        background.setStrokeWidth(1.5);

        DropShadow shadow = new DropShadow(20, Color.web("#00000033"));
        card.setEffect(shadow);

        double imgRadius = 70;
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imgRadius * 2);
        imageView.setFitHeight(imgRadius * 2);
        imageView.setClip(new Circle(imgRadius, imgRadius, imgRadius));

        Circle border = new Circle(imgRadius + 4);
        border.setStroke(Color.web("#fefefe88"));
        border.setStrokeWidth(2);
        border.setFill(Color.TRANSPARENT);

        StackPane imagePane = new StackPane(border, imageView);
        imagePane.setPadding(new Insets(20, 0, 10, 0));

        Label header = new Label(trimesterName);
        header.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 26));
        header.setTextFill(Color.web(txtColor));

        Separator sep = new Separator();
        sep.setMaxWidth(220);
        sep.setStyle("-fx-border-color: #ffffff33;");

        VBox content = new VBox(18);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(10, 28, 20, 28));
        content.getChildren().addAll(
            createSection("🧪 Key Nutrients", nutrients, txtColor),
            createSection("🍎 Foods", foods, txtColor),
            createSection("📝 Notes", notes, txtColor)
        );

        VBox layout = new VBox(12, imagePane, header, sep, content);
        layout.setAlignment(Pos.TOP_CENTER);

        card.getChildren().addAll(background, layout);

        card.setOnMouseEntered(e -> card.setScaleX(1.035));
        card.setOnMouseExited(e -> card.setScaleX(1.0));

        return card;
    }

    private VBox createSection(String title, String[] lines, String txtColor) {
        VBox section = new VBox(6);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI Semibold", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(txtColor));
        section.getChildren().add(titleLabel);

        VBox bullets = new VBox(6);
        for (String line : lines) {
            HBox bulletLine = new HBox(6);
            bulletLine.setAlignment(Pos.TOP_LEFT);

            Label bullet = new Label("•");
            bullet.setFont(Font.font("Segoe UI", 15));
            bullet.setTextFill(Color.web(txtColor));

            Label text = new Label(line);
            text.setFont(Font.font("Segoe UI", 14));
            text.setTextFill(Color.web(txtColor + "EE"));
            text.setWrapText(true);
            text.setMaxWidth(360);

            bulletLine.getChildren().addAll(bullet, text);
            bullets.getChildren().add(bulletLine);
        }

        section.getChildren().add(bullets);
        return section;
    }

    // --- CARD DROP ANIMATION ---
    private void playDropDownAnimation(java.util.List<StackPane> cards) {
        double startOffset = -330; // start above
        double bounceHeight = 38;
        double blockDelaySeconds = 0.08;

        for (int i = 0; i < cards.size(); i++) {
            StackPane card = cards.get(i);
            card.setOpacity(0);
            card.setTranslateY(startOffset);

            Timeline anim = new Timeline(
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.01), // small entry delay for cascade
                    e -> card.setOpacity(1)),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.28),
                    new KeyValue(card.translateYProperty(), 12, Interpolator.EASE_IN)),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.35),
                    new KeyValue(card.translateYProperty(), -bounceHeight, Interpolator.EASE_OUT)),
                new KeyFrame(Duration.seconds(blockDelaySeconds * i + 0.43),
                    new KeyValue(card.translateYProperty(), 0, Interpolator.EASE_BOTH))
            );
            anim.play();
        }
    }
}