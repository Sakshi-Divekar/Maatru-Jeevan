package com.priti.view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HomeCuresPage {

    private final Stage stage;
    private final VBox root = new VBox();

    // Pastel colors for home cure boxes, for visual variety
    private final String[] kadhaColors = {
            "linear-gradient(to bottom right, #FFE5F0, #FFC8DD)", // Soft pink
            "linear-gradient(to bottom right, #FFF9C4, #FFF59D)", // Light yellow
            "linear-gradient(to bottom right, #B2EBF2, #81D4FA)", // Soft cyan
            "linear-gradient(to bottom right, #C8E6C9, #A5D6A7)", // Pastel green
            "linear-gradient(to bottom right, #F8BBD0, #F48FB1)", // Pastel rose
            "linear-gradient(to bottom right, #FFCCBC, #FFAB91)", // Pastel peach
            "linear-gradient(to bottom right, #D1C4E9, #B39DDB)", // Pastel violet
            "linear-gradient(to bottom right, #FFE082, #FFD54F)"  // Pastel gold
    };

    // Pastel colors for tips boxes
    private final String[] tipsColors = {
            "#EAEFFF", "#FFE5F0", "#DFF0E0", "#FFFAD9", "#FFD8D8", "#DFEFF9"
    };

    // Instagram video links
    private final String[] instaLinks = {
            "https://www.instagram.com/reel/DMAhtojNpq4/?igsh=a2F0MW13ZmR6eHNn",
            "https://www.instagram.com/reel/DLUkX4RtGOv/?igsh=ZG03MXg5NGRsMnp6",
            "https://www.instagram.com/reel/DMXk0bHNFIG/?igsh=MXdjN3QwdTJtbHJ1Yw==",
            "https://www.instagram.com/reel/DLg0JR4NnE9/?igsh=Y2R0amlhNndzb2Ry",
            "https://www.instagram.com/reel/DMA5RlONlXs/?igsh=MTExdXV1NWd1bjQxdQ==",
            "https://www.instagram.com/reel/DMA5RlONlXs/?igsh=MTExdXV1NWd1bjQxdQ=="
    };

    public HomeCuresPage(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    public Scene getScene() {
        return new Scene(root, 1550, 800);
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fffafc, #fef5e6 80%, #f0fff0 100%);");
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        // Centered main title label
        Label title = new Label("Traditional Home Cures for Pregnancy ");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 38));
        title.setTextFill(Color.web("#E36414"));

        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 15, 0));

        // Back button below title, aligned left
        Button backButton = new Button("⬅ Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", 16));
        backButton.setStyle("-fx-background-color: #F48C06; -fx-background-radius: 20;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(e -> {
            TraditionaltipsAndRemediesPage remediesPage = new TraditionaltipsAndRemediesPage(stage);
            stage.setScene(remediesPage.getScene());
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.CENTER_LEFT);
        backButtonBox.setPadding(new Insets(0, 0, 10, 0));

        // Create home cure boxes with kid-friendly pastel colors (gradients)
        List<VBox> cureBoxes = new ArrayList<>();
        cureBoxes.add(cureBox("1. Ginger", kadhaColors[0],
                "Use: Ginger tea or small amounts of ginger to help reduce nausea, morning sickness, and vomiting common in early pregnancy.\n\n"
                        + "Safety: Generally safe in moderate amounts (less than 1000 mg per day). It has been shown to relieve symptoms of nausea effectively without significant adverse effects.\n\n"
                        + "Preparation: Brew fresh ginger slices in hot water for 5-10 minutes."));
        cureBoxes.add(cureBox("2. Peppermint", kadhaColors[1],
                "Use: Used to relieve nausea, indigestion, and flatulence.\n\n"
                        + "Forms: Peppermint tea, mint in drinks, or peppermint aromatherapy.\n\n"
                        + "Safety: Use in moderation as excessive amounts may cause uterine contractions."));
        cureBoxes.add(cureBox("3. Chamomile", kadhaColors[2],
                "Use: Tea for relaxation and better sleep.\n\n"
                        + "Safety: Generally safe in small doses; consult a healthcare provider before use."));
        cureBoxes.add(cureBox("4. Red Raspberry Leaf", kadhaColors[3],
                "Use: Traditionally used for toning the uterus and easing labor.\n\n"
                        + "Intake: Usually from 2nd trimester onwards.\n\n"
                        + "Safety: Consult healthcare provider prior to use."));
        cureBoxes.add(cureBox("5. Cranberry", kadhaColors[4],
                "Use: Juice to prevent urinary tract infections during pregnancy.\n\n"
                        + "Safety: Opt for fresh and low sugar juices."));
        cureBoxes.add(cureBox("6. Ashwagandha", kadhaColors[5],
                "Use: May help with stress reduction in late pregnancy.\n\n"
                        + "Safety: Only under medical supervision."));
        cureBoxes.add(cureBox("7. Herbal Kadha", kadhaColors[6],
                "Use: Combination of herbs like ginger, tulsi, ajwain, turmeric, gulkand for digestion and immunity.\n\n"
                        + "Caution: Use cautiously; avoid excessive use especially early pregnancy."));
        cureBoxes.add(cureBox("8. Dietary & Lifestyle", kadhaColors[7],
                "Maintain balanced diet rich in fruits & vegetables.\n\n"
                        + "Stay hydrated, gentle exercise, adequate rest.\n\n"
                        + "Avoid caffeine, alcohol, unpasteurized products."));

        // GridPane for home cure boxes, 2 per row, uniform size
        GridPane cureGrid = new GridPane();
        cureGrid.setHgap(30);
        cureGrid.setVgap(40);
        cureGrid.setPadding(new Insets(10, 40, 20, 40));
        cureGrid.setAlignment(Pos.TOP_CENTER);

        int uniformWidth = 680;
        int uniformHeight = 240;

        for (int i = 0; i < cureBoxes.size(); i++) {
            VBox b = cureBoxes.get(i);
            b.setPrefSize(uniformWidth, uniformHeight);
            cureGrid.add(b, i % 2, i / 2);
        }

        // Section label for Old Tips - Instagram videos, centered horizontally
        Label tipsLabel = new Label("Old Tips (Instagram Videos)");
        tipsLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 30));
        tipsLabel.setTextFill(Color.web("#4E65AF"));
        tipsLabel.setPadding(new Insets(15, 0, 8, 0));

        HBox tipsLabelBox = new HBox(tipsLabel);
        tipsLabelBox.setAlignment(Pos.CENTER);

        // GridPane for tips boxes (3 per row, 2 rows)
        GridPane tipsGrid = new GridPane();
        tipsGrid.setHgap(30);
        tipsGrid.setVgap(28);
        tipsGrid.setPadding(new Insets(10, 40, 15, 40));
        tipsGrid.setAlignment(Pos.TOP_CENTER);

        String[] tipTitles = {
                "BackPain Relief", "Walk Routine", "Green Diet",
                "Sleep Tips", "Immunity Boost", "Baby Care"
        };

        for (int i = 0; i < instaLinks.length; i++) {
            tipsGrid.add(oldTipBox(tipTitles[i], tipsColors[i], instaLinks[i]), i % 3, i / 3);
        }

        VBox mainContent = new VBox(25, cureGrid, tipsLabelBox, tipsGrid);
        mainContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().clear();
        root.getChildren().addAll(titleBox, backButtonBox, scrollPane);
    }

    private VBox cureBox(String heading, String bgGradient, String content) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(24));
        box.setAlignment(Pos.TOP_LEFT);
        box.setStyle(
                "-fx-background-color: " + bgGradient + ";" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 3, 3);"
        );

        Label headLabel = new Label(heading);
        headLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 22));
        headLabel.setTextFill(Color.web("#582F6C"));
        headLabel.setWrapText(true);

        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        contentLabel.setTextFill(Color.web("#3A3A3A"));
        contentLabel.setWrapText(true);

        box.getChildren().addAll(headLabel, contentLabel);
        return box;
    }

    private VBox oldTipBox(String title, String bgColor, String url) {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(15, 20, 15, 20));
        box.setPrefSize(250, 130);
        box.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 22;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 7, 0, 2, 2);"
        );

        Label label = new Label(title);
        label.setWrapText(true);
        label.setFont(Font.font("Arial Rounded MT Bold", FontWeight.SEMI_BOLD, 16));
        label.setTextFill(Color.web("#294060"));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);

        Button btn = new Button("▶ Watch Video");
        btn.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 15));
        btn.setStyle("-fx-background-color: #E34A70; -fx-text-fill: white; -fx-background-radius: 20;");
        btn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        box.getChildren().addAll(label, btn);

        return box;
    }
}