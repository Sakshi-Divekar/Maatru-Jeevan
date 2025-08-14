package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DailyMealChartPage {

    private final Stage stage;

    public DailyMealChartPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        // Kid-friendly pink to blue background gradient
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFC1E3, #A7D8FF);");

        // --- Back button ---
        Button backBtn = new Button("\u2190 Back ");
        backBtn.setPrefHeight(35);
        backBtn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
        backBtn.setTextFill(Color.web("#143759")); // Dark blue text
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #FFB6C1, #87CEFA);" +
                "-fx-background-radius: 20; -fx-cursor: hand;" +
                "-fx-border-radius: 20; -fx-border-color: #8A2BE2; -fx-border-width: 2;");
        backBtn.setEffect(new DropShadow(8, Color.web("#70707066")));
        backBtn.setOnAction(e -> {
            HealthAndWellnessPage wellnessPage = new HealthAndWellnessPage(stage);
            stage.setScene(wellnessPage.getScene());
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(10, 0, 0, 20));

        // --- Title and Subtitle ---
        Label title = new Label("Daily Nutrition Plan");
        title.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 34));
        title.setTextFill(Color.web("#2E0854")); // Dark purple

        Label subtitle = new Label("Your gentle guide to balanced eating throughout the day");
        subtitle.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 17));
        subtitle.setTextFill(Color.web("#2E0854"));

        VBox titleBox = new VBox(title, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(8);

        BorderPane headerPane = new BorderPane();
        headerPane.setLeft(backBox);
        headerPane.setCenter(titleBox);
        headerPane.setPadding(new Insets(20, 20, 12, 20));

        VBox headerBox = new VBox(headerPane);
        headerBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10,0,0,3);");

        // --- Meal Blocks Grid ---
        GridPane mealGrid = new GridPane();
        mealGrid.setAlignment(Pos.CENTER);
        mealGrid.setHgap(28);
        mealGrid.setVgap(28);
        mealGrid.setPadding(new Insets(25, 45, 45, 45));

        // Prepare blocks for animation
        List<VBox> mealBlocks = new ArrayList<>();

        VBox block1 = createMealBlock("6 AM", "Hydration", "#FFA3B1", "#79C7F3", "Warm lemon water\nor coconut water");
        VBox block2 = createMealBlock("8 AM", "Breakfast", "#84B2E1", "#FF9AC8", "Oats with fruits\nBoiled eggs\nMilk");
        VBox block3 = createMealBlock("11 AM", "Mid-Snack", "#8CC4FF", "#FF7FBB", "Mixed nuts\nSeeds\nButtermilk");
        VBox block4 = createMealBlock("1 PM", "Lunch", "#FF91BA", "#76C7FF", "Chapati\nDal & rice\nVegetables\nSalad");
        VBox block5 = createMealBlock("4 PM", "Evening", "#79B6F3", "#FF85B9", "Fruit bowl\nSprouts\nHerbal tea");
        VBox block6 = createMealBlock("7 PM", "Dinner", "#FFA6C1", "#6EB4FF", "Light khichdi\nVegetable soup\nSalad");
        VBox block7 = createMealBlock("9 PM", "Bedtime", "#7FC3FF", "#FF99B7", "Warm milk\nSoaked almonds");

        mealBlocks.add(block1);
        mealBlocks.add(block2);
        mealBlocks.add(block3);
        mealBlocks.add(block4);
        mealBlocks.add(block5);
        mealBlocks.add(block6);
        mealBlocks.add(block7);

        mealGrid.add(block1, 0, 0);
        mealGrid.add(block2, 1, 0);
        mealGrid.add(block3, 2, 0);
        mealGrid.add(block4, 0, 1);
        mealGrid.add(block5, 1, 1);
        mealGrid.add(block6, 2, 1);
        mealGrid.add(block7, 0, 2);

        GridPane.setColumnSpan(block7, 3);
        GridPane.setHalignment(block7, HPos.CENTER);

        // --- Animate blocks: scale (grow in) effect ---
        playScaleAnimation(mealBlocks);

        // --- ScrollPane ---
        ScrollPane scrollPane = new ScrollPane(mealGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // --- Final Layout ---
        root.setTop(headerBox);
        root.setCenter(scrollPane);

        return new Scene(root, 1550, 800);
    }

    private VBox createMealBlock(String time, String mealType, String color1, String color2, String content) {
        VBox block = new VBox(15);
        block.setAlignment(Pos.TOP_CENTER);
        block.setPadding(new Insets(18));
        block.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85); -fx-background-radius: 25;" +
                       "-fx-border-radius: 25; -fx-border-color: #b19cd9; -fx-border-width: 3;");
        block.setEffect(new DropShadow(14, Color.web("#8A2BE266")));

        StackPane circlePane = new StackPane();
        circlePane.setPrefSize(180, 180);

        Circle circle = new Circle(80);
        circle.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color1)),
                new Stop(1, Color.web(color2))));
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(4);
        circle.setEffect(new DropShadow(12, Color.web("#6B5B95AA")));

        VBox textBox = new VBox(5);
        Label timeLabel = new Label(time);
        timeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
        timeLabel.setTextFill(Color.web("#3A0CA3")); // Darker blue/purple

        Label mealTypeLabel = new Label(mealType);
        mealTypeLabel.setFont(Font.font("Comic Sans MS", FontWeight.SEMI_BOLD, 18));
        mealTypeLabel.setTextFill(Color.web("#560BAD")); // Deep purple

        textBox.getChildren().addAll(timeLabel, mealTypeLabel);
        textBox.setAlignment(Pos.CENTER);

        circlePane.getChildren().addAll(circle, textBox);

        Text desc = new Text(content);
        desc.setFont(Font.font("Comic Sans MS", 15));
        desc.setFill(Color.web("#310841")); // Dark purple
        desc.setTextAlignment(TextAlignment.CENTER);
        desc.setWrappingWidth(180);

        Label tipLabel = new Label("\u2727 Nutrition Tip");
        tipLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 13));
        tipLabel.setTextFill(Color.web("#ff4da6")); // playful pink
        tipLabel.setPadding(new Insets(7, 12, 7, 12));
        tipLabel.setStyle("-fx-background-color: #9c27b0; -fx-background-radius: 12;");

        block.getChildren().addAll(circlePane, desc, tipLabel);

        block.setOnMouseEntered(e -> {
            circle.setEffect(new DropShadow(22, Color.web("#6B5B95FF")));
            block.setScaleX(1.06);
            block.setScaleY(1.06);
        });

        block.setOnMouseExited(e -> {
            circle.setEffect(new DropShadow(12, Color.web("#6B5B95AA")));
            block.setScaleX(1.0);
            block.setScaleY(1.0);
        });

        return block;
    }

    /** Animate: Each block grows in scale from small to full size with a stagger. */
    private void playScaleAnimation(List<VBox> blocks) {
        double startScale = 0.65;
        double blockDelaySeconds = 0.12;

        for (int i = 0; i < blocks.size(); i++) {
            VBox block = blocks.get(i);
            block.setScaleX(startScale);
            block.setScaleY(startScale);
            block.setOpacity(0);

            ScaleTransition scale = new ScaleTransition(Duration.millis(440), block);
            scale.setFromX(startScale);
            scale.setFromY(startScale);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fade = new FadeTransition(Duration.millis(440), block);
            fade.setFromValue(0);
            fade.setToValue(1);

            ParallelTransition anim = new ParallelTransition(scale, fade);
            anim.setDelay(Duration.seconds(blockDelaySeconds * i));
            anim.play();
        }
    }
}