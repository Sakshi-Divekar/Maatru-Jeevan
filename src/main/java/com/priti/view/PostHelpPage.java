package com.priti.view;

import com.priti.controller.ChatGPT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PostHelpPage {

    public Scene getScene(Stage stage) {
        // Main container with soft gradient background (mother/baby theme)
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fce4ec, #f8bbd0, #f48fb1);");

        // Content container with white background and rounded corners
        VBox contentBox = new VBox(15);
        contentBox.setMaxWidth(800);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(25));
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Header with baby-themed colors
        Label title = new Label("Mom & Baby Helper");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#e91e63"));
        
        // Horizontal line separator with baby color
        Separator separator = new Separator();
        separator.setPrefWidth(650);
        separator.setStyle("-fx-background-color: #ffcdd2;");

        // Question area with kid-friendly styling
        Label questionLabel = new Label("Your Question:");
        questionLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 16));
        questionLabel.setTextFill(Color.web("#880e4f"));
        
        TextArea questionArea = new TextArea();
        questionArea.setPromptText("Ask anything about baby care or parenting...");
        questionArea.setWrapText(true);
        questionArea.setPrefHeight(120);
        questionArea.setFont(Font.font("Comic Sans MS", 14));
        questionArea.setStyle("-fx-background-color: #fff9f9; -fx-border-color: #f8bbd0; " +
                             "-fx-border-radius: 8; -fx-border-width: 2;");

        // Response area with playful styling
        Label responseLabel = new Label("Helper's Response:");
        responseLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 16));
        responseLabel.setTextFill(Color.web("#880e4f"));
        
        TextArea answerArea = new TextArea();
        answerArea.setWrapText(true);
        answerArea.setEditable(false);
        answerArea.setPrefHeight(300);
        answerArea.setFont(Font.font("Comic Sans MS", 14));
        answerArea.setStyle("-fx-background-color: #fff9f9; -fx-border-color: #f8bbd0; " +
                           "-fx-border-radius: 8; -fx-border-width: 2;");
        answerArea.setText("Your helpful response will appear here...");

        // Button container with playful buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button sendButton = new Button("Ask Helper");
        sendButton.setStyle("-fx-background-color: #e91e63; -fx-text-fill: white; " +
                            "-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; " +
                            "-fx-font-size: 14; -fx-min-width: 120; -fx-padding: 10; " +
                            "-fx-background-radius: 8;");
        sendButton.setOnMouseEntered(e -> sendButton.setStyle("-fx-background-color: #c2185b; " +
                                                            "-fx-text-fill: white; " +
                                                            "-fx-font-family: 'Comic Sans MS'; " +
                                                            "-fx-font-weight: bold; " +
                                                            "-fx-font-size: 14; " +
                                                            "-fx-min-width: 120; " +
                                                            "-fx-padding: 10; " +
                                                            "-fx-background-radius: 8;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle("-fx-background-color: #e91e63; " +
                                                           "-fx-text-fill: white; " +
                                                           "-fx-font-family: 'Comic Sans MS'; " +
                                                           "-fx-font-weight: bold; " +
                                                           "-fx-font-size: 14; " +
                                                           "-fx-min-width: 120; " +
                                                           "-fx-padding: 10; " +
                                                           "-fx-background-radius: 8;"));
        
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(20, 20);
        progressIndicator.setStyle("-fx-progress-color: #e91e63;");
        
        Button backButton = new Button("Go Back");
        backButton.setStyle("-fx-background-color: #f8bbd0; -fx-text-fill: #880e4f; " +
                           "-fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold; " +
                           "-fx-font-size: 14; -fx-border-color: #e91e63; " +
                           "-fx-border-radius: 8; -fx-border-width: 2; " +
                           "-fx-min-width: 120; -fx-padding: 10;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #fce4ec; " +
                                                            "-fx-text-fill: #880e4f; " +
                                                            "-fx-font-family: 'Comic Sans MS'; " +
                                                            "-fx-font-weight: bold; " +
                                                            "-fx-font-size: 14; " +
                                                            "-fx-border-color: #e91e63; " +
                                                            "-fx-border-radius: 8; " +
                                                            "-fx-border-width: 2; " +
                                                            "-fx-min-width: 120; " +
                                                            "-fx-padding: 10;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #f8bbd0; " +
                                                           "-fx-text-fill: #880e4f; " +
                                                           "-fx-font-family: 'Comic Sans MS'; " +
                                                           "-fx-font-weight: bold; " +
                                                           "-fx-font-size: 14; " +
                                                           "-fx-border-color: #e91e63; " +
                                                           "-fx-border-radius: 8; " +
                                                           "-fx-border-width: 2; " +
                                                           "-fx-min-width: 120; " +
                                                           "-fx-padding: 10;"));
        
        buttonBox.getChildren().addAll(backButton, sendButton, progressIndicator);

        // Add components to content box
        contentBox.getChildren().addAll(
            title,
            separator,
            questionLabel,
            questionArea,
            responseLabel,
            answerArea,
            buttonBox
        );
        
        root.getChildren().add(contentBox);

        // Event handlers (functionality remains the same)
        sendButton.setOnAction(e -> {
            String question = questionArea.getText().trim();
            if (question.isEmpty()) {
                answerArea.setStyle("-fx-text-fill: #e91e63; -fx-border-color: #f8bbd0; -fx-border-width: 2;");
                answerArea.setText("Please enter a question first.");
                return;
            }

            sendButton.setDisable(true);
            progressIndicator.setVisible(true);
            answerArea.setStyle("-fx-text-fill: #5a6a7a; -fx-border-color: #f8bbd0; -fx-border-width: 2;");
            answerArea.setText("Thinking about your question...");

            new Thread(() -> {
                try {
                    String aiResponse = ChatGPT.getAIResponse(question);
                    javafx.application.Platform.runLater(() -> {
                        answerArea.setStyle("-fx-text-fill: #2c3e50; -fx-border-color: #f8bbd0; -fx-border-width: 2;");
                        answerArea.setText(aiResponse);
                        progressIndicator.setVisible(false);
                        sendButton.setDisable(false);
                    });
                } catch (Exception ex) {
                    javafx.application.Platform.runLater(() -> {
                        answerArea.setStyle("-fx-text-fill: #e91e63; -fx-border-color: #f8bbd0; -fx-border-width: 2;");
                        answerArea.setText("Oops! Something went wrong. Please try again.");
                        progressIndicator.setVisible(false);
                        sendButton.setDisable(false);
                    });
                }
            }).start();
        });

        backButton.setOnAction(e -> stage.setScene(new PostHomePage().getScene(stage)));

        return new Scene(root, 1550, 800);
    }
}