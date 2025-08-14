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

public class HerbalTeasPage {

    private final Stage stage;
    private final VBox root = new VBox();

    // Pretty pastel colors for boxes
    private final String[] kadhaColors = {
            "#FFE5E0", "#FFF1B6", "#D2F6CE", "#B7E7FC",
            "#FFDFEC", "#C9F9F6", "#FFF6CC", "#F3DDFE"
    };

    private final String[] tipsColors = {
            "#EAE3FF", "#FFE2F2", "#DEFFF3", "#FFFACC", "#FFD6C9", "#DCE2FE"
    };

    // Instagram links to 6 tips videos
    private final String[] instaLinks = {
            "https://www.instagram.com/p/DMJ8M5ShhZH/?igsh=b3pyajZhYjk5ZmNx",
            "https://www.instagram.com/reel/DMMwYeDNOYE/?igsh=MXRzNXd3OW0xOWdiYw==",
            "https://www.instagram.com/reel/DLXuuiCNZ3m/?igsh=MW5pZ3JkZGh3a3Bq",
            "https://www.instagram.com/reel/DLwqMP_s86s/?igsh=MWw2dW16eXNlanJ0dQ==",
            "https://www.instagram.com/reel/DLy-FWEN68i/?igsh=MXFuMnA0dTFyNDNrbA==",
            "https://www.instagram.com/reel/DMPXJfnNX6G/?igsh=MnJ2NWZvZ3AybnB5"
    };

