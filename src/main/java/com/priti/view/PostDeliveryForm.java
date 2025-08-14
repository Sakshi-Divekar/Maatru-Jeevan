package com.priti.view;

import com.priti.controller.AuthController;
import com.priti.controller.FirebaseStorageService;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostDeliveryForm {
    
    private String userEmail;
    private File selectedImageFile;
    private ImageView profileImageView;
    private TextField motherNameField;
    private TextField recoveryStatusField;
    private TextField babyNameField;
    private TextField dobField;
    private TextField genderField;
    private TextField weightField;
    private TextField vaccinationDateField;
    private TextField feedingTypeField;
    private TextField addressField;
    private FirebaseStorageService storageService;
    

    public PostDeliveryForm(String email) {
        this.userEmail = email;
        this.storageService = new FirebaseStorageService();

        // Initialize fields
        motherNameField = new TextField();
        recoveryStatusField = new TextField();
        babyNameField = new TextField();
        dobField = new TextField();
        genderField = new TextField();
        weightField = new TextField();
        vaccinationDateField = new TextField();
        feedingTypeField = new TextField();
        addressField = new TextField();
        profileImageView = new ImageView();
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(true);
    }

    public void show(Stage stage) {
        loadExistingData();

        Label titleLabel = new Label("Post-Delivery Information");
        titleLabel.setFont(Font.font("Poppins", 30));
        titleLabel.setStyle("-fx-text-fill: #4B2C2C; -fx-font-weight: bold;");

        VBox questionsBox = new VBox(15);
        questionsBox.setAlignment(Pos.CENTER_LEFT);

        Label profileLabel = new Label("Upload Profile Picture:");
        profileLabel.setFont(Font.font("Poppins", 10));

        Button uploadButton = new Button("Choose Image");
        uploadButton.setStyle("-fx-background-color: #F9A8D4; -fx-text-fill: #4B2C2C; -fx-font-weight: bold;");
        uploadButton.setOnAction(e -> handleImageUpload(stage));

        profileImageView.setFitWidth(100);
profileImageView.setFitHeight(100);
profileImageView.setPreserveRatio(true);
VBox profilePicBox = new VBox(5, profileLabel, uploadButton, profileImageView);

        //profileImage.setStyle("-fx-border-color: #4B2C2C; -fx-border-width: 2px; -fx-border-radius: 10px;");
profileImageView.setStyle("-fx-border-color: #4B2C2C; -fx-border-width: 2px; -fx-border-radius: 10px;");


        // Updated question order as per user request
        questionsBox.getChildren().addAll(
            profilePicBox,
            createQuestion("Mother's Name:", motherNameField),
            createQuestion("Mother's Recovery Status (Normal, C-Section):", recoveryStatusField),
            createQuestion("Baby's Name:", babyNameField),
            createQuestion("Date of Birth:", dobField),
            createQuestion("Gender:", genderField),
            createQuestion("Weight:", weightField),
            createQuestion("Vaccination Schedule Start Date:", vaccinationDateField),
            createQuestion("Type of Feeding (Breastfeeding, Formula, Mixed):", feedingTypeField),
            createQuestion("Address (City):", addressField)
        );



        String submitButtonStyle = """
     -fx-background-color: #EFB6C8;
    -fx-font-size: 14px;
    -fx-font-family: 'Segoe UI Semibold';
    -fx-background-radius: 40;
    -fx-padding: 5 80;
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-cursor: hand;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);
""";

String backButtonStyle = """
    -fx-background-color: #FBCFE8;
    -fx-font-size: 14px;
    -fx-font-family: 'Segoe UI Semibold';
    -fx-background-radius: 40;
    -fx-padding: 5 80;
    -fx-text-fill: #4B2C2C;
    -fx-font-weight: bold;
    -fx-cursor: hand;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);
""";

        Button submitButton = new Button("Submit");
        submitButton.setStyle(submitButtonStyle);
        submitButton.setFont(Font.font("Poppins", 16));
        submitButton.setPrefWidth(250);
        submitButton.setOnAction(e -> handleFormSubmission(stage));

        submitButton.setOnMousePressed(e -> submitButton.setTranslateY(2));
submitButton.setOnMouseReleased(e -> submitButton.setTranslateY(0));

        Button backButton = new Button("Go Back");
        backButton.setStyle(backButtonStyle);
        backButton.setFont(Font.font("Poppins", 14));
        backButton.setPrefWidth(250);
        backButton.setOnAction(e -> new SignInPage().show(stage));
         backButton.setOnMousePressed(e -> backButton.setTranslateY(2));
backButton.setOnMouseReleased(e -> backButton.setTranslateY(0));

        HBox buttonBox = new HBox(20, submitButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox formArea = new VBox(20, titleLabel, questionsBox, buttonBox);
        formArea.setAlignment(Pos.CENTER);
        formArea.setPadding(new Insets(40));
        formArea.setMaxWidth(600);
        formArea.setStyle("-fx-background-color: #A53860; -fx-background-radius: 20; -fx-border-color: #A53860; -fx-border-radius: 20;");

        ScrollPane scrollPane = new ScrollPane(formArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");
        scrollPane.setPrefHeight(400);

        VBox leftImages = createImageColumn(
            "/assets/images/1month.jpg",
            "/assets/images/post_form4.jpg"
        );

        VBox rightImages = createImageColumn(
            "/assets/images/pre_leg.jpg",
            "/assets/images/post_form3.jpg"
        );

        HBox root = new HBox(30, leftImages, scrollPane, rightImages);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to right, #FBCFE8, #FFE4E6);");

        Scene scene = new Scene(root, 1550, 800);
        stage.setScene(scene);
        stage.setTitle("Post-Delivery Form");
        stage.show();
    }

    private void loadExistingData() {
    AuthController authController = new AuthController();
    Map<String, Object> postDeliveryData = authController.getPostDeliveryData(userEmail);

    if (postDeliveryData != null && !postDeliveryData.isEmpty()) {
            if (motherNameField != null) motherNameField.setText((String) postDeliveryData.getOrDefault("motherName", ""));
            if (recoveryStatusField != null) recoveryStatusField.setText((String) postDeliveryData.getOrDefault("recoveryStatus", ""));
            if (babyNameField != null) babyNameField.setText((String) postDeliveryData.getOrDefault("babyName", ""));
            if (dobField != null) dobField.setText((String) postDeliveryData.getOrDefault("dateOfBirth", ""));
            if (genderField != null) genderField.setText((String) postDeliveryData.getOrDefault("gender", ""));
            if (weightField != null) weightField.setText((String) postDeliveryData.getOrDefault("weight", ""));
            if (vaccinationDateField != null) vaccinationDateField.setText((String) postDeliveryData.getOrDefault("vaccinationStartDate", ""));
            if (feedingTypeField != null) feedingTypeField.setText((String) postDeliveryData.getOrDefault("feedingType", ""));
            if (addressField != null) addressField.setText((String) postDeliveryData.getOrDefault("address", ""));

            // Always try to load image if URL exists (even if empty)
        String imageUrl = (String) postDeliveryData.get("imageUrl");
        if (imageUrl != null) {
            try {
                Image image = new Image(imageUrl, true);
                image.errorProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        System.out.println("Error loading profile image from URL: " + imageUrl);
                    }
                });
                image.progressProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.doubleValue() == 1.0 && !image.isError()) {
                        Platform.runLater(() -> profileImageView.setImage(image));
                    }
                });
                profileImageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error creating image object: " + e.getMessage());
            }
        }
}

        }
    

    private void handleImageUpload(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Profile Picture");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
    );

    selectedImageFile = fileChooser.showOpenDialog(stage); // Store the selected file
    if (selectedImageFile != null) {
        try {
            profileImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading image: " + e.getMessage());
            selectedImageFile = null; // Reset if error occurs
        }
    }
}
   private void handleFormSubmission(Stage stage) {
    if (validateForm()) {
        Map<String, Object> postDeliveryData = new HashMap<>();
        postDeliveryData.put("motherName", motherNameField.getText().trim());
        postDeliveryData.put("recoveryStatus", recoveryStatusField.getText().trim());
        postDeliveryData.put("babyName", babyNameField.getText().trim());
        postDeliveryData.put("dateOfBirth", dobField.getText().trim());
        postDeliveryData.put("gender", genderField.getText().trim());
        postDeliveryData.put("weight", weightField.getText().trim());
        postDeliveryData.put("vaccinationStartDate", vaccinationDateField.getText().trim());
        postDeliveryData.put("feedingType", feedingTypeField.getText().trim());
        postDeliveryData.put("address", addressField.getText().trim());

        AuthController authController = new AuthController();
        
        // Get existing data to check for imageUrl
        Map<String, Object> existingData = authController.getPostDeliveryData(userEmail);
        String existingImageUrl = existingData != null ? (String) existingData.get("imageUrl") : null;
        
        // Handle image upload
        if (selectedImageFile != null) {
            try {
                // Upload to Firebase Storage
                String imagePath = "post_delivery_images/" + userEmail + ".jpg";
                String imageUrl = storageService.uploadFile(selectedImageFile, imagePath);
                postDeliveryData.put("imageUrl", imageUrl);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to upload profile image: " + e.getMessage());
                return;
            }
        } else if (existingImageUrl != null) {
            // Preserve existing image URL if no new image is selected
            postDeliveryData.put("imageUrl", existingImageUrl);
        }

        boolean success = authController.updatePostDeliveryData(userEmail, postDeliveryData);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Data saved successfully!");
            navigateToPostHome(stage);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data");
        }
    }
}

    private boolean validateForm() {
        if (motherNameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter mother's name");
            return false;
        }
        if (babyNameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter baby's name");
            return false;
        }
        if (dobField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter date of birth");
            return false;
        }
        if (genderField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter gender");
            return false;
        }
        if (weightField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter weight");
            return false;
        }
        if (vaccinationDateField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter vaccination start date");
            return false;
        }
        if (feedingTypeField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter feeding type");
            return false;
        }
        if (addressField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter address (city)");
            return false;
        }
        return true;
    }

    private VBox createImageColumn(String... imagePaths) {
        VBox column = new VBox(20);
        column.setAlignment(Pos.CENTER);
        for (String path : imagePaths) {
            column.getChildren().add(createImageBox(path, 270, 250));
        }
        return column;
    }

    private VBox createQuestion(String question, Control inputField) {
        Label label = new Label(question);
        label.setFont(Font.font("Poppins", 10));
        inputField.setPrefWidth(400);
        return new VBox(5, label, inputField);
    }

    private ImageView createImageBox(String imagePath, int width, int height) {
        try {
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setEffect(new DropShadow(15, Color.LIGHTPINK));
            return imageView;
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            return new ImageView();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToPostHome(Stage stage) {
        try {
            PostHomePage postHomePage = new PostHomePage();
            Scene newScene = postHomePage.getScene(stage);
            stage.setScene(newScene);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to home page: " + e.getMessage());
        }
    }
}
