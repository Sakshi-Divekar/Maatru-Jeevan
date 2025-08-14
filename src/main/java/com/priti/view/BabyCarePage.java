package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BabyCarePage {
    // Updated color palette: pastel pink, blue, green, purple
    private static final String CARD1 = "#F9C6D3"; // pastel pink
    private static final String CARD2 = "#A0D8EF"; // pastel light blue
    private static final String CARD3 = "#B3E6C1"; // pastel mint green
    private static final String CARD4 = "#D8B7DD"; // pastel lavender purple
    private static final String TEXT_DARK = "#3B3054"; // soft dark purple for contrast

    private final Stage stage;
    private final StackPane rootPane = new StackPane();

    public BabyCarePage(Stage stage) {
        this.stage = stage;
        // Background gradient blending pastel pink, mint green, blue, and lavender
        rootPane.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #F9C6D3 0%, #B3E6C1 40%, #A0D8EF 75%, #D8B7DD 100%);"
        );
        VBox menu = buildMenu();
        rootPane.getChildren().add(menu);
        animateCardsTranslateIn(menu);
    }

    public Scene getScene() {
        Scene scene = new Scene(rootPane, 1550, 800);

        Button backBtn = new Button("⬅ Back");
        backBtn.setPrefWidth(180);
        backBtn.setFont(Font.font("Arial Rounded MT Bold", 17));
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #F9C6D3, #B3E6C1);" +
            "-fx-background-radius: 27;");
        backBtn.setTextFill(Color.web(TEXT_DARK));
        backBtn.setOnAction(e -> {
            PostHomePage home = new PostHomePage();
            stage.setScene(home.getScene(stage));
        });

        StackPane.setAlignment(backBtn, Pos.TOP_LEFT);
        StackPane.setMargin(backBtn, new Insets(20, 0, 0, 20));
        rootPane.getChildren().add(backBtn);

        return scene;
    }

    private VBox buildMenu() {
        VBox wrapper = new VBox(40);
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setPadding(new Insets(52, 30, 54, 30));
        wrapper.setMaxWidth(1100);

        Label title = new Label("Baby Care");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 43));
        title.setTextFill(Color.web(CARD2)); // pastel blue
        title.setStyle("-fx-effect: dropshadow(gaussian, #FFFFFF99, 0,0,0,3);");
        VBox.setMargin(title, new Insets(0, 0, 32, 0));

        HBox cardRow = new HBox(38);
        cardRow.setAlignment(Pos.CENTER);

        List<StackPane> cards = new ArrayList<>();
        cards.add(phaseCard("Massage Tutorials", CARD2,
            "/assets/images/baby_massage1.jpg",
            "Video and step-by-step guidance for safe baby massages: improve sleep, growth, and bonding.", () -> {
                BabyMassageTutorial tutorialApp = new BabyMassageTutorial(stage);
                stage.setScene(tutorialApp.getScene());
            }));
        cards.add(phaseCard("Baby Diet Plan", CARD1,
            "/assets/images/baby_diet.jpg",
            "Personalized feeding charts, when to introduce solids, healthy recipes, and allergy safety.", () -> {
                BabyDietPlan dietPage = new BabyDietPlan(stage);
                stage.setScene(dietPage.getScene());
            }));
        cards.add(phaseCard("Baby Care Products", CARD3,
            "/assets/images/baby_products.jpg",
            "Recommended and reviewed products for bath, hygiene, skin, and comfort.", () -> {
                BabyCareProducts productUI = new BabyCareProducts(stage);
                stage.setScene(productUI.getScene());
            }));

        cardRow.getChildren().addAll(cards);
        wrapper.getChildren().addAll(title, cardRow);
        wrapper.setUserData(cards);

        return wrapper;
    }

    private void animateCardsTranslateIn(VBox menu) {
        @SuppressWarnings("unchecked")
        List<StackPane> cards = (List<StackPane>) menu.getUserData();
        if (cards == null) return;
        for (int i = 0; i < cards.size(); i++) {
            StackPane card = cards.get(i);
            card.setOpacity(0);
            card.setTranslateY(120);

            TranslateTransition tt = new TranslateTransition(Duration.millis(600), card);
            tt.setFromY(120);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition ft = new FadeTransition(Duration.millis(600), card);
            ft.setFromValue(0);
            ft.setToValue(1);

            ParallelTransition pt = new ParallelTransition(tt, ft);
            pt.setDelay(Duration.millis(i * 130));
            pt.play();
        }
    }

    private StackPane phaseCard(String label, String color, String imgPath, String desc, Runnable onClick) {
        StackPane pane = new StackPane();
        pane.setPrefSize(250, 380);

        Rectangle bg = new Rectangle(250, 380);
        bg.setArcWidth(54);
        bg.setArcHeight(54);
        bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color + "BB")),
            new Stop(1, Color.WHITE)));
        bg.setStroke(Color.web(color));
        bg.setStrokeWidth(2.4);
        bg.setEffect(new DropShadow(18, Color.web(color + "99")));

        ImageView icon = new ImageView(new Image(imgPath));
        icon.setFitWidth(92);
        icon.setFitHeight(92);
        icon.setClip(new Circle(46, 46, 46));

        StackPane iconPane = new StackPane(icon);
        iconPane.setPadding(new Insets(30, 0, 12, 0));

        Label phLabel = new Label(label);
        phLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 21));
        phLabel.setTextFill(Color.web(TEXT_DARK));
        phLabel.setPadding(new Insets(0, 0, 10, 0));

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // bold for high contrast
        descLabel.setTextFill(Color.web(TEXT_DARK));
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.TOP_CENTER);
        descLabel.setMaxWidth(177);

        Button exploreBtn = new Button("Explore");
        exploreBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        exploreBtn.setStyle("-fx-background-color: #FFFFFFDD; -fx-text-fill: " + color + "; -fx-border-color: " + color + "; -fx-background-radius: 17; -fx-border-radius: 17;");
        exploreBtn.setOnAction(e -> {
            animateCardClick(pane, onClick);
        });

        VBox content = new VBox(10, iconPane, phLabel, descLabel, exploreBtn);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(14, 16, 14, 16));

        pane.getChildren().addAll(bg, content);
        pane.setOnMouseEntered(e -> {
            pane.setScaleX(1.065);
            pane.setScaleY(1.065);
            bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color)), new Stop(1, Color.WHITE)));
            bg.setEffect(new DropShadow(24, Color.web(color + "CC")));
        });
        pane.setOnMouseExited(e -> {
            pane.setScaleX(1.0);
            pane.setScaleY(1.0);
            bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color + "BB")), new Stop(1, Color.WHITE)));
            bg.setEffect(new DropShadow(18, Color.web(color + "99")));
        });

        return pane;
    }

    private void animateCardClick(StackPane card, Runnable onComplete) {
        ScaleTransition s1 = new ScaleTransition(Duration.millis(80), card);
        s1.setToX(0.97);
        s1.setToY(0.97);
        ScaleTransition s2 = new ScaleTransition(Duration.millis(80), card);
        s2.setToX(1);
        s2.setToY(1);
        SequentialTransition seq = new SequentialTransition(s1, s2);
        seq.setOnFinished(e -> onComplete.run());
        seq.play();
    }
}