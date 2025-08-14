package com.priti.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BabyDietPlan {
    private final Stage stage;
    private Scene scene;

    // Baby-friendly palette for cards (background, text, border)
    private final String[][] months = {
            {"👶 Month 1",  "• Only breast milk or formula\n• Feed every 2–3hrs (8–12x/day)\n• No water, solids, or juice",        "#FFEBEE", "#BF5F82", "#FAC6D2"},
            {"👶 Month 2",  "• Exclusive breastfeeding or formula\n• Feed on demand\n• No extra liquids or solids",                  "#FFF3E0", "#884A39", "#FFCC80"},
            {"👶 Month 3",  "• Breast milk/formula only\n• Baby may feed more frequently (growth spurts)\n• No solids yet",           "#E3F2FD", "#3973B7", "#90CAF9"},
            {"👶 Month 4",  "• Ready for solids? Most not ready\n• Look for head control, sitting (no sooner)\n• Continue milk only", "#E8F5E9", "#29897B", "#A5D6A7"},
            {"👶 Month 5",  "• May try single-grain cereal (if ready)\n• Continue milk as main food\n• Introduce with a spoon",       "#F3E5F5", "#6F42C1", "#CE93D8"},
            {"👶 Month 6",  "• Add pureed fruits (banana, apple), veggies (carrot, peas)\n• Begin soft cereals\n• 2–3 meals/day\n• Start sips of water", "#FFFDE7", "#C67C07", "#FFF59D"},
            {"👶 7–10 Months",  "• Mashed fruits/veggies, soft finger foods\n• Well-cooked rice, dal, egg yolk (if not allergic)\n• 2–3 meals & 1–2 snacks daily\n• Continue milk feeds", "#E0F7FA", "#00838F", "#80DEEA"},
            {"👶 11–12 Months", "• Chopped food pieces (family food, less spice)\n• Curd, cheese, toast, fruits\n• 3 healthy meals daily plus snacks\n• Start cup drinking", "#FFF3E0", "#994D02", "#FFCC80"},
            {"👶 1 Year", "• Family food, cut small\n• Cooked, soft veggies, fruits, cheese, eggs\n• 3 meals + 2 snacks/day\n• Full-fat milk, avoid excess juice & no honey/whole nuts", "#FFF9C4", "#B99B2C", "#FFF176"},
            {"👶 2 Year", "• Family meals (chopped), whole grains, proteins (beans, tofu, fish, eggs)\n• Encourage self-feeding\n• Keep sweets and salty snacks limited",    "#E1F5FE", "#360788", "#81D4FA"},
            {"👶 3 Year", "• Regular meals with wide variety\n• Add salads, small raw veggies (if safe)\n• 2 cups milk/dairy daily\n• Limit processed sugar, juices",        "#FCE4EC", "#9C3461", "#F8BBD0"},
            {"👶 4 Year", "• 3 nutritious meals plus 2–3 snacks\n• Strong focus: fruits, vegs, grains, iron-rich foods\n• Allow kids to help choose healthy foods",    "#F1F8E9", "#3B793E", "#AED581"},
            {"👶 5 Year", "• Family meals: colorful plates\n• Calcium for bones; add seeds, fruits, lean proteins\n• Limit processed, high-sugar foods",                 "#EDE7F6", "#563D7C", "#B39DDB"}
    };


    // Corresponding images for each month/age group card (edit paths as per your resource location)
    private final String[] images = {
            "/assets/images/1monthmilk.jpg",
            "/assets/images/2monthmilk.jpg",
            "/assets/images/3monthmilk.jpg",
            "/assets/images/4monthmilk.jpg",
            "/assets/images/5monthmilk.jpg",
            "/assets/images/6monthmilk.jpg",
            "/assets/images/7month.jpg",
            "/assets/images/11month.jpg",
            "/assets/images/1year.jpg",
            "/assets/images/2myear.jpg",
            "/assets/images/3year.jpg",
            "/assets/images/4myear.jpg",
            "/assets/images/5myear.jpg"
    };


    public BabyDietPlan(Stage stage) {
        this.stage = stage;
        createScene();
    }


    public Scene getScene() {
        return scene;
    }


    private void createScene() {
        // Title
        Label title = new Label("Monthly & Yearly Baby Diet Plan");
        title.setFont(Font.font("Arial Rounded MT Bold", 38));
        title.setTextFill(Color.web("#37474F"));
        title.setAlignment(Pos.CENTER);


        // Grid for diet cards
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(32);
        grid.setVgap(34);
        grid.setPadding(new Insets(38, 38, 38, 38));
        grid.setMinWidth(980);


        int col = 0, row = 0;
        for (int i = 0; i < months.length; i++) {
            VBox card = createAnimatedInfoCard(
                months[i][0], months[i][1], months[i][2], months[i][3], months[i][4], images[i]);
            applyInitialScaleTransition(card, i * 110);
            grid.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Center the last block which is alone in last row (13th block)
        if (!grid.getChildren().isEmpty()) {
            Node lastBlock = grid.getChildren().get(grid.getChildren().size() - 1);
            // Make last block span all 3 columns
            GridPane.setColumnSpan(lastBlock, 3);
            // Center horizontally in the spanned area
            GridPane.setHalignment(lastBlock, HPos.CENTER);
        }


        // ScrollPane to hold the grid
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background:#ffffff00; -fx-background-color:#ffffff00;");


        // Back button
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", 16));
        backButton.setStyle("-fx-background-color:  #CE93D8 ; -fx-text-fill: #700267ff; -fx-border-color: #e468c9ff; -fx-border-radius: 8; -fx-padding: 6 14;");
        backButton.setOnAction(e -> {
            BabyCarePage babyCarePage = new BabyCarePage(stage);
            stage.setScene(babyCarePage.getScene());
        });


        // Align back button to top-left
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(18, 0, 0, 28));


        // Layout (with scrollable grid)
        VBox container = new VBox(18, backBox, title, scrollPane);
        container.setAlignment(Pos.TOP_CENTER);


        StackPane root = new StackPane(container);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFF9F9 0%, #FDF7E3 33%, #EAF6F6 77%, #F3E5F5 100%);");


        scene = new Scene(root, 1550, 800);
    }


    // Shows a diet card with hardcoded image
    private VBox createAnimatedInfoCard(String header, String content, String bgColor, String textColor, String borderColor, String imagePath) {
        Label head = new Label(header);
        head.setFont(Font.font("Verdana", 23));
        head.setTextFill(Color.web(textColor));
        head.setAlignment(Pos.CENTER);


        Label desc = new Label(content);
        desc.setFont(Font.font("Segoe UI", 16));
        desc.setTextFill(Color.web(textColor));
        desc.setWrapText(true);
        desc.setAlignment(Pos.TOP_LEFT);
        desc.setMaxWidth(290); // Ensures long text wraps and lines are visible


        ImageView cardImg = new ImageView();
        try {
            cardImg.setImage(new Image(getClass().getResourceAsStream(imagePath)));
            cardImg.setFitWidth(100);
            cardImg.setFitHeight(80);
            cardImg.setPreserveRatio(true);
            cardImg.setSmooth(true);
            cardImg.setVisible(true);
        } catch (Exception e) {
            // Hide image if not found
            cardImg.setVisible(false);
        }


        VBox card = new VBox(11, head, cardImg, desc);
        card.setPadding(new Insets(14, 14, 16, 14));
        card.setAlignment(Pos.TOP_CENTER);
        card.setMinWidth(270);
        card.setMaxWidth(340);
        card.setMinHeight(250);
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 24;" +
            "-fx-border-radius: 24;" +
            "-fx-border-color: " + borderColor + ";" +
            "-fx-border-width: 2.4;" +
            "-fx-effect: dropshadow(gaussian, #cfd8dc, 8,0,0,3);"
        );


        // Hover animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(260), card);
        scaleIn.setToX(1.075);
        scaleIn.setToY(1.075);


        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(260), card);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);


        card.setOnMouseEntered(e -> scaleIn.playFromStart());
        card.setOnMouseExited(e -> scaleOut.playFromStart());


        return card;
    }


    private void applyInitialScaleTransition(VBox card, int delayMillis) {
        ScaleTransition st = new ScaleTransition(Duration.millis(600), card);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setCycleCount(1);
        st.setDelay(Duration.millis(delayMillis));
        st.play();
    }
}