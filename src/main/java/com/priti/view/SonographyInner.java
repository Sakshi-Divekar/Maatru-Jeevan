package com.priti.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.AuthController;
import com.priti.controller.GeminiInsightController;
import com.priti.utils.UserSession;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SonographyInner {

    private final Stage stage;
    String userEmail = UserSession.getUserEmail();

    private File selectedImageFile;
    private TextArea insightArea;
    private ProgressIndicator loader;

    private final List<ReportData> uploadedReports = new ArrayList<>();
    private FlowPane reportCards;
    private ImageView reportPreview;

    public SonographyInner(Stage stage) {
        this.stage = stage;
    }

    private void fetchAIInsights() {
        if (selectedImageFile == null) {
            insightArea.setText("Please upload an image first.");
            return;
        }

        loader.setVisible(true);
        insightArea.clear();

        new Thread(() -> {
            try {
                String insights = GeminiInsightController.getInsights(selectedImageFile);
                Firestore db = FirestoreClient.getFirestore();
Map<String, Object> update = new HashMap<>();
update.put("responseOfSonographyReport", insights);

db.collection("users")
  .document(userEmail)
  .collection("preDeliveryData")
  .document("details")
  .set(update, SetOptions.merge());

                javafx.application.Platform.runLater(() -> {
                    loader.setVisible(false);
                    insightArea.setText("AI Insights:\n" + insights);
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    loader.setVisible(false);
                    insightArea.setText("Error fetching insights: " + e.getMessage());
                });
            }
        }).start();
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffe4ec, #fff9f9);");

        VBox leftPanel = new VBox(30);
        leftPanel.setPadding(new Insets(30, 20, 40, 40));
        leftPanel.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        backButton.setStyle("-fx-background-color: #FF8FAB; -fx-text-fill: white; -fx-background-radius: 14;");
        backButton.setOnAction(e -> {
            PregnancyMonitoringPage previousPage = new PregnancyMonitoringPage(stage);
            stage.setScene(previousPage.getScene());
        });

        VBox uploadSection = buildUploadSection();
        leftPanel.getChildren().addAll(backButton, uploadSection);
        root.setLeft(leftPanel);

        VBox storageSection = buildStorageBlock();
        root.setCenter(storageSection);
        fetchSonographyImagesFromFirebase();

        return new Scene(root, 1550, 800);
    }

    private VBox buildUploadSection() {
        VBox uploadBox = new VBox(30);
        uploadBox.setPadding(new Insets(30, 40, 40, 20));
        uploadBox.setAlignment(Pos.TOP_CENTER);
        uploadBox.setStyle("-fx-background-color: #FFD6E0; -fx-background-radius: 30 0 0 30;");

        Label title = new Label("Upload Sonography Report");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#A53860"));

        Button uploadButton = new Button("📤 Upload Report");
        uploadButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        uploadButton.setPrefWidth(220);
        uploadButton.setStyle("-fx-background-color: #A53860; -fx-text-fill: white; -fx-background-radius: 20;");
        uploadButton.setEffect(new DropShadow(10, Color.GRAY));

        Button aiButton = new Button("Find AI Insights");
        aiButton.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        aiButton.setPrefWidth(220);
        aiButton.setStyle("-fx-background-color: #FF8FAB; -fx-text-fill: white; -fx-background-radius: 20;");
        aiButton.setOnAction(e -> fetchAIInsights());

        loader = new ProgressIndicator();
        loader.setVisible(false);

        insightArea = new TextArea();
        insightArea.setWrapText(true);
        insightArea.setEditable(false);
        insightArea.setPrefHeight(250);
        insightArea.setPromptText("AI-generated insights from uploaded pregnancy report");

        uploadButton.setOnAction(e -> {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Select Pregnancy Report Image");
    chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    File file = chooser.showOpenDialog(stage);

    if (file != null) {
        selectedImageFile = file;
        insightArea.setText("Selected: " + selectedImageFile.getName());
        loader.setVisible(true);

        new Thread(() -> {
            try {
                String insights = GeminiInsightController.getInsights(file);
                Firestore db = FirestoreClient.getFirestore();
Map<String, Object> update = new HashMap<>();
update.put("responseOfSonographyReport", insights);

db.collection("users")
  .document(userEmail)
  .collection("preDeliveryData")
  .document("details")
  .set(update, SetOptions.merge());

                ReportData report = new ReportData(file, insights);
                uploadedReports.add(report);

                // Save to Firebase
                AuthController authController = new AuthController();
                // <-- get from login session
                 // Upload to Firebase Storage and get URL
        String imageUrl = AuthController.uploadSonographyImage(file, userEmail);
        AuthController.saveSonographyImageUrl(userEmail, imageUrl);

        javafx.application.Platform.runLater(() -> {
            loader.setVisible(false);
            insightArea.setText("AI Insights:\n" + insights + "\n\n✅ Image uploaded and saved.");
            addReportCard(report);
        });

            } catch (Exception ex) {
                javafx.application.Platform.runLater(() -> {
                    loader.setVisible(false);
                    insightArea.setText("Error: " + ex.getMessage());
                });
            }
        }).start();
    }
});



        Label note = new Label("Only JPG, PNG formats are accepted.");
        note.setFont(Font.font("Arial", FontPosture.ITALIC, 14));
        note.setTextFill(Color.DARKGRAY);

        uploadBox.getChildren().addAll(title, uploadButton, aiButton, loader, insightArea, note);

        return uploadBox;
    }


    private void fetchSonographyImagesFromFirebase() {
    String userEmail = UserSession.getUserEmail();
    Firestore db = FirestoreClient.getFirestore();

    DocumentReference docRef = db.collection("users").document(userEmail)
            .collection("preDeliveryData").document("details");

    // Run this on a background thread
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> {
        try {
            DocumentSnapshot snapshot = docRef.get().get();  // Blocking call
            if (snapshot.exists()) {
                List<String> imageUrls = (List<String>) snapshot.get("sonographyImages");
                if (imageUrls != null) {
                    for (String url : imageUrls) {
                        Platform.runLater(() -> {
                            addReportCardFromUrl(url); // UI update
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    });
}

private void addReportCardFromUrl(String imageUrl) {
    VBox card = new VBox(10);
    card.setAlignment(Pos.CENTER);
    card.setPrefSize(180, 220);
    card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #FF8FAB;");
    card.setEffect(new DropShadow(8, Color.LIGHTGRAY));

    Label name = new Label("Report Image");
    name.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
    name.setTextFill(Color.web("#FF5C8A"));

    Label viewText = new Label("Click to view & analyze");
    viewText.setFont(Font.font("Arial", FontPosture.ITALIC, 13));
    viewText.setTextFill(Color.GRAY);

    card.getChildren().addAll(name, viewText);

    card.setOnMouseClicked(e -> {
        loader.setVisible(true);
        insightArea.clear();
        reportPreview.setImage(new Image(imageUrl));

        new Thread(() -> {
            try {
                // 1. Download image to temp file
                File tempFile = File.createTempFile("report_", ".jpg");
                try (java.io.InputStream in = new java.net.URL(imageUrl).openStream();
                     java.io.FileOutputStream out = new java.io.FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                // 2. Run insights
                String insights = GeminiInsightController.getInsights(tempFile);
                Firestore db = FirestoreClient.getFirestore();
Map<String, Object> update = new HashMap<>();
update.put("responseOfSonographyReport", insights);

db.collection("users")
  .document(userEmail)
  .collection("preDeliveryData")
  .document("details")
  .set(update, SetOptions.merge());


                Platform.runLater(() -> {
                    loader.setVisible(false);
                    insightArea.setText("AI Insights (stored image):\n" + insights);
                });

            } catch (Exception ex) {
                Platform.runLater(() -> {
                    loader.setVisible(false);
                    insightArea.setText("Error analyzing stored image: " + ex.getMessage());
                });
            }
        }).start();
    });

    reportCards.getChildren().add(card);
}


    private VBox buildStorageBlock() {
        VBox storageBox = new VBox(20);
        storageBox.setPadding(new Insets(60, 60, 60, 40));
        storageBox.setAlignment(Pos.TOP_CENTER);

        Label storageTitle = new Label("Your Uploaded Reports");
        storageTitle.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 26));
        storageTitle.setTextFill(Color.web("#A53860"));

        reportCards = new FlowPane();
        reportCards.setHgap(20);
        reportCards.setVgap(20);
        reportCards.setPadding(new Insets(20));
        reportCards.setPrefWrapLength(800);

        reportPreview = new ImageView();
        reportPreview.setFitWidth(400);
        reportPreview.setPreserveRatio(true);
        reportPreview.setEffect(new DropShadow(10, Color.LIGHTGRAY));

        HBox content = new HBox(40, reportCards, reportPreview);
        content.setAlignment(Pos.TOP_CENTER);

        storageBox.getChildren().addAll(storageTitle, content);
        return storageBox;
    }

    private void addReportCard(ReportData report) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 220);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #FF8FAB;");
        card.setEffect(new DropShadow(8, Color.LIGHTGRAY));

        Label name = new Label(report.getImageFile().getName());
        name.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        name.setTextFill(Color.web("#FF5C8A"));

        Label viewText = new Label("Click to view");
        viewText.setFont(Font.font("Arial", FontPosture.ITALIC, 13));
        viewText.setTextFill(Color.GRAY);

        card.getChildren().addAll(name, viewText);

        card.setOnMouseClicked(e -> {
            insightArea.setText("AI Insights:\n" + report.getInsights());
            reportPreview.setImage(new Image(report.getImageFile().toURI().toString()));
        });

        reportCards.getChildren().add(card);
    }

    // Internal helper class to store uploaded report data
    private static class ReportData {
        private final File imageFile;
        private final String insights;

        public ReportData(File imageFile, String insights) {
            this.imageFile = imageFile;
            this.insights = insights;
        }

        public File getImageFile() {
            return imageFile;
        }

        public String getInsights() {
            return insights;
        }
    }
}
