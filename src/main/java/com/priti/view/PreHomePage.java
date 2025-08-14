package com.priti.view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import com.priti.utils.UserSession;

public class PreHomePage {
    

private static String userEmail;

public static void setUserEmail(String email) {
    userEmail = email;
}


   

    public Scene getScene(Stage stage) {
        VBox root = new VBox();
        root.setSpacing(0);

        // Navbar
        HBox navbar = createNavbar(stage); // Pass stage here
        navbar.setStyle("-fx-background-color: #A53860; -fx-padding: 12;");
        root.getChildren().add(navbar);

        // Main Content
// Back Button Container (just below navbar)
HBox backBtnBox = new HBox();
backBtnBox.setAlignment(Pos.CENTER_LEFT);
backBtnBox.setPadding(new Insets(10, 30, 0, 30)); // spacing below navbar
Button backButton = createBackButton(stage);
backBtnBox.getChildren().add(backButton);
root.getChildren().add(backBtnBox); // ⬅ Add it right after navbar

// Main Content
HBox blocks = createFeatureBlocks(stage);
VBox.setVgrow(blocks, Priority.ALWAYS);
root.getChildren().add(blocks);
 // Add it last

        return new Scene(root, 1550, 800);
    }

    private Button createBackButton(Stage stage) {
        Button backButton = new Button("← Back to Home");
        backButton.setFont(Font.font("Poppins", FontWeight.MEDIUM, 14));
        backButton.setStyle(
            "-fx-background-color: #f8d7da;" +
            "-fx-text-fill: #721c24;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 8 18;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: #f5c6cb;" +
            "-fx-border-radius: 20;"
        );
        backButton.setOnAction(e -> {
            try {
                new LandingPage().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return backButton;
    }

    // ✅ Updated to accept Stage
    private HBox createNavbar(Stage stage) {
        HBox nav = new HBox(30);
        nav.setAlignment(Pos.CENTER_LEFT);

        ImageView logoImage = new ImageView(new Image("/assets/images/final_logo.jpg"));
        logoImage.setFitHeight(35);
        logoImage.setPreserveRatio(true);

        Label title = new Label("Maatru-Jeevan");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);

        HBox logoBox = new HBox(10, logoImage, title);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnCall = new Button("📞 Emergency Call");
btnCall.setOnAction(e -> {
    try {
        String currentEmail = UserSession.getUserEmail(); // ✅
        EmergencyPage emergencyPage = new EmergencyPage(stage, currentEmail);
        System.out.println("Email in PreHomePage: " + currentEmail);
        stage.setScene(emergencyPage.getScene());
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});

// When emergency button is clicked:
// EmergencyPage emergencyPage = new EmergencyPage(stage, userEmail);
// stage.setScene(emergencyPage.getScene());
stage.show();



        Button btnAppoint = new Button("🗓️ Appointment");
        btnAppoint.setOnAction(e -> stage.setScene(new AppointmentPage().getScene(stage)));

        Button btnChatBot = new Button("💭 Help");
        btnChatBot.setOnAction(e -> stage.setScene(new HelpPage().getScene(stage)));

        Button btnNotify = new Button("🔔 Reminder");
        btnNotify.setOnAction(e -> stage.setScene(new NotificationPage().getScene(stage)));
        

Button profileButton = new Button("Go to Profile");
profileButton.setOnAction(e -> {
    String userEmail = UserSession.getInstance().getUserEmail(); // ✅ correct getter
    System.out.println("Navigating to PreProfilePage for " + userEmail);

    PreProfilePage preProfilePage = new PreProfilePage();
    Scene profileScene = preProfilePage.getScene(stage);
    stage.setScene(profileScene);
});


        for (Button b : new Button[]{btnCall, btnAppoint, btnChatBot, btnNotify, profileButton}) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
        }

        nav.getChildren().addAll(logoBox, spacer, btnCall, btnAppoint, btnChatBot, btnNotify, profileButton);
        return nav;
    }

    private HBox createFeatureBlocks(Stage stage) {
        HBox box = new HBox(45);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60, 40, 60, 40));
        box.setStyle("-fx-background-color: linear-gradient(to bottom right, #fff0f5, #fceeee);");

        box.getChildren().addAll(
                createBlock(stage,
                        "Pregnancy Monitoring",
                        "Track trimester progress, baby movement, symptoms, weight, and schedule timely doctor visits with insights.",
                        "/assets/images/i12.jpg",
                        "#ffe0e9", "#ff9a9e"
                ),
                createBlock(stage,
                        "Mother's Health & Wellness",
                        "Personalized routines with yoga, nutrition plans, hydration reminders, and sleep cycle tracking.",
                        "/assets/images/i11.jpg",
                        "#e0ffe4", "#00b894"
                ),
                createBlock(stage,
                        "Traditional Tips & Remedies",
                        "Explore grandmother's wisdom including herbal tonics, oil massages, and natural ways to ease discomfort.",
                        "/assets/images/i13.jpg",
                        "#e0f7ff", "#00cec9"
                )
        );

        return box;
    }

    private VBox createBlock(Stage stage, String title, String desc, String imagePath, String bgColor, String borderColor) {
        VBox card = new VBox(18);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(28));
        card.setPrefWidth(330);
        card.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-background-radius: 30;" +
                "-fx-border-radius: 30;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.3, 0, 6);"
        );

        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitHeight(140);
        icon.setFitWidth(140);
        Circle clip = new Circle(70, 70, 70);
        icon.setClip(clip);
        icon.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.2)));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 19));
        titleLabel.setTextFill(Color.web("#A53860"));
        titleLabel.setPadding(new Insets(10, 0, 0, 0));

        Label descLabel = new Label(desc);
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        descLabel.setTextFill(Color.web("#555"));
        descLabel.setStyle("-fx-padding:40");

        Button exploreBtn = new Button("Explore ➜");
        exploreBtn.setOnAction(e -> {
            switch (title) {
                case "Pregnancy Monitoring":
                    PregnancyMonitoringPage monitorPage = new PregnancyMonitoringPage(stage);
stage.setScene(monitorPage.getScene());

                    break;
                case "Mother's Health & Wellness":
                    stage.setScene(new HealthAndWellnessPage(stage).getScene());
                    break;
                case "Traditional Tips & Remedies":
                    stage.setScene(new TraditionaltipsAndRemediesPage(stage).getScene());
                    break;
            }
        });

        exploreBtn.setStyle(
                "-fx-background-color: " + borderColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 100;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20;"
        );

        addHoverEffect(card);
        card.getChildren().addAll(icon, titleLabel, descLabel, exploreBtn);
        return card;
    }

    private void addHoverEffect(javafx.scene.Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }




    


}

