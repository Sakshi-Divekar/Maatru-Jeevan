package com.priti.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.AuthController;
import com.priti.controller.FirebaseStorageService;
import com.priti.utils.UserSession;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import com.priti.utils.UserSession;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreProfilePage {
    ImageView profileImage = new ImageView();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Scene getScene(Stage stage) {
        // ----- MAIN CONTAINER STYLING -----
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f5f7fa, #fff1f1);");

        // ----- TOP BAR -----
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setStyle("-fx-background-color: #A53860; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            PreHomePage homePage = new PreHomePage();
            stage.setScene(homePage.getScene(stage));
        });

        // Add some spacing and styling to the top bar
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label appName = new Label("Maatru-Jeevan");
        appName.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        topBar.getChildren().addAll(backButton, spacer, appName);

        // ----- PROFILE CONTENT -----
        VBox profileContainer = new VBox(20);
        profileContainer.setAlignment(Pos.TOP_CENTER);
        profileContainer.setPadding(new Insets(30, 50, 50, 50));
        profileContainer.setMaxWidth(900);

        //String userEmail = UserSession.getUserEmail();

        // Profile Header
        Label titleLabel = new Label("My Profile");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#A53860"));
        titleLabel.setStyle("-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 3, 0, 1, 1);");

        // Profile Image with Circular Mask
        StackPane imageContainer = new StackPane();
        imageContainer.setAlignment(Pos.CENTER);
        
        // Circular background for the image
        Circle clip = new Circle(70);
        clip.setCenterX(70);
        clip.setCenterY(70);
        
        ImageView profileImage = new ImageView();
        profileImage.setFitWidth(140);
        profileImage.setFitHeight(140);
        profileImage.setPreserveRatio(true);
        profileImage.setClip(clip);
        
        // Add a subtle shadow and border
        imageContainer.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 2);");
        imageContainer.getChildren().add(profileImage);

        // ----- PROFILE DETAILS CARD -----
        GridPane profileGrid = new GridPane();
        profileGrid.setVgap(15);
        profileGrid.setHgap(25);
        profileGrid.setAlignment(Pos.CENTER);
        profileGrid.setPadding(new Insets(30, 40, 30, 40));
        profileGrid.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Create styled labels and values
        Label[] labels = {
            new Label("Mother's Name:"), new Label("Age:"), 
            new Label("Contact No:"), new Label("Address:"),
            new Label("Doctor Name:"), new Label("Due Date:"),
            new Label("Emergency Contact:"), new Label("Gravida Status:"),
            new Label("Medical History:")
        };
        
        Label[] values = new Label[labels.length];
        
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(Font.font("Arial", FontWeight.BOLD, 14));
            labels[i].setTextFill(Color.web("#555555"));
            
            values[i] = new Label();
            values[i].setFont(Font.font("Arial", 14));
            values[i].setTextFill(Color.web("#333333"));
            values[i].setWrapText(true);
            values[i].setMaxWidth(300);
            
            profileGrid.add(labels[i], 0, i);
            profileGrid.add(values[i], 1, i);
        }

        // Add a subtle separator
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));

        // ----- LOGOUT BUTTON -----
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-font-weight: bold; " +
                            "-fx-background-radius: 20; -fx-padding: 10 25; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> {
            UserSession.clear();
            LandingPage landingPage = new LandingPage();
            landingPage.start(stage);
            stage.setScene(landingPage.getScene());
        });

        // Add all components to the profile container
        profileContainer.getChildren().addAll(titleLabel, imageContainer, profileGrid, separator, logoutButton);

        // ----- MAIN LAYOUT -----
        root.setTop(topBar);
        root.setCenter(profileContainer);

        // ----- LOAD USER DATA -----
        String userEmail = UserSession.getUserEmail();
        System.out.println("Loading profile for " + userEmail);

        CompletableFuture.runAsync(() -> {
            try {
                Firestore db = FirestoreClient.getFirestore();
                DocumentReference docRef = db.collection("users")
                        .document(userEmail)
                        .collection("preDeliveryData")
                        .document("details");

                ApiFuture<DocumentSnapshot> future = docRef.get();
                DocumentSnapshot document = future.get();

                if (document.exists()) {
                    Map<String, Object> data = document.getData();

                    Platform.runLater(() -> {
                        values[0].setText((String) data.getOrDefault("motherName", ""));
                        values[1].setText((String) data.getOrDefault("age", ""));
                        values[2].setText((String) data.getOrDefault("contactNo", ""));
                        values[3].setText((String) data.getOrDefault("address", ""));
                        values[4].setText((String) data.getOrDefault("doctorName", ""));
                        values[5].setText((String) data.getOrDefault("dueDate", ""));
                        values[6].setText((String) data.getOrDefault("emergencyContact", ""));
                        values[7].setText((String) data.getOrDefault("gravidaStatus", ""));
                        values[8].setText((String) data.getOrDefault("medicalHistory", ""));

                        
                        
                // 👇 Add this to load and show the profile image
        String imageUrl = (String) data.get("imageUrl");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            profileImage.setImage(new Image(imageUrl));
        }

            });
        } else {
            System.out.println("❌ No profile data found for " + userEmail);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}, executor);

        return new Scene(root, 1550, 800);
    }
}