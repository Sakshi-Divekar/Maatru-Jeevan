package com.priti.view;

import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MotherhoodWisdomPage {

    private final Stage stage;
    private final VBox root = new VBox();

    // Soft pastel colors (suitable for kids)
    private final String[] boxColors = {
            "#FFD6E0", // pink
            "#FFF3B0", // light yellow
            "#B2F7EF", // teal
            "#FBCBCA", // peach
            "#D0F4DE", // mint
            "#F9D5E5", // light rose
            "#D4C1EC", // lavender
            "#FFEEB3"  // creamy yellow
    };

    public MotherhoodWisdomPage(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    public Scene getScene() {
        return new Scene(root, 1550, 800);
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fffafc, #f0f8ff);");
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        // Create back button and place it at top-left
        Button backButton = new Button("⬅ Back");
        backButton.setFont(Font.font("Arial Rounded MT Bold", 15));
        backButton.setStyle("-fx-background-color: #F48C06; -fx-background-radius: 20;");
        backButton.setTextFill(Color.WHITE);
        backButton.setOnAction(e -> {
            TraditionaltipsAndRemediesPage remediesPage = new TraditionaltipsAndRemediesPage(stage);
            stage.setScene(remediesPage.getScene());
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.TOP_LEFT);

        // Title centered
        Label title = new Label("Motherhood Wisdom: Books for Garbhasanskar ✨");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#DA627D"));
        title.setAlignment(Pos.CENTER);

        VBox topSection = new VBox(10, backButtonBox, title);
        topSection.setAlignment(Pos.TOP_CENTER);

        // Book blocks in a 2-column GridPane
        GridPane booksGrid = new GridPane();
        booksGrid.setHgap(32);
        booksGrid.setVgap(32);
        booksGrid.setPadding(new Insets(20, 40, 20, 40));
        booksGrid.setAlignment(Pos.TOP_CENTER);

        List<VBox> blocks = new ArrayList<>();

        blocks.add(bookBlock("Garbhasanskar", "/assets/images/book2.jpg", "Dr. Balaji Tambe",
                "Marathi, Hindi, English",
                "This is one of the most famous books for pregnant women in India. It covers everything a mother needs to know during pregnancy — including Ayurvedic diet, daily routines, yoga, meditation, chants, and emotional care. It also includes spiritual guidance and music recommendations.",
                "https://www.google.co.in/books/edition/AYURVEDIC_GARBHA_SANSKAR/MkniDwAAQBAJ?hl=en&gbpv=0", boxColors[0]));

        blocks.add(bookBlock("Shivaji the Great Maratha", "/assets/images/book3.jpg", "Ranjit Desai",
                "English / Marathi",
                "Depicts the courage, strategic thinking, and leadership of Shivaji Maharaj. His mother Jijabai is said to have told him great stories during childhood — perfect for Garbhasanskar reading.",
                "https://www.google.co.in/books/edition/Shivaji/nYFCDwAAQBAJ?hl=en&gbpv=0", boxColors[1]));

        blocks.add(bookBlock("Shrimad Bhagavad Gita", "/assets/images/book4.jpg", "—",
                "Sanskrit / Marathi / Hindi / English",
                "Helps build mental strength, peace, and deep values through timeless spiritual wisdom.",
                "https://www.google.co.in/books/edition/%E0%A4%B6%E0%A5%8D%E0%A4%B0%E0%A5%80%E0%A4%AE%E0%A4%A6%E0%A5%8D%E0%A4%AD%E0%A4%97%E0%A4%B5%E0%A4%A6%E0%A5%8D%E0%A4%97/a4dz6yKRl9sC?hl=en&gbpv=0", boxColors[2]));

        blocks.add(bookBlock("Dnyaneshwari", "/assets/images/book5.jpg", "Sant Dnyaneshwar",
                "Marathi",
                "A poetic explanation of the Bhagavad Gita written in the 13th century. Explains spiritual wisdom in simple Marathi, helps the mother stay peaceful and spiritually uplifted.",
                "https://www.google.co.in/books/edition/The_Eternal_Wisdom_of_Dnyaneshwari/uC1tngEACAAJ?hl=en", boxColors[3]));

        blocks.add(bookBlock("Tukaram Gatha", "/assets/images/book6.jpg", "Sant Tukaram",
                "Marathi",
                "A collection of abhangas (devotional poetry) filled with love for God. Helps mothers remain peaceful and emotionally connected to the divine during pregnancy.",
                "https://www.google.co.in/books/edition/Says_Tuka/6vY1AAAAMAAJ?hl=en&kptab=overview", boxColors[4]));

        blocks.add(bookBlock("Ramayan", "/assets/images/book7.jpg", "Swami Vivekananda",
                "Hindi",
                "Spiritual and philosophical interpretation of Ramayana. Focuses on values, strength, courage, duty, and sacrifice shown by Lord Rama, Lakshmana, and Sita.",
                "https://www.google.co.in/books/edition/%E0%A4%B0%E0%A4%BE%E0%A4%AE%E0%A4%BE%E0%A4%AF%E0%A4%A3_Ramayan/F7VcDwAAQBAJ?hl=en&gbpv=0", boxColors[5]));

        blocks.add(bookBlock("Sir Swami Samarth", "/assets/images/book8.jpg", "N. S. Karandikar",
                "English",
                "Stories of his divine powers and miracles that brought healing and peace. Insights into his identity as an incarnation of Lord Dattatreya.",
                "https://www.google.co.in/books/edition/Sir_Swami_Samarth/Ukr_Jk9TDCYC?hl=en&gbpv=0", boxColors[6]));

        blocks.add(bookBlock("The Power of One Thought", "/assets/images/book1.jpg", "BK Shivani",
                "English",
                "Powerful insights into how a single positive or negative thought can transform life. Focuses on emotional healing and spiritual wisdom.",
                "https://www.slideshare.net/slideshow/power-of-one-thought-by-bk-shivanipdf/266688626#8", boxColors[7]));

        // Add to GridPane: 2 columns per row
        for (int i = 0; i < blocks.size(); i++) {
            int row = i / 2;
            int col = i % 2;
            booksGrid.add(blocks.get(i), col, row);
        }

        ScrollPane scrollPane = new ScrollPane(booksGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(topSection, scrollPane);
    }

    // Additional color parameter
    private VBox bookBlock(String title, String imgPath, String author, String language, String desc, String url, String color) {
        VBox block = new VBox(10);
        block.setPadding(new Insets(20));
        block.setMaxWidth(600);
        // Unique, playful colors for each box
        block.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 18; -fx-border-color: #FFD6FA; -fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 4, 4);");

        ImageView imageView = new ImageView(new Image(imgPath));
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#880E4F"));

        Label authorLabel = new Label("Author: " + author);
        Label langLabel = new Label("Language: " + language);
        Label descLabel = new Label(desc);
        descLabel.setWrapText(true);

        Button readNow = new Button("📖 Read Now");
        readNow.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; -fx-font-size: 13px; -fx-background-radius: 10;");
        readNow.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox textBox = new VBox(5, titleLabel, authorLabel, langLabel, descLabel, readNow);
        textBox.setAlignment(Pos.CENTER_LEFT);

        HBox content = new HBox(24, imageView, textBox);
        content.setAlignment(Pos.CENTER_LEFT);

        block.getChildren().add(content);
        return block;
    }
}