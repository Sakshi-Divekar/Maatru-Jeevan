package com.priti.view;

import com.priti.controller.ChatGPT;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelpPage {

    public Scene getScene(Stage stage) {
        // ---- Color Palette (see provided shades at top for clarity) ----
        // #A53860 (deep berry/primary accent), #FFB7CE, #FFB5C0, #FFF0F5, #FFD6D1

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(40, 0, 40, 0));
        root.getChildren().add(centerPane);

        // Card-like Main VBox
        VBox card = new VBox(24);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(38, 38, 24, 38));
        card.setPrefWidth(610);
        card.setMaxWidth(650);
        card.setStyle(
                "-fx-background-radius: 24px;" +
                "-fx-background-color: linear-gradient(to bottom right, #FFF0F5 60%, #FFB7CE 100%, #A53860 170%);" +
                "-fx-effect: dropshadow(one-pass-box, #A5386077, 22, 0, 0, 8);" +
                "-fx-border-radius: 24px;" +
                "-fx-border-color: #FFB5C099;" +
                "-fx-border-width: 2.2px;"
        );

        // Header with icon
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        Label logo = new Label("👶🏼");
        logo.setStyle("-fx-font-size: 32px; -fx-opacity: 0.93; -fx-effect: dropshadow(one-pass-box, #A53860, 7, 0, 1, 2);");
        Label header = new Label("Maternal & Child Care Assistant");
        header.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
        header.setTextFill(Color.web("#A53860"));
        headerBox.getChildren().addAll(logo, header);

        // Suggested Questions Section
        VBox suggestionsArea = new VBox(9);
        suggestionsArea.setPadding(new Insets(8));
        suggestionsArea.setStyle(
            "-fx-background-radius:10px;" +
            "-fx-background-color: #FFF0F5;" +
            "-fx-border-color: #FFB7CE;" +
            "-fx-border-width:1.2;" +
            "-fx-effect: dropshadow(gaussian, #A5386085, 4, 0, 0, 1);"
        );
        Label suggHeader = new Label("💡 Common Questions");
        suggHeader.setFont(Font.font("Segoe UI Semibold", FontWeight.SEMI_BOLD, 16));
        suggHeader.setTextFill(Color.web("#A53860"));
        suggestionsArea.getChildren().add(suggHeader);

        String[] commonQuestions = {
            "How often should I feed my newborn?",
            "What are the signs of labor?",
            "Safe sleep practices for babies",
            "Nutrition tips during pregnancy"
        };
        for (String question : commonQuestions) {
            Hyperlink link = new Hyperlink(question);
            link.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
            link.setStyle(
                    "-fx-text-fill: #A53860; " +
                    "-fx-underline: false; " +
                    "-fx-background-radius: 6; " +
                    "-fx-padding: 7 14;" +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: transparent;" +
                    "-fx-transition: all 0.16s cubic-bezier(.35,1.4,.41,1.01);"
            );
            link.setOnMouseEntered(e -> link.setStyle(
                    "-fx-text-fill: #fff; " +
                    "-fx-background-color: #A53860; " +
                    "-fx-background-radius: 6;"
            ));
            link.setOnMouseExited(e -> link.setStyle(
                    "-fx-text-fill: #A53860; -fx-background-color: transparent; -fx-background-radius: 6;"
            ));
            link.setOnAction(e -> handleQuestion(question));
            suggestionsArea.getChildren().add(link);
        }

        // Chat Area in card
        VBox qaBox = new VBox(11);

        TextArea chatArea = new TextArea();
        chatArea.setPromptText("Type your question here...");
        chatArea.setFont(Font.font("Segoe UI", 16));
        chatArea.setPrefRowCount(2);
        chatArea.setStyle(
                "-fx-background-radius: 8; " +
                "-fx-background-color: #FFF0F5; " +
                "-fx-border-radius: 8;" +
                "-fx-border-color: #FFB7CE; " +
                "-fx-prompt-text-fill: #A5386080;"
        );
        chatArea.setWrapText(true);

        HBox askBox = new HBox(13);
        askBox.setAlignment(Pos.CENTER_RIGHT);

        Button askButton = new Button("✉️   Ask Expert");
        askButton.setFont(Font.font("Segoe UI Semibold", 14));
        askButton.setStyle(
            "-fx-background-radius: 6;"
            + "-fx-background-color: linear-gradient(to right,#A53860 80%, #FFB5C0);"
            + "-fx-text-fill: white;"
            + "-fx-padding: 8 22 8 22;"
            + "-fx-font-size: 14px;"
            + "-fx-cursor: hand;"
        );
        askBox.getChildren().add(askButton);

        // Response Area ("glassy" effect)
