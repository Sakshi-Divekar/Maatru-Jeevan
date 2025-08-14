package com.priti.view;

import com.priti.controller.GeminiVitalsInsightController;
import com.priti.utils.UserSession;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class VitalInner {
    private final Stage stage;

    public VitalInner(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fff4fa, #ffeef8);");

        // Top Bar with Back Button
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        backButton.setStyle("-fx-background-color: #FF8FAB; -fx-text-fill: white; -fx-background-radius: 14; -fx-padding: 8 15;");
        backButton.setOnAction(e -> {
            PregnancyMonitoringPage previousPage = new PregnancyMonitoringPage(stage);
            stage.setScene(previousPage.getScene());
        });

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(20, 0, 0, 20));

        // Title
        Label title = new Label("Mother's Vitals Monitoring");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 36));
        title.setTextFill(Color.web("#A53860"));
        title.setPadding(new Insets(0, 0, 20, 0));

        VBox headerBox = new VBox(10, topBar, title);
        headerBox.setAlignment(Pos.TOP_CENTER);
        headerBox.setPadding(new Insets(0, 20, 20, 20));

        // Main Content Area - Split View
        HBox contentBox = new HBox(20);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(0, 30, 30, 30));

        // Lab Report Card
        VBox labCard = createInsightCard(
            "🧪 Lab Report Insights", 
            Color.DARKBLUE, 
            "#E6F7FF", 
            "-fx-border-color: #5D9BEC;"
        );
        TextArea labArea = (TextArea) labCard.getChildren().get(1);

        // Sonography Report Card
        VBox sonoCard = createInsightCard(
            "🩺 Sonography Report Insights", 
            Color.DARKGREEN, 
            "#F0FFF0", 
            "-fx-border-color: #6BBF6B;"
        );
        TextArea sonoArea = (TextArea) sonoCard.getChildren().get(1);

        // Make cards equal width
        HBox.setHgrow(labCard, Priority.ALWAYS);
        HBox.setHgrow(sonoCard, Priority.ALWAYS);
        labCard.setMaxWidth(Double.MAX_VALUE);
        sonoCard.setMaxWidth(Double.MAX_VALUE);

        contentBox.getChildren().addAll(labCard, sonoCard);

        root.setTop(headerBox);
        root.setCenter(contentBox);

        // Fetch data from Firestore and generate insights
        fetchInsights(labArea, sonoArea);

        return new Scene(root, 1550, 800);
    }

    private VBox createInsightCard(String titleText, Color titleColor, String bgColor, String borderStyle) {
        Label title = new Label(titleText);
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 24));
        title.setTextFill(titleColor);
        title.setPadding(new Insets(0, 0, 10, 0));

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(500);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-background-color: " + bgColor + "; " + borderStyle + 
                         " -fx-border-radius: 12; -fx-background-radius: 12; " +
                         "-fx-border-width: 2; -fx-padding: 15;");
        textArea.setFont(Font.font("Arial", 16));
        VBox.setVgrow(textArea, Priority.ALWAYS);

        VBox card = new VBox(15, title, textArea);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                     "-fx-padding: 15;");
        card.setMaxHeight(Double.MAX_VALUE);

        return card;
    }

    private void fetchInsights(TextArea labArea, TextArea sonoArea) {
        new Thread(() -> {
            try {
                Firestore db = FirestoreClient.getFirestore();
                String email = UserSession.getUserEmail();
                String stage = "preDeliveryData"; // adjust if dynamic

                DocumentSnapshot doc = db.collection("users")
                                         .document(email)
                                         .collection(stage)
                                         .document("details")
                                         .get()
                                         .get(); // blocking

                String lab = doc.getString("responseOfLabReport");
                String sonography = doc.getString("responseOfSonographyReport");

                String labInsight = (lab != null && !lab.isEmpty())
                        ? GeminiVitalsInsightController.getInsightsFromLab(lab)
                        : "No Lab Report available.";

                String sonoInsight = (sonography != null && !sonography.isEmpty())
                        ? GeminiVitalsInsightController.getInsightsFromSonography(sonography)
                        : "No Sonography Report available.";

                Platform.runLater(() -> {
                    labArea.setText(labInsight);
                    sonoArea.setText(sonoInsight);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    labArea.setText("Error loading Lab Report: " + e.getMessage());
                    sonoArea.setText("Error loading Sonography Report: " + e.getMessage());
                });
            }
        }).start();
    }
}