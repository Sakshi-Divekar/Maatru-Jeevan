package com.priti.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.AuthController;
import com.priti.controller.FirebaseStorageService;
import com.priti.utils.UserSession;
import com.priti.view.PreProfilePage;

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

public class PreDeliveryForm {
    private String userEmail; // ✅ Add this
    private File selectedImageFile; // ✅ For storing selected profile image
    private ImageView profileImageView; // ✅ For displaying selected profile picture

   
    private TextField motherNameField;
    private TextField ageField;
    private TextField pregnancyStartDateField;
    private TextField dueDateField;
    private TextField gravidaStatusField;
    private TextField medicalHistoryField;
    private TextField doctorNameField;
    private TextField contactNoField;
    private TextField emergencyContactField;
    private TextField addressField;
    private Stage primaryStage;

public PreDeliveryForm(String email) {
    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("❌ Email cannot be null or empty");
    }
    this.userEmail = email; // ✅ Store email
    

        
        // Initialize all fields in constructor
        motherNameField = new TextField();
        ageField = new TextField();
        pregnancyStartDateField = new TextField();
        dueDateField = new TextField();
        gravidaStatusField = new TextField();
        medicalHistoryField = new TextField();
        doctorNameField = new TextField();
        contactNoField = new TextField();
        emergencyContactField = new TextField();
        addressField = new TextField();
    }

    public void show(Stage stage) {
        // Load existing data after fields are initialized
        loadExistingData();

        Label titleLabel = new Label("Pre-Delivery Information");
        titleLabel.setFont(Font.font(30));
        titleLabel.setStyle("-fx-text-fill: #4B2C2C; -fx-font-weight: bold;");

        VBox questionsBox = new VBox(15);
        questionsBox.setAlignment(Pos.CENTER_LEFT);

        Label profilePicLabel = new Label("Profile Picture:");
        profilePicLabel.setFont(Font.font("Poppins", 10));
        Button uploadButton = new Button("Upload Picture");
        profileImageView = new ImageView();
profileImageView.setFitWidth(150);
profileImageView.setFitHeight(150);
profileImageView.setPreserveRatio(true);
profileImageView.setEffect(new DropShadow(10, Color.LIGHTPINK));

       uploadButton.setOnAction(e -> {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Profile Picture");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
    );
    selectedImageFile = fileChooser.showOpenDialog(stage);

    if (selectedImageFile != null) {
        Image image = new Image(selectedImageFile.toURI().toString());
        profileImageView.setImage(image);
    }
});

        uploadButton.setStyle("-fx-background-color: #F9A8D4; -fx-text-fill: #4B2C2C; -fx-font-weight: bold;");

        VBox imageUploadBox = new VBox(10, profilePicLabel, uploadButton, profileImageView);
