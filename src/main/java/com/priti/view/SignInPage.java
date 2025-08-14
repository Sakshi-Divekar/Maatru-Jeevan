package com.priti.view;

import  com.priti.utils.UserSession;

import com.priti.controller.AuthController;
import com.priti.utils.UserSession;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;



import java.util.Map;

public class SignInPage {
    public Scene buildLoginScene(Stage stage, Runnable backToSignup) {

ImageView mjLogo = new ImageView(new Image("/assets/images/final_logo.jpg"));
mjLogo.setFitWidth(110);
mjLogo.setFitHeight(110);
mjLogo.setClip(new Circle(55, 55, 55)); // Circular crop

Label appName = new Label("Maatru-Jeevan");
appName.setFont(Font.font(35));
appName.setStyle("-fx-font-weight: bold; -fx-text-fill: #0e0c0cff;");
appName.setPadding(new Insets(10));

// Wrap in a VBox
VBox logoBox = new VBox(5, mjLogo, appName); // 10 is spacing
logoBox.setAlignment(Pos.CENTER);
logoBox.setPadding(new Insets(10));

        Label tagline = new Label("Welcome Back! Please log in.");
        tagline.setStyle("-fx-text-fill: #000000aa; -fx-font-size: 18px;");
        tagline.setMinHeight(20);

        Label userEmailLabel = createStyledLabel("Email:");
        TextField userEmailField = new TextField();

        Label passwordLabel = createStyledLabel("Password:");
        PasswordField passwordField = new PasswordField();

        Label stageLabel = createStyledLabel("Select Stage:");
        ComboBox<String> stageCombo = new ComboBox<>();
        stageCombo.getItems().addAll("Pre-Delivery", "Post-Delivery");
        stageCombo.getSelectionModel().selectFirst(); // Select first option by default

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(250);
        //loginBtn.setStyle("-fx-background-color: #EFB6C8; -fx-text-fill: white; -fx-font-weight: bold;");


        loginBtn.setOnAction(e -> {
    String email = userEmailField.getText().trim();
    String password = passwordField.getText().trim();
    String selectedStage = stageCombo.getValue();

    if (email.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please enter both email and password");
        return;
    }

    if (selectedStage == null) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please select a stage");
        return;
    }

    try {
        AuthController authController = new AuthController();
        Map<String, Object> userData = authController.loginUser(email, password);

        if (userData != null) {
            System.out.println("Logged in: " + email);

            // ✅ Set the user email globally for later use
            UserSession.setUserEmail(email);

            if ("Pre-Delivery".equals(selectedStage)) {
                new PreDeliveryForm(email).show(stage);
            } else if ("Post-Delivery".equals(selectedStage)) {
                new PostDeliveryForm(email).show(stage);
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid credentials");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Login failed: " + ex.getMessage());
    }
});


        
        Button backButton = new Button("Go Back");
        backButton.setPrefWidth(250);
        //backButton.setStyle("-fx-background-color: #FBCFE8; -fx-text-fill: #4B2C2C; -fx-font-weight: bold;");
        backButton.setOnAction(e -> new LandingPage(stage).start(stage));

        Hyperlink backLink = new Hyperlink("Don't have an account? Sign Up");
        backLink.setStyle("-fx-text-fill: #dddddd;");
        backLink.setOnAction(e -> {
            if (backToSignup != null) {
                backToSignup.run();
            } else {
                new PregnancyRegistrationForm().show(stage);
            }
        });


        // === Reusable button styles ===
String loginButtonStyle = """
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

loginBtn.setStyle(loginButtonStyle);
backButton.setStyle(backButtonStyle);


loginBtn.setOnMousePressed(e -> loginBtn.setTranslateY(2));
loginBtn.setOnMouseReleased(e -> loginBtn.setTranslateY(0));

backButton.setOnMousePressed(e -> backButton.setTranslateY(2));
backButton.setOnMouseReleased(e -> backButton.setTranslateY(0));



        

        VBox formBox = new VBox(18, logoBox, tagline, userEmailLabel, userEmailField,
                passwordLabel, passwordField, stageLabel, stageCombo, loginBtn, backButton, backLink);

formBox.setAlignment(Pos.CENTER_LEFT);
formBox.setPadding(new Insets(40));
formBox.setSpacing(15); // Matches SignInPage
formBox.setMaxWidth(600);
formBox.setPrefWidth(600); // Ensures it doesn’t shrink
formBox.setStyle("-fx-background-color: #A53860; -fx-background-radius: 20;");

        // Image columns setup
        VBox leftImageColumn = createImageColumn("/Assets/Images/pre.jpg", "/Assets/Images/shoes.jpg");
        VBox centerImageColumn = createImageColumn("/Assets/Images/5month.jpg");
        VBox rightImageColumn = createImageColumn("/Assets/Images/babycare.jpg", "/Assets/Images/i3.jpg");

        HBox imageSection = new HBox(25, leftImageColumn, centerImageColumn, rightImageColumn);
        imageSection.setAlignment(Pos.CENTER);

        HBox root = new HBox(60, formBox, imageSection);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to right, #EC7FA9, #FFEDFA);");

        return new Scene(root, 1550, 800);
    }

    private VBox createImageColumn(String... imagePaths) {
        VBox column = new VBox(20);
        column.setAlignment(Pos.CENTER);
        for (String path : imagePaths) {
            column.getChildren().add(createStyledImage(path, 300, 300));
        }
        return column;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font(18));
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff;");
        return label;
    }

    private ImageView createStyledImage(String imagePath, int width, int height) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        Circle clip = new Circle(width / 2.0, height / 2.0, Math.min(width, height) / 2.0);
        imageView.setClip(clip);

        DropShadow shadow = new DropShadow(10, Color.web("#999999"));
        imageView.setEffect(shadow);

        return imageView;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void show(Stage primaryStage) {
        Scene scene = buildLoginScene(primaryStage, null);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}