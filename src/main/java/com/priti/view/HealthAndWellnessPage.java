package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class HealthAndWellnessPage {
    // Accent and card theme colors for max readability
    private static final String ACCENT = "#DB4176";
    private static final String DIET1 = "#e260b3";
    private static final String DIET2 = "#7C3371";
    private static final String DIET3 = "#ae6ae6";
    private static final String YOGA1 = "#c568b8";
    private static final String YOGA2 = "#4C3C13";
    private static final String YOGA3 = "#bc77e0";
    private static final String TEXT_LIGHT = "#FAF7FB";
    private static final String TEXT_DARK = "#241633";

    private final Stage stage;
    private final BorderPane rootPane = new BorderPane();

    public HealthAndWellnessPage(Stage stage) {
        this.stage = stage;
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffe8f3 0%, #fff5fa 90%);");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        // Call buildHealthMenu and animate cards after UI is added
        VBox menu = buildHealthMenu();
        scrollPane.setContent(wrapScrollContent(menu));

        rootPane.setTop(buildTopBar());
        rootPane.setCenter(scrollPane);
    }

    public Scene getScene() {
        return new Scene(rootPane, 1550, 800);
    }

    private HBox buildTopBar() {
        Button backBtn = new Button("⬅ Back");
        backBtn.setPrefWidth(120);
        backBtn.setFont(Font.font("Segoe UI Semibold", 15));
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #DB4176d0, #bc77e0);" +
            "-fx-background-radius: 18;" +
            "-fx-effect: dropshadow(gaussian, #cc71a0AA, 4,0,0,2);"
        );
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction(e -> {
            PreHomePage home = new PreHomePage();
            stage.setScene(home.getScene(stage));
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(25, 40, 15, 40));
        return topBar;
    }

    private VBox buildHealthMenu() {
        VBox wrapper = new VBox(55);
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setPadding(new Insets(40, 20, 70, 20));
        wrapper.setMaxWidth(1280);

        Label title = new Label("Health & Wellness");
        title.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 46));
        title.setTextFill(Color.web(ACCENT));
        title.setStyle("-fx-effect: dropshadow(gaussian, #fff6fc99, 0,0,0,5);");

        // ---- Diet Section ----
        VBox dietSection = new VBox(50);
        dietSection.setAlignment(Pos.TOP_CENTER);

        Label dietHead = new Label("🥗 Diet Plan");
        dietHead.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 26));
        dietHead.setTextFill(Color.web(DIET1));

        HBox dietRow = new HBox(38);
        dietRow.setAlignment(Pos.CENTER);

        // ---- Yoga Section ----
        VBox yogaSection = new VBox(40);
        yogaSection.setAlignment(Pos.TOP_CENTER);

        Label yogaHead = new Label("🧘 Yoga Routine");
        yogaHead.setFont(Font.font("Segoe UI Black", FontWeight.EXTRA_BOLD, 26));
        yogaHead.setTextFill(Color.web(YOGA1));

        HBox yogaRow = new HBox(50);
        yogaRow.setAlignment(Pos.CENTER);

        // Create lists to hold card Nodes for animation
        List<StackPane> allCards = new ArrayList<>();

        // Diet cards
        StackPane card1 = phaseCard("Meal Trimester", DIET1, TEXT_LIGHT, "/assets/images/meal_plan.jpg");
        StackPane card2 = phaseCard("Daily Meal Chart", DIET2, TEXT_LIGHT, "/assets/images/daily_meal.jpg");
        StackPane card3 = phaseCard("Do's and Don'ts", DIET3, TEXT_DARK, "/assets/images/diet_donddonts.jpg");
        dietRow.getChildren().addAll(card1, card2, card3);

        allCards.add(card1); allCards.add(card2); allCards.add(card3);

        dietSection.getChildren().addAll(dietHead, dietRow);

        // Yoga cards
        StackPane card4 = phaseCard("Yoga Trimester", YOGA1, TEXT_LIGHT, "/assets/images/yoga_trimester.jpg");
        StackPane card5 = phaseCard("Exercise Videos", YOGA2, TEXT_LIGHT, "/assets/images/yoga_intro.jpg");
        StackPane card6 = phaseCard("Safety Instructions", YOGA3, TEXT_DARK, "/assets/images/yoga_safety.jpg");
        yogaRow.getChildren().addAll(card4, card5, card6);

        allCards.add(card4); allCards.add(card5); allCards.add(card6);

        yogaSection.getChildren().addAll(yogaHead, yogaRow);

        wrapper.getChildren().addAll(title, dietSection, yogaSection);

        // --- Animate all cards with TranslateTransition cascade ---
        animateCardsIn(allCards);

        return wrapper;
    }

    /** Animate each card vertical slide-in with a small stagger cascade. */
    private void animateCardsIn(java.util.List<StackPane> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Node card = cards.get(i);
            card.setOpacity(0); // Start invisible
            card.setTranslateY(100); // Start 100px below

            TranslateTransition tt = new TranslateTransition(Duration.millis(620), card);
            tt.setFromY(100);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition ft = new FadeTransition(Duration.millis(620), card);
            ft.setFromValue(0);
            ft.setToValue(1);

            ParallelTransition par = new ParallelTransition(tt, ft);
            par.setDelay(Duration.millis(i * 110));
            par.play();
        }
    }

    /**
     * Modern advanced card UI with large circular image and clear text alignment.
     */
    private StackPane phaseCard(String label, String color, String textColor, String imgPath) {
        StackPane pane = new StackPane();
        pane.setPrefSize(340, 360);

        Rectangle bg = new Rectangle(340, 360);
        bg.setArcWidth(38);
        bg.setArcHeight(38);
        bg.setFill(Color.web(color).deriveColor(0, 1, 1, 0.22));
        bg.setStroke(Color.web(color));
        bg.setStrokeWidth(1.8);
        bg.setEffect(new DropShadow(20, Color.web(color).deriveColor(0,1,0.8,0.38)));

        VBox content = new VBox(21);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(32, 20, 22, 20));

        ImageView icon = new ImageView(new Image(imgPath));
        icon.setFitWidth(120);
        icon.setFitHeight(120);
        Circle circ = new Circle(60, 60, 60);
        icon.setClip(circ);

        StackPane iconPane = new StackPane(icon);
        iconPane.setPadding(new Insets(1,0,0,0));
        iconPane.setStyle(
            "-fx-effect: dropshadow(gaussian," + color + "55, 20, 0.2, 0, 0);" +
            "-fx-background-radius:60;"
        );

        Label phLabel = new Label(label);
        phLabel.setFont(Font.font("Segoe UI Semibold", FontWeight.BOLD, 18));
        phLabel.setTextFill(Color.web(textColor));

        Label desc = new Label(phaseDescription(label));
        desc.setFont(Font.font("Segoe UI", 14));
        desc.setTextFill(Color.web(textColor + "E5"));
        desc.setWrapText(true);
        desc.setAlignment(Pos.TOP_CENTER);
        desc.setMaxWidth(220);

        Button exploreBtn = new Button("Explore ➔");
        exploreBtn.setFont(Font.font("Segoe UI Semibold", 13));
        exploreBtn.setTextFill(Color.WHITE);
        exploreBtn.setPrefWidth(132);
        exploreBtn.setPrefHeight(33);
        exploreBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, " + color + ", #ffffff33);" +
            "-fx-background-radius: 16;" +
            "-fx-padding: 6 12 6 12;" +
            "-fx-effect: dropshadow(gaussian, " + color + "66, 8,0,1,2);"
        );
        exploreBtn.setOnAction(e -> {
            switch (label) {
                case "Meal Trimester" -> stage.setScene(new MealTrimesterPage(stage).getScene());
                case "Daily Meal Chart" -> stage.setScene(new DailyMealChartPage(stage).getScene());
                case "Do's and Don'ts" -> stage.setScene(new DoAndDontsVideoPage(stage).getScene());
                case "Yoga Trimester" -> stage.setScene(new YogaTrimesterVideoPage(stage).getScene());
                case "Exercise Videos" -> stage.setScene(new ExerciseVideosPage(stage).getScene());
                case "Safety Instructions" -> stage.setScene(new SafetyInstructionsPage(stage).getScene());
            }
        });

        content.getChildren().addAll(iconPane, phLabel, desc, exploreBtn);
        pane.getChildren().addAll(bg, content);

        pane.setOnMouseEntered(e -> {
            pane.setScaleX(1.22);
            pane.setScaleY(1.22);
            bg.setEffect(new DropShadow(28, Color.web(color).deriveColor(0, 1, 0.85, 0.6)));
        });
        pane.setOnMouseExited(e -> {
            pane.setScaleX(1.0);
            pane.setScaleY(1.0);
            bg.setEffect(new DropShadow(20, Color.web(color).deriveColor(0,1,0.8,0.38)));
        });

        pane.setStyle("-fx-cursor: hand;");
        return pane;
    }

    private String phaseDescription(String label) {
        return switch (label) {
            case "Meal Trimester" -> "Personalized meal plans and key nutrition for each trimester.";
            case "Daily Meal Chart" -> "Your balanced daily meal chart: breakfast, lunch, snacks, and dinner guides.";
            case "Do's and Don'ts" -> "Essential dietary do's and don'ts for safe, healthy choices during pregnancy.";
            case "Yoga Trimester" -> "Recommended yoga activities by trimester—safe and doctor-approved.";
            case "Exercise Videos" -> "Guided routines: gentle yoga poses, stretches, and breathing practices.";
            case "Safety Instructions" -> "Critical safety tips and must-know instructions for yoga during pregnancy.";
            default -> "";
        };
    }

    private StackPane wrapScrollContent(Node content) {
        StackPane container = new StackPane();
        container.setAlignment(Pos.TOP_CENTER);
        container.getChildren().add(content);
        return container;
    }
}