    public HerbalTeasPage(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    public Scene getScene() {
        // Make scene resizable so ScrollPane can work properly when resizing window
        Scene scene = new Scene(root, 1550, 800);
        return scene;
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fff8fb 0%, #ffeaf5 45%, #e0fefa 100%);");
        root.setPadding(new Insets(20));
        root.setSpacing(22);

        // Section title - centered
        Label title = new Label("Kadhas for Pregnancy 🌿");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 36));
        title.setTextFill(Color.web("#DF3079"));
        title.setPadding(new Insets(8, 0, 14, 0));
        title.setAlignment(Pos.CENTER);
        // Wrap in HBox to center the title, because Label alignment inside VBox can fail
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        // Back button, aligned left below title
        Button backButton = new Button("⬅ Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", 15));
        backButton.setStyle("-fx-background-color: #E58FBD; -fx-background-radius: 20;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(e -> {
            TraditionaltipsAndRemediesPage remediesPage = new TraditionaltipsAndRemediesPage(stage);
            stage.setScene(remediesPage.getScene());
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.setPadding(new Insets(0, 0, 10, 0));

        // Grid for Kadha blocks (2 columns per row)
        GridPane kadhaGrid = new GridPane();
        kadhaGrid.setHgap(32);
        kadhaGrid.setVgap(34);
        kadhaGrid.setPadding(new Insets(10, 20, 15, 16));
        kadhaGrid.setAlignment(Pos.TOP_CENTER);

        List<VBox> kadhaBlocks = new ArrayList<>();
        kadhaBlocks.add(kadhaBlock(
                "🌸 1. Ginger-Tulsi Kadha (For Cold, Cough, Immunity)",
                kadhaColors[0],
                new String[]{
                        "Ingredients: Fresh ginger, tulsi leaves, black pepper, cloves, jaggery",
                        "\u25A0 Boosts immunity",
                        "\u25A0 Relieves cough and sore throat",
                        "\u25A0 Improves digestion",
                        "Intake: \u2714 Safe in 2nd & 3rd trimester, 1–2 times/week",
                        "Avoid: Overuse in 1st trimester (heaty herbs)"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌼 2. Ajwain (Carom Seeds) Kadha (For Digestion & Bloating)",
                kadhaColors[1],
                new String[]{
                        "Ingredients: Ajwain, fennel seeds, rock salt, jeera",
                        "\u25A0 Reduces bloating, gas, acidity",
                        "\u25A0 Enhances digestion",
                        "\u25A0 Relieves nausea",
                        "Intake: \u2714 Safe from 2nd month onward (after meals)"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🍵 3. Shatavari Kadha (For Hormonal Balance & Uterus Strength)",
                kadhaColors[2],
                new String[]{
                        "Ingredients: Shatavari powder, dry ginger, cardamom, jaggery",
                        "\u25A0 Strengthens uterus",
                        "\u25A0 Balances hormones",
                        "\u25A0 Promotes milk production (late pregnancy)",
                        "Intake: \u2714 From 2nd trimester; 2–3/week"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌾 4. Ashwagandha Kadha (For Stress, Sleep & Immunity)",
                kadhaColors[3],
                new String[]{
                        "Ingredients: Ashwagandha powder, cinnamon, nutmeg, milk",
                        "\u25A0 Reduces stress, anxiety",
                        "\u25A0 Improves sleep quality",
                        "\u25A0 Supports immunity",
                        "Intake: Only under doctor supervision (usually 3rd trimester)"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌰 5. Dry Fruit Kadha (For Strength and Energy)",
                kadhaColors[4],
                new String[]{
                        "Ingredients: Almonds, dates, dry coconut, poppy seeds, milk",
                        "\u25A0 Provides strength, stamina",
                        "\u25A0 Rich in iron and calcium",
                        "\u25A0 Supports fetal brain development",
                        "Intake: \u2714 From 4th month, best in winter"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌿 6. Mint-Fennel Kadha (For Nausea and Cooling)",
                kadhaColors[5],
                new String[]{
                        "Ingredients: Mint leaves, fennel seeds, cardamom, coriander seeds",
                        "\u25A0 Relieves morning sickness",
                        "\u25A0 Keeps the body cool",
                        "\u25A0 Soothes the stomach",
                        "Intake: \u2714 1st trimester safe; mild and cooling"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌼 7. Triphala Kadha (For Constipation Relief)",
                kadhaColors[6],
                new String[]{
                        "Ingredients: Triphala powder (Amla, Haritaki, Bibhitaki) boiled in water",
                        "\u25A0 Mild laxative for constipation",
                        "\u25A0 Detoxifies the digestive tract",
                        "Intake: \u2714 Occasional use, 2nd & 3rd trimester",
                        "Caution: Avoid overuse; very diluted form"
                }
        ));
        kadhaBlocks.add(kadhaBlock(
                "🌹 8. Rose-Gulkand Kadha (Cooling, Detoxifying)",
                kadhaColors[7],
                new String[]{
                        "Ingredients: Gulkand, fennel seeds, rose petals, milk",
                        "\u25A0 Reduces body heat",
                        "\u25A0 Prevents acidity",
                        "\u25A0 Calms mood",
                        "Intake: \u2714 Safe from 2nd trimester, best in summer"
                }
        ));

        // Place kadhaBlocks in grid with equal cell size
        for (int i = 0; i < kadhaBlocks.size(); i++) {
            // Ensure uniform size for all boxes
            VBox block = kadhaBlocks.get(i);
            block.setPrefWidth(650);
            block.setPrefHeight(220);
            kadhaGrid.add(block, i % 2, i / 2);
        }

        // Section for Old Tips (Instagram Videos)
        Label tipsLabel = new Label("Old Tips (Instagram Videos)");
        tipsLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 27));
        tipsLabel.setTextFill(Color.web("#6269CF"));
        tipsLabel.setPadding(new Insets(16, 0, 7, 12));
        tipsLabel.setAlignment(Pos.TOP_LEFT);

        GridPane tipsGrid = new GridPane();
        tipsGrid.setHgap(28);
        tipsGrid.setVgap(24);
        tipsGrid.setPadding(new Insets(4, 40, 10, 40));
        tipsGrid.setAlignment(Pos.TOP_CENTER);

        String[] boxTitles = {
                "Home Cure",
                "Daily Routine",
                "Diet Advice",
                "Bright Child",
                "Tips for Healthy Baby",
                "Brain Boosting"
        };

        for (int i = 0; i < 6; i++) {
            tipsGrid.add(oldTipBox(boxTitles[i], tipsColors[i], instaLinks[i]), i % 3, i / 3);
        }

        VBox mainPanel = new VBox(18, kadhaGrid, tipsLabel, tipsGrid);
        mainPanel.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(mainPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().clear();
        root.getChildren().addAll(titleBox, backButtonBox, scrollPane);
    }

    private VBox kadhaBlock(String head, String bgColor, String[] lines) {
        VBox block = new VBox(8);
        block.setPadding(new Insets(22, 22, 16, 20));
        block.setStyle("-fx-background-color: " + bgColor + ";" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 8,0,2,2);");

        Label headingLabel = new Label(head);
        headingLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 22));
        headingLabel.setTextFill(Color.web("#91306F"));
        headingLabel.setWrapText(true);

        VBox detailsBox = new VBox(4);
        for (String s : lines) {
            Label l = new Label(s);
            l.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
            l.setTextFill(Color.web("#353738"));
            l.setWrapText(true);
            detailsBox.getChildren().add(l);
        }

        block.getChildren().addAll(headingLabel, detailsBox);
        return block;
    }

    private VBox oldTipBox(String tipTitle, String bgColor, String instaLink) {
        VBox block = new VBox(8);
        block.setPadding(new Insets(13, 21, 14, 20));
        block.setMinWidth(210);
        block.setMaxWidth(250);
        block.setStyle("-fx-background-color: " + bgColor + ";" +
                "-fx-background-radius: 18;" +
                "-fx-effect: dropshadow(one-pass-box,#BDBEF7, 6,0,1,2);");

        Label lbl = new Label(tipTitle);
        lbl.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        lbl.setTextFill(Color.web("#24528C"));
        lbl.setWrapText(true);

        Button openBtn = new Button("▶ Watch Video");
        openBtn.setFont(Font.font("Arial Rounded MT Bold", 13));
        openBtn.setStyle("-fx-background-color:#DE4C8A; -fx-text-fill:white; -fx-background-radius:12;");
        openBtn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(instaLink));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        block.setAlignment(Pos.TOP_CENTER);
        block.getChildren().addAll(lbl, openBtn);
        return block;
    }
}