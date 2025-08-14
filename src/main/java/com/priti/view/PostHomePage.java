package com.priti.view;

import com.priti.utils.UserSession;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class PostHomePage extends Application {

    // Updated kid-friendly color palette
    private static final String CARD1_GRADIENT = "linear-gradient(to bottom right, #FFD1DC, #FFB6C1)";
    private static final String CARD1_BORDER = "#FF85A2";

    private static final String CARD2_GRADIENT = "linear-gradient(to bottom right, #B5EAD7, #C7CEEA)";
    private static final String CARD2_BORDER = "#84DCC6";

    private static final String CARD3_GRADIENT = "linear-gradient(to bottom right, #E2F0CB, #FFDAC1)";
    private static final String CARD3_BORDER = "#FFB7B2";

    private static final String SOFT_PINK = "#FFD1DC";
    private static final String SOFT_BLUE = "#B5EAD7";
    private static final String PALE_WHITE = "#FFF1F9";
    private static final String MINTY_GREEN = "#E0FFF9";
    private static final String TEXT = "#4B3869";
    private static final String ACCENT = "#FF6B6B";

    @Override
    public void start(Stage stage) {
        Scene scene = getScene(stage);
        stage.setScene(scene);
        stage.setTitle("Postnatal Home");
        stage.show();
    }

    public Scene getScene(Stage stage) {
        System.out.println("PostHomePage received stage: " + stage);
        VBox root = new VBox();
        root.setSpacing(0);

        HBox navbar = createNavbar(stage);
        navbar.setStyle(
            "-fx-background-color: linear-gradient(to right, #FFD1DC 0%, #B5EAD7 50%, #C7CEEA 100%);" +
            "-fx-padding: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.5, 0, 2);"
        );
        root.getChildren().add(navbar);

        Button backBtn = new Button("⬅ Back");
        backBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 15));
        backBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #FF9AA2, #FFB7B2);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 24;" +
            "-fx-padding: 10 24;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.5, 0, 2);"
        );

        backBtn.setOnAction(e -> new LandingPage(stage).start(stage));
        HBox backBox = new HBox(backBtn);
        backBox.setPadding(new Insets(15, 30, 0, 30));
        backBox.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().add(backBox);

        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFF1F9, #E2F0CB);");

        HBox blocks = createFeatureBlocks(stage);
        VBox.setVgrow(blocks, Priority.ALWAYS);
        root.getChildren().add(blocks);

        return new Scene(root, 1550, 800);
    }

    private HBox createNavbar(Stage stage) {
        HBox nav = new HBox(30);
        nav.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImage = new ImageView(new Image("/assets/images/final_logo.jpg"));
        logoImage.setFitHeight(45);
        logoImage.setPreserveRatio(true);

        Label title = new Label("Maatru-Jeevan");
        title.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 24));
        title.setTextFill(Color.web(TEXT));

        HBox logoBox = new HBox(12, logoImage, title);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnCall = new Button("📞 Emergency");
        Button btnAppoint = new Button("🗓️ Appointment");
        Button btnChatBot = new Button("💭 Help");
        Button btnNotify = new Button("🔔 Reminder");
        Button btnProfile = new Button("👤 Profile");
        btnProfile.setOnAction(e -> {
            String userEmail = UserSession.getInstance().getUserEmail();
            System.out.println("Navigating to PreProfilePage for " + userEmail);
            PostProfilePage postProfilePage = new PostProfilePage();
            Scene profileScene = postProfilePage.getScene(stage);
            stage.setScene(profileScene);
        });

        for (Button b : new Button[]{btnCall, btnAppoint, btnChatBot, btnNotify, btnProfile}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT + "; -fx-font-size: 16px; -fx-font-weight: bold;");
            b.setPadding(new Insets(5, 10, 5, 10));
        }

        btnCall.setOnAction(e -> {
            try {
                String currentEmail = UserSession.getUserEmail();
                PostEmergencyPage emergencyPage = new PostEmergencyPage(stage, currentEmail);
                stage.setScene(emergencyPage.getScene());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnAppoint.setOnAction(e -> stage.setScene(new PostAppointmentPage().getScene(stage)));
        btnChatBot.setOnAction(e -> stage.setScene(new PostHelpPage().getScene(stage)));
        btnNotify.setOnAction(e -> stage.setScene(new PostNotificationPage().getScene(stage)));

        nav.getChildren().addAll(logoBox, spacer, btnCall, btnAppoint, btnChatBot, btnNotify, btnProfile);
        return nav;
    }

    private HBox createFeatureBlocks(Stage stage) {
        HBox box = new HBox(50);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40, 40, 60, 40));
        box.setBackground(Background.EMPTY);

        box.getChildren().addAll(
            createBlock(stage, "👶 Baby Care",
                "Track baby's growth, vaccines, sleep & feeding. Get advice for colic, rashes, hygiene.",
                "/assets/images/post_login.jpg",
                CARD1_GRADIENT, CARD1_BORDER),
            createBlock(stage, "📈 Growing Up",
                "Log your postpartum healing: wounds, pain, mood & vitals. Guided checklists for safe recovery.",
                "/assets/images/recovery_tracker.jpg",
                CARD2_GRADIENT, CARD2_BORDER),
            createBlock(stage, "🌿 Traditional Tips &\nRemedies",
                "Learn Ayurveda foods, warm oil massage, belly wraps, and home cures for baby and mom.",
                "/assets/images/post_hometradi.jpg",
                CARD3_GRADIENT, CARD3_BORDER)
        );

        return box;
    }

    private VBox createBlock(Stage stage, String title, String desc, String imagePath, String bgGradient, String borderColor) {
        VBox card = new VBox(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(30, 30, 40, 30));
        card.setPrefWidth(360);
        card.setPrefHeight(550);
        card.setStyle(
            "-fx-background-color: " + bgGradient + ";" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-border-width: 3;" +
            "-fx-border-color: " + borderColor + ";" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 25, 0.2, 0, 10);"
        );

        // Larger image with bigger circle
        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitHeight(150);
        icon.setFitWidth(150);
        Circle clip = new Circle(75, 75, 75);
        icon.setClip(clip);
        icon.setEffect(new DropShadow(15, Color.web("#ffb5d155")));

        // Title with wrapping and bold font
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 22));
        titleLabel.setTextFill(Color.web(TEXT));
        titleLabel.setPadding(new Insets(10, 0, 0, 0));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(280);

        // Description with larger font
        Label descLabel = new Label(desc);
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 15));
        descLabel.setTextFill(Color.web(TEXT));
        descLabel.setPadding(new Insets(20, 10, 30, 10));
        descLabel.setMaxWidth(300);

        Button exploreBtn = new Button("Explore ➜");
        exploreBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #FF9AA2, #FFB7B2);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 100;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 30;" +
            "-fx-font-size: 16px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.5, 0, 2);"
        );

        exploreBtn.setOnAction(e -> {
            switch (title.split("\n")[0]) { // Handle wrapped title
                case "👶 Baby Care":
                    stage.setScene(new BabyCarePage(stage).getScene());
                    break;
                case "📈 Growing Up":
                    stage.setScene(new RecoveryTrackerPage(stage).getScene());
                    break;
                case "🌿 Traditional Tips &":
                    stage.setScene(new TradRemediesPage(stage).getScene());
                    break;
            }
        });

        // Consistent spacing between elements
        VBox.setMargin(icon, new Insets(0, 0, 10, 0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 10, 0));
        VBox.setMargin(descLabel, new Insets(0, 0, 20, 0));
        VBox.setMargin(exploreBtn, new Insets(20, 0, 0, 0));

        addHoverEffect(card);
        card.getChildren().addAll(icon, titleLabel, descLabel, exploreBtn);
        return card;
    }

    private void addHoverEffect(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(javafx.util.Duration.millis(210), node);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(javafx.util.Duration.millis(210), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}