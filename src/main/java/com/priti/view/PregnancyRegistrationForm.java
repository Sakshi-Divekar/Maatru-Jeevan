package com.priti.view;

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

import java.util.HashMap;
import java.util.Map;

public class PregnancyRegistrationForm {
    Stage primaryStage;
    
    private static String userEmail;
String setuserEmail(String userEmail ){
    this.userEmail=userEmail;
    return  userEmail;

}
    

    public void show(Stage stage1) {
        this.primaryStage = stage1;
        showSignupScene();
    }

    private void showSignupScene() {
        // Logo
ImageView mjLogo = new ImageView(new Image("/assets/images/final_logo.jpg"));
mjLogo.setFitWidth(110);
mjLogo.setFitHeight(110);
mjLogo.setClip(new Circle(55, 55, 55));

// Title
Label appName = new Label("Maatru-Jeevan");
appName.setFont(Font.font(35));
appName.setStyle("-fx-font-weight: bold; -fx-text-fill: #0e0c0cff;");

// Wrap both in VBox to center together
VBox titleBox = new VBox(5, mjLogo, appName);
titleBox.setAlignment(Pos.CENTER);


        Label tagline = new Label("A SMART Maternal & Child CARE Assistant");
        tagline.setStyle("-fx-text-fill: #0e0c0cff; -fx-font-size: 14px;");
        tagline.setAlignment(Pos.CENTER);

        Label usernameLabel = createStyledLabel("Username:");
        TextField usernameField = new TextField();

        Label userEmailLabel = createStyledLabel("Email:");
        TextField userEmailField = new TextField();
        userEmail=userEmailField.getText();

        Label passwordLabel = createStyledLabel("Password:");
        PasswordField passwordField = new PasswordField();

        Label stageLabel = createStyledLabel("Select Stage:");

        ComboBox<String> stageCombo = new ComboBox<>();
        stageCombo.getItems().addAll("Pre-Delivery", "Post-Delivery");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: #EFB6C8; -fx-text-fill: white; -fx-font-weight: bold;");
        signupBtn.setFont(Font.font(16));
        //signupBtn.setPrefWidth(300);

        signupBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String userEmail = userEmailField.getText().trim();
            String password = passwordField.getText().trim();
            String selectedStage = stageCombo.getValue();

            if (username.isEmpty() || userEmail.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields");
                alert.showAndWait();
                return;
            }

            if (selectedStage == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a stage.");
                alert.showAndWait();
                return;
            }

            AuthController authController = new AuthController();
            boolean registrationSuccess = authController.registerUser(username, userEmail, password, selectedStage);

            if (registrationSuccess) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Registration successful!");
                successAlert.showAndWait();

                UserSession.setUserEmail(userEmail); // <- Make sure this is set only once and early


                if ("Pre-Delivery".equals(selectedStage)) {
                    new PreDeliveryForm(userEmail).show(primaryStage);
                } else if ("Post-Delivery".equals(selectedStage)) {
                    new PostDeliveryForm(userEmail).show(primaryStage);
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Registration failed. userEmail may already be in use.");
                errorAlert.showAndWait();
            }
        });


        System.out.println("sign up"+ userEmail);

        Button backButton = new Button("Go Back");
        backButton.setStyle("-fx-background-color: #FBCFE8; -fx-text-fill: #4B2C2C; -fx-font-weight: bold;");
        backButton.setFont(Font.font(14));
        //backButton.setPrefWidth(300);

        backButton.setOnAction(e -> new LandingPage(primaryStage).start(primaryStage));


        String signupButtonStyle = """
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


signupBtn.setStyle(signupButtonStyle);
backButton.setStyle(backButtonStyle);


// Pop effect on click (without flicker)
signupBtn.setOnMousePressed(e -> signupBtn.setTranslateY(2));
signupBtn.setOnMouseReleased(e -> signupBtn.setTranslateY(0));

backButton.setOnMousePressed(e -> backButton.setTranslateY(2));
backButton.setOnMouseReleased(e -> backButton.setTranslateY(0));








        Hyperlink signinLink = new Hyperlink("Already have an account? Sign In");
        signinLink.setStyle("-fx-text-fill: #dddddd;");
        signinLink.setOnAction(e -> showLoginScene());

VBox formBox = new VBox(18, titleBox, tagline, usernameLabel, usernameField,
        userEmailLabel, userEmailField, passwordLabel, passwordField,
        stageLabel, stageCombo, signupBtn, backButton, signinLink);

formBox.setAlignment(Pos.CENTER_LEFT);
formBox.setPadding(new Insets(40));
formBox.setSpacing(15); // Matches SignInPage
formBox.setMaxWidth(600);
formBox.setPrefWidth(600); // Ensures it doesn’t shrink
formBox.setStyle("-fx-background-color: #A53860; -fx-background-radius: 20;");



        VBox leftImageColumn = new VBox(20);
        leftImageColumn.setAlignment(Pos.CENTER);
        leftImageColumn.getChildren().addAll(
                createStyledImage("/assets/images/pre.jpg", 300, 300),
                createStyledImage("/assets/images/shoes.jpg", 300, 300)
        );

        VBox centerImageColumn = new VBox(20);
        centerImageColumn.setAlignment(Pos.CENTER);
        centerImageColumn.getChildren().addAll(
                createStyledImage("/assets/images/5month.jpg", 300, 300)
        );

        VBox rightImageColumn = new VBox(20);
        rightImageColumn.setAlignment(Pos.CENTER);
        rightImageColumn.getChildren().addAll(
                createStyledImage("/assets/images/babycare.jpg", 300, 300),
                createStyledImage("/assets/images/i3.jpg", 300, 300)
        );

        HBox imageSection = new HBox(25, leftImageColumn, centerImageColumn, rightImageColumn);
        imageSection.setAlignment(Pos.CENTER);

        HBox root = new HBox(60, formBox, imageSection);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to right, #EC7FA9, #FFEDFA);");

        Scene scene = new Scene(root, 1550, 800);

        primaryStage.setTitle("Maatrutwa Setu - Maternal & Child Care");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginScene() {
        SignInPage loginPage = new SignInPage();
        Scene loginScene = loginPage.buildLoginScene(primaryStage, null);
        primaryStage.setScene(loginScene);
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
}