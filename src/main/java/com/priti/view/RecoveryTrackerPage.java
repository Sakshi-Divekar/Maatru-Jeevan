package com.priti.view;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.List;

public class RecoveryTrackerPage {
    private static final String MINT_TEAL = "#91DDCF";
    private static final String PALE_WHITE = "#F7F9F2";
    private static final String SOFT_VIOLET = "#E8C5E5";
    private static final String BABY_PINK = "#F19ED2";
    private static final String TEXT = "#294152";
    private static final String ACCENT = "#816797";
    private final Stage stage;

    public RecoveryTrackerPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + PALE_WHITE + ", " + MINT_TEAL + ");");

        VBox content = new VBox(16);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(28, 0, 10, 0));

        Label title = new Label("Recovery Tracker System");
        title.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 35));
        title.setTextFill(Color.web(ACCENT));
        title.setStyle("-fx-effect: dropshadow(gaussian, #e8c5e599, 0,0,0,2);");
        title.setPadding(new Insets(25, 0, 12, 0));
        title.setTextAlignment(TextAlignment.CENTER);

        Label subtitle = new Label("Monitor your baby's health & documentation, and nurture their milestones—all in one view.");
        subtitle.setFont(Font.font("Poppins", FontWeight.NORMAL, 16));
        subtitle.setTextFill(Color.web("#768799"));
        subtitle.setPadding(new Insets(0, 0, 30, 0));
        subtitle.setWrapText(true);

        HBox cards = new HBox(52);
        cards.setAlignment(Pos.CENTER);
        cards.setPadding(new Insets(24, 0, 18, 0));

        // Collect all cards for animation
        List<VBox> cardList = new ArrayList<>();

        cardList.add(featureCard("💉 Monthly Vaccination", MINT_TEAL, "/assets/images/mothly_vaccine.jpg",
            "Keep your baby's immunizations up to date!<br><b>Track upcoming doses</b> for BCG, DTP, Polio, Hepatitis B, Rotavirus, and more. <b>Get reminders and see records at a glance</b>. Learn about side effects and post-vaccine care tips."));
        cardList.add(featureCard("👶 Age-based Activities", SOFT_VIOLET, "/assets/images/age_basedactivites.jpg",
            "<b>Encourage growth at every age!</b><br>Get curated play and motion ideas for every month—from tummy time to grasping, babbling, and first steps. <b>Track milestones</b> and discover fun, safe activities that boost cognitive & physical skills."));
        cardList.add(featureCard("📄 Legal Document Info", BABY_PINK, "/assets/images/child_legal.jpg",
            "Stay ready for every formality!<br><b>Birth Certificate:</b> Steps and important deadlines.<br><b>Insurance & Aadhaar:</b> What documents you need, tips for smooth application, and managing digital health records securely."));

        cards.getChildren().addAll(cardList);

        content.getChildren().addAll(title, subtitle, cards);

        // Back Button positioned separately
        Button backBtn = new Button("⬅ Back");
        backBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        backBtn.setPrefWidth(185);
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, " + SOFT_VIOLET + ", " + MINT_TEAL + ");" +
            "-fx-background-radius: 24;" +
            "-fx-effect: dropshadow(gaussian, #e8c5e544, 8,0.20,0,2);" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill:" + PALE_WHITE + ";"
        );
        backBtn.setOnAction(e -> {
            PostHomePage postHome = new PostHomePage();
            stage.setScene(postHome.getScene(stage));
        });

        StackPane.setAlignment(backBtn, Pos.TOP_LEFT);
        StackPane.setMargin(backBtn, new Insets(20, 0, 0, 20));

        root.getChildren().addAll(content, backBtn);

        // Animate cards with translate transition
        playTranslateIn(cardList);

        return new Scene(root, 1550, 800);
    }

    /** Animate each card sliding up and fading in, staggered for cascade effect */
    private void playTranslateIn(java.util.List<VBox> cards) {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);
            card.setOpacity(0);
            card.setTranslateY(120);

            TranslateTransition tt = new TranslateTransition(Duration.millis(580), card);
            tt.setFromY(120);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fade = new FadeTransition(Duration.millis(580), card);
            fade.setFromValue(0);
            fade.setToValue(1);

            ParallelTransition pt = new ParallelTransition(tt, fade);
            pt.setDelay(Duration.millis(i * 135)); // 135ms stagger between cards
            pt.play();
        }
    }

    private VBox featureCard(String label, String bgColor, String imgPath, String descHtml) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(18, 18, 18, 18));
        card.setPrefWidth(330);
        card.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, " + bgColor + " 95%, #FCFCFC 100%);" +
            "-fx-background-radius: 27;" +
            "-fx-border-radius: 27;" +
            "-fx-border-width: 2;" +
            "-fx-border-color: #fff;" +
            "-fx-effect: dropshadow(gaussian, " + bgColor + "66, 15, 0.17, 0, 7);"
        );

        ImageView icon = new ImageView(new Image(imgPath));
        icon.setFitHeight(58);
        icon.setFitWidth(58);
        Circle clip = new Circle(29, 29, 29);
        icon.setClip(clip);
        icon.setEffect(new DropShadow(12, Color.web("#b5f7e977")));

        Label titleLabel = new Label(label);
        titleLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 17));
        titleLabel.setTextFill(Color.web(TEXT));
        titleLabel.setPadding(new Insets(8, 0, 0, 0));

        Label descLabel = new Label(descHtml.replace("<br>", "\n").replaceAll("<b>(.*?)</b>", "$1"));
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 13));
        descLabel.setTextFill(Color.web("#425564"));
        descLabel.setPadding(new Insets(3, 5, 2, 5));

        Button exploreBtn = new Button("Explore ➜");
        exploreBtn.setFont(Font.font("Poppins", FontWeight.MEDIUM, 14));
        exploreBtn.setStyle(
            "-fx-background-color: #816797;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 6 18 6 18;"
        );
        exploreBtn.setOnAction(e -> {
            if(label.contains("Monthly Vaccination")){
                MonthlyVaccination vaccinationUI = new MonthlyVaccination(stage);
                stage.setScene(vaccinationUI.getScene());
            } else if (label.contains("Age-based Activities")) {
                AgeBasedActivities ageUI = new AgeBasedActivities(stage);
                stage.setScene(ageUI.getScene());
            } else if (label.contains("Legal Document Info")) {
                LegaldocumentInfo legalPage = new LegaldocumentInfo(stage);
                stage.setScene(legalPage.getScene());
            }
        });

        VBox.setMargin(exploreBtn, new Insets(10, 0, 0, 0));
        addHoverEffect(card);
        card.getChildren().addAll(icon, titleLabel, descLabel, exploreBtn);
        return card;
    }

    private void addHoverEffect(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(javafx.util.Duration.millis(200), node);
            st.setToX(1.04);
            st.setToY(1.04);
            st.play();
        });
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(javafx.util.Duration.millis(200), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}