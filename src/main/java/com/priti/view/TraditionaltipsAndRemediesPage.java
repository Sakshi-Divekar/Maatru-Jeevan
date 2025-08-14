package com.priti.view;

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
import javafx.animation.*;
import javafx.util.Duration;
import java.util.*;

public class TraditionaltipsAndRemediesPage {
    // Kid-friendly, colorful gradients
    private static final String ACCENT = "#ff66c4"; // bright pink
    private static final String REMEDY1 = "#6ecbff"; // sky blue
    private static final String REMEDY2 = "#ffd36b"; // pastel yellow
    private static final String BOOK1   = "#ff6f61"; // coral red
    private static final String BOOK2   = "#82e08a"; // mint green
    private static final String TEXT_DARK = "#3A2B41"; // for contrast

    private final Stage stage;
    private final StackPane rootPane = new StackPane();

    public TraditionaltipsAndRemediesPage(Stage stage) {
        this.stage = stage;
        rootPane.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #fffbe8 0%, #ffe0f7 35%, #b6f1e8 100%);"
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        HBox centeredBox = new HBox();
        centeredBox.setAlignment(Pos.TOP_CENTER);
        centeredBox.setPadding(new Insets(36));
        VBox menu = buildTraditionalMenu();
        centeredBox.getChildren().add(menu);

        scrollPane.setContent(centeredBox);
        rootPane.getChildren().add(scrollPane);
    }

    public Scene getScene() {
        return new Scene(rootPane, 1550, 800);
    }

    private VBox buildTraditionalMenu() {
        VBox wrapper = new VBox(42);
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setMaxWidth(1500);
        wrapper.setMinWidth(1500);

        HBox backWrapper = new HBox();
        backWrapper.setAlignment(Pos.TOP_LEFT);

        Button backBtn = new Button("⬅ Back");
        backBtn.setPrefWidth(150);
        backBtn.setFont(Font.font("Comic Sans MS", 18));
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #ff66c499, #ffd36bbb);" +
                         "-fx-background-radius: 18;");
        backBtn.setTextFill(Color.web(TEXT_DARK));
        backBtn.setOnAction(e -> {
            PreHomePage home = new PreHomePage();
            stage.setScene(home.getScene(stage));
        });

        backWrapper.getChildren().add(backBtn);
        VBox.setMargin(backWrapper, new Insets(0, 0, 18, 0));

