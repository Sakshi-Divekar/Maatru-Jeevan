package com.priti.view;

import com.priti.controller.PostEmergencyAlertController;
import com.priti.controller.PostEmergencyController;
import com.priti.model.Hospital;
import com.priti.model.UserEmergency;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostEmergencyPage {
    private final Stage primaryStage;
    private final PostEmergencyController controller;
    private final String userId;
    private WebView mapView;
    private UserEmergency currentUser;
    private String userRegisteredAddress;
    private Scene scene;

    public PostEmergencyPage(Stage stage, String userId) {
        this.primaryStage = stage;
        this.controller = new PostEmergencyController();
        this.userId = userId;
        initializeUI();
    }

    private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to right bottom, #ffe2f2, #fdd6ec);");

        // Back Button
        Button backButton = new Button("← Back");
        backButton.setStyle(
            "-fx-background-color: #f59ec4;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Segoe UI Semibold';" +
            "-fx-background-radius: 18;" +
            "-fx-padding: 8 22;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, #f59ec4AA, 8, 0.6, 0, 2);"
        );
        backButton.setOnAction(e -> navigateToPostHome());

        // Emergency Button
        Button emergencyBtn = new Button("EMERGENCY");
        emergencyBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #ff7096, #fc9ac4);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Segoe UI Black';" +
            "-fx-background-radius: 25;" +
            "-fx-padding: 10 32;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, #ff709699, 16, 0.7, 0, 3);"
        );
        emergencyBtn.setOnAction(e -> handleEmergencyAlert());

        HBox topBar = new HBox(backButton, new Region(), emergencyBtn);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        topBar.setPadding(new Insets(0, 0, 20, 0));
        topBar.setAlignment(Pos.TOP_LEFT);
        root.setTop(topBar);

        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setStyle("-fx-progress-color: #f59ec4");
        StackPane loadingPane = new StackPane(loadingIndicator);
        loadingPane.setAlignment(Pos.CENTER);
        root.setCenter(loadingPane);

        this.scene = new Scene(root, 1550, 800);

        loadEmergencyDataAsync(root);
    }

    private void navigateToPostHome() {
    try {
        new PostHomePage().start(primaryStage);
    } catch (Exception ex) {
        ex.printStackTrace();
        showAlert("Navigation Error", "Failed to load Post Home Page: " + ex.getMessage(), Alert.AlertType.ERROR);
    }
}


    private void handleEmergencyAlert() {
        try {
            PostEmergencyAlertController alertController = new PostEmergencyAlertController();
            alertController.sendEmergencyAlert();
            showAlert("Emergency Alert", "Emergency alert sent successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            showAlert("Error", "Failed to send emergency alert: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void loadEmergencyDataAsync(BorderPane root) {
        new Thread(() -> {
            try {
                Map<String, Object> data = controller.getEmergencyData(userId);
                currentUser = (UserEmergency) data.get("user");
                this.userRegisteredAddress = (String) data.get("address");
                Platform.runLater(() -> {
                    root.setCenter(createContentPane());
                    if (currentUser != null) {
                        loadUserEmergencyLocationOnMap();
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> 
                    showAlert("Error", "Failed to load emergency data: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        }).start();
    }

    private HBox createContentPane() {
        HBox content = new HBox(30);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        VBox leftColumn = new VBox(40);
        leftColumn.setPrefWidth(650);

        List<Hospital> hospitals = getHardcodedHospitals();
        if (hospitals != null && !hospitals.isEmpty()) {
            int mid = (hospitals.size() + 1) / 2;
            VBox topCards = new VBox(24);
            topCards.setAlignment(Pos.TOP_CENTER);
            VBox bottomCards = new VBox(24);
            bottomCards.setAlignment(Pos.BOTTOM_CENTER);

            for (int i = 0; i < hospitals.size(); i++) {
                Hospital hospital = hospitals.get(i);
                if (hospital != null) {
                    VBox card = createHospitalCard(hospital);
                    if (i < mid) {
                        topCards.getChildren().add(card);
                    } else {
                        bottomCards.getChildren().add(card);
                    }
                }
            }

            leftColumn.getChildren().addAll(topCards, bottomCards);
        }

        VBox mapSection = createMapSection();
        content.getChildren().addAll(leftColumn, mapSection);
        return content;
    }

    private List<Hospital> getHardcodedHospitals() {
        List<Hospital> hospitals = new ArrayList<>();

        try {
            Hospital h1 = new Hospital();
            h1.setName("MJM Hospital");
            h1.setContact("020-25698754");
            h1.setAddress("1202/2, Ghole Rd, Shivajinagar, Pune");
            h1.setLatitude(18.524338);
            h1.setLongitude(73.843887);

            Hospital h2 = new Hospital();
            h2.setName("Nityanand Hospital");
            h2.setContact("020-24382244");
            h2.setAddress("78/2/2, Patang Plaza, Katraj, Pune");
            h2.setLatitude(18.457527);
            h2.setLongitude(73.8676);

            Hospital h3 = new Hospital();
            h3.setName("Deenanath Mangeshkar Hospital");
            h3.setContact("020-25446000");
            h3.setAddress("Erandwane, Pune");
            h3.setLatitude(18.50198);
            h3.setLongitude(73.83217);

            Hospital h4 = new Hospital();
            h4.setName("Jehangir Hospital");
            h4.setContact("020-26051234");
            h4.setAddress("32 Sassoon Road, Pune");
            h4.setLatitude(18.53028);
            h4.setLongitude(73.8058);

            hospitals.add(h1);
            hospitals.add(h2);
            hospitals.add(h3);
            hospitals.add(h4);
        } catch (Exception e) {
            showAlert("Error", "Failed to load hospital data: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return hospitals;
    }

    private VBox createHospitalCard(Hospital hospital) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(290);
        card.setStyle("-fx-background-radius: 20; -fx-background-color: linear-gradient(to bottom right, #ffaccb, #ffe1ed); -fx-border-radius: 20; -fx-border-color: #ff82b2; -fx-border-width: 1.8;");

        Label nameLabel = new Label(hospital.getName());
        nameLabel.setFont(Font.font("Segoe UI Semibold", 16));
        nameLabel.setTextFill(Color.web("#444"));

        Label contact = new Label("Contact: " + hospital.getContact());
        contact.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: #333;");

        Label address = new Label(hospital.getAddress());
        address.setWrapText(true);
        address.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-text-fill: #333;");

        card.getChildren().addAll(nameLabel, contact, address);
        return card;
    }

    private VBox createMapSection() {
        VBox mapContainer = new VBox(12);
        mapContainer.setPadding(new Insets(14));
        mapContainer.setAlignment(Pos.TOP_CENTER);
        mapContainer.setMaxWidth(600);
        mapContainer.setMaxHeight(600);
        mapContainer.setStyle("-fx-background-color: #ffe2f2; -fx-background-radius: 22; -fx-border-radius: 22; -fx-border-color: #f59ec4; -fx-border-width: 3;");

        Label title = new Label("REGISTERED ADDRESS LOCATION");
        title.setFont(Font.font("Segoe UI Semibold", 19));
        title.setTextFill(Color.web("#ff7096"));

        mapView = new WebView();
        mapView.setPrefSize(580, 580);
        mapContainer.getChildren().addAll(title, mapView);

        return mapContainer;
    }

    private void loadUserEmergencyLocationOnMap() {
        if (currentUser == null || mapView == null) return;

        String htmlContent = String.format("""
            <!DOCTYPE html>
            <html><head><meta charset=\"utf-8\" />
            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
            <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet/dist/leaflet.css\" />
            <style> #map { height: 100%%; width: 100%%; border-radius: 18px; border: 3px solid #f59ec4; } html, body { margin: 0; padding: 0; height: 100%%; background: #ffe2f2; } </style>
            </head>
            <body>
            <div id=\"map\"></div>
            <script src=\"https://unpkg.com/leaflet/dist/leaflet.js\"></script>
            <script>
                var map = L.map('map').setView([%f, %f], 15);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { attribution: '© OpenStreetMap contributors' }).addTo(map);
                L.marker([%f, %f]).addTo(map);
            </script>
            </body></html>
            """,
            currentUser.getLatitude(), currentUser.getLongitude(),
            currentUser.getLatitude(), currentUser.getLongitude());

        mapView.getEngine().loadContent(htmlContent);
    }

    public Scene getScene() {
        return this.scene;
    }
}