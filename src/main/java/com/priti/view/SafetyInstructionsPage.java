package com.priti.view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class SafetyInstructionsPage {

    private final Stage stage;

    public SafetyInstructionsPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();

        // Fun Animated Gradient Background
        root.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #fdeefc 0%, #c6e6fb 100%);" +
            "-fx-background-radius: 40px;"
        );

        // ==== Header section with cute back button ===
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setPadding(new Insets(28, 28, 0, 28));
        headerBox.setSpacing(16);

        // Icon Circle for Back Button (optional)
        Circle backCircle = new Circle(22, Color.web("#ff90b3")); // pink bubble
        DropShadow circleShadow = new DropShadow(6, Color.web("#bd5c80aa"));
        backCircle.setEffect(circleShadow);

        // or you may use a back arrow image/icon here
        Text backArrow = new Text("←");
        backArrow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 21));
        backArrow.setFill(Color.WHITE);

        StackPane iconStack = new StackPane(backCircle, backArrow);

        Button backBtn = new Button("Back", iconStack);
        backBtn.setContentDisplay(ContentDisplay.LEFT);
        backBtn.setPrefHeight(42);
        backBtn.setPrefWidth(115);
        backBtn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 16));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setStyle(
            "-fx-background-radius: 25; " +
            "-fx-background-color: linear-gradient(to right, #9bafd9, #f6c1d5);" +
            "-fx-cursor: hand;"
        );
        backBtn.setEffect(new DropShadow(6, Color.web("#a3b8d866")));
        backBtn.setOnAction(e -> {
            HealthAndWellnessPage wellnessPage = new HealthAndWellnessPage(stage);
            stage.setScene(wellnessPage.getScene());
        });

        headerBox.getChildren().add(backBtn);

        // ---- Top-Center Image (kid-friendly icon) -----
        // You can use a more playful image, even a cartoon icon!
        ImageView topImage = new ImageView(new Image("/assets/images/safety_tips.jpg"));
        topImage.setFitWidth(190);
        topImage.setPreserveRatio(true);

        VBox imageBox = new VBox(topImage);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(10, 0, 16, 0));

        // ---- Stylized Title ----
        Text title = new Text("Safety Instructions for Pregnancy Yoga");
        title.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 30));
        title.setFill(Color.web("#5a3db7")); // fun purple-blue
        title.setStyle("-fx-effect: dropshadow( gaussian , #fbb8ed55 , 2 ,0,1,1);");

        VBox titleBox = new VBox(7, imageBox, title);
        titleBox.setAlignment(Pos.CENTER);

        // ====== Instruction Blocks (Bubble Cards) ==========
        VBox instructionBlocks = new VBox(28);
        instructionBlocks.setAlignment(Pos.TOP_CENTER);
        instructionBlocks.setPadding(new Insets(38, 20, 40, 20));

        instructionBlocks.getChildren().addAll(
            createInstructionBlock("1. Avoid Overstretching", "Pregnancy hormones loosen joints, so avoid deep stretches or forcing your body beyond comfort."),
            createInstructionBlock("2. No Lying on Back After First Trimester", "Avoid poses lying flat after first trimester to prevent restricting blood flow."),
            createInstructionBlock("3. Focus on Balance", "With a shifted center of gravity, avoid poses that risk imbalance and falling."),
            createInstructionBlock("4. Avoid Hot Yoga", "Excessive heat can harm the baby; keep your environment cool and comfortable."),
            createInstructionBlock("5. Use Support", "Use walls, chairs, or blocks to maintain balance and stability in poses."),
            createInstructionBlock("6. Listen to Your Body", "If something feels uncomfortable or causes strain, stop immediately."),
            createInstructionBlock("7. Consult Doctor First", "Always get your healthcare provider's approval before starting or continuing yoga during pregnancy.")
        );

        ScrollPane scrollPane = new ScrollPane(instructionBlocks);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(0));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background: transparent; " +
            "-fx-border-color: transparent;"
        );

        VBox mainContent = new VBox(18, titleBox, scrollPane);
        mainContent.setAlignment(Pos.TOP_CENTER);

        root.setTop(headerBox);
        root.setCenter(mainContent);

        return new Scene(root, 1550, 800); // Adjusted for cuter boxed look
    }

    private VBox createInstructionBlock(String heading, String description) {
        VBox block = new VBox(10);
        block.setAlignment(Pos.TOP_LEFT);
        block.setPadding(new Insets(26, 28, 26, 36));
        block.setPrefWidth(880);

        // Pink and blue bubbles
        String bubbleGradient = "-fx-background-color: linear-gradient(to bottom right, #f1b3e0cc 55%, #abe2fff6 120%);";
        block.setStyle(
            bubbleGradient +
            " -fx-background-radius: 30; " +
            " -fx-border-radius: 30; " +
            " -fx-border-width: 2px; " +
            " -fx-border-color: #ffffff88;"
        );
        DropShadow shadow = new DropShadow(13, Color.web("#a5cfff55"));
        block.setEffect(shadow);

        Text headingText = new Text(heading);
        headingText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 21));
        headingText.setFill(Color.web("#4d008d")); // deep purple-blue

        Text descriptionText = new Text(description);
        descriptionText.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
        descriptionText.setWrappingWidth(780);
        descriptionText.setFill(Color.web("#313648"));

        block.getChildren().addAll(headingText, descriptionText);

        return block;
    }
}