        Label title = new Label("Traditional Tips & Home Remedies");
        title.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 50));
        title.setTextFill(Color.web(ACCENT));
        title.setStyle("-fx-effect: dropshadow(gaussian, #fff6fc99, 0,0,0,3);");
        
        VBox.setMargin(title, new Insets(0, 0, 20, 0));

        // Cards
        StackPane herbalCard = phaseCard("Herbal Teas", REMEDY1, "/assets/images/herbal_teas.jpg");
        StackPane homeCuresCard = phaseCard("Home Cures", REMEDY2, "/assets/images/tradi_haladi.jpg");
        StackPane wisdomCard = phaseCard("Motherhood Wisdom", BOOK2, "/assets/images/tradi_health.jpg");

        // --- Layout of category headers and cards in proper row ---

        // Health & Food Remedies header shifted right, above its two cards arranged horizontally
        VBox remedyHeaderBox = new VBox(5);
        remedyHeaderBox.setAlignment(Pos.CENTER_LEFT);
        remedyHeaderBox.setPadding(new Insets(0,0,0,230));  // Shift right by 45 px

        Label remedyIcon = new Label("🌱");
        remedyIcon.setPadding(new Insets(0,0,0,170));
        remedyIcon.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
        remedyIcon.setTextFill(Color.web(REMEDY1));
        Label remedyHead = new Label("Health & Food Remedies");
        remedyHead.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 33));
        remedyHead.setTextFill(Color.web(REMEDY1));
        remedyHead.setStyle("-fx-letter-spacing: 1;");

        remedyHeaderBox.getChildren().addAll(remedyIcon, remedyHead);

        HBox remedyCardsBox = new HBox(70, herbalCard, homeCuresCard);
        remedyCardsBox.setAlignment(Pos.CENTER);

        VBox leftCategoryBox = new VBox(15, remedyHeaderBox, remedyCardsBox);
        leftCategoryBox.setAlignment(Pos.TOP_RIGHT);

        // Traditional Book Reading header and single card
        VBox bookCategoryBox = new VBox(12);
        bookCategoryBox.setAlignment(Pos.TOP_CENTER);

        Label bookIcon = new Label("📖");
        bookIcon.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
        bookIcon.setTextFill(Color.web(BOOK1));
        Label bookHead = new Label("Traditional Book Reading");
        bookHead.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 33));
        bookHead.setTextFill(Color.web(BOOK1));
        bookHead.setStyle("-fx-letter-spacing: 1;");

        bookCategoryBox.getChildren().addAll(bookIcon, bookHead, wisdomCard);

        // Main HBox for the entire row with spacing between categories
        HBox mainRow = new HBox(100, leftCategoryBox, bookCategoryBox);
        mainRow.setAlignment(Pos.CENTER);

        wrapper.getChildren().addAll(backWrapper, title, mainRow);

        // Animate all cards
        List<StackPane> allCards = new ArrayList<>();
        allCards.add(herbalCard);
        allCards.add(homeCuresCard);
        allCards.add(wisdomCard);
        animateCardsIn(allCards);

        return wrapper;
    }

    /**
     * Animate each card with a fade & vertical slide cascade effect.
     * Each card slides in from below and fades from opacity 0 to 1, staggered.
     */
    private void animateCardsIn(List<StackPane> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Node card = cards.get(i);
            card.setOpacity(0);
            card.setTranslateY(100);

            TranslateTransition tt = new TranslateTransition(Duration.millis(620), card);
            tt.setFromY(100);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition ft = new FadeTransition(Duration.millis(620), card);
            ft.setFromValue(0);
            ft.setToValue(1);

            ParallelTransition pt = new ParallelTransition(tt, ft);
            pt.setDelay(Duration.millis(i * 120));
            pt.play();
        }
    }

    // Card with bigger dimensions, larger images & playful gradient borders
    private StackPane phaseCard(String label, String color, String imgPath) {
        StackPane pane = new StackPane();
        pane.setPrefSize(400, 480);

        Rectangle bg = new Rectangle(400, 480);
        bg.setArcWidth(46);
        bg.setArcHeight(46);
        bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color + "bb")),
            new Stop(1, Color.web("#FFFFFF"))));
        bg.setStroke(Color.web(color));
        bg.setStrokeWidth(2.7);
        bg.setEffect(new DropShadow(20, Color.web(color + "46")));

        ImageView icon = new ImageView(new Image(imgPath));
        icon.setFitWidth(175);
        icon.setFitHeight(175);
        Circle circ = new Circle(87.5, 87.5, 87.5);
        icon.setClip(circ);

        StackPane iconPane = new StackPane(icon);
        iconPane.setPadding(new Insets(10, 0, 10, 0));

        Label phLabel = new Label(label);
        phLabel.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 25));
        phLabel.setTextFill(Color.web(color));

        Label desc = new Label(phaseDescription(label));
        desc.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 17));
        desc.setTextFill(Color.web(TEXT_DARK));
        desc.setWrapText(true);
        desc.setMaxWidth(320);
        desc.setAlignment(Pos.TOP_CENTER);

        Button exploreBtn = new Button("Explore");
        exploreBtn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 16));
        exploreBtn.setPrefWidth(140);
        exploreBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, " + color + ", #fff59d);" +
            "-fx-background-radius: 26;" +
            "-fx-border-radius: 26;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2.4;"
        );
        exploreBtn.setTextFill(Color.web(TEXT_DARK));
        exploreBtn.setOnAction(e -> {
            if ("Herbal Teas".equals(label)) {
                HerbalTeasPage herbalPage = new HerbalTeasPage(stage);
                stage.setScene(herbalPage.getScene());
            } else if ("Home Cures".equals(label)) {
                HomeCuresPage homeCuresPage = new HomeCuresPage(stage);
                stage.setScene(homeCuresPage.getScene());
            } else if ("Motherhood Wisdom".equals(label)) {
                MotherhoodWisdomPage wisdomPage = new MotherhoodWisdomPage(stage);
                stage.setScene(wisdomPage.getScene());
            }
        });

        VBox content = new VBox(20, iconPane, phLabel, desc, exploreBtn);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(15, 15, 15, 15));

        pane.getChildren().addAll(bg, content);

        pane.setOnMouseEntered(e -> {
            pane.setScaleX(1.025);
            pane.setScaleY(1.025);
            bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web(color)),
                    new Stop(1, Color.WHITE)));
        });
        pane.setOnMouseExited(e -> {
            pane.setScaleX(1.0);
            pane.setScaleY(1.0);
            bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.web(color + "bb")),
                    new Stop(1, Color.web("#FFFFFF"))));
        });

        pane.setStyle("-fx-cursor: hand;");

        return pane;
    }

    // Friendly, easy-to-read descriptions
    private String phaseDescription(String label) {
        switch (label) {
            case "Herbal Teas":
                return "Yummy and healthy teas made from plants. Good for tummy and helps you feel stronger!";
            case "Home Cures":
                return "Simple tricks from kitchen to feel better. Easy, tasty, and fun for families!";
            case "Motherhood Wisdom":
                return "Special advice from moms and grandmas. Stories and tips passed through generations!";
            default:
                return "";
        }
    }
}