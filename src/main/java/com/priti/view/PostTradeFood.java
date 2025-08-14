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

public class PostTradeFood {

    private final Stage stage;
    private final VBox root = new VBox();

    // Kid-friendly pastel gradient colors for info boxes
    private final String[] pastelGradients = {
            "linear-gradient(to bottom right, #FFDEDE, #FFBBB0)", // Soft coral
            "linear-gradient(to bottom right, #FFF2CC, #FFE195)", // Soft yellow
            "linear-gradient(to bottom right, #D4F1F4, #A2D5F2)", // Soft cyan
            "linear-gradient(to bottom right, #D6EAD8, #A7D7A2)", // Soft green
            "linear-gradient(to bottom right, #FADADD, #F7A7A1)", // Soft pink
            "linear-gradient(to bottom right, #FFDBB6, #FFBD61)", // Soft orange
            "linear-gradient(to bottom right, #D7CCE0, #BBA1D9)", // Soft purple
            "linear-gradient(to bottom right, #FAE8C8, #F5D16C)"  // Soft gold
    };

    // Instagram video links for old food tips for baby
    private final String[] instaLinks = {
            "https://www.instagram.com/reel/DG-UJAazXf-/?igsh=MXRmNWV1bGF2N2hoYw==",
            "https://www.instagram.com/reel/DGiJiEnyaWc/?igsh=cHJ4ajhpN3p6NGVp",
            "https://www.instagram.com/reel/DHcr8w8ILwI/?igsh=MWdjaGFldHl4OXJpeA==",
            "https://www.instagram.com/reel/DGzmwjXNAFe/?igsh=MnpzMDhnbHQwc2x1",
            "https://www.instagram.com/reel/DK1hamsSt5t/?igsh=MWpjdDd1YWI2cmlpZw==",
            "https://www.instagram.com/reel/DHOZhq9yIxo/?igsh=MTB6ZXg0Ymh2dmVhcg=="
    };

    // Titles for Old Tips section
    private final String[] oldTipsTitles = {
            "Feeding Essentials",
            "Weaning Guidance",
            "Nutrition Boost",
            "Hydration Tips",
            "Sleep Support",
            "Growth Milestones"
    };

    // Mother's Tips
    private final String[] motherTips = {
            "                                           Gond ke Laddu😋\n\n\nIngredients: Gond, dry fruits, jaggery, ghee, wheat flour.\nBenefits: Strengthens back and joints, increases milk production, warms body.\nWhen: Start after 5-7 days post delivery, in winters.",
            "                                       Methi (Fenugreek) Laddus😋\n\n\nIngredients: Fenugreek, jaggery, ghee, dry fruits.\nBenefits: Improves digestion, reduces inflammation, aids uterus healing.\nTip: Eat in small quantity; fenugreek is heat-inducing.",
            "                                       Ajwain Water / Soup🍲\n\n\nPreparation: Boil ajwain seeds.\nBenefits: Reduces bloating and gas, aids digestion, helps uterus contraction.",
            "                                                       Panajiri🥗\n\n\nIngredients: Wheat flour, ghee, gond, dry ginger, dry fruits.\nBenefits: Restores strength, supports lactation, boosts immunity.\nTip: Used daily first 40 days.",
            "                                               Haldi Doodh🥛\n\n\nIngredients: Turmeric, milk, pinch of black pepper, ghee.\nBenefits: Heals internal wounds, boosts immunity, fights infection.",
            "                                                       Daliya🍲\n\n\nBenefits: High fiber & energy, easy digestion, aids bowel movement.\nTip: Add ghee and jaggery.",
            "                                   Jeera-Ginger Soup / Rice🍲\n\n\nIngredients: Cumin seeds, grated ginger, ghee.\nBenefits: Stimulates appetite, soothes digestion, controls gas.",
            "                                           Lactation Recipes🥗\n\n\nExamples: Barley water, drumstick soup, garlic-rich dishes.\nBenefits: Boosts natural breast milk."
    };

    // Baby's Tips
    private final String[] babyTips = {
            "                                                   Rice Water🥛\n\n\nWhen: 6+ months\nBenefits: Gentle on stomach, hydrating, easy digest.\nHow: Boil rice in excess water, strain, feed.",
            "                                           Moong Dal Water🍲\n\n\nWhen: 6+ months\nBenefits: Protein-rich, easily digested.\nTip: Add ghee for fat.",
            "                                               Mashed Banana🍌\n\n\nWhen: 6+ months\nBenefits: Natural energy, potassium rich.\nTip: Use ripe elaichi banana.",
            "                                               Suji Halwa😋\n\n\nWhen: 7+ months\nBenefits: Easy digest, rich carbs.\nTip: Cook with ghee and jaggery.",
            "                                           Boiled Apple Puree🍲\n\n\nWhen: 6+ months\nBenefits: Fiber rich, supports digestion.",
            "                                           Sweet Potato Mash🍲\n\n\nWhen: 7+ months\nBenefits: Beta-carotene, high fiber and energy.",
            "                                               Ragi Porridge🍲\n\n\nWhen: 8+ months\nBenefits: High calcium, iron, protein.\nTip: Traditional weaning food.",
            "                                           Dry Fruits Powder😋\n\n\nWhen: 1y+\nIngredients: Almonds, cashew, pista, cardamom.\nUse: Add to milk/porridge.\nBenefits: Brain and immunity aid."
    };

    public PostTradeFood(Stage stage) {
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

        // Back Button top-left. Click opens TradRemediesPage.
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

        // Page Title
        Label title = new Label("Post-Delivery Dietary Tips");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 38));
        title.setTextFill(Color.web("#5A2A6D"));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        // Mother Tips Section
        Label motherLabel = new Label("👩‍🍼 Traditional Food Tips for Mother");
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

        // Baby Tips Section
        Label babyLabel = new Label("👶 Traditional Food Tips for Baby");
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

        // Old Tips Section
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

        final String[] oldTipsTitles = {
                "Feeding Practices",
                "First Food",
                "Natural Immunity",
                "Balanced Diet",
                "Baby Hydration",
                "Food Section"
        };

        for (int i = 0; i < instaLinks.length; i++) {
            VBox tipBox = createTipBox(oldTipsTitles[i], pastelGradients[i % pastelGradients.length], instaLinks[i]);
            tipBox.setPrefSize(230, 130);
            oldTipsGrid.add(tipBox, i % 3, i / 3);
        }

        // Main content
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