TextArea responseArea = new TextArea();
responseArea.setEditable(false);
responseArea.setFont(Font.font("Segoe UI", 15));
responseArea.setWrapText(true);
responseArea.setPrefRowCount(12);      // Shows about 12 lines
responseArea.setMinHeight(120);        // Give a reasonable min
responseArea.setMaxHeight(Double.MAX_VALUE); // Allow as much as needed

responseArea.setText("Your answer will appear here...");
responseArea.setStyle(
    "-fx-background-color: rgba(255,247,250,0.96);"
    + "-fx-background-radius: 10;"
    + "-fx-border-radius: 10;"
    + "-fx-border-color: #FFD6D1;"
    + "-fx-border-width: 1.5;"
    + "-fx-prompt-text-fill: #A5386044;"
    + "-fx-effect: dropshadow(one-pass-box, #A5386088, 2, 0.18, 1, 1);"
);
VBox.setVgrow(responseArea, Priority.ALWAYS); // <--- Ensures it can expand or shrink as needed


        qaBox.getChildren().addAll(chatArea, askBox, responseArea);

        askButton.setOnAction(e -> runQuestion(chatArea, responseArea));
        chatArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                askButton.fire();
            }
        });

        // Disclaimer
        Label disclaimer = new Label(
            "❗ Note: This assistant provides general information only. Always consult with your healthcare provider for medical advice."
        );
        disclaimer.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        disclaimer.setWrapText(true);
        disclaimer.setTextFill(Color.web("#A53860"));
        disclaimer.setPadding(new Insets(11, 4, 0, 4));
        disclaimer.setStyle("-fx-effect: dropshadow(gaussian,#FFF0F5,2,0,0,0)");

        // Footer with back nav
        HBox footerBox = new HBox();
        footerBox.setAlignment(Pos.CENTER_LEFT);
        footerBox.setPadding(new Insets(8, 0, 0, 0));

        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Segoe UI", 13));
        backButton.setStyle(
            "-fx-background-radius: 6;" +
            "-fx-background-color: #FFB7CE;" +
            "-fx-text-fill: #A53860;" +
            "-fx-padding: 7 19;" +
            "-fx-border-color: #FFD6D1;" +
            "-fx-border-radius: 6;" +
            "-fx-border-width: 1.3;" +
            "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> stage.setScene(new PreHomePage().getScene(stage)));
        footerBox.getChildren().add(backButton);

        // Add all themed content into the card
        card.getChildren().addAll(
            headerBox,
            new Separator(),
            suggestionsArea,
            new Separator(),
            qaBox,
            disclaimer,
            footerBox
        );
        centerPane.getChildren().add(card);

        // Main Scene with soft baby pink background
        Scene scene = new Scene(root, 1550, 800);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFF0F5 80%, #FFD6D1 130%);");

        // Fade in animation for the card
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), card);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        return scene;
    }

    private void handleQuestion(String question) {
        // Just as before: replace with autofill and send if you wish
        System.out.println("Clicked: " + question);
    }

    private void runQuestion(TextArea chatArea, TextArea responseArea) {
        String question = chatArea.getText().trim();
        if (!question.isEmpty()) {
            responseArea.setText("🔎 Searching for the best answer...");
            responseArea.setScrollTop(Double.MIN_VALUE);
            new Thread(() -> {
                String response = ChatGPT.getAIResponse(question);
                Platform.runLater(() -> {
                    responseArea.setText(response);
                    responseArea.setScrollTop(Double.MIN_VALUE);
                });
            }).start();
        }
    }

    public Scene createScene(Stage stage) {
        throw new UnsupportedOperationException("Unimplemented method 'createScene'");
    }
}
