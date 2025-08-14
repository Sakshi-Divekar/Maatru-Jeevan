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

public class PostTradeHealthRemedies {

    private final Stage stage;
    private final VBox root = new VBox();

    private final String[] pastelGradients = {
            "linear-gradient(to bottom right, #FFDEDE, #FFBBB0)",
            "linear-gradient(to bottom right, #FFF2CC, #FFE195)",
            "linear-gradient(to bottom right, #D4F1F4, #A2D5F2)",
            "linear-gradient(to bottom right, #D6EAD8, #A7D7A2)",
            "linear-gradient(to bottom right, #FADADD, #F7A7A1)",
            "linear-gradient(to bottom right, #FFDBB6, #FFBD61)",
            "linear-gradient(to bottom right, #D7CCE0, #BBA1D9)",
            "linear-gradient(to bottom right, #FAE8C8, #F5D16C)"
    };

    private final String[] instaLinks = {
            "https://www.instagram.com/reel/DK1hamsSt5t/?igsh=MWpjdDd1YWI2cmlpZw==",
            "https://www.instagram.com/reel/DFrrZQEsFpp/?igsh=MTVhYWo2cjRrYjdoaQ==",
            "https://www.instagram.com/reel/DILQVf5SDTq/?igsh=aWFiOTd3cG5saGU0",
            "https://www.instagram.com/reel/DJCwoygoQtE/?igsh=MWYzeHg3dmxweG9haQ==",
            "https://www.instagram.com/reel/DKP-zAnITo8/?igsh=dGVqcDF6dmg1Z2V3",
            "https://www.instagram.com/reel/DFx9qaXIItU/?igsh=MTI5NmJlcmdqdnBnaw=="
    };

    private final String[] oldTipsTitles = {
            "Swaddle Baby",
            "Happy Food",
            "Basic Massage",
            "Healthy Baby",
            "Home Weapon",
            "Tummy Massage"
    };

    private final String[] motherTips = {
            "                           🌿 Daily Oil Massage (Abhyanga)\n\n\n\nOils: Sesame, coconut, or castor oil with herbs.\nBenefits: Pain relief, circulation, healing.\nDuration: 40–60 days post-delivery.",
            "                           🧣 Belly Binding (Stomach Tying)\n\n\n\nMaterial: Long cotton cloth.\nPurpose: Shrinks uterus, supports back & core.",
            "                           🛁 Warm Water Bath with Herbs\n\n\n\nIngredients: Ajwain, neem, turmeric.\nBenefits: Prevents infection, heals stitches, relaxes muscles.",
            "                               😴 Strict Sleep Schedule\n\n\n\nPractice: Sleep early, nap when baby sleeps.\nWhy: Reduces depression, hormonal balance, recovery.",
            "                               💨 Avoid Cold Wind\n\n\n\nTip: Shut windows, cover head.\nReason: Prevent chills & keep body warm.",
            "                               🧘 Postnatal Yoga or Walks\n\n\n\nStart: After 40 days with doctor advice.\nBenefits: Energy, posture, gradual weight loss.",
            "                               🛏 No Heavy Work\n\n\n\nRule: Rest 40–60 days.\nFocus: Healing and baby bonding.",
            "                               ☀️ Sunbath (Surya Snan)\n\n\n\nTime: Morning, 10–15 min.\nBenefits: Vitamin D, joint relief."
    };

    private final String[] babyTips = {
            "                           💆 Daily Warm Oil Massage\n\n\n\nOils: Coconut, almond, mustard (winter).\nBenefits: Bone strength, sleep, immunity, growth.",
            "                               ☀️ Sunbath (Surya Snan)\n\n\n\nDuration: 5–10 mins early morning.\nWhy: Vitamin D, jaundice prevention.",
            "                                   👶 Tummy Time\n\n\n\nTip: 2–5 mins daily (with supervision).\nBenefits: Neck & back strength.",
            "                                   🍼 Swaddling\n\n\n\nMethod: Snug cotton wrap.\nPurpose: Security, better sleep, reflex control.",
            "                               🧿 Black Thread / Kajal Dot\n\n\n\nBelief: Protect from evil eye (Nazar).\nPlacement: Forehead or behind ear.",
            "                               🛁 Ayurvedic Bath\n\n\n\nWater: Boil with neem or ajwain.\nUse: Skin soothing, infection protection.",
            "                           🌙 Baby Sleep Routine\n\n\n\nTip: Quiet/dim room post-sunset.\nWhy: Circadian rhythm training.",
            "                               🔕 No Loud Sounds or Visitors\n\n\n\nReason: Baby's immunity is low.\nPractice: Calm, positive surroundings."
    };

