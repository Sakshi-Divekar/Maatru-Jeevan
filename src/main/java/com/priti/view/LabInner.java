package com.priti.view;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.AuthController;
import com.priti.controller.FirebaseStorageService;
import com.priti.controller.LabInsightController;
import com.priti.utils.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabInner {

    private final Stage stage;
    private VBox storageBox;
    private FirebaseStorageService storageService = new FirebaseStorageService();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    // Pink color palette
    private static final String PRIMARY_PINK = "#FF69B4"; // Hot pink
    private static final String SECONDARY_PINK = "#FFB6C1"; // Light pink
    private static final String DARK_PINK = "#DB7093"; // Pale violet red
    private static final String LIGHT_PINK = "#FFF0F5"; // Lavender blush
    private static final String ACCENT_PINK = "#FF1493"; // Deep pink

    public LabInner(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        stage.setTitle("Lab Reports Management");

        // Back Button with pink styling
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", 16));
        backButton.setStyle("-fx-background-color: " + DARK_PINK + "; -fx-text-fill: white; -fx-background-radius: 14; -fx-padding: 8 15;");
        backButton.setOnAction(e -> {
            PregnancyMonitoringPage previousPage = new PregnancyMonitoringPage(stage);
            stage.setScene(previousPage.getScene());
        });

        HBox topBar = new HBox(backButton);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(15, 0, 15, 20));

        // Title with icon - using safe image loading
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);
        ImageView labIcon = createSafeImageView("/assets/images/lab_icon.jpg", 40, 40);
        Label titleLabel = new Label("Lab Reports Management");
        titleLabel.setFont(Font.font("Arial Rounded MT Bold", 28));
        titleLabel.setTextFill(Color.web(DARK_PINK));
        titleBox.getChildren().addAll(labIcon, titleLabel);

        // Main content container
        HBox contentContainer = new HBox(30);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.setPadding(new Insets(20));

        // Upload Section
        VBox uploadSection = new VBox(20);
        uploadSection.setAlignment(Pos.CENTER);
        uploadSection.setPadding(new Insets(30));
        uploadSection.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        uploadSection.setPrefWidth(400);

        Label uploadTitle = new Label("Upload New Report");
        uploadTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        uploadTitle.setTextFill(Color.web(DARK_PINK));

        ImageView uploadIcon = createSafeImageView("/assets/images/upload_icon.jpg", 80, 80);
        Button uploadButton = createUploadButton("Lab");
        uploadButton.setPrefWidth(250);
        uploadButton.setPrefHeight(50);

        Label uploadHint = new Label("Supported formats: JPG, PNG, PDF");
        uploadHint.setFont(Font.font("Arial", 12));
        uploadHint.setTextFill(Color.GRAY);

        uploadSection.getChildren().addAll(uploadTitle, uploadIcon, uploadButton, uploadHint);

        // Report Viewer Section
        VBox reportsSection = new VBox(15);
        reportsSection.setAlignment(Pos.TOP_CENTER);
        reportsSection.setPadding(new Insets(20));
        reportsSection.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        reportsSection.setPrefWidth(700);

        Label reportsTitle = new Label("Your Lab Reports");
        reportsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        reportsTitle.setTextFill(Color.web(ACCENT_PINK));

        storageBox = new VBox(15);
        storageBox.setPadding(new Insets(20));
        storageBox.setStyle("-fx-background-color: #FFFFFF;");

        ScrollPane storageScrollPane = new ScrollPane(storageBox);
        storageScrollPane.setFitToWidth(true);
        storageScrollPane.setPrefHeight(400);
        storageScrollPane.setStyle("-fx-background: #FFFFFF; -fx-border-color: transparent;");

        reportsSection.getChildren().addAll(reportsTitle, storageScrollPane);
        contentContainer.getChildren().addAll(uploadSection, reportsSection);

        // Main Layout
        VBox mainLayout = new VBox(20, topBar, titleBox, contentContainer);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: " + LIGHT_PINK + ";");

        // Load previous reports when initializing the page
        loadPreviousReports();

        return new Scene(mainLayout, 1550, 800);
    }

    private void loadPreviousReports() {
        String email = UserSession.getUserEmail();
        String stage = "Pre-Delivery";
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";

        ProgressIndicator loader = new ProgressIndicator();
        loader.setMaxSize(40, 40);
        storageBox.getChildren().add(loader);

        new Thread(() -> {
            try {
                DocumentSnapshot document = FirestoreClient.getFirestore()
                        .collection("users")
                        .document(email)
                        .collection(collection)
                        .document("details")
                        .get()
                        .get();

                if (document.exists()) {
                    List<String> labReportUrls = (List<String>) document.get("labReportImages");
                    if (labReportUrls != null && !labReportUrls.isEmpty()) {
                        javafx.application.Platform.runLater(() -> {
                            storageBox.getChildren().remove(loader);
                            for (String url : labReportUrls) {
                                addReportCard(url, LocalDate.now().format(DATE_FORMATTER));
                            }
                        });
                        return;
                    }
                }

                javafx.application.Platform.runLater(() -> {
                    storageBox.getChildren().remove(loader);
                    Label noReportsLabel = new Label("No previous reports found");
                    noReportsLabel.setFont(Font.font("Arial", 14));
                    noReportsLabel.setTextFill(Color.GRAY);
                    storageBox.getChildren().add(noReportsLabel);
                });
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    storageBox.getChildren().remove(loader);
                    Label errorLabel = new Label("❌ Failed to load previous reports");
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setFont(Font.font("Arial", 12));
                    storageBox.getChildren().add(errorLabel);
                });
            }
        }).start();
    }

    private ImageView createSafeImageView(String resourcePath, double fitWidth, double fitHeight) {
        ImageView imageView = new ImageView();
        try {
            InputStream imageStream = getClass().getResourceAsStream(resourcePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                imageView.setImage(image);
            } else {
                System.err.println("Image not found: " + resourcePath);
                imageView.setStyle("-fx-background-color: lightgray;");
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + resourcePath);
            e.printStackTrace();
        }
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        return imageView;
    }

    private Button createUploadButton(String testName) {
        Button button = new Button("📤 Upload Lab Report");
        button.setPrefWidth(250);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-background-radius: 25; -fx-padding: 12 24;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + SECONDARY_PINK + "; -fx-text-fill: white; -fx-background-radius: 25; -fx-padding: 12 24;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-background-radius: 25; -fx-padding: 12 24;"));

        button.setOnAction(e -> handleUpload(testName));
        return button;
    }

    private void handleUpload(String testType) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Lab Report");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = chooser.showOpenDialog(stage);

        if (file == null) return;

        ProgressIndicator loader = new ProgressIndicator();
        loader.setMaxSize(40, 40);
        storageBox.getChildren().add(loader);

        new Thread(() -> {
            try {
                String email = UserSession.getUserEmail();
                String fileName = "lab_reports/" + email + "/" + System.currentTimeMillis() + "_" + file.getName();
                String fileUrl = storageService.uploadFile(file, fileName);

                AuthController authController = new AuthController();
                String stage = "Pre-Delivery";
                String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";

                DocumentReference docRef = FirestoreClient.getFirestore()
                        .collection("users")
                        .document(email)
                        .collection(collection)
                        .document("details");

                Map<String, Object> update = new HashMap<>();
                update.put("labReportImages", FieldValue.arrayUnion(fileUrl));
                update.put("updatedAt", FieldValue.serverTimestamp());

                docRef.update(update).get();

                String insight = LabInsightController.getLabInsights(file);
                boolean saved = authController.saveInsightresponseOfLabReport(email, insight, stage);

                String formattedDate = LocalDate.now().format(DATE_FORMATTER);
                javafx.application.Platform.runLater(() -> {
                    storageBox.getChildren().remove(loader);
                    addReportCard(fileUrl, formattedDate);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    storageBox.getChildren().remove(loader);
                    Label errorLabel = new Label("❌ Failed to upload report: " + ex.getMessage());
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setFont(Font.font("Arial", 12));
                    storageBox.getChildren().add(errorLabel);
                });
            }
        }).start();
    }

    private void addReportCard(String fileUrl, String date) {
        VBox reportCard = new VBox(10);
        reportCard.setPadding(new Insets(15));
        reportCard.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 10; -fx-border-radius: 10;");

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        ImageView docIcon = createSafeImageView("/assets/images/document_icon.jpg", 24, 24);

        String fileName = extractFileNameFromUrl(fileUrl);
        Label fileNameLabel = new Label(fileName);
        fileNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        headerBox.getChildren().addAll(docIcon, fileNameLabel);

        Label dateLabel = new Label("Uploaded: " + date);
        dateLabel.setFont(Font.font("Arial", 12));
        dateLabel.setTextFill(Color.GRAY);

        Button viewButton = new Button("View Report");
        viewButton.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-background-radius: 15;");
        viewButton.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(fileUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        reportCard.getChildren().addAll(headerBox, dateLabel, viewButton);
        storageBox.getChildren().add(reportCard);
    }

    private String extractFileNameFromUrl(String url) {
        try {
            String path = url.split("\\?")[0];
            String[] parts = path.split("/");
            String lastPart = parts[parts.length - 1];
            // Remove timestamp prefix if present (format: 123456789_filename.ext)
            if (lastPart.matches("^\\d+_.+")) {
                return lastPart.substring(lastPart.indexOf('_') + 1);
            }
            return lastPart.split("&")[0];
        } catch (Exception e) {
            return "Report";
        }
    }
}