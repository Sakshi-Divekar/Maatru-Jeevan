package com.priti.view;

import com.priti.controller.EmergencyAlertController;
import com.priti.controller.EmergencyController;
import com.priti.model.Hospital;
import com.priti.model.UserEmergency;
import com.priti.utils.UserSession;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmergencyPage {
    private final Stage primaryStage;
    private final EmergencyController controller;
    private final String userId;
    private WebView mapView;
    private UserEmergency currentUser;
    private String userRegisteredAddress;
    private Scene scene;

    public EmergencyPage(Stage stage, String userId) {
    this.primaryStage = stage;
    this.userId = UserSession.getUserEmail(); // Use the actual logged-in user's email
    this.controller = new EmergencyController();
    initializeUI();
}


    private void initializeUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to right bottom, #f5ccd8, #ffdce9);");

        Button backButton = new Button("← Back");
        backButton.setStyle(
                "-fx-background-color: #A53860;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI Semibold';" +
                "-fx-background-radius: 18;" +
                "-fx-padding: 8 22;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #A53860AA, 8, 0.6, 0, 2);");

        backButton.setOnAction(e -> {
            PreHomePage preHomePage = new PreHomePage();
            primaryStage.setScene(preHomePage.getScene(primaryStage));
        });

        Button emergencyBtn = new Button("EMERGENCY");
        emergencyBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #A53860, #db6c92);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'Segoe UI Black';" +
                "-fx-background-radius: 25;" +
                "-fx-padding: 10 32;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #A5386099, 16, 0.7, 0, 3);");

        emergencyBtn.setOnAction(e -> {
            try {
                EmergencyAlertController alertController = new EmergencyAlertController();
                alertController.sendEmergencyAlert();
                showSuccessAlert("Emergency alert sent successfully!");
            } catch (Exception ex) {
                showErrorAlert("Failed to send emergency alert: " + ex.getMessage());
            }
        });

        HBox topBar = new HBox(backButton, new Region(), emergencyBtn);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        topBar.setPadding(new Insets(0, 0, 20, 0));
        topBar.setAlignment(Pos.TOP_LEFT);
        root.setTop(topBar);

        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setStyle("-fx-progress-color: #A53860");
        StackPane loadingPane = new StackPane(loadingIndicator);
        loadingPane.setAlignment(Pos.CENTER);
        root.setCenter(loadingPane);

        this.scene = new Scene(root, 1550, 800);

        new Thread(() -> {
            try {
                Map<String, Object> data = controller.getEmergencyData(userId);
                currentUser = (UserEmergency) data.get("user");
                this.userRegisteredAddress = (String) data.get("address");
                Platform.runLater(() -> root.setCenter(createContentPane()));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showErrorAlert("Failed to load emergency data: " + e.getMessage());
                    primaryStage.close();
                });
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
        int mid = (hospitals.size() + 1) / 2;

        VBox topCards = new VBox(24);
        topCards.setAlignment(Pos.TOP_CENTER);
        VBox bottomCards = new VBox(24);
        bottomCards.setAlignment(Pos.BOTTOM_CENTER);

        for (int i = 0; i < hospitals.size(); i++) {
            VBox card = createHospitalCard(hospitals.get(i));
            if (i < mid) topCards.getChildren().add(card);
            else bottomCards.getChildren().add(card);
        }

        leftColumn.getChildren().addAll(topCards, bottomCards);

        VBox mapSection = createMapSection();

        content.getChildren().addAll(leftColumn, mapSection);
        return content;
    }

    private List<Hospital> getHardcodedHospitals() {
        List<Hospital> hospitals = new ArrayList<>();

        Hospital h1 = new Hospital();
        h1.setName("MJM Hospital");
        h1.setContact("020-25698754");
        h1.setAddress("1202/2, Ghole Rd, Shivajinagar, Pune, Maharashtra");
        h1.setLatitude(18.524338);
        h1.setLongitude(73.843887);
        hospitals.add(h1);

        Hospital h2 = new Hospital();
        h2.setName("Nityanand Hospital");
        h2.setContact("020-24382244");
        h2.setAddress("78/2/2, Patang Plaza, Katraj, Pune, Maharashtra");
        h2.setLatitude(18.457527);
        h2.setLongitude(73.8676);
        hospitals.add(h2);

        Hospital h3 = new Hospital();
        h3.setName("Deenanath Mangeshkar Hospital");
        h3.setContact("020-25446000");
        h3.setAddress("Erandwane, Pune, Maharashtra");
        h3.setLatitude(18.50198);
        h3.setLongitude(73.83217);
        hospitals.add(h3);

        Hospital h4 = new Hospital();
        h4.setName("Jehangir Hospital");
        h4.setContact("020-26051234");
        h4.setAddress("32 Sassoon Road, Pune, Maharashtra");
        h4.setLatitude(18.53028);
        h4.setLongitude(73.8058);
        hospitals.add(h4);

        return hospitals;
    }

    private VBox createHospitalCard(Hospital hospital) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18, 16, 18, 16));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(290);
        card.setStyle(
                "-fx-background-radius: 20;" +
                "-fx-background-insets: 0;" +
                "-fx-background-color: linear-gradient(to bottom right, #a53860, #e593ac);" +
                "-fx-border-radius: 20;" +
                "-fx-border-color: #A53860;" +
                "-fx-border-width: 1.8;" +
                "-fx-effect: dropshadow(gaussian, #A53860AA, 14, 0.2, 0, 4);");

        DropShadow shadowGlow = new DropShadow(20, Color.web("#A53860"));
        card.setOnMouseEntered(e -> {
            card.setEffect(shadowGlow);
            card.setScaleX(1.04);
            card.setScaleY(1.04);
        });
        card.setOnMouseExited(e -> {
            card.setEffect(null);
            card.setScaleX(1);
            card.setScaleY(1);
        });

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/assets/images/hospital_fill_pink.png")));
            icon.setFitWidth(32);
            icon.setFitHeight(32);
            header.getChildren().add(icon);
        } catch (Exception ignored) {
        }

        Label nameLabel = new Label(hospital.getName());
        nameLabel.setFont(Font.font("Segoe UI Semibold", 16));
        nameLabel.setTextFill(Color.web("#020202ff"));
        header.getChildren().add(nameLabel);

        Label contact = new Label("Contact: " + hospital.getContact());
        contact.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-text-fill: #000000ff");

        Label address = new Label(hospital.getAddress());
        address.setWrapText(true);
        address.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-text-fill: #000000ff;");

        card.getChildren().addAll(header, contact, address);
        return card;
    }

    private VBox createMapSection() {
        VBox mapContainer = new VBox(12);
        mapContainer.setPadding(new Insets(14));
        mapContainer.setAlignment(Pos.TOP_CENTER);
        mapContainer.setMaxWidth(600);
        mapContainer.setMaxHeight(600);
        mapContainer.setStyle(
                "-fx-background-color: #f5ccd8;" +
                "-fx-background-radius: 22;" +
                "-fx-border-radius: 22;" +
                "-fx-border-color: #A53860;" +
                "-fx-border-width: 3;" +
                "-fx-effect: dropshadow(gaussian, #a53860aa, 18, 0.25, 0, 5);");

        Label title = new Label("REGISTERED ADDRESS LOCATION");
        title.setFont(Font.font("Segoe UI Semibold", 19));
        title.setTextFill(Color.web("#A53860"));

        mapView = new WebView();
        mapView.setPrefSize(580, 580);

        loadUserEmergencyLocationOnMap();

        mapContainer.getChildren().addAll(title, mapView);
        return mapContainer;
    }

    private void loadUserEmergencyLocationOnMap() {
        String htmlContent = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
            <style>
                #map { height: 100%%; width: 100%%; border-radius: 18px; border: 3px solid #A53860; }
                html, body { margin: 0; padding: 0; height: 100%%; background: #f5ccd8; }
                .custom-icon {
                  background-color: #A53860;
                  border-radius: 50%%;
                  width: 24px;
                  height: 24px;
                  border: 3px solid #fff;
                  box-shadow: 0 0 16px #a53860aa;
                }
            </style>
            </head>
            <body>
            <div id="map"></div>
            <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
            <script>
                var map = L.map('map').setView([%f, %f], 15);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap contributors'
                }).addTo(map);
                L.marker([%f, %f], {icon: L.divIcon({className: 'custom-icon'})}).addTo(map);
            </script>
            </body>
            </html>""",
                currentUser.getLatitude(), currentUser.getLongitude(),
                currentUser.getLatitude(), currentUser.getLongitude());

        mapView.getEngine().loadContent(htmlContent);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return this.scene;
    }
}