    public PostTradeHealthRemedies(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    public Scene getScene() {
        return new Scene(root, 1550, 800);
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFAF0, #E6F0FF);");
        root.setPadding(new Insets(25));
        root.setSpacing(25);

        Button backBtn = new Button("⬅ Back");
        backBtn.setFont(Font.font("Arial Rounded MT Bold", 16));
        backBtn.setStyle("-fx-background-color: #FFB6C1; -fx-text-fill: white; -fx-background-radius: 20;");
        backBtn.setOnAction(e -> {
            TradRemediesPage tradPage = new TradRemediesPage(stage);
            stage.setScene(tradPage.getScene());
        });

        HBox backBtnBox = new HBox(backBtn);
        backBtnBox.setAlignment(Pos.TOP_LEFT);
        backBtnBox.setPadding(new Insets(0, 0, 10, 10));

        Label title = new Label("Post-Delivery Health Remedies");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 38));
        title.setTextFill(Color.web("#5A2A6D"));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        Label motherLabel = new Label("🌸 Traditional Health Remedies for the Mother");
        motherLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 32));
        motherLabel.setTextFill(Color.web("#8E44AD"));
        HBox motherLabelBox = new HBox(motherLabel);
        motherLabelBox.setAlignment(Pos.CENTER_LEFT);
        motherLabelBox.setPadding(new Insets(15, 0, 10, 0));

        GridPane motherGrid = new GridPane();
        motherGrid.setHgap(30);
        motherGrid.setVgap(30);
        motherGrid.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < motherTips.length; i++) {
            VBox box = createInfoBox(motherTips[i], pastelGradients[i % pastelGradients.length]);
            box.setPrefSize(650, 220);
            motherGrid.add(box, i % 2, i / 2);
        }

        Label babyLabel = new Label("👶 Traditional Health Remedies for the Baby (0–6 Months)");
        babyLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 32));
        babyLabel.setTextFill(Color.web("#2874A6"));
        HBox babyLabelBox = new HBox(babyLabel);
        babyLabelBox.setAlignment(Pos.CENTER_LEFT);
        babyLabelBox.setPadding(new Insets(25, 0, 10, 0));

        GridPane babyGrid = new GridPane();
        babyGrid.setHgap(30);
        babyGrid.setVgap(30);
        babyGrid.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < babyTips.length; i++) {
            VBox box = createInfoBox(babyTips[i], pastelGradients[(i + 4) % pastelGradients.length]);
            box.setPrefSize(650, 220);
            babyGrid.add(box, i % 2, i / 2);
        }

        Label oldTipsLabel = new Label("Old Tips");
        oldTipsLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 32));
        oldTipsLabel.setTextFill(Color.web("#34495E"));
        HBox oldTipsLabelBox = new HBox(oldTipsLabel);
        oldTipsLabelBox.setAlignment(Pos.CENTER);
        oldTipsLabelBox.setPadding(new Insets(25, 0, 20, 0));

        GridPane oldTipsGrid = new GridPane();
        oldTipsGrid.setHgap(30);
        oldTipsGrid.setVgap(30);
        oldTipsGrid.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < instaLinks.length; i++) {
            VBox tipBox = createTipBox(oldTipsTitles[i], pastelGradients[i % pastelGradients.length], instaLinks[i]);
            tipBox.setPrefSize(230, 130);
            oldTipsGrid.add(tipBox, i % 3, i / 3);
        }

        VBox mainContent = new VBox(25, motherLabelBox, motherGrid, babyLabelBox, babyGrid, oldTipsLabelBox, oldTipsGrid);
        mainContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(backBtnBox, titleBox, scrollPane);
    }

    private VBox createInfoBox(String text, String background) {
        VBox box = new VBox();
        box.setPadding(new Insets(18));
        box.setStyle("-fx-background-color: " + background + ";" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 5, 5);");

        Label label = new Label(text);
        label.setWrapText(true);
        label.setFont(Font.font("Arial Rounded MT Bold", FontWeight.SEMI_BOLD, 18));
        label.setTextFill(Color.web("#4A235A"));

        box.getChildren().add(label);
        return box;
    }

    private VBox createTipBox(String title, String background, String instaLink) {
        VBox box = new VBox();
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: " + background + ";" +
                "-fx-background-radius: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 7, 0, 3, 3);");
        box.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setWrapText(true);
        label.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#2C3E50"));
        label.setTextAlignment(TextAlignment.CENTER);

        Button btn = new Button("Watch Video");
        btn.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        btn.setStyle("-fx-background-color: #FF6F91; -fx-text-fill: white; -fx-background-radius: 20;");
        btn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(instaLink));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        box.getChildren().addAll(label, btn);
        box.setSpacing(15);

        return box;
    }
}