imageUploadBox.setAlignment(Pos.CENTER_LEFT);
questionsBox.getChildren().add(imageUploadBox);



        questionsBox.getChildren().addAll(
                
                createQuestion("Mother's Name:", motherNameField),
                createQuestion("Age / Date of Birth:", ageField),
                createQuestion("Pregnancy Start Date:", pregnancyStartDateField),
                createQuestion("Expected Due Date:", dueDateField),
                createQuestion("Gravida Status (1st, 2nd, 3rd):", gravidaStatusField),
                createQuestion("Medical History (Diabetes, BP, Thyroid, etc.):", medicalHistoryField),
                createQuestion("Doctor's Name:", doctorNameField),
                createQuestion("Contact No.:", contactNoField),
                createQuestion("Emergency Contact No. (Husband No.):", emergencyContactField),
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
        submitButton.setFont(Font.font(16));
        submitButton.setPrefWidth(250);

        submitButton.setOnAction(e -> {
            if (validateForm()) {
                Map<String, Object> preDeliveryData = new HashMap<>();
                preDeliveryData.put("motherName", motherNameField.getText().trim());
                preDeliveryData.put("age", ageField.getText().trim());
                preDeliveryData.put("pregnancyStartDate", pregnancyStartDateField.getText().trim());
                preDeliveryData.put("dueDate", dueDateField.getText().trim());
                preDeliveryData.put("gravidaStatus", gravidaStatusField.getText().trim());
                preDeliveryData.put("medicalHistory", medicalHistoryField.getText().trim());
                preDeliveryData.put("doctorName", doctorNameField.getText().trim());
                preDeliveryData.put("contactNo", contactNoField.getText().trim());
                preDeliveryData.put("emergencyContact", emergencyContactField.getText().trim());
                preDeliveryData.put("address", addressField.getText().trim());
                

                Firestore db = FirestoreClient.getFirestore();
DocumentReference docRef = db
    .collection("users")
    .document(userEmail)
    .collection("preDeliveryData")
    .document("details");

// ✅ Upload image if selected
if (selectedImageFile != null) {
    FirebaseStorageService storageService = new FirebaseStorageService();
    String imageUrl = storageService.uploadFile(selectedImageFile, "profile_images/" + userEmail + ".jpg");
    preDeliveryData.put("imageUrl", imageUrl);
}

ApiFuture<WriteResult> result = docRef.set(preDeliveryData, SetOptions.merge());

result.addListener(() -> {
    Platform.runLater(() -> {
        showAlert(Alert.AlertType.INFORMATION, "Success", "Data saved successfully!");
        navigateToPreHome(stage, userEmail);
    });
}, Runnable::run);

            }
            
        });

        submitButton.setOnMousePressed(e -> submitButton.setTranslateY(2));
submitButton.setOnMouseReleased(e -> submitButton.setTranslateY(0));



        System.out.println("In form"+ userEmail);

        Button backButton = new Button("Go Back");
        backButton.setStyle(backButtonStyle);
        backButton.setFont(Font.font(14));
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

        VBox scrollContainer = new VBox(scrollPane);
        scrollContainer.setAlignment(Pos.CENTER);

        VBox leftImages = new VBox(30,
                createImageBox("/assets/images/post_form2.jpg", 200, 200),
                createImageBox("/assets/images/post_form1.jpg", 200, 200));
        leftImages.setAlignment(Pos.CENTER);

        VBox rightImages = new VBox(30,
                createImageBox("/assets/images/pre_form2.jpg", 200, 200),
                createImageBox("/assets/images/pre_form5.jpg", 200, 200));
        rightImages.setAlignment(Pos.CENTER);

        HBox root = new HBox(30, leftImages, scrollContainer, rightImages);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to right, #FBCFE8, #FFE4E6);");

        Scene scene = new Scene(root, 1550, 800);
        stage.setScene(scene);
        stage.setTitle("Pre-Delivery Form");
        stage.show();
    }


    


    private void loadExistingData() {
    AuthController authController = new AuthController();
    Map<String, Object> preDeliveryData = authController.getPreDeliveryData(userEmail);
    
    if (preDeliveryData != null && !preDeliveryData.isEmpty()) {
        // Safely set text on initialized fields
        if (motherNameField != null) motherNameField.setText((String) preDeliveryData.getOrDefault("motherName", ""));
        if (ageField != null) ageField.setText((String) preDeliveryData.getOrDefault("age", ""));
        if (pregnancyStartDateField != null) pregnancyStartDateField.setText((String) preDeliveryData.getOrDefault("pregnancyStartDate", ""));
        if (dueDateField != null) dueDateField.setText((String) preDeliveryData.getOrDefault("dueDate", ""));
        if (gravidaStatusField != null) gravidaStatusField.setText((String) preDeliveryData.getOrDefault("gravidaStatus", ""));
        if (medicalHistoryField != null) medicalHistoryField.setText((String) preDeliveryData.getOrDefault("medicalHistory", ""));
        if (doctorNameField != null) doctorNameField.setText((String) preDeliveryData.getOrDefault("doctorName", ""));
        if (contactNoField != null) contactNoField.setText((String) preDeliveryData.getOrDefault("contactNo", ""));
        if (emergencyContactField != null) emergencyContactField.setText((String) preDeliveryData.getOrDefault("emergencyContact", ""));
        if (addressField != null) addressField.setText((String) preDeliveryData.getOrDefault("address", ""));
      // Load profile image if it exists
        String imageUrl = (String) preDeliveryData.get("imageUrl");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl, true); // true for background loading
                image.errorProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        System.err.println("❌ Failed to load profile image from URL: " + imageUrl);
                    }
                });
                // Only set the image if loading is successful
                image.progressProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.doubleValue() == 1.0 && !image.isError()) {
                        Platform.runLater(() -> profileImageView.setImage(image));
                    }
                });
            } catch (Exception e) {
                System.err.println("❌ Error loading image: " + e.getMessage());
            }
        }
    }
}


    
    

        
    

    private boolean validateForm() {
        if (motherNameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter mother's name");
            return false;
        }
        if (ageField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter age or date of birth");
            return false;
        }
        if (pregnancyStartDateField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter pregnancy start date");
            return false;
        }
        if (dueDateField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter expected due date");
            return false;
        }
        if (gravidaStatusField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter gravida status");
            return false;
        }
        if (contactNoField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter contact number");
            return false;
        }
        if (emergencyContactField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter emergency contact number");
            return false;
        }
        if (addressField.getText().trim().isEmpty()) {
    showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter address");
    return false;
}

        return true;
    }

    private VBox createQuestion(String question, Control inputField) {
        Label label = new Label(question);
        label.setFont(Font.font(10));
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

private void navigateToPreHome(Stage stage, String userEmail) {
    try {
        // ✅ Store the email in global session
        UserSession.setUserEmail(userEmail);
        PreHomePage preHomePage = new PreHomePage(); // ✅ Pass email
        Scene preHomeScene = preHomePage.getScene(stage);
        stage.setScene(preHomeScene);
        stage.setTitle("Maatrutwa Setu - Pre Delivery Home");
    } catch (Exception ex) {
        ex.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to home page: " + ex.getMessage());
    }
}
    
}