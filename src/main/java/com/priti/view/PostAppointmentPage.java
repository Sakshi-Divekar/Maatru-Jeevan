package com.priti.view;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class PostAppointmentPage {

    public Scene getScene(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #fffaf0, #fef5e7);");

        // Back button
        Button back = new Button("← Back to Home");
        back.setOnAction(e -> stage.setScene(new PostHomePage().getScene(stage)));
        back.setStyle("-fx-background-color: #f9cfcf; -fx-text-fill: #6e0e0a; -fx-font-weight: bold;");
        HBox backBox = new HBox(back);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(10, 0, 0, 10));

        Label title = new Label("Suggested Doctors");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#D97706"));

        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(10));

        // Child Doctors Section
        Label childTitle = new Label("👶 Child Specialists");
        childTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        childTitle.setTextFill(Color.web("#34495e"));

        TilePane childDoctorsPane = new TilePane();
        childDoctorsPane.setHgap(20);
        childDoctorsPane.setVgap(20);
        childDoctorsPane.setPrefColumns(4);
        childDoctorsPane.setAlignment(Pos.TOP_CENTER);

        // Collect child cards for animation
        List<VBox> childCards = new ArrayList<>();
        childCards.add(createCard("Dr. Anshu Sethi", "MBBS, MD, DM (Paediatrics & Neonatology)", "Apollo Clinic, Viman Nagar, Pune", "₹700 via Apollo 24|7 / Practo", "#fde2e4"));
        childCards.add(createCard("Dr. Saurabh Sharma", "MBBS, MD (Paediatrics)", "Blooming Tads, Paud Road, Pune", "₹800 via Apollo 24|7", "#f9dcc4"));
        childCards.add(createCard("Dr. Sameer Mhatre", "MBBS, MD (Paediatrics)", "Apollo Clinic, Viman Nagar", "₹700 via Apollo 24|7 / Practo", "#d0f4de"));
        childCards.add(createCard("Dr. Rahul Kallianpur", "MBBS, likely DCH/DNB", "Nurture Child Care, Baner", "₹600 via Practo / ClinicSpots", "#cceabb"));
        childCards.add(createCard("Dr. Chandrashekhar Phadnis", "MD (Paediatrics)", "AV Children Clinic, Aundh", "₹500 via Practo", "#e4f9f5"));
        childCards.add(createCard("Dr. Amol Saswade", "MBBS, MD (Paediatrics)", "Children’s Clinic, Wagholi", "₹500 via Practo", "#f9e7e7"));
        childCards.add(createCard("Dr. Suryawanshi Pradip", "MBBS, MD (Paediatrics)", "Sahyadri Hospital, Nagar Road", "₹600 via Practo", "#f6dfeb"));
        childCards.add(createCard("Dr. Lalit Rawal", "MBBS, 40 yrs exp", "Rawal's Children Clinic, Sadashiv Peth", "₹500 via Practo", "#f4edcc"));
        childCards.add(createCard("Dr. Anupama Sen", "MBBS, DCH – 40 yrs exp", "Apollo Clinic, Wanowrie", "₹750 via Apollo / Lybrate", "#d7e3fc"));
        childCards.add(createCard("Dr. Manish Ramteke", "MBBS, 18 yrs exp", "Apollo Clinic, Aundh/Shivajinagar", "₹800 via Practo", "#fbe4d8"));

        childDoctorsPane.getChildren().addAll(childCards);

        // Postpartum Doctors Section
        Label momTitle = new Label("🤱 Post Pregnancy Specialists");
        momTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        momTitle.setTextFill(Color.web("#34495e"));

        TilePane momDoctorsPane = new TilePane();
        momDoctorsPane.setHgap(20);
        momDoctorsPane.setVgap(20);
        momDoctorsPane.setPrefColumns(4);
        momDoctorsPane.setAlignment(Pos.TOP_CENTER);

        // Collect mom cards for animation
        List<VBox> momCards = new ArrayList<>();
        momCards.add(createCard("Dr. Vaishali Chavan", "MBBS, DGO", "Saanvi Clinic, Wanowrie, Pune", "+91 98500 58408", "#ffc9de"));
        momCards.add(createCard("Dr. Mini Salunkhe", "MBBS, Obstetrics & Gynae, 38+ yrs", "Sahyadri Super Speciality Hospital, Hadapsar", "~₹500–₹600", "#ffeaa7"));
        momCards.add(createCard("Dr. Meenu Agarwal", "MBBS, MD, FRCOG (London)", "Ruby Hall Clinic, Sasoon/Deccan Road", "~₹1200–₹1500", "#c7ecee"));
        momCards.add(createCard("Dr. Swatee Gaggare", "MBBS, MS, DNB, Dip Repro Med", "Manipal Hospital, Baner", "~₹700–₹800", "#dff9fb"));

        momDoctorsPane.getChildren().addAll(momCards);

        contentBox.getChildren().addAll(childTitle, childDoctorsPane, momTitle, momDoctorsPane);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(backBox, title, scrollPane);

        // Animate all cards with scale transition entry effect (cascade)
        List<VBox> allCards = new ArrayList<>();
        allCards.addAll(childCards);
        allCards.addAll(momCards);
        animateCardsScale(allCards);

        return new Scene(root, 1550, 800);
    }

    /**
     * Animate each card growing from small to normal size in sequence.
     */
    private void animateCardsScale(java.util.List<VBox> cards) {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);
            card.setScaleX(0.65);
            card.setScaleY(0.65);
            card.setOpacity(0);

            ScaleTransition scale = new ScaleTransition(Duration.millis(340), card);
            scale.setFromX(0.65);
            scale.setFromY(0.65);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fade = new FadeTransition(Duration.millis(340), card);
            fade.setFromValue(0);
            fade.setToValue(1);

            ParallelTransition entry = new ParallelTransition(scale, fade);
            entry.setDelay(Duration.millis(i * 100));
            entry.play();
        }
    }

    private VBox createCard(String name, String degree, String address, String contact, String bgColor) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(15));
        box.setPrefWidth(300);
        box.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");
        box.setAlignment(Pos.TOP_LEFT);

        DropShadow defaultShadow = new DropShadow(10, Color.rgb(0, 0, 0, 0.15));
        box.setEffect(defaultShadow);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 15));

        Label degLabel = new Label(degree);
        degLabel.setFont(Font.font("Poppins", 13));

        Label addLabel = new Label(address);
        addLabel.setFont(Font.font("Poppins", 13));
        addLabel.setWrapText(true);

        Label conLabel = new Label("Contact: " + contact);
        conLabel.setFont(Font.font("Poppins", 13));
        conLabel.setTextFill(Color.web("#2e86de"));

        box.getChildren().addAll(nameLabel, degLabel, addLabel, conLabel);

        // Hover effect with zoom using ScaleTransition
        box.setOnMouseEntered(e -> {
            DropShadow hoverShadow = new DropShadow(20, Color.rgb(0, 0, 0, 0.4));
            box.setEffect(hoverShadow);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), box);
            st.setToX(1.11);
            st.setToY(1.11);
            st.play();
        });

        box.setOnMouseExited(e -> {
            box.setEffect(defaultShadow);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), box);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        return box;
    }
}