package com.priti.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.AuthController;
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
import javafx.stage.Stage;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PostProfilePage {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Scene getScene(Stage stage) {
        // Main container with elegant gradient background
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #fdf2f9, #f3e6ff);");

        // ----- TOP BAR WITH IMPROVED STYLING -----
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(12, 25, 12, 25));
        topBar.setStyle("-fx-background-color: #8e44ad; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);");

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 15px; " +
                          "-fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15;");
        backButton.setOnAction(e -> {
            PostHomePage home = new PostHomePage();
            stage.setScene(home.getScene(stage));
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label appName = new Label("Maatru-Jeevan");
        appName.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.3), 2, 0, 1, 1);");
        
        topBar.getChildren().addAll(backButton, spacer, appName);

        // ----- SCROLLABLE CONTENT CONTAINER -----
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // ----- MAIN CONTENT CONTAINER -----
        VBox profileContainer = new VBox(25);
        profileContainer.setAlignment(Pos.TOP_CENTER);
        profileContainer.setPadding(new Insets(35, 50, 50, 50));
        profileContainer.setMaxWidth(950);
        profileContainer.setStyle("-fx-background-color: transparent;");

       // Profile Header with improved styling
Label titleLabel = new Label("Post-Delivery Profile");
titleLabel.setStyle("-fx-font-family: 'Segoe UI'; " +
                   "-fx-font-weight: bold; " +
                   "-fx-font-size: 36px; " +
                   "-fx-text-fill: purple; " +
                   "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 5, 0, 2, 2);");

        // ----- PROFILE IMAGE WITH PROPER CIRCULAR CLIPPING -----
        StackPane imageContainer = new StackPane();
        imageContainer.setAlignment(Pos.CENTER);
        
        // Create the clipping circle (radius 80)
        Circle clip = new Circle(80);
        clip.setCenterX(80);
        clip.setCenterY(80);
        
        // Create the ImageView with proper sizing
        ImageView profileImage = new ImageView();
        profileImage.setFitWidth(160);  // Should be 2*radius
        profileImage.setFitHeight(160);
        profileImage.setPreserveRatio(false);  // Disable to fill the circle completely
        profileImage.setClip(clip);
        
        // Create the decorative frame (same size as clip)
        Circle frame = new Circle(80);
        frame.setFill(Color.TRANSPARENT);
        frame.setStroke(Color.web("#8e44ad"));
        frame.setStrokeWidth(3);
        
        // Set up the container
        StackPane imageHolder = new StackPane();
        imageHolder.getChildren().addAll(profileImage, frame);
        imageHolder.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(142,68,173,0.4), 15, 0, 0, 5);");
        
        // Add to main container with proper sizing
        imageContainer.getChildren().add(imageHolder);
        imageContainer.setMinSize(160, 160);
        imageContainer.setMaxSize(160, 160);

        // ----- ELEGANT PROFILE DETAILS CARD -----
        GridPane profileGrid = new GridPane();
        profileGrid.setVgap(18);
        profileGrid.setHgap(30);
        profileGrid.setAlignment(Pos.CENTER);
        profileGrid.setPadding(new Insets(35, 45, 35, 45));
        profileGrid.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                           "-fx-border-color: #e0d0eb; -fx-border-width: 1; -fx-border-radius: 20; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(142,68,173,0.2), 20, 0, 0, 8);");

        // Create form fields
        TextField nameField = createStyledField();
        TextArea addressField = createStyledTextArea();
        TextField babyNameField = createStyledField();
        TextField weightField = createStyledField();
        TextField dobField = createStyledField();
        TextField genderField = createStyledField();
        TextField feedingTypeField = createStyledField();
        TextField recoveryField = createStyledField();
        TextField vaccinationField = createStyledField();

        // Add fields to grid
        addFormSection(profileGrid, "Mother's Information", 0);
        addFormField(profileGrid, "Mother Name:", nameField, 1);
        addFormField(profileGrid, "Address:", addressField, 2);
        
        addFormSection(profileGrid, "Baby's Information", 3);
        addFormField(profileGrid, "Baby Name:", babyNameField, 4);
        addFormField(profileGrid, "Weight (kg):", weightField, 5);
        addFormField(profileGrid, "Date of Birth:", dobField, 6);
        addFormField(profileGrid, "Gender:", genderField, 7);
        addFormField(profileGrid, "Feeding Type:", feedingTypeField, 8);
        
        addFormSection(profileGrid, "Health Information", 9);
        addFormField(profileGrid, "Recovery Status:", recoveryField, 10);
        addFormField(profileGrid, "Vaccination Start:", vaccinationField, 11);

        // Add all components to the profile container
        profileContainer.getChildren().addAll(titleLabel, imageContainer, profileGrid);
        
        // Set the content of the scroll pane
        VBox wrapperBox = new VBox(profileContainer);
        wrapperBox.setAlignment(Pos.TOP_CENTER);
        wrapperBox.setPadding(new Insets(30, 0, 30, 0));
        scrollPane.setContent(wrapperBox);

        // Apply scrollbar styling after scene is created
        scrollPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                scrollPane.lookupAll(".scroll-bar:vertical").forEach(node -> 
                    node.setStyle("-fx-background-color: transparent;"));
                scrollPane.lookupAll(".scroll-bar:vertical .track").forEach(node -> 
                    node.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"));
                scrollPane.lookupAll(".scroll-bar:vertical .thumb").forEach(node -> 
                    node.setStyle("-fx-background-color: #d0b0e0; -fx-background-radius: 5em;"));
            }
        });

        // ----- MAIN LAYOUT -----
        root.setTop(topBar);
        root.setCenter(scrollPane);

        // ----- LOAD USER DATA -----
        String userEmail = UserSession.getUserEmail();
        System.out.println("Loading post-delivery profile for " + userEmail);

        CompletableFuture.runAsync(() -> {
            try {
                Firestore db = FirestoreClient.getFirestore();
                DocumentReference docRef = db.collection("users")
                        .document(userEmail)
                        .collection("postDeliveryData")
                        .document("details");

                ApiFuture<DocumentSnapshot> future = docRef.get();
                DocumentSnapshot document = future.get();

                if (document.exists()) {
                    Map<String, Object> data = document.getData();

                    Platform.runLater(() -> {
                        // Set all field values
                        nameField.setText((String) data.getOrDefault("motherName", ""));
                        addressField.setText((String) data.getOrDefault("address", ""));
                        babyNameField.setText((String) data.getOrDefault("babyName", ""));
                        weightField.setText((String) data.getOrDefault("weight", ""));
                        dobField.setText((String) data.getOrDefault("dateOfBirth", ""));
                        genderField.setText((String) data.getOrDefault("gender", ""));
                        feedingTypeField.setText((String) data.getOrDefault("feedingType", ""));
                        recoveryField.setText((String) data.getOrDefault("recoveryStatus", ""));
                        vaccinationField.setText((String) data.getOrDefault("vaccinationStartDate", ""));

                        // Load image using imageUrl field
                        String imageUrl = (String) data.get("imageUrl");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Image image = new Image(imageUrl, true); // background loading
                            image.errorProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    System.out.println("Failed to load profile image: " + imageUrl);
                                    // Optionally set a default image
                                    // profileImage.setImage(new Image("/assets/images/default-profile.jpg"));
                                }
                            });
                            profileImage.setImage(image);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);

        return new Scene(root, 1550, 800);
    }

    private void addFormSection(GridPane grid, String title, int row) {
        Label section = new Label(title);
        section.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 18px; " +
                       "-fx-font-weight: bold; -fx-text-fill: #8e44ad; " +
                       "-fx-padding: 0 0 10 0;");
        grid.add(section, 0, row, 2, 1);
    }

    private void addFormField(GridPane grid, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 15px; " +
                     "-fx-font-weight: bold; -fx-text-fill: #555555;");
        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }

    private TextField createStyledField() {
        TextField field = new TextField();
        field.setEditable(false);
        field.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; " +
                      "-fx-background-color: #faf5ff; -fx-border-color: #e0d0eb; " +
                      "-fx-border-radius: 8; -fx-border-width: 1.5; " +
                      "-fx-padding: 10 15; -fx-text-fill: #333333;");
        field.setMinHeight(40);
        return field;
    }

    private TextArea createStyledTextArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(3);
        area.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; " +
                     "-fx-background-color: #faf5ff; -fx-border-color: #e0d0eb; " +
                     "-fx-border-radius: 8; -fx-border-width: 1.5; " +
                     "-fx-padding: 10 15; -fx-text-fill: #333333;");
        area.setMinHeight(80);
        return area;
    }
}