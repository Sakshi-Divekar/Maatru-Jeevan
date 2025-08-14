package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPage {

    public Scene getScene(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f8f0fc, #fceff9);");

        // Back button at top-left
        Button backBtn = new Button("\u2190 Back to Home");
        backBtn.setStyle("-fx-background-color: #f8d7da; -fx-text-fill: #721c24; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> stage.setScene(new PreHomePage().getScene(stage)));
        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(10, 0, 0, 10));

        Label title = new Label("Recommended Doctors for You 👩‍⚕️");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#6a0572"));
        title.setAlignment(Pos.CENTER);

        FlowPane doctorCards = new FlowPane();
        doctorCards.setHgap(30);
        doctorCards.setVgap(30);
        doctorCards.setPadding(new Insets(10));
        doctorCards.setAlignment(Pos.CENTER);

        // Create doctor card nodes for animation
        List<VBox> cardList = new ArrayList<>();
        cardList.add(createDoctorCard("Dr. Sushrut Dilip Ghaisas", "MBBS, DNB, MRCOG", "Cloudnine Hospital, SB Road, Pune", "+91 99728 99728", "#dfe6e9"));
        cardList.add(createDoctorCard("Dr. Mangala Wani", "MBBS, MD, MRCOG, FRCOG, IBCLC", "Hirkani Clinic, Model Colony, Pune", "ClinicSpots", "#f8c291"));
        cardList.add(createDoctorCard("Dr. Mukta Umarji", "43 years experience", "Umarji Mother & Child Care, Baner, Pune", "ClinicSpots", "#a3cb38"));
        cardList.add(createDoctorCard("Dr. Usha Kiran Gaikwad", "42 years experience", "Ushakiran Hospital, Hadapsar, Pune", "ClinicSpots", "#78e08f"));
        cardList.add(createDoctorCard("Dr. Neelima Agarwal", "38 years experience", "Surya Mother & Child Hospital, Wakad, Pune", "ClinicSpots", "#f6b93b"));
        cardList.add(createDoctorCard("Dr. Jyoti Dekate", "18 years experience", "Rainmist Healthcare, Wakad, Pune", "WhatsApp 73505 71911", "#60a3bc"));
        cardList.add(createDoctorCard("Dr. Rajesh Bawaskar", "18 years experience", "Sairaj Fertility Clinic, Chikhali, Pune", "ClinicSpots", "#82ccdd"));
        cardList.add(createDoctorCard("Dr. Anil Chittake", "MBBS, DNB, DGO", "Embrio IVF Centre, Baner, Pune", "Practo", "#b8e994"));
        cardList.add(createDoctorCard("Dr. Mithil Patil", "23 years experience", "Ashwini Hospital, Balewadi, Pune", "Practo", "#f3a683"));
        cardList.add(createDoctorCard("Dr. Kirti Joglekar", "43 years experience", "Gynaeworld Hospital, Shivajinagar, Pune", "Practo", "#63cdda"));

        doctorCards.getChildren().addAll(cardList);

        ScrollPane scrollPane = new ScrollPane(doctorCards);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(title, backBox, scrollPane);

        // Animate the cards with ScaleTransition on page load
        animateCardsScale(cardList);

        return new Scene(root, 1550, 800);
    }

    /**
     * Animate each doctor card appearing with a scale transition.
     */
    private void animateCardsScale(List<VBox> cards) {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);
            card.setScaleX(0.6);
            card.setScaleY(0.6);
            card.setOpacity(0);

            ScaleTransition scale = new ScaleTransition(Duration.millis(370), card);
            scale.setFromX(0.6);
            scale.setFromY(0.6);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fade = new FadeTransition(Duration.millis(370), card);
            fade.setFromValue(0);
            fade.setToValue(1);

            ParallelTransition entry = new ParallelTransition(scale, fade);
            entry.setDelay(Duration.millis(i * 110)); // Staggered
            entry.play();
        }
    }

    private VBox createDoctorCard(String name, String degree, String clinic, String contact, String bgColor) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(300);
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 20;"
        );

        DropShadow defaultShadow = new DropShadow(20, Color.rgb(0, 0, 0, 0.15));
        DropShadow hoverShadow = new DropShadow(100, Color.rgb(0, 0, 0, 0.4));
        card.setEffect(defaultShadow);

        // Hover effect - scale up and shadow
        card.setOnMouseEntered(e -> {
            card.setScaleX(1.11);
            card.setScaleY(1.11);
            card.setEffect(hoverShadow);
            card.setCursor(javafx.scene.Cursor.HAND);
        });

        // On mouse exit - reset
        card.setOnMouseExited(e -> {
            card.setScaleX(1.0);
            card.setScaleY(1.0);
            card.setEffect(defaultShadow);
        });

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#2d3436"));

        Label degreeLabel = new Label(degree);
        degreeLabel.setFont(Font.font("Poppins", 13));
        degreeLabel.setTextFill(Color.web("#34495e"));

        Label clinicLabel = new Label(clinic);
        clinicLabel.setFont(Font.font("Poppins", 13));
        clinicLabel.setTextFill(Color.web("#2d3436"));
        clinicLabel.setWrapText(true);

        Label contactLabel = new Label("Contact: " + contact);
        contactLabel.setFont(Font.font("Poppins", 13));
        contactLabel.setTextFill(Color.web("#0984e3"));

        card.getChildren().addAll(nameLabel, degreeLabel, clinicLabel, contactLabel);
        return card;
    }
}